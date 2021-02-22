package com.jpa.repository;

import com.jpa.model.Course;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class CourseSpringDataRepositoryTest {
    private static final Long COURSE_ID = 10001L;
    private static final String EXPECTED_COURSE = "Jpa in 50 steps";
    private static final String EXPECTED_COURSE_NAME = "Microservices in 100 steps - updated";

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CourseSpringDataRepository courseRepository;

    //////////Spring Data Repository Tests////////////
    @Test
    public void shouldFindCourseById() {
        //Arrange & Act
        Optional<Course> optionalCourse = courseRepository.findById(COURSE_ID);
        Course course = null;
        if (optionalCourse.isPresent()) {
            course = optionalCourse.get();
        }

        //Assert
        assertTrue(optionalCourse.isPresent());
        assertThat(course.getName()).isEqualTo(EXPECTED_COURSE);
    }

    @Test
    public void shouldNotFindCourseById() {
        //Arrange & Act
        Optional<Course> optionalCourse = courseRepository.findById(2001L);

        //Assert
        assertFalse(optionalCourse.isPresent());
    }

    @Test
    public void shouldInsertAndUpdateRecord() {
        //Arrange & Act
        Course course = courseRepository.save(new Course("Microservices in 100 steps")); //used 4 insert!
        course.setName("Microservices in 100 steps - updated"); //used fof update
        courseRepository.save(course);

        //Assert
        assertTrue(course.getName().contentEquals(EXPECTED_COURSE_NAME));
    }

    @Test
    public void shouldSortCoursesByName() {
        //Arrange
        Course course = new Course("Microservices in 100 steps");
        // Act
        List<Course> courses = courseRepository.findAll(Sort.by(Sort.Direction.DESC, "name"));//used 4 insert!
        log.info(String.format("Courses: '%s'", courses));
    }

    @Test
    public void shouldPaginateData() {
        //Arrange
        PageRequest pageRequest = PageRequest.of(0, 3);

        // Act
        Page<Course> firstPage = courseRepository.findAll(pageRequest);//getting the FIRST PAGE
        log.info(String.format("First page: '%s'", firstPage.getContent()));

        Pageable secondRequest = firstPage.nextPageable();
        Page<Course> secondPage = courseRepository.findAll(secondRequest);
        log.info(String.format("First page: '%s'", secondPage.getContent()));
    }

    @Test
    public void shouldReturnCourseByName() {
        //Arrange & Act
        List<Course> courses = courseRepository.findByName(EXPECTED_COURSE);//used 4 insert!
        //Assert
        assertTrue(courses.get(0).getName().contentEquals(EXPECTED_COURSE));
    }
}