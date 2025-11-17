--liquibase formatted sql
--changeset admin:create-init-table-changelog splitStatements:true endDelimiter:;

CREATE TABLE `users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL,
  `email` VARCHAR(255) NULL,
  `image_url` VARCHAR(1000) NULL,
  `email_verified` TINYINT NULL,
  `password` VARCHAR(1000) NULL,
  `provider` VARCHAR(45) NULL,
  `provider_id` VARCHAR(45) NULL,
  `role_id` INT NULL,
  `status` INT NULL,
  `create_time` DATETIME NULL DEFAULT NULL,
  `update_time` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;

CREATE TABLE `content` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `creator_id` INT NOT NULL,
  `content_title` VARCHAR(100) NULL,
  `content` VARCHAR(2000) NULL,
  `thumbnail` VARCHAR(500) NULL,
   is_public TINYINT(1) DEFAULT 1,
  `status` INT NULL,
  `create_time` DATETIME NULL DEFAULT NULL,
  `update_time` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;

CREATE TABLE `content_comment` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `content_id` INT NOT NULL,
  `creator_id` INT NOT NULL,
  `comment` VARCHAR(1000) NULL,
  `is_hidden` TINYINT(1) DEFAULT 0,
  `is_create_by_creator` TINYINT(1) DEFAULT 0,
  `status` INT NULL,
  `create_time` DATETIME NULL DEFAULT NULL,
  `update_time` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;