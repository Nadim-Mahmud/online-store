package net.therap.onlinestore.contoller;

import net.therap.onlinestore.entity.Order;
import net.therap.onlinestore.entity.OrderItem;
import net.therap.onlinestore.service.CategoryService;
import net.therap.onlinestore.service.ItemService;
import net.therap.onlinestore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import static net.therap.onlinestore.constant.Constants.*;

/**
 * @author nadimmahmud
 * @since 3/8/23
 */
@Controller
@RequestMapping(CUSTOMER_BASE_URL)
@SessionAttributes(ORDER)
public class OrderController {

    private static final String ORDER_REDIRECT_URL = "customer/orders";
    private static final String ORDERS_URL = "orders";
    private static final String ORDER_VIEW = "my-orders";
    private static final String ORDER_FORM_URL = "order";
    private static final String ORDER_FORM_VIEW = "order-form";
    private static final String ORDER_FORM_SAVE_URL = "new-order/save";
    private static final String ORDER_ID_PARAM = "orderId";
    private static final String ORDER_CANCEL_URL = "order/cancel";

    @Autowired
    private OrderService orderService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private CategoryService categoryService;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping(ORDER_FORM_URL)
    public String showOrderForm(@RequestParam(value = ORDER_ID_PARAM, required = false) String orderId, ModelMap modelMap){
        Order order = Objects.nonNull(orderId) ? orderService.findById(Integer.parseInt(orderId)) : new Order();
        modelMap.put(ORDER_ITEM_LIST, order.getOrderItemList());
        modelMap.put(ORDER_ITEM, new OrderItem());
        modelMap.put(ITEM_LIST, itemService.findAll());
        modelMap.put(CATEGORY_LIST, categoryService.findAll());

        return ORDER_FORM_VIEW;
    }
}
