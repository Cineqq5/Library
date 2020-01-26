package controler;

import ModelFx.BookFx;
import ModelFx.BookService;
import ModelFx.ReaderFx;
import com.sun.xml.bind.v2.TODO;
import database.models.Book;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.FxmlUtils;

import static javafx.beans.binding.Bindings.createStringBinding;
import static Projekt.Main.readerService;
import static Projekt.Main.bookService;
import static Projekt.Main.rentalService;

public class BooksController {

    MainController mainController;
    int id=0;
    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private TextField amountField;
    @FXML
    private TextField statusLabel;
    SortedList<BookFx> sortedData;
    @FXML
    private TableColumn<BookFx, String> titleColumn;
    @FXML
    private TableColumn<BookFx, String>  authorColumn;
    @FXML
    private TableColumn<BookFx, String> availableColumn;
    @FXML
    private TableView<BookFx> bookTableView ;




    @FXML
    public void initialize() {


        this.bookTableView.setItems(bookService.getBookFxObservableList());

        this.titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        this.authorColumn.setCellValueFactory(cellData -> cellData.getValue().authorProperty());
        this.availableColumn.setCellValueFactory(cellData -> (createStringBinding(
                () -> (cellData.getValue().availableProperty()).getValue() + " / " + (cellData.getValue().allProperty()).getValue()
        )));

        this.titleColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.authorColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.availableColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        this.bookTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            //System.out.println(newValue);
            bookService.setBookFxObjectPropertyEdit(newValue);
        });
        searchBook();
    }

    public void searchBook() {

        FilteredList<BookFx> filteredData = new FilteredList<>(bookService.getBookFxObservableListDist(), b->true);


        sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(bookTableView.comparatorProperty());
        bookTableView.setItems(sortedData);
    }


    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }


    public void addButton() {
        String am = amountField.getText();
        int amm = Integer.parseInt(am);
        for(int i=0;i<amm;i++) {

            bookService.bookFxObjectPropertyProperty().get().titleProperty().bind(this.titleField.textProperty());
            bookService.bookFxObjectPropertyProperty().get().authorProperty().bind(this.authorField.textProperty());
            bookService.bookFxObjectPropertyProperty().get().statusProperty().bind(this.statusLabel.textProperty());

            bookService.persist();
        }

        this.initialize();
    }

    public void moreInfo() {

        if(id==bookService.getBookFxObservableList().get(bookTableView.getSelectionModel().getSelectedIndex()).getId()){

            open(id);
            System.out.println("open "+id);
        }
        else {
            id = bookService.getBookFxObservableList().get(bookTableView.getSelectionModel().getSelectedIndex()).getId();
        }

    }

    private void open(int id) {
        FXMLLoader loader = FxmlUtils.getLoader("/fxml/Ksiazka.fxml");
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        BookFx rek=sortedData.get(bookTableView.getSelectionModel().getSelectedIndex());
        BookController controller = loader.getController();

        controller.setBookTitle(rek.getTitle());
        controller.setBookAuthor(rek.getAuthor());

        controller.searchBook();
        controller.getBookService().setBookFxObjectProperty(rek);


        Stage stage = new Stage();
        stage.setScene(scene);
        controller.setScene(scene);
        //okno modalne nad aplikacją
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        this.initialize();

    }


}
