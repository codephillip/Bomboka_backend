package models.vendor;


import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;
import play.api.Play;
import uk.co.panaxiom.playjongo.PlayJongo;

import java.util.Date;
import java.util.List;

/**
 * Created by Ahereza on 11/15/16.
 */

public class Vendor {
//    public static PlayJongo jongo = Play.current().injector().instanceOf(PlayJongo.class);
//
//    public static MongoCollection vendor() {
//        return jongo.getCollection("vendor");
//    }
    @MongoObjectId
    private ObjectId _id;
    private String companyName;
    private String website;
    private boolean verified;
    private Date createdAt;
    private List<VendorShops> vendorShopsList;


    public Vendor()
    {
        this.createdAt = new Date();
    }

    public Vendor(String companyName, String website, boolean verified) {
        this.createdAt = new Date();
        this.companyName = companyName;
        this.website = website;
        this.verified = verified;
    }

    public Vendor(String companyName) {
        this.createdAt = new Date();
        this.companyName = companyName;
    }

    public Vendor(String companyName, String website) {
        this.createdAt = new Date();
        this.companyName = companyName;
        this.website = website;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public List<VendorShops> getVendorShopsList() {
        return vendorShopsList;
    }

    public void setVendorShopsList(List<VendorShops> vendorShopsList) {
        this.vendorShopsList = vendorShopsList;
    }
}

