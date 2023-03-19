package net.therap.onlinestore.contoller;

import net.therap.onlinestore.entity.OrderStatus;
import net.therap.onlinestore.entity.User;
import net.therap.onlinestore.service.CategoryService;
import net.therap.onlinestore.service.ItemService;
import net.therap.onlinestore.service.OrderService;
import net.therap.onlinestore.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.persistence.Table;

import static net.therap.onlinestore.constant.Constants.*;
import static net.therap.onlinestore.constant.Constants.HOME_URL;

/**
 * @author nadimmahmud
 * @since 3/15/23
 */
@Controller
@RequestMapping(DELIVERY_BASE_URL)
public class DeliveryController {

    private static final String HOME_URL = "/";
    private static final String HOME_VIEW = "home";
    private static final String READY_ORDER_URL = "ready-order";
    private static final String DELIVERY_ORDER_VIEW = "delivery-order-list";
    private static final String DELIVERY_ORDER_URL = "delivery-list";
    private static final String DELIVERY_HISTORY_URL = "delivery-history";
    private static final String DELIVERY_HISTORY = "deliveryHistory";

    @Autowired
    private ItemService itemService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @GetMapping(HOME_URL)
    public String showHome(@RequestParam(value = CATEGORY_ID, required = false) String categoryId, @RequestParam(value = TAG_ID, required = false) String tagId, ModelMap modelMap) {
        modelMap.put(CATEGORY_LIST, categoryService.findAll());
        modelMap.put(TAG_LIST, tagService.findAll());
        modelMap.put(ITEM_LIST, itemService.filter(categoryId, tagId));
        modelMap.put(CATEGORY_ID, categoryId);
        modelMap.put(TAG_ID, tagId);

        return HOME_VIEW;
    }

    @GetMapping(READY_ORDER_URL)
    public String showReadyOrdersList(ModelMap modelMap) {
        modelMap.put(ORDER_LIST, orderService.findOrdersByOrderStatus(OrderStatus.READY));
        modelMap.put(NAV_ITEM, READY_ORDER);
        modelMap.put(BUTTON, READY_ORDER);

        return DELIVERY_ORDER_VIEW;
    }

    @GetMapping(DELIVERY_ORDER_URL)
    public String showDeliveryOrderList(@SessionAttribute(ACTIVE_USER) User user, ModelMap modelMap) {
        modelMap.put(ORDER_LIST, orderService.findOrdersNotDeliveredByDeliveryMan(user));
        modelMap.put(NAV_ITEM, DELIVERY_LIST);
        modelMap.put(BUTTON, DELIVERY_LIST);

        return DELIVERY_ORDER_VIEW;
    }

    @GetMapping(DELIVERY_HISTORY_URL)
    public String showDeliveryOrderHistory(@SessionAttribute(ACTIVE_USER) User user, ModelMap modelMap) {
        modelMap.put(ORDER_LIST, orderService.findDeliveredOrderByUser(user));
        modelMap.put(NAV_ITEM, DELIVERY_HISTORY);
        modelMap.put(BUTTON, DELIVERY_HISTORY);

        return DELIVERY_ORDER_VIEW;
    }
}
