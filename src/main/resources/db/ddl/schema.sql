create database if not exists GPT;

use GPT;

drop table if exists user;
CREATE TABLE User (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'id', -- Auto-increment primary key
                       email VARCHAR(512) NOT NULL COMMENT 'email', -- Email address of the user
                       phone VARCHAR(128) NOT NULL COMMENT 'phone number', -- Phone number of the user
                       user_password VARCHAR(512) NOT NULL COMMENT 'password', -- Password of the user
                       last_name VARCHAR(512) COMMENT 'last name', -- Last name of the user
                       first_name VARCHAR(512) COMMENT 'first name', -- First name of the user
                       middle_name VARCHAR(512) COMMENT 'middle name', -- Middle name of the user
                       nickname VARCHAR(512) COMMENT 'nickname', -- Nickname of the user
                       avatar_url VARCHAR(1024) COMMENT 'URL of the user\'s avatar', -- URL of the user's avatar
    is_gpt_plus TINYINT DEFAULT 0 NOT NULL COMMENT 'Flag indicating if the user is a GPT Plus member', -- Flag indicating if the user is a GPT Plus member
                       create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'create time', -- Timestamp of record creation
                       update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time', -- Timestamp of record update
                       is_delete TINYINT DEFAULT 0 NOT NULL COMMENT 'is delete?' -- Soft delete flag
) COMMENT 'Users table conforms to the Third Normal Form (3NF)';
