package ModelFx;

import database.dao.ReaderDao;
import database.models.Reader;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

import utils.converters.ConverterReader;

public class ReaderService extends Thread {


    private static ReaderDao readerDao;
    private ObjectProperty<ReaderFx> readerFxObjectProperty = new SimpleObjectProperty<>(new ReaderFx());
    private ObjectProperty<ReaderFx> readerFxObjectPropertyEdit = new SimpleObjectProperty<>(new ReaderFx());
    private ObservableList<ReaderFx> readerFxObservableList = FXCollections.observableArrayList();


    public static ReaderDao getReaderDao() {
        return readerDao;
    }

    public static void setReaderDao(ReaderDao readerDao) {
        ReaderService.readerDao = readerDao;
    }

    public ReaderFx getReaderFxObjectProperty() {
        return readerFxObjectProperty.get();
    }

    public ObjectProperty<ReaderFx> readerFxObjectPropertyProperty() {
        return readerFxObjectProperty;
    }

    public void setReaderFxObjectProperty(ReaderFx readerFxObjectProperty) {
        this.readerFxObjectProperty.set(readerFxObjectProperty);
    }

    public ReaderFx getReaderFxObjectPropertyEdit() {
        return readerFxObjectPropertyEdit.get();
    }

    public ObjectProperty<ReaderFx> readerFxObjectPropertyEditProperty() {
        return readerFxObjectPropertyEdit;
    }

    public void setReaderFxObjectPropertyEdit(ReaderFx readerFxObjectPropertyEdit) {
        this.readerFxObjectPropertyEdit.set(readerFxObjectPropertyEdit);
    }

    public ObservableList<ReaderFx> getReaderFxObservableList() {
        return readerFxObservableList;
    }

    public void setReaderFxObservableList(ObservableList<ReaderFx> readerFxObservableList) {
        this.readerFxObservableList = readerFxObservableList;
    }


    public ReaderService() {
        readerDao = new ReaderDao();
    }

    public void init() {
        List<Reader> readers = getClients();
        this.readerFxObservableList.clear();
        readers.forEach(e ->
        {
            ReaderFx readerFx = ConverterReader.convertToReaderFx(e);
            this.readerFxObservableList.add(readerFx);
        });

    }

    public List<Reader> getClients() {
        readerDao = new ReaderDao();
        return this.findAll();
    }

    public void persist() {
        Reader reader = ConverterReader.convertToReader(this.getReaderFxObjectProperty());
        readerDao.openCurrentSessionwithTransaction();
        readerDao.persist(reader);
        readerDao.closeCurrentSessionwithTransaction();
        this.init();
    }

    public void update() {
        Reader reader = ConverterReader.convertToReader(this.getReaderFxObjectPropertyEdit());
        readerDao.openCurrentSessionwithTransaction();
        readerDao.update(reader);
        readerDao.closeCurrentSessionwithTransaction();
        this.init();
    }

    public Reader findById(Integer id) {
        readerDao.openCurrentSession();
        Reader book = readerDao.findById(id);
        readerDao.closeCurrentSession();
        return book;
    }

    public void delete(Integer id) {
        readerDao.openCurrentSessionwithTransaction();
        Reader book = readerDao.findById(id);
        readerDao.delete(book);
        readerDao.closeCurrentSessionwithTransaction();
        this.init();
    }

    public List<Reader> findAll() {
        readerDao.openCurrentSession();
        List<Reader> readers = readerDao.findAll();
        readerDao.closeCurrentSession();
        return readers;
    }

    public int getIdByIndex(String index) {
        int id = -1;
        List<Reader> readers = findAll();
        for (Reader reader : readers) {
            if (reader.getIndeks().equals(index)) {
                id = reader.getId();
            }
        }
        return id;
    }


}
