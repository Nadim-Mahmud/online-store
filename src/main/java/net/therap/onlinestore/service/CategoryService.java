package net.therap.onlinestore.service;

import net.therap.onlinestore.entity.AccessStatus;
import net.therap.onlinestore.entity.Category;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author nadimmahmud
 * @since 3/5/23
 */
@Repository
public class CategoryService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Category> findAll() {
        return entityManager.createNamedQuery("Category.findAll", Category.class).getResultList();
    }

    public Category findById(int id) {
        return entityManager.find(Category.class, id);
    }

    public boolean isCategoryNotInUse(int id) {
        Category category = entityManager.find(Category.class, id);

        return category.getItemList().isEmpty();
    }

    @Transactional
    public void delete(int id) {
        Category category = findById(id);
        category.setAccessStatus(AccessStatus.DELETED);
        saveOrUpdate(category);
    }

    @Transactional
    public Category saveOrUpdate(Category category) {

        if (category.isNew()) {
            entityManager.persist(category);
        } else {
            category = entityManager.merge(category);
        }

        return category;
    }

    public boolean isExistingCategory(Category category) {
        return !entityManager.createNamedQuery("User.getCategoryByNameAndId", Category.class)
                .setParameter("name", category.getName())
                .setParameter("id", category.getId())
                .getResultList()
                .isEmpty();
    }
}
