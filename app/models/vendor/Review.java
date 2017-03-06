package models.vendor;

import org.bson.types.ObjectId;
import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

/**
 * Created by Ahereza on 11/19/16.
 */
public class Review {
    @MongoId
    @MongoObjectId
    private String key;
    private String text;
    //todo place mapping of product,vendor, shop
    private ObjectId User; //todo should be an object of the user model

    public Review(String text, ObjectId user) {
        this.text = text;
        User = user;
    }

    public Review(String text) {
        this.text = text;
    }

    public Review() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ObjectId getUser() {
        return User;
    }

    public void setUser(ObjectId user) {
        User = user;
    }
}
