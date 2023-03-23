package net.therap.onlinestore.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;
import java.util.Objects;

/**
 * @author nadimmahmud
 * @since 3/5/23
 */
@Entity
@Table(name = "order_item")
@SQLDelete(sql = "UPDATE order_item SET access_status = 'DELETED' WHERE id = ? AND version = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "access_status <> 'DELETED'")
@NamedQueries({
        @NamedQuery(name = "OrderItem.findAll", query = "SELECT o FROM OrderItem o")
})
public class OrderItem extends Persistent {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderItemSeq")
    @SequenceGenerator(name = "orderItemSeq", sequenceName = "order_item_seq", allocationSize = 1)
    @Column(name = "id", updatable = false)
    private int id;

    @CreationTimestamp
    @Column(name = "accepted_at")
    private Date acceptedAt;

    @Min(value = 1, message = "{input.quantity}")
    @Max(value = 500, message = "{input.quantity}")
    @Column(name = "quantity")
    private int quantity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id")
    private Order order;

    public OrderItem() {
        this.quantity = 1;
    }

    public OrderItem(int id) {
        super();

        this.item = new Item(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getAcceptedAt() {
        return acceptedAt;
    }

    public void setAcceptedAt(Date acceptedAt) {
        this.acceptedAt = acceptedAt;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public boolean isNew() {
        return id == 0;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof OrderItem)) return false;
        OrderItem orderItem = (OrderItem) o;

        return getItem().getId() == orderItem.getItem().getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getItem());
    }
}
