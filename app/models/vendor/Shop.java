package models.vendor;

import org.bson.types.ObjectId;
import org.jongo.marshall.jackson.oid.MongoObjectId;
import play.data.format.Formats;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ahereza on 11/15/16.
 */
public class Shop {
    @MongoObjectId
    private ObjectId _id;
    private double longitude;
    private double latitude;
    //products category
    private Date timestamp;
    private Date modificationTimeStamp;
    private Date tombstone;
    private String address;
    private ObjectId vendor;
    private List<ObjectId> products;
    private List<ObjectId> reviews;
    private List<ObjectId> ratings;

    public Shop(String address) {
        this.timestamp = new Date();
        this.address = address;
        this.products = new ArrayList<ObjectId>();
        this.ratings = new ArrayList<ObjectId>();
        this.reviews = new ArrayList<ObjectId>();
    }

    public Shop(double longitude, double latitude, String address) {
        this.timestamp = new Date();
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.products = new ArrayList<ObjectId>();
        this.ratings = new ArrayList<ObjectId>();
        this.reviews = new ArrayList<ObjectId>();
    }

    public Shop() {
        this.products = new ArrayList<ObjectId>();
        this.timestamp = new Date();
        this.ratings = new ArrayList<ObjectId>();
        this.reviews = new ArrayList<ObjectId>();
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

    public ObjectId get_id() {
        return _id;
    }

    public List<ObjectId> getReviews() {
        return reviews;
    }

    public void setReviews(List<ObjectId> reviews) {
        this.reviews = reviews;
    }


    public List<ObjectId> getRatings() {
        return ratings;
    }

    public void setRatings(List<ObjectId> ratings) {
        this.ratings = ratings;
    }
    public void addReview(Review review){
        reviews.add(review.get_id());
    }

    public void addRating(Rating rating){
        ratings.add(rating.get_id());
    }

    public List<ObjectId> getProducts() {
        return products;
    }

    public void setProducts(List<ObjectId> products) {
        this.products = products;
    }

    public void addToProductsList(Product product){
        products.add(product.get_id());
    }

    public ObjectId getVendor() {
        return vendor;
    }

    public void setVendor(ObjectId vendor) {
        this.vendor = vendor;
    }
}
