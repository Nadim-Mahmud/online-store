package net.therap.onlinestore.service;

import net.therap.onlinestore.entity.*;
import net.therap.onlinestore.helper.OrderHelper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author nadimmahmud
 * @since 3/5/23
 */
@Repository
public class OrderService {

    private final OrderHelper orderHelper;

    @PersistenceContext
    private EntityManager entityManager;

    public OrderService() {
        orderHelper = new OrderHelper();
    }

    public List<Order> findAll() {
        return entityManager.createNamedQuery("Order.findAll", Order.class).getResultList();
    }

    public List<Order> findOrdersByOrderStatus(OrderStatus orderStatus) {
        List<Order> orderList = entityManager.createNamedQuery("Order.findByOrderStatus", Order.class)
                .setParameter("orderStatus", orderStatus)
                .getResultList();

        return orderHelper.calculatePriceOfOrderList(orderList);
    }

    public List<Order> findOrdersByCustomer(User customer) {
        List<Order> orderList = entityManager.createNamedQuery("Order.findByCustomer", Order.class)
                .setParameter("customerId", customer.getId())
                .getResultList();

        return orderHelper.calculatePriceOfOrderList(orderList);
    }

    public List<Order> findActiveOrdersByCustomer(User customer) {
        List<Order> orderList = entityManager.createNamedQuery("Order.findActiveByCustomer", Order.class)
                .setParameter("customerId", customer.getId())
                .getResultList();

        return orderHelper.calculatePriceOfOrderList(orderList);
    }

    public List<Order> findDeliveredOrdersByCustomer(User customer) {
        List<Order> orderList = entityManager.createNamedQuery("Order.findDeliveredByCustomer", Order.class)
                .setParameter("customerId", customer.getId())
                .getResultList();

        return orderHelper.calculatePriceOfOrderList(orderList);
    }

    public List<Order> findOrdersByDeliveryMan(User deliveryMan) {
        List<Order> orderList = entityManager.createNamedQuery("Order.findByDeliveryMan", Order.class)
                .setParameter("deliveryManId", deliveryMan.getId())
                .getResultList();

        return orderHelper.calculatePriceOfOrderList(orderList);
    }

    public List<Order> findOrdersNotDeliveredByDeliveryMan(User deliveryMan) {
        List<Order> orderList = entityManager.createNamedQuery("Order.findOrdersNotDeliveredByDeliveryMan", Order.class)
                .setParameter("deliveryManId", deliveryMan.getId())
                .getResultList();

        return orderHelper.calculatePriceOfOrderList(orderList);
    }

    public List<Order> findDeliveredOrderByUser(User deliveryMan) {
        List<Order> orderList = entityManager.createNamedQuery("Order.findDeliveredOrderByUser", Order.class)
                .setParameter("deliveryManId", deliveryMan.getId())
                .getResultList();

        return orderHelper.calculatePriceOfOrderList(orderList);
    }

    public boolean isAccessibleOrderByUser(User user, Order order) {
        return !entityManager.createNamedQuery("Order.findOrderByOrderIdAndUserId", Order.class)
                .setParameter("userId", user.getId())
                .setParameter("orderId", order.getId())
                .getResultList()
                .isEmpty();
    }

    public Order findById(int id) {
        return entityManager.find(Order.class, id);
    }

    public boolean isOrderOnProcess(int orderId) {
        Order order = entityManager.find(Order.class, orderId);

        return OrderStatus.PICKED.equals(order.getStatus());
    }

    public boolean isUserInUse(User user) {
        List<Order> orderList1 = findOrdersByDeliveryMan(user);
        List<Order> orderList2 = findOrdersByCustomer(user);

        return orderList1.size() > 0 || orderList2.size() > 0;
    }

    @Transactional
    public void cancel(int orderId) {
        Order order = entityManager.find(Order.class, orderId);
        order.setStatus(OrderStatus.CANCELED);
        saveOrUpdate(order);
    }

    @Transactional
    public void delete(int orderId) {
        Order order = entityManager.find(Order.class, orderId);
        order.setAccessStatus(AccessStatus.DELETED);

        for (OrderItem orderItem : order.getOrderItemList()) {
            orderItem.setAccessStatus(AccessStatus.DELETED);
        }

        saveOrUpdate(order);
    }

    @Transactional
    public Order saveOrUpdate(Order order) {

        if (order.isNew()) {
            entityManager.persist(order);
        } else {
            order = entityManager.merge(order);
        }

        return order;
    }
}
