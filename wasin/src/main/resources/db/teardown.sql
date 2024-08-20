
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS user_tb;
DROP TABLE IF EXISTS company_tb;
DROP TABLE IF EXISTS company_image_tb;
DROP TABLE IF EXISTS router_tb;
DROP TABLE IF EXISTS profile_tb;

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
     CONSTRAINT fk_user_company FOREIGN KEY (company_id) REFERENCES company_tb(company_id) ON DELETE CASCADE
);

create table company_tb (
     company_id bigint not null auto_increment,
     profile_id bigint,
     fss_id varchar(255),
     location varchar(255),
     name varchar(255),
     is_auto bit,
     primary key (company_id),
     CONSTRAINT fk_company_profile FOREIGN KEY (profile_id) REFERENCES profile_tb(profile_id) ON DELETE CASCADE
);

create table company_image_tb (
      company_image_id bigint not null auto_increment,
      company_id bigint,
      url varchar(1000),
      width int,
      height int,
      primary key (company_image_id),
      CONSTRAINT fk_company_image_company FOREIGN KEY (company_id) REFERENCES company_tb(company_id) ON DELETE CASCADE
);

create table router_tb (
    router_id bigint not null auto_increment,
    company_id bigint,
    position_x float(53),
    position_y float(53),
    instance varchar(255),
    job varchar(255),
    mac_address varchar(255),
    router_name varchar(255),
    ssid varchar(255),
    primary key (router_id),
    CONSTRAINT fk_router_company FOREIGN KEY (company_id) REFERENCES company_tb(company_id) ON DELETE CASCADE
);

create table profile_tb (
    profile_id bigint not null auto_increment,
    profile_index bigint,
    title varchar(1000),
    description varchar(3000),
    tip varchar(3000),
    primary key (profile_id)
);

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO profile_tb (`profile_id`, `profile_index`, `title`, `description`, `tip`) VALUES
(1, 1, '테스트 제목1', '테스트 설명1', '테스트 팁1'),
(2, 2, '테스트 제목2', '테스트 설명2', '테스트 팁2'),
(3, 3, '테스트 제목3', '테스트 설명3', '테스트 팁3'),
(4, 4, '테스트 제목4', '테스트 설명4', '테스트 팁4');

INSERT INTO company_tb (`company_id`, `profile_id`, `fss_id`, `name`, `location`, `is_auto`) VALUES
(1, 1, '12345-55555-3663', '원정이의 집', '부산광역시 수영구 민락동', true),
(2, 2, '23456-55555-3663', '원석이의 집', '부산광역시 금정구 장전동', false);

-- 패스워드: password1@
INSERT INTO user_tb (`user_id`, `username`,`email`,`password`,`lock_password`, `role`,`status`,`is_mode_auto`, `company_id`) VALUES
(1, '1jeongg', 'leena0912@naver.com', '{bcrypt}$2a$10$ZyY8A2GMb5a9tq5RD1/LsOc7ExG1VX9KAWIsWOpojnlK92mQEOMZC', '{bcrypt}$2a$10$NlF1LONeXtejcNReYu3XPu37FJrNll691IdoUykpY89aUzgwlDlYK', 'SUPER_ADMIN', 'ACTIVE', true, 1),
(2, '2jeongg', 'leena0913@naver.com', '{bcrypt}$2a$10$ZyY8A2GMb5a9tq5RD1/LsOc7ExG1VX9KAWIsWOpojnlK92mQEOMZC', '{bcrypt}$2a$10$NlF1LONeXtejcNReYu3XPu37FJrNll691IdoUykpY89aUzgwlDlYK', 'ADMIN', 'STAND_BY', true, 1),
(3, '3jeongg', 'leena0914@naver.com', '{bcrypt}$2a$10$ZyY8A2GMb5a9tq5RD1/LsOc7ExG1VX9KAWIsWOpojnlK92mQEOMZC', '{bcrypt}$2a$10$NlF1LONeXtejcNReYu3XPu37FJrNll691IdoUykpY89aUzgwlDlYK', 'ADMIN', 'STAND_BY', true, 2),
(4, '4jeongg', 'leena0915@naver.com', '{bcrypt}$2a$10$ZyY8A2GMb5a9tq5RD1/LsOc7ExG1VX9KAWIsWOpojnlK92mQEOMZC', '{bcrypt}$2a$10$NlF1LONeXtejcNReYu3XPu37FJrNll691IdoUykpY89aUzgwlDlYK', 'SUPER_ADMIN', 'STAND_BY', true, 1),
(5, '5jeongg', 'leena0916@naver.com', '{bcrypt}$2a$10$ZyY8A2GMb5a9tq5RD1/LsOc7ExG1VX9KAWIsWOpojnlK92mQEOMZC', '{bcrypt}$2a$10$NlF1LONeXtejcNReYu3XPu37FJrNll691IdoUykpY89aUzgwlDlYK', 'ADMIN', 'ACTIVE', true, 1),
(6, '6jeongg', 'leena0917@naver.com', '{bcrypt}$2a$10$ZyY8A2GMb5a9tq5RD1/LsOc7ExG1VX9KAWIsWOpojnlK92mQEOMZC', '{bcrypt}$2a$10$NlF1LONeXtejcNReYu3XPu37FJrNll691IdoUykpY89aUzgwlDlYK', 'USER', 'ACTIVE', true, 1),
(7, '7jeongg', 'leena0918@naver.com', '{bcrypt}$2a$10$ZyY8A2GMb5a9tq5RD1/LsOc7ExG1VX9KAWIsWOpojnlK92mQEOMZC', '{bcrypt}$2a$10$NlF1LONeXtejcNReYu3XPu37FJrNll691IdoUykpY89aUzgwlDlYK', 'SUPER_ADMIN', 'ACTIVE', true, 2),
(8, '8jeongg', 'leena0919@naver.com', '{bcrypt}$2a$10$ZyY8A2GMb5a9tq5RD1/LsOc7ExG1VX9KAWIsWOpojnlK92mQEOMZC', '{bcrypt}$2a$10$NlF1LONeXtejcNReYu3XPu37FJrNll691IdoUykpY89aUzgwlDlYK', 'SUPER_ADMIN', 'ACTIVE', true, null);

INSERT INTO company_image_tb (`company_image_id`, `company_id`, `url`, `height`, `width`) VALUES
(1, 1, 'https://static.wixstatic.com/media/d465da_ed07388770a5418f8db3cf4e37573c47.jpg/v1/fill/w_342,h_336,al_c,q_80,usm_0.66_1.00_0.01,enc_auto/d465da_ed07388770a5418f8db3cf4e37573c47', 1243, 56352);

INSERT INTO router_tb (`router_id`, `company_id`, `mac_address`, `router_name`, `ssid`, `instance`, `job`, `position_x`, `position_y`) VALUES
(1, 1, '5a:86:94:7f:b4:c7', '휴게실 Wifi', 'ipTIMEOpenWrt', '219.241.29.68:9100', 'routers', 124.32, 1653.22);
