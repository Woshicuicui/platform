
create table course_user
(
    id                  bigint auto_increment
        primary key,
    username            varchar(50)  default '' not null,
    password            varchar(64)  default '' not null,
    nick_name           varchar(50)  default '' not null,
    user_email          varchar(100) default '' not null,
    user_phone          varchar(100) default '' not null,
    user_activation_key varchar(60)  default '' not null,
    user_status         int          default 0  not null,
    create_time         datetime                null,
    update_time         datetime                null
);

INSERT INTO course.course_user (id, username, password, nick_name, user_email, user_phone, user_activation_key, user_status, create_time, update_time) VALUES (5, 'admin', 'f7373d1a8161bab0655a0f31b1286fc5', 'xiaozhang', '', '', '3J0xfY', 0, '2022-03-06 14:12:20', '2022-03-06 14:12:20');
INSERT INTO course.course_user (id, username, password, nick_name, user_email, user_phone, user_activation_key, user_status, create_time, update_time) VALUES (6, 'xiaozhang1', 'b835f50d917cebb8f14bf5ca21b245a0', 'xiaozhang', '', '', 'uimCeZ', 0, '2022-03-06 16:57:40', '2022-03-06 16:57:40');

# admin 123456789
# xiaozhang1 123456789

