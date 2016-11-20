import Utils.DatabaseUtils;
import com.mongodb.DB;
import com.mongodb.Mongo;
import models.vendor.Product;
import models.vendor.Shop;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Ahereza on 11/20/16.
 */
public class ProductTest {
    private DatabaseUtils shopManager = new DatabaseUtils("shops");
    MongoCollection collection;
    MongoCollection shopCollection;
    @Before
    public void setUp() throws Exception{
        Mongo mongo = new Mongo("127.0.0.1", 27017);
        DB db = mongo.getDB("bomboka");

        Jongo jongo = new Jongo(db);
        collection = jongo.getCollection("products");
        shopCollection = jongo.getCollection("shops");


        Product soap = new Product(1600, "Dettol", "Bathing soap", "GmBH");
        Product battery = new Product(16000, "Energizer", "AAA batteries", "Energizer Power");
        Product phone = new Product(1000000, "iPhone 7", "Smartphone", "Apple");
        Product ipad = new Product(2000000, "iPad mini 2", "Tablet Computer", "Apple");
        Product note7 = new Product(1900000, "Samsung Galaxy Note 7", "Probably Explode", "Samsung");
        Product fridge = new Product(2000600, "Refridgerator", "Double door minus Zero", "HiSense");

        collection.save(soap);
        collection.save(battery);
        collection.save(phone);
        collection.save(ipad);
        collection.save(note7);
        collection.save(fridge);

        Shop kotido = shopCollection.findOne("{address: 'Kotido'}").as(Shop.class);
        Shop masaka = shopCollection.findOne("{address: 'Masaka'}").as(Shop.class);
        Shop kla = shopCollection.findOne("{address: 'Kampala'}").as(Shop.class);

        kotido.addToProductsList(fridge);
        masaka.addToProductsList(soap);
        masaka.addToProductsList(phone);
        masaka.addToProductsList(battery);
        kla.addToProductsList(fridge);
        kla.addToProductsList(note7);
        kla.addToProductsList(ipad);
        kla.addToProductsList(soap);
        kla.addToProductsList(phone);

        shopManager.updateShop(kotido);
        shopManager.updateShop(masaka);
        shopManager.updateShop(kla);

    }
    @Test
    public void testCreate() throws Exception{
        assert(2 == 2);
    }

}
