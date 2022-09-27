package br.com.alura.school.section;

import br.com.alura.school.video.Video;
import br.com.alura.school.video.VideoResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SectionResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty
    private Long id;

    @JsonProperty
    private String code;

    @JsonProperty
    private String title;

    @JsonProperty
    private List<VideoResponse> videos = new ArrayList<>();

    public SectionResponse(Section section, Set<Video> videosOnSection) {
        id = section.getId();
        code = section.getCode();
        title = section.getTitle();
        videosOnSection.forEach(x -> {
            videos.add(new VideoResponse(x));
        });
    }
}
