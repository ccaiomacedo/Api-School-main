package br.com.alura.school.section;

import br.com.alura.school.support.validation.Unique;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class NewSectionRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @Unique(entity = Section.class, field = "code")
    @NotBlank(message = "Required field")
    private String code;

    @NotBlank(message = "Required field")
    @Size(min = 5, message = "The minimum characters is 5")
    private String title;

    @NotBlank(message = "Required field")
    private String authorUsername;

    private String courseCode;

    public NewSectionRequest(Long id, String code, String title, String authorUsername, String courseCode) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.authorUsername = authorUsername;
        this.courseCode = courseCode;
    }

    public NewSectionRequest(Section section) {
        id = section.getId();
        code = section.getCode();
        title = section.getTitle();
        authorUsername = section.getAuthorUsername().getUsername();
        courseCode = section.getCourse().getCode();
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
}
