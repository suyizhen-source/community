create table comment
(
    id            INT auto_increment,
    parent_id     INT NOT NULL,
    type          INT NOT NULL,
    content       VARCHAR(1024),
    commentator   INT NOT NULL,
    like_count    INT DEFAULT 0,
    comment_count INT DEFAULT 0,
    gmt_create    BIGINT,
    gmt_modified  BIGINT,
    constraint comment_pk
        primary key (ID)
);