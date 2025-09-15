package com.shamim.Hibernate_CRUD.repository;

import com.shamim.Hibernate_CRUD.entity.User;
import com.shamim.Hibernate_CRUD.exception.DatabaseOperationException;
import com.shamim.Hibernate_CRUD.exception.UserNotFoundException;
import com.shamim.Hibernate_CRUD.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

    private final HibernateUtil hibernateUtil;

    public UserRepository(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
    }

    public User save(User user) {
        Transaction tx = null;
        try (Session session = hibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();        // start transaction
            session.saveOrUpdate(user);
            tx.commit();                             // commit to database
            return user;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();          // rollback transaction
            throw e;
        }
    }

    public List<User> findAll() {
        try (Session session = hibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from User", User.class).list();
        }
    }

    public User findById(Long id) {
        try (Session session = hibernateUtil.getSessionFactory().openSession()) {
            User user = session.get(User.class, id);
            if (user == null) {
                throw new UserNotFoundException(id);
            }
            return user;
        }
    }

    public void delete(User user) {
        if (user == null) throw new IllegalArgumentException("User cannot be null");

        Transaction tx = null;
        try (Session session = hibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.delete(user);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public void deleteById(Long id) {
        Transaction tx = null;
        try (Session session = hibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            User user = session.get(User.class, id);
            if (user == null) {
                throw new UserNotFoundException(id);
            }

            session.delete(user);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }
}
