package net.therap.onlinestore.contoller;

import net.therap.onlinestore.entity.*;
import net.therap.onlinestore.helper.OrderHelper;
import net.therap.onlinestore.service.OrderService;
import net.therap.onlinestore.util.Util;
import net.therap.onlinestore.validator.OrderItemValidator;
import org.apache.log4j.Logger;
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

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Locale;
import java.util.Objects;

import static net.therap.onlinestore.constant.Constants.*;

/**
 * @author nadimmahmud
 * @since 3/8/23
 */
@Controller
@RequestMapping({ADMIN_BASE_URL, CUSTOMER_BASE_URL, SHOPKEEPER_BASE_URL, DELIVERY_BASE_URL})
@SessionAttributes(ORDER)
public class OrderController {

    private static final Logger logger = Logger.getLogger(OrderController.class);

    private static final String ALL_ORDER = "all-order";
    private static final String ADMIN_ORDER_LIST = "admin-order-list";
    private static final String NEW_ORDER_REDIRECT_URL = "customer/order";
    private static final String ORDER_FORM_URL = "order";
    private static final String ORDER_FORM_VIEW = "order/order-form";
    private static final String ADD_ORDER_ITEM_URL = "add-item";
    private static final String ORDER_FORM_SAVE_URL = "order/save";
    private static final String ORDER_ID_PARAM = "orderId";
    private static final String REMOVE_ORDER_ITEM_URL = "items/remove";
    private static final String ORDER_ITEM_ID = "orderItemId";
    private static final String NEXT_PAGE = "order/address";
    private static final String ADDRESS_VIEW = "order/address-form";

    private static final String REDIRECT_ORDER_LIST_URL = "customer/order-list";
    private static final String ORDER_READY = "order/ready";
    private static final String ORDER_DELETE_URL = "order/delete";
    private static final String ORDER_CANCEL_URL = "order/cancel";
    private static final String ACCEPT_READY_ORDER = "ready-order/accept";
    private static final String REDIRECT_READY_ORDER_URL = "delivery/";
    private static final String DELIVER_ACCEPTED_ORDER = "delivered";
    private static final String REDIRECT_DELIVERY_ORDER_URL = "delivery/delivery-list";
    private static final String ITEM_DETAILS_VIEW = "item/item-details";
    private static final String NAV_ALL_ORDER = "allOrder";

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderHelper orderHelper;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        webDataBinder.setDisallowedFields("id", "access_status", "version", "created_at", "updated_at");
    }

    @GetMapping(ALL_ORDER)
    public String showAllOrder(ModelMap modelMap, HttpSession httpSession) {
        orderHelper.checkAccess(Util.getActiveUser(httpSession), AccessType.VIEW_ALL);

        modelMap.put(ORDER_LIST, orderService.findAll());
        modelMap.put(NAV_ITEM, NAV_ALL_ORDER);

        return ADMIN_ORDER_LIST;
    }

    @GetMapping(ORDER_FORM_URL)
    public String showOrderForm(@SessionAttribute(value = ORDER, required = false) Order order,
                                @RequestParam(value = ORDER_ID_PARAM, required = false) String orderId,
                                ModelMap modelMap,
                                HttpSession httpSession) {

        order = Objects.nonNull(orderId) ? orderService.findById(Integer.parseInt(orderId)) :
                (Objects.nonNull(order) ? order : new Order());

        orderHelper.orderFormAccess(Util.getActiveUser(httpSession), order);
        orderHelper.populateOrderFormModel(modelMap, order);

        return ORDER_FORM_VIEW;
    }

    @GetMapping(NEXT_PAGE)
    public String showAddressPage(@SessionAttribute(ORDER) Order order,
                                  ModelMap modelMap,
                                  HttpSession httpSession,
                                  RedirectAttributes redirectAttributes) {

        orderHelper.orderFormAccess(Util.getActiveUser(httpSession), order);

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
                               @RequestParam(value = DETAILS_PAGE, required = false) String detailsPage,
                               @Valid @ModelAttribute(ORDER_ITEM) OrderItem orderItem,
                               BindingResult bindingResult,
                               HttpSession httpSession,
                               ModelMap modelMap) {

        orderHelper.orderFormAccess(Util.getActiveUser(httpSession), order);

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

        orderHelper.populateAddOrderItemModel(modelMap, order);

        return ORDER_FORM_VIEW;
    }

    @PostMapping(REMOVE_ORDER_ITEM_URL)
    public String removeOrderItem(@SessionAttribute(ORDER) Order order,
                                  @RequestParam(ORDER_ITEM_ID) int orderItemId,
                                  HttpSession httpSession) {

        orderHelper.orderFormAccess(Util.getActiveUser(httpSession), order);
        order.removeOrderItem(new OrderItem(orderItemId));

        return REDIRECT + NEW_ORDER_REDIRECT_URL;
    }

    @PostMapping(ORDER_FORM_SAVE_URL)
    public String saveOrUpdateOrder(@SessionAttribute(ORDER) Order order,
                                    @Valid @ModelAttribute(ADDRESS) Address address,
                                    BindingResult bindingResult,
                                    SessionStatus sessionStatus,
                                    HttpSession httpSession,
                                    RedirectAttributes redirectAttributes) {

        orderHelper.orderFormAccess(Util.getActiveUser(httpSession), order);

        if (bindingResult.hasErrors()) {
            return ADDRESS_VIEW;
        }

        String successMessage = messageSource.getMessage(order.isNew() ? "success.add" : "success.update", null, Locale.getDefault());
        address.setUser(Util.getActiveUser(httpSession));
        order.setAddress(address);
        order.setStatus(OrderStatus.ORDERED);

        orderService.saveOrUpdate(order);

        logger.info("Placed order for user: " + Util.getActiveUser(httpSession).getFirstName() + " OrderId: " + order.getId());

        sessionStatus.setComplete();
        redirectAttributes.addFlashAttribute(SUCCESS, successMessage);

        return REDIRECT + REDIRECT_ORDER_LIST_URL;
    }

    @PostMapping(ORDER_READY)
    public String markOrderReady(@RequestParam(ORDER_ID_PARAM) int orderId, HttpSession httpSession) {
        orderHelper.checkAccess(Util.getActiveUser(httpSession), AccessType.MARK_ORDER_READY);

        Order order = orderService.findById(orderId);
        order.setStatus(OrderStatus.READY);
        orderService.saveOrUpdate(order);
        logger.info("Order marked ready by: " + Util.getActiveUser(httpSession).getFirstName() + "Order id: " + order.getId());

        return REDIRECT;
    }

    @PostMapping(ACCEPT_READY_ORDER)
    public String getAcceptReadyOrder(@RequestParam(ORDER_ID_PARAM) int orderId, HttpSession httpSession) {
        orderHelper.checkAccess(Util.getActiveUser(httpSession), AccessType.ACCEPT_READY_ORDER);

        Order order = orderService.findById(orderId);
        order.setStatus(OrderStatus.PICKED);
        order.setUser(Util.getActiveUser(httpSession));
        orderService.saveOrUpdate(order);
        logger.info("Order accepted by: " + Util.getActiveUser(httpSession).getFirstName() + "Order id: " + order.getId());

        return REDIRECT + REDIRECT_READY_ORDER_URL;
    }

    @PostMapping(DELIVER_ACCEPTED_ORDER)
    public String markOrderDelivered(@RequestParam(ORDER_ID_PARAM) int orderId, HttpSession httpSession) {
        orderHelper.checkAccess(Util.getActiveUser(httpSession), AccessType.SET_ORDER_DELIVERED);

        Order order = orderService.findById(orderId);
        order.setStatus(OrderStatus.DELIVERED);
        orderService.saveOrUpdate(order);
        logger.info("Order delivered by: " + Util.getActiveUser(httpSession).getFirstName() + "Order id: " + order.getId());

        return REDIRECT + REDIRECT_DELIVERY_ORDER_URL;
    }

    @PostMapping(ORDER_DELETE_URL)
    public String deleteOrder(@RequestParam(ORDER_ID_PARAM) int orderId,
                              HttpSession httpSession,
                              RedirectAttributes redirectAttributes) {

        Order order = orderService.findById(orderId);
        orderHelper.checkOrderDeleteAccess(Util.getActiveUser(httpSession), order);

        if (orderService.isOrderOnProcess(orderId)) {
            redirectAttributes.addFlashAttribute(FAILED, messageSource.getMessage("fail.delete.inUse", null, Locale.getDefault()));
        } else {
            orderService.delete(orderId);
            logger.info("Order deleted by: " + Util.getActiveUser(httpSession).getFirstName() + "Order id: " + orderId);
            redirectAttributes.addFlashAttribute(SUCCESS, messageSource.getMessage("success.delete", null, Locale.getDefault()));
        }

        return REDIRECT + REDIRECT_ORDER_LIST_URL;
    }

    @PostMapping(ORDER_CANCEL_URL)
    public String cancelOrder(@RequestParam(ORDER_ID_PARAM) int orderId,
                              HttpSession httpSession,
                              RedirectAttributes redirectAttributes) {

        orderHelper.checkAccess(Util.getActiveUser(httpSession), AccessType.CANCEL);

        if (orderService.isOrderOnProcess(orderId)) {
            redirectAttributes.addFlashAttribute(FAILED, messageSource.getMessage("fail.cancel.inUse", null, Locale.getDefault()));
        } else {
            orderService.cancel(orderId);
            logger.info("Order canceled by: " + Util.getActiveUser(httpSession).getFirstName() + "Order id: " + orderId);
            redirectAttributes.addFlashAttribute(SUCCESS, messageSource.getMessage("success.cancel", null, Locale.getDefault()));
        }

        return REDIRECT;
    }
}
