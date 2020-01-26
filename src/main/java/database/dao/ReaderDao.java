package database.dao;

import database.dbutils.DbManager;
import database.models.Reader;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("ALL")
public class ReaderDao extends DbManager implements CommonDaoInterface<Reader, Integer> {

    public void persist(Reader entity) {
        getCurrentSession().save(entity);
    }


    public void update(Reader entity) {
        getCurrentSession().update(entity);
    }

    @Override
    public Reader findById(Integer id) {
        Reader client = (Reader) getCurrentSession().get(Reader.class, id);
        return client;
    }


    public void delete(Reader entity) {
        getCurrentSession().delete(entity);
    }

    @SuppressWarnings("unchecked")
    public List<Reader> findAll() {
        List<Reader> readers = (List<Reader>) getCurrentSession().createQuery("from Reader ").list();
        return readers;
    }


    public void deleteAll() {
        List<Reader> entityList = findAll();
        for (Reader entity : entityList) {
            delete(entity);
        }
    }


}
