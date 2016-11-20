package models.vendor;

import org.bson.types.ObjectId;
import org.jongo.marshall.jackson.oid.MongoObjectId;
import play.data.format.Formats;

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
    //products category
    private Date timestamp;
    private String manufacturer;
    private List<Review> reviews;
    private List<Rating> ratings;

    public ObjectId get_id() {
        return _id;
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

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }
}
