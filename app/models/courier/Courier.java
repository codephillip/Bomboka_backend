package models.courier;

import models.vendor.Rating;
import models.vendor.Review;
import org.bson.types.ObjectId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by codephillip on 30/11/16.
 */
public class Courier {
    @MongoObjectId
    private ObjectId _id;
    private String name;
    private String image;
    private String address;
    private String email;
    private String phoneNumber;
    //todo add delivery { }
    private double latitude;
    private double longitude;
    private Date createdAt;
    private Date modificationTimeStamp;
    private Date tombStone;
    private boolean deleted;
    private boolean approved;
    private boolean blocked;
    private List<ObjectId> reviews;
    private List<ObjectId> ratings;

    public Courier(ObjectId _id, String name, String address, String email, String phoneNumber, double latitude, double longitude, Date createdAt, Date modificationTimeStamp, Date tombStone, boolean deleted, boolean approved, boolean blocked, List<ObjectId> reviews, List<ObjectId> ratings) {
        this._id = _id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.createdAt = createdAt;
        this.modificationTimeStamp = modificationTimeStamp;
        this.tombStone = tombStone;
        this.deleted = deleted;
        this.approved = approved;
        this.blocked = blocked;
        this.ratings = new ArrayList<ObjectId>();
        this.reviews = new ArrayList<ObjectId>();
    }

    public Courier(ObjectId _id, String name, String address, String email, String phoneNumber, double latitude, double longitude, Date createdAt, Date modificationTimeStamp, Date tombStone, boolean deleted) {
        this._id = _id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.createdAt = createdAt;
        this.modificationTimeStamp = modificationTimeStamp;
        this.tombStone = tombStone;
        this.deleted = false;
        this.blocked = false;
        this.approved = true;
    }

    public Courier(ObjectId _id, String name, String address, String email, String phoneNumber, double latitude, double longitude, Date createdAt, Date modificationTimeStamp, Date tombStone) {
        this._id = _id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.createdAt = createdAt;
        this.modificationTimeStamp = modificationTimeStamp;
        this.tombStone = tombStone;
        this.deleted = false;
        this.blocked = false;
        this.approved = true;
    }

    public Courier(String name, String address, String email, String phoneNumber, double latitude, double longitude, Date createdAt, Date modificationTimeStamp, Date tombStone) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.createdAt = new Date();
        this.modificationTimeStamp = new Date();
        this.tombStone = tombStone;
        this.deleted = false;
        this.blocked = false;
        this.approved = true;
    }

    public Courier(String name, String address, String email, String phoneNumber, double latitude, double longitude, Date tombStone) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.tombStone = tombStone;
        this.createdAt = new Date();
        this.modificationTimeStamp = new Date();
        this.deleted = false;
        this.blocked = false;
        this.approved = true;
    }

    public Courier() {
        this.createdAt = new Date();
        this.modificationTimeStamp = new Date();
        this.deleted = false;
        this.blocked = false;
        this.approved = true;
        this.ratings = new ArrayList<ObjectId>();
        this.reviews = new ArrayList<ObjectId>();
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getModificationTimeStamp() {
        return modificationTimeStamp;
    }

    public void setModificationTimeStamp(Date modificationTimeStamp) {
        this.modificationTimeStamp = modificationTimeStamp;
    }

    public Date getTombStone() {
        return tombStone;
    }

    public void setTombStone(Date tombStone) {
        this.tombStone = tombStone;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
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
