package com.solution.survey.repository;

import com.solution.survey.model.entity.AnswerOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AnswerOptionRepository extends JpaRepository<AnswerOption, Long> {
}
