package Models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.io.File;

public class MainTab {
    private final ObjectProperty<File> file;
    private final SimpleBooleanProperty savedState;

    public MainTab(File file) {
        this.savedState = new SimpleBooleanProperty(true);
        this.file = new SimpleObjectProperty<>(file);
    }

    public boolean getSavedState() {
        return savedState.get();
    }

    public void setSavedState(boolean value) {
        savedState.set(value);
    }

    public SimpleBooleanProperty savedStateProperty() {
        return savedState;
    }

    public void setFile(File value) { 
        file.set(value); 
    }

    public ObjectProperty<File> fileProperty() { 
        return file; 
    }

    public File getFile() { 
        return file.get(); 
    }
}
