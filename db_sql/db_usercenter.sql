-- usercenter database
USE usercenter;

CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- Initialize data
INSERT INTO `user` (`id`, `username`, `password`, `phone`, `email`) VALUES
(1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', '13800138000', 'admin@example.com'),
(2, 'user1', 'e10adc3949ba59abbe56e057f20f883e', '13800138001', 'user1@example.com'),
(3, 'user2', 'e10adc3949ba59abbe56e057f20f883e', '13800138002', 'user2@example.com'); 