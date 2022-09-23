package br.com.alura.school.video;

import br.com.alura.school.course.Course;
import br.com.alura.school.course.CourseRepository;
import br.com.alura.school.exceptions.ResourceNotFoundException;
import br.com.alura.school.section.Section;
import br.com.alura.school.section.SectionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoService {

    private final VideoRepository videoRepository;
    private final CourseRepository courseRepository;
    private final SectionRepository sectionRepository;

    public VideoService(VideoRepository videoRepository, CourseRepository courseRepository, SectionRepository sectionRepository) {
        this.videoRepository = videoRepository;
        this.courseRepository = courseRepository;
        this.sectionRepository = sectionRepository;
    }

    public NewVideoRequest insert(String courseCode, String sectionCode, NewVideoRequest newVideoRequest) {
        Video video = new Video();
        Course course = courseRepository.findByCode(courseCode).orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        List<Section> list = sectionRepository.findByCourse(course);
        if (list.isEmpty()) {
            throw new ResourceNotFoundException("Section not found for course " + courseCode);
        }
        Section section = sectionRepository.findByCode(sectionCode).orElseThrow(() -> new ResourceNotFoundException("Section not found"));
        video.setSection(section);
        copyDtoToEntity(newVideoRequest, video);

        videoRepository.save(video);
        return new NewVideoRequest(video);
    }

    private void copyDtoToEntity(NewVideoRequest dto, Video entity) {
        entity.setVideo(dto.getVideo());
    }
}
