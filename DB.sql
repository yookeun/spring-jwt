CREATE DATABASE `yktest` CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
use `yktest`;

# mysql 서버에서 수행
CREATE USER 'yktest'@'%' IDENTIFIED BY  'Yktest123!';
GRANT ALL PRIVILEGES ON yktest.* TO 'yktest'@'%';

# user테이블 생성
CREATE table `user`
(
    `user_key` bigint NOT NULL AUTO_INCREMENT COMMENT '고유키',
    `user_id` varchar(50) NOT NULL COMMENT '계정',
    `password` varchar(100) NOT NULL COMMENT '패스워',
    `user_name` varchar(50) NOT NULL COMMENT '이름',
    `role` varchar(20) NOT NULL COMMENT '권한',
    `reg_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일' ,
    PRIMARY KEY(`user_key`),
    UNIQUE KEY(`user_id`)
);