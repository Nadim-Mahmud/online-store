package net.therap.onlinestore.contoller;

import net.therap.onlinestore.entity.OrderStatus;
import net.therap.onlinestore.entity.User;
import net.therap.onlinestore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import static net.therap.onlinestore.constant.Constants.*;

/**
 * @author nadimmahmud
 * @since 3/15/23
 */
@Controller
@RequestMapping(SHOPKEEPER)
public class ShopkeeperController {

    private static final String SHOPKEEPER_NOTIFICATION_VIEW = "shopkeeper-dashboard";

    @Autowired
    private OrderService orderService;

    @GetMapping(HOME_URL)
    public String showShopkeeperDashBoard(@SessionAttribute(ACTIVE_USER) User user, ModelMap modelMap) {
        modelMap.put(ORDER_LIST, orderService.findOrdersByOrderStatus(OrderStatus.ORDERED));

        return SHOPKEEPER_NOTIFICATION_VIEW;
    }
}
