package br.com.alura.school.course;

import br.com.alura.school.section.SectionResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class CourseResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty
    private final String code;

    @JsonProperty
    private final String name;

    @JsonProperty
    private final String shortDescription;

    @JsonProperty
    private final List<SectionResponse> sections = new ArrayList<>();

    CourseResponse(Course course) {
        this.code = course.getCode();
        this.name = course.getName();
        this.shortDescription = Optional.of(course.getDescription()).map(this::abbreviateDescription).orElse("");
        course.getSections().forEach(section -> {
            this.sections.add(new SectionResponse(section,section.getVideos()));
        });
    }

    private String abbreviateDescription(String description) {
        if (description.length() <= 13) return description;
        return description.substring(0, 10) + "...";
    }

}
