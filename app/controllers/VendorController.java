package controllers;

import Utils.DatabaseUtils;
import Utils.Utility;
import models.courier.Courier;
import models.vendor.*;
import org.bson.types.ObjectId;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import play.mvc.Http;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;


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
    private DatabaseUtils productCategoryManager = new DatabaseUtils("productCategory");
    private DatabaseUtils courierManager = new DatabaseUtils("courier");

    private final FormFactory formFactory;

    @Inject
    public VendorController(FormFactory formFactory) {
        this.formFactory = formFactory;
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

    public Result addProduct() {
        Form<Product> product = formFactory.form(Product.class).bindFromRequest();
        Product obj = product.get();

        Map<String, String> hold = product.data();
        Logger.debug("addProduct#" + hold);
        Logger.debug("productCategoryID#" + hold.get("category"));
        ProductCategory parentProductCategory = productCategoryManager.getProductCategoryByName(hold.get("category"));

        obj.setProductCategory(parentProductCategory.get_id());
        productManager.saveProduct(obj); // saves to Product table
        return ok(Json.toJson(obj)) ;
    }

//    public Result uploadProductImage(String imageCategory){
//        String path = System.getProperty("user.dir")+"/uploads/" + imageCategory;
//        Logger.debug("File upload#" + path);
//
//        deleteOldImage(path);
//
//        Http.MultipartFormData body = request().body().asMultipartFormData();
//        Http.MultipartFormData.FilePart uploadFile = body.getFile("upload");
//        String file_name = uploadFile.getFilename();
//
//        File uploadF = (File) uploadFile.getFile();
//        String newFileName =  System.currentTimeMillis()+"-"+file_name;
//        File openFile = new File(path +"/"+ newFileName);
//        String [] x = new String[]{newFileName, uploadFile.getContentType()};
//        InputStream isFile1 = null;
//
//        try {
//            isFile1 = new FileInputStream(uploadF);
//            byte[] byteFile1 = IOUtils.toByteArray(isFile1);
//
//            FileUtils.writeByteArrayToFile(openFile, byteFile1);
//            isFile1.close();
//
//        } catch (IOException  e) {
//            e.printStackTrace();
//        }
//        return ok(Json.toJson(x));
//    }

    private String[] uploadImage(String path){
        Http.MultipartFormData body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart uploadFile = body.getFile("upload");
        String file_name = uploadFile.getFilename();

        File uploadF = (File) uploadFile.getFile();
        String newFileName =  System.currentTimeMillis()+"-"+file_name;
        File openFile = new File(path +"/"+ newFileName);
        String [] x = new String[]{newFileName, uploadFile.getContentType()};
        InputStream isFile1 = null;

        try {
            isFile1 = new FileInputStream(uploadF);
            byte[] byteFile1 = IOUtils.toByteArray(isFile1);

            FileUtils.writeByteArrayToFile(openFile, byteFile1);
            isFile1.close();

        } catch (IOException  e) {
            e.printStackTrace();
        }
        Logger.debug(String.valueOf(Json.toJson(x)));
        return x;
    }


    private void deleteOldImage(String path) {
        Logger.debug("Delete old image");
        File f_d;
        f_d = new File(path);
        if(f_d.isDirectory()){
            for (String x : f_d.list()) {
                new File(f_d, x).delete();

            }
        }
    }

    public Result viewProductCategorys() {
        List<ProductCategory> productCategorys= productCategoryManager.allProductCategorys();
        for (ProductCategory productCategory : productCategorys) {
            Logger.debug("PC id# " + productCategory.get_id().toString());
        }
        return ok(Json.toJson(productCategoryManager.allProductCategorys()));
    }

    public Result addProductCategory() {
        Form<ProductCategory> productCategory = formFactory.form(ProductCategory.class).bindFromRequest();
        ProductCategory obj = productCategory.get(); // returns ProductCategory object from clean form

        Map<String, String> hold = productCategory.data();
        Logger.debug("insertProductCategory add#" + hold);
        Logger.debug("insertProductCategory add2#" + hold.get("name"));

        ArrayList<String> code = new ArrayList<>();

        try {
            ProductCategory parentProductCategory = productCategoryManager.getProductCategoryByName(hold.get("parent"));
            code = parentProductCategory.getAncestorCode(); // retrieves the arraylist(code) of the parent
            code.add(hold.get("name")); // appends name of the child to the end of the arraylist
        } catch (Exception e){
            e.printStackTrace();
            code.add(hold.get("name"));
        }

        ProductCategory childProductCategory = new ProductCategory(hold.get("name"), code, hold.get("parent"));
        Logger.debug("insertProductCategory " + childProductCategory);
        productCategoryManager.saveProductCategory(childProductCategory);
        return ok(Json.toJson(obj));
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
        // debug purposes
        for (Vendor vendor : allVendors) {
            Logger.debug("viewVendors#" + vendor.get_id().toString());
        }
        return ok(Json.toJson(allVendors));
    }

    public Result viewShopDetails(String shopID){
        // tested passed
        Shop shop = shopManager.getVendorShopDetails(shopID);
        return ok(Json.toJson(shop));
    }

    public Result editVendorDetails(String vendorID){
        Logger.debug("editVendor# "+ vendorID);
        Form<Vendor> vendorForm = formFactory.form(Vendor.class).bindFromRequest();
        Vendor dbVendor = vendorManager.getVendorByID(vendorID);
        Logger.debug("editVendor# " + dbVendor.getCompanyName() + dbVendor.isApproved());

        Map<String, String> data = vendorForm.data();
        if (!Objects.equals(dbVendor.getCompanyName(), data.get("companyName")) && Utility.isNotEmpty(data.get("companyName"))) {
            dbVendor.setCompanyName(data.get("companyName"));
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
        String path = System.getProperty("user.dir")+"/uploads/" + Utility.PRODUCT_IMAGE;
        Logger.debug("File upload#" + path);
        String imageName = uploadImage(path)[0];
        ArrayList<String> images = new ArrayList<String>();
        images.add(imageName);

        Form<Product> newProduct = formFactory.form(Product.class).bindFromRequest();
        Product product = newProduct.get();
        product.setImages(images);
        productManager.saveProduct(product);
        Shop shop = shopManager.getVendorShopDetails(shopID);
        shop.addToProductsList(product);
        shopManager.updateShop(shop);
        return ok(Json.toJson(shop));
    }

    public Result deleteProductFromShop(String productID){
        productManager.deleteProduct(productID);
        return ok();
    }

    public Result editProduct(String productID){
        Logger.debug("editProduct# "+ productID);
        Form<Product> productForm = formFactory.form(Product.class).bindFromRequest();
        Product obj = productForm.get();
        Product dbProduct = productManager.getProductByID(productID);

        Logger.debug("editProduct# " + dbProduct.getName() + dbProduct.getPrice());

        Map<String, String> data = productForm.data();

        if (dbProduct.getPrice() != obj.getPrice() && obj.getPrice() != 0) {
            dbProduct.setPrice(obj.getPrice());
        }
        if (!Objects.equals(dbProduct.getName(), obj.getName()) && !obj.getName().isEmpty()){
            dbProduct.setName(obj.getName());
        }
        if (!Objects.equals(dbProduct.getDescription(), obj.getDescription()) && !obj.getName().isEmpty()){
            dbProduct.setDescription(obj.getDescription());
        }
        if (dbProduct.getProductCategory() != obj.getProductCategory()){
            dbProduct.setProductCategory(obj.getProductCategory());
        }
        if (!Objects.equals(dbProduct.getManufacturer(), obj.getManufacturer())){
            dbProduct.setManufacturer(obj.getManufacturer());
        }
        if (!Objects.equals(String.valueOf(dbProduct.isFake()), data.get("fake")) && Utility.isNotEmpty(data.get("fake"))) {
            Logger.debug("editproduct " + data.get("fake"));
            dbProduct.setFake(!dbProduct.isFake());
        }

        productManager.updateProduct(dbProduct);
        return ok(Json.toJson(obj));
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
        if(Utility.isNotEmpty(data.get("companyName"))) {
            vendor.setCompanyName(data.get("companyName"));
        }
        if (Utility.isNotEmpty(data.get("website"))) {
            vendor.setWebsite(data.get("website"));
        }
        vendorManager.updateVendor(vendor);

        return ok(Json.toJson(vendor));
    }

    public Result editShopDetails(String shopID){
        Form<Shop> dataForm = formFactory.form(Shop.class).bindFromRequest();
        Shop obj = shopManager.getVendorShopDetails(shopID);
        Map<String, String> data = dataForm.data();
        //todo handle casting exception
        double latitude = Double.parseDouble(data.get("latitude"));
        String address = data.get("address");
        if (!address.isEmpty()){
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

    public Result addCourierToVendor(String vendorID) {
        Form<Vendor> dataForm = formFactory.form(Vendor.class).bindFromRequest();
        Map<String, String> data = dataForm.data();

        Vendor vendor = vendorManager.getVendorByID(vendorID);
        if (Utility.isNotEmpty(data.get("courier"))) {
            Courier courier = courierManager.getCourierByID(data.get("courier"));
            Logger.debug("addCourierToVendor# " + courier.getName());
            if (!vendor.getCouriers().contains(courier.get_id())) {
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
//            Courier courier = courierManager.getCourierByID(data.get("courier"));
//            Logger.debug("addCourierToVendor# " + courier.getName());
            vendor.removeCourier(new ObjectId(data.get("courier")));
            vendorManager.updateVendor(vendor);
            return ok(Json.toJson(vendor));
        }
        return ok("Failed to add Courier");
    }
}
