package utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.util.ResourceBundle;

/**
 * Created by ZacznijProgramowac on 06.12.2016.
 */
public class FxmlUtils {

    public static Pane fxmlLoader(String fxmlPath) {
        FXMLLoader loader = new FXMLLoader(FxmlUtils.class.getResource(fxmlPath));
        loader.getController();
        loader.setResources(getResourceBundle());
        try {
            return loader.load();
        } catch (Exception e) {
            System.out.println("upsi");
        }
        return null;
    }

    public static FXMLLoader getLoader(String fxmlPath) {
        FXMLLoader loader = new FXMLLoader(FxmlUtils.class.getResource(fxmlPath));
        loader.getController();
        loader.setResources(getResourceBundle());
        return loader;
    }


    public static ResourceBundle getResourceBundle() {
        return ResourceBundle.getBundle("bundles.message");
    }

}
