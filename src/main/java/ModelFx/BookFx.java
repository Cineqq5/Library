package ModelFx;

import javafx.beans.property.*;

public class BookFx {
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty title = new SimpleStringProperty();
    private StringProperty author = new SimpleStringProperty();
    private StringProperty status = new SimpleStringProperty();
    private StringProperty available = new SimpleStringProperty();
    private StringProperty all = new SimpleStringProperty();

    public String getAvailable() {
        return available.get();
    }

    public StringProperty availableProperty() {
        return available;
    }

    public void setAvailable(String available) {
        this.available.set(available);
    }

    public String getAll() {
        return all.get();
    }

    public StringProperty allProperty() {
        return all;
    }

    public void setAll(String all) {
        this.all.set(all);
    }








    public int getId() {        return id.get();    }

    public IntegerProperty idProperty() {        return id;    }

    public void setId(int id) {this.id.set(id);    }

    public String getTitle() {        return title.get();    }

    public StringProperty titleProperty() {        return title;    }

    public void setTitle(String title) {        this.title.set(title);    }

    public String getAuthor() {        return author.get();    }

    public StringProperty authorProperty() {        return author;    }

    public void setAuthor(String author) {        this.author.set(author);    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    private BooleanProperty validTitleProperty = new SimpleBooleanProperty();
    private BooleanProperty validAuthorProperty = new SimpleBooleanProperty();
    private BooleanProperty validStatusProperty = new SimpleBooleanProperty();
    private BooleanProperty validAvailableProperty = new SimpleBooleanProperty();



    public BookFx() {
        validTitleProperty.bind(title.isNotEmpty());
        validAuthorProperty.bind(author.isNotEmpty());
        validStatusProperty.bind(status.isNotEmpty());
        validAvailableProperty.bind(available.isNotEmpty());
        validAvailableProperty.bind(all.isNotEmpty());

    }



    @Override
    public String toString(){
        return "Ksiazka: "+
                ", Id: "+ id.get() +
                ", Tytul: "+ title.get() +
                ", Autor: "+ author.get();
    }

}
