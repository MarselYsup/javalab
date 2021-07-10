package company.models;

import java.util.Set;

public class Course {
    private Integer id;
    private String name;
    private String startEndDate;
    private Integer lectorer;
    private Set<Student> students;

    public Course(Integer id, String name, String startEndDate, Integer lectorer, Set<Student> students) {
        this.id = id;
        this.name = name;
        this.startEndDate = startEndDate;
        this.lectorer = lectorer;
        this.students = students;
    }

    public Course(String name, String startEndDate, Integer lectorer, Set<Student> students) {
        this.name = name;
        this.startEndDate = startEndDate;
        this.lectorer = lectorer;
        this.students = students;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartEndDate() {
        return startEndDate;
    }

    public void setStartEndDate(String startEndDate) {
        this.startEndDate = startEndDate;
    }

    public Integer getLectorer() {
        return lectorer;
    }

    public void setLectorer(Integer lectorer) {
        this.lectorer = lectorer;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startEndDate='" + startEndDate + '\'' +
                ", lectorer=" + lectorer +
                ", students=" + students +
                '}';
    }
}
