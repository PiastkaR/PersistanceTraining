package com.jpa;

import com.jpa.repository.CourseRepository;
import com.jpa.repository.EmployeeRepository;
import com.jpa.repository.PersonJpaRepository;
import com.jpa.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class JpaApp implements CommandLineRunner {
    private final PersonJpaRepository personJpaRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final EmployeeRepository employeeRepository;

    public static void main(String[] args) {
        SpringApplication.run(JpaApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        List<Review> reviews = new ArrayList<>();
//        reviews.add(new Review("5", "Great Hands-on Stuff"));
//        reviews.add(new Review("5", "Hatsoff."));

//        log.info("All users -> {}", personJpaRepository.findAll());
//        log.info("User id -> {}", personJpaRepository.findById(10001));
//        personJpaRepository.deleteById(10002);
//
//        Course course = courseRepository.findById(10001L);
//        courseRepository.save(new Course("My course"));
//        courseRepository.playWithEntityManager();
//        course.setName("Jpa in 50 steps - updated");
//        courseRepository.save(course);
//        studentRepository.saveStudentWithPassport();
//        courseRepository.addReviewsForCourseGeneric(10003L, reviews);
//        studentRepository.insertStudentAndCourse(new Student("Jack"), new Course("Microservices in 100 steps"));
//        employeeRepository.insert(new FullTimeEmployee("Jack", new BigDecimal("10000")));
//        employeeRepository.insert(new PartTimeEmployee("Jill", new BigDecimal("50")));
//        log.info("All Employees: -> {}", employeeRepository.retrieveAllEmployees());
//        log.info("All FullTimeEmployees: -> {}", employeeRepository.retrieveAllFullTimeEmployees());
//        log.info("All PartTimeEmployees: -> {}", employeeRepository.retrieveAllPartTimeEmployees());
    }
}
