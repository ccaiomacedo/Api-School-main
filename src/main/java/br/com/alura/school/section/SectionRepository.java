package br.com.alura.school.section;

import br.com.alura.school.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SectionRepository extends JpaRepository<Section, Long> {

    Optional<Section> findByCode(String code);

    List<Section> findByCourse(Course course);

    @Query("SELECT new br.com.alura.school.section.SectionReportResponse(obj.course.name,obj.title,obj.authorUsername.username,COUNT(v.section.id)) FROM Section obj " +
            "LEFT JOIN obj.videos v on obj.id=v.section.id " +
            "JOIN Enrollment e on obj.course.id = e.course.id GROUP BY obj.course.id,obj.title ORDER BY COUNT(v.id) DESC")
    List<SectionReportResponse> generateReport();


}
