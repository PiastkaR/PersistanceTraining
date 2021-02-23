package com.jpa.repository;

import com.jpa.model.Course;
import com.jpa.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class JPQLTest {
    //Hibernate cannot be unit tested!
    private static final List<Course> EXPECTED_LIST = new ArrayList<>(Arrays.asList(
            new Course("Jpa in 50 steps"),
            new Course("Jpa in 100 steps"),
            new Course("Jpa in 150 steps"),
            new Course("Jpa in 200 steps")
    ));

    private static final List<Course> COURSES_WITHOUT_STUDENTS = new ArrayList<>(Arrays.asList(
            new Course("Jpa in 100 steps"),
            new Course("Jpa in 200 steps")
    ));

    private static final List<Course> COURSES_WITH_MORE_THAN_TWO_STUDENTS = new ArrayList<>(Arrays.asList(
            new Course("Jpa in 50 steps")
    ));

    private static final List<Course> EXPECTED_ORDERED_LIST = new ArrayList<>(Arrays.asList(
            new Course("Jpa in 100 steps"),
            new Course("Jpa in 200 steps"),
            new Course("Jpa in 150 steps"),
            new Course("Jpa in 50 steps")
    ));

    private static final Course EXPECTED_COURSE = new Course("Jpa in 100 steps");

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CourseRepository courseRepository;

    @Test
    public void shouldFindAllCoursesByJPQLQuery() {
        //Arrange & Act
        Query query = entityManager.createQuery("Select c from Course c");
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
        ////////NATIVE QUERIES////////
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
//        assertEquals(12, noOfRowsUpdated);
    }

    ///////////JPQL with Inheritance!////////////////////////////
    @Test
    public void coursesWithoutStudents() {
        //Arrange & Act
        TypedQuery<Course> typedQuery = entityManager.createQuery("Select c From Course c where c.students is empty", Course.class);
        List<Course> resultList = typedQuery.getResultList();

        //Assert
        assertThat(resultList.equals(COURSES_WITHOUT_STUDENTS));
    }

    @Test
    public void coursesWithoutAtLeastTwoStudents() {
        //Arrange & Act
        TypedQuery<Course> typedQuery = entityManager.createQuery("Select c From Course c where size(c.students) >= 2", Course.class);
        List<Course> resultList = typedQuery.getResultList();

        //Assert
        assertThat(resultList.equals(COURSES_WITH_MORE_THAN_TWO_STUDENTS));
    }

    @Test
    public void coursesOrderedByNumberOfStudents() {
        //Arrange & Act
        TypedQuery<Course> typedQuery = entityManager.createQuery("Select c From Course c order by size(c.students)", Course.class);
        List<Course> resultList = typedQuery.getResultList();

        //Assert
        assertThat(resultList.equals(EXPECTED_ORDERED_LIST));
    }

    @Test
    public void studentsWithPassportsWithPattern1234() {
        //Arrange & Act
        TypedQuery<Student> typedQuery = entityManager.createQuery("Select s from Student s where s.passport.number like '%1234%'", Student.class);
        List<Student> resultList = typedQuery.getResultList();

        //Assert
        assertThat(resultList.equals(new Student("Rafal")));
    }

    ////////////////JPQL JOINS////////////////
    @Test
    public void join() {
        //Arrange & Act
        Query query = entityManager.createQuery("Select c, s from Course c JOIN c.students s");
        List<Object[]> resultList = query.getResultList();

        //Assert
        for (Object[] result : resultList) {
            log.info("Course:{} Student:{}", result[0], result[1]);
        }
    }

    @Test
    public void leftJoin() {
        //Arrange & Act
        Query query = entityManager.createQuery("Select c, s from Course c LEFT JOIN c.students s");
        List<Object[]> resultList = query.getResultList();

        //Assert
        for (Object[] result : resultList) {
            log.info("Course:{} Student:{}", result[0], result[1]);
        }
    }

    @Test
    public void crossJoin() {
        //Arrange & Act
        Query query = entityManager.createQuery("Select c, s from Course c, Student s");
        List<Object[]> resultList = query.getResultList();

        //Assert
        for (Object[] result : resultList) {
            log.info("Course:{} Student:{}", result[0], result[1]);
        }
    }

    ////////////////JPQL JOINS////////////////
}
