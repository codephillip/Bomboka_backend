package models.vendor;

import org.bson.types.ObjectId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * - category name
 - {code,parent_code}
 - timestamp
 - modificationtimestamp
 - tombstone
 */
public class ProductCategory {

    @MongoObjectId
    private ObjectId _id;
    private String name;
    private Date timestamp;
    private Date modificationTimestamp;
    private boolean tombstone;

    public ProductCategory(ObjectId _id, String name, Date timestamp, Date modificationTimestamp, boolean tombstone) {
        this._id = _id;
        this.name = name;
        this.timestamp = new Date();
        this.modificationTimestamp = new Date();
        this.tombstone = true;
    }

    public ProductCategory(ObjectId _id, String name, Date timestamp, boolean tombstone) {
        this._id = _id;
        this.name = name;
        this.timestamp = new Date();
        this.tombstone = true;
    }

    public ProductCategory(String name) {
        this.name = name;
        this.timestamp = new Date();
        this.tombstone = true;
    }

    public ProductCategory() {
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Date getModificationTimestamp() {
        return modificationTimestamp;
    }

    public void setModificationTimestamp(Date modificationTimestamp) {
        this.modificationTimestamp = modificationTimestamp;
    }

    public boolean isTombstone() {
        return tombstone;
    }

    public void setTombstone(boolean tombstone) {
        this.tombstone = tombstone;
    }
}
