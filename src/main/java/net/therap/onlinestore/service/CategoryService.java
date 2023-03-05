package net.therap.onlinestore.service;

import net.therap.onlinestore.entity.Category;
import net.therap.onlinestore.entity.Item;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author nadimmahmud
 * @since 3/5/23
 */
@Service
public class CategoryService extends BaseService {

    public List<Category> findAll() {
        return entityManager.createNamedQuery("Category.findAll", Category.class).getResultList();
    }

    public Category findById(int id) {
        return entityManager.find(Category.class, id);
    }

    @Transactional
    public void delete(int id) throws Exception {
        entityManager.remove(entityManager.find(Category.class, id));
    }

    @Transactional
    public Category saveOrUpdate(Category category) throws Exception {

        if (category.isNew()) {
            entityManager.persist(category);
        } else {
            category = entityManager.merge(category);
        }

        return category;
    }

}
