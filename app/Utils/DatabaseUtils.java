package Utils;

import com.mongodb.DB;
import com.mongodb.Mongo;
import models.User.User;
import models.vendor.*;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import play.Logger;

import java.util.ArrayList;
import java.util.Date;
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

    public void saveVendor(Vendor vendor) {
        this.collection.insert(vendor);
    }

    public void saveShop(Shop shop) {
        this.collection.insert(shop);
    }

    public void saveRating(Rating rating) {
        this.collection.insert(rating);
    }

    public void saveReview(Review review) {
        this.collection.insert(review);
    }

    public void saveProduct(Product product) {
        this.collection.insert(product);
    }

    public void saveProductCategory(ProductCategory productCategory) {
        this.collection.insert(productCategory);
    }

    public void saveCoupon(Coupon coupon) {
        this.collection.insert(coupon);
    }

    public List<Coupon> showCoupons() {
        MongoCursor<Coupon> cursor = collection.find().as(Coupon.class);
        List<Coupon> coupons = new ArrayList<>();
        while (cursor.hasNext()) {
            Coupon coupon = cursor.next();
            coupons.add(coupon);
        }
        return coupons;
    }

    public void deleteCoupon(String couponID) {
        this.collection.remove(new ObjectId(couponID));
    }

    public Vendor getVendorByID(String vendorID) {
        Vendor result = this.collection.findOne(new ObjectId(vendorID)).as(Vendor.class);
        return result;
    }

    public Product getProductByID(String productID) {
        Product result = this.collection.findOne(new ObjectId(productID)).as(Product.class);
        return result;
    }

    public ProductCategory getProductCategoryByID(String productCategoryID) {
        ProductCategory result = this.collection.findOne(new ObjectId(productCategoryID)).as(ProductCategory.class);
        return result;
    }

    public Coupon getCouponByID(String couponID) {
        Coupon result = this.collection.findOne(new ObjectId(couponID)).as(Coupon.class);
        return result;
    }

    public Shop getVendorShopDetails(String shopID) {
        Shop shop = this.collection.findOne(new ObjectId(shopID)).as(Shop.class);
        return shop;
    }

    public void deleteVendor(String vendorID) {
        this.collection.remove(new ObjectId(vendorID));

    }

    public void deleteShop(String shopID) {
        this.collection.remove(new ObjectId(shopID));
    }

    public void deleteProduct(String productID) {
        this.collection.remove(new ObjectId(productID));
    }

    public void deleteProductCategory(String productCategoryID) {
        this.collection.remove(new ObjectId(productCategoryID));
    }


    public List<Vendor> allVendors() {
        MongoCursor<Vendor> cursor = collection.find().as(Vendor.class);
        List<Vendor> vendors = new ArrayList<Vendor>();
        while (cursor.hasNext()) {
            Vendor vendor = cursor.next();
            vendors.add(vendor);
        }
        return vendors;
    }

    public List<ProductCategory> allProductCategorys() {
        MongoCursor<ProductCategory> cursor = collection.find().as(ProductCategory.class);
        List<ProductCategory> productCategorys = new ArrayList<ProductCategory>();
        while (cursor.hasNext()) {
            ProductCategory productCategory = cursor.next();
            productCategorys.add(productCategory);
        }
        return productCategorys;
    }

    public List<Product> allProducts() {
        MongoCursor<Product> cursor = collection.find().as(Product.class);
        List<Product> products = new ArrayList<Product>();
        while (cursor.hasNext()) {
            Product product = cursor.next();
            products.add(product);
        }
        return products;
    }

    // TODO: 11/19/16 create update method
    public void updateVendor(Vendor vendor) {
        this.collection.save(vendor);
    }

    public void updateProduct(Product product) {
        this.collection.save(product);
    }

    public void updateProductCategory(ProductCategory productCategory) {
        this.collection.save(productCategory);
    }

    public void updateShop(Shop shop) {
        this.collection.save(shop);
    }

    public void updateCoupon(Coupon coupon) {
        this.collection.save(coupon);
    }


    public List<Shop> findVendorShops(String vendorID) {
        MongoCursor<Shop> cursor = this.collection.find("{vendor: #}", new ObjectId(vendorID)).as(Shop.class);
        List<Shop> shops = new ArrayList<Shop>();
        while (cursor.hasNext()) {
            Shop shop = cursor.next();
            shops.add(shop);
        }
        return shops;

    }

    public List<Product> shopProducts(Shop shop) {
        List<ObjectId> objList = shop.getProducts();
        List<Product> productList = new ArrayList<>();
        for (ObjectId objId : objList)
            if (this.collection.findOne(objId).as(Product.class) != null) {
                productList.add(this.collection.findOne(objId).as(Product.class));
            }
        return productList;
    }

    public ProductCategory getProductCategoryByName(String parent) {
        ProductCategory result = this.collection.findOne("{name:\""+ parent +"\"}").as(ProductCategory.class);
        Logger.debug("BYNAME#" + result.getAncestorCode().get(0));
        return result;
    }

    public void registerUser(User user){
        this.collection.insert(user);
    }

    public User getUserByUserName(String username){
        User user = this.collection.findOne("{username:#}", username).as(User.class);
        return user;
    }
    public User getUserByEmail(String email){
        User user = this.collection.findOne("{email:#}", email).as(User.class);
        return user;
    }

    public User getUserByID(String userID){
        User user = this.collection.findOne(new ObjectId(userID)).as(User.class);
        return user;
    }

    public void deleteUser(String userID){
        User user = getUserByID(userID);
        user.setActive(false);
        user.setTombStone(new Date());
        this.collection.save(user);
    }

    public void blockUser(String userID, boolean value){
        User user = getUserByID(userID);
        user.setBlocked(value);
        this.collection.save(user);
    }

    public void updateUser(User user){
        this.collection.save(user);
    }

}
