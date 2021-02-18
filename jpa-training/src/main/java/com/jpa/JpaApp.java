package com.jpa;

import com.jpa.model.Course;
import com.jpa.repository.CourseRepository;
import com.jpa.repository.PersonJpaRepository;
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

    public static void main(String[] args) {
        SpringApplication.run(JpaApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("All users -> {}", personJpaRepository.findAll());
        log.info("User id -> {}", personJpaRepository.findById(10001));
        personJpaRepository.deleteById(10002);
        courseRepository.save(new Course("My course"));
//        courseRepository.playWithEntityManager();
    }
}
