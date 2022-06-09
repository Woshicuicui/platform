use `course`;

drop table if exists course_api_definition;
CREATE TABLE `course_api_definition`
(
    `id`               int(11)     NOT NULL AUTO_INCREMENT,
    `project_id`       int(11)     NOT NULL,
    `definition_name`  varchar(255) DEFAULT NULL,
    `request_data`     text         DEFAULT NULL,
    `protocol`         varchar(50) not null,
    `body`             text         default null,
    `case_count`       int(11)      default '0',
    `connect_timeout`  bigint       default null,
    `response_timeout` bigint       default null,
    `create_user`      varchar(50)  DEFAULT NULL,
    `update_user`      varchar(50)  DEFAULT NULL,
    `status`           int(11)      DEFAULT '0',
    `create_time`      datetime     DEFAULT NULL,
    `update_time`      datetime     DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;



drop table if exists course_api_test_case;
CREATE TABLE `course_api_test_case`
(
    `id`            int(11)      NOT NULL AUTO_INCREMENT,
    `definition_id` int(11)      not null,
    `case_name`     varchar(255) not null,
    `priority`      varchar(50) default 'P0',
    `body`          text        default null,
    `otherParams`   text        default null,
    `asserts`       text        default null,
    `extraction`    text        default null,
    `create_user`   varchar(50) DEFAULT NULL,
    `update_user`   varchar(50) DEFAULT NULL,
    `status`        int(11)     DEFAULT '0',
    `create_time`   datetime    DEFAULT NULL,
    `update_time`   datetime    DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;