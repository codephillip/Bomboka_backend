package controllers;

import Utils.DatabaseUtils;
import Utils.Password;
import Utils.Utility;
import models.user.User;
import models.vendor.Vendor;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import org.bson.types.ObjectId;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static Utils.Utility.isNotEmpty;

/**
 * Created by Ahereza on 11/25/16.
 */
public class UserController extends Controller {
    private DatabaseUtils userManager = new DatabaseUtils("users");
    private final FormFactory formFactory;
    private final Utility utility;

    @Inject
    public UserController(FormFactory formFactory, Utility utility) {
        this.formFactory = formFactory;
        this.utility = utility;
    }

    public Result signUp() throws Exception {
        Form<User> newUser = formFactory.form(User.class).bindFromRequest();
        User obj = newUser.get(); 
        obj.setPassword(Password.getSaltedHash(obj.getPassword()));
        //todo check first if the user is already registered before saving to prevent having duplicate accounts
        userManager.registerUser(obj); 
        return ok(Json.toJson(obj));
    }

    public Result signIn() throws Exception{
        Form<User> dataForm = formFactory.form(User.class).bindFromRequest();
        Map<String, String> data = dataForm.data();
        String username = data.get("username");
        String password = data.get("password");
        User user = userManager.getUserByUserName(username);
        if (user != null){
            if(user.getUsername().equals(username) && Password.check(password, user.getPassword())){
                return ok(Json.toJson(user));
            } else {
                return ok("username or password is wrong");
            }

        } else {
            return badRequest(Json.toJson("Account not found, Create a new account Check that the username is typed correctly"));
        }
    }

    public Result blockUser(String userID){
        userManager.blockUser(userID, true);
        User user = userManager.getUserByID(userID);
        return ok(Json.toJson(user));
    }
    public Result unBlockUser(String userID){
        userManager.blockUser(userID, false);
        User user = userManager.getUserByID(userID);
        return ok(Json.toJson(user));
    }

    public Result deleteUser(String userID){
        userManager.deleteUser(userID);
        User user = userManager.getUserByID(userID);
        return ok(Json.toJson(user));
    }

    public Result recoverAccount(String uname){
        User user;
        if (userManager.getUserByUserName(uname) != null){
            user = userManager.getUserByUserName(uname);
            return ok(Json.toJson("Recovery Email sent to " + user.getEmail()));
        } else if (userManager.getUserByEmail(uname) != null){
            user = userManager.getUserByEmail(uname);
            return ok(Json.toJson("Recovery Email sent to " + user.getEmail()));
        } else{
            return ok(Json.toJson("There is no existing user for this account"));
        }
    }

    public Result setRole(String userID){
        Form<User> dataForm = formFactory.form(User.class).bindFromRequest();
        Map<String, String> data = dataForm.data();
        User user = userManager.getUserByID(userID);
        user.setRole(data.get("role"));
        userManager.updateUser(user);
        return ok(Json.toJson(user));
    }

    public Result getRole(String userID){
        User user = userManager.getUserByID(userID);
        return ok(user.getRole());
    }

    public Result editUser(String userID){
        Form<User> dataForm = formFactory.form(User.class).bindFromRequest();
        User dbUser = userManager.getUserByID(userID);
        Map<String, String> data = dataForm.data();
        for(String key: data.keySet()){
            if (key.equals("fullnames") && isNotEmpty(data.get(key))){
                dbUser.setFullnames(data.get(key));
            } else if (key.equals("username") && isNotEmpty(data.get(key))){
                dbUser.setUsername(data.get(key));
            } else if (key.equals("dob") && isNotEmpty(data.get(key))){
                dbUser.setDob(data.get(key));
            } else if (key.equals("email") && isNotEmpty(data.get(key))){
                dbUser.setEmail(data.get(key));
            } else if (key.equals("address") && isNotEmpty(data.get(key))){
                dbUser.setAddress(data.get(key));
            } else if (key.equals("country") && isNotEmpty(data.get(key))){
                dbUser.setCountry(data.get(key));
            } else if (key.equals("age") && isNotEmpty(data.get(key))){
                try{
                    dbUser.setAge(Integer.parseInt(data.get(key)));
                }
                catch (NumberFormatException e){
                    continue;
                }
            } else if (key.equals("phoneNumber") && isNotEmpty(data.get(key))){
                dbUser.setPhoneNumber(data.get(key));
            }
        }
        userManager.updateUser(dbUser);
        return ok(Json.toJson(userManager.getUserByID(userID)));
        }


    public Result viewUsers() {
        Logger.debug("viewUsers#");
        List<User> allUsers = userManager.allUsers();
        //todo remove on release
        for (User user : allUsers) {
            Logger.debug("viewUsers# " + user.getKey());
        }
        return ok(Json.toJson(allUsers));
    }

    public Result getUserProfile(String userID) {
        User user = userManager.getUserByID(userID);
        return ok(Json.toJson(user));
    }

    public Result addOrReplaceUserImage(String userID) {
        User user = userManager.getUserByID(userID);
        String path = System.getProperty("user.dir") + "/uploads/" + Utility.PROFILE_IMAGE + "/" + userID;
        Logger.debug("File upload#" + path);
        utility.deleteOldImage(path);
        ArrayList<String> imageNames =  utility.uploadImage(path, 1);
        user.setImage(imageNames.get(0));
        userManager.updateUser(user);
        return ok(Json.toJson(user));
    }
}
