package net.therap.onlinestore.service;

import net.therap.onlinestore.entity.User;
import net.therap.onlinestore.entity.UserType;
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

    public List<User> finByUserType(UserType userType) {
        return entityManager.createNamedQuery("User.findByUserType", User.class)
                .setParameter("userType", userType)
                .getResultList();
    }

    public User findById(int id) {
        return entityManager.find(User.class, id);
    }

    public boolean isDuplicateEmail(User user){
        return !entityManager.createNamedQuery("User.getUserByNameAndId", User.class)
                .setParameter("email", user.getEmail())
                .setParameter("id", user.getId())
                .getResultList()
                .isEmpty();
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
