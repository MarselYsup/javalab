package company;

import company.models.Course;
import company.models.Lesson;
import company.models.Student;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class LessonRepositoryJdbcTemplateImpl implements LessonRepository {
    private JdbcTemplate jdbcTemplate;
    public LessonRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    private static final String UPDATE_LESSON = "Update lesson set name = ?,day_of_week=?,id_course=? where id = ?";
    private static final String SQL_INSERT =
            "insert into lesson(name, day_of_week, id_course) VALUES (?,?,?)";
    private static final String SELECT_ALL =
            "select l.id as lessonId ,l.name as lessonName, * from course inner join student_course sc on course.id = sc.id_course inner join student s on sc.id_student = s.id inner join lesson l on course.id = l.id_course order by l.id";
    private static final String SELECT_LESSONS_BY_NAME =
            "select l.id as lessonId ,l.name as lessonName, * from course inner join student_course sc on course.id = sc.id_course inner join student s on sc.id_student = s.id inner join lesson l on course.id = l.id_course where l.name = ? order by l.id";
    private static final String SELECT_LESSON_BY_ID =
            "select l.id as lessonId ,l.name as lessonName, * from course inner join student_course sc on course.id = sc.id_course inner join student s on sc.id_student = s.id inner join lesson l on course.id = l.id_course where l.id = ? order by l.id";
    private static final String UPDATE_COURSE = "Update course set name = ? ,start_end_date=?,lecturer_id = ? where id = ?";
    private static final String DELETE_STUDENT_COURSE = "delete from student_course where id_course = ?";
    private static final String SQL_INSERT_STUDENTS = "insert into student_course (id_student, id_course) values(?,?)";
    private final ResultSetExtractor<List<Lesson>> lessonsResultSetExtractor = row -> {
        List<Lesson> lessonList = new ArrayList<>();
        while (row.next()) {
            Integer id = row.getInt("lessonId");
            String name = row.getString("lessonName");
            String dayOfWeek = row.getString("day_of_week");

            Integer idCourse = row.getInt("id_course");
            String nameCourse = row.getString("name");
            String date = row.getString("start_end_date");
            Integer idLecturer = row.getInt("lecturer_id");

            Course course = new Course(idCourse, nameCourse, date, idLecturer, new HashSet<>());
            do {
                Integer idStudent = row.getInt("id_student");
                String firstName = row.getString("first_name");
                String lastName = row.getString("last_name");
                String numGroup = row.getString("numgroup");
                Student student = new Student(idStudent, firstName, lastName, numGroup);
                course.getStudents().add(student);
            } while (row.next()&&id==row.getInt("lessonId"));
            Lesson lesson = new Lesson(id,name,dayOfWeek,course);
            lessonList.add(lesson);
        }
        return lessonList;
    };
    private final ResultSetExtractor<Lesson> lessonResultSetExtractor = row -> {
        Lesson lesson = null;
        while (row.next()) {
            Integer id = row.getInt("lessonId");
            String name = row.getString("lessonName");
            String dayOfWeek = row.getString("day_of_week");

            Integer idCourse = row.getInt("id_course");
            String nameCourse = row.getString("name");
            String date = row.getString("start_end_date");
            Integer idLecturer = row.getInt("lecturer_id");

            Course course = new Course(idCourse, nameCourse, date, idLecturer, new HashSet<>());
            do {
                Integer idStudent = row.getInt("id_student");
                String firstName = row.getString("first_name");
                String lastName = row.getString("last_name");
                String numGroup = row.getString("numgroup");
                Student student = new Student(idStudent, firstName, lastName, numGroup);
                course.getStudents().add(student);
            } while (row.next()&&id==row.getInt("lessonId"));
            lesson = new Lesson(id,name,dayOfWeek,course);

        }
        return lesson;
    };
    @Override
    public void save(Lesson lesson) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT, new String[]{"id"});

            statement.setString(1, lesson.getName());
            statement.setString(2, lesson.getTimeOfLesson());
            statement.setInt(3, lesson.getCourse().getId());

            return statement;
        }, keyHolder);

        lesson.setId(keyHolder.getKey().intValue());
    }

    @Override
    public void update(Lesson lesson) {
        jdbcTemplate.update(UPDATE_LESSON,lesson.getName(),lesson.getTimeOfLesson()
                ,lesson.getCourse().getId(),lesson.getId());
        jdbcTemplate.update(UPDATE_COURSE,lesson.getCourse().getName(),lesson.getCourse().getStartEndDate()
                ,lesson.getCourse().getLectorer(),lesson.getCourse().getId());
        jdbcTemplate.update(DELETE_STUDENT_COURSE,lesson.getCourse().getId());
        for (Student student :
                lesson.getCourse().getStudents()) {
            jdbcTemplate.update(SQL_INSERT_STUDENTS, student.getId(), lesson.getCourse().getId());
        }

    }

    @Override
    public List<Lesson> findAll() {
        return jdbcTemplate.query(SELECT_ALL,lessonsResultSetExtractor);
    }

    @Override
    public Optional<Lesson> findById(Integer id) {
        try {
            return Optional.of(jdbcTemplate.query(SELECT_LESSON_BY_ID,lessonResultSetExtractor,id));
        } catch (NullPointerException e) {
            return Optional.empty();
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Lesson> findByName(String name) {
        return jdbcTemplate.query(SELECT_LESSONS_BY_NAME,lessonsResultSetExtractor,name);
    }
}
