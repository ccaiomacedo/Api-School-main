package br.com.alura.school.section;

import br.com.alura.school.course.Course;
import br.com.alura.school.course.CourseRepository;
import br.com.alura.school.exceptions.DatabaseException;
import br.com.alura.school.exceptions.ResourceNotFoundException;
import br.com.alura.school.user.User;
import br.com.alura.school.user.UserRepository;
import br.com.alura.school.utils.ValidateUserRole;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class SectionService {

    private final SectionRepository sectionRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public SectionService(SectionRepository sectionRepository, UserRepository userRepository, CourseRepository courseRepository) {
        this.sectionRepository = sectionRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    public NewSectionRequest insert(String code, NewSectionRequest newSectionRequest) {
        try {
            Section section = new Section();
            copyDtoToEntity(newSectionRequest, section);
            Course course = courseRepository.findByCode(code).orElseThrow(() -> new ResourceNotFoundException("Course not found"));
            section.setCourse(course);
            section = sectionRepository.save(section);
            return newSectionRequest = new NewSectionRequest(section);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }

    }


    private void copyDtoToEntity(NewSectionRequest dto, Section entity) {
        entity.setCode(dto.getCode());
        entity.setTitle(dto.getTitle());
        User user = userRepository.findByUsername(dto.getAuthorUsername()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        ValidateUserRole.validateUser(user);
        entity.setAuthorUsername(user);
    }
}
