package controllers;

import Utils.DatabaseUtils;
import Utils.Utility;
import models.courier.Courier;
import models.order.Order;
import models.user.User;
import models.vendor.Product;
import org.bson.types.ObjectId;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
        obj.setProduct(productManager.getProductByID(data.get("productId")));
        //todo add delivery time
//        obj.setDeliveryTime(new Date(data.get("deliveryTime")));
        orderManager.saveOrder(obj);
        return ok(Json.toJson(obj));
    }

    public Result viewOrders() {
        Logger.debug("viewOrders#");
        List<Order> orders = orderManager.allOrders();
        for (Order order : orders) {
            Logger.debug("viewOrders ID" + order.getkey());
        }
        return ok(Json.toJson(orders));
    }

    public Result viewCourierOrders(String courierId) {
        Logger.debug("viewOrders#");
        List<Order> orders = orderManager.allOrders();
        List<Order> courierOrders = new ArrayList<Order>();
        for (Order order : orders) {
            //todo compare objects
            if (order.getCourier() == courierManager.getCourierByID(courierId))
                courierOrders.add(order);
            Logger.debug("viewCourierOrders ID" + order.getkey());
        }
        return ok(Json.toJson(courierOrders));
    }

    public Result receivedOrder(String orderID) {
        Order order = orderManager.getOrderByID(orderID);
        if (!order.isReceived() && order.isValid()){
            order.setReceived(true);
            orderManager.updateOrder(order);
            return ok(Json.toJson(order));
        }
        return ok("Order not delivered or was already delivered");
    }

    public Result getCartItems(String userID) {
        List<Order> order = orderManager.getUserOrders(userID);
        return ok(Json.toJson(order));
    }
}
