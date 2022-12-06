package com.solution.survey.repository;

import com.solution.survey.model.entity.AnswerOption;
import com.solution.survey.model.entity.Question;
import com.solution.survey.model.entity.Survey;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import javax.validation.ConstraintViolationException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private SurveyRepository surveyRepository;

    private static Question question;

    @AfterAll
    static void cleanup(@Autowired DataSource dataSource) {
        try (Connection conn = dataSource.getConnection()) {
            conn.prepareStatement("Delete from answer_options").execute();
            conn.prepareStatement("Delete from questions").execute();
            conn.prepareStatement("Delete from surveys").execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeAll
    static void setup(@Autowired DataSource dataSource) {
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("integration/db/add_surveys.sql"));
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("integration/db/add_questions.sql"));
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("integration/db/add_answer_options.sql"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        question = new Question();
        question.setText("question1Text");
        question.setEnabled(null);

        List<AnswerOption> answerOptionList = new ArrayList<>();
        AnswerOption answerOption1 = new AnswerOption();
        answerOption1.setText("answerOptionText1");
        answerOption1.setQuestion(question);
        answerOptionList.add(answerOption1);
        AnswerOption answerOption2 = new AnswerOption();
        answerOption2.setText("answerOptionText2");
        answerOption2.setQuestion(question);
        answerOptionList.add(answerOption2);

        question.setAnswerOptions(answerOptionList);
    }

    @Test
    public void saveQuestion() {
        Survey survey = surveyRepository.findByName("SurveyName1");
        question.setSurvey(survey);
        Question result = questionRepository.saveAndFlush(question);
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getId());
        Assertions.assertTrue(result.isEnabled());
        Assertions.assertNotNull(result.getAnswerOptions());
        Assertions.assertNotNull(result.getAnswerOptions());
        Assertions.assertNotNull(result.getAnswerOptions().get(0));
        Assertions.assertNotNull(result.getAnswerOptions().get(0).getId());
    }

    @Test
    public void getExceptionWhenSaveSurveyIsNull() {
        Question badQuestion = new Question();
        badQuestion.setText("badQuestionText");
        badQuestion.setSurvey(null);
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            questionRepository.saveAndFlush(badQuestion);
        });
    }

    @Test
    public void getExceptionWhenTextIsNull() {
        Survey survey = surveyRepository.findByName("SurveyName1");
        Question badQuestion = new Question();
        badQuestion.setText("");
        badQuestion.setSurvey(survey);
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            questionRepository.saveAndFlush(badQuestion);
        });
    }


}
