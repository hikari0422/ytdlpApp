package com.hikari.ytdlpapp.lib;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class YtdlpCheck {
    ;
    public static boolean isYtdlpAvailable() {
        ProcessBuilder pb = new ProcessBuilder("yt-dlp", "--version");

        try {
            Process process = pb.start();
            int exitCode = process.waitFor();
            return exitCode == 0;
        } catch (Exception e) {
            showError("未找到yt-dlp指令", e);
            return false;
        }
    }

    public static void showError(String title, Exception exception) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText("yt-dlp檢查");
        alert.setContentText("錯誤: " + exception.getMessage());
        alert.showAndWait();
    }

    public static boolean showConfirmation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    static {
        if (!isYtdlpAvailable()) {
            if (showConfirmation("yt-dlp未找到", "要開始下載嗎?")) {
                try {
                    new ProcessBuilder("winget", "install", "yt-dlp").start();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("安裝完成，請重啟此應用程式");

                } catch (Exception e) {
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("錯誤");
                    alert.setContentText("無法自動安裝yt-dlp，請前往 https://github.com/yt-dlp/yt-dlp 下載");
                }
            }
        }
    }
}
