package net.therap.onlinestore.helper;

import net.therap.onlinestore.entity.*;
import net.therap.onlinestore.exception.IllegalAccessException;
import net.therap.onlinestore.service.CategoryService;
import net.therap.onlinestore.service.ItemService;
import net.therap.onlinestore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static net.therap.onlinestore.constant.Constants.*;

/**
 * @author nadimmahmud
 * @since 3/26/23
 */
@Component
public class OrderHelper {

    @Autowired
    private ItemService itemService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private OrderService orderService;

    public void orderFormAccess(User user, Order order) throws IllegalAccessException {

        if (Objects.isNull(user) || !UserType.CUSTOMER.equals(user.getType())) {
            throw new IllegalAccessException();
        }

        if (order.isNew()) {
            return;
        }

        if (!OrderStatus.ORDERED.equals(order.getStatus()) || !orderService.isAccessibleOrderByUser(user, order)) {
            throw new IllegalAccessException();
        }
    }

    public void checkOrderDeleteAccess(User user, Order order) throws IllegalAccessException {

        if (Objects.isNull(user) || !UserType.CUSTOMER.equals(user.getType())) {
            throw new IllegalAccessException();
        }

        if (!orderService.isAccessibleOrderByUser(user, order)) {
            throw new IllegalAccessException();
        }
    }

    public void checkAccess(User user, AccessType accessType) throws IllegalAccessException {

        if (Objects.isNull(user)) {
            throw new IllegalAccessException();
        }

        if (AccessType.VIEW_ALL.equals(accessType) && !UserType.ADMIN.equals(user.getType())) {

            throw new IllegalAccessException();
        } else if (Arrays.asList(AccessType.MARK_ORDER_READY, AccessType.CANCEL).contains(accessType)
                && !UserType.SHOPKEEPER.equals(user.getType())) {

            throw new IllegalAccessException();
        } else if (Arrays.asList(AccessType.ACCEPT_READY_ORDER, AccessType.SET_ORDER_DELIVERED).contains(accessType)
                && !UserType.DELIVERYMAN.equals(user.getType())) {

            throw new IllegalAccessException();
        }
    }


    public void populateOrderFormModel(ModelMap modelMap, Order order) {
        modelMap.put(ORDER, order);
        modelMap.put(ORDER_ITEM_LIST, order.getOrderItemList());
        modelMap.put(ORDER_ITEM, new OrderItem());
        modelMap.put(ITEM_LIST, itemService.findAll());
        modelMap.put(CATEGORY_LIST, categoryService.findAll());
        modelMap.put(NAV_ITEM, ORDER_FORM);
    }

    public void populateAddOrderItemModel(ModelMap modelMap, Order order) {
        modelMap.put(ORDER_ITEM_LIST, order.getOrderItemList());
        modelMap.put(NAV_ITEM, ORDER_FORM);
        modelMap.put(CATEGORY_LIST, categoryService.findAll());
    }

    public List<Order> calculatePriceOfOrderList(List<Order> orderList) {

        for (Order order : orderList) {
            double price = 0;

            for (OrderItem orderItem : order.getOrderItemList()) {
                price += orderItem.getItem().getPrice() * orderItem.getQuantity();
            }

            order.setPrice(price);
        }

        return orderList;
    }
}
