CREATE INDEX idx_questions_survey_id
ON QUESTIONS(SURVEY_ID);

CREATE INDEX idx_answer_options_question_id
ON ANSWER_OPTIONS(QUESTION_ID);

CREATE INDEX idx_answers_answer_option_id
ON ANSWERS(ANSWER_OPTION_ID);
