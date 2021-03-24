package Main;

import Controllers.MainPaneController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MidTermTextEditorApp extends Application {
    private MainPaneController mainPaneController;
    
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/MainPane.fxml"));
        Parent root = fxmlLoader.load();
        mainPaneController = fxmlLoader.getController();
        Scene scene = new Scene(root);

        stage.setOnCloseRequest(onCloseEvent());
        stage.setTitle("MidTerm Text Editor App");
        stage.setScene(scene);
        stage.getIcons().add(new Image("/Styling/ataturk_logo.png"));
        stage.show();
    }

    private EventHandler<WindowEvent> onCloseEvent() {
        return (WindowEvent event) -> {
            try {
                closeAllTabs();
            } catch (IOException ex) {
                Logger.getLogger(MidTermTextEditorApp.class.getName()).log(Level.SEVERE, null, ex);
            }
            event.consume();
        };
    }

    private void closeAllTabs() throws IOException {
        mainPaneController.getFileTabPaneController().closeTabsAndExit();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
