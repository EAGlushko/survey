INSERT INTO QUESTIONS
(ID,TEXT,SURVEY_ID,ENABLED)
VALUES
(nextval('seq_id'),'Question1',(Select ID from SURVEYS where name = 'SurveyName1'),true),
(nextval('seq_id'),'Question2',(Select ID from SURVEYS where name = 'SurveyName1'),true),
(nextval('seq_id'),'Question3',(Select ID from SURVEYS where name = 'SurveyName1'),true);