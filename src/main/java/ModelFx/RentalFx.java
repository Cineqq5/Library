package ModelFx;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;

public class RentalFx {

    private IntegerProperty id = new SimpleIntegerProperty();
    private ObjectProperty<LocalDate> rentDate = new SimpleObjectProperty(LocalDate.now());
    private ObjectProperty<BookFx> bookFx = new SimpleObjectProperty<>();
    private ObjectProperty<ReaderFx> readerFx = new SimpleObjectProperty<>();

    public int getId() {
        return id.get();

    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public LocalDate getAddedDate() {
        return rentDate.get();
    }

    public ObjectProperty<LocalDate> addedDateProperty() {
        return rentDate;
    }

    public void setAddedDate(LocalDate addedDate) {
        this.rentDate.set(addedDate);
    }

    public BookFx getBookFx() {
        return bookFx.get();
    }

    public ObjectProperty<BookFx> bookFxProperty() {
        return bookFx;
    }

    public void setBookFx(BookFx bookFx) {
        this.bookFx.set(bookFx);
    }

    public ReaderFx getReaderFx() {
        return readerFx.get();
    }

    public ObjectProperty<ReaderFx> readerFxProperty() {
        return readerFx;
    }

    public void setReaderFx(ReaderFx readerFx) {
        this.readerFx.set(readerFx);
    }




}
