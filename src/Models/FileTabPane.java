package Models;

import Controllers.FileTabPaneController;
import Controllers.MainTabBarController;
import javafx.stage.FileChooser;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FileTabPane {
    private final Map<MainTab, MainTabBarController> fileTabControllerMap;
    private final FileChooser fileChooser;

    public FileTabPane(FileTabPaneController controller) {
        this.fileTabControllerMap = new HashMap<>();
        this.fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
    }

    public FileChooser getFileChooser() {
        return fileChooser;
    }

    public void addFileTab(MainTab mainTab, MainTabBarController mainTabBarController) {
        fileTabControllerMap.put(mainTab, mainTabBarController);
    }

    public void removeFileTab(MainTab mainTab) {
        fileTabControllerMap.remove(mainTab);
    }

    public Map<MainTab, MainTabBarController> getFileTabControllerMap() {
        return Collections.unmodifiableMap(fileTabControllerMap);
    }
}
