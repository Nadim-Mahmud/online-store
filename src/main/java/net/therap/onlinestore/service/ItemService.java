package net.therap.onlinestore.service;

import net.therap.onlinestore.entity.Item;
import net.therap.onlinestore.entity.Tag;
import net.therap.onlinestore.helper.ItemHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * @author nadimmahmud
 * @since 3/5/23
 */
@Service
public class ItemService {

    private final ItemHelper itemHelper;

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    TagService tagService;

    public ItemService() {
        this.itemHelper = new ItemHelper();
    }

    public List<Item> findAll() {
        return entityManager.createNamedQuery("Item.findAll", Item.class).getResultList();
    }

    public List<Item> findByCategory(int categoryId) {
        return entityManager.createNamedQuery("Item.findByCategoryId", Item.class)
                .setParameter("categoryId", categoryId)
                .getResultList();
    }

    public List<Item> findAvailableItems() {
        return entityManager.createNamedQuery("Item.findAvailable", Item.class)
                .getResultList();
    }

    public Item findById(int id) {
        return entityManager.find(Item.class, id);
    }

    public List<Item> search(String searchKey) {
        return entityManager.createNamedQuery("Item.search", Item.class)
                .setParameter("searchKey", "%" + searchKey + "%")
                .getResultList();
    }

    public List<Item> filter(String categoryId, String tagId) {
        List<Item> itemListByCategory = new ArrayList<>();
        List<Item> itemListByTag = new ArrayList<>();

        if ((isNull(categoryId) || categoryId.isEmpty()) && (isNull(tagId) || tagId.isEmpty())) {
            return findAll();
        }

        if (nonNull(categoryId) && !categoryId.isEmpty()) {
            itemListByCategory = findByCategory(Integer.parseInt(categoryId));

            if (isNull(tagId) || tagId.isEmpty()) {
                return itemListByCategory;
            }
        }

        if (nonNull(tagId) && !tagId.isEmpty()) {
            Tag tag = tagService.findById(Integer.parseInt(tagId));
            itemListByTag = tag.getItemList();

            if (isNull(categoryId) || categoryId.isEmpty()) {
                return itemListByTag;
            }
        }

        return itemHelper.intersectItemList(itemListByCategory, itemListByTag);
    }

    @Transactional
    public void delete(int id) throws Exception {
        entityManager.remove(entityManager.find(Item.class, id));
    }

    @Transactional
    public Item saveOrUpdate(Item item) throws Exception {

        if (item.isNew()) {
            entityManager.persist(item);
        } else {
            item = entityManager.merge(item);
        }

        return item;
    }

    public boolean isExistingItem(Item item) {
        return !entityManager.createNamedQuery("Item.findItemsByNameAndId", Item.class)
                .setParameter("name", item.getName())
                .setParameter("id", item.getId())
                .getResultList()
                .isEmpty();
    }
}
