package net.therap.onlinestore.service;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author nadimmahmud
 * @since 3/5/23
 */
@Service
public class BaseService {

    @PersistenceContext
    protected EntityManager entityManager;
}
