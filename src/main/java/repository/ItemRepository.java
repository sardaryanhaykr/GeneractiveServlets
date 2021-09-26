package repository;

import model.Item;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import orm.HibernateConfiguration;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Hayk on 19.07.2021.
 */
public class ItemRepository implements CrudRepository<Item, Long> {
    public static final HibernateConfiguration HIBERNATE_CONFIGURATION =
            HibernateConfiguration.getInstance();

    @Override
    public void create(Item item) {

        Session session = HibernateConfiguration.getInstance().getSession();
        try {
            Transaction transaction = session.beginTransaction();
            session.save(item);
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

    @Override
    public void update(Item item, Long id) {
        Session session = HibernateConfiguration.getInstance().getSession();
        item.setId(id);
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

    @Override
    public void delete(Long id) {
        Session session=HibernateConfiguration.getInstance().getSession();
        try {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("delete from items where id="+id);
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
        Session session = HibernateConfiguration.getInstance().getSession();
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

    public List<? extends Item> findAll() {
        List<Item> items =new ArrayList<>();
        Session session = HibernateConfiguration.getInstance().getSession();
        try {
            Transaction transaction = session.beginTransaction();
            items=session.createSQLQuery("from items").getResultList();

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
        Session session = HibernateConfiguration.getInstance().getSession();
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
        Session session = HibernateConfiguration.getInstance().getSession();
        try {
            Transaction transaction = session.beginTransaction();
            items=session.createSQLQuery("from items where price>=:"+priceL+" and price<=:"+priceH).getResultList();

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
