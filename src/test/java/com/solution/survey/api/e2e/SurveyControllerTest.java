package com.solution.survey.api.e2e;

import com.solution.survey.SurveyApplication;
import com.solution.survey.dto.AnswerOptionDTO;
import com.solution.survey.dto.PageDTO;
import com.solution.survey.dto.QuestionDTO;
import com.solution.survey.dto.SurveyDTO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SurveyApplication.class)
public class SurveyControllerTest {

    private static HttpHeaders headers = new HttpHeaders();

    private static SurveyDTO surveyDTO;

    @Autowired
    private TestRestTemplate client;

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

        headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        List<AnswerOptionDTO> answerOptionDTOList = new ArrayList<>();
        AnswerOptionDTO answerOptionDTO1 = new AnswerOptionDTO();
        answerOptionDTO1.setText("answerOptionText1");
        answerOptionDTOList.add(answerOptionDTO1);
        AnswerOptionDTO answerOptionDTO2 = new AnswerOptionDTO();
        answerOptionDTO2.setText("answerOptionText2");
        answerOptionDTOList.add(answerOptionDTO2);
        AnswerOptionDTO answerOptionDTO3 = new AnswerOptionDTO();
        answerOptionDTO3.setText("answerOptionText3");
        answerOptionDTOList.add(answerOptionDTO3);

        List<QuestionDTO> questionDTOList = new ArrayList<>();
        QuestionDTO questionDTO1 = new QuestionDTO();
        questionDTO1.setText("question1Text");
        questionDTO1.setAnswerOptions(answerOptionDTOList);
        questionDTOList.add(questionDTO1);
        QuestionDTO questionDTO2 = new QuestionDTO();
        questionDTO2.setText("question2Text");
        questionDTO2.setAnswerOptions(answerOptionDTOList);
        questionDTOList.add(questionDTO2);
        QuestionDTO questionDTO3 = new QuestionDTO();
        questionDTO3.setText("question3Text");
        questionDTO3.setAnswerOptions(answerOptionDTOList);
        questionDTOList.add(questionDTO3);

        surveyDTO = new SurveyDTO();
        surveyDTO.setName("surveyName");
        surveyDTO.setDescription("surveyDescription");
        surveyDTO.setQuestions(questionDTOList);
    }

    @Test
    public void findByPage() {
        PageDTO pageDTO = new PageDTO(0, 2);
        HttpEntity<PageDTO> request = new HttpEntity<>(pageDTO, headers);
        ResponseEntity<List<SurveyDTO>> response = client.exchange("/surveys/findAllOnPage", HttpMethod.POST, request, new ParameterizedTypeReference<List<SurveyDTO>>() {
        });
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(2, response.getBody().size());
        Assertions.assertEquals("SurveyName1",response.getBody().get(0).getName());
        Assertions.assertEquals("SurveyName2",response.getBody().get(1).getName());
    }

    @Test
    public void findByName() {
        ResponseEntity<SurveyDTO> response = client.exchange("/surveys/find/byName/SurveyName1", HttpMethod.GET, null, SurveyDTO.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    public void findByNextPage() {
        PageDTO pageDTO = new PageDTO(1, 2);
        HttpEntity<PageDTO> request = new HttpEntity<>(pageDTO, headers);
        ResponseEntity<List<SurveyDTO>> response = client.exchange("/surveys/findAllOnPage", HttpMethod.POST, request, new ParameterizedTypeReference<List<SurveyDTO>>() {
        });
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(1, response.getBody().size());
        Assertions.assertEquals("SurveyName3",response.getBody().get(0).getName());
    }

    @Test
    public void createSurvey() {
        surveyDTO.setName("SurveyCreateSurvey");
        HttpEntity<SurveyDTO> request = new HttpEntity<>(surveyDTO, headers);
        ResponseEntity<SurveyDTO> response = client.exchange("/surveys/create", HttpMethod.POST, request, SurveyDTO.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertNotNull(response.getBody().getId());
        Assertions.assertNotNull(response.getBody().getQuestions());
        Assertions.assertNotNull(response.getBody().getQuestions().get(0).getId());
        Assertions.assertNotNull(response.getBody().getQuestions().get(0).getAnswerOptions());
        Assertions.assertNotNull(response.getBody().getQuestions().get(0).getAnswerOptions().get(0).getId());
    }

    @Test
    public void createSurveyWithoutQuestions() {
        SurveyDTO newServey = new SurveyDTO();
        newServey.setName("SurveyWithoutQuestions");
        newServey.setDescription(surveyDTO.getDescription());
        newServey.setQuestions(Collections.emptyList());
        HttpEntity<SurveyDTO> request = new HttpEntity<>(newServey, headers);
        ResponseEntity<SurveyDTO> response = client.exchange("/surveys/create", HttpMethod.POST, request, SurveyDTO.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertNotNull(response.getBody().getId());
    }

    @Test
    public void getBadRequestWhenSurveyNameIsEmptyOrBlank() {
        SurveyDTO badServey = new SurveyDTO();
        badServey.setName("");
        badServey.setDescription(surveyDTO.getDescription());
        badServey.setId(surveyDTO.getId());
        HttpEntity<SurveyDTO> request = new HttpEntity<>(badServey, headers);
        ResponseEntity<SurveyDTO> response = client.exchange("/surveys/create", HttpMethod.POST, request, SurveyDTO.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
