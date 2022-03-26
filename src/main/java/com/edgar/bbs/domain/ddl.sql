      
create table article
(
    id          bigint auto_increment
        primary key,
    content     text                               null,
    create_time datetime default CURRENT_TIMESTAMP null,
    title       varchar(200)                       null,
    type        varchar(40)                        null,
    star        bigint   default 0                 null,
    comments    bigint   default 0                 null,
    `read`      bigint   default 0                 null,
    username    varchar(40)                        null
);

create table avatar
(
    id       bigint auto_increment
        primary key,
    path     varchar(200) default 'default.jpg' not null,
    username varchar(40)                        null
);

create table carousel
(
    id       bigint auto_increment
        primary key,
    title    varchar(30)                          null,
    time     datetime   default CURRENT_TIMESTAMP null,
    active   tinyint(1) default 1                 null,
    path     varchar(200)                         not null,
    filename varchar(40)                          null,
    url      varchar(100)                         null
);

create table comment
(
    id       bigint auto_increment
        primary key,
    comment  text        null,
    reply_id bigint      null,
    username varchar(30) null
);

create table course
(
    id          bigint auto_increment
        primary key,
    school      varchar(60) null,
    course_name varchar(40) null,
    teacher     varchar(30) null,
    type        varchar(30) null
);

create table course_comments
(
    id        bigint auto_increment
        primary key,
    comment   text        null,
    course_id bigint      null,
    username  varchar(40) null
);

create table feedback
(
    id       bigint auto_increment
        primary key,
    content  text                 not null,
    email    varchar(30)          null,
    title    varchar(200)         null,
    username varchar(30)          not null,
    active   tinyint(1) default 1 null
);

create table files
(
    id             bigint auto_increment
        primary key,
    description    text                               null,
    file_name      varchar(100)                       not null,
    path           varchar(200)                       not null,
    type           varchar(30)                        not null,
    upload_time    datetime default CURRENT_TIMESTAMP null,
    download_times bigint   default 0                 null,
    username       varchar(40)                        null
);

create table jwc_notice
(
    id       bigint auto_increment
        primary key,
    title    varchar(100) null,
    link     varchar(100) null,
    pub_date varchar(30)  null
);

create table login_log
(
    id          bigint auto_increment
        primary key,
    address     varchar(50)                        null,
    create_time datetime default CURRENT_TIMESTAMP null,
    ip          varchar(20)                        not null,
    username    varchar(40)                        null
);

create table message
(
    id       bigint auto_increment
        primary key,
    content  text                                 null,
    `read`   tinyint(1) default 0                 null,
    time     datetime   default CURRENT_TIMESTAMP null,
    title    varchar(100)                         null,
    username varchar(30)                          null,
    type     varchar(20)                          null,
    url      varchar(200)                         null
);

create table message_settings
(
    id       bigint auto_increment
        primary key,
    username varchar(20)          null,
    comment  tinyint(1) default 1 null,
    star     tinyint(1) default 1 null,
    `like`   tinyint(1) default 1 null
);

create table reply
(
    id         bigint auto_increment
        primary key,
    article_id bigint           null,
    comments   bigint default 0 null,
    reply      text             null,
    username   varchar(40)      null,
    dislike    bigint default 0 null,
    `like`     bigint default 0 null,
    star       bigint default 0 null
);

create table user
(
    id          bigint auto_increment
        primary key,
    password    varchar(20)                        not null,
    username    varchar(20)                        not null,
    academy     varchar(30)                        null,
    avatar      varchar(100)                       null,
    email       varchar(30)                        null,
    create_time datetime default CURRENT_TIMESTAMP null,
    grade       varchar(30)                        null,
    age         smallint                           null,
    gender      varchar(30)                        null,
    description text                               null,
    constraint UK_sb8bbouer5wak8vyiiy4pf2bx
        unique (username)
);


    
