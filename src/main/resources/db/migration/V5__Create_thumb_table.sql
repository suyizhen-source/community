create table thumb
(
    id INT auto_increment,
    thumb_id INT not null,
    thumb_id_parent INT not null,
    constraint thumb_pk
        primary key (id)
);