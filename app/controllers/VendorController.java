package controllers;

import Utils.DatabaseUtils;
import models.vendor.*;
import org.bson.types.ObjectId;
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

    private DatabaseUtils vendorManager = new DatabaseUtils("vendors");
    private DatabaseUtils shopManager = new DatabaseUtils("shops");


    private final FormFactory formFactory;

    @Inject
    public VendorController(FormFactory formFactory) {
        this.formFactory = formFactory;

    }

    public Result addVendor(){
        // tested and passed
        Form<Vendor> vendor = formFactory.form(Vendor.class).bindFromRequest();
        Vendor obj = vendor.get();
        vendorManager.saveVendor(obj);
        return ok(Json.toJson(obj)) ;
    }

    public Result viewVendorDetails(String vendorID){
        // tested passed
        Vendor result = vendorManager.getVendorByID(vendorID);
        return ok(Json.toJson(result));
    }

    public Result viewVendorShops(String vendorID){
        //tested passed
        return ok(Json.toJson(shopManager.findVendorShops(vendorID)));
    }

    public Result addVendorShop(String vendorID){
        // tested passed
        Form<Shop> shop = formFactory.form(Shop.class).bindFromRequest();
        Shop obj = shop.get();
        obj.setVendor(new ObjectId(vendorID));
        shopManager.saveShop(obj);
        return ok();
    }

    public Result viewAllVendors(){
        // tested and passed
        List<Vendor> allVendors = vendorManager.allVendors();
        return ok(Json.toJson(allVendors));
    }

    public Result viewShopDetails(String shopID){
        // tested passed
        Shop shop = shopManager.getVendorShopDetails(shopID);
        return ok(Json.toJson(shop));
    }

    public Result editShopDetails(String shopID){
        Form<Shop> shop = formFactory.form(Shop.class).bindFromRequest();
        Shop obj = shop.get();
        Shop original = shopManager.getVendorShopDetails(shopID);

        return ok(shopID);
    }

    public Result editVendorDetails(String vendorID){
        return ok(vendorID);
    }

    public Result deleteVendor(String vendorID){
        // testing passed
        vendorManager.deleteVendor(vendorID);
        return ok();
    }

    public Result deleteShop(String shopID){
        // tested passed
        shopManager.deleteShop(shopID);
        return ok();
    }

    public Result approveVendor(String vendorID){
        // tested passed
        Vendor vendor = vendorManager.getVendorByID(vendorID);
        vendor.setApproved(true);
        vendorManager.saveVendor(vendor);
        return ok(Json.toJson(vendor));
    }

    public Result disapproveVendor(String vendorID){
        // tested and passed
        Vendor vendor = vendorManager.getVendorByID(vendorID);
        vendor.setApproved(false);
        vendorManager.saveVendor(vendor);
        return ok(Json.toJson(vendor));
    }

    public Result writeVendorReview(String reviewText, String vendorID){
        Vendor vendor = vendorManager.getVendorByID(vendorID);
        Review review = new Review(reviewText);
        vendor.addReview(review);
        // TODO: 11/19/16  update the item
        vendorManager.saveVendor(vendor);
        return ok(Json.toJson(vendor));
    }

    public Result addVendorRating(double rating, String vendorID){
        if (rating > 5){
            rating = 5;
        } else if (rating < 0){
            rating = 0;
        }
        Vendor vendor = vendorManager.getVendorByID(vendorID);
        Rating newRating = new Rating(rating);
        vendor.addRating(newRating);
        // TODO: 11/19/16  update the item
        vendorManager.saveVendor(vendor);
        return ok(Json.toJson(vendor));
    }

    public Result writeShopReview(String reviewText, String shopID){
        Shop shop = shopManager.getVendorShopDetails(shopID);
        Review review = new Review(reviewText);
        shop.addReview(review);
        // TODO: 11/19/16  update the item
        shopManager.saveShop(shop);
        return ok(Json.toJson(shop));
    }

    public Result addShopRating(double rating, String shopID){
        if (rating > 5){
            rating = 5;
        } else if (rating < 0){
            rating = 0;
        }
        Shop shop = shopManager.getVendorShopDetails(shopID);
        Rating newRating = new Rating(rating);
        shop.addRating(newRating);
        // TODO: 11/19/16  update the item
        shopManager.saveShop(shop);
        return ok(Json.toJson(shop));
    }

    public Result addProductToShop(String shopID){
        Shop shop = shopManager.getVendorShopDetails(shopID);
        // TODO: 11/19/16 get the product from a form and bind to a form model then save
        return ok();
    }


}
