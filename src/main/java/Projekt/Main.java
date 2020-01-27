package Projekt;

import ModelFx.BookService;
import ModelFx.ReaderService;
import ModelFx.RentalService;
import database.dbutils.DbManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import utils.FxmlUtils;


public class Main extends Application {

    private static final String BORDER_PANE_MAIN_FXML = "/fxml/BorderPaneMain.fxml";
    private static final String LOADING_FXML = "/fxml/Loading.fxml";

    public static ReaderService readerService;
    public static BookService bookService;
    public static RentalService rentalService;

    private static Thread thread, thread1;
    private static volatile boolean isLoading = true;

    public static void main(String[] args) {
        thread = new Thread(Main::loading);
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {

        Pane pane = FxmlUtils.fxmlLoader(LOADING_FXML);
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        thread.start();
        thread1 = new Thread(() ->
        {
            changeGUI(primaryStage);
        });
    }

    private static void loading() {
        /*DbManager dbManager = new DbManager();
        dbManager.openCurrentSession();
        dbManager.openCurrentSessionwithTransaction();
*/


        readerService = new ReaderService();
        bookService = new BookService();
        rentalService = new RentalService();
        readerService.start();
        bookService.start();
        rentalService.start();

        readerService.init();
        bookService.init();
        rentalService.init();

        isLoading = false;
        Platform.setImplicitExit(false);
        thread1.start();
    }


    private void changeGUI(Stage primaryStage) {
        Platform.runLater(() -> {
            primaryStage.hide();
            Pane borderPane = FxmlUtils.fxmlLoader(BORDER_PANE_MAIN_FXML);
            if (borderPane != null) {
                Scene scene1 = new Scene(borderPane);
                primaryStage.setScene(scene1);
            }

            primaryStage.isMaximized();
            primaryStage.show();
        });
    }
}
