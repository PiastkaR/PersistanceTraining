package com.jpa.repository;

import com.jpa.model.Passport;
import com.jpa.model.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
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
}