package br.com.alura.school.section;

import java.io.Serializable;

public class SectionReportResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String courseName;
    private String title;
    private String authorName;
    private Long totalVideos;

    public SectionReportResponse(){

    }

    public SectionReportResponse(String courseName, String title, String authorName, Long totalVideos) {
        this.courseName = courseName;
        this.title = title;
        this.authorName = authorName;
        this.totalVideos = totalVideos;
    }


    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Long getTotalVideos() {
        return totalVideos;
    }

    public void setTotalVideos(Long totalVideos) {
        this.totalVideos = totalVideos;
    }
}
