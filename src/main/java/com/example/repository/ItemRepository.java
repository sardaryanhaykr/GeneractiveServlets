package com.example.repository;

import com.example.model.item.Item;
import com.example.model.item.ItemDto;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Created by Hayk on 19.07.2021.
 */
@Component
public class ItemRepository{
    public final SessionFactory sessionFactory;
    public ItemRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Item create(Item item) {

        Session session = sessionFactory.getCurrentSession();
        Long id;
        try {
            Transaction transaction = session.beginTransaction();
            id=(Long) session.save(item);
            transaction.commit();
        }
        catch (Exception e){
            if ( session.getTransaction().getStatus() == TransactionStatus.ACTIVE ) {
                session.getTransaction().rollback();
            }
            throw e;
        }
        finally {
            session.close();
        }
        item.setId(id);
        return item;
    }


    public void update(Item item) {
        Session session = sessionFactory.getCurrentSession();
        try {
            Transaction transaction = session.beginTransaction();
            session.merge(item);
            transaction.commit();
        }
        catch (Exception e){
            if ( session.getTransaction().getStatus() == TransactionStatus.ACTIVE ) {
                session.getTransaction().rollback();
            }
            throw e;
        }
        finally {
            session.close();
        }
    }

    public void delete(Item item) {
        Session session=sessionFactory.getCurrentSession();
        try {
            Transaction transaction = session.beginTransaction();
            session.delete(item);
            transaction.commit();
        }
        catch (Exception e){
            if ( session.getTransaction().getStatus() == TransactionStatus.ACTIVE ) {
                session.getTransaction().rollback();
            }
            throw e;
        }
        finally {
            session.close();
        }
    }

    public Optional<Item> findById(long id) {
        Item item;
        Session session = sessionFactory.getCurrentSession();
        try {
            Transaction transaction = session.beginTransaction();
            item=session.get(Item.class,id);
            transaction.commit();
        }
        catch (Exception e){
            if ( session.getTransaction().getStatus() == TransactionStatus.ACTIVE ) {
                session.getTransaction().rollback();
            }
            throw e;
        }
        finally {
            session.close();
        }
        return Optional.ofNullable(item);
    }

    public List<Item> findAll() {
        List<Item> items;
        Session session = sessionFactory.getCurrentSession();
        try {
            Transaction transaction = session.beginTransaction();
            Query query=session.createQuery("from Item",Item.class);
            items=query.list();

            transaction.commit();
        }
        catch (Exception e){
            if ( session.getTransaction().getStatus() == TransactionStatus.ACTIVE ) {
                session.getTransaction().rollback();
            }
            throw e;
        }
        return items;
    }

    public Optional<Item> findByName(String name) {
        Item item;
        Session session = sessionFactory.getCurrentSession();
        try {
            Transaction transaction = session.beginTransaction();
            item=(Item) session.createSQLQuery("from items where name=:"+name).uniqueResult();
            transaction.commit();
        }
        catch (Exception e){
            if ( session.getTransaction().getStatus() == TransactionStatus.ACTIVE ) {
                session.getTransaction().rollback();
            }
            throw e;
        }
        finally {
            session.close();
        }
        return Optional.ofNullable(item);
    }

    public List<Item> search(double priceL,double priceH){
        List<Item> items;
        Session session = sessionFactory.getCurrentSession();
        try {
            Transaction transaction = session.beginTransaction();
            Query query=session.createQuery("from Item where price>=:priceL and price<=:priceH");
            query.setParameter("priceL",priceL);
            query.setParameter("priceH",priceH);
            items=query.list();
            transaction.commit();
        }
        catch (Exception e){
            if ( session.getTransaction().getStatus() == TransactionStatus.ACTIVE ) {
                session.getTransaction().rollback();
            }
            throw e;
        }
        finally {
            session.close();
        }
        return items;
    }
}
