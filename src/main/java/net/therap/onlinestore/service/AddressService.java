package net.therap.onlinestore.service;

import net.therap.onlinestore.entity.Address;
import net.therap.onlinestore.entity.Category;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author nadimmahmud
 * @since 3/5/23
 */
@Service
public class AddressService extends BaseService {

    public List<Address> findAll() {
        return entityManager.createNamedQuery("Address.findAll", Address.class).getResultList();
    }

    public Address findById(int id) {
        return entityManager.find(Address.class, id);
    }

    @Transactional
    public void delete(int id) throws Exception {
        entityManager.remove(entityManager.find(Address.class, id));
    }

    @Transactional
    public Address saveOrUpdate(Address address) throws Exception {

        if (address.isNew()) {
            entityManager.persist(address);
        } else {
            address = entityManager.merge(address);
        }

        return address;
    }

}