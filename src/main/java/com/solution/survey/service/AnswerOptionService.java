package com.solution.survey.service;

import com.solution.survey.model.entity.AnswerOption;
import com.solution.survey.repository.AnswerOptionRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


@Service
public class AnswerOptionService extends BaseService<AnswerOption> {

    private final AnswerOptionRepository answerOptionRepository;

    public AnswerOptionService(AnswerOptionRepository answerOptionRepository) {
        this.answerOptionRepository = answerOptionRepository;
    }

    @Override
    protected JpaRepository<AnswerOption, Long> getRepository() {
        return this.answerOptionRepository;
    }
}