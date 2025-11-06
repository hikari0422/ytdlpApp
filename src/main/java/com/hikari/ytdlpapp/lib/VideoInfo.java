package com.hikari.ytdlpapp.lib;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Map;

/**
 * 用於 Gson 解析 YouTube 影片資訊 JSON 的主要 Java 類別 (Java class)。
 * 對應您提供的 yt-dlp 輸出結構。
 */
public class VideoInfo {

    @SerializedName("id")
    public String id;

    @SerializedName("title")
    public String title;

    @SerializedName("description")
    public String description;

    @SerializedName("duration")
    public int duration;

    @SerializedName("view_count")
    public long viewCount;

    @SerializedName("like_count")
    public int likeCount;

    @SerializedName("comment_count")
    public int commentCount;

    @SerializedName("channel")
    public String channel;

    @SerializedName("channel_id")
    public String channelId;

    @SerializedName("uploader")
    public String uploader;

    @SerializedName("upload_date")
    public String uploadDate; // 格式為 "YYYYMMDD"

    @SerializedName("tags")
    public List<String> tags;

    @SerializedName("categories")
    public List<String> categories;

    @SerializedName("formats")
    public List<Format> formats;

    @SerializedName("thumbnails")
    public List<Thumbnail> thumbnails;

    @SerializedName("heatmap")
    public List<HeatmapEntry> heatmap;

    // 使用 Map 來處理動態鍵 (語言代碼)
    @SerializedName("automatic_captions")
    public Map<String, List<CaptionInfo>> automaticCaptions;

    @SerializedName("subtitles")
    public Map<String, List<CaptionInfo>> subtitles;

    // --- 內部靜態類別 (Inner Static Classes) ---

    /**
     * 影片/音訊格式
     */
    public static class Format {

        @SerializedName("format_id")
        public String formatId;

        @SerializedName("ext")
        public String ext;

        @SerializedName("url")
        public String url;

        @SerializedName("width")
        public Integer width; // 使用 Integer 而非 int 以允許 null (例如 audio-only)

        @SerializedName("height")
        public Integer height; // 使用 Integer 而非 int 以允許 null

        @SerializedName("fps")
        public Double fps; // 使用 Double 以允許 null 或小數

        @SerializedName("acodec")
        public String acodec;

        @SerializedName("vcodec")
        public String vcodec;

        @SerializedName("filesize")
        public Long filesize; // 使用 Long 以允許 null 或大檔案

        @SerializedName("filesize_approx")
        public Long filesizeApprox;

        @SerializedName("tbr")
        public Double tbr; // Total Bitrate

        @SerializedName("vbr")
        public Double vbr; // Video Bitrate

        @SerializedName("abr")
        public Double abr; // Audio Bitrate

        @SerializedName("http_headers")
        public HttpHeaders httpHeaders;

        @SerializedName("fragments")
        public List<Fragment> fragments;
    }

    /**
     * HTTP 標頭
     */
    public static class HttpHeaders {
        @SerializedName("User-Agent")
        public String userAgent;

        @SerializedName("Accept")
        public String accept;

        @SerializedName("Accept-Language")
        public String acceptLanguage;
    }

    /**
     * 影片分段 (用於 storyboard)
     */
    public static class Fragment {
        @SerializedName("url")
        public String url;

        @SerializedName("duration")
        public double duration;
    }

    /**
     * 縮圖
     */
    public static class Thumbnail {
        @SerializedName("id")
        public String id;

        @SerializedName("url")
        public String url;

        @SerializedName("width")
        public Integer width;

        @SerializedName("height")
        public Integer height;

        @SerializedName("preference")
        public int preference;
    }

    /**
     * 字幕/自動字幕資訊
     */
    public static class CaptionInfo {
        @SerializedName("url")
        public String url;

        @SerializedName("ext")
        public String ext;

        @SerializedName("name")
        public String name;

        @SerializedName("protocol")
        public String protocol;
    }

    /**
     * 熱點圖 (Heatmap) 項目
     */
    public static class HeatmapEntry {
        @SerializedName("start_time")
        public double startTime;

        @SerializedName("end_time")
        public double endTime;

        @SerializedName("value")
        public double value;
    }
}