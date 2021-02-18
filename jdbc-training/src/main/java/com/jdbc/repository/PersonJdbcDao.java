package com.jdbc.repository;

import com.jdbc.model.Person;
import com.jdbc.model.PersonRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PersonJdbcDao {

    private final JdbcTemplate jdbcTemplate;

      public List<Person> findAll() {
        return jdbcTemplate.query("select * from person", new PersonRowMapper());
    }

    public Person findById(int id) {
        return jdbcTemplate.queryForObject("select * from person where id=?",
                new Object[]{id},
                new PersonRowMapper());
    }

    public Person findByLocation(String location) {
        return jdbcTemplate.queryForObject("select * from person where location=?",
                new Object[]{location},
                new PersonRowMapper());
    }

    public int deleteById(int id) {
        return jdbcTemplate.update("delete from person where id=?", id);
    }


    public int insertPerson(Person person) {
        return jdbcTemplate.update("insert into person (id, name, location, birth_date) values (?,?,?,?)",
                person.getId(), person.getName(), person.getLocation(), person.getBirthDate());
    }

    public int updatePerson(Person person) {
        return jdbcTemplate.update("update person set name=?, location=?, birth_date=? where id=?",
                person.getName(), person.getLocation(), person.getBirthDate(), person.getId());
    }
}
