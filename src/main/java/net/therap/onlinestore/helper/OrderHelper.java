package net.therap.onlinestore.helper;

import net.therap.onlinestore.entity.Order;
import net.therap.onlinestore.entity.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author nadimmahmud
 * @since 3/19/23
 */
@Component
public class OrderHelper {

    public List<Order> calculatePriceOfOrderList(List<Order> orderList){

        for (Order order : orderList){
            double price = 0;

            for (OrderItem orderItem : order.getOrderItemList()){
                price += orderItem.getItem().getPrice() * orderItem.getQuantity();
            }

            order.setPrice(price);
        }

        return orderList;
    }
}
