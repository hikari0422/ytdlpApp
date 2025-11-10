package com.hikari.ytdlpapp.lib;

import javafx.fxml.FXML;

import java.io.IOException;
import java.util.ArrayList;

public class VideoDownload {
    static ArrayList<String> VidDLArgs = new ArrayList<>();

    public static void AddArgs(String args) {
        VidDLArgs.add(args);
    }

    public static void RemoveArgs(String args) {
        VidDLArgs.remove(args);
    }

    static void GetDownloadArgs() {
        VidDLArgs.addFirst("yt-dlp");
        VidDLArgs.add("--output");
        VidDLArgs.add("%(title)s.%(ext)s");
        VidDLArgs.add("--merge-output-format");
        VidDLArgs.add("mp4");

        VidDLArgs.addLast(GetVideoFormat.GetVidUrl());
    }

    @FXML
    public static void StartDownloadVid() {
        GetDownloadArgs();
        ProcessBuilder pb = new ProcessBuilder();
        pb.command(VidDLArgs);

        try {
            Process p = pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
