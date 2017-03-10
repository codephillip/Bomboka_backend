import com.mongodb.DB;
import com.mongodb.Mongo;
import models.vendor.Vendor;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.*;

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
        collection = jongo.getCollection("vendors");

        collection.remove("{companyName: 'Unilever Ltd' }");

        //collection.insert(new Vendor("Unilever Ltd", "https://unilever.com"));
    }

    @Test
    public void createDocument() throws Exception{
        // create
        collection.insert(new Vendor("Maganjo", "https://maganjo.com"));
        collection.insert(new Vendor("Unilever Ltd", "https://unilever.com"));

        collection.insert(new Vendor("Azam", "https://azam.com"));
        collection.insert(new Vendor("Mukwano", "https://mukwano.com"));
    }

    @Test
    public void testGetterName() throws Exception{
        // read
        Vendor unilever = collection.findOne("{companyName: 'Unilever Ltd'}").as(Vendor.class);
        System.out.print(unilever);
        assert unilever.getName().equals("Unilever Ltd");

    }

    @Test
    public void testSetterName() throws Exception{
        //update
        String newName = "Unilever Ltd";
        Vendor unilever = collection.findOne("{companyName: 'Unilever Ltd'}").as(Vendor.class);
        unilever.setName(newName);
        collection.update(unilever.get_id());
        assert unilever.getName().equals(newName);
    }

    @Test
    public void testFindAll() throws Exception{
        collection.save(new Vendor("Maganjo", "https://maganjo.com"));
        MongoCursor<Vendor> cursor = collection.find().as(Vendor.class);
        List<Vendor> vendors = new ArrayList<Vendor>();
        System.out.println(cursor.count());
        while (cursor.hasNext()){
            Vendor vendor = cursor.next();
            vendors.add(vendor);
        }
        for(Vendor v : vendors){
            System.out.println(v.getName());
        }


        cursor.close();
    }

    @After
    public void tearDown() throws Exception{
        // delete
//        collection.remove("{companyName: 'Maganjo' }");
//        collection.remove("{companyName: 'Azam' }");
//        collection.remove("{companyName: 'Mukwano' }");
    }
}
