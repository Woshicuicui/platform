use `course`;

drop table if exists course_project;
CREATE TABLE `course_project`
(
    `id`           int(11) NOT NULL AUTO_INCREMENT,
    `name`         varchar(255) DEFAULT NULL,
    `short_name`   varchar(50)  DEFAULT NULL,
    `project_desc` varchar(255) DEFAULT NULL,
    `create_user`  varchar(50)  DEFAULT NULL,
    `update_user`  varchar(50)  DEFAULT NULL,
    `status`       int(11) DEFAULT '0',
    `create_time`  datetime     DEFAULT NULL,
    `update_time`  datetime     DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

drop table if exists course_project_env;
CREATE TABLE `course_project_env`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `project_id`  int(11) NOT NULL,
    `name`        varchar(255) DEFAULT NULL,
    `env_desc`    varchar(255) DEFAULT NULL,
    `create_user` varchar(50)  DEFAULT NULL,
    `update_user` varchar(50)  DEFAULT NULL,
    `status`      int(11) DEFAULT '0',
    `create_time` datetime     DEFAULT NULL,
    `update_time` datetime     DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

drop table if exists course_project_host;
CREATE TABLE `course_project_host`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `env_id`      int(11) NOT NULL,
    `ip`          varchar(255) DEFAULT NULL,
    `host`        varchar(255) DEFAULT NULL,
    `create_user` varchar(50)  DEFAULT NULL,
    `update_user` varchar(50)  DEFAULT NULL,
    `status`      int(11) DEFAULT '0',
    `create_time` datetime     DEFAULT NULL,
    `update_time` datetime     DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;