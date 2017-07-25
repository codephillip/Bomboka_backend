package controllers;

import Utils.DatabaseUtils;
import Utils.Utility;
import models.courier.Courier;
import models.order.Order;
import models.user.User;
import models.vendor.Product;
import org.bson.types.ObjectId;
import org.omg.CORBA.LocalObject;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import scala.reflect.internal.Trees;

import javax.inject.Inject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Codephillip on 12/02/16.
 */
public class OrderController extends Controller {
    private DatabaseUtils orderManager = new DatabaseUtils("order");
    private DatabaseUtils courierManager = new DatabaseUtils("courier");
    private DatabaseUtils userManager = new DatabaseUtils("users");
    private DatabaseUtils productManager = new DatabaseUtils("products");
    private final FormFactory formFactory;

    @Inject
    public OrderController(FormFactory formFactory) {
        this.formFactory = formFactory;
    }


    public Result changeCourier(String orderID) {
        Form<Order> orderForm = formFactory.form(Order.class).bindFromRequest();
        Map<String, String> data = orderForm.data();
        Order order = orderManager.getOrderByID(orderID);
        Logger.debug("CHANGE ID1-" + order.getCourier());
        Logger.debug("CHANGE ID2-" + data.get("courier"));
        if (Utility.isNotEmpty(data.get("courier"))) {
            order.setCourier(orderManager.getCourierByID(data.get("courier")));
            orderManager.updateOrder(order);
            return ok("Changed Courier");
        }
        return ok("Failed to change courier");
    }

    public Result cancelOrder(String orderID) {
        Order order = orderManager.getOrderByID(orderID);
        if (order.isValid()) {
            order.setValid(false);
            orderManager.updateOrder(order);
            return ok(Json.toJson(order));
        }
        return ok("Failed to cancel");
    }

    public Result addOrder() {
        Form<Order> order = formFactory.form(Order.class).bindFromRequest();
        Map<String, String> data = order.data();
        Order obj = new Order();
        obj.setBuyer(userManager.getUserByID(data.get("buyerId")));
        obj.setCourier(courierManager.getCourierByID(data.get("courierId")));
        Logger.debug(String.valueOf(courierManager.getCourierByID(data.get("courierId"))));
        Logger.debug("Product#");
        Logger.debug(data.get("productId"));
        Logger.debug(String.valueOf(productManager.getProductByID(data.get("productId"))));
        obj.setProduct(productManager.getProductByID(data.get("productId")));
        //verification code is 6 digits long between 100000 to 999999
        obj.setVerificationCode(Utility.randInt(100000, 999999));
        //todo add delivery time
//        obj.setDeliveryTime(new Date(data.get("deliveryTime")));
        orderManager.saveOrder(obj);
        return ok(Json.toJson(obj));
    }

    public Result viewOrders() {
        Logger.debug("viewOrders#");
        List<Order> orders = orderManager.allOrders();
        for (Order order : orders) {
            Logger.debug("viewOrders ID" + order.getKey());
        }
        return ok(Json.toJson(orders));
    }

    public Result viewUserOrders(String userId) {
        Logger.debug("viewOrders#");
        Logger.debug(userId);
        List<Order> orders = orderManager.getUserOrders(userId);
        for (Order order : orders) {
            Logger.debug("viewOrders ID" + order.getKey());
        }
        return ok(Json.toJson(orders));
    }

    public Result viewCourierOrders(String courierId) {
        Logger.debug("viewOrders#");
        List<Order> orders = orderManager.allOrders();
        List<Order> courierOrders = new ArrayList<Order>();
        for (Order order : orders) {
            if (Objects.equals(order.getCourier().getkey(), courierId))
                courierOrders.add(order);
            Logger.debug("viewCourierOrders courierID" + order.getCourier().getkey());
        }
        return ok(Json.toJson(courierOrders));
    }

    public Result viewCourierOrdersByParams(String courierID, String isDelivered, String vendorID, String startDate, String endDate) {
        List<Order> orders = orderManager.allOrders();
        List<Order> filteredOrders = new ArrayList<>();

        try {
            for (Order order : orders) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date realStartDate = formatter.parse(startDate);
                Date realEndDate = formatter.parse(endDate);

                Logger.debug(String.valueOf(realStartDate));
                Logger.debug(String.valueOf(realEndDate));
                Logger.debug(String.valueOf(order.getDeliveryTime()));
                if (Objects.equals(order.getCourier().getkey(), courierID)
                        && Objects.equals(String.valueOf(order.isReceived()), isDelivered)
                        && Objects.equals(order.getProduct().getVendor().getKey(), vendorID)
                        && isWithinDateRange(order.getDeliveryTime(), realStartDate, realEndDate)) {
                    filteredOrders.add(order);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Logger.debug("viewCourierOrdersByParams");
        Logger.debug(courierID + " # " + isDelivered);
        return ok(Json.toJson(filteredOrders));
    }

    private boolean isWithinDateRange(Date deliveryTime, Date startDate, Date endDate) {
        return deliveryTime.after(startDate) && deliveryTime.before(endDate);
    }

    public Result receivedOrder(String orderID) {
        Form<Order> orderform = formFactory.form(Order.class).bindFromRequest();
        Map<String, String> data = orderform.data();
        Order order = orderManager.getOrderByID(orderID);
        if (Integer.parseInt(data.get("verificationCode")) == order.getVerificationCode()) {
            if (!order.isReceived() && order.isValid()) {
                order.setReceived(true);
                orderManager.updateOrder(order);
                return ok("Successfully delivered");
            }
        }
        return ok("Wrong verification code");
    }

    public Result getCartItems(String userID) {
        List<Order> order = orderManager.getUserOrders(userID);
        return ok(Json.toJson(order));
    }
}
