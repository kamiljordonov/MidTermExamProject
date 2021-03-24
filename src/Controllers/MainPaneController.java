package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainPaneController implements Initializable {
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public FileTabPaneController getFileTabPaneController() {
        return fileTabPaneController;
    }

    @FXML public void newFile() throws IOException  {
        fileTabPaneController.newFile();
    }

    @FXML public void openFile() throws IOException  {
        fileTabPaneController.openFile();
    }

    @FXML public void saveFile() throws IOException {
        fileTabPaneController.saveSelectedFile();
    }

    @FXML public void saveAsFile() throws IOException {
        fileTabPaneController.saveAsSelectedFile();
    }

    @FXML public void about() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/AboutDialog.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML public void univerInfo() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/UniverInfoDialog.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML private VBox vBox;
    @FXML private FileTabPaneController fileTabPaneController;
}