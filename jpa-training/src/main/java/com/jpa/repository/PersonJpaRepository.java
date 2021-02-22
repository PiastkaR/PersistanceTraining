package com.jpa.repository;

import com.jpa.model.PersonJpa;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
@EqualsAndHashCode
public class PersonJpaRepository {
    //connect to DB
    @PersistenceContext
    EntityManager entityManager;

    public List<PersonJpa> findAll() {
        TypedQuery<PersonJpa> namedQuery = entityManager.createNamedQuery("find_all_persons", PersonJpa.class);
        return namedQuery.getResultList();
    }

    public PersonJpa findById(int id) {
        return entityManager.find(PersonJpa.class, id);
    }

    public PersonJpa update(PersonJpa person) {
        PersonJpa personToUpdate = findById(person.getId());

        if (person.equals(personToUpdate)) {
            return entityManager.merge(personToUpdate);
        } else return entityManager.merge(person);
    }

    public PersonJpa insert(PersonJpa person) {
        return entityManager.merge(person);
    }

    public void deleteById(int personId) {
        PersonJpa personToDelete = findById(personId);
        entityManager.remove(personToDelete);
    }
}
