package models.order;

import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.util.Date;

/**
 * Created by codephillip on 02/12/16.
 */
public class Order {
    @MongoId
    @MongoObjectId
    private String key;
    private String user;
    private String vendor;
    private String courier;
    private String product;
    private Date createdAt;
    private boolean valid;
    private boolean received;
    private Date deliveryTime; //todo calculate delivery time, basing on distance and courier

    public Order(String key, String user, String vendor, String courier, String product, Date createdAt, boolean valid, boolean received, Date deliveryTime) {
        this.key = key;
        this.user = user;
        this.vendor = vendor;
        this.courier = courier;
        this.product = product;
        this.createdAt = new Date();
        this.valid = true;
        this.received = false;
        this.deliveryTime = new Date();
    }

    public Order(String key, String user, String vendor, String courier, String product, Date createdAt, boolean valid, Date deliveryTime) {
        this.key = key;
        this.user = user;
        this.vendor = vendor;
        this.courier = courier;
        this.product = product;
        this.createdAt = new Date();
        this.valid = true;
        this.deliveryTime = new Date();
    }

    public Order(String key, String user, String vendor, String courier, Date createdAt, boolean valid, Date deliveryTime) {
        this.key = key;
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
        this.received = false;
        this.deliveryTime = new Date();
    }

    public String getkey() {
        return key;
    }

    public void setkey(String key) {
        this.key = key;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getCourier() {
        return courier;
    }

    public void setCourier(String courier) {
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

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public boolean isReceived() {
        return received;
    }

    public void setReceived(boolean received) {
        this.received = received;
    }
}
