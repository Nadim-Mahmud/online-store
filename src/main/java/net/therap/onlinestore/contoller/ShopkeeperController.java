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

import static net.therap.onlinestore.constant.Constants.*;

/**
 * @author nadimmahmud
 * @since 3/15/23
 */
@Controller
@RequestMapping(SHOPKEEPER)
public class ShopkeeperController {

    private static final String HOME_VIEW = "home";
    private static final String SHOPKEEPER_NOTIFICATION_URL = "notification";
    private static final String SHOPKEEPER_NOTIFICATION_VIEW = "shopkeeper-notification";

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

    @GetMapping(SHOPKEEPER_NOTIFICATION_URL)
    public String showShopkeeperNotification(@SessionAttribute(ACTIVE_USER) User user, ModelMap modelMap) {
        modelMap.put(ORDER_LIST, orderService.findOrdersByOrderStatus(OrderStatus.ORDERED));
        modelMap.put(NAV_ITEM, NOTIFICATION);

        return SHOPKEEPER_NOTIFICATION_VIEW;
    }
}
