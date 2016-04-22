import model.Dummy;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by pthanhtrung on 4/20/2016.
 */
public class SimpleHibernate {
    public static SessionFactory factory;

    @BeforeClass
    public static void setup() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        try {
            Class.forName("org.hsqldb.jdbcDriver").newInstance();
        } catch (Exception e) {
            System.out.println("ERROR: failed to load HSQLDB JDBC driver.");
            e.printStackTrace();
            return;
        }


        Configuration configuration = new Configuration();
        configuration.addProperties(getHibernateProperties());
        configuration.addAnnotatedClass(Dummy.class);

        factory = configuration.buildSessionFactory();


    }

    public static Properties getHibernateProperties() {

        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
        properties.put("connection.driver_class", "org.hsqldb.jdbc.JDBCDriver");
        properties.put("hibernate.connection.url", "jdbc:hsqldb:mem:test;shutdown=true");
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.hbm2ddl.auto", "create-drop");


        return properties;
    }

    @Test
    public void initHibernate(){
        Session  session = factory.openSession();
        session.save(new Dummy());
    }

}
