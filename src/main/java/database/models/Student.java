package database.models;

import java.util.Objects;

public class Student implements Model {

    private Integer id;
    private String matriculationNumber;
    private String firstName;
    private String lastName;
    private JavaSkillRating javaSkillRating;
    private String corporationName;
    private Course course;

    public Student() {
    }

    public Student(
            final Integer id,
            final String matriculationNumber,
            final String firstName,
            final String lastName,
            final JavaSkillRating javaSkillRating,
            final String corporationName,
            final Course course) {
        this.id = id;
        this.matriculationNumber = matriculationNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.javaSkillRating = javaSkillRating;
        this.corporationName = corporationName;
        this.course = course;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMatriculationNumber() {
        return matriculationNumber;
    }

    public void setMatriculationNumber(String matriculationNumber) {
        this.matriculationNumber = matriculationNumber;
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

    public JavaSkillRating getJavaSkillRating() {
        return javaSkillRating;
    }

    public void setJavaSkillRating(JavaSkillRating javaSkillRating) {
        this.javaSkillRating = javaSkillRating;
    }

    public String getCorporationName() {
        return corporationName;
    }

    public void setCorporationName(final String corporationName) {
        this.corporationName = corporationName;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(final Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", matriculationNumber='" + matriculationNumber + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", javaSkillRating=" + javaSkillRating +
                ", corporationName='" + corporationName + '\'' +
                ", course=" + course +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id) && Objects.equals(matriculationNumber, student.matriculationNumber) && Objects.equals(firstName, student.firstName) && Objects.equals(lastName, student.lastName) && javaSkillRating == student.javaSkillRating && Objects.equals(corporationName, student.corporationName) && Objects.equals(course, student.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, matriculationNumber, firstName, lastName, javaSkillRating, corporationName, course);
    }
}
