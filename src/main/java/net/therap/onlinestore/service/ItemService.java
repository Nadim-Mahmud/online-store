package net.therap.onlinestore.service;

import net.therap.onlinestore.entity.Item;
import net.therap.onlinestore.entity.Tag;
import net.therap.onlinestore.helper.ItemHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * @author nadimmahmud
 * @since 3/5/23
 */
@Service
public class ItemService extends BaseService {

    private final ItemHelper itemHelper;

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

    public List<Item> filter(String categoryId, String tagId){
        List<Item>itemListByCategory = new ArrayList<>();
        List<Item>itemListByTag = new ArrayList<>();

        if(isNull(categoryId) && isNull(tagId)){
            return findAll();
        }

        if(nonNull(categoryId) && !categoryId.isEmpty()){
            itemListByCategory = findByCategory(Integer.parseInt(categoryId));
        }

        if(nonNull(tagId) && !tagId.isEmpty()){
            Tag tag = tagService.findById(Integer.parseInt(tagId));
            itemListByTag = tag.getItemList();
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
