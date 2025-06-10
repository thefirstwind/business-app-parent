-- tradecenter database
USE tradecenter;

CREATE TABLE IF NOT EXISTS `order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(50) NOT NULL COMMENT '订单编号',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `item_id` bigint(20) NOT NULL COMMENT '商品ID',
  `quantity` int(11) NOT NULL COMMENT '商品数量',
  `amount` decimal(10,2) NOT NULL COMMENT '订单金额',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '订单状态：0-待付款，1-已付款，2-已发货，3-已完成，4-已取消',
  `address` varchar(255) DEFAULT NULL COMMENT '收货地址',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_order_no` (`order_no`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_item_id` (`item_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- Initialize data
INSERT INTO `order` (`id`, `order_no`, `user_id`, `item_id`, `quantity`, `amount`, `status`, `address`) VALUES
(1, 'ORD20230001', 1, 1, 1, 8999.00, 1, '北京市朝阳区xxx街道xxx号'),
(2, 'ORD20230002', 2, 2, 1, 18999.00, 2, '上海市浦东新区xxx街道xxx号'),
(3, 'ORD20230003', 3, 3, 2, 9998.00, 0, '广州市天河区xxx街道xxx号'); 