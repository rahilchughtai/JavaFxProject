package model;


import controllers.CoursesController;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;

public class Course {
    String cid, courseName, room;

    Button update;

    public Course(String cid, String courseName, String room, Button update) {
        this.cid = cid;
        this.courseName = courseName;
        this.room = room;
        this.update = update;

        update.setOnAction(e ->{

            ObservableList<Course> courses = CoursesController.table_info_2.getSelectionModel().getSelectedItems();

            for (Course course: courses) {
                if (course.getUpdate() == update) {
                    System.out.println("id: "+ course.getCid());
                    System.out.println("course: "+ course.getCourseName());
                    System.out.println("room: "+ course.getRoom());
                }

            }
        });

    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourse(String course) {
        this.courseName = course;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Button getUpdate() {
        return update;
    }

    public void setUpdate(Button update) {
        this.update = update;
    }
}
