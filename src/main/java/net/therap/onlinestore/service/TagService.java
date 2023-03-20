package net.therap.onlinestore.service;

import net.therap.onlinestore.entity.Tag;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author nadimmahmud
 * @since 3/5/23
 */
@Service
public class TagService {

    @PersistenceContext
    protected EntityManager entityManager;

    public List<Tag> findAll() {
        return entityManager.createNamedQuery("Tag.findAll", Tag.class).getResultList();
    }

    public Tag findById(int id) {
        return entityManager.find(Tag.class, id);
    }

    @Transactional
    public void delete(int id) throws Exception {
        entityManager.remove(entityManager.find(Tag.class, id));
    }

    @Transactional
    public Tag saveOrUpdate(Tag tag) throws Exception {

        if (tag.isNew()) {
            entityManager.persist(tag);
        } else {
            tag = entityManager.merge(tag);
        }

        return tag;
    }

    public boolean isExistingTag(Tag tag) {
        return !entityManager.createNamedQuery("User.getTagByNameAndId", Tag.class)
                .setParameter("name", tag.getName())
                .setParameter("id", tag.getId())
                .getResultList()
                .isEmpty();
    }
}
