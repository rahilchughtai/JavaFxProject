package models;

import database.models.JavaSkillRating;
import database.models.Model;

public class Student implements Model {

    private String matri_Id;
    private String FirstName;
    private String LastName;
    private String Corporation;
    private JavaSkillRating JavaSkill;

    public Student(){

    }

    public Student(final String matri_id, final String firstName,final String lastName, final String corporation, final JavaSkillRating javaSkill) {
        matri_Id = matri_id;
        FirstName = firstName;
        LastName = lastName;
        Corporation = corporation;
        JavaSkill = javaSkill;
    }

    public JavaSkillRating getJavaSkill() {
        return JavaSkill;
    }

    public String getCorporation() {
        return Corporation;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public void setId(Integer id) {

    }
}
