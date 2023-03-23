package net.therap.onlinestore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author nadimmahmud
 * @since 3/5/23
 */
@Entity
@Table(name = "item")
@SQLDelete(sql = "UPDATE item SET access_status = 'DELETED' WHERE id = ? AND version = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "access_status <> 'DELETED'")
@NamedQueries({
        @NamedQuery(name = "Item.findAll", query = "SELECT i FROM Item i ORDER BY i.name ASC"),
        @NamedQuery(name = "Item.search", query = "SELECT i FROM Item i WHERE LOWER(i.name) LIKE LOWER(:searchKey) OR LOWER(i.category.name) LIKE LOWER(:searchKey) OR LOWER(i.description) LIKE LOWER(:searchKey) ORDER BY i.name ASC"),
        @NamedQuery(name = "Item.findAvailable", query = "SELECT i FROM Item i WHERE i.availability = 'AVAILABLE' ORDER BY i.name ASC"),
        @NamedQuery(name = "Item.findItemsByNameAndId", query = "SELECT i FROM Item i WHERE i.name = :name AND i.id != :id"),
        @NamedQuery(name = "Item.findByCategoryId", query = "SELECT i FROM Item i WHERE i.category.id = :categoryId ORDER BY i.name ASC"),
        @NamedQuery(name = "Item.findByTag", query = "SELECT i FROM Item i WHERE :tag MEMBER OF i.tagList ORDER BY i.name ASC"),
        @NamedQuery(name = "Item.findByTagAndCategoryId", query = "SELECT i FROM Item i WHERE :tag MEMBER OF i.tagList AND i.category.id = :categoryId ORDER BY i.name ASC"),
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
    @Min(value = 1, message = "{input.price}")
    @Max(value = 1000000, message = "{input.price}")
    @Column(name = "price")
    private double price;

    @Transient
    @JsonIgnore
    private MultipartFile image;

    @Column(name = "image_path")
    private String imagePath;

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

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "tag_item",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    @JsonIgnore
    public boolean isNew() {
        return id == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return getId() == item.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
