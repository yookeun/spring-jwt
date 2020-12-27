# user테이블 생성
CREATE table `users`
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


insert into users(user_id, password, user_name, role)
values('hong', '{bcrypt}$2a$10$5ueMHBZpCGZ9oesru.MQluiHxOLuMzAcmqHqrfier3ILUCxhiXNBm', '홍길동', 'ADMIN_ROLE');