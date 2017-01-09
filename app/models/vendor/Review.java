package models.vendor;

import org.bson.types.ObjectId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

/**
 * Created by Ahereza on 11/19/16.
 */
public class Review {
    @MongoObjectId
    private ObjectId _id;
    private String text;
    private ObjectId User; // should be an object of the user model TODO

    public Review(String text, ObjectId user) {
        this.text = text;
        User = user;
    }

    public Review(String text) {
        this.text = text;
    }

    public Review() {
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
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
