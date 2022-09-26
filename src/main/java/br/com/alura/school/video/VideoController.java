package br.com.alura.school.video;

import br.com.alura.school.course.Course;
import br.com.alura.school.course.CourseRepository;
import br.com.alura.school.exceptions.ResourceNotFoundException;
import br.com.alura.school.section.Section;
import br.com.alura.school.section.SectionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class VideoController {


    private final VideoRepository videoRepository;
    private final CourseRepository courseRepository;
    private final SectionRepository sectionRepository;

    public VideoController(VideoRepository videoRepository, CourseRepository courseRepository, SectionRepository sectionRepository) {
        this.videoRepository = videoRepository;
        this.courseRepository = courseRepository;
        this.sectionRepository = sectionRepository;
    }

    @PostMapping(value = "/courses/{courseCode}/sections/{sectionCode}")
    ResponseEntity<NewVideoRequest> newVideo(@PathVariable String courseCode, @PathVariable String sectionCode, @RequestBody @Valid NewVideoRequest newVideoRequest) {
        Course course = courseRepository.findByCode(courseCode).orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        List<Section> list = sectionRepository.findByCourse(course);
        if (list.isEmpty()) {
            throw new ResourceNotFoundException("Section not found for course " + courseCode);
        }
        Section section = sectionRepository.findByCode(sectionCode).orElseThrow(() -> new ResourceNotFoundException("Section not found"));
        Video video = new Video(section);
        copyDtoToEntity(newVideoRequest, video);

        video = videoRepository.save(video);
        newVideoRequest = new NewVideoRequest(video);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newVideoRequest.getId()).toUri();
        return ResponseEntity.created(uri).body(newVideoRequest);
    }

    private void copyDtoToEntity(NewVideoRequest dto, Video entity) {
        entity.setVideo(dto.getVideo());
    }
}
