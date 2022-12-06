package com.solution.survey.repository;

import com.solution.survey.model.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {

    Optional<Survey> findByName(String name);
}
