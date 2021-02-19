package com.jpa.repository;

import com.jpa.model.Course;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JPQLTest {
    //Hibernate cannot be unit tested!
    private static final List<Course> EXPECTED_LIST = new ArrayList<>(Arrays.asList(
            new Course("Jpa in 50 steps"),
            new Course("Jpa in 100 steps"),
            new Course("Jpa in 150 steps"),
            new Course("Jpa in 200 steps")
    ));

    private static final Course EXPECTED_COURSE = new Course("Jpa in 100 steps");

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CourseRepository courseRepository;

    @Test
    public void shouldFindAllCoursesByJPQLQuery() {
        //Arrange & Act
        var query = entityManager.createQuery("Select c from Course c");
        List resultList = query.getResultList();

        //Assert
        assertThat(resultList.equals(EXPECTED_LIST));
    }

    @Test
    public void shouldFindAllCoursesTypedQuery() {
        //Arrange & Act
        TypedQuery<Course> typedQuery = entityManager.createQuery("Select c from Course c", Course.class);
        List<Course> resultList = typedQuery.getResultList();

        //Assert
        assertThat(resultList.equals(EXPECTED_LIST));
    }

    @Test
    public void shouldFindAllCoursesJPQLWhereQuery() { //PREFERRED QUERY TYPE!
        //Arrange & Act
        TypedQuery<Course> typedQuery = entityManager.createQuery("Select c From Course c where name like '%100 steps'", Course.class);
        List<Course> resultList = typedQuery.getResultList();

        //Assert
        assertThat(resultList.equals(EXPECTED_COURSE));
    }

    @Test
    public void shouldFindAllCoursesJPQLNamedQuery() {
        //Arrange & Act
        var query = entityManager.createNamedQuery("get_all_courses");
        List resultList = query.getResultList();

        //Assert
        assertThat(resultList.equals(EXPECTED_LIST));
    }

    @Test
    public void shouldFindAllCoursesJPQLWhereNamedQuery() { //PREFERRED QUERY TYPE!
        //Arrange & Act
        TypedQuery<Course> typedQuery = entityManager.createNamedQuery("get_with_name_100_steps", Course.class);
        List<Course> resultList = typedQuery.getResultList();

        //Assert
        assertThat(resultList.equals(EXPECTED_COURSE));
    }

    @Test
    public void shouldFindCourseByNativeQuery() {
        //Arrange & Act
        var query = entityManager.createNativeQuery("Select * from course where id=?", Course.class);
        query.setParameter(1, 1000L); //to get one specific course back
        List resultList = query.getResultList();

        //Assert
        assertThat(resultList.equals(EXPECTED_LIST));
    }

    @Test
    public void shouldFindCourseByNamedParameter() {
        //Arrange & Act
        var query = entityManager.createNativeQuery("Select * from course where id = :id", Course.class);
        query.setParameter("id", 1000L); //to get one specific course back
        List resultList = query.getResultList();

        //Assert
        assertThat(resultList.equals(EXPECTED_LIST));
    }

    @Test
    @Transactional
    public void massUpdate() {
        //Arrange & Act
        var query = entityManager.createNativeQuery("update course set last_updated_date = sysdate()", Course.class);
        int noOfRowsUpdated = query.executeUpdate();

        //Assert
        assertTrue(noOfRowsUpdated == 6);
    }
}
