package company;

import company.models.Lesson;

import java.util.List;
import java.util.Optional;

public interface LessonRepository {
    void save(Lesson lesson);
    void update(Lesson lesson);
    List<Lesson> findAll();
    Optional<Lesson> findById(Integer id);
    List<Lesson> findByName(String name);

}
