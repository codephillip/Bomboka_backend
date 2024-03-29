package controllers;

import Utils.DatabaseUtils;
import Utils.Utility;
import models.courier.Courier;
import models.courier.CourierProfile;
import models.vendor.Rating;
import models.vendor.Review;
import org.bson.types.ObjectId;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.*;

/**
 * Created by Codephillip on 11/30/16.
 */
public class CourierController extends Controller{

    private DatabaseUtils courierManager = new DatabaseUtils("courier");
    private DatabaseUtils reviewManager = new DatabaseUtils("reviews");
    private DatabaseUtils ratingManager = new DatabaseUtils("ratings");

    private final FormFactory formFactory;
    private final Utility utility;

    @Inject
    public CourierController(FormFactory formFactory, Utility utility) {
        this.formFactory = formFactory;
        this.utility = utility;
    }

    public Result viewCouriers() {
        Logger.debug("viewCouriers#");
        List<Courier> allCouriers = courierManager.allCouriers();
        //todo remove on release
        for (Courier courier : allCouriers) {
            Logger.debug("viewCouriers# " + courier.getkey());
        }
        return ok(Json.toJson(allCouriers));
    }

    public Result addCourier() {
        Form<Courier> courier = formFactory.form(Courier.class).bindFromRequest();
        Courier obj = courier.get();
        courierManager.saveCourier(obj);
        return ok(Json.toJson(obj)) ;
    }

    public Result editCourier(String courierID){
        Logger.debug("editCourier# "+ courierID);
        Form<Courier> courierForm = formFactory.form(Courier.class).bindFromRequest();
        Courier dbCourier = courierManager.getCourierByID(courierID);

        Logger.debug("editCourier# " + dbCourier.getName() + dbCourier.getEmail());

        Map<String, String> data = courierForm.data();

        if (!Objects.equals(dbCourier.getName(), data.get("name")) && Utility.isNotEmpty(data.get("name"))) {
            dbCourier.setName(data.get("name"));
        }
        if (!Objects.equals(dbCourier.getAddress(), data.get("address")) && Utility.isNotEmpty(data.get("address"))) {
            dbCourier.setAddress(data.get("address"));
        }
        if (!Objects.equals(dbCourier.getEmail(), data.get("email")) && Utility.isNotEmpty(data.get("email"))) {
            dbCourier.setEmail(data.get("email"));
        }
        if (!Objects.equals(dbCourier.getPhoneNumber(), data.get("phoneNumber")) && Utility.isNotEmpty(data.get("phoneNumber"))) {
            dbCourier.setPhoneNumber(data.get("phoneNumber"));
        }
        if (!Objects.equals(String.valueOf(dbCourier.isDeleted()), data.get("deleted")) && Utility.isNotEmpty(data.get("deleted"))) {
            dbCourier.setDeleted(!dbCourier.isDeleted());
        }
        if (!Objects.equals(String.valueOf(dbCourier.isBlocked()), data.get("blocked")) && Utility.isNotEmpty(data.get("blocked"))) {
            dbCourier.setBlocked(!dbCourier.isBlocked());
        }
        if (!Objects.equals(String.valueOf(dbCourier.isApproved()), data.get("approved")) && Utility.isNotEmpty(data.get("approved"))) {
            dbCourier.setApproved(!dbCourier.isApproved());
        }

        try {
            Double latitude = Double.parseDouble(data.get("latitude"));
            if (dbCourier.getLatitude() != latitude) {
                dbCourier.setLatitude(latitude);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Double longitude = Double.parseDouble(data.get("longitude"));
            if (dbCourier.getLongitude() != longitude) {
                dbCourier.setLongitude(longitude);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        dbCourier.setModificationTimeStamp(new Date());

        courierManager.updateCourier(dbCourier);
        return ok(Json.toJson(dbCourier));
    }

    public Result deleteCourier(String courierID){
        Courier dbCourier = courierManager.getCourierByID(courierID);
        dbCourier.setTombStone(new Date());
        dbCourier.setDeleted(true);
        courierManager.updateCourier(dbCourier);
        return ok();
    }

    public Result undeleteCourier(String courierID){
        Courier courier = courierManager.getCourierByID(courierID);
        courier.setDeleted(false);
        courierManager.updateCourier(courier);
        return ok(Json.toJson(courier));
    }

    public Result approveCourier(String courierID){
        Courier courier = courierManager.getCourierByID(courierID);
        courier.setApproved(true);
        courierManager.updateCourier(courier);
        return ok(Json.toJson(courier));
    }

    public Result disapproveCourier(String courierID){
        Courier courier = courierManager.getCourierByID(courierID);
        courier.setApproved(false);
        courierManager.updateCourier(courier);
        return ok(Json.toJson(courier));
    }

    public Result blockCourier(String courierID){
        Courier courier = courierManager.getCourierByID(courierID);
        courier.setBlocked(true);
        courierManager.updateCourier(courier);
        return ok(Json.toJson(courier));
    }

    public Result unblockCourier(String courierID){
        Courier courier = courierManager.getCourierByID(courierID);
        courier.setBlocked(false);
        courierManager.updateCourier(courier);
        return ok(Json.toJson(courier));
    }

    public Result writeCourierReview(String courierID){
        Form<Review> reviewForm = formFactory.form(Review.class).bindFromRequest();
        Review obj = reviewForm.get();
        Map<String, String> data = reviewForm.data();

        ObjectId userObject = new ObjectId(data.get("user"));
        Logger.debug("viewRatings# USERID " + userObject);

        String text = data.get("text");

        Courier courier = courierManager.getCourierByID(courierID);
        List<String> courierReviews = courier.getReviews();
        Logger.debug("LIST " + courierReviews.isEmpty());
        if (!courierReviews.isEmpty()) {
            // update old user review
            for (String courierReviewId : courierReviews) {
                Logger.debug("courierReview" + courierReviewId);
                Review review = reviewManager.getReviewByID(courierReviewId);
                if (review.getUser().equals(data.get("user"))) {
                    Logger.debug("MATCH FOUND");
                    review.setText(text);
                    reviewManager.updateReview(review);
                    return ok("Updated");
                }
            }
            createReviewForNewUser(text, userObject, courier);
            return ok("Created New");
        } else {
            createReviewForNewUser(text, userObject, courier);
        }
        return ok("Finished");
    }

    private void createReviewForNewUser(String text, ObjectId userObject, Courier courier) {
        Logger.debug("creating new review for user");
        Review newReview = new Review(text, userObject);
        reviewManager.saveReview(newReview);
        courier.addReview(newReview);
        courierManager.updateCourier(courier);
    }

    public Result addCourierRating(String courierID){
        Form<Rating> ratingForm = formFactory.form(Rating.class).bindFromRequest();
        Map<String, String> data = ratingForm.data();
        
        double stars = Double.parseDouble(data.get("stars"));
        if (stars > 5){
            stars = 5;
        } else if (stars < 0){
            stars = 0;
        }
        
        ObjectId userObject = new ObjectId(data.get("user"));
        Logger.debug("viewRatings# USERID " + userObject);

        Courier courier = courierManager.getCourierByID(courierID);
        List<String> courierRatings = courier.getRatings();
        Logger.debug("LIST " + courierRatings.isEmpty());
        if (!courierRatings.isEmpty()) {
            // update old user review
            for (String courierRatingId : courierRatings) {
                Logger.debug("courierRating" + courierRatingId);
                Rating rating = ratingManager.getRatingByID(courierRatingId);
                if (Objects.equals(rating.getUser(), data.get("user"))) {
                    Logger.debug("MATCH FOUND");
                    rating.setStars(stars);
                    ratingManager.updateRating(rating);
                    return ok("Updated");
                }
            }
            createRatingForNewUser(stars, userObject, courier);
            return ok("Created New");
        } else {
            createRatingForNewUser(stars, userObject, courier);
        }
        return ok("Finished");
    }

    private void createRatingForNewUser(double stars, ObjectId userObject, Courier courier) {
        Logger.debug("creating new rating for user");
        Rating newRating = new Rating(stars, userObject);
        ratingManager.saveRating(newRating);
        courier.addRating(newRating);
        courierManager.updateCourier(courier);
    }


    public Result viewRatings() {
        Logger.debug("viewRatings#");
        List<Rating> allRatings = ratingManager.allRatings();
        for (Rating rating : allRatings) {
            Logger.debug("viewRatings# " + rating.getKey());
        }
        return ok(Json.toJson(allRatings));
    }

    public Result viewReviews() {
        Logger.debug("viewReviews#");
        List<Review> allReviews = reviewManager.allReviews();
        for (Review review : allReviews) {
            Logger.debug("viewReviews# " + review.getKey());
        }
        return ok(Json.toJson(allReviews));
    }

    public Result viewCourierProfile(String courierID){
        Courier result = courierManager.getCourierByID(courierID);
        CourierProfile courierProfile = new CourierProfile(result.getkey(), result.getName(), result.getAddress(), result.getImage(), result.getEmail(), result.getPhoneNumber(), result.isApproved(), result.getReviews(), result.getRatings());
        return ok(Json.toJson(courierProfile));
    }

    public Result editCourierProfile(String courierID){
        Logger.debug("editCourier# "+ courierID);
        Form<Courier> courierForm = formFactory.form(Courier.class).bindFromRequest();
        Courier dbCourier = courierManager.getCourierByID(courierID);

        Logger.debug("editCourier# " + dbCourier.getName() + dbCourier.getEmail());

        Map<String, String> data = courierForm.data();

        if (!Objects.equals(dbCourier.getName(), data.get("name")) && Utility.isNotEmpty(data.get("name"))) {
            dbCourier.setName(data.get("name"));
        }
        if (!Objects.equals(dbCourier.getAddress(), data.get("address")) && Utility.isNotEmpty(data.get("address"))) {
            dbCourier.setAddress(data.get("address"));
        }
        if (!Objects.equals(dbCourier.getEmail(), data.get("email")) && Utility.isNotEmpty(data.get("email"))) {
            dbCourier.setEmail(data.get("email"));
        }
        if (!Objects.equals(dbCourier.getPhoneNumber(), data.get("phoneNumber")) && Utility.isNotEmpty(data.get("phoneNumber"))) {
            dbCourier.setPhoneNumber(data.get("phoneNumber"));
        }
        if (!Objects.equals(String.valueOf(dbCourier.isDeleted()), data.get("deleted")) && Utility.isNotEmpty(data.get("deleted"))) {
            dbCourier.setDeleted(!dbCourier.isDeleted());
        }

        dbCourier.setModificationTimeStamp(new Date());

        courierManager.updateCourier(dbCourier);
        return ok(Json.toJson(dbCourier));
    }

    public Result addOrReplaceCourierImage(String courierID) {
        Courier courier = courierManager.getCourierByID(courierID);
        String path = System.getProperty("user.dir") + "/uploads/" + Utility.PROFILE_IMAGE + "/" + courierID;
        Logger.debug("File upload#" + path);
        utility.deleteOldImage(path);
        ArrayList<String> imageNames =  utility.uploadImage(path, 1);
        courier.setImage(imageNames.get(0));
        courierManager.updateCourier(courier);
        return ok(Json.toJson(courier));
    }
}
