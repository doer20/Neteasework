-- account-database
DROP DATABASE IF EXISTS doer_neteasework_account ;
CREATE  DATABASE doer_neteasework_account CHARACTER SET  utf8  COLLATE utf8_general_ci;
grant all privileges on doer_neteasework_account.* to 'neteasework'@'%' identified by '123456';
flush privileges;

use doer_neteasework_account;
DROP TABLE IF EXISTS users;
CREATE TABLE users (
	user_name varchar(256) PRIMARY KEY NOT NULL,
    user_pass varchar(256),
    role varchar(256)
)ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
-- ----------------------------
-- Table structure for oauth_access_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_access_token`;
CREATE TABLE `oauth_access_token`  (
  `token_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `token` blob NULL,
  `authentication_id` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `client_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authentication` blob NULL,
  `refresh_token` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`authentication_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`  (
  `client_id` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `resource_ids` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `client_secret` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `scope` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authorized_grant_types` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authorities` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `access_token_validity` int(11) NULL DEFAULT NULL,
  `refresh_token_validity` int(11) NULL DEFAULT NULL,
  `additional_information` varchar(4096) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `autoapprove` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oauth_refresh_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_refresh_token`;
CREATE TABLE `oauth_refresh_token`  (
  `token_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `token` blob NULL,
  `authentication` blob NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

insert into oauth_client_details (client_id,client_secret,scope,authorized_grant_types) value ('neteasework','{noop}doerpass','webclient','password,refresh_token,client_credentials');
insert into users values ('buyer','37254660e226ea65ce6f1efd54233424','buyer');
insert into users values ('seller','981c57a5cfb0f868e064904b8745766f','seller');
insert into users values ('admin','ee10c315eba2c75b403ea99136f5b48d','buyer,seller,admin');

-- product database
DROP DATABASE IF EXISTS doer_neteasework_product ;
CREATE  DATABASE doer_neteasework_product CHARACTER SET  utf8  COLLATE utf8_general_ci;
grant all privileges on doer_neteasework_product.* to 'neteasework'@'%' identified by '123456';
flush privileges;
use doer_neteasework_product;
DROP TABLE IF EXISTS product;
CREATE TABLE product (
	product_id VARCHAR(255) PRIMARY KEY NOT NULL,
    title VARCHAR(128) NOT NULL,
    content VARCHAR(1024) NOT NULL,
    summary VARCHAR(256) NOT NULL,
    image_src VARCHAR(2083) NOT NULL,
    price INT NOT NULL,
	closing_num INT DEFAULT 0
);

-- cart-database
DROP DATABASE IF EXISTS doer_neteasework_cart;
CREATE  DATABASE doer_neteasework_cart CHARACTER SET  utf8  COLLATE utf8_general_ci;
grant all privileges on doer_neteasework_cart.* to 'neteasework'@'%' identified by '123456';
flush privileges;
use doer_neteasework_cart;
DROP TABLE IF EXISTS cart;
CREATE TABLE cart (
	product_id VARCHAR(255) PRIMARY KEY NOT NULL,
	count INT NOT NULL,
    price_in_cart INT NOT NULL
);

-- order-database
DROP DATABASE IF EXISTS doer_neteasework_order;
CREATE  DATABASE doer_neteasework_order CHARACTER SET  utf8  COLLATE utf8_general_ci;
grant all privileges on doer_neteasework_order.* to 'neteasework'@'%' identified by '123456';
flush privileges;
use doer_neteasework_order;
DROP TABLE IF EXISTS orders;
CREATE TABLE orders (
	order_id VARCHAR(64) PRIMARY KEY, #auto_increment: snow flake
	product_id VARCHAR(255) NOT NULL,
    date TIMESTAMP,
	count INT NOT NULL,
    price_done INT NOT NULL
);