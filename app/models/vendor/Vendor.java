package models.vendor;


import org.bson.types.ObjectId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ahereza on 11/15/16.
 */

public class Vendor {
//    public static PlayJongo jongo = Play.current().injector().instanceOf(PlayJongo.class);
//
//    public static MongoCollection vendor() {
//        return jongo.getCollection("vendor");
//    }
    @MongoObjectId
    private ObjectId _id;
    private String companyName;
    private String website;
    private boolean verified;
    private Date createdAt;
    private boolean approved;
    private List<ObjectId> reviews;
    private List<ObjectId> ratings;
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
        this.ratings = new ArrayList<ObjectId>();
        this.reviews = new ArrayList<ObjectId>();
    }

    public Vendor(String companyName, String website, boolean verified) {
        this.createdAt = new Date();
        this.companyName = companyName;
        this.website = website;
        this.verified = verified;
        this.ratings = new ArrayList<ObjectId>();
        this.reviews = new ArrayList<ObjectId>();
    }

    public Vendor(String companyName) {
        this.createdAt = new Date();
        this.companyName = companyName;
        this.blocked = false;
        this.ratings = new ArrayList<ObjectId>();
        this.reviews = new ArrayList<ObjectId>();
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public ObjectId get_id() {
        return _id;
    }

    public Vendor(String companyName, String website) {
        this.createdAt = new Date();
        this.companyName = companyName;
        this.website = website;
        this.blocked = false;
        this.ratings = new ArrayList<ObjectId>();
        this.reviews = new ArrayList<ObjectId>();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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
}

