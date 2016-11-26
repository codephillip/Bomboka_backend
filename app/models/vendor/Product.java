package models.vendor;

import org.bson.types.ObjectId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ahereza on 11/19/16.
 */
public class Product {
    @MongoObjectId
    private ObjectId _id;
    private double price;
    private String name;
    private String description;
    private ObjectId productCategory;
    private Date timestamp;
    private String manufacturer;
    private List<ObjectId> reviews;
    private List<ObjectId> ratings;

    public ObjectId get_id() {
        return _id;
    }

    public Product(double price, String name, String description, String manufacturer, ObjectId productCategory) {
        this.price = price;
        this.name = name;
        this.description = description;
        this.manufacturer = manufacturer;
        this.timestamp = new Date();
        this.ratings = new ArrayList<ObjectId>();
        this.reviews = new ArrayList<ObjectId>();
        this.productCategory = productCategory;
    }

    public Product(double price, String name, String description, String manufacturer) {
        this.price = price;
        this.name = name;
        this.description = description;
        this.manufacturer = manufacturer;
        this.timestamp = new Date();
        this.ratings = new ArrayList<ObjectId>();
        this.reviews = new ArrayList<ObjectId>();
    }

    public Product(double price, String name, String description, ObjectId productCategory) {
        this.price = price;
        this.name = name;
        this.description = description;
        this.timestamp = new Date();
        this.ratings = new ArrayList<ObjectId>();
        this.reviews = new ArrayList<ObjectId>();
        this.productCategory = productCategory;
    }

    public Product(String name, String price, ObjectId productCategory) {
        this.price = Double.parseDouble(price);
        this.name = name;
        this.timestamp = new Date();
        this.ratings = new ArrayList<ObjectId>();
        this.reviews = new ArrayList<ObjectId>();
        this.productCategory = productCategory;
    }

    public Product() {
        this.timestamp = new Date();
        this.ratings = new ArrayList<ObjectId>();
        this.reviews = new ArrayList<ObjectId>();
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
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

    public void addRating(Rating rating) {
        ratings.add(rating.get_id());
    }

    public void addReview(Review review) {
        reviews.add(review.get_id());
    }

    public ObjectId getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ObjectId productCategory) {
        this.productCategory = productCategory;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
