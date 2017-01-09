import com.mongodb.DB;
import com.mongodb.Mongo;
import models.vendor.Shop;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Ahereza on 11/16/16.
 */
public class ShopTest {
    MongoCollection collection;
    @Before
    public void setUp() throws Exception{
        Mongo mongo = new Mongo("127.0.0.1", 27017);
        DB db = mongo.getDB("bomboka");

        Jongo jongo = new Jongo(db);
        collection = jongo.getCollection("shops");

//        collection.insert(new Shop("Masaka", new ObjectId("58313b13e8cdc999e0553c73")));
//        collection.insert(new Shop("Mbarara", new ObjectId("58313b13e8cdc999e0553c73")));
//        collection.insert(new Shop("Kampala", new ObjectId("58313b14e8cdc999e0553c74")));
//        collection.insert(new Shop("Kotido", new ObjectId("58313b14e8cdc999e0553c74")));
//        collection.insert(new Shop("Jinja", new ObjectId("58313b14e8cdc999e0553c75")));
//        collection.insert(new Shop("Moroto", new ObjectId("58313b14e8cdc999e0553c75")));
//        collection.insert(new Shop("Apac", new ObjectId("58313b14e8cdc999e0553c76")));
//        collection.insert(new Shop("Kumi", new ObjectId("58313b14e8cdc999e0553c76")));
    }

    @Test
    public void testCreate() throws Exception{
        // create
        collection.save(new Shop("Kotido"));

        // read
        Shop kotido = collection.findOne("{address: 'Kotido'}").as(Shop.class);
        assertNotNull(kotido);
        assertTrue(kotido.getAddress().equals("Kotido"));
    }

    @Test
    public void testBUpdate() throws Exception{
        Shop kotido = collection.findOne("{address: 'Kotido'}").as(Shop.class);
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
        Shop kotido = collection.findOne("{address: 'Kotido'}").as(Shop.class);
        collection.remove(kotido.get_id());

        Shop kotid = collection.findOne("{address: 'Kotido'}").as(Shop.class);
        assertNull(kotid);
    }


    @After
    public void tearDown() throws Exception{
//        collection.remove("{address: 'Masaka' }");
//        collection.remove("{address: 'Mbarara' }");
//        collection.remove("{address: 'Kampala' }");
    }
}
