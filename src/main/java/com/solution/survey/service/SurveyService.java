package com.solution.survey.service;

import com.solution.survey.model.entity.AnswerOption;
import com.solution.survey.model.entity.Question;
import com.solution.survey.model.entity.Survey;
import com.solution.survey.repository.SurveyRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class SurveyService extends BaseService<Survey> {

    private final SurveyRepository surveyRepository;
    private final QuestionService questionService;
    private final AnswerOptionService answerOptionService;

    public SurveyService(SurveyRepository surveyRepository, QuestionService questionService, AnswerOptionService answerOptionService) {
        this.surveyRepository = surveyRepository;
        this.questionService = questionService;
        this.answerOptionService = answerOptionService;
    }

    @Override
    protected JpaRepository<Survey, Long> getRepository() {
        return this.surveyRepository;
    }

    public Survey create(Survey survey) {
        return surveyRepository.save(survey);
    }

    public Survey update(Survey survey) {
        if (survey.getId() == null) {
            throw new EntityNotFoundException("The ID of the entity " + survey
                    + " was not passed. Unable to update");
        }
        return surveyRepository.save(survey);
    }

    public Optional<Survey> findByName(String name) {
        return Optional.of(surveyRepository.findByName(name));
    }

    public List<Survey> findAllOnPage(Pageable page) {
        return this.surveyRepository.findAll(page).toList();
    }
}
