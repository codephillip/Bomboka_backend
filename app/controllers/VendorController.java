package controllers;

import Utils.DatabaseUtils;
import Utils.Utility;
import models.courier.Courier;
import models.vendor.*;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * Created by Ahereza on 11/15/16.
 */
public class VendorController extends Controller {

    private DatabaseUtils vendorManager = new DatabaseUtils("vendors");
    private DatabaseUtils shopManager = new DatabaseUtils("shops");
    private DatabaseUtils productManager = new DatabaseUtils("products");
    private DatabaseUtils ratingManager = new DatabaseUtils("ratings");
    private DatabaseUtils reviewManager = new DatabaseUtils("reviews");
    private DatabaseUtils couponManager = new DatabaseUtils("coupons");
    private DatabaseUtils productCategoryManager = new DatabaseUtils("productCategory");
    private DatabaseUtils courierManager = new DatabaseUtils("courier");

    private final FormFactory formFactory;
    private final Utility utility;


    @Inject
    public VendorController(FormFactory formFactory, Utility utility) {
        this.formFactory = formFactory;
        this.utility = utility;
    }

    public Result searchProduct() {
        Logger.debug("searching product#");
        Form<Product> product = formFactory.form(Product.class).bindFromRequest();
        Map<String, String> data = product.data();
        Logger.debug("wait...");
        Logger.debug(String.valueOf(data));
        List<Product> allProducts = productManager.searchProducts(data.get("keyword"));
        Logger.debug(String.valueOf(allProducts));
        return ok(Json.toJson(allProducts));
    }

    public Result viewProducts() {
        Logger.debug("viewProducts#");
        List<Product> allProducts = productManager.allProducts();
        return ok(Json.toJson(allProducts));
    }

    public Result FetchImageUpload(String link){
        return ok(new java.io.File(System.getProperty("user.dir")+"/uploads/"+link));
    }

    public Result viewProductCategorys() {
        List<ProductCategory> productCategorys = productCategoryManager.allProductCategorys();
        for (ProductCategory productCategory : productCategorys) {
            Logger.debug("PC id# " + productCategory.getKey());
        }
        return ok(Json.toJson(productCategoryManager.allProductCategorys()));
    }

    public Result addProductCategory(String parentID) {
        Form<ProductCategory> productCategory = formFactory.form(ProductCategory.class).bindFromRequest();
        ProductCategory obj = productCategory.get();
        if (parentID == null)
            obj.setParent("parent");
        else
            obj.setParent(parentID);
        productCategoryManager.saveProductCategory(obj);
        return ok(Json.toJson(obj));
    }

    public Result addVendor() {
        Form<Vendor> vendor = formFactory.form(Vendor.class).bindFromRequest();
        Vendor obj = vendor.get();
        vendorManager.saveVendor(obj);
        return ok(Json.toJson(obj));
    }

    public Result addOrReplaceVendorImage(String VendorID) {
        Vendor vendor = vendorManager.getVendorByID(VendorID);
        String path = System.getProperty("user.dir") + "/uploads/" + Utility.PROFILE_IMAGE + "/" + VendorID;
        Logger.debug("File upload#" + path);
        utility.deleteOldImage(path);
        ArrayList<String> imageNames =  utility.uploadImage(path, 1);
        vendor.setImage(imageNames.get(0));
        vendorManager.updateVendor(vendor);
        return ok(Json.toJson(vendor));
    }

    public Result viewVendorDetails(String vendorID) {
        // tested passed
        Vendor result = vendorManager.getVendorByID(vendorID);
        return ok(Json.toJson(result));
    }

    public Result viewVendorShops(String vendorID) {
        return ok(Json.toJson(shopManager.findVendorShops(vendorID)));
    }

    public Result addVendorShop(String vendorID) {
        Form<Shop> shopForm = formFactory.form(Shop.class).bindFromRequest();
        Map<String, String> data = shopForm.data();
        Shop shop = new Shop(vendorManager.getVendorByID(vendorID), Double.parseDouble(data.get("latitude")), Double.parseDouble(data.get("longitude")), data.get("address"), data.get("name"));
        shopManager.saveShop(shop);
        return ok(Json.toJson(shop));
    }

    public Result viewAllVendors() {
        // tested and passed
        List<Vendor> allVendors = vendorManager.allVendors();
        // debug purposes
        for (Vendor vendor : allVendors) {
            Logger.debug("viewVendors#" + vendor.getKey());
        }
        return ok(Json.toJson(allVendors));
    }

    public Result viewShopDetails(String shopID) {
        // tested passed
        Shop shop = shopManager.getVendorShopDetails(shopID);
        return ok(Json.toJson(shop));
    }

    public Result deleteVendor(String vendorID) {
        // testing passed
        vendorManager.deleteVendor(vendorID);
        return ok();
    }

    public Result deleteShop(String shopID) {
        // tested passed
        shopManager.deleteShop(shopID);
        return ok();
    }

    public Result approveVendor(String vendorID) {
        // tested passed
        Vendor vendor = vendorManager.getVendorByID(vendorID);
        vendor.setApproved(true);
        vendorManager.saveVendor(vendor);
        return ok(Json.toJson(vendor));
    }

    public Result disapproveVendor(String vendorID) {
        // tested and passed
        Vendor vendor = vendorManager.getVendorByID(vendorID);
        vendor.setApproved(false);
        vendorManager.saveVendor(vendor);
        return ok(Json.toJson(vendor));
    }

    public Result writeVendorReview(String reviewText, String vendorID) {
        Vendor vendor = vendorManager.getVendorByID(vendorID);
        Review review = new Review(reviewText);
        vendor.addReview(review);
        // TODO: 11/19/16  update the item
        vendorManager.updateVendor(vendor);
        return ok(Json.toJson(vendor));
    }

    public Result addVendorRating(double rating, String vendorID) {
        if (rating > 5) {
            rating = 5;
        } else if (rating < 0) {
            rating = 0;
        }
        Vendor vendor = vendorManager.getVendorByID(vendorID);
        Rating newRating = new Rating(rating);
        vendor.addRating(newRating);
        // TODO: 11/19/16  update the item
        vendorManager.saveVendor(vendor);
        return ok(Json.toJson(vendor));
    }

    public Result writeShopReview(String reviewText, String shopID) {
        Shop shop = shopManager.getVendorShopDetails(shopID);
        Review review = new Review(reviewText);
        shop.addReview(review);
        // TODO: 11/19/16  update the item
        shopManager.saveShop(shop);
        return ok(Json.toJson(shop));
    }

    public Result addShopRating(double rating, String shopID) {
        if (rating > 5) {
            rating = 5;
        } else if (rating < 0) {
            rating = 0;
        }
        Shop shop = shopManager.getVendorShopDetails(shopID);
        Rating newRating = new Rating(rating);
        shop.addRating(newRating);
        // TODO: 11/19/16  update the item
        shopManager.saveShop(shop);
        return ok(Json.toJson(shop));
    }

    public Result addProductToShop(String shopID) {
        //create image_url and upload images
        String path = System.getProperty("user.dir") + "/uploads/" + Utility.PRODUCT_IMAGE;
        Logger.debug("File upload#" + path);
        ArrayList<String> imageNames =  utility.uploadImage(path, 4);

        //get form data and create product
        Form<Product> productForm = formFactory.form(Product.class).bindFromRequest();
        Map<String, String> data = productForm.data();
        Product product = new Product(data.get("name"), data.get("description"), Double.parseDouble(data.get("price")));
        product.setVendor(vendorManager.getVendorByID(data.get("vendor")));
        product.setProductCategory(productCategoryManager.getProductCategoryByID(data.get("productCategory")));
        product.setImages(imageNames);
        productManager.saveProduct(product);

        //add product to shop
        Shop shop = shopManager.getVendorShopDetails(shopID);
        shop.addToProductsList(product);
        shopManager.updateShop(shop);
        return ok(Json.toJson(shop));
    }

    public Result deleteProductFromShop(String productID) {
        productManager.deleteProduct(productID);
        return ok();
    }

    public Result editProduct(String productID) {
        Logger.debug("editProduct# " + productID);
        Form<Product> productForm = formFactory.form(Product.class).bindFromRequest();
        Product obj = productForm.get();
        Product dbProduct = productManager.getProductByID(productID);

        Logger.debug("editProduct# " + dbProduct.getName() + dbProduct.getPrice());

        Map<String, String> data = productForm.data();

        if (dbProduct.getPrice() != obj.getPrice() && obj.getPrice() != 0) {
            dbProduct.setPrice(obj.getPrice());
        }
        if (!Objects.equals(dbProduct.getName(), obj.getName()) && !obj.getName().isEmpty()) {
            dbProduct.setName(obj.getName());
        }
        if (!Objects.equals(dbProduct.getDescription(), obj.getDescription()) && !obj.getName().isEmpty()) {
            dbProduct.setDescription(obj.getDescription());
        }
        if (dbProduct.getProductCategory() != obj.getProductCategory()) {
            dbProduct.setProductCategory(obj.getProductCategory());
        }
        if (!Objects.equals(dbProduct.getManufacturer(), obj.getManufacturer())) {
            dbProduct.setManufacturer(obj.getManufacturer());
        }
        if (!Objects.equals(String.valueOf(dbProduct.isFake()), data.get("fake")) && Utility.isNotEmpty(data.get("fake"))) {
            Logger.debug("editproduct " + data.get("fake"));
            dbProduct.setFake(!dbProduct.isFake());
        }

        productManager.updateProduct(dbProduct);
        return ok(Json.toJson(obj));
    }

    public Result blockVendor(String vendorID) {
        Vendor vendor = vendorManager.getVendorByID(vendorID);
        vendor.setBlocked(true);
        vendorManager.updateVendor(vendor);
        return ok(Json.toJson(vendor));
    }

    public Result unBlockVendor(String vendorID) {
        Vendor vendor = vendorManager.getVendorByID(vendorID);
        vendor.setBlocked(false);
        vendorManager.updateVendor(vendor);
        return ok(Json.toJson(vendor));
    }

    public Result addRatingToVendor(String vendorID) {
        Form<Rating> newRating = formFactory.form(Rating.class).bindFromRequest();
        Rating obj = newRating.get();
        ratingManager.saveRating(obj);
        Vendor vendor = vendorManager.getVendorByID(vendorID);
        vendor.addRating(obj);
        vendorManager.updateVendor(vendor);
        return ok(Json.toJson(vendor));
    }

    public Result addRatingToShop(String shopID) {
        Form<Rating> newRating = formFactory.form(Rating.class).bindFromRequest();
        Rating obj = newRating.get();
        ratingManager.saveRating(obj);
        Shop shop = shopManager.getVendorShopDetails(shopID);
        shop.addRating(obj);
        vendorManager.updateShop(shop);
        return ok(Json.toJson(shop));
    }

    public Result addRatingToProduct(String productID) {
        Form<Rating> newRating = formFactory.form(Rating.class).bindFromRequest();
        Rating obj = newRating.get();
        ratingManager.saveRating(obj);
        Product product = productManager.getProductByID(productID);
        product.addRating(obj);
        productManager.updateProduct(product);
        return ok(Json.toJson(product));
    }

    public Result addReviewToVendor(String vendorID) {
        Form<Review> newReview = formFactory.form(Review.class).bindFromRequest();
        Review obj = newReview.get();
        reviewManager.saveReview(obj);
        Vendor vendor = vendorManager.getVendorByID(vendorID);
        vendor.addReview(obj);
        vendorManager.updateVendor(vendor);
        return ok(Json.toJson(vendor));
    }

    public Result addReviewToShop(String shopID) {
        Form<Review> newReview = formFactory.form(Review.class).bindFromRequest();
        Review obj = newReview.get();
        reviewManager.saveReview(obj);
        Shop shop = shopManager.getVendorShopDetails(shopID);
        shop.addReview(obj);
        shopManager.updateShop(shop);
        return ok(Json.toJson(shop));
    }

    public Result addReviewToProduct(String productID) {
        Form<Review> newReview = formFactory.form(Review.class).bindFromRequest();
        Review obj = newReview.get();
        reviewManager.saveReview(obj);
        Product product = productManager.getProductByID(productID);
        product.addReview(obj);
        productManager.updateProduct(product);
        return ok(Json.toJson(product));
    }

    public Result viewShopProducts(String shopID) {
        Logger.debug("Viewshopproducts#");
        Shop shop = shopManager.getVendorShopDetails(shopID);
        List<Product> productList = productManager.shopProducts(shop);
        return ok(Json.toJson(productList));
    }

    public Result viewAllProducts() {
        Logger.debug("Viewallproducts#");
        List<Product> productList = productManager.allProducts();
        return ok(Json.toJson(productList));
    }

    public Result editVendor(String vendorID) {
        Form<Vendor> dataForm = formFactory.form(Vendor.class).bindFromRequest();
        Vendor dbVendor = vendorManager.getVendorByID(vendorID);

        Map<String, String> data = dataForm.data();
        if (!Objects.equals(dbVendor.getName(), data.get("name")) && Utility.isNotEmpty(data.get("name"))) {
            dbVendor.setName(data.get("name"));
        }
        if (!Objects.equals(dbVendor.getWebsite(), data.get("website")) && Utility.isNotEmpty(data.get("website"))) {
            dbVendor.setWebsite(data.get("website"));
        }
        if (!Objects.equals(String.valueOf(dbVendor.isVerified()), data.get("verified")) && Utility.isNotEmpty(data.get("verified"))) {
            Logger.debug("editvendor " + data.get("verified"));
            dbVendor.setVerified(!dbVendor.isVerified());
        }
        if (!Objects.equals(String.valueOf(dbVendor.isBlocked()), data.get("blocked")) && Utility.isNotEmpty(data.get("blocked"))) {
            Logger.debug("editvendor " + data.get("blocked"));
            dbVendor.setBlocked(!dbVendor.isBlocked());
        }
        if (!Objects.equals(String.valueOf(dbVendor.isApproved()), data.get("approved")) && Utility.isNotEmpty(data.get("approved"))) {
            Logger.debug("editvendor " + data.get("approved"));
            dbVendor.setApproved(!dbVendor.isApproved());
        }

        vendorManager.updateVendor(dbVendor);
        return ok(Json.toJson(dbVendor));
    }

    public Result editShopDetails(String shopID) {
        Form<Shop> dataForm = formFactory.form(Shop.class).bindFromRequest();
        Shop obj = shopManager.getVendorShopDetails(shopID);
        Map<String, String> data = dataForm.data();
        //todo handle casting exception
        double latitude = Double.parseDouble(data.get("latitude"));
        String address = data.get("address");
        if (!address.isEmpty()) {
            obj.setAddress(address);
        }
        if (Utility.isNotEmpty(data.get("longitutude"))) {
            obj.setLongitude(Double.parseDouble(data.get("longitude")));
        }
        if (Utility.isNotEmpty(data.get("latitude"))) {
            obj.setLatitude(Double.parseDouble(data.get("latitude")));
        }
        return ok(Json.toJson(data));
    }

    public Result addCoupon() {
        Form<Coupon> newCoupon = formFactory.form(Coupon.class).bindFromRequest();
        Coupon obj = newCoupon.get();
        couponManager.saveCoupon(obj);
        return ok();
    }

    public Result showCoupons() {
        List<Coupon> coupons = couponManager.showCoupons();
        return ok(Json.toJson(coupons));
    }

    public Result deleteCoupon(String couponID) {
        couponManager.deleteCoupon(couponID);
        return ok();
    }

    public Result validateCoupon(String couponID) {
        Coupon coupon = couponManager.getCouponByID(couponID);
        coupon.setValid();
        couponManager.updateCoupon(coupon);
        return ok(Json.toJson(coupon));
    }

    public Result invalidateCoupon(String couponID) {
        Coupon coupon = couponManager.getCouponByID(couponID);
        coupon.setInvalid();
        couponManager.updateCoupon(coupon);
        return ok(Json.toJson(coupon));
    }

    public Result addCourierToVendor(String vendorID) {
        Form<Vendor> dataForm = formFactory.form(Vendor.class).bindFromRequest();
        Map<String, String> data = dataForm.data();

        Vendor vendor = vendorManager.getVendorByID(vendorID);
        if (Utility.isNotEmpty(data.get("courier"))) {
            Courier courier = courierManager.getCourierByID(data.get("courier"));
            Logger.debug("addCourierToVendor# " + courier.getName());
            if (!vendor.getCouriers().contains(courier.getkey())) {
                vendor.addCourier(courier);
                vendorManager.updateVendor(vendor);
                return ok(Json.toJson(vendor));
            }
        }
        return ok("Failed to add Courier");
    }

    public Result removeCourierFromVendor(String vendorID) {
        Form<Vendor> dataForm = formFactory.form(Vendor.class).bindFromRequest();
        Map<String, String> data = dataForm.data();

        Vendor vendor = vendorManager.getVendorByID(vendorID);
        if (Utility.isNotEmpty(data.get("courier"))) {
            vendor.removeCourier(data.get("courier"));
            vendorManager.updateVendor(vendor);
            return ok(Json.toJson(vendor));
        }
        return ok("Failed to add Courier");
    }
}
