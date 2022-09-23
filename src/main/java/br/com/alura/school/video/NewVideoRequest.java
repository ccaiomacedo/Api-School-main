package br.com.alura.school.video;


import br.com.alura.school.support.validation.Unique;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class NewVideoRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank(message = "Required field")

    @Unique(entity = Video.class, field = "video")
    private String video;

    private String sectionCode;

    public NewVideoRequest(Long id, String video, String sectionCode) {
        this.id = id;
        this.video = video;
        this.sectionCode = sectionCode;
    }

    public NewVideoRequest(Video video) {
        id = video.getId();
        this.video = video.getVideo();
        sectionCode = video.getSection().getCode();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }
}
