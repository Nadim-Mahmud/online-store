package net.therap.onlinestore.service;

import net.therap.onlinestore.entity.AccessStatus;
import net.therap.onlinestore.entity.Item;
import net.therap.onlinestore.entity.Tag;
import net.therap.onlinestore.helper.FIleHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.List;

/**
 * @author nadimmahmud
 * @since 3/5/23
 */
@Repository
public class ItemService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private FIleHelper fIleService;

    public List<Item> findAll() {
        return entityManager.createNamedQuery("Item.findAll", Item.class).getResultList();
    }

    public List<Item> findAllPaginated(int start, int limit) {
        return entityManager.createNamedQuery("Item.findAll", Item.class)
                .setFirstResult(start)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<Item> findByCategory(int categoryId) {
        return entityManager.createNamedQuery("Item.findByCategoryId", Item.class)
                .setParameter("categoryId", categoryId)
                .getResultList();
    }

    public List<Item> findByCategoryPaginated(int categoryId, int start, int limit) {
        return entityManager.createNamedQuery("Item.findByCategoryId", Item.class)
                .setParameter("categoryId", categoryId)
                .setFirstResult(start)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<Item> findByTagPaginated(Tag tag, int start, int limit) {
        return entityManager.createNamedQuery("Item.findByTag", Item.class)
                .setParameter("tag", tag)
                .setFirstResult(start)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<Item> findByTagAndCategoryIdPaginated(Tag tag, int categoryId, int start, int limit) {
        return entityManager.createNamedQuery("Item.findByTagAndCategoryId", Item.class)
                .setParameter("tag", tag)
                .setParameter("categoryId", categoryId)
                .setFirstResult(start)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<Item> findAvailableItems() {
        return entityManager.createNamedQuery("Item.findAvailable", Item.class)
                .getResultList();
    }

    public Item findById(int id) {
        return entityManager.find(Item.class, id);
    }

    public List<Item> search(String searchKey, int start, int limit) {
        return entityManager.createNamedQuery("Item.search", Item.class)
                .setParameter("searchKey", "%" + searchKey + "%")
                .setFirstResult(start)
                .setMaxResults(limit)
                .getResultList();
    }

    @Transactional
    public void delete(int id) throws IOException {
        Item item = findById(id);
        item.setAccessStatus(AccessStatus.DELETED);
        saveOrUpdate(item);
    }

    @Transactional
    public Item saveOrUpdate(Item item) throws IOException {
        fIleService.saveItemImage(item);

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
