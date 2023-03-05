package net.therap.onlinestore.service;

import net.therap.onlinestore.entity.Order;
import net.therap.onlinestore.entity.Tag;
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

    public Order findById(int id) {
        return entityManager.find(Order.class, id);
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
