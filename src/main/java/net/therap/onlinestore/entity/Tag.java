package net.therap.onlinestore.entity;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nadimmahmud
 * @since 3/5/23
 */
@Entity
@Table(name = "tag")
@SQLDelete(sql = "UPDATE tag SET access_status = 'DELETED' WHERE id = ? AND version = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "access_status <> 'DELETED'")
@NamedQueries({
        @NamedQuery(name = "Tag.findAll", query = "SELECT t FROM Tag t"),
        @NamedQuery(name = "User.getTagByNameAndId", query = "SELECT t FROM Tag t WHERE t.name = :name AND id != :id")
})
public class Tag extends Persistent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tagSeq")
    @SequenceGenerator(name = "tagSeq", sequenceName = "tag_seq", allocationSize = 1)
    @Column(name = "id")
    private int id;

    @NotNull(message = "{input.text}")
    @Size(min = 1, max = 45, message = "{input.text}")
    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(
            name = "tag_item",
            joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> itemList;

    public Tag() {
        itemList = new ArrayList<>();
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

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public boolean isNew() {
        return id == 0;
    }
}
