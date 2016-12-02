package controllers;

import Utils.DatabaseUtils;
import models.order.Order;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Ahereza on 11/25/16.
 */
public class OrderController extends Controller {
    private DatabaseUtils orderManager = new DatabaseUtils("order");
    private final FormFactory formFactory;

    @Inject
    public OrderController(FormFactory formFactory) {
        this.formFactory = formFactory;
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
