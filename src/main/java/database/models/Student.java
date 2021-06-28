package database.models;

public class Student {
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
}
