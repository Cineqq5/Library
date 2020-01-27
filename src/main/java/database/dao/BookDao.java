package database.dao;

import database.dbutils.DbManager;
import database.models.Book;

import java.util.List;

@SuppressWarnings("ALL")
public class BookDao extends DbManager implements CommonDaoInterface<Book, Integer> {
    @Override
    public void persist(Book entity) {
        getCurrentSession().save(entity);
    }

    @Override
    public void update(Book entity) {
        getCurrentSession().update(entity);
    }

    @Override
    public Book findById(Integer integer) {
        Book items = (Book) getCurrentSession().get(Book.class, integer);
        return items;
    }

    @Override
    public void delete(Book entity) {
        getCurrentSession().delete(entity);
    }

    @Override
    public List<Book> findAll() {
        List<Book> categories = (List<Book>) getCurrentSession().createQuery("from Book").list();
        return categories;
    }

    @Override
    public void deleteAll() {
        List<Book> entityList = findAll();
        for (Book entity : entityList) {
            delete(entity);
        }
    }
}
