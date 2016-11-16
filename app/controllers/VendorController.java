package controllers;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;
import models.vendor.Vendor;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
//import views.html.vendor.viewAllVendors;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ahereza on 11/15/16.
 */
public class VendorController extends Controller{


    private final FormFactory formFactory;

    @Inject
    public VendorController(FormFactory formFactory) {
        this.formFactory = formFactory;

    }

    public Result addVendor(){
        Form<Vendor> vendor = formFactory.form(Vendor.class).bindFromRequest();
        Vendor obj = vendor.get();
        DatabaseM manager = new DatabaseM("vendor");
        manager.saveObject(obj);
        return ok(Json.toJson(obj)) ;
    }

    public Result viewVendorDetails(String vendorID){
        return TODO;
    }

    public Result viewVendorShops(String vendorID){
        return ok(vendorID);
    }

    public Result addVendorShop(){

        return ok(Json.toJson(new String("test")));
    }

    public Result viewAllVendors(){
        return ok("Hello");
    }

    public Result viewShopDetails(String shopID){
        return ok("shop details");
    }

    public Result editShopDetails(String shopID){
        return ok("edit shop details");
    }

    public Result editVendorDetails(String shopID){
        return ok("edit vendor details");
    }
}
