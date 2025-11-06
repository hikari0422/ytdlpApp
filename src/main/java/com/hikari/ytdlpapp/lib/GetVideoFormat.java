package com.hikari.ytdlpapp.lib;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class GetVideoFormat {
    ProcessBuilder pb = new ProcessBuilder();
    Gson gson = new Gson();

    @FXML
    private TextField urlField;

    @FXML
    void StartGetVideoFormat(ActionEvent event) {
        String VideoUrl = urlField.getText().trim();

        pb.command("yt-dlp", "--dump-json", VideoUrl);
        String VideoJsonInfo;
        try {
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            VideoJsonInfo = output.toString();
            process.waitFor();

            VideoInfo vidInfo = gson.fromJson(VideoJsonInfo, VideoInfo.class);
            String vidJson = gson.toJson(vidInfo);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
