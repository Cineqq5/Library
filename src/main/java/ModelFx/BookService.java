package ModelFx;

import database.dao.BookDao;
import database.models.Book;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.converters.ConverterBook;

import java.util.List;

public class BookService extends Thread {
    private BookDao bookDao;


    private ObjectProperty<BookFx> bookFxObjectProperty = new SimpleObjectProperty<>(new BookFx());
    private ObjectProperty<BookFx> bookFxObjectPropertyEdit = new SimpleObjectProperty<>(new BookFx());
    private ObservableList<BookFx> bookFxObservableList = FXCollections.observableArrayList();

    public ObservableList<BookFx> getBookFxObservableListDist() {
        return bookFxObservableListDist;
    }

    public void createObservableListDist() {
        this.bookFxObservableListDist.clear();
        BookFx bokz;
        int i = 0;
        for (BookFx bok : bookFxObservableList) {
            i = 0;
            if (bookFxObservableListDist.size() > 0) {
                for (BookFx bookFx : bookFxObservableListDist) {
                    bokz = bookFx;
                    if (bokz.getTitle().equals(bok.getTitle())) {
                        if (i == 0) {
                            if (bok.getStatus().equals("dostepne")) {
                                bookFx.setAvailable("" + (Integer.parseInt(bookFx.getAvailable()) + 1));
                            }
                            bookFx.setAll("" + (Integer.parseInt(bookFx.getAll()) + 1));

                        }
                        i = 1;
                    }
                }
            }
            if (i == 0) {
                int id = this.bookFxObservableListDist.size();
                this.bookFxObservableListDist.add(bok);
                if (bok.getStatus().equals("dostepne")) {
                    this.bookFxObservableListDist.get(id).setAvailable("1");
                } else {
                    this.bookFxObservableListDist.get(id).setAvailable("0");
                }
                this.bookFxObservableListDist.get(id).setAll("1");
            }
        }
    }

    private ObservableList<BookFx> bookFxObservableListDist = FXCollections.observableArrayList();

    public BookService() {
        bookDao = new BookDao();
    }

    public void init() {
        List<Book> books = getBooks();
        this.bookFxObservableList.clear();
        books.forEach(e ->
        {
            BookFx bookFx = ConverterBook.convertToBookFx(e);
            this.bookFxObservableList.add(bookFx);
        });
        createObservableListDist();

    }

    public void persist() {
        Book client = ConverterBook.convertToBook(this.getBookFxObjectProperty());
        bookDao.openCurrentSessionwithTransaction();
        bookDao.persist(client);
        bookDao.closeCurrentSessionwithTransaction();

        this.init();
    }

    public List<Book> getBooks() {
        bookDao = new BookDao();
        return this.findAll();
    }

    public void delete(Integer id) {
        bookDao.openCurrentSessionwithTransaction();
        Book book = bookDao.findById(id);
        bookDao.delete(book);
        bookDao.closeCurrentSessionwithTransaction();

        this.init();
    }

    public void update() {
        Book entity = ConverterBook.convertToBook(this.getBookFxObjectPropertyEdit());
        bookDao.openCurrentSessionwithTransaction();
        bookDao.update(entity);
        bookDao.closeCurrentSessionwithTransaction();
        this.init();
    }


    public BookDao getBookDao() {
        return bookDao;
    }

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public BookFx getBookFxObjectProperty() {
        return bookFxObjectProperty.get();
    }

    public ObjectProperty<BookFx> bookFxObjectPropertyProperty() {
        return bookFxObjectProperty;
    }

    public void setBookFxObjectProperty(BookFx bookFxObjectProperty) {
        this.bookFxObjectProperty.set(bookFxObjectProperty);
    }

    public BookFx getBookFxObjectPropertyEdit() {
        return bookFxObjectPropertyEdit.get();
    }

    public ObjectProperty<BookFx> bookFxObjectPropertyEditProperty() {
        return bookFxObjectPropertyEdit;
    }

    public void setBookFxObjectPropertyEdit(BookFx bookFxObjectPropertyEdit) {
        this.bookFxObjectPropertyEdit.set(bookFxObjectPropertyEdit);
    }

    public ObservableList<BookFx> getBookFxObservableList() {
        return bookFxObservableList;
    }

    public void setBookFxObservableList(ObservableList<BookFx> bookFxObservableList) {
        this.bookFxObservableList = bookFxObservableList;
    }

    public List<Book> findAll() {
        bookDao.openCurrentSession();
        List<Book> books = bookDao.findAll();
        bookDao.closeCurrentSession();
        return books;
    }


}
