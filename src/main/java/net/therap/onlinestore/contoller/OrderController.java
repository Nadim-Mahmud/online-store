package net.therap.onlinestore.contoller;

import net.therap.onlinestore.entity.*;
import net.therap.onlinestore.formatter.ItemFormatter;
import net.therap.onlinestore.service.CategoryService;
import net.therap.onlinestore.service.ItemService;
import net.therap.onlinestore.service.OrderService;
import net.therap.onlinestore.validator.OrderItemValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Locale;
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

    private static final String NEW_ORDER_REDIRECT_URL = "customer/order";
    private static final String ORDER_FORM_URL = "order";
    private static final String ORDER_FORM_VIEW = "order-form";
    private static final String ADD_ORDER_ITEM_URL = "add-item";
    private static final String ORDER_FORM_SAVE_URL = "order/save";
    private static final String ORDER_ID_PARAM = "orderId";
    private static final String REMOVE_ORDER_ITEM_URL = "items/remove";
    private static final String ORDER_ITEM_ID = "orderItemId";
    private static final String NEXT_PAGE = "order/address";
    private static final String ADDRESS_VIEW = "address-form";

    private static final String REDIRECT_ORDER_LIST_URL = "customer/order-list";
    private static final String ORDER_LIST_URL = "order-list";
    private static final String ORDER_LIST_VIEW = "order-list";
    private static final String ORDER_CANCEL_URL = "order/cancel";

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ItemFormatter itemFormatter;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        webDataBinder.addCustomFormatter(itemFormatter);
    }

    @GetMapping(ORDER_FORM_URL)
    public String showOrderForm(@SessionAttribute(value = ORDER, required = false) Order order,
                                @RequestParam(value = ORDER_ID_PARAM, required = false) String orderId,
                                ModelMap modelMap) {
        order = Objects.nonNull(orderId) ? orderService.findById(Integer.parseInt(orderId)) :
                (Objects.nonNull(order) ? order : new Order());
        modelMap.put(ORDER, order);
        modelMap.put(ORDER_ITEM_LIST, order.getOrderItemList());
        modelMap.put(ORDER_ITEM, new OrderItem());
        modelMap.put(ITEM_LIST, itemService.findAll());
        modelMap.put(CATEGORY_LIST, categoryService.findAll());
        modelMap.put(NAV_ITEM, ORDER_FORM);

        return ORDER_FORM_VIEW;
    }

    @PostMapping(ADD_ORDER_ITEM_URL)
    public String addOrderItem(@SessionAttribute(ORDER) Order order,
                               @Valid @ModelAttribute(ORDER_ITEM) OrderItem orderItem,
                               BindingResult bindingResult,
                               ModelMap modelMap) {

        modelMap.put(ITEM_LIST, itemService.findAvailableItems());
        modelMap.put(ORDER_ITEM_LIST, order.getOrderItemList());
        modelMap.put(NAV_ITEM, ORDER_FORM);

        OrderItemValidator.validate(order.getOrderItemList(), orderItem, bindingResult);

        if (!bindingResult.hasErrors()) {
            order.addOrderItem(orderItem);
        }

        return ORDER_FORM_VIEW;
    }

    @PostMapping(REMOVE_ORDER_ITEM_URL)
    public String removeOrderItem(@SessionAttribute(ORDER) Order order,
                                  @RequestParam(ORDER_ITEM_ID) int orderItemId) {
        order.removeOrderItem(new OrderItem(orderItemId));

        return REDIRECT + NEW_ORDER_REDIRECT_URL;
    }

    @GetMapping(NEXT_PAGE)
    public String showAddressPage(@SessionAttribute(ORDER) Order order,
                                  ModelMap modelMap,
                                  RedirectAttributes redirectAttributes) {

        if (order.getOrderItemList().isEmpty()) {
            redirectAttributes.addFlashAttribute(EMPTY_LIST, EMPTY_LIST);

            return REDIRECT + NEW_ORDER_REDIRECT_URL;
        }

        Address address = order.isNew() ? new Address() : order.getAddress();
        modelMap.put(ADDRESS, address);
        modelMap.put(NAV_ITEM, ORDER_FORM);

        return ADDRESS_VIEW;
    }

    @PostMapping(ORDER_FORM_SAVE_URL)
    public String saveOrUpdateResOrder(@SessionAttribute(ACTIVE_USER) User user,
                                       @SessionAttribute(ORDER) Order order,
                                       @Valid @ModelAttribute(ADDRESS) Address address,
                                       BindingResult bindingResult,
                                       SessionStatus sessionStatus,
                                       RedirectAttributes redirectAttributes) throws Exception {

        if (bindingResult.hasErrors()) {
            return ADDRESS_VIEW;
        }

        String successMessage = messageSource.getMessage(order.isNew() ? "success.add" : "success.update", null, Locale.getDefault());

        address.setUser(user);
        order.setAddress(address);
        order.setStatus(OrderStatus.ORDERED);
        orderService.saveOrUpdate(order);
        sessionStatus.setComplete();
        redirectAttributes.addFlashAttribute(SUCCESS, successMessage);

        return REDIRECT + REDIRECT_ORDER_LIST_URL;
    }

    @GetMapping(ORDER_LIST_URL)
    public String showOrderList(@SessionAttribute(ACTIVE_USER) User user,
                                ModelMap modelMap) {
        modelMap.put(ORDER_LIST, orderService.findOrdersByCustomer(user));

        return ORDER_LIST_VIEW;
    }

    @PostMapping(ORDER_CANCEL_URL)
    public String cancelOrder(@RequestParam(ORDER_ID_PARAM) int orderId, RedirectAttributes redirectAttributes) throws Exception {

        if (orderService.isOrderOnProcess(orderId)) {
            redirectAttributes.addFlashAttribute(FAILED, messageSource.getMessage("fail.cancel.inUse", null, Locale.getDefault()));
        } else {
            orderService.cancel(orderId);
            redirectAttributes.addFlashAttribute(SUCCESS, messageSource.getMessage("success.cancel", null, Locale.getDefault()));
        }

        return REDIRECT + REDIRECT_ORDER_LIST_URL;
    }
}
