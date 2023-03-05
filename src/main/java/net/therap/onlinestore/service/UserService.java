package net.therap.onlinestore.service;

import net.therap.onlinestore.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author nadimmahmud
 * @since 3/5/23
 */
@Service
public class UserService extends BaseService {

    public List<User> findAll() {
        return entityManager.createNamedQuery("User.findAll", User.class).getResultList();
    }

    public User findById(int id) {
        return entityManager.find(User.class, id);
    }

    @Transactional
    public void delete(int id) throws Exception {
        entityManager.remove(entityManager.find(User.class, id));
    }

    @Transactional
    public User saveOrUpdate(User user) throws Exception {

        if (user.isNew()) {
            entityManager.persist(user);
        } else {
            user = entityManager.merge(user);
        }

        return user;
    }

}
