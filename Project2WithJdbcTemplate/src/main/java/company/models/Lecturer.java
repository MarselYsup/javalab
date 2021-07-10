package company.models;

import java.util.Set;

public class Lecturer {
    private Integer id;
    private String firstName;
    private String lastName;
    private Integer experience;
    private Set<Integer> idCourses;

    public Lecturer(Integer id, String firstName, String lastName, Integer experience, Set<Integer> idCourses) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.experience = experience;
        this.idCourses = idCourses;
    }

    public Lecturer(String firstName, String lastName, Integer experience, Set<Integer> idCourses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.experience = experience;
        this.idCourses = idCourses;
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

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Set<Integer> getIdCourses() {
        return idCourses;
    }

    public void setIdCourses(Set<Integer> idCourses) {
        this.idCourses = idCourses;
    }

    @Override
    public String toString() {
        return "Lecturer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", experience=" + experience +
                ", idCourses=" + idCourses +
                '}';
    }
}
