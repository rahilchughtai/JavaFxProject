package database.models;

import java.util.Objects;

public class Student implements Model {
    private Integer id;
    private String matriculationNumber;
    private String firstName;
    private String lastName;
    private JavaSkillRating javaSkillRating;
    private Corporation corporation;

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

    public Corporation getCorporation() {
        return corporation;
    }

    public void setCorporation(Corporation corporation) {
        this.corporation = corporation;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", matriculationNumber='" + matriculationNumber + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", javaSkillRating=" + javaSkillRating +
                ", corporation=" + corporation +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id) && Objects.equals(matriculationNumber, student.matriculationNumber) && Objects.equals(firstName, student.firstName) && Objects.equals(lastName, student.lastName) && javaSkillRating == student.javaSkillRating && Objects.equals(corporation, student.corporation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, matriculationNumber, firstName, lastName, javaSkillRating, corporation);
    }
}
