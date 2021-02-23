package com.jpa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "course")
@NamedQueries(value = {
        @NamedQuery(name = "get_all_courses", query = "select c from Course c"),
        @NamedQuery(name = "get_all_courses_join_fetch", query = "select c from Course c JOIN FETCH c.students s"),
        @NamedQuery(name = "get_with_name_100_steps", query = "Select c From Course c where name like '%100 steps'")
})
@Cacheable
@SQLDelete(sql="update course set is_deleted=true where id=?")
@Where(clause = "is_deleted=false")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="cour_seq")
    @SequenceGenerator(name = "cour_seq", sequenceName = "cour_seq", initialValue = 1, allocationSize=1)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "course")
//    @JsonIgnore
    private List<Review> reviews = new ArrayList<>();

    //to reduce no. of join tables. does not matter on which side put
    @ManyToMany(mappedBy = "courses")
    @JsonIgnore
    private List<Student> students = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime lastUpdatedDate;

    private boolean isDeleted;

    @PreRemove
    private void preRemove(){
        this.isDeleted = true;
    }

    public void addReview(Review review) {
        this.reviews.add(review);
    }

    public void removeReview(Review review) {
        this.reviews.remove(review);
    }

    public void addStudent(Student student) {
        this.students.add(student);
    }

    public Course(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("Course[%s]", name);
    }
}
