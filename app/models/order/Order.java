package models.order;

import models.courier.Courier;
import models.user.User;
import models.vendor.Product;
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
    private User buyer;
    private Courier courier;
    private Product product;
    private Date createdAt;
    private int verificationCode;
    private boolean valid;
    private boolean received;
    private Date deliveryTime; //todo calculate delivery time, basing on distance and courier

    public Order(String key, User buyer, Courier courier, Product product, Date createdAt, boolean valid, boolean received, Date deliveryTime) {
        this.key = key;
        this.buyer = buyer;
        this.courier = courier;
        this.product = product;
        this.createdAt = new Date();
        this.valid = true;
        this.received = false;
        this.deliveryTime = new Date();
    }

    public Order() {
        this.createdAt = new Date();
        this.valid = true;
        this.received = false;
        this.deliveryTime = new Date();
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public boolean isReceived() {
        return received;
    }

    public void setReceived(boolean received) {
        this.received = received;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(int verificationCode) {
        this.verificationCode = verificationCode;
    }
}
