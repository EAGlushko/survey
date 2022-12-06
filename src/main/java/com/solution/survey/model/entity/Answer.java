package com.solution.survey.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;


@Entity
@Table(name = "ANSWERS")
public class Answer extends BaseEntity{

    @NotNull
    @ManyToOne
    @JoinColumn(name = "ANSWER_OPTION_ID", nullable = false)
    private AnswerOption answerOption;

    @Column(name = "USER_ID")
    @NotNull // Can it be anonymous?
    private Long userId;


    public AnswerOption getAnswerOption() {
        return answerOption;
    }

    public void setAnswerOption(AnswerOption answerOption) {
        this.answerOption = answerOption;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Answer answer = (Answer) obj;
        return Objects.equals(this.getId(), answer.getId()) && answerOption.equals(answer.answerOption)
                && Objects.equals(this.userId, answer.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), answerOption, userId);
    }

    @Override
    public String toString() {
        return "AnswerOption{" + "id=" + this.getId() + ", answerOptionId=" + answerOption + ", userId='" + userId + '}';
    }
}
