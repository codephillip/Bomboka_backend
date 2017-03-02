package models.courier;

import models.vendor.Rating;
import models.vendor.Review;
import org.bson.types.ObjectId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.util.List;

/**
 * Created by codephillip on 30/11/16.
 */
public class CourierProfile {
    @MongoObjectId
    private ObjectId _id;
    private String name;
    private String address;
    private String image;
    private String email;
    private String phoneNumber;
    private boolean approved;
    private List<ObjectId> reviews;
    private List<ObjectId> ratings;

    public CourierProfile(ObjectId _id, String name, String address, String image,String email, String phoneNumber, boolean approved, List<ObjectId> reviews, List<ObjectId> ratings) {
        this._id = _id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.image = image;
        this.phoneNumber = phoneNumber;
        this.approved = approved;
        this.reviews = reviews;
        this.ratings = ratings;
    }

    public CourierProfile() {
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
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
        this.reviews.add(review.get_id());
    }

    public void addRating(Rating rating){
        this.ratings.add(rating.get_id());
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
