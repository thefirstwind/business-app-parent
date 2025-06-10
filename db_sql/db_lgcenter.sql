-- lgcenter database
USE lgcenter;

CREATE TABLE IF NOT EXISTS `logistics` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `logistics_no` varchar(50) NOT NULL COMMENT '物流单号',
  `order_id` bigint(20) NOT NULL COMMENT '订单ID',
  `order_no` varchar(50) NOT NULL COMMENT '订单编号',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '物流状态：0-待发货，1-已发货，2-运输中，3-已签收，4-异常',
  `shipping_company` varchar(50) DEFAULT NULL COMMENT '快递公司',
  `shipping_address` varchar(255) DEFAULT NULL COMMENT '收货地址',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_logistics_no` (`logistics_no`),
  UNIQUE KEY `idx_order_id` (`order_id`),
  KEY `idx_order_no` (`order_no`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物流信息表';

CREATE TABLE IF NOT EXISTS `logistics_trace` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `logistics_id` bigint(20) NOT NULL COMMENT '物流ID',
  `logistics_no` varchar(50) NOT NULL COMMENT '物流单号',
  `action` varchar(100) NOT NULL COMMENT '物流动作',
  `location` varchar(100) DEFAULT NULL COMMENT '地点',
  `operator` varchar(50) DEFAULT NULL COMMENT '操作员',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_logistics_id` (`logistics_id`),
  KEY `idx_logistics_no` (`logistics_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物流轨迹表';

-- Initialize data
INSERT INTO `logistics` (`id`, `logistics_no`, `order_id`, `order_no`, `status`, `shipping_company`, `shipping_address`) VALUES
(1, 'LG20230001', 1, 'ORD20230001', 1, '顺丰速运', '北京市朝阳区xxx街道xxx号'),
(2, 'LG20230002', 2, 'ORD20230002', 2, '京东物流', '上海市浦东新区xxx街道xxx号');

INSERT INTO `logistics_trace` (`logistics_id`, `logistics_no`, `action`, `location`, `operator`) VALUES
(1, 'LG20230001', '订单已发货', '北京市海淀区仓库', '张三'),
(1, 'LG20230001', '运输中', '北京市朝阳区转运中心', '李四'),
(2, 'LG20230002', '订单已发货', '上海市青浦区仓库', '王五'),
(2, 'LG20230002', '运输中', '上海市浦东新区转运中心', '赵六'),
(2, 'LG20230002', '派送中', '上海市浦东新区xxx街道', '钱七'); 