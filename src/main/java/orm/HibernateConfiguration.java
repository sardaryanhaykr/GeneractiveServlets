package orm;

import model.Basket;
import model.Item;
import model.group.Group;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import util.DatabaseConfigurationUtil;

public class HibernateConfiguration {

    private final SessionFactory factory;
    private static HibernateConfiguration sInstance;

    public static HibernateConfiguration getInstance() {
        if (sInstance == null) {
            sInstance = new HibernateConfiguration();
        }
        return sInstance;
    }

    private HibernateConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addProperties(DatabaseConfigurationUtil
                .getConnectionProperties());
        addAnnotatedClasses(configuration);
        factory = configuration.buildSessionFactory();
    }

    public Session getSession() {
        return factory.getCurrentSession();
    }

    private void addAnnotatedClasses(Configuration configuration) {
        configuration.addAnnotatedClass(Item.class);
        configuration.addAnnotatedClass(Group.class);
        configuration.addAnnotatedClass(Basket.class);
    }
}
