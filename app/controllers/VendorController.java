package controllers;

import Utils.DatabaseUtils;
import models.User.User;
import models.vendor.*;
import org.bson.types.ObjectId;
import org.jongo.MongoCursor;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
//import views.html.vendor.viewAllVendors;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Ahereza on 11/15/16.
 */
public class VendorController extends Controller{

    private DatabaseUtils vendorManager = new DatabaseUtils("vendors");
    private DatabaseUtils shopManager = new DatabaseUtils("shops");
    private DatabaseUtils productManager = new DatabaseUtils("products");
    private DatabaseUtils ratingManager = new DatabaseUtils("ratings");
    private DatabaseUtils reviewManager = new DatabaseUtils("reviews");
    private DatabaseUtils couponManager = new DatabaseUtils("coupons");

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
        vendorManager.updateVendor(vendor);
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
        // tested and working
        Form<Product> newProduct = formFactory.form(Product.class).bindFromRequest();
        Product obj = newProduct.get();
        productManager.saveProduct(obj);
        Shop shop = shopManager.getVendorShopDetails(shopID);
        shop.addToProductsList(obj);
        shopManager.updateShop(shop);
        return ok(Json.toJson(shop));
    }

    public Result deleteProductFromShop(String productID){
        productManager.deleteProduct(productID);
        return ok();
    }

    public Result editProduct(String productID){
        return ok();
    }

    public Result blockVendor(String vendorID){
        Vendor vendor = vendorManager.getVendorByID(vendorID);
        vendor.setBlocked(true);
        vendorManager.updateVendor(vendor);
        return ok(Json.toJson(vendor));
    }

    public Result unBlockVendor(String vendorID){
        Vendor vendor = vendorManager.getVendorByID(vendorID);
        vendor.setBlocked(false);
        vendorManager.updateVendor(vendor);
        return ok(Json.toJson(vendor));
    }

    public Result addRatingToVendor(String vendorID){
        Form<Rating> newRating = formFactory.form(Rating.class).bindFromRequest();
        Rating obj = newRating.get();
        ratingManager.saveRating(obj);
        Vendor vendor = vendorManager.getVendorByID(vendorID);
        vendor.addRating(obj);
        vendorManager.updateVendor(vendor);
        return ok(Json.toJson(vendor));
    }

    public Result addRatingToShop(String shopID){
        Form<Rating> newRating = formFactory.form(Rating.class).bindFromRequest();
        Rating obj = newRating.get();
        ratingManager.saveRating(obj);
        Shop shop = shopManager.getVendorShopDetails(shopID);
        shop.addRating(obj);
        vendorManager.updateShop(shop);
        return ok(Json.toJson(shop));
    }

    public Result addRatingToProduct(String productID){
        Form<Rating> newRating = formFactory.form(Rating.class).bindFromRequest();
        Rating obj = newRating.get();
        ratingManager.saveRating(obj);
        Product product = productManager.getProductByID(productID);
        product.addRating(obj);
        productManager.updateProduct(product);
        return ok(Json.toJson(product));
    }

    public Result addReviewToVendor(String vendorID){
        Form<Review> newReview = formFactory.form(Review.class).bindFromRequest();
        Review obj = newReview.get();
        reviewManager.saveReview(obj);
        Vendor vendor = vendorManager.getVendorByID(vendorID);
        vendor.addReview(obj);
        vendorManager.updateVendor(vendor);
        return ok(Json.toJson(vendor));
    }

    public Result addReviewToShop(String shopID){
        Form<Review> newReview = formFactory.form(Review.class).bindFromRequest();
        Review obj = newReview.get();
        reviewManager.saveReview(obj);
        Shop shop = shopManager.getVendorShopDetails(shopID);
        shop.addReview(obj);
        shopManager.updateShop(shop);
        return ok(Json.toJson(shop));
    }

    public Result addReviewToProduct(String productID){
        Form<Review> newReview = formFactory.form(Review.class).bindFromRequest();
        Review obj = newReview.get();
        reviewManager.saveReview(obj);
        Product product = productManager.getProductByID(productID);
        product.addReview(obj);
        productManager.updateProduct(product);
        return ok(Json.toJson(product));
    }

    public Result viewShopProducts(String shopID){
        Shop shop = shopManager.getVendorShopDetails(shopID);
        List<Product> productList = productManager.shopProducts(shop);
        return ok(Json.toJson(productList));
    }
    public Result editVendor(String vendorID){
        Form<Vendor> dataForm = formFactory.form(Vendor.class).bindFromRequest();
        Vendor vendor = vendorManager.getVendorByID(vendorID);

        Map<String, String> data = dataForm.data();
        if(data.get("companyName") != null){
            vendor.setCompanyName(data.get("companyName"));
        }
        if (data.get("website") != null){
            vendor.setWebsite(data.get("website"));
        }
        vendorManager.updateVendor(vendor);

        return ok(Json.toJson(vendor));
    }

    public Result editShopDetails(String shopID){
        Form<Shop> dataForm = formFactory.form(Shop.class).bindFromRequest();
        Shop obj = shopManager.getVendorShopDetails(shopID);
        Map<String, String> data = dataForm.data();
        double latitude = Double.parseDouble(data.get("latitude"));
        String address = data.get("address");
        if (address != null){
            obj.setAddress(address);
        }
        if (data.get("longitude") != null){
            obj.setLongitude(Double.parseDouble(data.get("longitude")));
        }
        if (data.get("latitude") != null){
            obj.setLatitude(Double.parseDouble(data.get("latitude")));
        }
        return ok(Json.toJson(data));
    }

    public Result addCoupon(){
        Form<Coupon> newCoupon = formFactory.form(Coupon.class).bindFromRequest();
        Coupon obj = newCoupon.get();
        couponManager.saveCoupon(obj);
        return ok();
    }
    public Result showCoupons(){
        List<Coupon> coupons = couponManager.showCoupons();
        return ok(Json.toJson(coupons));
    }
    public Result deleteCoupon(String couponID){
        couponManager.deleteCoupon(couponID);
        return ok();
    }

    public Result validateCoupon(String couponID){
        Coupon coupon = couponManager.getCouponByID(couponID);
        coupon.setValid();
        couponManager.updateCoupon(coupon);
        return ok(Json.toJson(coupon));
    }
    public Result invalidateCoupon(String couponID){
        Coupon coupon = couponManager.getCouponByID(couponID);
        coupon.setInvalid();
        couponManager.updateCoupon(coupon);
        return ok(Json.toJson(coupon));
    }

}
