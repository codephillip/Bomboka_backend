package models.user;

import org.jongo.marshall.jackson.oid.MongoObjectId;
import org.jongo.marshall.jackson.oid.MongoId;

import java.util.Date;
import Utils.Password;

/**
 * Created by Ahereza on 11/25/16.
 */
public class User {
    @MongoId
    @MongoObjectId
    private String key;
    private String fullnames;
    private String username;
    private String image;
    private String email;
    private String password;
    private String address;
    private String sex;
    private double latitude;
    private double longitude;
    private String dob;
    private Date createdAt;
    private Date modificationTimeStamp;
    private Date tombStone;
    private int age;
    private String country;
    private String phoneNumber;
    private boolean blocked;
    private boolean verified;
    private String role;
    private boolean active;

    public User(String fullnames, String username, String email, String password, String address, double latitude,
                double longitude, String dob, int age, String country) throws Exception {
        this.fullnames = fullnames;
        this.username = username;
        this.email = email;
        this.password = Password.getSaltedHash(password);
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dob = dob;
        this.age = age;
        this.country = country;
        this.role = "normal";
        this.createdAt = new Date();
        this.active = true;
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = "normal";
        this.createdAt = new Date();
        this.active = true;
    }

    public User(){
        this.createdAt = new Date();
        this.active = true;
        this.role = "normal";
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFullnames() {
        return fullnames;
    }

    public void setFullnames(String fullnames) {
        this.fullnames = fullnames;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public Date getModificationTimeStamp() {
        return modificationTimeStamp;
    }

    public void setModificationTimeStamp(Date modificationTimeStamp) {
        this.modificationTimeStamp = modificationTimeStamp;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getTombStone() {
        return tombStone;
    }

    public void setTombStone(Date tombStone) {
        this.tombStone = tombStone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
