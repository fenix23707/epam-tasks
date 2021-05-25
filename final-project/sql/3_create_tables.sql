USE `housing_and_utilities_db`;

CREATE TABLE `user`
(
    `id`        BIGINT       NOT NULL AUTO_INCREMENT,
    `login`     VARCHAR(200) NOT NULL UNIQUE,
    `password`  CHAR(100)    NOT NULL,
    `name`      VARCHAR(200) NOT NULL,
    `role`      TINYINT      NOT NULL,
    `is_active` TINYINT DEFAULT TRUE,
    PRIMARY KEY (`id`)
) ENGINE = INNODB
  DEFAULT CHARACTER SET utf8;

CREATE TABLE `tenant`
(
    `id`               BIGINT       NOT NULL,
    `address`          VARCHAR(200) NOT NULL,
    `apartment_number` SMALLINT     NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`id`) REFERENCES `user` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE = INNODB
  DEFAULT CHARACTER SET utf8;

CREATE TABLE `dispatcher`
(
    `id` BIGINT NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`id`) REFERENCES `user` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE = INNODB
  DEFAULT CHARACTER SET utf8;

CREATE TABLE `worker`
(
    `id`             BIGINT   NOT NULL,
    `specialization` SMALLINT NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`id`) REFERENCES `user` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE = INNODB
  DEFAULT CHARACTER SET utf8;

CREATE TABLE `request`
(
    `id`          BIGINT       NOT NULL AUTO_INCREMENT,
    `description` VARCHAR(400) NOT NULL,
    `desired_day` DATE         NOT NULL,
    `status`      SMALLINT     NOT NULL DEFAULT 0,
    `tenant_id`   BIGINT       NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`tenant_id`) REFERENCES `tenant` (`id`) ON UPDATE RESTRICT ON DELETE RESTRICT
) ENGINE = INNODB
  DEFAULT CHARACTER SET utf8;

CREATE TABLE `work_plan`
(
    `id`            BIGINT   NOT NULL AUTO_INCREMENT,
    `end_day`       DATE     NOT NULL,
    `work_scope`    SMALLINT NOT NULL,
    `request_id`    BIGINT   NOT NULL UNIQUE ,
    `dispatcher_id` BIGINT   NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`request_id`) REFERENCES `request` (`id`) ON UPDATE CASCADE ON DELETE RESTRICT ,
    FOREIGN KEY (`dispatcher_id`) REFERENCES `dispatcher` (`id`) ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE = INNODB
  DEFAULT CHARACTER SET utf8;

CREATE TABLE `work_plan_vs_worker`
(
    `work_plan_id` BIGINT NOT NULL,
    `worker_id`    BIGINT NOT NULL,
    PRIMARY KEY (`work_plan_id`, `worker_id`),
    FOREIGN KEY (`work_plan_id`) REFERENCES `work_plan` (`id`) ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY (`worker_id`) REFERENCES `worker` (`id`) ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE = INNODB
  DEFAULT CHARACTER SET utf8;