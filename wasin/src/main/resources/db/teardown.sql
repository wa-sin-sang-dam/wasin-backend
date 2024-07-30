
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS user_tb;
create table user_tb (
     is_mode_auto bit,
     user_id bigint not null auto_increment,
     email varchar(255),
     password varchar(255),
     username varchar(255),
     role enum ('ADMIN','SUPER_ADMIN','USER'),
     status enum ('ACTIVE','INACTIVE','STAND_BY'),
     primary key (user_id)
 );

SET FOREIGN_KEY_CHECKS = 1;

    -- 패스워드: password1@
INSERT INTO user_tb (`user_id`, `username`,`email`,`password`,`role`,`status`,`is_mode_auto`)
VALUES (1, '1jeongg', 'leena0912@naver.com', '{bcrypt}$2a$10$ZyY8A2GMb5a9tq5RD1/LsOc7ExG1VX9KAWIsWOpojnlK92mQEOMZC', 'SUPER_ADMIN', 'ACTIVE', true);
