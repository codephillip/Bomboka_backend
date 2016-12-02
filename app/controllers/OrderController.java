package controllers;

import Utils.DatabaseUtils;
import Utils.Utility;
import models.order.Order;
import org.bson.types.ObjectId;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 * Created by Codephillip on 12/02/16.
 */
public class OrderController extends Controller {
    private DatabaseUtils orderManager = new DatabaseUtils("order");
    private final FormFactory formFactory;

    @Inject
    public OrderController(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    public Result changeCourier(String orderID) {
        Form<Order> orderForm = formFactory.form(Order.class).bindFromRequest();
        Map<String, String> data = orderForm.data();
        Order order = orderManager.getOrderByID(orderID);
        Logger.debug("CHANGE ID1-" + order.getCourier().toString());
        Logger.debug("CHANGE ID2-" + data.get("courier"));
        if (Utility.isNotEmpty(data.get("courier"))) {
            order.setCourier(new ObjectId(data.get("courier")));
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
        Order obj = order.get();
        orderManager.saveOrder(obj);
        return ok(Json.toJson(obj)) ;
    }

    public Result viewOrders() {
        Logger.debug("viewOrders#");
        List<Order> orders = orderManager.allOrders();
        for (Order order : orders) {
            Logger.debug("viewOrders ID" + order.get_id().toString());
        }
        return ok(Json.toJson(orders));
    }
}
