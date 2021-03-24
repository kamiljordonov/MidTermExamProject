package Controllers;

import Models.MainTab;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MainTabBarController implements Initializable {
    private MainTab fileTab;
    private FileTabPaneController fileTabPaneController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        textArea.textProperty().addListener(savedStateListener());
        tab.setOnCloseRequest(removeSelectedTabIfSavedListener());
    }

    public void setFileTabPaneController(FileTabPaneController fileTabPaneController) {
        this.fileTabPaneController = fileTabPaneController;
    }

    public void saveToFile() throws IOException {
        File file = fileTab.getFile();
        if (file != null && file.isFile()) {
            writeToFile();
            updateFileTab();
        }
        else {
            promptSaveToFile();
        }
    }

    public void promptSaveToFile() throws IOException {
        FileChooser fileChooser = fileTabPaneController.getFileTabPane().getFileChooser();
        File file = fileChooser.showSaveDialog(fileTabPaneController.getWindow());
        if (file != null) {
            fileTab.setFile(file);
            writeToFile();
            updateFileTab();
        }
    }

    public MainTab getFileTab() {
        return fileTab;
    }

    public void setFileTab(MainTab mainTab) throws IOException {
        this.fileTab = mainTab;
        updateFileTab();
    }

    public void setFile(File file) throws IOException {
        fileTab.setFile(file);
        updateFileTab();
    }

    public Tab getTab() {
        return tab;
    }

    public TextArea getTextArea() {
        return textArea;
    }

    private void updateFileTab() throws IOException {
        File file = fileTab.getFile();
        if (file.isFile()) {
            tab.setText(file.getName());
            textArea.setText(getFileOutputAsString(file));
            fileTab.setSavedState(true);
        }
    }

    private void writeToFile() throws IOException {
        File file = fileTab.getFile();
        if (file != null) {
            try(FileOutputStream fos = new FileOutputStream(file);
                OutputStreamWriter osw = new OutputStreamWriter(fos);
                Writer writer = new BufferedWriter(osw)) {
                    writer.write(textArea.getText());
                    // Set saved state to true.
                    fileTab.setSavedState(true);
            }
        }
    }

    private String getFileOutputAsString(File file) throws FileNotFoundException, IOException {
        try(FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);) {
                StringBuilder sb = new StringBuilder();
                while (bis.available() > 0) {
                    sb.append((char) bis.read());
                }
                return sb.toString();
        }
    }

    private EventHandler<Event> removeSelectedTabIfSavedListener() {
        return (final Event event) -> {
            try {
                Tab tab1 = (Tab) event.getSource();
                event.consume();
                fileTabPaneController.closeFile(tab1);
            }catch (Exception ex) {
                Logger.getLogger(MainTab.class.getName()).log(Level.SEVERE, null, ex);
            }
        };
    }

    private ChangeListener<String> savedStateListener() {
        return (new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                fileTab.setSavedState(false);
            }
        });
    }

    @FXML private Tab tab;
    @FXML private TextArea textArea;
}