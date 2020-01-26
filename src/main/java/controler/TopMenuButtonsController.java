package controler;

import javafx.fxml.FXML;

import java.util.Locale;

public class TopMenuButtonsController {
    private static final String BOOKS_FXML = "/fxml/Ksiazki.fxml";
    private static final String READERS_FXML = "/fxml/Czytelnicy.fxml";
    private static final String RENTAL_FXML = "/fxml/Wypozyczenia.fxml";

    MainController mainController;


    @FXML
    public void initialize(){
    }

    @FXML
    public void books(){
        mainController.setLocation(BOOKS_FXML);
    }

    @FXML
    public void readers(){
       mainController.setLocation(READERS_FXML);
    }


    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }


    public void rentals() {
        mainController.setLocation(RENTAL_FXML);
    }


}
