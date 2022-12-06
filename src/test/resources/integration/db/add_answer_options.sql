INSERT INTO ANSWER_OPTIONS
(ID,TEXT,QUESTION_ID)
VALUES
(nextval('seq_id'),'ANSWER_OPTIONS1', (Select ID from QUESTIONS where text = 'Question1')),
(nextval('seq_id'),'ANSWER_OPTIONS2', (Select ID from QUESTIONS where text = 'Question1')),
(nextval('seq_id'),'ANSWER_OPTIONS3', (Select ID from QUESTIONS where text = 'Question1')),
(nextval('seq_id'),'ANSWER_OPTION1', (Select ID from QUESTIONS where text = 'Question2')),
(nextval('seq_id'),'ANSWER_OPTIONS2', (Select ID from QUESTIONS where text = 'Question2')),
(nextval('seq_id'),'ANSWER_OPTIONS1', (Select ID from QUESTIONS where text = 'Question3'));