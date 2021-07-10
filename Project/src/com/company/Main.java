package com.company;

import com.company.models.Course;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class Main {

    public static void main(String[] args) {

        Properties properties = new Properties();
        try {
            properties.load(new FileReader("resources\\application.properties"));
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
        CourseRepository courseRepository = new CourseRepositoryJdbcImpl(dataSource);
        System.out.println(courseRepository.findByName("Algebra"));
        //System.out.println(courseRepository.findById(5));
        //System.out.println(courseRepository.findByName("Math"));
        //System.out.println(courseRepository.findAll());

        /* Set<Integer> students = new HashSet<>();
        students.add(12);
        students.add(23);
        students.add(13);
        students.add(34);
        students.add(25);
        students.add(36);
        students.add(47);
        Course course = new Course(4,"Game","11.09.2021/12.12.2022",36,students);

        courseRepository.update(course);
        courseRepository.save(course);
        courseRepository.delete(course.getId());
        */

    }
}
