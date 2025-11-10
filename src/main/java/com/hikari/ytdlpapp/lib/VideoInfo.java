package com.hikari.ytdlpapp.lib;

import com.google.gson.annotations.SerializedName;

import java.text.Format;
import java.util.List;
import java.util.Map;

/**
 * 用於 Gson 解析 YouTube 影片資訊 JSON 的主要 Java 類別 (Java class)。
 * 對應您提供的 yt-dlp 輸出結構。
 */

public class VideoInfo {
    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("formats")
    private List<Format> formatList;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<Format> getFormatList() {
        return formatList;
    }

    public static class Format {
        @SerializedName("resolution")
        private String resolution;

        @SerializedName("video_ext")
        private String videoExtension;

        public String getResolution() {
            return resolution;
        }

        public String getVideoExtension() {
            return videoExtension;
        }
    }
}
