package models.vendor;

import org.bson.types.ObjectId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

/**
 * Created by Ahereza on 11/19/16.
 */
public class Rating {
    // star rating system, maximum rating 5 stars, minimum 0 stars
    @MongoObjectId
    private ObjectId _id;
    private double stars;
    private ObjectId user;

    public Rating(double stars, ObjectId user) {
        this.stars = stars;
        this.user = user;
    }

    public ObjectId get_id() {
        return _id;
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
