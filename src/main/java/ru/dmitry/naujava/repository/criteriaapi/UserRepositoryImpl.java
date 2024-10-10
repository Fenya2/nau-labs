package ru.dmitry.naujava.repository.criteriaapi;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.dmitry.naujava.entity.User;

import java.time.LocalDate;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final EntityManager entityManager;

    @Autowired
    public UserRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<User> findByBirthdayDateEarlierThan(LocalDate localDate) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        criteriaQuery.select(userRoot)
                .where(criteriaBuilder.lessThan(userRoot.get("birthdayDate"), localDate));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<User> findByBirthdayDateAfter(LocalDate localDate) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        criteriaQuery.select(userRoot)
                .where(criteriaBuilder.greaterThan(userRoot.get("birthdayDate"), localDate));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
