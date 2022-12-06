package com.solution.survey.service;

import com.solution.survey.model.entity.Question;
import com.solution.survey.repository.QuestionRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


@Service
public class QuestionService extends BaseService<Question> {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    protected JpaRepository<Question, Long> getRepository() {
        return this.questionRepository;
    }
}