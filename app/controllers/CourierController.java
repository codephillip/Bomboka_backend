package controllers;

import Utils.DatabaseUtils;
import models.courier.Courier;
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
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Codephillip on 11/30/16.
 */
public class CourierController extends Controller{

    private DatabaseUtils courierManager = new DatabaseUtils("courier");
    private DatabaseUtils reviewManager = new DatabaseUtils("reviews");
    private DatabaseUtils ratingManager = new DatabaseUtils("ratings");

    private final FormFactory formFactory;

    @Inject
    public CourierController(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    public Result viewCouriers() {
        Logger.debug("viewCouriers#");
        List<Courier> allCouriers = courierManager.allCouriers();
        //todo remove on release
        for (Courier courier : allCouriers) {
            Logger.debug("viewCouriers# " + courier.get_id().toString());
        }
        //
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

        if (!Objects.equals(dbCourier.getName(), data.get("name")) && data.get("name") != null) {
            dbCourier.setName(data.get("name"));
        }
        if (!Objects.equals(dbCourier.getAddress(), data.get("address")) && data.get("address") != null) {
            dbCourier.setName(data.get("address"));
        }
        if (!Objects.equals(dbCourier.getEmail(), data.get("email")) && data.get("email") != null) {
            dbCourier.setEmail(data.get("email"));
        }
        if (!Objects.equals(dbCourier.getPhoneNumber(), data.get("phoneNumber")) && data.get("phoneNumber") != null) {
            dbCourier.setPhoneNumber(data.get("phoneNumber"));
        }
        if (!Objects.equals(String.valueOf(dbCourier.isBlocked()), data.get("blocked")) && data.get("blocked") != null){
            dbCourier.setBlocked(!dbCourier.isBlocked());
        }
        if (!Objects.equals(String.valueOf(dbCourier.isApproved()), data.get("approved")) && data.get("approved") != null){
            dbCourier.setApproved(!dbCourier.isApproved());
        }
        if (!Objects.equals(String.valueOf(dbCourier.isDeleted()), data.get("deleted")) && data.get("deleted") != null){
            dbCourier.setDeleted(!dbCourier.isDeleted());
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
        Logger.debug("viewRatings# USERID" + userObject.toString());
        
//        Courier courier = courierManager.getCourierByID(courierID);
//        reviewManager.saveReview(obj);
//        courier.addReview(obj);
//        courierManager.saveCourier(courier);
//        return ok(Json.toJson(courier));

        // checks if user already has a review
        List<Review> allReviews = reviewManager.allReviews();
        for (Review review : allReviews) {
            Logger.debug("viewReviews# " + "updating review");
            Logger.debug("viewReviews#" + review.getUser().toString());
            if (review.getUser().toString().equals(userObject.toString())) {
                Logger.debug("viewReviews# " + "finding userObject");
                review.setText(data.get("text"));
                reviewManager.updateReview(review);

                Courier courier = courierManager.getCourierByID(courierID);
                courier.addReview(review);
                courierManager.updateCourier(courier);
                return ok(Json.toJson(review));
            }
        }

        //creates new review
        Review newReview = new Review();
        newReview.setText(data.get("text"));
        newReview.setUser(userObject);
        Logger.debug("addCourierReview# " + newReview.getText());
        reviewManager.saveReview(newReview);

        Courier courier = courierManager.getCourierByID(courierID);
        courier.addReview(newReview);
        courierManager.updateCourier(courier);
        return ok(Json.toJson(newReview));
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
        Logger.debug("viewRatings# USERID" + userObject.toString());

        // checks if user already has a rating
        List<Rating> allRatings = ratingManager.allRatings();
        for (Rating rating : allRatings) {
            Logger.debug("viewRatings# " + "updating rating");
            Logger.debug("viewRatings#" + rating.getUser().toString());
            if (rating.getUser().toString().equals(userObject.toString())) {
                Logger.debug("viewRatings# " + "finding userObject");
                rating.setStars(stars);
                ratingManager.updateRating(rating);

                Courier courier = courierManager.getCourierByID(courierID);
                courier.addRating(rating);
                courierManager.updateCourier(courier);
                return ok(Json.toJson(rating));
            }
        }

        //creates new rating
        Rating newRating = new Rating();
        newRating.setStars(stars);
        newRating.setUser(userObject);
        Logger.debug("addCourierRating# " + newRating.getStars());
        ratingManager.saveRating(newRating);

        Courier courier = courierManager.getCourierByID(courierID);
        courier.addRating(newRating);
        courierManager.updateCourier(courier);
        return ok(Json.toJson(newRating));
    }

    public Result viewRatings() {
        Logger.debug("viewRatings#");
        List<Rating> allRatings = ratingManager.allRatings();
        //todo remove on release
        for (Rating rating : allRatings) {
            Logger.debug("viewRatings# " + rating.get_id().toString());
        }
        return ok(Json.toJson(allRatings));
    }

    public Result viewReviews() {
        Logger.debug("viewReviews#");
        List<Review> allReviews = reviewManager.allReviews();
        //todo remove on release
        for (Review review : allReviews) {
            Logger.debug("viewReviews# " + review.get_id().toString());
        }
        return ok(Json.toJson(allReviews));
    }
}
