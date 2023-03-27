package net.therap.onlinestore.contoller;

import net.therap.onlinestore.entity.User;
import net.therap.onlinestore.exception.IllegalAccessException;
import net.therap.onlinestore.helper.CustomerHelper;
import net.therap.onlinestore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import static net.therap.onlinestore.constant.Constants.*;

/**
 * @author nadimmahmud
 * @since 3/7/23
 */
@Controller
@RequestMapping(CUSTOMER_BASE_URL)
@SessionAttributes(USER)
public class CustomerController {

    private static final String ORDER_HISTORY = "orderHistory";
    private static final String ORDER_LIST_URL = "order-list";
    private static final String ORDER_LIST_VIEW = "customer-order-list";
    private static final String ORDER_HISTORY_URL = "order-history";

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerHelper customerHelper;

    @GetMapping(ORDER_LIST_URL)
    public String showOrderList(@SessionAttribute(ACTIVE_USER) User user, ModelMap modelMap) throws IllegalAccessException {
        customerHelper.checkAccess(user);

        modelMap.put(ORDER_LIST, orderService.findActiveOrdersByCustomer(user));
        modelMap.put(NAV_ITEM, ORDER_LIST);

        return ORDER_LIST_VIEW;
    }

    @GetMapping(ORDER_HISTORY_URL)
    public String showOrderHistory(@SessionAttribute(ACTIVE_USER) User user, ModelMap modelMap) throws IllegalAccessException {
        customerHelper.checkAccess(user);

        modelMap.put(ORDER_LIST, orderService.findDeliveredOrdersByCustomer(user));
        modelMap.put(NAV_ITEM, ORDER_HISTORY);

        return ORDER_LIST_VIEW;
    }
}
