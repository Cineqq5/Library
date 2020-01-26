package ModelFx;

import database.dao.RentalDao;
import database.models.Rental;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.converters.ConverterRental;

import java.util.List;

public class RentalService extends Thread{
    private RentalDao rentalDao;


    private ObservableList<ReaderFx> readersList = FXCollections.observableArrayList();
    private ObservableList<BookFx> orderBookList = FXCollections.observableArrayList();
    private ObservableList<RentalFx> orderRentalList = FXCollections.observableArrayList();
    private ObjectProperty<RentalFx> rentalFxObjectProperty = new SimpleObjectProperty<>(new RentalFx());

    public RentalFx getRentalFxObjectPropertyEdit() {
        return rentalFxObjectPropertyEdit.get();
    }

    public ObjectProperty<RentalFx> rentalFxObjectPropertyEditProperty() {
        return rentalFxObjectPropertyEdit;
    }

    public void setRentalFxObjectPropertyEdit(RentalFx rentalFxObjectPropertyEdit) {
        this.rentalFxObjectPropertyEdit.set(rentalFxObjectPropertyEdit);
    }

    private ObjectProperty<RentalFx> rentalFxObjectPropertyEdit = new SimpleObjectProperty<>(new RentalFx());

    public ObjectProperty<RentalFx> rentalFxObjectPropertyProperty() {
        return rentalFxObjectProperty;
    }

    public void setRentalFxObjectProperty(RentalFx rentalFxObjectProperty) {
        this.rentalFxObjectProperty.set(rentalFxObjectProperty);
    }

    public RentalService(){
        rentalDao= new RentalDao();
    }

    public void init() {

        List<Rental> rentals = getItems();
        this.orderRentalList.clear();
        rentals.forEach(e->{
            RentalFx rentalFx = ConverterRental.convertToRentalFx(e);
            this.orderRentalList.add(rentalFx);
        });
    }

    private List<Rental> getItems() {
        rentalDao = new RentalDao();
        return this.findAll();
    }

    private List<Rental> findAll() {
        rentalDao.openCurrentSession();
        List<Rental> clients = rentalDao.findAll();
        rentalDao.closeCurrentSession();
        return clients;
    }

    public void delete(Integer id) {
        rentalDao.openCurrentSessionwithTransaction();
        Rental rental = rentalDao.findById(id);
        rentalDao.delete(rental);
        rentalDao.closeCurrentSessionwithTransaction();
        this.init();
    }

    public void persist() {
        Rental client = ConverterRental.convertToRental(this.rentalFxObjectPropertyProperty().get());
        rentalDao.openCurrentSessionwithTransaction();
        rentalDao.persist(client);
        rentalDao.closeCurrentSessionwithTransaction();
        this.init();
    }

    public void update() {
        Rental reader=ConverterRental.convertToRental(this.getRentalFxObjectPropertyEdit());
        rentalDao.openCurrentSessionwithTransaction();
        rentalDao.update(reader);
        rentalDao.closeCurrentSessionwithTransaction();
        this.init();
    }
    public RentalDao getRentalDao() {
        return rentalDao;
    }

    public void setRentalDao(RentalDao rentalDao) {
        this.rentalDao = rentalDao;
    }

    public ObservableList<ReaderFx> getReadersList() {
        return readersList;
    }

    public void setReadersList(ObservableList<ReaderFx> readersList) {
        this.readersList = readersList;
    }

    public ObservableList<BookFx> getOrderBookList() {
        return orderBookList;
    }

    public void setOrderBoookList(ObservableList<BookFx> orderBookList) {
        this.orderBookList = orderBookList;
    }

    public ObservableList<RentalFx> getOrderRentalList() {
        return orderRentalList;
    }

    public void setOrderRentalList(ObservableList<RentalFx> orderRentalList) {
        this.orderRentalList = orderRentalList;
    }

    public ObservableList<RentalFx> getOrderRentalListSpec(String indeks) {
        return orderRentalList.filtered(b->b.getReaderFx().getIndeks().equals(indeks));
    }


}
