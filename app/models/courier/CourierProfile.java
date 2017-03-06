package models.courier;

import models.vendor.Rating;
import models.vendor.Review;
import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.util.List;

/**
 * Created by codephillip on 30/11/16.
 */
public class CourierProfile {
    @MongoId
    @MongoObjectId
    private String key;
    private String name;
    private String address;
    private String image;
    private String email;
    private String phoneNumber;
    private boolean approved;
    private List<String> reviews;
    private List<String> ratings;

    public CourierProfile(String key, String name, String address, String image,String email, String phoneNumber, boolean approved, List<String> reviews, List<String> ratings) {
        this.key = key;
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
        this.reviews.add(review.getKey());
    }

    public void addRating(Rating rating){
        this.ratings.add(rating.getKey());
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
