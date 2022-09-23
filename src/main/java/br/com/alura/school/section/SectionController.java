package br.com.alura.school.section;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;


@RestController
public class SectionController {


    private final SectionService sectionService;

    SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @PostMapping(value = "/courses/{code}/sections")
    ResponseEntity<NewSectionRequest> newSection(@PathVariable String code, @RequestBody @Valid NewSectionRequest newSectionRequest) {
        newSectionRequest = sectionService.insert(code, newSectionRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newSectionRequest.getId()).toUri();
        return ResponseEntity.created(uri).body(newSectionRequest);
    }


}
