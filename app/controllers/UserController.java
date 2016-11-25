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
}
