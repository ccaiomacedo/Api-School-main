package br.com.alura.school.tests;

import br.com.alura.school.course.Course;
import br.com.alura.school.enrollment.Enrollment;
import br.com.alura.school.enrollment.NewEnrollmentRequest;
import br.com.alura.school.section.NewSectionRequest;
import br.com.alura.school.section.Section;
import br.com.alura.school.user.User;
import br.com.alura.school.user.UserRole;
import br.com.alura.school.video.NewVideoRequest;


public class Factory {

    public static Section createSection() {
        Section section = new Section("flutter-cores-dinamicas", "Flutter: Configurando cores dinâmicas", createUser(), createCourse());
        return section;
    }

    public static User createUser() {
        User user = new User("alex", "alex@email.com", UserRole.INSTRUCTOR);
        user.setId(1L);
        return user;
    }

    public static User createUserStudent() {
        User user = new User("alex", "alex@email.com", UserRole.STUDENT);
        user.setId(2L);
        return user;
    }

    public static Course createCourse() {
        Course course = new Course("java-2", "Java Collections", "Java Collections: Lists, Sets, Maps and more.");
        course.setId(1L);
        return course;
    }

    public static Enrollment createEnrollment() {
        return new Enrollment(createCourse(), createUser());
    }

    public static NewSectionRequest createNewSectionRequest() {
        return new NewSectionRequest(1L, "flutter-cores-dinamicas", "Flutter: Configurando cores dinâmicas", createUser().getUsername(), createCourse().getCode());
    }

    public static NewVideoRequest createNewVideoRequest() {
        return new NewVideoRequest(1L, "https://www.youtube.com/watch?v=gI4-vj0WpK", createSection().getCode());
    }

    public static NewEnrollmentRequest createNewEnrollmentRequest() {
        return new NewEnrollmentRequest(1L, createCourse().getCode(), createUser().getUsername());
    }


}
