package com.company.models;

public class Lesson {
    private Integer id;
    private String name;
    private String timeOfLesson;
    private Integer idCourse;

    public Lesson(Integer id, String name, String timeOfLesson, Integer idCourse) {
        this.id = id;
        this.name = name;
        this.timeOfLesson = timeOfLesson;
        this.idCourse = idCourse;
    }

    public Lesson(String name, String timeOfLesson, Integer idCourse) {
        this.name = name;
        this.timeOfLesson = timeOfLesson;
        this.idCourse = idCourse;
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

    public Integer getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(Integer idCourse) {
        this.idCourse = idCourse;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", timeOfLesson='" + timeOfLesson + '\'' +
                ", idCourse=" + idCourse +
                '}';
    }
}
