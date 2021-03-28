create table notification
(
    id            INT auto_increment,
    notifier_name VARCHAR(100) null,
    notifier      INT not null,
    receiver      INT not null,
    outer_id      INT not null,
    outer_title   VARCHAR(256) null,
    type          INT not null,
    gmt_create    BIGINT not null,
    status        INT default 0 not null,
    constraint  notification_pk
        primary key (ID)
);