CREATE TABLE IF NOT EXISTS USERS
(
    ID          NUMERIC(20) PRIMARY KEY,
    LOGIN      VARCHAR(255) NOT NULL,
    CONSTRAINT uc_users_login Unique(LOGIN)
);
create TABLE IF NOT EXISTS SURVEYS
(
    ID              NUMERIC(20)     PRIMARY KEY,
    NAME           VARCHAR(255)  NOT NULL,
    DESCRIPTION     TEXT          NOT NULL,
    CONSTRAINT uc_surveys_name Unique(NAME)
);
create TABLE IF NOT EXISTS QUESTIONS
(
    ID          NUMERIC(20)     PRIMARY KEY,
    TEXT        TEXT            NOT NULL,
    SURVEY_ID   NUMERIC(20)     NOT NULL,
    ENABLED     BOOLEAN         DEFAULT TRUE NOT NULL
);
create TABLE IF NOT EXISTS ANSWER_OPTIONS
(
    ID                NUMERIC(20)   PRIMARY KEY,
    TEXT              VARCHAR(255)   NOT NULL,
    QUESTION_ID       NUMERIC(20)   NOT NULL
);
create TABLE IF NOT EXISTS ANSWERS
(
    ID                      NUMERIC(20)       PRIMARY KEY,
    ANSWER_OPTION_ID        NUMERIC(20)       NOT NULL ,
    USER_ID                 NUMERIC(20)       NOT NULL
);