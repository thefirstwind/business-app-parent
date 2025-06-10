-- paygw database
USE paygw;

CREATE TABLE IF NOT EXISTS `payment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `payment_no` varchar(50) NOT NULL COMMENT '支付单号',
  `order_id` bigint(20) NOT NULL COMMENT '订单ID',
  `order_no` varchar(50) NOT NULL COMMENT '订单编号',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `amount` decimal(10,2) NOT NULL COMMENT '支付金额',
  `payment_method` varchar(20) NOT NULL COMMENT '支付方式：ALIPAY-支付宝，WECHAT-微信，BANK-银行卡',
  `transaction_id` varchar(100) DEFAULT NULL COMMENT '第三方交易号',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '支付状态：0-待支付，1-支付中，2-支付成功，3-支付失败',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `pay_time` timestamp NULL DEFAULT NULL COMMENT '支付时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_payment_no` (`payment_no`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_order_no` (`order_no`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付信息表';

-- Initialize data
INSERT INTO `payment` (`id`, `payment_no`, `order_id`, `order_no`, `user_id`, `amount`, `payment_method`, `transaction_id`, `status`, `pay_time`) VALUES
(1, 'PAY20230001', 1, 'ORD20230001', 1, 8999.00, 'ALIPAY', '2023060122001476751412705258', 2, '2023-06-01 14:30:00'),
(2, 'PAY20230002', 2, 'ORD20230002', 2, 18999.00, 'WECHAT', 'WX2023060200001587', 2, '2023-06-02 10:15:00'),
(3, 'PAY20230003', 3, 'ORD20230003', 3, 9998.00, 'BANK', NULL, 0, NULL); 