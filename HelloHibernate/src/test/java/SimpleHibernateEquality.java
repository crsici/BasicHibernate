import equality.Email;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Properties;

/**
 * Created by pthanhtrung on 4/20/2016.
 */
public class SimpleHibernateEquality {
    public static SessionFactory factory;

    @BeforeClass
    public static void setup() throws ClassNotFoundException, IllegalAccessException, SQLException {
        try {
            Class.forName("org.hsqldb.jdbcDriver").newInstance();
        } catch (Exception e) {
            System.out.println("ERROR: failed to load HSQLDB JDBC driver.");
            e.printStackTrace();
            return;
        }


        Configuration configuration = new Configuration();
        configuration.addProperties(getHibernateProperties());
        configuration.addAnnotatedClass(Email.class);

        factory = configuration.buildSessionFactory();
        System.out.println("Finish init ----------------------");


    }

    public static Properties getHibernateProperties() {

        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
        properties.put("connection.driver_class", "org.hsqldb.jdbc.JDBCDriver");
        properties.put("hibernate.connection.url", "jdbc:hsqldb:mem:test");
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.format_sql", "true");
        properties.put("hibernate.hbm2ddl.auto", "update");


        return properties;
    }

    @Test
    public void testBrokenInversionCode() {
        System.out.println("Test ------------------------ ");
        Long emailId;
        Long messageId;

        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        Email email = new Email("Broken");

//        message.setEmail(email);
        session.save(email);

        emailId = email.getId();

        tx.commit();
        session.close();
        email.setSubject("new");

        org.junit.Assert.assertNotNull(email.getId());

        session = factory.openSession();
        tx = session.beginTransaction();
        email = (Email) session.get(Email.class, emailId);

        System.out.println(email.getId());

        tx.commit();
        session.close();


    }


}
