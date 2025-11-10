package com.hikari.ytdlpapp;

import com.hikari.ytdlpapp.lib.GetVideoFormat;
import com.hikari.ytdlpapp.lib.VideoDownload;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Label welcomeText;

    private TextField urlField;

    private ComboBox<String> formatComboBox;

    private TextArea logArea;

// options arguments####################
    @FXML
    private CheckBox embedThumbnail;

    @FXML
    private CheckBox addMetadata;

    @FXML
    private void handleCheckBox(ActionEvent event) {
        CheckBox cb = (CheckBox) event.getSource();
        String arg = cb.getText();
        if (cb.isSelected()) {
            VideoDownload.AddArgs(arg);
        } else {
            VideoDownload.RemoveArgs(arg);
        }
    }
//######################################

    @FXML
    private HBox urlInput;

    @FXML
    private VBox downloadPanel;

    @FXML
    private VBox formatSelector;

    private Button getFormatsButton;

    private final GetVideoFormat getVideoFormat = new GetVideoFormat();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getFormatsButton = (Button) urlInput.lookup("#getFormatsButton");
        logArea = (TextArea) downloadPanel.lookup("#logArea");
        formatComboBox = (ComboBox<String>) formatSelector.lookup("#formatComboBox");
        urlField = (TextField) urlInput.lookup("#urlField");
        getFormatsButton.setOnAction(this::onGetFormatsButtonClick);
    }

    protected void onGetFormatsButtonClick(ActionEvent event) {
        logArea.appendText("正在取得影片格式...\n");
        formatComboBox.setDisable(true);
        getVideoFormat.startGetVideoFormat(urlField.getText(), formatComboBox);
    }

    @FXML
    protected void onDownloadButtonClick() {
        logArea.clear();
        logArea.appendText("開始下載...\n");
    }
}
