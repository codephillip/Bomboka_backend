package models.vendor;

import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ahereza on 11/19/16.
 */
public class Product {
    @MongoId
    @MongoObjectId
    private String key;
    private double price;
    private String name;
    private String description;
    private List<String> images;
    private String productCategory;
    private Date timestamp;
    private String manufacturer;
    private List<String> reviews;
    private List<String> ratings;
    private boolean fake;

    public Product(String key, double price, String name, String description, String productCategory, Date timestamp, String manufacturer, List<String> reviews, List<String> ratings, boolean fake) {
        this.key = key;
        this.price = price;
        this.name = name;
        this.description = description;
        this.productCategory = productCategory;
        this.timestamp = timestamp;
        this.manufacturer = manufacturer;
        this.reviews = reviews;
        this.ratings = ratings;
        this.fake = fake;
    }

    public Product(double price, String name, String description, String manufacturer, String productCategory) {
        this.price = price;
        this.name = name;
        this.description = description;
        this.manufacturer = manufacturer;
        this.timestamp = new Date();
        this.ratings = new ArrayList<String>();
        this.reviews = new ArrayList<String>();
        this.productCategory = productCategory;
    }

    public Product(double price, String name, String description, String productCategory) {
        this.price = price;
        this.name = name;
        this.description = description;
        this.timestamp = new Date();
        this.ratings = new ArrayList<String>();
        this.reviews = new ArrayList<String>();
        this.productCategory = productCategory;
    }

    public Product(String name, String price, String productCategory) {
        this.price = Double.parseDouble(price);
        this.name = name;
        this.timestamp = new Date();
        this.ratings = new ArrayList<String>();
        this.reviews = new ArrayList<String>();
        this.productCategory = productCategory;
    }

    public Product() {
        this.timestamp = new Date();
        this.ratings = new ArrayList<String>();
        this.reviews = new ArrayList<String>();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public void addRating(Rating rating) {
        ratings.add(rating.getKey());
    }

    public void addReview(Review review) {
        reviews.add(review.getKey());
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isFake() {
        return fake;
    }

    public void setFake(boolean fake) {
        this.fake = fake;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
