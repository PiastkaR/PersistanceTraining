package com.jpa.repository;

import com.jpa.model.Course;
import com.jpa.model.Passport;
import com.jpa.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class StudentRepositoryTest {
    //Hibernate cannot be unit tested!
    private static final String EXPECTED_PASSPORT_NUMBER = "E12345";
    private static final Long EXPECTED_STUDENT_NUMBER = 20001L;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private StudentRepository studentRepository;

    @Test
    @Transactional
    public void retrieveStudentAndPassportDetails() {
        //Arrange & Act
        Student student = entityManager.find(Student.class, 20001L);
        Passport passport = student.getPassport();

        //Assert
        assertThat(passport.getNumber()).isEqualTo(EXPECTED_PASSPORT_NUMBER);
    }

    @Test
    @Transactional
    public void setAddressDetailsForStudent() {
        //Arrange & Act
        Student student = entityManager.find(Student.class, 20001L);
        log.debug("Student address: {}", student.getAddress());
        entityManager.flush();
    }

    @Test  //@Transactional might be deleted as it is in Repository!
    public void exampleTest() {
        studentRepository.methodToUnderstandPersistenceContext();
    }

    @Test
    @Transactional
    public void retrievePassportAndAssociatedStudent() {
        //Arrange & Act
        Passport passport = entityManager.find(Passport.class, 40001L);
        Student student = passport.getStudent();

        //Assert
        assertThat(student.getId()).isEqualTo(EXPECTED_STUDENT_NUMBER);
    }

    @Test
    @Transactional
    public void retrieveStudentAndCourses() {
        //Arrange
        ArrayList<Course> courses = createCourses();
        // Act
        Student student = entityManager.find(Student.class, 20001L);

        //Assert
        assertThat(student.getCourses().get(0).getName()).isEqualTo(courses.get(0).getName());
    }

    private ArrayList<Course> createCourses() {
        Course course = CourseFactory.createCourse("Jpa in 50 steps");
        Course course2 = CourseFactory.createCourse("Jpa in 150 steps");
        return new ArrayList<>(Arrays.asList(course, course2));
    }

}