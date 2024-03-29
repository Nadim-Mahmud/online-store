package net.therap.onlinestore.contoller;

import net.therap.onlinestore.entity.AccessType;
import net.therap.onlinestore.entity.OrderStatus;
import net.therap.onlinestore.helper.ShopkeeperHelper;
import net.therap.onlinestore.service.OrderService;
import net.therap.onlinestore.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

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

    @Autowired
    private ShopkeeperHelper shopkeeperHelper;

    @GetMapping(HOME_URL)
    public String showShopkeeperDashBoard(ModelMap modelMap, HttpSession httpSession) {
        shopkeeperHelper.checkAccess(Util.getActiveUser(httpSession), AccessType.VIEW_ALL);
        modelMap.put(ORDER_LIST, orderService.findOrdersByOrderStatus(OrderStatus.ORDERED));

        return SHOPKEEPER_NOTIFICATION_VIEW;
    }
}
