package net.therap.onlinestore.contoller;

import net.therap.onlinestore.entity.OrderStatus;
import net.therap.onlinestore.entity.User;
import net.therap.onlinestore.service.ItemService;
import net.therap.onlinestore.service.OrderService;
import net.therap.onlinestore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpSession;

import static net.therap.onlinestore.constant.Constants.*;

/**
 * @author nadimmahmud
 * @since 3/15/23
 */
@Controller
@RequestMapping(SHOPKEEPER)
public class ShopkeeperController {

    private static final String HOME_URL = "/";
    private static final String HOME_VIEW = "home";
    private static final String SHOPKEEPER_NOTIFICATION_URL = "notification";
    private static final String SHOPKEEPER_NOTIFICATION_VIEW = "shopkeeper-notification";

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @GetMapping(HOME_URL)
    public String shopkeeperHome(ModelMap modelMap) {
        modelMap.put(ITEM_LIST, itemService.findAll());

        return HOME_VIEW;
    }

    @GetMapping(SHOPKEEPER_NOTIFICATION_URL)
    public String showShopkeeperNotification(@SessionAttribute(ACTIVE_USER) User user, ModelMap modelMap) {
        modelMap.put(ORDER_LIST, orderService.findOrdersByOrderStatus(OrderStatus.ORDERED));
        modelMap.put(NAV_ITEM, NOTIFICATION);

        return SHOPKEEPER_NOTIFICATION_VIEW;
    }
}
