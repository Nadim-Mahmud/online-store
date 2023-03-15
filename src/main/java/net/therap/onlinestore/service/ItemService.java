package net.therap.onlinestore.service;

import net.therap.onlinestore.entity.Item;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author nadimmahmud
 * @since 3/5/23
 */
@Service
public class ItemService extends BaseService {

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
