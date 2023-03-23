package net.therap.onlinestore.helper;

import net.therap.onlinestore.entity.Order;
import net.therap.onlinestore.entity.OrderItem;
import net.therap.onlinestore.entity.User;
import net.therap.onlinestore.entity.UserType;
import net.therap.onlinestore.exception.IllegalAccessException;
import net.therap.onlinestore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author nadimmahmud
 * @since 3/19/23
 */
@Component
public class OrderHelper {

    @Autowired
    private OrderService orderService;

    public void orderFormAccess(User user, Order order) throws IllegalAccessException {

        if(!UserType.CUSTOMER.equals(user.getType())){
            throw new IllegalAccessException();
        }

        if (!orderService.isAccessible(user, order)) {
            throw new IllegalAccessException();
        }
    }

    public void allOrderAccess(User user) throws IllegalAccessException {

        if(!UserType.ADMIN.equals(user.getType())){
            throw new IllegalAccessException();
        }
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
