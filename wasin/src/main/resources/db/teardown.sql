
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS user_tb;
DROP TABLE IF EXISTS company_tb;
DROP TABLE IF EXISTS company_image_tb;

create table user_tb (
     is_mode_auto bit,
     company_id bigint,
     user_id bigint not null auto_increment,
     email varchar(255),
     password varchar(255),
     lock_password varchar(255),
     username varchar(255),
     role enum ('ADMIN','SUPER_ADMIN','USER'),
     status enum ('ACTIVE','INACTIVE','STAND_BY'),
     primary key (user_id),
     CONSTRAINT fk_company FOREIGN KEY (company_id) REFERENCES company_tb(company_id) ON DELETE CASCADE
);

create table company_tb (
     company_id bigint not null auto_increment,
     fss_id varchar(255),
     location varchar(255),
     name varchar(255),
     primary key (company_id)
);

create table company_image_tb (
      company_image_id bigint not null auto_increment,
      company_id bigint,
      url varchar(1000),
      primary key (company_image_id)
);

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO company_tb (`company_id`, `fss_id`, `name`, `location`) VALUES
(1, '12345-55555-3663', '원정이의 집', '부산광역시 수영구 민락동'),
(2, '23456-55555-3663', '원석이의 집', '부산광역시 금정구 장전동');

-- 패스워드: password1@
INSERT INTO user_tb (`user_id`, `username`,`email`,`password`,`lock_password`, `role`,`status`,`is_mode_auto`, `company_id`) VALUES
(1, '1jeongg', 'leena0912@naver.com', '{bcrypt}$2a$10$ZyY8A2GMb5a9tq5RD1/LsOc7ExG1VX9KAWIsWOpojnlK92mQEOMZC', '{bcrypt}$2a$10$NlF1LONeXtejcNReYu3XPu37FJrNll691IdoUykpY89aUzgwlDlYK', 'SUPER_ADMIN', 'ACTIVE', true, 1),
(2, '2jeongg', 'leena0913@naver.com', '{bcrypt}$2a$10$ZyY8A2GMb5a9tq5RD1/LsOc7ExG1VX9KAWIsWOpojnlK92mQEOMZC', '{bcrypt}$2a$10$NlF1LONeXtejcNReYu3XPu37FJrNll691IdoUykpY89aUzgwlDlYK', 'ADMIN', 'STAND_BY', true, 1),
(3, '3jeongg', 'leena0914@naver.com', '{bcrypt}$2a$10$ZyY8A2GMb5a9tq5RD1/LsOc7ExG1VX9KAWIsWOpojnlK92mQEOMZC', '{bcrypt}$2a$10$NlF1LONeXtejcNReYu3XPu37FJrNll691IdoUykpY89aUzgwlDlYK', 'ADMIN', 'STAND_BY', true, 2),
(4, '4jeongg', 'leena0915@naver.com', '{bcrypt}$2a$10$ZyY8A2GMb5a9tq5RD1/LsOc7ExG1VX9KAWIsWOpojnlK92mQEOMZC', '{bcrypt}$2a$10$NlF1LONeXtejcNReYu3XPu37FJrNll691IdoUykpY89aUzgwlDlYK', 'SUPER_ADMIN', 'STAND_BY', true, 1);

INSERT INTO company_image_tb (company_image_id, company_id, url) VALUES
(1, 1, 'https://static.wixstatic.com/media/d465da_ed07388770a5418f8db3cf4e37573c47.jpg/v1/fill/w_342,h_336,al_c,q_80,usm_0.66_1.00_0.01,enc_auto/d465da_ed07388770a5418f8db3cf4e37573c47');
