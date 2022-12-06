package com.solution.survey.dto;

import com.solution.survey.model.entity.AnswerOption;
import com.solution.survey.model.entity.Question;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionDTO {

    private Long id;

    @NotBlank
    private String text;

    private boolean enabled = true;

    private SurveyDTO survey;

    @Valid
    private List<AnswerOptionDTO> answerOptions;

    public QuestionDTO() {

    }

    public static QuestionDTO createFromQuestion(Question question) {
        final QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setId(question.getId());
        questionDTO.setText(question.getText());
        questionDTO.setEnabled(question.isEnabled());
        questionDTO.setAnswerOptions(question.getAnswerOptions().stream().map(AnswerOptionDTO::createFromAnswerOption).collect(Collectors.toList()));
        return questionDTO;
    }

    public static Question convertToQuestion(QuestionDTO dto) {
        final Question question = new Question();
        question.setId(dto.getId());
        question.setText(dto.getText());
        question.setEnabled(dto.isEnabled());
        question.setAnswerOptions(dto.getAnswerOptions().stream().map(aODTO -> {
            AnswerOption answerOption = AnswerOptionDTO.convertToAnswerOption(aODTO);
            answerOption.setQuestion(question);
            return answerOption;
        }).collect(Collectors.toList()));
        return question;
    }

    public SurveyDTO getSurvey() {
        return survey;
    }

    public void setSurvey(SurveyDTO survey) {
        this.survey = survey;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<AnswerOptionDTO> getAnswerOptions() {
        return answerOptions;
    }

    public void setAnswerOptions(List<AnswerOptionDTO> answerOptions) {
        this.answerOptions = answerOptions;
    }

}
