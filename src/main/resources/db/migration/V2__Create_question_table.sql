create table question
(
    id            INT auto_increment ,
    title         VARCHAR(50),
    description   TEXT,
    creator       INT,
    comment_count INT DEFAULT 0,
    view_count    INT DEFAULT 0,
    like_count    INT DEFAULT 0,
    tag           VARCHAR(256),
    gmt_create    BIGINT,
    gmt_modified  BIGINT,
    constraint question_pk
        primary key (ID)
);