CREATE TABLE `goods`(
`id` BIGINT(20) NOT NULL auto_increment COMMENT '商品ID',
`goods_name` VARCHAR(16) DEFAULT NULL COMMENT '商品名称',
`goods_title` VARCHAR(64) DEFAULT NULL COMMENT '商品标题',
`goods_img` VARCHAR(64) DEFAULT NULL COMMENT '商品的图片',
`goods_detail` LONGTEXT COMMENT '商品的详情介绍',
`goods_price` DECIMAL(10, 2) DEFAULT '0.00' COMMENT '商品单价',
`goods_stock` INT(11) DEFAULT '0' COMMENT '商品库存，-1表示没有限制',
PRIMARY KEY(`id`)
) ENGINE=INNODB auto_increment=3 DEFAULT charset=utf8;



INSERT INTO goods VALUES(
1, 'iphone X', 'Apple iphone 64GB 银色', '/img/iohonex.png', 'Apple iphone 64GB 银色', 5000, 200
)

INSERT INTO goods VALUES(
2, '华为mete 9', '华为mate 9 64GB + 8GB 版 白色', '/img/meta10.png', '华为mate 9 64GB + 8GB 版 白色', 4000, 400
)


CREATE TABLE `miaosha_goods`(
`id` BIGINT(20) NOT NULL auto_increment COMMENT '秒杀的商品表',
`goods_id` BIGINT(20) DEFAULT NULL COMMENT '商品ID',
`miaosha_price` DECIMAL(10, 2) DEFAULT '0.00' COMMENT '秒杀价',
`stock_count` INT(11) DEFAULT NULL COMMENT '库存数量',
`start_date` datetime DEFAULT NULL COMMENT '秒杀开始时间',
`end_date` datetime DEFAULT NULL COMMENT '秒杀结束时间',
PRIMARY KEY(`id`)
) ENGINE=INNODB auto_increment=3 DEFAULT charset=utf8;

CREATE TABLE `order_info`(

`id` BIGINT(20) NOT NULL auto_increment ,
`user_id` BIGINT(20) DEFAULT NULL COMMENT '用户ID',
`goods_id` BIGINT(20) DEFAULT NULL COMMENT '商品ID',
`delivery_addr_id` BIGINT(20) DEFAULT NULL COMMENT '收货地址ID' ,
`goods_name`   VARCHAR(16) DEFAULT NULL COMMENT '冗余过来的商品名称' ,
`goods_count` int(11) DEFAULT '0' COMMENT '商品数量',
`goods_price` DECIMAL(10, 2) DEFAULT '0.00' COMMENT '商品单价',
`order_channel` TINYINT(4) DEFAULT '0' COMMENT '1pc ,2 android, 3 ios',
`status` TINYINT(4) DEFAULT '0' COMMENT '订单状态， 0新建未支付，1已支付，2已发货，3已收货，4已退款，5已完成',
`create_date` datetime DEFAULT NULL COMMENT '订单的创建时间',
`pay_date` datetime DEFAULT NULL COMMENT '交付时间',

PRIMARY KEY(`id`)
) ENGINE=INNODB auto_increment=12 DEFAULT charset=utf8;


CREATE TABLE `miaosha_order`(

`id` BIGINT(20) NOT NULL auto_increment ,
`user_id` BIGINT(20) DEFAULT NULL COMMENT '用户ID',
`order_id` BIGINT(20) DEFAULT NULL COMMENT '订单ID',
`goods_id` BIGINT(20) DEFAULT NULL COMMENT '商品ID',

PRIMARY KEY(`id`)
) ENGINE=INNODB auto_increment=3 DEFAULT charset=utf8;
