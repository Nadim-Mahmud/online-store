package net.therap.onlinestore.entity;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * @author nadimmahmud
 * @since 3/5/23
 */
@Entity
@Table(name = "item")
@SQLDelete(sql = "UPDATE item SET access_status = 'DELETED' WHERE id = ? AND version = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "access_status <> 'DELETED'")
@NamedQueries({
        @NamedQuery(name = "Item.findAll", query = "SELECT i FROM Item i")
})
public class Item extends Persistent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "itemSeq")
    @SequenceGenerator(name = "itemSeq", sequenceName = "item_seq", allocationSize = 1)
    @Column(name = "id", updatable = false)
    private int id;

    @NotNull(message = "{input.text}")
    @Size(min = 1, max = 45, message = "{input.text}")
    @Column(name = "name")
    private String name;

    @NotNull(message = "{input.number}")
    @Column(name = "price")
    private double price;

    @NotNull(message = "{input.select}")
    @Enumerated(EnumType.STRING)
    @Column(name = "availability")
    private Availability availability;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id")
    @NotNull(message = "{input.select}")
    private Category category;

    @NotNull(message = "{input.paragraph}")
    @Size(min = 1, max = 3000, message = "{input.paragraph}")
    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "itemList")
    private List<Tag> tagList;

    public Item() {
    }

    public Item(int id) {
        this();

        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    public boolean isNew() {
        return id == 0;
    }
}
