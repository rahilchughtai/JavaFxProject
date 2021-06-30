package models;

import java.util.Objects;

public class Course {

    private Integer id;
    private String name;
    private String roomName;

    public Course() {
    }

    public Course(final String name, final String roomName) {
        this.setName(name);
        this.setRoomName(roomName);
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

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(id, course.id) && Objects.equals(name, course.name) && Objects.equals(roomName, course.roomName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, roomName);
    }
}
