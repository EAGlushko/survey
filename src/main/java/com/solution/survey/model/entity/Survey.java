package com.solution.survey.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "SURVEYS")
public class Survey extends BaseEntity {

    @Column(name = "NAME")
    @NotBlank
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @OneToMany(mappedBy = "survey", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Question> questions;

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Survey survey = (Survey) obj;
        return Objects.equals(this.getId(), survey.getId()) && name.equals(survey.getName()) && description.equals(
                survey.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), name);
    }

    @Override
    public String toString() {
        return "Survey{"
                + "id=" + this.getId() +
                ", name=" + name +
                ", description='" + description +
                ", questions='" + questions +
                '}';
    }
}
