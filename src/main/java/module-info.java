module com.hikari.ytdlpapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires javafx.base;

    opens com.hikari.ytdlpapp to javafx.fxml;
    exports com.hikari.ytdlpapp;
    exports com.hikari.ytdlpapp.lib;
    opens com.hikari.ytdlpapp.lib to javafx.fxml, com.google.gson;
}
