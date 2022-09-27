package br.com.alura.school.video;

import com.fasterxml.jackson.annotation.JsonProperty;



public class VideoResponse {

    @JsonProperty
    private Long id;

    @JsonProperty
    private String video;

    public VideoResponse(Video videoEntity) {
        id = videoEntity.getId();
        video = videoEntity.getVideo();
    }

}
