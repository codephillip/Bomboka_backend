package models.vendor;

import org.bson.types.ObjectId;
import org.jongo.marshall.jackson.oid.MongoObjectId;
import play.data.format.Formats;

import java.util.Date;

/**
 * Created by Ahereza on 11/15/16.
 */
public class VendorShops {
    @MongoObjectId
    private ObjectId _id;
    private double longitude;
    private double latitude;
    //products category
    private Date timestamp;
    private Date modificationTimeStamp;
    private Date tombstone;
    private String address;

    public VendorShops(String address) {
        this.timestamp = new Date();
        this.address = address;
    }

    public VendorShops(double longitude, double latitude, String address) {
        this.timestamp = new Date();
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
    }

    public VendorShops() {
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Date getModificationTimeStamp() {
        return modificationTimeStamp;
    }

    public void setModificationTimeStamp(Date modificationTimeStamp) {
        this.modificationTimeStamp = modificationTimeStamp;
    }

    public Date getTombstone() {
        return tombstone;
    }

    public void setTombstone(Date tombstone) {
        this.tombstone = tombstone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public ObjectId get_id() {
        return _id;
    }
}
