package net.therap.onlinestore.contoller;

import net.therap.onlinestore.entity.User;
import net.therap.onlinestore.service.CategoryService;
import net.therap.onlinestore.service.ItemService;
import net.therap.onlinestore.service.OrderService;
import net.therap.onlinestore.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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
    private static final String HOME_VIEW = "home";
    private static final String ORDER_LIST_URL = "order-list";
    private static final String ORDER_LIST_VIEW = "customer-order-list";
    private static final String REDIRECT_ORDER_HISTORY_URL = "customer/order-history";
    private static final String ORDER_HISTORY_URL = "order-history";

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

    @GetMapping(ORDER_LIST_URL)
    public String showOrderList(@SessionAttribute(ACTIVE_USER) User user, ModelMap modelMap) {
        modelMap.put(ORDER_LIST, orderService.findActiveOrdersByCustomer(user));
        modelMap.put(NAV_ITEM, ORDER_LIST);

        return ORDER_LIST_VIEW;
    }

    @GetMapping(ORDER_HISTORY_URL)
    public String showOrderHistory(@SessionAttribute(ACTIVE_USER) User user, ModelMap modelMap) {
        modelMap.put(ORDER_LIST, orderService.findDeliveredOrdersByCustomer(user));
        modelMap.put(NAV_ITEM, ORDER_HISTORY);

        return ORDER_LIST_VIEW;
    }
}
