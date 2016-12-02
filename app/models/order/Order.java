package models.order;

import org.bson.types.ObjectId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.util.Date;

/**
 * Created by codephillip on 02/12/16.
 */
public class Order {
    @MongoObjectId
    private ObjectId _id;
    private ObjectId user;
    private ObjectId vendor;
    private ObjectId courier;
    private Date createdAt;
    private boolean valid;
    private Date deliveryTime;

    public Order(ObjectId _id, ObjectId user, ObjectId vendor, ObjectId courier, Date createdAt, boolean valid, Date deliveryTime) {
        this._id = _id;
        this.user = user;
        this.vendor = vendor;
        this.courier = courier;
        this.createdAt = new Date();
        this.valid = true;
        this.deliveryTime = new Date();
    }

    public Order() {
        this.createdAt = new Date();
        this.valid = true;
        this.deliveryTime = new Date();
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public ObjectId getUser() {
        return user;
    }

    public void setUser(ObjectId user) {
        this.user = user;
    }

    public ObjectId getVendor() {
        return vendor;
    }

    public void setVendor(ObjectId vendor) {
        this.vendor = vendor;
    }

    public ObjectId getCourier() {
        return courier;
    }

    public void setCourier(ObjectId courier) {
        this.courier = courier;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
}
