import com.mongodb.DB;
import com.mongodb.Mongo;
import models.vendor.Vendor;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * Created by Ahereza on 11/15/16.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VendorModelTest {
    MongoCollection collection;

    @Before
    public void setup() throws Exception{
        Mongo mongo = new Mongo("127.0.0.1", 27017);
        DB db = mongo.getDB("bomboka");

        Jongo jongo = new Jongo(db);
        collection = jongo.getCollection("vendor");

    }

    @Test
    public void createDocument() throws Exception{
        // create
        collection.save(new Vendor("Maganjo", "https://maganjo.com"));
        collection.save(new Vendor("Azam", "https://azam.com"));
    }

    @Test
    public void testGetterName() throws Exception{
        // read
        Vendor unilever = collection.findOne("{companyName: 'Unilever'}").as(Vendor.class);
//        System.out.print(unilever);
        assert unilever.getCompanyName().equals("Unilever");

    }

    @Test
    public void testSetterName() throws Exception{
        //update
        String newName = "Unilever Ltd";
        Vendor unilever = collection.findOne("{companyName: 'Unilever'}").as(Vendor.class);
        unilever.setCompanyName(newName);
        collection.save(unilever);
        assert unilever.getCompanyName().equals(newName);
    }

    @After
    public void tearDown() throws Exception{
        // delete
        collection.remove("{companyName: 'Maganjo' }");
        collection.remove("{companyName: 'Azam' }");
    }
}
