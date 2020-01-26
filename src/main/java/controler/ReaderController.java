package controler;

import ModelFx.*;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;

import static Projekt.Main.readerService;
import static Projekt.Main.bookService;
import static Projekt.Main.rentalService;

public class ReaderController {

    @FXML
    public Label nameLabel;
    @FXML
    public Label surnameLabel;
    @FXML
    public Label indeksLabel;

    @FXML
    private TableView<RentalFx> rentalTableView;

    @FXML
    private TableColumn<RentalFx, String> titleColumn;

    @FXML
    private TableColumn<RentalFx, String> authorColumn;

    @FXML
    private TableColumn<RentalFx, String> statusColumn;

    @FXML
    private TableColumn<RentalFx, LocalDate> dateColumn;



    @FXML // fx:id="nameTextField"
    private TextField nameTextField;

    @FXML
    private TextField surnameTextField;

    @FXML // fx:id="indeksTextField"
    private TextField indeksTextField;

    @FXML // fx:id="saveButton"
    private Button saveButton;


    private int id=0;
    public ReaderService getReaderService() {
        return readerService;

    }


    private ReaderFx readerFx;

    @FXML
    public void initialize(){


        this.rentalTableView.setItems(rentalService.getOrderRentalList());
        this.titleColumn.setCellValueFactory(cellData -> cellData.getValue().bookFxProperty().get().titleProperty());
        this.authorColumn.setCellValueFactory(cellData -> cellData.getValue().bookFxProperty().get().authorProperty());
        this.statusColumn.setCellValueFactory(cellData -> cellData.getValue().bookFxProperty().get().statusProperty());
        this.dateColumn.setCellValueFactory(cellData -> cellData.getValue().addedDateProperty());

        bindings();

        searchClient();

    }

    public void searchClient() {
        FilteredList<RentalFx> filteredData = new FilteredList<>(rentalService.getOrderRentalList(), b-> b.getReaderFx().getIndeks().equals(indeksLabel.getText()));

        SortedList sortedData = new SortedList<>(rentalService.getOrderRentalList());
        sortedData.comparatorProperty().bind(rentalTableView.comparatorProperty());
        rentalTableView.setItems(sortedData);

    }

    public void filtr(String filtr){

        this.rentalTableView.setItems(rentalService.getOrderRentalListSpec(filtr));
    }

    public void bindings() {

        this.nameLabel.setText(readerService.readerFxObjectPropertyProperty().get().getName());
        this.surnameLabel.setText(readerService.readerFxObjectPropertyProperty().get().getSurname());
        this.indeksLabel.setText(readerService.readerFxObjectPropertyProperty().get().getIndeks());


        this.nameTextField.textProperty().bindBidirectional(readerService.readerFxObjectPropertyProperty().get().nameProperty());
        this.surnameTextField.textProperty().bindBidirectional(readerService.readerFxObjectPropertyProperty().get().surnameProperty());
        this.indeksTextField.textProperty().bindBidirectional(readerService.readerFxObjectPropertyProperty().get().indeksProperty());

        this.statusColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.rentalTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            rentalService.setRentalFxObjectPropertyEdit(newValue);
        });

    }

    @FXML
    void editIndeks() {
        showFields();

    }

    @FXML
    void editName() {
        showFields();
    }

    public void showFields(){
        nameTextField.setVisible(true);
        surnameTextField.setVisible(true);
        indeksTextField.setVisible(true);
        saveButton.setVisible(true);
    }



    @FXML
    void save() {
       // System.out.println(nameTextField.getCharacters());
        //System.out.println(surnameTextField.getCharacters());
       // System.out.println(indeksTextField.getCharacters());

        nameLabel.setText(nameTextField.getText());
        surnameLabel.setText(surnameTextField.getText());
        if(readerService.getIdByIndex(indeksTextField.getText())==-1){
            indeksLabel.setText(indeksTextField.getText());
        }
        else{
            indeksTextField.setText(indeksLabel.getText());
        }



            nameTextField.setVisible(false);
            surnameTextField.setVisible(false);
            indeksTextField.setVisible(false);
            saveButton.setVisible(false);

    }

    public void setReaderId(int id) {
        this.id=id;
    }

    public void setReaderFx(ReaderFx rek) {
        this.readerFx=rek;

    }



    public void changeStatus(TableColumn.CellEditEvent<RentalFx, String> rentalFxStringCellEditEvent) {
       if(rentalFxStringCellEditEvent.getOldValue().equals("wypozyczone")) {
            if (rentalFxStringCellEditEvent.getNewValue().equals("dostepne") || rentalFxStringCellEditEvent.getNewValue().equals("")) {

                RentalFx rentalFx = rentalService.getRentalFxObjectPropertyEdit();
                rentalService.delete(rentalFx.getId());
                new Thread(() ->  updateBook(rentalFx.getBookFx())).start();
                this.initialize();

            }
        }
    }

    private void updateBook(BookFx bookFx) {
        BookService bookService = new BookService();
        bookService.init();
        bookFx.setStatus("dostepne");
        bookService.setBookFxObjectPropertyEdit(bookFx);
        new Thread(() -> bookService.update()).start();
    }


}
