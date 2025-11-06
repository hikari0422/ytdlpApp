package com.hikari.ytdlpapp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Label welcomeText;

    @FXML
    private TextField urlField;

    @FXML
    private ComboBox<String> formatComboBox;

    @FXML
    private VBox downloadPanel;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private TextArea logArea;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onDownloadButtonClick() {
        // TODO: Implement download logic using yt-dlp
        logArea.appendText("開始下載...\n");
        // Example: Call GetVideoFormat or integrate yt-dlp here
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Button downloadButton = (Button) downloadPanel.lookup("#downloadButton");
        downloadButton.setOnAction(e -> onDownloadButtonClick());
    }
}
