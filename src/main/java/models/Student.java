package models;

import database.models.JavaSkillRating;
import database.models.Model;

public class Student implements Model {
    private Integer Id;
    private String matri_Id;
    private String firstName;
    private String lastName;
    private String corporation;
    private JavaSkillRating javaSkill;
    private String courseName;

    public Student(){
    }

    public Student(final Integer id, String matri_Id, String firstName, String lastName, String corporation, JavaSkillRating javaSkill, String courseName) {
        this.Id = id;
        this.matri_Id = matri_Id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.corporation = corporation;
        this.javaSkill = javaSkill;
        this.courseName = courseName;
    }

    public String getMatri_Id() {
        return matri_Id;
    }

    public String getCourseName() {
        return courseName;
    }

    public JavaSkillRating getJavaSkill() {
        return javaSkill;
    }

    public String getCorporation() {
        return corporation;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public Integer getId() {
        return Id;
    }

    @Override
    public void setId(Integer id) {
        this.Id=id;
    }
}
