package controllers;

import com.mongodb.DB;
import com.mongodb.Mongo;
import models.vendor.Vendor;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

/**
 * Created by Ahereza on 11/16/16.
 */
public class DatabaseM {
    private MongoCollection collection;
    private Mongo mongo;
    private DB db;
    private Jongo jongo;

    public DatabaseM(String collectionName) {
        this.mongo = new Mongo("127.0.0.1", 27017);
        this.db = mongo.getDB("bomboka");
        this.jongo = new Jongo(db);
        this.collection = jongo.getCollection(collectionName);
    }

    public void saveObject(Vendor vendor){
        this.collection.save(vendor);
    }



}
