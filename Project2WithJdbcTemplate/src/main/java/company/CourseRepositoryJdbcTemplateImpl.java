package company;

import company.models.Course;
import company.models.Student;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class CourseRepositoryJdbcTemplateImpl implements CourseRepository {
    private JdbcTemplate jdbcTemplate;

    public CourseRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private static final String SELECT_BY_COURSE_NAME =
            "select * from course inner join student_course sc on course.id = sc.id_course inner join student s on sc.id_student = s.id  where course.name = ? order by course.id";
    private static final String SELECT_BY_COURSE_Id =
            "select * from course inner join student_course sc on course.id = sc.id_course inner join student s on sc.id_student = s.id  where course.id  = ? order by course.id";
    private static final String SELECT_ALL =
            "select * from course inner join student_course sc on course.id = sc.id_course inner join student s on sc.id_student = s.id order by course.id";
    private static final String SQL_INSERT =
            "Insert into course(name, start_end_date, lecturer_id) values(?,?,?)";
    private static final String SQL_INSERT_STUDENTS = "insert into student_course (id_student, id_course) values(?,?)";
    private static final String UPDATE_COURSE = "Update course set name = ? ,start_end_date=?,lecturer_id = ? where id = ?";
    private static final String DELETE_STUDENT_COURSE = "delete from student_course where id_course = ?";
    private final ResultSetExtractor<List<Course>> coursesResultSetExtractor = row -> {
        List<Course> courses = new ArrayList<>();
        while (row.next()) {
            Integer id = row.getInt("id_course");
            String name = row.getString("name");
            String date = row.getString("start_end_date");
            Integer idLecturer = row.getInt("lecturer_id");
            Course course = new Course(id, name, date, idLecturer, new HashSet<>());
            do {
                Integer idStudent = row.getInt("id_student");
                String firstName = row.getString("first_name");
                String lastName = row.getString("last_name");
                String numGroup = row.getString("numgroup");
                Student student = new Student(idStudent, firstName, lastName, numGroup);
                course.getStudents().add(student);
            } while (row.next() && id == row.getInt("id_course"));
            courses.add(course);
        }
        return courses;
    };
    private final ResultSetExtractor<Course> courseResultSetExtractor = row -> {
        Course course = null;
        while (row.next()) {
            Integer id = row.getInt("id_course");
            String name = row.getString("name");
            String date = row.getString("start_end_date");
            Integer idLecturer = row.getInt("lecturer_id");
            course = new Course(id, name, date, idLecturer, new HashSet<>());
            do {
                Integer idStudent = row.getInt("id_student");
                String firstName = row.getString("first_name");
                String lastName = row.getString("last_name");
                String numGroup = row.getString("numgroup");
                Student student = new Student(idStudent, firstName, lastName, numGroup);
                course.getStudents().add(student);
            } while (row.next());
        }

        return course;
    };

    @Override
    public void save(Course course) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT, new String[]{"id"});

            statement.setString(1, course.getName());
            statement.setString(2, course.getStartEndDate());
            statement.setInt(3, course.getLectorer());

            return statement;
        }, keyHolder);

        course.setId(keyHolder.getKey().intValue());

        for (Student student :
                course.getStudents()) {
            jdbcTemplate.update(SQL_INSERT_STUDENTS, student.getId(), course.getId());
        }

    }

    @Override
    public void update(Course course) {
        jdbcTemplate.update(UPDATE_COURSE, course.getName(), course.getStartEndDate(), course.getLectorer(), course.getId());
        jdbcTemplate.update(DELETE_STUDENT_COURSE, course.getId());
        for (Student student :
                course.getStudents()) {
            jdbcTemplate.update(SQL_INSERT_STUDENTS, student.getId(), course.getId());
        }
    }

    @Override
    public List<Course> findAll() {
        return jdbcTemplate.query(SELECT_ALL, coursesResultSetExtractor);
    }

    @Override
    public Optional<Course> findById(Integer id) {
        try {
            return Optional.of(jdbcTemplate.query(SELECT_BY_COURSE_Id, courseResultSetExtractor, id));
        } catch (NullPointerException e) {
            return Optional.empty();
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Course> findByName(String name) {
        return jdbcTemplate.query(SELECT_BY_COURSE_NAME, coursesResultSetExtractor, name);
    }


}
