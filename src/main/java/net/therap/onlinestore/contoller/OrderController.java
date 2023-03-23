package net.therap.onlinestore.contoller;

import net.therap.onlinestore.entity.*;
import net.therap.onlinestore.exception.IllegalAccessException;
import net.therap.onlinestore.formatter.ItemFormatter;
import net.therap.onlinestore.formatter.StringToIntgerFormatter;
import net.therap.onlinestore.helper.AccessCheckHelper;
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
@RequestMapping({CUSTOMER_BASE_URL, SHOPKEEPER_BASE_URL, DELIVERY_BASE_URL})
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
    private static final String ORDER_READY = "order/ready";
    private static final String ORDER_DELETE_URL = "order/delete";
    private static final String ORDER_CANCEL_URL = "order/cancel";
    private static final String ACCEPT_READY_ORDER = "ready-order/accept";
    private static final String REDIRECT_READY_ORDER_URL = "delivery/ready-order";
    private static final String DELIVER_ACCEPTED_ORDER = "delivered";
    private static final String REDIRECT_DELIVERY_ORDER_URL = "delivery/delivery-list";
    private static final String ITEM_DETAILS_VIEW = "item-details";

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

    @Autowired
    private StringToIntgerFormatter stringToIntgerFormatter;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        webDataBinder.addCustomFormatter(itemFormatter);
        webDataBinder.addCustomFormatter(stringToIntgerFormatter, "quantity");
    }

    @GetMapping(ORDER_FORM_URL)
    public String showOrderForm(@SessionAttribute(ACTIVE_USER) User user,
                                @SessionAttribute(value = ORDER, required = false) Order order,
                                @RequestParam(value = ORDER_ID_PARAM, required = false) String orderId,
                                ModelMap modelMap) throws IllegalAccessException {
        order = Objects.nonNull(orderId) ? orderService.findById(Integer.parseInt(orderId)) :
                (Objects.nonNull(order) ? order : new Order());

        if (!orderService.isAccessible(user, order)) {
            throw new IllegalAccessException();
        }

        modelMap.put(ORDER, order);
        modelMap.put(ORDER_ITEM_LIST, order.getOrderItemList());
        modelMap.put(ORDER_ITEM, new OrderItem());
        modelMap.put(ITEM_LIST, itemService.findAll());
        modelMap.put(CATEGORY_LIST, categoryService.findAll());
        modelMap.put(NAV_ITEM, ORDER_FORM);

        return ORDER_FORM_VIEW;
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

    @PostMapping(ADD_ORDER_ITEM_URL)
    public String addOrderItem(@SessionAttribute(ORDER) Order order,
                               @Valid @ModelAttribute(ORDER_ITEM) OrderItem orderItem,
                               @RequestParam(value = DETAILS_PAGE, required = false) String detailsPage,
                               BindingResult bindingResult,
                               ModelMap modelMap) {
        OrderItemValidator.validate(order.getOrderItemList(), orderItem, bindingResult);

        if (!bindingResult.hasErrors()) {
            order.addOrderItem(orderItem);
        }

        if (Objects.nonNull(detailsPage)) {

            if (bindingResult.hasErrors()) {
                modelMap.put(FAILED, messageSource.getMessage("failed.add.item", null, Locale.getDefault()));
            } else {
                modelMap.put(SUCCESS, messageSource.getMessage("success.add", null, Locale.getDefault()));
            }

            return ITEM_DETAILS_VIEW;
        }

        modelMap.put(ORDER_ITEM_LIST, order.getOrderItemList());
        modelMap.put(NAV_ITEM, ORDER_FORM);
        modelMap.put(CATEGORY_LIST, categoryService.findAll());

        return ORDER_FORM_VIEW;
    }

    @PostMapping(REMOVE_ORDER_ITEM_URL)
    public String removeOrderItem(@SessionAttribute(ORDER) Order order,
                                  @RequestParam(ORDER_ITEM_ID) int orderItemId) {
        order.removeOrderItem(new OrderItem(orderItemId));

        return REDIRECT + NEW_ORDER_REDIRECT_URL;
    }

    @PostMapping(ORDER_FORM_SAVE_URL)
    public String saveOrUpdateOrder(@SessionAttribute(ACTIVE_USER) User user,
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

    @PostMapping(ORDER_READY)
    public String markOrderReady(@SessionAttribute(ACTIVE_USER) User user,
                                 @RequestParam(ORDER_ID_PARAM) int orderId) throws Exception {
        Order order = orderService.findById(orderId);
        order.setStatus(OrderStatus.READY);
        AccessCheckHelper.check(user, AccessType.UPDATE, order);
        orderService.saveOrUpdate(order);

        return REDIRECT;
    }

    @PostMapping(ACCEPT_READY_ORDER)
    public String getAcceptReadyOrder(@SessionAttribute(ACTIVE_USER) User user,
                                      @RequestParam(ORDER_ID_PARAM) int orderId) throws Exception {
        Order order = orderService.findById(orderId);
        order.setStatus(OrderStatus.PICKED);
        order.setUser(user);
        AccessCheckHelper.check(user, AccessType.UPDATE, order);
        orderService.saveOrUpdate(order);

        return REDIRECT + REDIRECT_READY_ORDER_URL;
    }

    @PostMapping(DELIVER_ACCEPTED_ORDER)
    public String markOrderDelivered(@SessionAttribute(ACTIVE_USER) User user,
                                     @RequestParam(ORDER_ID_PARAM) int orderId) throws Exception {
        Order order = orderService.findById(orderId);
        order.setStatus(OrderStatus.DELIVERED);
        AccessCheckHelper.check(user, AccessType.UPDATE, order);
        orderService.saveOrUpdate(order);

        return REDIRECT + REDIRECT_DELIVERY_ORDER_URL;
    }

    @PostMapping(ORDER_DELETE_URL)
    public String deleteOrder(@SessionAttribute(ACTIVE_USER) User user,
                              @RequestParam(ORDER_ID_PARAM) int orderId,
                              RedirectAttributes redirectAttributes) throws Exception {
        Order order = orderService.findById(orderId);
        AccessCheckHelper.check(user, AccessType.DELETE, order);

        if (orderService.isOrderOnProcess(orderId)) {
            redirectAttributes.addFlashAttribute(FAILED, messageSource.getMessage("fail.delete.inUse", null, Locale.getDefault()));
        } else {
            orderService.delete(orderId);
            redirectAttributes.addFlashAttribute(SUCCESS, messageSource.getMessage("success.delete", null, Locale.getDefault()));
        }

        return REDIRECT + REDIRECT_ORDER_LIST_URL;
    }

    @PostMapping(ORDER_CANCEL_URL)
    public String cancelOrder(@SessionAttribute(ACTIVE_USER) User user,
                              @RequestParam(ORDER_ID_PARAM) int orderId,
                              RedirectAttributes redirectAttributes) throws Exception {
        Order order = orderService.findById(orderId);
        AccessCheckHelper.check(user, AccessType.CANCEL, order);

        if (orderService.isOrderOnProcess(orderId)) {
            redirectAttributes.addFlashAttribute(FAILED, messageSource.getMessage("fail.cancel.inUse", null, Locale.getDefault()));
        } else {
            orderService.cancel(orderId);
            redirectAttributes.addFlashAttribute(SUCCESS, messageSource.getMessage("success.cancel", null, Locale.getDefault()));
        }

        return REDIRECT;
    }
}
