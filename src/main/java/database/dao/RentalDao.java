package database.dao;

import database.dbutils.DbManager;
import database.models.Reader;
import database.models.Rental;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("ALL")
public class RentalDao extends DbManager implements CommonDaoInterface<Rental, Integer> {
    @Override
    public void persist(Rental entity) {
        getCurrentSession().save(entity);
    }

    @Override
    public void update(Rental entity) {
        getCurrentSession().update(entity);
    }

    @Override
    public Rental findById(Integer integer) {
        Rental order = (Rental) getCurrentSession().get(Rental.class, integer);
        return order;
    }

    @Override
    public void delete(Rental entity) {
        getCurrentSession().delete(entity);
    }

    @SuppressWarnings("unchecked")
    public List<Rental> findAll() {
        List<Rental> rentals = (List<Rental>) getCurrentSession().createQuery("from Rental ").list();
        return rentals;
    }
    @Override
    public void deleteAll() {
        List<Rental> entityList = findAll();
        for (Rental entity : entityList) {
            delete(entity);
        }
    }

}
