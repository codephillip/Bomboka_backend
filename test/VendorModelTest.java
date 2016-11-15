import com.mongodb.DB;
import com.mongodb.Mongo;
import models.vendor.Vendor;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Ahereza on 11/15/16.
 */
public class VendorModelTest {
    MongoCollection collection;

    @Before
    public void setup() throws Exception{
        Mongo mongo = new Mongo("127.0.0.1", 60052);
        DB db = mongo.getDB("bomboka");

        Jongo jongo = new Jongo(db);
        collection = jongo.getCollection("vendor");

    }

    @Test
    public void createDocument() throws Exception{
        collection.save(new Vendor("Maganjo", "https://maganjo.com"));
        collection.save(new Vendor("Azam", "https://azam.com"));
    }

}
