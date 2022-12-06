package com.solution.survey.controller;

import com.solution.survey.dto.PageDTO;
import com.solution.survey.dto.SurveyDTO;
import com.solution.survey.model.entity.Survey;
import com.solution.survey.service.SurveyService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/surveys")
public class SurveyRestController {

    private final SurveyService surveyService;

    public SurveyRestController(SurveyService surveyService){
        this.surveyService = surveyService;

    }

    @PostMapping("/create")
    public ResponseEntity<SurveyDTO> create(@Valid @RequestBody final SurveyDTO dto) {
        Survey survey = SurveyDTO.convertToSurvey(dto);
        survey = surveyService.create(survey);
        return ResponseEntity.ok(SurveyDTO.createFromSurvey(survey, true));
    }

    @PostMapping("/update")
    public ResponseEntity<SurveyDTO> update(@Valid @RequestBody final SurveyDTO dto) {
        Survey survey = SurveyDTO.convertToSurvey(dto);
        survey = surveyService.update(survey);
        return ResponseEntity.ok(SurveyDTO.createFromSurvey(survey, true));
    }

    /**
     * Find Survey with its name and description. Doesn't return questions and answer options.
     * Can be used for searching available surveys.
     * @param name - survey.name
     * @return SurveyDTO with its name and description.
     */
    @GetMapping("/find/byName/{name}")
    public ResponseEntity<SurveyDTO> findByName(@PathVariable final String name) {
        Survey survey = surveyService.findByName(name).orElse(null);
        if (survey == null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(SurveyDTO.createFromSurvey(survey, false));
    }

    /**
     * Find Surveys on page with their names and descriptions. Doesn't return questions and answer options.
     * Can be used for searching available surveys.
     * @param pageDTO - contains page number and page size
     * @return List of SurveyDTO with their names and descriptions.
     */
    @PostMapping("/findAllOnPage")
    public ResponseEntity<List<SurveyDTO>> findAllOnPage(@Valid @RequestBody PageDTO pageDTO) {
        Pageable page = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), Sort.by("id").ascending());

        List<Survey> surveys = surveyService.findAllOnPage(page);
        if (surveys == null || surveys.isEmpty())
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(
                    surveys.stream().map(survey -> SurveyDTO.createFromSurvey(survey, false)).collect(Collectors.toList()));
    }

    /**
     * Find full Survey. Also contains questions and answer options.
     * Can be used for getting a full survey.
     * @param id - survey.id
     * @return SurveyDTO with its name and description.
     */
    @GetMapping("/get/byId/{id}")
    public ResponseEntity<SurveyDTO> findById(@PathVariable final Long id) {
        Survey survey = surveyService.findById(id).orElse(null);
        if (survey == null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(SurveyDTO.createFromSurvey(survey, true));
    }
}
