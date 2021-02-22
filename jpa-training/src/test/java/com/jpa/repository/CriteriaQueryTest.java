package com.jpa.repository;

import com.jpa.model.Course;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CriteriaQueryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CourseRepository courseRepository;

    @Test
    public void basicCriteriaQuery() {
        //1. Use criteria Builder
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Course> courseCriteriaQuery = criteriaBuilder.createQuery(Course.class);
        //2.Define roots for tables
        Root<Course> courseRoot = courseCriteriaQuery.from(Course.class);
        //3.Define predicates
        //4.Add predicates
        //5.Build typed query using em and criteria query
        TypedQuery<Course> query = entityManager.createQuery(courseCriteriaQuery.select(courseRoot));
        List<Course> resultList = query.getResultList();
    }

    @Test
    public void getAllCoursesWith100StepsInName() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Course> courseCriteriaQuery = criteriaBuilder.createQuery(Course.class);
        Root<Course> courseRoot = courseCriteriaQuery.from(Course.class);
        //3.Define predicates
        Predicate like100steps = criteriaBuilder.like(courseRoot.get("name"), "%100steps");
        //4.Add predicates
        courseCriteriaQuery.where(like100steps);

        TypedQuery<Course> query = entityManager.createQuery(courseCriteriaQuery.select(courseRoot));
        List<Course> resultList = query.getResultList();
    }

    @Test
    public void getAllCoursesWithoutStudents() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Course> courseCriteriaQuery = criteriaBuilder.createQuery(Course.class);
        Root<Course> courseRoot = courseCriteriaQuery.from(Course.class);

        Predicate studentsIsEmpty = criteriaBuilder.isEmpty(courseRoot.get("students"));
        courseCriteriaQuery.where(studentsIsEmpty);

        TypedQuery<Course> query = entityManager.createQuery(courseCriteriaQuery.select(courseRoot));
        List<Course> resultList = query.getResultList();
    }

    @Test
    public void join() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Course> courseCriteriaQuery = criteriaBuilder.createQuery(Course.class);
        Root<Course> courseRoot = courseCriteriaQuery.from(Course.class);

        Join<Object, Object> join = courseRoot.join("students");

        TypedQuery<Course> query = entityManager.createQuery(courseCriteriaQuery.select(courseRoot));
        List<Course> resultList = query.getResultList();
    }

    @Test
    public void leftJoin() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Course> courseCriteriaQuery = criteriaBuilder.createQuery(Course.class);
        Root<Course> courseRoot = courseCriteriaQuery.from(Course.class);

        Join<Object, Object> join = courseRoot.join("students", JoinType.LEFT);

        TypedQuery<Course> query = entityManager.createQuery(courseCriteriaQuery.select(courseRoot));
        List<Course> resultList = query.getResultList();
    }
}
