package controler;

import ModelFx.*;
import java.time.format.DateTimeFormatter;
import javafx.util.StringConverter;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import java.time.LocalDate;
import static Projekt.Main.rentalService;

public class RentalController {
    SortedList<RentalFx> sortedData;

    @FXML
    private TableView<RentalFx> rentalTableView;

    @FXML
    private TableColumn<RentalFx, String> titleColumn;

    @FXML
    private TableColumn<RentalFx, String> authorColumn;

    @FXML
    private TableColumn<RentalFx, String> readerIdColumn;

    @FXML
    private TableColumn<RentalFx, String> statusColumn;

    @FXML
    private TableColumn<RentalFx, LocalDate> dateColumn;

    @FXML
    public void initialize() {

        final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.rentalTableView.setItems(rentalService.getOrderRentalList());

        this.titleColumn.setCellValueFactory(cellData -> cellData.getValue().bookFxProperty().get().titleProperty());
        this.authorColumn.setCellValueFactory(cellData -> cellData.getValue().bookFxProperty().get().authorProperty());
        this.readerIdColumn.setCellValueFactory(cellData -> cellData.getValue().readerFxProperty().get().indeksProperty());
        this.statusColumn.setCellValueFactory(cellData -> cellData.getValue().bookFxProperty().get().statusProperty());
        this.dateColumn.setCellValueFactory(cellData -> cellData.getValue().addedDateProperty());

        this.titleColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.dateColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<LocalDate>() {

            @Override
            public String toString(LocalDate t) {
                if (t==null) {
                    return "" ;
                } else {
                    return dateFormat.format(t);
                }
            }

            @Override
            public LocalDate fromString(String string) {
                try {
                    return LocalDate.parse(string, dateFormat);
                } catch (Exception exc) {
                    return null ;
                }
            }

        }));

        searchClient();
    }

    public void searchClient() {
                sortedData = new SortedList<>(rentalService.getOrderRentalList());
        sortedData.comparatorProperty().bind(rentalTableView.comparatorProperty());
        rentalTableView.setItems(sortedData);
    }


    public void dateChange(TableColumn.CellEditEvent<RentalFx, LocalDate> rentalFxStringCellEditEvent){
        try {
            int i = rentalTableView.getSelectionModel().getSelectedIndex();
            RentalFx rent = sortedData.get(i);
            System.out.println(rentalFxStringCellEditEvent.getNewValue());
            rent.setAddedDate(rentalFxStringCellEditEvent.getNewValue());
            rentalService.setRentalFxObjectPropertyEdit(rent);
            new Thread(() -> rentalService.update()).start();
        }
        catch (Exception e ){
            System.out.println("Nie udało się zmienic daty");
        }
    }
}

