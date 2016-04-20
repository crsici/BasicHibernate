import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.BeforeClass;

/**
 * Created by pthanhtrung on 4/20/2016.
 */
public class SimpleHibernate {
    SessionFactory factory;

    @BeforeClass
    public void setup(){
        Configuration configuration = new Configuration();
        configuration.configure();
        factory = configuration.buildSessionFactory();
    }


}
