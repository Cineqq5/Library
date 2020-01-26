package utils;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class HibernateUtil
{
    private static final SessionFactory sessionFactory ;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");

            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    private static Session getSession() throws HibernateException {
        return sessionFactory.openSession();
    }


    public static void insert(Object o) {
        Session session = getSession();
        session.beginTransaction();
        session.save(o);
        session.getTransaction().commit();
    }

    public static void delete(Object o) {
        Session session = getSession();
        session.beginTransaction();
        session.delete(o);
        session.getTransaction().commit();
    }

    public static void update(Object o) {
        Session session = getSession();
        session.beginTransaction();
        session.update(o);
        session.getTransaction().commit();
    }

    public static <T> List<T> findAll(String className) {
        Session session = getSession();
        session.beginTransaction();
        List<T> list = session.createQuery("FROM " + className).list();
        session.getTransaction().commit();
        return list;
    }


}