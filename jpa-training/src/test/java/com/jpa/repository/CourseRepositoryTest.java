package com.jpa.repository;

import com.jpa.model.Course;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CourseRepositoryTest {
    //Hibernate cannot be unit tested!
    private static final Long COURSE_ID = 10001L;
    private static final Long COURSE_ID2 = 10002L;
    private static final String EXPECTED_COURSE = "Jpa in 50 steps";
    private static final String UPDATED_COURSE = "Updated name";

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CourseRepository courseRepository;

    @Test
    public void shouldFindCourseById() {
        //Arrange
        //Act
        Course actualCourse = courseRepository.findById(COURSE_ID);

        //Assert
        assertThat(actualCourse.getName()).isEqualTo(EXPECTED_COURSE);
    }

    @Test
    @DirtiesContext
    public void shouldDeleteById() {
        //Arrange
        //Act
        courseRepository.deleteById(COURSE_ID2);

        //Assert
        assertNull(courseRepository.findById(COURSE_ID2));
    }

    @Test
    @DirtiesContext
    public void shouldSaveCourse() {
        //Arrange
        Course actualCourse = courseRepository.findById(COURSE_ID);
        actualCourse.setName(UPDATED_COURSE);

        //Act
        courseRepository.save(actualCourse);

        //Assert
        assertThat(actualCourse.getName()).isEqualTo(UPDATED_COURSE);
    }
}