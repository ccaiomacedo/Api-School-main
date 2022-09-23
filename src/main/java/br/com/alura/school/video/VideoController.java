package br.com.alura.school.video;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class VideoController {


    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @PostMapping(value = "/courses/{courseCode}/sections/{sectionCode}")
    ResponseEntity<NewVideoRequest> newVideo(@PathVariable String courseCode, @PathVariable String sectionCode, @RequestBody @Valid NewVideoRequest newVideoRequest) {
        newVideoRequest = videoService.insert(courseCode, sectionCode, newVideoRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newVideoRequest.getId()).toUri();
        return ResponseEntity.created(uri).body(newVideoRequest);
    }
}
