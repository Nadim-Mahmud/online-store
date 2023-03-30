package net.therap.onlinestore.contoller;

import net.therap.onlinestore.helper.CustomerHelper;
import net.therap.onlinestore.service.OrderService;
import net.therap.onlinestore.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;

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
    public String showOrderList(ModelMap modelMap, HttpSession httpSession) {
        customerHelper.checkAccess(Util.getActiveUser(httpSession));

        modelMap.put(ORDER_LIST, orderService.findActiveOrdersByCustomer(Util.getActiveUser(httpSession)));
        modelMap.put(NAV_ITEM, ORDER_LIST);

        return ORDER_LIST_VIEW;
    }

    @GetMapping(ORDER_HISTORY_URL)
    public String showOrderHistory(ModelMap modelMap, HttpSession httpSession) {
        customerHelper.checkAccess(Util.getActiveUser(httpSession));

        modelMap.put(ORDER_LIST, orderService.findDeliveredOrdersByCustomer(Util.getActiveUser(httpSession)));
        modelMap.put(NAV_ITEM, ORDER_HISTORY);

        return ORDER_LIST_VIEW;
    }
}
