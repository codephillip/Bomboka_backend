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
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

//    public Result deleteVendor(String vendorID){
//        // testing passed
//        vendorManager.deleteVendor(vendorID);
//        return ok();
//    }
//
}
