package com.company.models;

import java.util.*;

public class Course {
    private Integer id;
    private String name;
    private String startEndDate;
    private Integer idLectorer;
    private Set<Integer> idStudent;

    public Course(Integer id, String name, String startEndDate, Integer idLectorer, Set<Integer> idStudent) {
        this.id = id;
        this.name = name;
        this.startEndDate = startEndDate;
        this.idLectorer = idLectorer;
        this.idStudent = idStudent;
    }

    public Course(String name, String startEndDate, Integer idLectorer, Set<Integer> idStudent) {
        this.name = name;
        this.startEndDate = startEndDate;
        this.idLectorer = idLectorer;
        this.idStudent = idStudent;
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

    public Integer getIdLectorer() {
        return idLectorer;
    }

    public void setIdLectorer(Integer idLectorer) {
        this.idLectorer = idLectorer;
    }

    public Set<Integer> getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(Set<Integer> idStudent) {
        this.idStudent = idStudent;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startEndDate='" + startEndDate + '\'' +
                ", idLectorer=" + idLectorer +
                ", idStudent=" + idStudent +
                '}';
    }
}
