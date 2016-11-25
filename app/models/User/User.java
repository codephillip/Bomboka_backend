package models.User;

import org.bson.types.ObjectId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.util.Date;
import Utils.Password;

/**
 * Created by Ahereza on 11/25/16.
 */
public class User {
    @MongoObjectId
    private ObjectId _id;
    private String fullnames;
    private String username;
    private String email;
    private String password;
    private String address;
    private double latitude;
    private double longitude;
    private Date dob;
    private Date createdAt;
    private Date modificationTimeStamp;
    private Date tombStone;
    private int age;
    private String country;
    private String phoneNumber;
    private boolean blocked;
    private boolean verified;
    private Role role;

    public User(String fullnames, String username, String email, String password, String address, double latitude,
                double longitude, Date dob, int age, String country) throws Exception {
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
    }

    public User(String fullnames, String username, String email, String password, String address, Date dob, int age, String country) throws Exception {
        this.fullnames = fullnames;
        this.username = username;
        this.email = email;
        this.password = Password.getSaltedHash(password);
        this.address = address;
        this.dob = dob;
        this.age = age;
        this.country = country;
    }

    public User(String fullnames, String username, String email, String password, Date dob) throws Exception {
        this.fullnames = fullnames;
        this.username = username;
        this.email = email;
        this.password = Password.getSaltedHash(password);;
        this.dob = dob;
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(){
        this.createdAt = new Date();
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
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

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
