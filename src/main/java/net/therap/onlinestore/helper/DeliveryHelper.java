package net.therap.onlinestore.helper;

import net.therap.onlinestore.entity.OrderStatus;
import net.therap.onlinestore.entity.User;
import net.therap.onlinestore.entity.UserType;
import net.therap.onlinestore.exception.IllegalAccessException;
import net.therap.onlinestore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import java.util.Objects;

import static net.therap.onlinestore.constant.Constants.*;

/**
 * @author nadimmahmud
 * @since 3/26/23
 */
@Component
public class DeliveryHelper {

    private static final String DELIVERY_HISTORY = "deliveryHistory";

    @Autowired
    private OrderService orderService;

    public void checkAccess(User user) throws IllegalAccessException {

        if (Objects.isNull(user) || !UserType.DELIVERYMAN.equals(user.getType())) {

            throw new IllegalAccessException();
        }
    }

    public void populateReadyOrderListModel(ModelMap modelMap) {
        modelMap.put(ORDER_LIST, orderService.findOrdersByOrderStatus(OrderStatus.READY));
        modelMap.put(NAV_ITEM, READY_ORDER);
        modelMap.put(BUTTON, READY_ORDER);
    }

    public void populateDeliveryOrderListModel(ModelMap modelMap, User user) {
        modelMap.put(ORDER_LIST, orderService.findOrdersNotDeliveredByDeliveryMan(user));
        modelMap.put(NAV_ITEM, DELIVERY_LIST);
        modelMap.put(BUTTON, DELIVERY_LIST);
    }

    public void populateDeliveryHistoryModel(ModelMap modelMap, User user) {
        modelMap.put(ORDER_LIST, orderService.findDeliveredOrderByUser(user));
        modelMap.put(NAV_ITEM, DELIVERY_HISTORY);
        modelMap.put(BUTTON, DELIVERY_HISTORY);
    }
}
