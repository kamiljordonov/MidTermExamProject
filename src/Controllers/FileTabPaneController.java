package Controllers;

import Models.FileTabPane;
import Models.MainTab;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

public class FileTabPaneController implements Initializable {
    private FileTabPane fileTabPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fileTabPane = new FileTabPane(this);
        tabPane.getSelectionModel().selectedItemProperty().addListener(selectTextAreaOnTabSelection());
    }

    public void openFile() throws IOException {
        File file = fileTabPane.getFileChooser().showOpenDialog(getWindow());
        if (file != null) {
            openUniqueFile(file);      
        }
    }

    public void closeFile(Tab tab) {
        if (tab != null) {
            MainTab mainTab = getFileTab(tab);
            if (mainTab != null) {
                tabPane.getTabs().remove(tab);
                fileTabPane.removeFileTab(mainTab);
            }
        }
    }

    public void saveFile(Tab tab) throws IOException {
        if (tab != null) {
            MainTabBarController controller = getFileTabController(tab);
            if (controller != null) {
                controller.saveToFile();
            }
        }
    }

    public void saveAsFile(Tab tab) throws IOException {
        if (tab != null) {
            MainTabBarController controller = getFileTabController(tab);
            if (controller != null) {
                controller.promptSaveToFile();
            }
        }
    }

    public void saveSelectedFile() throws IOException {
        Tab tab = tabPane.getSelectionModel().getSelectedItem();
        if (tab != null) {
            saveFile(tab);
        }
    }

    public void saveAsSelectedFile() throws IOException {
        Tab tab = tabPane.getSelectionModel().getSelectedItem();
        if (tab != null) {
            saveAsFile(tab);
        }
    }

    public void closeTabsAndExit() throws IOException {
        for (Tab tab : tabPane.getTabs()) {
            Boolean isTabSaved = getFileTabController(tab).getFileTab().getSavedState();
            if (!isTabSaved) {
                return;
            }
        }
            Platform.exit();
    }


    public FileTabPane getFileTabPane() {
        return fileTabPane;
    }

    public Window getWindow() {
        return tabPane.getScene().getWindow();
    }

    @FXML public void newFile() throws IOException {
        newFile(new File("new file"));
    }

   private ChangeListener<Tab> selectTextAreaOnTabSelection() {
        return (new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                if (newValue != null) {
                    Platform.runLater(() -> {
                        Tab tab = newValue;
                        MainTabBarController controller = getFileTabController(tab);
                        if (controller != null) {
                            controller.getTextArea().requestFocus();
                        }
                    });
                }   
            }
        });
    }

    private void newFile(File file) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/MainTabBar.fxml"));
        Tab tab = fxmlLoader.load();

        MainTabBarController mainTabBarController = fxmlLoader.getController();

        MainTab mainTab = new MainTab(file);

        mainTabBarController.setFileTab(mainTab);
        mainTabBarController.setFileTabPaneController(this);

        tabPane.getTabs().add(tab);
        fileTabPane.addFileTab(mainTab, mainTabBarController);
        tabPane.getSelectionModel().select(tab);
    }

    private void openUniqueFile(File file) throws IOException {
        MainTabBarController mainTabBarController = getFileTabController(file);
        if (mainTabBarController == null) {
            newFile(file);
        }
    }

    private MainTab getFileTab(Tab tab) {
        Iterator<MainTabBarController> iter = fileTabPane.getFileTabControllerMap().values().iterator();
        for (Iterator<MainTabBarController> i = iter; iter.hasNext();) {
            MainTabBarController controller = i.next();
            if (controller.getTab().equals(tab)) {
                return controller.getFileTab();
            }
        }
        return null;
    }

    private MainTabBarController getFileTabController(Tab tab) {
        Iterator<MainTabBarController> iter = fileTabPane.getFileTabControllerMap().values().iterator();
        for (Iterator<MainTabBarController> i = iter; iter.hasNext();) {
            MainTabBarController controller = i.next();
            if (controller.getTab().equals(tab)) {
                return controller;
            }
        }
        return null;
    }

    private MainTabBarController getFileTabController(File file) {
        Iterator<MainTabBarController> iter = fileTabPane.getFileTabControllerMap().values().iterator();
        for (Iterator<MainTabBarController> i = iter; iter.hasNext();) {
            MainTabBarController controller = i.next();
            if (controller.getFileTab().getFile().equals(file)) {
                return controller;
            }
        }
        return null;
    }

    @FXML private TabPane tabPane;
}
