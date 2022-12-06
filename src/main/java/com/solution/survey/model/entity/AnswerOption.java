package com.solution.survey.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;


@Entity
@Table(name = "ANSWER_OPTIONS")
public class AnswerOption extends BaseEntity {

    @Column(name = "TEXT")
    @NotBlank
    private String text;

    @ManyToOne
    @JoinColumn(name = "QUESTION_ID", nullable = false)
    @NotNull
    private Question question;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question questionId) {
        this.question = questionId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final AnswerOption answerOption = (AnswerOption) obj;
        return Objects.equals(this.getId(), answerOption.getId()) && text.equals(answerOption.getText())
                && Objects.equals(this.question, answerOption.getQuestion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), text);
    }

    @Override
    public String toString() {
        return "AnswerOption{" +
                "id=" + this.getId() +
                ", text=" + text +
                '}';
    }
}
