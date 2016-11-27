package controllers;

import Utils.DatabaseUtils;
import Utils.Password;
import models.User.User;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.Map;

/**
 * Created by Ahereza on 11/25/16.
 */
public class UserController extends Controller {
    private DatabaseUtils userManager = new DatabaseUtils("users");
    private final FormFactory formFactory;

    @Inject
    public UserController(FormFactory formFactory) {
        this.formFactory = formFactory;

    }
    public Result signUp() throws Exception {
        Form<User> newUser = formFactory.form(User.class).bindFromRequest();
        User obj = newUser.get();
        obj.setPassword(Password.getSaltedHash(obj.getPassword()));
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
                return ok("Log in successful");
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
        Map<String, String> data = dataForm.data();

        return ok();
    }
}
