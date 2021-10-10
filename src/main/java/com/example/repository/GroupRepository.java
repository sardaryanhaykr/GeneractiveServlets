package com.example.repository;

import com.example.model.group.Group;
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
public class GroupRepository {

    private final SessionFactory sessionFactory;

    public GroupRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Group create(Group group) {

        Session session = sessionFactory.getCurrentSession();
        Group created;
        try {
            Transaction transaction = session.beginTransaction();
            int id = (int) session.save(group);
            created = session.get(Group.class, id);
            transaction.commit();
        } catch (Exception e) {
            if (session.getTransaction().getStatus() == TransactionStatus.ACTIVE) {
                session.getTransaction().rollback();
            }
            throw e;
        } finally {
            session.close();
        }
        return created;
    }


    public void update(Group group, Integer id) {

        Session session = sessionFactory.getCurrentSession();
        group.setId(id);
        try {
            Transaction transaction = session.beginTransaction();
            session.merge(group);
            transaction.commit();
        } catch (Exception e) {
            if (session.getTransaction().getStatus() == TransactionStatus.ACTIVE) {
                session.getTransaction().rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }


    public void delete(Group group) {
        Session session = sessionFactory.getCurrentSession();

        try {
            Transaction transaction = session.beginTransaction();
            session.delete(group);
            transaction.commit();
        } catch (Exception e) {
            if (session.getTransaction().getStatus() == TransactionStatus.ACTIVE) {
                session.getTransaction().rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

    public Optional<Group> findById(int id) {
        Group group;
        Session session = sessionFactory.getCurrentSession();
        try {
            Transaction transaction = session.beginTransaction();
            group = session.get(Group.class, id);
            transaction.commit();
        } catch (Exception e) {
            if (session.getTransaction().getStatus() == TransactionStatus.ACTIVE) {
                session.getTransaction().rollback();
            }
            throw e;
        } finally {
            session.close();
        }
        return Optional.ofNullable(group);
    }

    public boolean isParent(int id) {
        Long count = 0L;
        Session session = sessionFactory.getCurrentSession();
        try {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("select count(*) from Group where parent.id=:id");
            query.setParameter("id", id);
            count = (Long) query.getSingleResult();
            transaction.commit();
        } catch (Exception e) {
            if (session.getTransaction().getStatus() == TransactionStatus.ACTIVE) {
                session.getTransaction().rollback();
            }
            throw e;
        } finally {
            session.close();
        }
        return count.intValue() > 0;
    }

    public List<Group> findAll() {
        Session session = sessionFactory.getCurrentSession();
        List<Group> groups;

        try {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("FROM Group", Group.class);
            groups = query.list();
            transaction.commit();
        } catch (Exception e) {
            if (session.getTransaction().getStatus() == TransactionStatus.ACTIVE) {
                session.getTransaction().rollback();
            }
            throw e;
        } finally {
            session.close();
        }
        return groups;
    }

    public List<Group> findAllRoot() {
        Session session = sessionFactory.getCurrentSession();
        List<Group> groups;
        try {
            Transaction transaction = session.beginTransaction();
            groups = session.createQuery("from Group where parent_id=null ", Group.class).list();
            transaction.commit();
        } catch (Exception e) {
            if (session.getTransaction().getStatus() == TransactionStatus.ACTIVE) {
                session.getTransaction().rollback();
            }
            throw e;
        } finally {
            session.close();
        }
        return groups;
    }

    public Optional<Group> findByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Group group;
        try {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("from Group where name=:name");
            query.setParameter("name", name);
            group = (Group) query.uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            if (session.getTransaction().getStatus() == TransactionStatus.ACTIVE) {
                session.getTransaction().rollback();
            }
            throw e;
        } finally {
            session.close();
        }
        return Optional.ofNullable(group);
    }

    public Optional<List<Group>> findByParent(int id) {
        Session session = sessionFactory.getCurrentSession();
        List<Group> groups;
        try {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("from Group  where parent.id=:id");
            query.setParameter("id", id);
            groups = query.list();
            transaction.commit();
        } catch (Exception e) {
            if (session.getTransaction().getStatus() == TransactionStatus.ACTIVE) {
                session.getTransaction().rollback();
            }
            throw e;
        } finally {
            session.close();
        }
        return Optional.ofNullable(groups);
    }
}
