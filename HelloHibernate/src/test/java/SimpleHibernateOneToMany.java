import onetomany.Email;
import onetomany.Message;
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
public class SimpleHibernateOneToMany {
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
        configuration.addAnnotatedClass(Message.class);
        configuration.addAnnotatedClass(Email.class);
        System.out.println("Init ------------------------ ");
        factory = configuration.buildSessionFactory();


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
        Message message = new Message("Broken Msg");

        email.setMessages(Arrays.asList(message));
//        message.setEmail(email);
        session.save(email);
        session.save(message);
        emailId = email.getId();
        messageId = message.getId();
        tx.commit();
        session.close();

        org.junit.Assert.assertNotNull(email.getMessages().get(0));
        org.junit.Assert.assertNull(message.getEmail());

        session = factory.openSession();


        tx = session.beginTransaction();
        email = (Email) session.get(Email.class, emailId);
        System.out.println(email);
        message = session.get(Message.class, messageId);
        System.out.println(message);


        System.out.println("Raw values ------------- ");
        Query query = session.createSQLQuery("select\n" +
                "       * " +
                "    from\n" +
                "        Email  ");
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        System.out.println(query.list());

        query = session.createSQLQuery("select\n" +
                "       * " +
                "    from\n" +
                "        Message  ");
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        System.out.println(query.list());

        tx.commit();
        session.close();

        org.junit.Assert.assertNotNull(email.getMessages());
        org.junit.Assert.assertNull(message.getEmail());
    }


}
