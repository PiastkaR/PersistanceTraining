package com.jpa.repository;

import com.jpa.model.Course;
import com.jpa.model.Review;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class CourseRepositoryTest {
    //Hibernate cannot be unit tested!
    private static final Long COURSE_ID = 10001L;
    private static final Long COURSE_ID2 = 10002L;
    private static final String EXPECTED_COURSE = "Jpa in 50 steps";
    private static final String UPDATED_COURSE = "Updated name";
    private static final ArrayList<Review> EXPECTED_REVIEW_LIST = new ArrayList<>(Arrays.asList(
            new Review("5", "Great Course"),
            new Review("4", "Wonderful Course"),
            new Review("5", "Awesome Course")
    ));


    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CourseRepository courseRepository;

    @Test
    public void shouldFindCourseById() {
        //Arrange & Act
        Course actualCourse = courseRepository.findById(COURSE_ID);
        //Assert
        assertThat(actualCourse.getName()).isEqualTo(EXPECTED_COURSE);
    }

    @Test
    @DirtiesContext
    public void shouldDeleteById() {
        //Arrange & Act
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

    @Test
    @Transactional
    public void retrieveReviewsForCourse() {
        //Arrange & Act
        Course actualCourse = courseRepository.findById(10001L);
        List<Review> reviews = actualCourse.getReviews();
        //Assert
        assertThat(reviews).isNotEqualTo(EXPECTED_REVIEW_LIST);
    }

    @Test
    @Transactional
    public void firstLvlCacheDemo() {
        Course actualCourse = courseRepository.findById(10001L);
        log.info("Course retrieved: {}", actualCourse);

        Course actualCourse2 = courseRepository.findById(10001L);
        log.info("Course retrieved again: {}", actualCourse2);
    }
}