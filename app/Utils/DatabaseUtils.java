package Utils;

import com.mongodb.DB;
import com.mongodb.Mongo;
import models.courier.Courier;
import models.order.Order;
import models.user.User;
import models.vendor.*;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import play.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

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

    //save is for first time creation of object
    //update is for updating an existing object
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

    public void saveCourier(Courier courier) {
        this.collection.insert(courier);
    }

    public void saveOrder(Order order) {
        this.collection.insert(order);
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
        return this.collection.findOne(new ObjectId(vendorID)).as(Vendor.class);
    }

    public Product getProductByID(String productID) {
        return this.collection.findOne(new ObjectId(productID)).as(Product.class);
    }

    public Courier getCourierByID(String courierID) {
        return this.collection.findOne(new ObjectId(courierID)).as(Courier.class);
    }

    public Rating getRatingByID(String ratingID) {
        return this.collection.findOne(new ObjectId(ratingID)).as(Rating.class);
    }

    public Review getReviewByID(String reviewID) {
        return this.collection.findOne(new ObjectId(reviewID)).as(Review.class);
    }

    public ProductCategory getProductCategoryByID(String productCategoryID) {
        return this.collection.findOne(new ObjectId(productCategoryID)).as(ProductCategory.class);
    }

    public Coupon getCouponByID(String couponID) {
        return this.collection.findOne(new ObjectId(couponID)).as(Coupon.class);
    }

    public Order getOrderByID(String orderID) {
        return this.collection.findOne(new ObjectId(orderID)).as(Order.class);
    }

    public Shop getVendorShopDetails(String shopID) {
        return this.collection.findOne(new ObjectId(shopID)).as(Shop.class);
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
            Logger.debug(product.toString());
            products.add(product);
        }
        return products;
    }

    public List<Product> searchProducts(String keyword) {
        String capkeyword = keyword.substring(0, 1).toUpperCase() + keyword.substring(1);
        MongoCursor<Product> cursor = collection.find("{name:{$in:[#, #]}}", Pattern.compile(keyword + ".*"), Pattern.compile(capkeyword + ".*")).as(Product.class);
        List<Product> products = new ArrayList<Product>();
        while (cursor.hasNext()) {
            Product product = cursor.next();
            products.add(product);
        }
        return products;
    }

    public List<Courier> allCouriers() {
        MongoCursor<Courier> cursor = collection.find().as(Courier.class);
        List<Courier> couriers = new ArrayList<Courier>();
        while (cursor.hasNext()) {
            Courier courier = cursor.next();
            couriers.add(courier);
        }
        return couriers;
    }

    public List<Order> allOrders() {
        MongoCursor<Order> cursor = collection.find().as(Order.class);
        Logger.debug("cursor#", cursor);
        List<Order> orders = new ArrayList<Order>();
        while (cursor.hasNext()) {
            Order order = cursor.next();
            orders.add(order);
        }
        return orders;
    }

    public List<Order> getUserOrders(String userID) {
        Logger.debug("getUserOrders");
        MongoCursor<Order> cursor = collection.find().as(Order.class);
        List<Order> orders = new ArrayList<Order>();
        while (cursor.hasNext()) {
            Order order = cursor.next();
            Logger.debug(order.getBuyer().getKey() + " # " + userID);
            if (Objects.equals(order.getBuyer().getKey(), userID))
                orders.add(order);
        }
        return orders;
    }

    public List<Rating> allRatings() {
        MongoCursor<Rating> cursor = collection.find().as(Rating.class);
        List<Rating> ratings = new ArrayList<Rating>();
        while (cursor.hasNext()) {
            Rating rating = cursor.next();
            ratings.add(rating);
        }
        return ratings;
    }

    public List<Review> allReviews() {
        MongoCursor<Review> cursor = collection.find().as(Review.class);
        List<Review> reviews = new ArrayList<Review>();
        while (cursor.hasNext()) {
            Review review = cursor.next();
            reviews.add(review);
        }
        return reviews;
    }

    public List<User> allUsers() {
        MongoCursor<User> cursor = collection.find().as(User.class);
        List<User> users = new ArrayList<User>();
        while (cursor.hasNext()) {
            User user = cursor.next();
            users.add(user);
        }
        return users;
    }

    // TODO: 11/19/16 create update method
    public void updateVendor(Vendor vendor) {
        this.collection.save(vendor);
    }

    public void updateProduct(Product product) {
        this.collection.save(product);
    }

    public void updateCourier(Courier courier) {
        this.collection.save(courier);
    }

    public void updateOrder(Order order) {
        this.collection.save(order);
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

    public void updateRating(Rating rating) {
        this.collection.save(rating);
    }

    public void updateReview(Review review) {
        this.collection.save(review);
    }


    public List<Shop> findVendorShops(String vendorID) {
        MongoCursor<Shop> cursor = this.collection.find("{vendor: #}", vendorID).as(Shop.class);
        List<Shop> shops = new ArrayList<Shop>();
        while (cursor.hasNext()) {
            Shop shop = cursor.next();
            shops.add(shop);
        }
        return shops;

    }

    public List<Product> shopProducts(Shop shop) {
        List<String> objList = shop.getProducts();
        List<Product> productList = new ArrayList<>();
        for (String objId : objList){
            Product product = this.collection.findOne(new ObjectId(objId)).as(Product.class);
            if (product != null) {
                productList.add(product);
            }
        }
        return productList;
    }

    public ProductCategory getProductCategoryByName(String parent) {
        ProductCategory result = this.collection.findOne("{name:\"" + parent + "\"}").as(ProductCategory.class);
        return result;
    }

    public void registerUser(User user) {
        this.collection.insert(user);
    }

    public User getUserByUserName(String username) {
        User user = this.collection.findOne("{username:#}", username).as(User.class);
        return user;
    }

    public User getUserByEmail(String email) {
        User user = this.collection.findOne("{email:#}", email).as(User.class);
        return user;
    }

    public User getUserByID(String userID) {
        User user = this.collection.findOne(new ObjectId(userID)).as(User.class);
        return user;
    }

    public void deleteUser(String userID) {
        User user = getUserByID(userID);
        user.setActive(false);
        user.setTombStone(new Date());
        this.collection.save(user);
    }

    public void blockUser(String userID, boolean value) {
        User user = getUserByID(userID);
        user.setBlocked(value);
        this.collection.save(user);
    }

    public void updateUser(User user) {
        this.collection.save(user);
    }

}
