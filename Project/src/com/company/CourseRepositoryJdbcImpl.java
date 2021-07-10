package com.company;

import com.company.models.Course;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class CourseRepositoryJdbcImpl implements CourseRepository{
    private final DataSource dataSource;

    public CourseRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    private static final String SELECT_BY_SEQ_ID = "select id from course where id in(select last_value from course_id_seq)";
    private static final String INSERT_COURSE = "insert into course (name, start_end_date, lecturer_id) values (?, ?, ?)";
    private static final String INSERT_STUDENTS_FROM_COURSE = "insert into student_course (id_student, id_course) values (?, ?)";
    private static final String DELETE_COURSE_BY_ID = "Delete from course where id = ?";
    private static final String DELETE_LIST_STUDENTS_FROM_COURSE_BY_ID = "Delete from student_course where id_course = ?";
    private static final String SELECT_ALL_COURSE =
            "SELECT id,name,start_end_date,lecturer_id,id_student from student_course sc inner join course on course.id = sc.id_course order by id";
    private static final String SELECT_COURSE_BY_ID =
            "SELECT id,name,start_end_date,lecturer_id,id_student from student_course sc inner join course on course.id = sc.id_course where id_course = ?";
    private static final String SELECT_COURSE_BY_NAME =
            "Select * from Course inner join student_course sc on Course.id = sc.id_course where name = ? order by id";
    private static final String UPDATE_COURSE_BY_ID =
            "Update course set name = ? ,start_end_date=?,lecturer_id = ? where id = ?";
    Function<ResultSet,Course> function = row -> {
        try {
            Integer id = row.getInt("id");
            String name = row.getString("name");
            String startEndDate = row.getString("start_end_date");
            Integer idLecturer = row.getInt("lecturer_id");
            Set<Integer> students = new HashSet<>();
            students.add(row.getInt("id_student"));
            while(row.next()){
                students.add(row.getInt("id_student"));
            }

            return new Course(id,name,startEndDate,idLecturer,students);
        } catch (SQLException e) {
           throw new IllegalArgumentException(e);
        }

    };
        /*
        * save data in table "course"
        * and list of students on Course in table "student_course"
        * */

    @Override
    public void save(Course course) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet row = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(INSERT_COURSE);

            preparedStatement.setString(1,course.getName());
            preparedStatement.setString(2, course.getStartEndDate());
            preparedStatement.setInt(3,course.getIdLectorer());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(SELECT_BY_SEQ_ID);
            row = preparedStatement.executeQuery();
            row.next();
            Integer idCourseBySeq = row.getInt("id");
            preparedStatement = connection.prepareStatement(INSERT_STUDENTS_FROM_COURSE);
            preparedStatement.setInt(2,idCourseBySeq);

            for (Integer students:
                    course.getIdStudent()) {
                preparedStatement.setInt(1,students);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        finally {
            if(row!=null) {
                try {
                    row.close();
                } catch (SQLException e) {
                    throw new IllegalArgumentException(e);
                }
            }
            if(preparedStatement!=null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new IllegalArgumentException(e);
                }
            }
            if(connection!=null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }
    }
    /*
     * updates only data from Course,
     * list of students on Course isn't updated
     * */

    @Override
    public void update(Course course) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(UPDATE_COURSE_BY_ID);
            statement.setString(1,course.getName());
            statement.setString(2,course.getStartEndDate());
            statement.setInt(3,course.getIdLectorer());
            statement.setInt(4,course.getId());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        finally {
            if(statement!=null) {
                try {
                   statement.close();
                } catch (SQLException e) {
                    throw new IllegalArgumentException(e);
                }
            }
            if(connection!=null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }

    }
        /*
        * Deletes course from table "course" and
        * list of students from table "student_course"
        *
        * */
    @Override
    public void delete(Integer id) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(DELETE_LIST_STUDENTS_FROM_COURSE_BY_ID);
            statement.setInt(1,id);
            statement.executeUpdate();
            statement = connection.prepareStatement(DELETE_COURSE_BY_ID);
            statement.setInt(1,id);
            statement.executeUpdate();


        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        finally {
            if(statement!=null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new IllegalArgumentException(e);
                }
            }
            if(connection!=null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }
    }
    /*
     * find all course with list of students
     * */

    @Override
    public List<Course> findAll() {
        List<Course> list  = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet row = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(SELECT_ALL_COURSE,
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            row = statement.executeQuery();
            while(row.next()) {
                Integer id = row.getInt("id");
                String name = row.getString("name");
                String startEndDate = row.getString("start_end_date");
                Integer idLecturer = row.getInt("lecturer_id");
                Set<Integer> students = new HashSet<>();
                do {
                    students.add(row.getInt("id_student"));
                }while(row.next()&&id==row.getInt("id"));
                Course course = new Course(id,name,startEndDate,idLecturer,students);
                list.add(course);
                row.previous();
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        finally {
            if(row!=null) {
                try {
                    row.close();
                } catch (SQLException e) {
                    throw new IllegalArgumentException(e);
                }
            }
            if(statement!=null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new IllegalArgumentException(e);
                }
            }
            if(connection!=null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }
        return list;
    }
    /*
    * find by id Course with list of students
    * */
    @Override
    public Course findById(Integer id) {
        Course course = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet row = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(SELECT_COURSE_BY_ID);
            statement.setInt(1,id);
            row = statement.executeQuery();
            row.next();
            course = function.apply(row);

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        finally {
            if(row!=null) {
                try {
                    row.close();
                } catch (SQLException e) {
                    throw new IllegalArgumentException(e);
                }
            }
            if(statement!=null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new IllegalArgumentException(e);
                }
            }
            if(connection!=null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }
        return course;
    }
    /*
    * find all course by certain name with list of students
    * */
    @Override
    public List<Course> findByName(String name) {
        List<Course> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet row = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(SELECT_COURSE_BY_NAME,
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statement.setString(1,name);
            row = statement.executeQuery();
            while(row.next()) {
                Integer id = row.getInt("id");
                String startEndDate = row.getString("start_end_date");
                Integer idLecturer = row.getInt("lecturer_id");
                Set<Integer> students = new HashSet<>();
                do {
                    students.add(row.getInt("id_student"));
                }while(row.next()&&id==row.getInt("id"));
                Course course = new Course(id,name,startEndDate,idLecturer,students);
                list.add(course);
                row.previous();
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        {
            if(row!=null) {
                try {
                    row.close();
                } catch (SQLException e) {
                    throw new IllegalArgumentException(e);
                }
            }
            if(statement!=null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new IllegalArgumentException(e);
                }
            }
            if(connection!=null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }
        return list;
    }
}
