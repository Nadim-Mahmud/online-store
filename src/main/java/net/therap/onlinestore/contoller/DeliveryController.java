package net.therap.onlinestore.contoller;

import net.therap.onlinestore.entity.User;
import net.therap.onlinestore.exception.IllegalAccessException;
import net.therap.onlinestore.helper.DeliveryHelper;
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
@RequestMapping(DELIVERY_BASE_URL)
public class DeliveryController {

    private static final String DELIVERY_ORDER_VIEW = "delivery-order-list";
    private static final String DELIVERY_ORDER_URL = "delivery-list";
    private static final String DELIVERY_HISTORY_URL = "delivery-history";

    @Autowired
    private DeliveryHelper deliveryHelper;

    @GetMapping(HOME_URL)
    public String showReadyOrdersList(@SessionAttribute(ACTIVE_USER) User user, ModelMap modelMap) throws IllegalAccessException {
        deliveryHelper.checkAccess(user);
        deliveryHelper.populateReadyOrderListModel(modelMap);

        return DELIVERY_ORDER_VIEW;
    }

    @GetMapping(DELIVERY_ORDER_URL)
    public String showDeliveryOrderList(@SessionAttribute(ACTIVE_USER) User user, ModelMap modelMap) throws IllegalAccessException {
        deliveryHelper.checkAccess(user);
        deliveryHelper.populateDeliveryOrderListModel(modelMap, user);

        return DELIVERY_ORDER_VIEW;
    }

    @GetMapping(DELIVERY_HISTORY_URL)
    public String showDeliveryOrderHistory(@SessionAttribute(ACTIVE_USER) User user, ModelMap modelMap) throws IllegalAccessException {
        deliveryHelper.checkAccess(user);
        deliveryHelper.populateDeliveryHistoryModel(modelMap, user);

        return DELIVERY_ORDER_VIEW;
    }
}
