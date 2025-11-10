package com.hikari.ytdlpapp.lib;

import com.google.gson.Gson;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList; // 引入 ArrayList
import java.util.List;      // 引入 List

public class GetVideoFormat {
    ProcessBuilder pb = new ProcessBuilder();
    Gson gson = new Gson();

    @FXML
    private TextField urlField;

    @FXML
    void StartGetVideoFormat(ActionEvent event) {
        String videoUrl = urlField.getText().trim();
        if (videoUrl.isEmpty()) {
            System.out.println("URL 欄位是空的。");
            return;
        }

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                // 建議：指定 yt-dlp 的完整路徑，或確保它在系統 PATH 中
                pb.command("yt-dlp", "--dump-json", videoUrl);
                String videoJsonInfo; // 原始 JSON 字串

                try {
                    Process process = pb.start();

                    // 讀取標準輸出 (yt-dlp 成功時的 JSON)
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    StringBuilder output = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        output.append(line).append("\n");
                    }
                    videoJsonInfo = output.toString();

                    // 等待行程結束
                    int exitVal = process.waitFor();

                    if (exitVal == 0 && !videoJsonInfo.isEmpty()) {
                        // --- 成功取得 JSON，開始解析 ---

                        // 1. 將 JSON 字串轉換為 VideoInfo 物件
                        VideoInfo vidInfo = gson.fromJson(videoJsonInfo, VideoInfo.class);

                        // (您可以選擇性地刪除這行，除非您真的需要 "重新" 序列化的 JSON)
                        // String vidJson = gson.toJson(vidInfo);

                        // 2. 建立一個 List 來存放結果
                        List<String> validResolutions = new ArrayList<>();

                        // 3. 迭代並過濾
                        if (vidInfo != null && vidInfo.getFormatList() != null) {
                            for (VideoInfo.Format format : vidInfo.getFormatList()) {
                                String res = format.getResolution();
                                String videoExt = format.getVideoExtension();

                                // 檢查條件
                                if (res != null && !res.isEmpty() &&
                                        videoExt != null && !videoExt.equals("none"))
                                {
                                    validResolutions.add(res);
                                }
                            }
                        }

                        // 4. 印出結果 (未來您可以在這裡更新 JavaFX 的 UI)
                        System.out.println("--- 找到的影片解析度 ---");
                        validResolutions.forEach(System.out::println);

                    } else {
                        // --- yt-dlp 執行失敗 ---
                        System.err.println("yt-dlp 執行失敗。 Exit Code: " + exitVal);
                        // 讀取錯誤訊息
                        BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                        StringBuilder errorOutput = new StringBuilder();
                        while ((line = errorReader.readLine()) != null) {
                            errorOutput.append(line).append("\n");
                        }
                        System.err.println("錯誤訊息:\n" + errorOutput);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
}