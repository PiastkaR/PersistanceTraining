package com.jpa.repository;

import com.jpa.model.Course;
import com.jpa.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.Subgraph;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class PerformanceTuningTest {
    private static final Long COURSE_ID = 10001L;
    private static final String EXPECTED_COURSE = "Jpa in 50 steps";
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CourseRepository courseRepository;

    @Test
    @Transactional
    public void createNPlusOneProblem() {
        //Arrange & Act
        TypedQuery<Course> query = entityManager.createNamedQuery("get_all_courses", Course.class);
        List<Course> resultList = query.getResultList();

        List <Student> students = new ArrayList<>();
        for (Course course : resultList) {
            students.addAll(course.getStudents());
        }

        assertTrue(students.size() == 4);
    }

    @Test
    @Transactional
    public void solveNPlusOneEntityGraph() {
        //Arrange
        EntityGraph<Course> entityGraph = entityManager.createEntityGraph(Course.class);
        Subgraph<Object> subGraph = entityGraph.addSubgraph("students");

        //Act
        List<Course> resultList = entityManager
                .createNamedQuery("get_all_courses", Course.class)
                .setHint("javax.persistence.loadgraph", entityGraph)
                .getResultList();

        List <Student> students = new ArrayList<>();
        for (Course course : resultList) {
            students.addAll(course.getStudents());
        }

        assertTrue(students.size() == 4);
    }

    @Test
    @Transactional
    public void solveNPlusOneJoinFetch() {
        //Arrange
        EntityGraph<Course> entityGraph = entityManager.createEntityGraph(Course.class);
        Subgraph<Object> subGraph = entityGraph.addSubgraph("students");

        //Act
        List<Course> resultList = entityManager
                .createNamedQuery("get_all_courses_join_fetch", Course.class)
                .setHint("javax.persistence.loadgraph", entityGraph)
                .getResultList();

        List <Student> students = new ArrayList<>();
        for (Course course : resultList) {
            students.addAll(course.getStudents());
        }

        assertTrue(students.size() == 4);
    }
}