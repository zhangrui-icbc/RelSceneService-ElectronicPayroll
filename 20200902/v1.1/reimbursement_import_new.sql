DROP TABLE IF EXISTS `reimbursement_import_new`;
CREATE TABLE `reimbursement_import_new` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `batch_no` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '批次号',
  `user_id` bigint(30) DEFAULT NULL COMMENT '员工编号(客户提供)/手机号码',
  `issue_time` date DEFAULT NULL COMMENT '工资发放时间',
  `total_reim` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '报销合计',
  `special_info` text COLLATE utf8mb4_bin COMMENT '报销详细信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `company_id` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '公司id',
  PRIMARY KEY (`id`),
  KEY `companyid` (`company_id`),
  KEY `userid` (`user_id`),
  KEY `batchNo` (`batch_no`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='自定义数据表';