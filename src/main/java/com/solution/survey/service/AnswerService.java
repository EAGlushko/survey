package com.solution.survey.service;

import com.solution.survey.model.entity.Answer;
import com.solution.survey.model.entity.AnswerOption;
import com.solution.survey.repository.AnswerOptionRepository;
import com.solution.survey.repository.AnswerRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


@Service
public class AnswerService extends BaseService<Answer> {

    private final AnswerRepository answerRepository;

    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Override
    protected JpaRepository<Answer, Long> getRepository() {
        return this.answerRepository;
    }
}