package net.therap.onlinestore.service;

import net.therap.onlinestore.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author nadimmahmud
 * @since 3/5/23
 */
@Service
public class OrderService extends BaseService {

    public List<Order> findAll() {
        return entityManager.createNamedQuery("Order.findAll", Order.class).getResultList();
    }

    public List<Order> findOrdersByOrderStatus(OrderStatus orderStatus) {
        return entityManager.createNamedQuery("Order.findByOrderStatus", Order.class)
                .setParameter("orderStatus", orderStatus)
                .getResultList();
    }

    public List<Order> findOrdersByCustomer(User customer) {
        return entityManager.createNamedQuery("Order.findOrdersByCustomer", Order.class)
                .setParameter("customerId", customer.getId())
                .getResultList();
    }

    public List<Order> findOrdersNotDeliveredByDeliveryMan(User deliveryMan) {
        return entityManager.createNamedQuery("Order.findOrdersNotDeliveredByDeliveryMan", Order.class)
                .setParameter("deliveryManId", deliveryMan.getId())
                .getResultList();
    }

    public Order findById(int id) {
        return entityManager.find(Order.class, id);
    }

    public boolean isOrderOnProcess(int orderId) {
        Order order = entityManager.find(Order.class, orderId);

        return OrderStatus.PICKED.equals(order.getStatus()) || OrderStatus.DELIVERED.equals(order.getStatus());
    }


    @Transactional
    public void cancel(int orderId) throws Exception {
        Order order = entityManager.find(Order.class, orderId);
        order.setAccessStatus(AccessStatus.DELETED);

        for (OrderItem orderItem : order.getOrderItemList()){
            orderItem.setAccessStatus(AccessStatus.DELETED);
        }

        saveOrUpdate(order);
    }

    @Transactional
    public void delete(int id) throws Exception {
        entityManager.remove(entityManager.find(Order.class, id));
    }

    @Transactional
    public Order saveOrUpdate(Order order) throws Exception {

        if (order.isNew()) {
            entityManager.persist(order);
        } else {
            order = entityManager.merge(order);
        }

        return order;
    }
}
