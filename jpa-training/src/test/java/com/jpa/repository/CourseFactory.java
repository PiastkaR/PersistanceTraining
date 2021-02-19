package com.jpa.repository;

import com.jpa.model.Course;

public class CourseFactory {
    public static Course createCourse(String name){
        return new Course(name);
    }
}
