package controllers;

import Utils.DatabaseUtils;
import models.courier.Courier;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Codephillip on 11/30/16.
 */
public class CourierController extends Controller{

    private DatabaseUtils courierManager = new DatabaseUtils("courier");

    private final FormFactory formFactory;

    @Inject
    public CourierController(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    public Result viewCouriers() {
        Logger.debug("viewCouriers#");
        List<Courier> allCouriers = courierManager.allCouriers();
        return ok(Json.toJson(allCouriers));
    }

    public Result addCourier() {
        Form<Courier> courier = formFactory.form(Courier.class).bindFromRequest();
        Courier obj = courier.get();
        courierManager.saveCourier(obj);
        return ok(Json.toJson(obj)) ;
    }

//    public Result viewProductCategorys() {
//        return ok(Json.toJson(productCategoryManager.allProductCategorys()));
//    }
//
//    public Result addProductCategory() {
//        Form<ProductCategory> productCategory = formFactory.form(ProductCategory.class).bindFromRequest();
//        ProductCategory obj = productCategory.get(); // returns ProductCategory object from clean form
//
//        Map<String, String> hold = productCategory.data();
//        Logger.debug("insertProductCategory add#" + hold);
//        Logger.debug("insertProductCategory add2#" + hold.get("name"));
//
//        ArrayList<String> code = new ArrayList<>();
//
//        try {
//            ProductCategory parentProductCategory = productCategoryManager.getProductCategoryByName(hold.get("parent"));
//            code = parentProductCategory.getAncestorCode(); // retrieves the arraylist(code) of the parent
//            code.add(hold.get("name")); // appends name of the child to the end of the arraylist
//        } catch (Exception e){
//            e.printStackTrace();
//            code.add(hold.get("name"));
//        }
//
//        ProductCategory childProductCategory = new ProductCategory(hold.get("name"), code, hold.get("parent"));
//        Logger.debug("insertProductCategory " + childProductCategory);
//        productCategoryManager.saveProductCategory(childProductCategory);
//        return ok(Json.toJson(obj));
//    }
//
//    public Result addVendor(){
//        // tested and passed
//        Form<Vendor> vendor = formFactory.form(Vendor.class).bindFromRequest();
//        Vendor obj = vendor.get();
//        vendorManager.saveVendor(obj);
//        return ok(Json.toJson(obj)) ;
//    }
//
//    public Result viewVendorDetails(String vendorID){
//        // tested passed
//        Vendor result = vendorManager.getVendorByID(vendorID);
//        return ok(Json.toJson(result));
//    }
//
//    public Result viewVendorShops(String vendorID){
//        //tested passed
//        return ok(Json.toJson(shopManager.findVendorShops(vendorID)));
//    }
//
//    public Result addVendorShop(String vendorID){
//        // tested passed
//        Form<Shop> shop = formFactory.form(Shop.class).bindFromRequest();
//        Shop obj = shop.get();
//        obj.setVendor(new ObjectId(vendorID));
//        shopManager.saveShop(obj);
//        return ok();
//    }
//
//    public Result viewAllVendors(){
//        // tested and passed
//        List<Vendor> allVendors = vendorManager.allVendors();
//        // debug purposes
//        for (Vendor vendor : allVendors) {
//            Logger.debug("viewVendors#" + vendor.get_id().toString());
//        }
//        return ok(Json.toJson(allVendors));
//    }
//
//    public Result viewShopDetails(String shopID){
//        // tested passed
//        Shop shop = shopManager.getVendorShopDetails(shopID);
//        return ok(Json.toJson(shop));
//    }
//
//    public Result editVendorDetails(String vendorID){
//        Logger.debug("editVendor# "+ vendorID);
//        Form<Vendor> vendorForm = formFactory.form(Vendor.class).bindFromRequest();
//        Vendor dbVendor = vendorManager.getVendorByID(vendorID);
//        Logger.debug("editVendor# " + dbVendor.getCompanyName() + dbVendor.isApproved());
//
//        Map<String, String> data = vendorForm.data();
//        if (!Objects.equals(dbVendor.getCompanyName(), data.get("companyName")) && data.get("companyName") != null) {
//            dbVendor.setCompanyName(data.get("companyName"));
//        }
//        if (!Objects.equals(dbVendor.getWebsite(), data.get("website")) && data.get("website") != null){
//            dbVendor.setWebsite(data.get("website"));
//        }
//        if (!Objects.equals(String.valueOf(dbVendor.isVerified()), data.get("verified")) && data.get("verified") != null){
//            Logger.debug("editvendor " + data.get("verified"));
//            dbVendor.setVerified(!dbVendor.isVerified());
//        }
//        if (!Objects.equals(String.valueOf(dbVendor.isBlocked()), data.get("blocked")) && data.get("blocked") != null){
//            Logger.debug("editvendor " + data.get("blocked"));
//            dbVendor.setBlocked(!dbVendor.isBlocked());
//        }
//        if (!Objects.equals(String.valueOf(dbVendor.isApproved()), data.get("approved")) && data.get("approved") != null){
//            Logger.debug("editvendor " + data.get("approved"));
//            dbVendor.setApproved(!dbVendor.isApproved());
//        }
//
//        vendorManager.updateVendor(dbVendor);
//        return ok(Json.toJson(dbVendor));
//    }
//
//    public Result deleteVendor(String vendorID){
//        // testing passed
//        vendorManager.deleteVendor(vendorID);
//        return ok();
//    }
//
//    public Result deleteShop(String shopID){
//        // tested passed
//        shopManager.deleteShop(shopID);
//        return ok();
//    }
//
//    public Result approveVendor(String vendorID){
//        // tested passed
//        Vendor vendor = vendorManager.getVendorByID(vendorID);
//        vendor.setApproved(true);
//        vendorManager.saveVendor(vendor);
//        return ok(Json.toJson(vendor));
//    }
//
//    public Result disapproveVendor(String vendorID){
//        // tested and passed
//        Vendor vendor = vendorManager.getVendorByID(vendorID);
//        vendor.setApproved(false);
//        vendorManager.saveVendor(vendor);
//        return ok(Json.toJson(vendor));
//    }
//
//    public Result writeVendorReview(String reviewText, String vendorID){
//        Vendor vendor = vendorManager.getVendorByID(vendorID);
//        Review review = new Review(reviewText);
//        vendor.addReview(review);
//        // TODO: 11/19/16  update the item
//        vendorManager.updateVendor(vendor);
//        return ok(Json.toJson(vendor));
//    }
//
//    public Result addVendorRating(double rating, String vendorID){
//        if (rating > 5){
//            rating = 5;
//        } else if (rating < 0){
//            rating = 0;
//        }
//        Vendor vendor = vendorManager.getVendorByID(vendorID);
//        Rating newRating = new Rating(rating);
//        vendor.addRating(newRating);
//        // TODO: 11/19/16  update the item
//        vendorManager.saveVendor(vendor);
//        return ok(Json.toJson(vendor));
//    }
//
//    public Result writeShopReview(String reviewText, String shopID){
//        Shop shop = shopManager.getVendorShopDetails(shopID);
//        Review review = new Review(reviewText);
//        shop.addReview(review);
//        // TODO: 11/19/16  update the item
//        shopManager.saveShop(shop);
//        return ok(Json.toJson(shop));
//    }
//
//    public Result addShopRating(double rating, String shopID){
//        if (rating > 5){
//            rating = 5;
//        } else if (rating < 0){
//            rating = 0;
//        }
//        Shop shop = shopManager.getVendorShopDetails(shopID);
//        Rating newRating = new Rating(rating);
//        shop.addRating(newRating);
//        // TODO: 11/19/16  update the item
//        shopManager.saveShop(shop);
//        return ok(Json.toJson(shop));
//    }
//
//    public Result addProductToShop(String shopID){
//        // tested and working
//        Form<Product> newProduct = formFactory.form(Product.class).bindFromRequest();
//        Product obj = newProduct.get();
//        courierManager.saveProduct(obj);
//        Shop shop = shopManager.getVendorShopDetails(shopID);
//        shop.addToProductsList(obj);
//        shopManager.updateShop(shop);
//        return ok(Json.toJson(shop));
//    }
//
//    public Result deleteProductFromShop(String productID){
//        courierManager.deleteProduct(productID);
//        return ok();
//    }
//
//    public Result editProduct(String productID){
//        Logger.debug("editProduct# "+ productID);
//        Form<Product> productForm = formFactory.form(Product.class).bindFromRequest();
//        Product obj = productForm.get();
//        Product dbProduct = courierManager.getProductByID(productID);
//
//        Logger.debug("editProduct# " + dbProduct.getName() + dbProduct.getPrice());
//
//        Map<String, String> data = productForm.data();
//
//        if (dbProduct.getPrice() != obj.getPrice() && obj.getPrice() != 0) {
//            dbProduct.setPrice(obj.getPrice());
//        }
//        if (!Objects.equals(dbProduct.getName(), obj.getName()) && obj.getName() != null){
//            dbProduct.setName(obj.getName());
//        }
//        if (!Objects.equals(dbProduct.getDescription(), obj.getDescription()) && obj.getName() != null){
//            dbProduct.setDescription(obj.getDescription());
//        }
//        if (dbProduct.getProductCategory() != obj.getProductCategory()){
//            dbProduct.setProductCategory(obj.getProductCategory());
//        }
//        if (!Objects.equals(dbProduct.getManufacturer(), obj.getManufacturer())){
//            dbProduct.setManufacturer(obj.getManufacturer());
//        }
//        if (!Objects.equals(String.valueOf(dbProduct.isFake()), data.get("fake")) && data.get("fake") != null){
//            Logger.debug("editproduct " + data.get("fake"));
//            dbProduct.setFake(!dbProduct.isFake());
//        }
//
//        courierManager.updateProduct(dbProduct);
//        return ok(Json.toJson(obj));
//    }
//
//    public Result blockVendor(String vendorID){
//        Vendor vendor = vendorManager.getVendorByID(vendorID);
//        vendor.setBlocked(true);
//        vendorManager.updateVendor(vendor);
//        return ok(Json.toJson(vendor));
//    }
//
//    public Result unBlockVendor(String vendorID){
//        Vendor vendor = vendorManager.getVendorByID(vendorID);
//        vendor.setBlocked(false);
//        vendorManager.updateVendor(vendor);
//        return ok(Json.toJson(vendor));
//    }
//
//    public Result addRatingToVendor(String vendorID){
//        Form<Rating> newRating = formFactory.form(Rating.class).bindFromRequest();
//        Rating obj = newRating.get();
//        ratingManager.saveRating(obj);
//        Vendor vendor = vendorManager.getVendorByID(vendorID);
//        vendor.addRating(obj);
//        vendorManager.updateVendor(vendor);
//        return ok(Json.toJson(vendor));
//    }
//
//    public Result addRatingToShop(String shopID){
//        Form<Rating> newRating = formFactory.form(Rating.class).bindFromRequest();
//        Rating obj = newRating.get();
//        ratingManager.saveRating(obj);
//        Shop shop = shopManager.getVendorShopDetails(shopID);
//        shop.addRating(obj);
//        vendorManager.updateShop(shop);
//        return ok(Json.toJson(shop));
//    }
//
//    public Result addRatingToProduct(String productID){
//        Form<Rating> newRating = formFactory.form(Rating.class).bindFromRequest();
//        Rating obj = newRating.get();
//        ratingManager.saveRating(obj);
//        Product product = courierManager.getProductByID(productID);
//        product.addRating(obj);
//        courierManager.updateProduct(product);
//        return ok(Json.toJson(product));
//    }
//
//    public Result addReviewToVendor(String vendorID){
//        Form<Review> newReview = formFactory.form(Review.class).bindFromRequest();
//        Review obj = newReview.get();
//        reviewManager.saveReview(obj);
//        Vendor vendor = vendorManager.getVendorByID(vendorID);
//        vendor.addReview(obj);
//        vendorManager.updateVendor(vendor);
//        return ok(Json.toJson(vendor));
//    }
//
//    public Result addReviewToShop(String shopID){
//        Form<Review> newReview = formFactory.form(Review.class).bindFromRequest();
//        Review obj = newReview.get();
//        reviewManager.saveReview(obj);
//        Shop shop = shopManager.getVendorShopDetails(shopID);
//        shop.addReview(obj);
//        shopManager.updateShop(shop);
//        return ok(Json.toJson(shop));
//    }
//
//    public Result addReviewToProduct(String productID){
//        Form<Review> newReview = formFactory.form(Review.class).bindFromRequest();
//        Review obj = newReview.get();
//        reviewManager.saveReview(obj);
//        Product product = courierManager.getProductByID(productID);
//        product.addReview(obj);
//        courierManager.updateProduct(product);
//        return ok(Json.toJson(product));
//    }
//
//    public Result viewShopProducts(String shopID){
//        Shop shop = shopManager.getVendorShopDetails(shopID);
//        List<Product> productList = courierManager.shopProducts(shop);
//        return ok(Json.toJson(productList));
//    }
//    public Result editVendor(String vendorID){
//        Form<Vendor> dataForm = formFactory.form(Vendor.class).bindFromRequest();
//        Vendor vendor = vendorManager.getVendorByID(vendorID);
//
//        Map<String, String> data = dataForm.data();
//        if(data.get("companyName") != null){
//            vendor.setCompanyName(data.get("companyName"));
//        }
//        if (data.get("website") != null){
//            vendor.setWebsite(data.get("website"));
//        }
//        vendorManager.updateVendor(vendor);
//
//        return ok(Json.toJson(vendor));
//    }
//
//    public Result editShopDetails(String shopID){
//        Form<Shop> dataForm = formFactory.form(Shop.class).bindFromRequest();
//        Shop obj = shopManager.getVendorShopDetails(shopID);
//        Map<String, String> data = dataForm.data();
//        double latitude = Double.parseDouble(data.get("latitude"));
//        String address = data.get("address");
//        if (address != null){
//            obj.setAddress(address);
//        }
//        if (data.get("longitude") != null){
//            obj.setLongitude(Double.parseDouble(data.get("longitude")));
//        }
//        if (data.get("latitude") != null){
//            obj.setLatitude(Double.parseDouble(data.get("latitude")));
//        }
//        return ok(Json.toJson(data));
//    }
//
//    public Result addCoupon(){
//        Form<Coupon> newCoupon = formFactory.form(Coupon.class).bindFromRequest();
//        Coupon obj = newCoupon.get();
//        couponManager.saveCoupon(obj);
//        return ok();
//    }
//    public Result showCoupons(){
//        List<Coupon> coupons = couponManager.showCoupons();
//        return ok(Json.toJson(coupons));
//    }
//    public Result deleteCoupon(String couponID){
//        couponManager.deleteCoupon(couponID);
//        return ok();
//    }
//
//    public Result validateCoupon(String couponID){
//        Coupon coupon = couponManager.getCouponByID(couponID);
//        coupon.setValid();
//        couponManager.updateCoupon(coupon);
//        return ok(Json.toJson(coupon));
//    }
//    public Result invalidateCoupon(String couponID){
//        Coupon coupon = couponManager.getCouponByID(couponID);
//        coupon.setInvalid();
//        couponManager.updateCoupon(coupon);
//        return ok(Json.toJson(coupon));
//    }
}
