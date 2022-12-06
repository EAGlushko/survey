package com.solution.survey.dto;

import com.solution.survey.model.entity.AnswerOption;
import com.solution.survey.model.entity.Question;

import javax.validation.constraints.NotBlank;


public class AnswerOptionDTO {

    private Long id;

    @NotBlank
    private String text;

    private Question question;

    public AnswerOptionDTO() {

    }


    public static AnswerOptionDTO createFromAnswerOption(AnswerOption answerOption) {
        final AnswerOptionDTO answerOptionDTO = new AnswerOptionDTO();
        answerOptionDTO.setId(answerOption.getId());
        answerOptionDTO.setText(answerOption.getText());
        return answerOptionDTO;
    }

    public static AnswerOption convertToAnswerOption(AnswerOptionDTO dto) {
        final AnswerOption answerOption = new AnswerOption();
        answerOption.setId(dto.getId());
        answerOption.setText(dto.getText());
        return answerOption;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
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

}
