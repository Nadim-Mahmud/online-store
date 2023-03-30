package net.therap.onlinestore.service;

import net.therap.onlinestore.entity.AccessStatus;
import net.therap.onlinestore.entity.Tag;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author nadimmahmud
 * @since 3/5/23
 */
@Repository
public class TagService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Tag> findAll() {
        return entityManager.createNamedQuery("Tag.findAll", Tag.class).getResultList();
    }

    public Tag findById(int id) {
        return entityManager.find(Tag.class, id);
    }

    @Transactional
    public void delete(int id) {
        Tag tag = findById(id);
        tag.setAccessStatus(AccessStatus.DELETED);
        saveOrUpdate(tag);
    }

    @Transactional
    public Tag saveOrUpdate(Tag tag) {

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
