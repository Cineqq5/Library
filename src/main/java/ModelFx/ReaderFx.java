package ModelFx;

import javafx.beans.property.*;

public class ReaderFx {

    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty name = new SimpleStringProperty();
    private StringProperty surname = new SimpleStringProperty();
    private StringProperty indeks = new SimpleStringProperty();


    private BooleanProperty validNameProperty = new SimpleBooleanProperty();
    private BooleanProperty validSecondNameProperty = new SimpleBooleanProperty();
    private BooleanProperty validIndeksProperty = new SimpleBooleanProperty();

    public ReaderFx() {
        validNameProperty.bind(name.isNotEmpty());
        validSecondNameProperty.bind(surname.isNotEmpty());
        validIndeksProperty.bind(indeks.isNotEmpty());
    }


    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSurname() {
        return surname.get();
    }

    public StringProperty surnameProperty() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }


    public String getIndeks() {
        return indeks.get();
    }

    public StringProperty indeksProperty() {
        return indeks;
    }

    public void setIndeks(String indeks) {
        this.indeks.set(indeks);
    }


    @Override
    public String toString() {
        return name.get() + " " + surname.get();
    }

}
