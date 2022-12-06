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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SurveyRepositoryTest {

    @Autowired
    private SurveyRepository repository;

    private static Survey survey;

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

        survey = new Survey();
        survey.setName("surveyName");
        survey.setDescription("surveyDescription");

        List<Question> questionList = new ArrayList<>();
        Question question1 = new Question();
        question1.setText("question1Text");

        questionList.add(question1);
        Question question2 = new Question();
        question2.setText("question2Text");
        questionList.add(question2);

        List<AnswerOption> answerOptionList1 = new ArrayList<>();
        AnswerOption answerOption1 = new AnswerOption();
        answerOption1.setText("answerOptionText1");
        answerOption1.setQuestion(question1);
        answerOptionList1.add(answerOption1);
        AnswerOption answerOption2 = new AnswerOption();
        answerOption2.setText("answerOptionText2");
        answerOption2.setQuestion(question1);
        answerOptionList1.add(answerOption2);

        List<AnswerOption> answerOptionList2 = new ArrayList<>();
        AnswerOption answerOption3 = new AnswerOption();
        answerOption3.setText("answerOptionText3");
        answerOption3.setQuestion(question2);
        answerOptionList2.add(answerOption3);
        AnswerOption answerOption4 = new AnswerOption();
        answerOption4.setText("answerOptionText4");
        answerOption4.setQuestion(question2);
        answerOptionList2.add(answerOption4);

        question1.setAnswerOptions(answerOptionList1);
        question1.setSurvey(survey);
        question2.setAnswerOptions(answerOptionList2);
        question2.setSurvey(survey);
        survey.setQuestions(questionList);

    }

    @Test
    public void getSurveyWithName() {
        Survey survey = repository.findByName("SurveyName1").orElse(null);
        Assertions.assertNotNull(survey);
    }

    @Test
    public void saveSurvey() {
        Survey result = repository.saveAndFlush(survey);
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getId());
    }

    @Test
    public void getExceptionWhenSaveSurveyWithExistedName() {
        Survey badServey = new Survey();
        badServey.setName("SurveyName1");
        badServey.setDescription(survey.getDescription());
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            repository.saveAndFlush(badServey);
        });
    }

}
