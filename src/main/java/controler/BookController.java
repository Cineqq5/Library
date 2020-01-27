package controler;

import ModelFx.*;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import utils.converters.ConverterReader;


import java.time.LocalDate;

import static Projekt.Main.readerService;
import static Projekt.Main.bookService;
import static Projekt.Main.rentalService;

public class BookController {
    SortedList<BookFx> sortedData;
    @FXML
    public Label titleLabel;
    @FXML
    public Label authorLabel;


    @FXML
    private TableColumn<BookFx, String> titleColumn;
    @FXML
    private TableColumn<BookFx, String> authorColumn;
    @FXML
    private TableColumn<BookFx, String> statusColumn;

    @FXML
    private TableColumn<RentalFx, String> idReaderColumn;


    @FXML
    private TableView<BookFx> bookTableView;


    private int id = 0;
    private String title;
    private String author;

    public BookService getBookService() {
        return bookService;
    }

    Scene scene;


    @FXML
    public void initialize() {

        this.bookTableView.setItems(bookService.getBookFxObservableList());

        this.titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        this.authorColumn.setCellValueFactory(cellData -> cellData.getValue().authorProperty());
        this.statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        //this.idReaderColumn.setCellValueFactory(cellData -> cellData.getValue().readerFxProperty().get().indeksProperty());

        this.titleColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.authorColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.statusColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.idReaderColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        this.bookTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            //System.out.println(oldValue);
            System.out.println(newValue);
            bookService.setBookFxObjectPropertyEdit(newValue);
        });


    }

    public void searchBook() {
        this.titleLabel.setText(this.title);
        this.authorLabel.setText(this.author);
        FilteredList<BookFx> filteredData = new FilteredList<>(bookService.getBookFxObservableList(), b -> b.getTitle().equals(title) && b.getAuthor().equals(author));


        sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(bookTableView.comparatorProperty());
        bookTableView.setItems(sortedData);
        if (sortedData.size() == 0) {
            Stage stage = (Stage) scene.getWindow();
            stage.close();

        }
    }


    public void setBookTitle(String title) {
        this.title = title;
    }

    public void setBookAuthor(String author) {
        this.author = author;
    }

    public void setBookId(int id) {
        this.id = id;
    }


    public void delete() {
        BookFx re = new BookFx();
        int i = bookTableView.getSelectionModel().getSelectedIndex();
        i = searchToDelete(i, title, author);
        System.out.println(i);
        bookService.delete(i);
        System.out.println("wielkosc " + sortedData.size());


        reopen();

    }

    public void reopen() {
        initialize();
        searchBook();
    }

    private int searchToDelete(int i, String title, String author) {
        int j = 0;
        for (BookFx bok : sortedData) {
            if (bok.getTitle().equals(title) && bok.getAuthor().equals(author)) {
                if (j == i) {
                    System.out.println("znaleziono id: " + bok.getId());
                    return bok.getId();
                }
                j++;
            }
        }
        return 0;
    }


    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void titleEditCommit(TableColumn.CellEditEvent<BookFx, String> bookFxStringCellEditEvent) {
        int i = bookTableView.getSelectionModel().getSelectedIndex();
        BookFx rek = sortedData.get(i);
        rek.setTitle(bookFxStringCellEditEvent.getNewValue());
        bookService.setBookFxObjectPropertyEdit(rek);
        new Thread(() -> bookService.update()).start();
        reopen();
    }

    public void authorEditCommit(TableColumn.CellEditEvent<BookFx, String> bookFxStringCellEditEvent) {
        int i = bookTableView.getSelectionModel().getSelectedIndex();
        BookFx rek = sortedData.get(i);
        rek.setAuthor(bookFxStringCellEditEvent.getNewValue());
        bookService.setBookFxObjectPropertyEdit(rek);
        new Thread(() -> bookService.update()).start();
        reopen();
    }

    public void statusEditCommit(TableColumn.CellEditEvent<BookFx, String> bookFxStringCellEditEvent) {
        int i = bookTableView.getSelectionModel().getSelectedIndex();
        BookFx rek = sortedData.get(i);
        if (bookFxStringCellEditEvent.getNewValue().equals("dostepne")) {
            rek.setStatus(bookFxStringCellEditEvent.getNewValue());
            bookService.setBookFxObjectPropertyEdit(rek);
            new Thread(() -> bookService.update()).start();
        }
        reopen();
    }

    public void idReaderEdit(TableColumn.CellEditEvent cellEditEvent) {
        System.out.println(cellEditEvent.getNewValue());
        int i = bookTableView.getSelectionModel().getSelectedIndex();
        BookFx rek = sortedData.get(i);
        if (rek.getStatus().equals("dostepne")) {
            try {
                int idek = readerService.getIdByIndex("" + cellEditEvent.getNewValue());
                ReaderFx readerFx = ConverterReader.convertToReaderFx(readerService.findById(idek));
                rek.setStatus("wypozyczone");
                bookService.setBookFxObjectPropertyEdit(rek);
                bookService.update();
                addRental(rek, readerFx);
                reopen();
            } catch (Exception e) {
                System.out.println("nie udalo sie");
            }
        } else {
            System.out.println("ksiazka niedostepna");
        }


    }

    public void addRental(BookFx rek, ReaderFx readerFx) {
        RentalFx rent = new RentalFx();
        rent.setAddedDate(LocalDate.now());
        rent.setBookFx(rek);
        rent.setReaderFx(readerFx);
        rentalService.setRentalFxObjectProperty(rent);
        new Thread(() -> rentalService.persist()).start();

    }


}
