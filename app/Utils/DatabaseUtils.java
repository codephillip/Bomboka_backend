package Utils;

import com.mongodb.DB;
import com.mongodb.Mongo;
import models.vendor.*;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ahereza on 11/16/16.
 */
public class DatabaseUtils {
    private MongoCollection collection;
    private Mongo mongo;
    private DB db;
    private Jongo jongo;

    public DatabaseUtils(String collectionName) {
        this.mongo = new Mongo("127.0.0.1", 27017);
        this.db = mongo.getDB("bomboka");
        this.jongo = new Jongo(db);
        this.collection = jongo.getCollection(collectionName);
    }

    public void saveVendor(Vendor vendor){
        this.collection.insert(vendor);
    }

    public void saveShop(Shop shop){
        this.collection.insert(shop);
    }

    public void saveRating(Rating rating){
        this.collection.insert(rating);
    }
    public void saveReview(Review review){
        this.collection.insert(review);
    }

    public void saveProduct(Product product){
        this.collection.insert(product);
    }
    public void saveCoupon(Coupon coupon){
        this.collection.insert(coupon);
    }
    public List<Coupon> showCoupons(){
        MongoCursor<Coupon> cursor = collection.find().as(Coupon.class);
        List<Coupon> coupons = new ArrayList<>();
        while (cursor.hasNext()){
            Coupon coupon = cursor.next();
            coupons.add(coupon);
        }
        return coupons;
    }
    public void deleteCoupon(String couponID){
        this.collection.remove(new ObjectId(couponID));
    }

    public Vendor getVendorByID(String vendorID){
        Vendor result = this.collection.findOne(new ObjectId(vendorID)).as(Vendor.class);
        return result;
    }
    public Product getProductByID(String productID){
        Product result = this.collection.findOne(new ObjectId(productID)).as(Product.class);
        return result;
    }

    public Coupon getCouponByID(String couponID){
        Coupon result = this.collection.findOne(new ObjectId(couponID)).as(Coupon.class);
        return result;
    }

    public Shop getVendorShopDetails(String shopID){
        Shop shop = this.collection.findOne(new ObjectId(shopID)).as(Shop.class);
        return shop;
    }

    public void deleteVendor(String vendorID){
        this.collection.remove(new ObjectId(vendorID));

    }

    public void deleteShop(String shopID){
        this.collection.remove(new ObjectId(shopID));
    }

    public void deleteProduct(String productID){
        this.collection.remove(new ObjectId(productID));
    }


    public List<Vendor> allVendors(){
        MongoCursor<Vendor> cursor = collection.find().as(Vendor.class);
        List<Vendor> vendors = new ArrayList<Vendor>();
        while (cursor.hasNext()){
            Vendor vendor = cursor.next();
            vendors.add(vendor);
        }
        return vendors;
    }

    // TODO: 11/19/16 create update method
    public void updateVendor(Vendor vendor){
        this.collection.save(vendor);
    }
    public void updateProduct(Product product){
        this.collection.save(product);
    }

    public void updateShop(Shop shop){
        this.collection.save(shop);
    }

    public void updateCoupon(Coupon coupon){
        this.collection.save(coupon);
    }


    public List<Shop> findVendorShops(String vendorID){
        MongoCursor<Shop> cursor = this.collection.find("{vendor: #}", new ObjectId(vendorID)).as(Shop.class);
        List<Shop> shops = new ArrayList<Shop>();
        while (cursor.hasNext()){
            Shop shop = cursor.next();
            shops.add(shop);
        }
        return shops;

    }

    public List<Product> shopProducts(Shop shop){
        List<ObjectId> objList = shop.getProducts();
        List<Product> productList = new ArrayList<>();
        for (ObjectId objId: objList)
            if (this.collection.findOne(objId).as(Product.class) != null) {
                productList.add(this.collection.findOne(objId).as(Product.class));
            }
        return productList;
    }


}
