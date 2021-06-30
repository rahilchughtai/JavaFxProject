package models;

import java.util.Objects;

public class Course {

    private Integer id;
    private String name;
    private Integer roomId;
    private String roomName;

    public Course() {
    }

    public Course(final Integer id, final String name, final Integer roomId, final String roomName) {
        this.setId(id);
        this.setName(name);
        this.setRoomId(roomId);
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

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", roomId=" + roomId +
                ", roomName='" + roomName + '\'' +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(id, course.id) && Objects.equals(name, course.name) && Objects.equals(roomId, course.roomId) && Objects.equals(roomName, course.roomName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, roomId, roomName);
    }
}
