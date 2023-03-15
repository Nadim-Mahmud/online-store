package net.therap.onlinestore.contoller;

import net.therap.onlinestore.entity.OrderStatus;
import net.therap.onlinestore.entity.User;
import net.therap.onlinestore.service.ItemService;
import net.therap.onlinestore.service.OrderService;
import net.therap.onlinestore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static net.therap.onlinestore.constant.Constants.*;

/**
 * @author nadimmahmud
 * @since 3/15/23
 */
@Controller
@RequestMapping(DELIVERY_BASE_URL)
public class DeliveryController {

    private static final String HOME_URL = "/";
    private static final String HOME_VIEW = "home";
    private static final String REDIRECT_READY_ORDER_URL = "delivery/ready-order";
    private static final String READY_ORDER_URL = "ready-order";
    private static final String DELIVERY_ORDER_VIEW = "delivery-order-list";
    private static final String DELIVERY_ORDER_URL = "delivery-list";

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @GetMapping(HOME_URL)
    public String deliveryHome(HttpSession httpSession, ModelMap modelMap) {
        httpSession.setAttribute(ACTIVE_USER, userService.findById(17));
        modelMap.put(ITEM_LIST, itemService.findAll());

        return HOME_VIEW;
    }

    @GetMapping(READY_ORDER_URL)
    public String showReadyOrdersList(ModelMap modelMap) {
        modelMap.put(ORDER_LIST, orderService.findOrdersByOrderStatus(OrderStatus.READY));
        modelMap.put(NAV_ITEM, READY_ORDER);

        return DELIVERY_ORDER_VIEW;
    }

    @GetMapping(DELIVERY_ORDER_URL)
    public String showDeliveryOrderList(@SessionAttribute(ACTIVE_USER) User user, ModelMap modelMap) {
        modelMap.put(ORDER_LIST, orderService.findOrdersNotDeliveredByDeliveryMan(user));
        modelMap.put(NAV_ITEM, DELIVERY_LIST);

        return DELIVERY_ORDER_VIEW;
    }
}
