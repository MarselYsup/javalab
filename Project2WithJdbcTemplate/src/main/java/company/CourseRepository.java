package company;

import company.models.Course;

import java.util.List;
import java.util.Optional;

public interface CourseRepository {
    void save(Course course);
    void update(Course course);
    List<Course> findAll();
    Optional<Course> findById(Integer id);
    List<Course> findByName(String name);

}
