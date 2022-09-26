package br.com.alura.school.section;

import br.com.alura.school.course.Course;
import br.com.alura.school.course.CourseRepository;
import br.com.alura.school.exceptions.ResourceNotFoundException;
import br.com.alura.school.user.User;
import br.com.alura.school.user.UserRepository;
import br.com.alura.school.utils.ValidateUserRole;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
public class SectionController {


    private final SectionRepository sectionRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public SectionController(SectionRepository sectionRepository, UserRepository userRepository, CourseRepository courseRepository) {
        this.sectionRepository = sectionRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @PostMapping(value = "/courses/{code}/sections")
    ResponseEntity<NewSectionRequest> newSection(@PathVariable String code, @RequestBody @Valid NewSectionRequest newSectionRequest) {
        Course course = courseRepository.findByCode(code).orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        Section section = new Section(course);
        copyDtoToEntity(newSectionRequest, section);

        section = sectionRepository.save(section);
        newSectionRequest = new NewSectionRequest(section);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newSectionRequest.getId()).toUri();
        return ResponseEntity.created(uri).body(newSectionRequest);
    }

    @GetMapping(value = "/sectionByVideosReport")
    ResponseEntity sectionReport() {
        List<SectionReportResponse> sectionReportResponseList = sectionRepository.generateReport();
        if (sectionReportResponseList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(sectionReportResponseList);
    }

    private void copyDtoToEntity(NewSectionRequest dto, Section entity) {
        entity.setCode(dto.getCode());
        entity.setTitle(dto.getTitle());
        User user = userRepository.findByUsername(dto.getAuthorUsername()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        ValidateUserRole.validateUserAsInstructor(user);
        entity.setAuthorUsername(user);
    }

}
