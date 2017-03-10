package models.vendor;

import org.bson.types.ObjectId;
import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;
import play.data.format.Formats;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ahereza on 11/15/16.
 */
public class Shop {
    @MongoId
    @MongoObjectId
    private String key;
    private double longitude;
    private double latitude;
    private String name;
    //products category
    private Date timestamp;
    private Date modificationTimeStamp;
    private Date tombstone;
    private String address;
    private Vendor vendor;
    private List<String> products;
    private List<String> reviews;
    private List<String> ratings;

    public Shop( Vendor vendor, double latitude, double longitude, String address,  String name) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.name = name;
        this.address = address;
        this.vendor = vendor;
    }

    public Shop(String address) {
        this.timestamp = new Date();
        this.address = address;
        this.products = new ArrayList<String>();
        this.ratings = new ArrayList<String>();
        this.reviews = new ArrayList<String>();
    }

    public Shop(double longitude, double latitude, String address) {
        this.timestamp = new Date();
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.products = new ArrayList<String>();
        this.ratings = new ArrayList<String>();
        this.reviews = new ArrayList<String>();
    }

    public Shop() {
        this.products = new ArrayList<String>();
        this.timestamp = new Date();
        this.ratings = new ArrayList<String>();
        this.reviews = new ArrayList<String>();
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Date getModificationTimeStamp() {
        return modificationTimeStamp;
    }

    public void setModificationTimeStamp(Date modificationTimeStamp) {
        this.modificationTimeStamp = modificationTimeStamp;
    }

    public Date getTombstone() {
        return tombstone;
    }

    public void setTombstone(Date tombstone) {
        this.tombstone = tombstone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public List<String> getReviews() {
        return reviews;
    }

    public void setReviews(List<String> reviews) {
        this.reviews = reviews;
    }


    public List<String> getRatings() {
        return ratings;
    }

    public void setRatings(List<String> ratings) {
        this.ratings = ratings;
    }
    public void addReview(Review review){
        reviews.add(review.getKey());
    }

    public void addRating(Rating rating){
        ratings.add(rating.getKey());
    }

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }

    public void addToProductsList(Product product){
        products.add(product.getKey());
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
