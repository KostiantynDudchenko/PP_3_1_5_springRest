package ru.kata.springRest.dao;


import org.springframework.stereotype.Repository;
import ru.kata.springRest.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;


@Repository
public class UserDAOImpl implements UserDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getUsers() {
        return entityManager.createQuery("select distinct u from User u left join fetch u.roles", User.class).getResultList();
    }

    @Override
    public void saveUser(User user) {
        entityManager.persist(user);
    }

    /*@Override
    public User getUserById(Long id) {
        return entityManager.find(User.class, id);
    }*/

    @Override
    public User getUserById(Long id) {
        TypedQuery<User> queryUser = entityManager.createQuery("select distinct u from User u left join fetch u.roles where u.id=:id",
                User.class).setParameter("id", id);
        return queryUser.getSingleResult();
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }


    @Override
    public void deleteUser(User user) {
        entityManager.remove(entityManager.contains(user) ? user
                : entityManager.merge(user));
    }

    @Override
    public User findUserByName(String first_name) {
        TypedQuery<User> queryUser = entityManager.createQuery("select distinct u from User u left join fetch u.roles where u.first_name=:first_name",
                User.class).setParameter("first_name", first_name);
        return queryUser.getSingleResult();
    }
}
