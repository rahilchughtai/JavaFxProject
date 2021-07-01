package models;

public class Student {
    private Integer Id;
    private String matriculationNumber;
    private String firstName;
    private String lastName;
    private String corporationName;
    private JavaSkillRating javaSkill;
    private Integer courseId;
    private String courseName;

    public Student(){
    }

    public Student(
            final Integer id,
            final String matriculationNumber,
            final String firstName,
            final String lastName,
            final String corporationName,
            final Integer courseId,
            final String courseName,
            final JavaSkillRating javaSkill) {

        this.setId(id);
        this.setMatriculationNumber(matriculationNumber);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setCorporationName(corporationName);
        this.setJavaSkill(javaSkill);
        this.setCourseId(courseId);
        this.setCourseName(courseName);
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
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

    public String getCorporationName() {
        return corporationName;
    }

    public void setCorporationName(String corporationName) {
        this.corporationName = corporationName;
    }

    public JavaSkillRating getJavaSkill() {
        return javaSkill;
    }

    public void setJavaSkill(JavaSkillRating javaSkill) {
        this.javaSkill = javaSkill;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
