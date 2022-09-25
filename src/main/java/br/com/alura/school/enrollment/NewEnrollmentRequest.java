package br.com.alura.school.enrollment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class NewEnrollmentRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String courseCode;
    @Size(min = 3, max = 20, message = "username must contain betwen 3 and 20 characters")
    @NotBlank(message = "Required field")
    private String username;

    public NewEnrollmentRequest(){

    }

    public NewEnrollmentRequest(Long id, String courseCode, String username) {
        this.id = id;
        this.courseCode = courseCode;
        this.username = username;
    }

    public NewEnrollmentRequest(Enrollment enrollment) {
        id = enrollment.getId();
        courseCode = enrollment.getCourse().getCode();
        username = enrollment.getUser().getUsername();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
