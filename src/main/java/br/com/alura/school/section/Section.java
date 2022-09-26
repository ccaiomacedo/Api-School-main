package br.com.alura.school.section;

import br.com.alura.school.course.Course;
import br.com.alura.school.user.*;
import br.com.alura.school.video.Video;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Section implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String title;

    @OneToOne
    private User authorUsername;

    @ManyToOne()
    private Course course;

    @OneToMany(mappedBy = "section")
    private Set<Video> videos = new HashSet<>();

    @Deprecated
    public Section() {

    }

    public Section(Course course) {
        this.course = course;
    }

    public Section(String code, String title, User authorUsername, Course course) {
        this.code = code;
        this.title = title;
        this.authorUsername = authorUsername;
        this.course = course;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(User authorUsername) {
        this.authorUsername = authorUsername;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Section section = (Section) o;
        return id.equals(section.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
