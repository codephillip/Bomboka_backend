import com.mongodb.DB;
import com.mongodb.Mongo;
import models.vendor.VendorShops;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Ahereza on 11/16/16.
 */
public class VendorShopsTest {
    MongoCollection collection;
    @Before
    public void setUp() throws Exception{
        Mongo mongo = new Mongo("127.0.0.1", 27017);
        DB db = mongo.getDB("bomboka");

        Jongo jongo = new Jongo(db);
        collection = jongo.getCollection("vendorshops");

        collection.save(new VendorShops("Masaka"));
        collection.save(new VendorShops("Mbarara"));
        collection.save(new VendorShops("Kampala"));
    }

    @Test
    public void testCreate() throws Exception{
        // create
        collection.save(new VendorShops("Kotido"));

        // read
        VendorShops kotido = collection.findOne("{address: 'Kotido'}").as(VendorShops.class);
        assertNotNull(kotido);
        assertTrue(kotido.getAddress().equals("Kotido"));
    }

    @Test
    public void testBUpdate() throws Exception{
        VendorShops kotido = collection.findOne("{address: 'Kotido'}").as(VendorShops.class);
        double lat = 31.14;
        double lng = 32.14;

        kotido.setLongitude(lng);
        kotido.setLatitude(lat);
        collection.save(kotido);

        assertEquals(kotido.getLatitude(), lat, 0.0);
    }

    @Test
    public void testDelete() throws Exception{
        // delete
        VendorShops kotido = collection.findOne("{address: 'Kotido'}").as(VendorShops.class);
        collection.remove(kotido.get_id());

        VendorShops kotid = collection.findOne("{address: 'Kotido'}").as(VendorShops.class);
        assertNull(kotid);
    }


    @After
    public void tearDown() throws Exception{
        collection.remove("{address: 'Masaka' }");
        collection.remove("{address: 'Mbarara' }");
        collection.remove("{address: 'Kampala' }");
    }
}
