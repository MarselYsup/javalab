package company.models;

public class Lesson {
    private Integer id;
    private String name;
    private String timeOfLesson;
    private Course course;

    public Lesson(Integer id, String name, String timeOfLesson, Course course) {
        this.id = id;
        this.name = name;
        this.timeOfLesson = timeOfLesson;
        this.course = course;
    }

    public Lesson(String name, String timeOfLesson, Course course) {
        this.name = name;
        this.timeOfLesson = timeOfLesson;
        this.course = course;
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

    public String getTimeOfLesson() {
        return timeOfLesson;
    }

    public void setTimeOfLesson(String timeOfLesson) {
        this.timeOfLesson = timeOfLesson;
    }

    public Course getCourse() {
        return course;
    }

    public void setIdCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", timeOfLesson='" + timeOfLesson + '\'' +
                ", course=" + course +
                '}';
    }
}
