package br.com.alura.school.video;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.io.Serializable;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Video implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String video;

    public Video(){

    }

    public Video(Long id, String video) {
        this.id = id;
        this.video = video;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Video video = (Video) o;
        return id.equals(video.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
