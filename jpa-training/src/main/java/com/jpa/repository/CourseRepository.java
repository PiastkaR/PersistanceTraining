package com.jpa.repository;

import com.jpa.model.Course;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@RequiredArgsConstructor
@Repository
@Transactional
@Slf4j
public class CourseRepository {
    private final EntityManager entityManager;

    public Course findById(Long id) {
        return entityManager.find(Course.class, id);
    }

    public Course save(Course course) {
        if (course.getId() == null) {
            entityManager.persist(course);
        } else {
            entityManager.merge(course);
        }

        return course;
    }

    public void deleteById(Long id) {
        Course course = findById(id);
        entityManager.remove(course);
    }

    public void playWithEntityManager() { //it follows transaction ad hoc!
        Course course = new Course("playWithEntityManager");
        entityManager.persist(course);
        Course course2 = new Course("Angular JS");
        entityManager.persist(course2);
        entityManager.detach(course2); //detach == changes of course 2 are no longer tracked!

        entityManager.flush(); //send do DB

        course.setName("playWithEntityManager - updated");
        course2.setName("Angular JS - updated");
        entityManager.refresh(course); //refreshing data! no changes will be saved!
    }
}
