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
        Form<Review> newReview = formFactory.form(Review.class).bindFromRequest();
        Review obj = newReview.get();
        Courier courier = courierManager.getCourierByID(courierID);
        reviewManager.saveReview(obj);
        courier.addReview(obj);
        courierManager.saveCourier(courier);
        return ok(Json.toJson(courier));
    }

    public Result addCourierRating(String courierID){
        Form<Rating> ratingForm = formFactory.form(Rating.class).bindFromRequest();
        Map<String, String> data = ratingForm.data();
        
        double rating = Double.parseDouble(data.get("stars"));
        if (rating > 5){
            rating = 5;
        } else if (rating < 0){
            rating = 0;
        }

        Rating newRating = new Rating();
        newRating.setStars(rating);
        newRating.setUser(new ObjectId(data.get("user")));
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
}
