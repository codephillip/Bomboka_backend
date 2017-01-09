package models.vendor;

import java.util.Date;

/**
 * Created by Ahereza on 11/22/16.
 */
public class Coupon {
    private Date timestamp;
    private Date expiryDate;
    private boolean isValid;
    private double amount;

    public Coupon(Date expiryDate, boolean isValid) {
        this.expiryDate = expiryDate;
        this.isValid = isValid;
        this.timestamp = new Date();
    }

    public Coupon(boolean isValid, double amount) {
        this.isValid = isValid;
        this.amount = amount;
        this.timestamp = new Date();
    }

    public Coupon(Date expiryDate, boolean isValid, double amount) {
        this.expiryDate = expiryDate;
        this.isValid = isValid;
        this.amount = amount;
        this.timestamp = new Date();
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Coupon() {
        this.timestamp = new Date();
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid() {
        isValid = true;
    }

    public void setInvalid() {
        isValid = false;
    }

}
