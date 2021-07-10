package company.models;

import java.util.Set;

public class Student {
    private Integer id;
    private String firstName;
    private String lastName;
    private String numGroup;
    private Set<Integer> idCourses;

    public Student(Integer id, String firstName, String lastName, String numGroup, Set<Integer> idCourses) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.numGroup = numGroup;
        this.idCourses = idCourses;
    }

    public Student(String firstName, String lastName, String numGroup, Set<Integer> idCourses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.numGroup = numGroup;
        this.idCourses = idCourses;
    }

    public Student(Integer id, String firstName, String lastName, String numGroup) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.numGroup = numGroup;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNumGroup() {
        return numGroup;
    }

    public void setNumGroup(String numGroup) {
        this.numGroup = numGroup;
    }

    public Set<Integer> getIdCourses() {
        return idCourses;
    }

    public void setIdCourses(Set<Integer> idCourses) {
        this.idCourses = idCourses;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", numGroup='" + numGroup + '\'' +
                '}';
    }
}
