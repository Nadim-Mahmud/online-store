package net.therap.onlinestore.entity;

import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nadimmahmud
 * @since 3/5/23
 */
@Entity
@Table(name = "order_table")
@Where(clause = "access_status <> 'DELETED'")
@NamedQueries({
        @NamedQuery(name = "Order.findAll", query = "SELECT o FROM Order o"),
        @NamedQuery(name = "Order.findByOrderStatus", query = "SELECT o FROM Order o WHERE o.status = :orderStatus"),
        @NamedQuery(name = "Order.findByDeliveryMan", query = "SELECT o FROM Order o WHERE o.user.id = :deliveryManId"),
        @NamedQuery(name = "Order.findByCustomer", query = "SELECT o FROM Order o WHERE o.address.user.id = :customerId"),
        @NamedQuery(name = "Order.findActiveByCustomer", query = "SELECT o FROM Order o WHERE o.address.user.id = :customerId AND o.status != 'DELIVERED'"),
        @NamedQuery(name = "Order.findDeliveredByCustomer", query = "SELECT o FROM Order o WHERE o.address.user.id = :customerId AND o.status = 'DELIVERED'"),
        @NamedQuery(name = "Order.findOrdersNotDeliveredByDeliveryMan", query = "SELECT o FROM Order o WHERE o.user.id = :deliveryManId AND o.status != 'DELIVERED'"),
        @NamedQuery(name = "Order.findDeliveredOrderByUser", query = "SELECT o FROM Order o WHERE o.user.id = :deliveryManId AND o.status = 'DELIVERED'"),
        @NamedQuery(name = "Order.findOrderByOrderIdAndUserId", query = "SELECT o FROM Order o WHERE o.address.user.id = :userId AND o.id = :orderId")
})
public class Order extends Persistent {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderTableSeq")
    @SequenceGenerator(name = "orderTableSeq", sequenceName = "order_table_seq", allocationSize = 1)
    @Column(name = "id", updatable = false)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @Transient
    private double price;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "address")
    private Address address;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "order")
    private List<OrderItem> orderItemList;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Order() {
        orderItemList = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItemList.add(orderItem);
        orderItem.setOrder(this);
    }

    public void removeOrderItem(OrderItem orderItem) {
        this.orderItemList.remove(orderItem);
        orderItem.setOrder(null);
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isNew() {
        return id == 0;
    }
}
