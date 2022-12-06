package com.solution.survey.dto;

import com.solution.survey.model.entity.Question;
import com.solution.survey.model.entity.Survey;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;


public class SurveyDTO {

    private Long id;

    @NotBlank
    private String name;

    private String description;

    @Valid
    private List<QuestionDTO> questions;

    public SurveyDTO() {

    }


    /**
     * Create Survey dto from Survey entity
     *
     * @param survey  - Survey entity
     * @param cascade - if true, will put all questions and answer options
     * @return SurveyDTO
     */
    public static SurveyDTO createFromSurvey(Survey survey, boolean cascade) {
        final SurveyDTO surveyDTO = new SurveyDTO();
        surveyDTO.setId(survey.getId());
        surveyDTO.setName(survey.getName());
        surveyDTO.setDescription(survey.getDescription());
        if (cascade)
            surveyDTO.setQuestions(survey.getQuestions().stream().map(QuestionDTO::createFromQuestion).collect(Collectors.toList()));
        return surveyDTO;
    }

    /**
     * Create Survey entity from Survey dto
     *
     * @param dto - Survey dto
     * @return SurveyDTO
     */
    public static Survey convertToSurvey(SurveyDTO dto) {
        final Survey survey = new Survey();
        survey.setId(dto.getId());
        survey.setName(dto.getName());
        survey.setDescription(dto.getDescription());
        survey.setQuestions(dto.getQuestions().stream().map(questionDTO -> {
            Question question = QuestionDTO.convertToQuestion(questionDTO);
            question.setSurvey(survey);
            return question;
        }).collect(Collectors.toList()));
        return survey;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDTO> questions) {
        this.questions = questions;
    }


}
