package com.solution.survey.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "QUESTIONS")
public class Question extends BaseEntity {

    @Column(name = "TEXT")
    @NotBlank
    private String text;

    @ManyToOne
    @JoinColumn(name = "SURVEY_ID", nullable = false)
    @NotNull
    private Survey survey;

    @Column(name = "ENABLED", columnDefinition = "boolean default true")
    private Boolean enabled = true;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<AnswerOption> answerOptions = new ArrayList<>();

    public List<AnswerOption> getAnswerOptions() {
        return answerOptions;
    }

    public void setAnswerOptions(List<AnswerOption> answerOptions) {
        this.answerOptions = answerOptions;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled == null || enabled;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Question question = (Question) obj;
        return Objects.equals(this.getId(), question.getId()) && text.equals(question.getText())
                && question.isEnabled() == this.enabled;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), text);
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + this.getId() +
                ", text=" + text +
                ", enabled='" + enabled +
                ", answerOptions='" + answerOptions +
                '}';
    }
}
