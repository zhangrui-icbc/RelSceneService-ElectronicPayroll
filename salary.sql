/*
SQLyog v10.2 
MySQL - 5.6.28 : Database - salary_demo
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

/*Table structure for table `reimbursement` */

DROP TABLE IF EXISTS `reimbursement`;

CREATE TABLE `reimbursement` (
  `id` varchar(32) NOT NULL,
  `import_time` datetime DEFAULT NULL COMMENT '导入时间',
  `issue_time` date DEFAULT NULL COMMENT '发放工资时间',
  `excel_name` varchar(100) DEFAULT NULL COMMENT '导入excel表的名称',
  `descp` text COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk COMMENT='批次表';

/*Data for the table `reimbursement` */

/*Table structure for table `reimbursement_common_template` */

DROP TABLE IF EXISTS `reimbursement_common_template`;

CREATE TABLE `reimbursement_common_template` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` varchar(20) DEFAULT NULL COMMENT '字段类型:实际收入:real_income;收入合计:total_income;支出合计:total_expenditure;',
  `name` varchar(20) DEFAULT NULL COMMENT '列名称',
  `col_index` varchar(3) DEFAULT NULL COMMENT '列号',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4 COMMENT='固定值模板表';

/*Data for the table `reimbursement_common_template` */

insert  into `reimbursement_common_template`(`id`,`type`,`name`,`col_index`,`remark`) values (16,NULL,'手机号码','0','11'),(54,NULL,'报销日期','1','111');

/*Table structure for table `reimbursement_custom_template` */

DROP TABLE IF EXISTS `reimbursement_custom_template`;

CREATE TABLE `reimbursement_custom_template` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `company_id` varchar(30) DEFAULT NULL COMMENT '公司id模板id',
  `type` varchar(20) DEFAULT NULL COMMENT '字段类型:实际收入:real_income;收入合计:total_income;支出合计:total_expenditure;税:tax;岗位工资:post_salary;绩效工资:merit_pay;加班费:overtime_pay;全勤奖:attendance_bonus;交通费:car_fare;过节费:festival_bonus;伙食补贴:food_allowance;住房公积金:housing_provident_fund;养老保险:endowment_insurance;医保金:medical_insurance_fund;失业金:unemployment_compensation;房租:rent;水电费:electricity_water',
  `name` varchar(20) DEFAULT NULL COMMENT '列名称',
  `col_index` varchar(3) DEFAULT NULL COMMENT '列号',
  `category` int(3) DEFAULT '0' COMMENT '类别.1收入合计,2支出合计,3实际收入,总计项:收入合计11,支出合计22,实际收入33',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1007 DEFAULT CHARSET=gbk COMMENT='自定义模板表';

/*Data for the table `reimbursement_custom_template` */

/*Table structure for table `reimbursement_import` */

DROP TABLE IF EXISTS `reimbursement_import`;

CREATE TABLE `reimbursement_import` (
  `reimbursement_item_id` int(9) DEFAULT NULL COMMENT '每一项的id',
  `reimbursement_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '一批一条',
  `template_id` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '模板ID公司id',
  `template_col_type` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '模板字段类型',
  `template_col_name` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '模板名称',
  `import_amount` text COLLATE utf8mb4_bin COMMENT '导入金额',
  `user_id` bigint(30) DEFAULT NULL COMMENT '员工编号(客户提供)/手机号码',
  `col_index` int(3) DEFAULT NULL COMMENT '列号',
  `category` int(2) DEFAULT NULL COMMENT '类别.1收入合计,2支出合计,3实际收入,总计项:收入合计11,支出合计22,实际收入33',
  KEY `salary_id` (`reimbursement_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='自定义数据表';

/*Data for the table `reimbursement_import` */

/*Table structure for table `reimbursement_template_alternative` */

DROP TABLE IF EXISTS `reimbursement_template_alternative`;

CREATE TABLE `reimbursement_template_alternative` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `type` varchar(20) DEFAULT NULL COMMENT '字段类型:实际收入:real_income;收入合计:total_income;支出合计:total_expenditure;税:tax;岗位工资:post_salary;绩效工资:merit_pay;加班费:overtime_pay;全勤奖:attendance_bonus;交通费:car_fare;过节费:festival_bonus;伙食补贴:food_allowance;住房公积金:housing_provident_fund;养老保险:endowment_insurance;医保金:medical_insurance_fund;失业金:unemployment_compensation;房租:rent;水电费:electricity_water',
  `name` varchar(20) DEFAULT NULL COMMENT '列名称',
  `category` int(3) DEFAULT NULL COMMENT '类别.1收入合计,2支出合计,3实际收入,总计项:收入合计11,支出合计22,实际收入33',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=99 DEFAULT CHARSET=utf8mb4 COMMENT='模板字段备选表';

/*Data for the table `reimbursement_template_alternative` */

insert  into `reimbursement_template_alternative`(`id`,`type`,`name`,`category`) values (23,'','报销合计',11),(93,NULL,'交通费',NULL),(94,NULL,'住宿费',NULL),(96,NULL,'话费',NULL),(98,NULL,'餐费',NULL);

/*Table structure for table `salary` */

DROP TABLE IF EXISTS `salary`;

CREATE TABLE `salary` (
  `id` varchar(32) NOT NULL,
  `import_time` datetime DEFAULT NULL COMMENT '导入时间',
  `issue_time` date DEFAULT NULL COMMENT '发放工资时间',
  `excel_name` varchar(100) DEFAULT NULL COMMENT '导入Excel名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk COMMENT='批次表';

/*Data for the table `salary` */

/*Table structure for table `salary_common_template` */

DROP TABLE IF EXISTS `salary_common_template`;

CREATE TABLE `salary_common_template` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` varchar(20) DEFAULT NULL COMMENT '字段类型:实际收入:real_income;收入合计:total_income;支出合计:total_expenditure;',
  `name` varchar(20) DEFAULT NULL COMMENT '列名称',
  `col_index` varchar(3) DEFAULT NULL COMMENT '列号',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8mb4 COMMENT='固定值模板表';

/*Data for the table `salary_common_template` */

insert  into `salary_common_template`(`id`,`type`,`name`,`col_index`,`remark`) values (55,NULL,'发放日期','1',''),(56,NULL,'员工手机号','0',NULL);

/*Table structure for table `salary_custom_template` */

DROP TABLE IF EXISTS `salary_custom_template`;

CREATE TABLE `salary_custom_template` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `company_id` varchar(30) DEFAULT NULL COMMENT '公司id模板id',
  `type` varchar(20) DEFAULT NULL COMMENT '字段类型:实际收入:real_income;收入合计:total_income;支出合计:total_expenditure;税:tax;岗位工资:post_salary;绩效工资:merit_pay;加班费:overtime_pay;全勤奖:attendance_bonus;交通费:car_fare;过节费:festival_bonus;伙食补贴:food_allowance;住房公积金:housing_provident_fund;养老保险:endowment_insurance;医保金:medical_insurance_fund;失业金:unemployment_compensation;房租:rent;水电费:electricity_water',
  `name` varchar(20) DEFAULT NULL COMMENT '列名称',
  `col_index` varchar(3) DEFAULT NULL COMMENT '列号',
  `category` int(3) DEFAULT '0' COMMENT '类别.1收入合计,2支出合计,3实际收入,0无分组',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=742 DEFAULT CHARSET=gbk COMMENT='自定义模板表';

/*Data for the table `salary_custom_template` */

/*Table structure for table `salary_import` */

DROP TABLE IF EXISTS `salary_import`;

CREATE TABLE `salary_import` (
  `salary_item_id` int(9) DEFAULT NULL COMMENT '每一项的id',
  `salary_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '一批一条',
  `template_id` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '模板ID公司id',
  `template_col_type` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '模板字段类型',
  `template_col_name` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '模板名称',
  `import_amount` text COLLATE utf8mb4_bin COMMENT '导入金额',
  `user_id` bigint(30) DEFAULT NULL COMMENT '员工编号(客户提供)/手机号码',
  `col_index` int(3) DEFAULT NULL COMMENT '列号',
  `category` int(2) DEFAULT NULL COMMENT '类别.1收入合计,2支出合计,3实际收入,0无分组',
  KEY `salary_id` (`salary_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='自定义数据表';

/*Data for the table `salary_import` */

/*Table structure for table `salary_template_alternative` */

DROP TABLE IF EXISTS `salary_template_alternative`;

CREATE TABLE `salary_template_alternative` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `type` varchar(20) DEFAULT NULL COMMENT '字段类型:实际收入:real_income;收入合计:total_income;支出合计:total_expenditure;税:tax;岗位工资:post_salary;绩效工资:merit_pay;加班费:overtime_pay;全勤奖:attendance_bonus;交通费:car_fare;过节费:festival_bonus;伙食补贴:food_allowance;住房公积金:housing_provident_fund;养老保险:endowment_insurance;医保金:medical_insurance_fund;失业金:unemployment_compensation;房租:rent;水电费:electricity_water',
  `name` varchar(20) DEFAULT NULL COMMENT '列名称',
  `category` int(3) DEFAULT NULL COMMENT '类别.1收入合计,2支出合计,3实际收入,0无分组',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=128 DEFAULT CHARSET=utf8mb4 COMMENT='模板字段备选表';

/*Data for the table `salary_template_alternative` */

insert  into `salary_template_alternative`(`id`,`type`,`name`,`category`) values (23,'real_income','实际收入',11),(29,'total_income','收入合计',22),(30,'total_expenditure','支出合计',33),(95,NULL,'工资总额',1),(96,NULL,'基本工资',1),(97,NULL,'应付工资合计',1),(98,NULL,'应扣款合计',2),(99,NULL,'应补款合计',1),(100,NULL,'工资合计',1),(101,NULL,'社保',2),(102,NULL,' 应扣公积金 ',2),(104,NULL,'收入额',1),(105,NULL,'个人所得税',2),(106,NULL,'当月实发工资',1),(107,NULL,'单位支出',44),(109,NULL,'单位住房',4),(110,NULL,'单位缴费',4),(112,NULL,'年金',4),(114,NULL,'养老（单位）',4),(127,NULL,'备注:',55);

/*Table structure for table `salary_user` */

DROP TABLE IF EXISTS `salary_user`;

CREATE TABLE `salary_user` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `mobile` varchar(11) NOT NULL COMMENT '用户名/手机号',
  `password` varchar(11) DEFAULT '300386' COMMENT '密码',
  `company_id` varchar(30) DEFAULT NULL COMMENT '公司id/公司编号',
  `name` varchar(20) DEFAULT NULL COMMENT '员工姓名',
  `openId` varchar(32) DEFAULT NULL COMMENT '用户唯一标识',
  PRIMARY KEY (`id`,`mobile`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8 COMMENT='公司员工表';

/*Data for the table `salary_user` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
