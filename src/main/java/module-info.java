module com.hikari.ytdlpapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.desktop;

    opens com.hikari.ytdlpapp to javafx.fxml;
    opens com.hikari.ytdlpapp.lib to javafx.fxml, com.google.gson;
    exports com.hikari.ytdlpapp;
}
