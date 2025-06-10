-- itemcenter database
USE itemcenter;

CREATE TABLE IF NOT EXISTS `item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `item_name` varchar(100) NOT NULL COMMENT '商品名称',
  `description` text COMMENT '商品描述',
  `price` decimal(10,2) NOT NULL COMMENT '商品价格',
  `stock` int(11) NOT NULL DEFAULT '0' COMMENT '库存数量',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '商品状态：0-下架，1-上架',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

-- Initialize data
INSERT INTO `item` (`id`, `item_name`, `description`, `price`, `stock`, `status`) VALUES
(1, 'iPhone 14 Pro', 'Apple iPhone 14 Pro 256GB', 8999.00, 100, 1),
(2, 'MacBook Pro', 'Apple MacBook Pro 16-inch', 18999.00, 50, 1),
(3, 'iPad Air', 'Apple iPad Air 256GB', 4999.00, 200, 1),
(4, 'AirPods Pro', 'Apple AirPods Pro 2', 1999.00, 300, 1); 