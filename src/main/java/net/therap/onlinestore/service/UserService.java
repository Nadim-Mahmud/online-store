package net.therap.onlinestore.service;

import net.therap.onlinestore.command.Credentials;
import net.therap.onlinestore.entity.AccessStatus;
import net.therap.onlinestore.entity.User;
import net.therap.onlinestore.entity.UserType;
import net.therap.onlinestore.util.Encryption;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Objects;

/**
 * @author nadimmahmud
 * @since 3/5/23
 */
@Repository
public class UserService {

    @PersistenceContext
    private EntityManager entityManager;

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

    public User findByEmail(String email) {

        try {
            return entityManager.createNamedQuery("User.findByEmail", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception ex) {
            return new User();
        }
    }

    public boolean isValidCredential(Credentials credentials) throws NoSuchAlgorithmException, InvalidKeySpecException {
        User user;

        try {
            user = entityManager.createNamedQuery("User.findByEmail", User.class)
                    .setParameter("email", credentials.getEmail())
                    .getSingleResult();
        } catch (Exception ex) {
            return false;
        }

        return Objects.nonNull(user) && user.getPassword().equals(Encryption.getPBKDF2(credentials.getPassword()));
    }

    public boolean isDuplicateEmail(User user) {
        return !entityManager.createNamedQuery("User.getUserByNameAndId", User.class)
                .setParameter("email", user.getEmail())
                .setParameter("id", user.getId())
                .getResultList()
                .isEmpty();
    }


    @Transactional
    public void delete(int id) {
        User user = findById(id);
        user.setAccessStatus(AccessStatus.DELETED);
        saveOrUpdate(user);
    }

    @Transactional
    public User saveOrUpdate(User user) {

        if (user.isNew()) {
            entityManager.persist(user);
        } else {
            user = entityManager.merge(user);
        }

        return user;
    }
}
