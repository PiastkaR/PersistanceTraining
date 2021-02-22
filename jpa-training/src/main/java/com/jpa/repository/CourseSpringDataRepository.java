package com.jpa.repository;

import com.jpa.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseSpringDataRepository extends JpaRepository<Course, Long> {
    List<Course> findByName(String name);
    List<Course> findByNameOrderByIdDesc(String name, Long id);
    List<Course> findByNameAndId(String name, Long id);
    List<Course> countByName(String name);
    List<Course> deleteByName(String name);

    @Query("select c from Course c where name lke '%100 steps'")
    List<Course> coursesWith100StepsInaName();

    @Query(value = "select * from Course c where name lke '%100 steps'",
            nativeQuery = true)
    List<Course> coursesWith100StepsInaNameUsingNativeQuery();

    @Query(name = "get_with_name_100_steps")
    List<Course> coursesWith100StepsInaNameUsingNamedQuery();
}
