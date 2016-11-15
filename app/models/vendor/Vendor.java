package models.vendor;


import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

/**
 * Created by Ahereza on 11/15/16.
 */

public class Vendor {
    @MongoObjectId
    private Long _id;
    private String companyName;
    private String website;
    private boolean verified;

    public Vendor(String companyName, String website, boolean verified) {
        this.companyName = companyName;
        this.website = website;
        this.verified = verified;
    }

    public Vendor(String companyName) {
        this.companyName = companyName;
    }

    public Vendor(String companyName, String website) {
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
}

