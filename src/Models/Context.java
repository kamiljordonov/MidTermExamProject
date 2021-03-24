package Models;

import Controllers.FileTabPaneController;
import Controllers.MainPaneController;

public class Context {
    private final FileTabPaneController fileTabPaneController;
    private final MainPaneController mainPaneController;

    public Context(FileTabPaneController fileTabPaneController, MainPaneController mainPaneController) {
        this.fileTabPaneController = fileTabPaneController;
        this.mainPaneController = mainPaneController;
    }

    public FileTabPaneController getFileTabPaneController() {
        return fileTabPaneController;
    }

    public MainPaneController getMainPaneController() {
        return mainPaneController;
    }
}
