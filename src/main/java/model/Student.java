package model;

import javafx.scene.control.Button;

public class Student {
    String id, name, lastname, company, skill;

    Button update;

    public Student(String id, String name, String lastname, String company, String skill, Button update) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.company = company;
        this.skill = skill;
        this.update = update;


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public Button getUpdate() {
        return update;
    }

    public void setUpdate(Button update) {
        this.update = update;
    }
}
