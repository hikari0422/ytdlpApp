package com.hikari.ytdlpapp.lib;

import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.ComboBox;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class GetVideoFormat {
    private final ProcessBuilder pb = new ProcessBuilder();
    private final Gson gson = new Gson();
    private static String VidUrl;

    public void startGetVideoFormat(String videoUrl, ComboBox<String> formatComboBox) {
        if (videoUrl == null || videoUrl.trim().isEmpty()) {
            System.out.println("URL 欄位是空的。");
            return;
        }

        Task<List<String>> task = new Task<>() {
            @Override
            protected List<String> call() throws Exception {
                VidUrl = videoUrl;
                pb.command("yt-dlp", "--dump-json", videoUrl.trim());
                Process process = pb.start();

                StringBuilder output = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        output.append(line);
                    }
                }

                int exitVal = process.waitFor();
                if (exitVal == 0) {
                    String videoJsonInfo = output.toString();
                    VideoInfo vidInfo = gson.fromJson(videoJsonInfo, VideoInfo.class);
                    List<String> validResolutions = new ArrayList<>();

                    if (vidInfo != null && vidInfo.getFormatList() != null) {
                        for (VideoInfo.Format format : vidInfo.getFormatList()) {
                            String res = format.getResolution();
                            String videoExt = format.getVideoExtension();
                            if (res != null && !res.isEmpty() && videoExt != null && !videoExt.equals("none")) {
                                validResolutions.add(res);
                            }
                        }
                    }

                    Set<String> videoResolutionsSet = new LinkedHashSet<>();
                    for (String res : validResolutions) {
                        String[] parts = res.split("x");
                        if (parts.length > 1) {
                            videoResolutionsSet.add(parts[1]);
                        }
                    }
                    return new ArrayList<>(videoResolutionsSet);
                } else {
                    StringBuilder errorOutput = new StringBuilder();
                    try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                        String line;
                        while ((line = errorReader.readLine()) != null) {
                            errorOutput.append(line).append("\n");
                        }
                    }
                    throw new Exception("yt-dlp 執行失敗。 Exit Code: " + exitVal + "\n錯誤訊息:\n" + errorOutput);
                }
            }
        };

        task.setOnSucceeded(event -> {
            List<String> resolutions = task.getValue();
            ObservableList<String> vidFormatsCombox = FXCollections.observableArrayList(resolutions);
            formatComboBox.setItems(vidFormatsCombox);
            formatComboBox.setDisable(false);
            System.out.println("格式已成功載入。");
        });

        task.setOnFailed(event -> {
            task.getException().printStackTrace();
        });

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    public static String GetVidUrl() {
        return VidUrl;
    }
}