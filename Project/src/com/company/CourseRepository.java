package com.company;

import com.company.models.Course;

import java.util.List;

public interface CourseRepository {
    void save(Course course);
    void update(Course course);
    void delete(Integer id);
    List<Course> findAll();
    Course findById(Integer id);
    List<Course> findByName(String name);

}
