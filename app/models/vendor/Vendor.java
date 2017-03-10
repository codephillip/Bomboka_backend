package models.vendor;


import models.courier.Courier;
import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ahereza on 11/15/16.
 */

public class Vendor {
    @MongoId
    @MongoObjectId
    private String key;
    private String name;
    private String image;
    private String website;
    private boolean verified;
    private Date createdAt;
    private boolean approved;
    private List<String> reviews;
    private List<String> ratings;
    private List<String> couriers;
    private boolean blocked;

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public Vendor()
    {
        this.createdAt = new Date();
        this.ratings = new ArrayList<String>();
        this.reviews = new ArrayList<String>();
        this.couriers = new ArrayList<String>();
    }

    public Vendor(String companyName, String website, boolean verified) {
        this.createdAt = new Date();
        this.name = companyName;
        this.website = website;
        this.verified = verified;
        this.ratings = new ArrayList<String>();
        this.reviews = new ArrayList<String>();
        this.couriers = new ArrayList<String>();
    }

    public Vendor(String companyName) {
        this.createdAt = new Date();
        this.name = companyName;
        this.blocked = false;
        this.ratings = new ArrayList<String>();
        this.reviews = new ArrayList<String>();
        this.couriers = new ArrayList<String>();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Vendor(String companyName, String website) {
        this.createdAt = new Date();
        this.name = companyName;
        this.website = website;
        this.blocked = false;
        this.ratings = new ArrayList<String>();
        this.reviews = new ArrayList<String>();
        this.couriers = new ArrayList<String>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
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

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<String> getCouriers() {
        return couriers;
    }

    public void setCouriers(List<String> couriers) {
        this.couriers = couriers;
    }

    public void addCourier(Courier courier){
        couriers.add(courier.getkey());
    }

    public void removeCourier(String courier){
        couriers.remove(courier);
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}

