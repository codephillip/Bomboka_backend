package models.vendor;

import org.bson.types.ObjectId;
import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

/**
 * Created by Ahereza on 11/19/16.
 */
public class Rating {
    // star rating system, maximum rating 5 stars, minimum 0 stars
    @MongoId
    @MongoObjectId
    private String key;
    private double stars;
    private ObjectId user;

    public Rating(double stars, ObjectId user) {
        this.stars = stars;
        this.user = user;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Rating(double stars) {
        this.stars = stars;
    }

    public Rating() {
    }

    public double getStars() {
        return stars;
    }

    public void setStars(double stars) {
        this.stars = stars;
    }

    public ObjectId getUser() {
        return user;
    }

    public void setUser(ObjectId user) {
        this.user = user;
    }

}
