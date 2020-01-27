package controler;

import ModelFx.ReaderFx;
import ModelFx.ReaderService;

import database.models.Reader;
import javafx.beans.binding.StringBinding;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.FxmlUtils;

import java.io.IOException;

import static Projekt.Main.readerService;
import static Projekt.Main.bookService;
import static Projekt.Main.rentalService;

public class ReadersController {
    MainController mainController;

    SortedList<ReaderFx> sortedData;
    @FXML
    TextField idField;
    @FXML
    TextField nameField;
    @FXML
    TextField surnameField;

    @FXML
    private TableColumn<ReaderFx, String> nameColumn;
    @FXML
    private TableColumn<ReaderFx, String> surnameColumn;
    @FXML
    private TableColumn<ReaderFx, String> indeksColumn;

    @FXML
    private TableView<ReaderFx> readerTableView;
    private static Thread thread2;
    int id = 0;


    private TextField filterField;
    //private ReaderService readerService;
    private StringBinding stringBinding;

    @FXML
    public void initialize() {

        this.readerTableView.setItems(readerService.getReaderFxObservableList());

        this.nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        this.surnameColumn.setCellValueFactory(cellData -> cellData.getValue().surnameProperty());
        this.indeksColumn.setCellValueFactory(cellData -> cellData.getValue().indeksProperty());


        this.nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.surnameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.indeksColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        searchClient();
    }


    public void searchClient() {
        FilteredList<ReaderFx> filteredData = new FilteredList<>(readerService.getReaderFxObservableList(), b -> true);


        sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(readerTableView.comparatorProperty());
        readerTableView.setItems(sortedData);
    }


    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void addButton() {

        //TopMenuButtonsController.mainController.setMainController;

        readerService.readerFxObjectPropertyProperty().get().indeksProperty().bind(this.idField.textProperty());
        readerService.readerFxObjectPropertyProperty().get().nameProperty().bind(this.nameField.textProperty());
        readerService.readerFxObjectPropertyProperty().get().surnameProperty().bind(this.surnameField.textProperty());

        new Thread(() -> readerService.persist()).start();
        ;
        this.initialize();


    }

    public void delete() {
        ReaderFx re = new ReaderFx();
        //System.out.println(readerTableView.getSelectionModel().getSelectedIndex());
        int i = readerService.getReaderFxObservableList().get(readerTableView.getSelectionModel().getSelectedIndex()).getId();
        System.out.println(i);
        ;
        new Thread(() -> readerService.delete(i)).start();
        this.initialize();


    }

    public void openReader() {
        System.out.println(readerTableView.getSelectionModel().getSelectedIndex());
        if (readerTableView.getSelectionModel().getSelectedIndex() != -1) {
            if (id == readerService.getReaderFxObservableList().get(readerTableView.getSelectionModel().getSelectedIndex()).getId()) {

                open(id);
                //System.out.println("double kill");
            } else {
                id = readerService.getReaderFxObservableList().get(readerTableView.getSelectionModel().getSelectedIndex()).getId();
            }
        } else {
            id = -1;
            //readerTableView.set;
            // readerTableView.setSelectionModel(0);
        }


    }

    public void open(int id) {
        ReaderFx re = new ReaderFx();

        FXMLLoader loader = FxmlUtils.getLoader("/fxml/Czytelnik.fxml");
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        ReaderFx rek = sortedData.get(readerTableView.getSelectionModel().getSelectedIndex());
        ReaderController controller = loader.getController();
        controller.getReaderService().setReaderFxObjectProperty(rek);
        controller.filtr(rek.getIndeks());
        controller.bindings();

        Stage stage = new Stage();
        stage.setScene(scene);

        //okno modalne nad aplikacją
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();


        //ReaderFx rek=sortedData.get(readerTableView.getSelectionModel().getSelectedIndex());
        //sortedData.get(readerTableView.getSelectionModel().getSelectedIndex()).setName("lol1");
        readerService.setReaderFxObjectPropertyEdit(rek);
        ;
        new Thread(() -> readerService.update()).start();
    }
}
