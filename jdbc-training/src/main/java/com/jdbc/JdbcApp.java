package com.jdbc;

import com.jdbc.model.Person;
import com.jdbc.repository.PersonJdbcDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class JdbcApp implements CommandLineRunner {

    private final PersonJdbcDao personJdbcDao;

    public static void main(String[] args) {
        SpringApplication.run(JdbcApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
       log.info("All users -> {}", personJdbcDao.findAll());
       log.info("User id -> {}", personJdbcDao.findById(10001));
       log.info("User by location -> {}", personJdbcDao.findByLocation("Amsterdam"));
       log.info("DeleteById no of rows deleted -> {}", personJdbcDao.deleteById(10002));
       log.info("Insert person -> {}", personJdbcDao.insertPerson(new Person(10004, "Agata", "Warsaw", new Date())));
       log.info("update 1004 -> {}", personJdbcDao.updatePerson(new Person(10004, "Agata2", "Warsaw2", new Date())));
    }
}
