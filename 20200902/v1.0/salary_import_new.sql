DROP TABLE IF EXISTS `salary_import_new`;

CREATE TABLE `salary_import_new` (
  `id` INT(30) NOT NULL AUTO_INCREMENT,
  `batch_no` VARCHAR(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '批次号',
  `user_id` BIGINT(30) DEFAULT NULL COMMENT '员工编号(客户提供)/手机号码',
  `issue_time` DATE DEFAULT NULL COMMENT '工资发放时间',
  `real_income` VARCHAR(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '实际收入',
  `total_revenue` VARCHAR(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '收入合计',
  `total_expenditure` VARCHAR(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '支出合计',
  `salary_remark` TEXT COLLATE utf8mb4_bin COMMENT '工资备注',
  `special_deduction` VARCHAR(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '专项扣除',
  `unit_expenditure` VARCHAR(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '单位支出',
  `special_info` TEXT COLLATE utf8mb4_bin COMMENT '个性化工资信息',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `company_id` VARCHAR(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '公司id',
 PRIMARY KEY (`id`),
  KEY `companyid` (`company_id`),
  KEY `userid` (`user_id`),
  KEY `batchNo` (`batch_no`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='自定义数据表';
