package company;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import company.models.Course;
import company.models.Lesson;
import company.models.Student;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class Main {

    public static void main(String[] args) {

        Properties properties = new Properties();
        try {
            properties.load(ClassLoader.getSystemResourceAsStream("application.properties"));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        //HikariConnectionPool
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(properties.getProperty("db.driver"));
        config.setUsername(properties.getProperty("db.user"));
        config.setPassword(properties.getProperty("db.password"));
        config.setJdbcUrl(properties.getProperty("db.url"));

        DataSource dataSource = new HikariDataSource(config);
        CourseRepository courseRepository = new CourseRepositoryJdbcTemplateImpl(dataSource);
        LessonRepository lessonRepository = new LessonRepositoryJdbcTemplateImpl(dataSource);

        /*Student student1 = new Student(1,"Erich","Cornelleau","11-002");
        Student student2 = new Student(2,"Lenore","Ferretti","11-002");
        Set<Student> students = new HashSet<>();
        students.add(student1);
        students.add(student2);
        Course course = new Course(5,"Chemistry","19.09.2021/05.05.2022",2,students);
        Lesson lesson = new Lesson("PPPPP","Saturday/11:00",course);
        lessonRepository.save(lesson)*/;

    }
}
