package controler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import utils.FxmlUtils;

import java.io.IOException;

public class MainController {

    @FXML
    private StackPane mainStackPane;
    @FXML
    private BorderPane borderPane;

    @FXML
    private TopMenuButtonsController topMenuButtonsController;


    @FXML
    public void initialize() {

        topMenuButtonsController.setMainController(this);
    }


    public void setLocation(String fxmlPath) {
        Pane pane = new Pane();
        pane = FxmlUtils.fxmlLoader(fxmlPath);
        FXMLLoader loadd = FxmlUtils.getLoader(fxmlPath);
        borderPane.setCenter(pane);

    }


    public void setMainController(TopMenuButtonsController topMenuButtonsController) {
    }


}
