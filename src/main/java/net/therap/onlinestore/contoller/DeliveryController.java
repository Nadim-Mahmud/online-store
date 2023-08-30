package net.therap.onlinestore.contoller;

import net.therap.onlinestore.constant.Constants;
import net.therap.onlinestore.helper.DeliveryHelper;
import net.therap.onlinestore.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @author nadimmahmud
 * @since 3/15/23
 */
@Controller
@RequestMapping(Constants.DELIVERY_BASE_URL)
public class DeliveryController {

    private static final String DELIVERY_ORDER_VIEW = "delivery-order-list";
    private static final String DELIVERY_ORDER_URL = "delivery-list";
    private static final String DELIVERY_HISTORY_URL = "delivery-history";

    @Autowired
    private DeliveryHelper deliveryHelper;

    @GetMapping(Constants.HOME_URL)
    public String showReadyOrdersList(ModelMap modelMap, HttpSession httpSession) {
        deliveryHelper.checkAccess(Util.getActiveUser(httpSession));
        deliveryHelper.populateReadyOrderListModel(modelMap);

        return DELIVERY_ORDER_VIEW;
    }

    @GetMapping(DELIVERY_ORDER_URL)
    public String showDeliveryOrderList(ModelMap modelMap, HttpSession httpSession) {
        deliveryHelper.checkAccess(Util.getActiveUser(httpSession));
        deliveryHelper.populateDeliveryOrderListModel(modelMap, Util.getActiveUser(httpSession));

        return DELIVERY_ORDER_VIEW;
    }

    @GetMapping(DELIVERY_HISTORY_URL)
    public String showDeliveryOrderHistory(ModelMap modelMap, HttpSession httpSession) {
        deliveryHelper.checkAccess(Util.getActiveUser(httpSession));
        deliveryHelper.populateDeliveryHistoryModel(modelMap, Util.getActiveUser(httpSession));

        return DELIVERY_ORDER_VIEW;
    }
}
