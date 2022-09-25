package br.com.alura.school.section;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
public class SectionController {


    private final SectionService sectionService;
    private final SectionRepository sectionRepository;

    public SectionController(SectionService sectionService, SectionRepository sectionRepository) {
        this.sectionService = sectionService;
        this.sectionRepository = sectionRepository;
    }

    @PostMapping(value = "/courses/{code}/sections")
    ResponseEntity<NewSectionRequest> newSection(@PathVariable String code, @RequestBody @Valid NewSectionRequest newSectionRequest) {
        newSectionRequest = sectionService.insert(code, newSectionRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newSectionRequest.getId()).toUri();
        return ResponseEntity.created(uri).body(newSectionRequest);
    }

    @GetMapping(value = "/sectionByVideosReport")
    ResponseEntity sectionReport() {
        List<SectionReportResponse> sectionReportResponseList = sectionRepository.generateReport();
        if (sectionReportResponseList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(sectionReportResponseList);
    }

}
