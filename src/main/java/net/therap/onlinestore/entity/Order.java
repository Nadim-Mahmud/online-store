package net.therap.onlinestore.entity;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nadimmahmud
 * @since 3/5/23
 */
@Entity
@Table(name = "order_table")
@SQLDelete(sql = "UPDATE order_table SET access_status = 'DELETED' WHERE id = ? AND version = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "access_status <> 'DELETED'")
@NamedQueries({
        @NamedQuery(name = "Order.findAll", query = "SELECT o FROM Order o")
})
public class Order extends Persistent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderTableSeq")
    @SequenceGenerator(name = "orderTableSeq", sequenceName = "order_table_seq", allocationSize = 1)
    @Column(name = "id", updatable = false)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @OneToOne
    @JoinColumn(name = "address")
    private Address address;

    @OneToMany(mappedBy = "order")
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

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public boolean isNew() {
        return id == 0;
    }
}
