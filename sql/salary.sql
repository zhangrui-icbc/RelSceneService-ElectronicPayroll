/*
SQLyog v10.2 
MySQL - 5.5.49 : Database - test
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`test` /*!40100 DEFAULT CHARACTER SET gbk */;

/*Table structure for table `reimbursement` */

DROP TABLE IF EXISTS `reimbursement`;

CREATE TABLE `reimbursement` (
  `id` varchar(32) NOT NULL,
  `import_time` datetime DEFAULT NULL COMMENT '导入时间',
  `issue_time` date DEFAULT NULL COMMENT '发放工资时间',
  `excel_name` varchar(100) DEFAULT NULL COMMENT '导入excel表的名称',
  `company_id` varchar(30) DEFAULT NULL COMMENT '公司id',
  `remark` varchar(300) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `issuetime` (`issue_time`),
  KEY `companyId` (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk COMMENT='批次表';

/*Data for the table `reimbursement` */

/*Table structure for table `reimbursement_common_template` */

DROP TABLE IF EXISTS `reimbursement_common_template`;

CREATE TABLE `reimbursement_common_template` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(20) DEFAULT NULL COMMENT '列名称',
  `col_index` varchar(3) DEFAULT NULL COMMENT '列号',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8mb4 COMMENT='固定值模板表';

/*Data for the table `reimbursement_common_template` */

insert  into `reimbursement_common_template`(`id`,`name`,`col_index`,`remark`) values (16,'手机号码','0',NULL),(54,'报销日期','1',NULL),(55,NULL,NULL,NULL);

/*Table structure for table `reimbursement_custom_template` */

DROP TABLE IF EXISTS `reimbursement_custom_template`;

CREATE TABLE `reimbursement_custom_template` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `company_id` varchar(30) DEFAULT NULL COMMENT '公司id模板id',
  `name` varchar(20) DEFAULT NULL COMMENT '列名称',
  `col_index` varchar(3) DEFAULT NULL COMMENT '列号',
  `category` int(3) DEFAULT '0' COMMENT '类别.1收入合计,2支出合计,3实际收入,总计项:收入合计11,支出合计22,实际收入33',
  PRIMARY KEY (`id`),
  KEY `companyId` (`company_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1149 DEFAULT CHARSET=gbk COMMENT='自定义模板表';

/*Data for the table `reimbursement_custom_template` */

/*Table structure for table `reimbursement_import` */

DROP TABLE IF EXISTS `reimbursement_import`;

CREATE TABLE `reimbursement_import` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `batch_no` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '批次号',
  `user_id` bigint(30) DEFAULT NULL COMMENT '员工编号(客户提供)/手机号码',
  `issue_time` date DEFAULT NULL COMMENT '工资发放时间',
  `total_reim` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '报销合计',
  `special_info` text COLLATE utf8mb4_bin COMMENT '个性化信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `company_id` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '公司id',
  PRIMARY KEY (`id`),
  KEY `userId` (`user_id`),
  KEY `companyId` (`company_id`),
  KEY `batchNo` (`batch_no`)
) ENGINE=InnoDB AUTO_INCREMENT=16204 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='自定义数据表';

/*Data for the table `reimbursement_import` */

/*Table structure for table `reimbursement_template_alternative` */

DROP TABLE IF EXISTS `reimbursement_template_alternative`;

CREATE TABLE `reimbursement_template_alternative` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(20) DEFAULT NULL COMMENT '列名称',
  `category` int(3) DEFAULT NULL COMMENT '类别.1收入合计,2支出合计,3实际收入,总计项:收入合计11,支出合计22,实际收入33',
  `company_id` varchar(30) DEFAULT NULL COMMENT '公司id',
  PRIMARY KEY (`id`),
  KEY `companyId` (`company_id`)
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=utf8mb4 COMMENT='模板字段备选表';

/*Data for the table `reimbursement_template_alternative` */

/*Table structure for table `salary` */

DROP TABLE IF EXISTS `salary`;

CREATE TABLE `salary` (
  `id` varchar(32) NOT NULL,
  `import_time` datetime DEFAULT NULL COMMENT '导入时间',
  `issue_time` date DEFAULT NULL COMMENT '发放工资时间',
  `excel_name` varchar(100) DEFAULT NULL COMMENT '导入Excel名称',
  `company_id` varchar(30) DEFAULT NULL COMMENT '公司id',
  `remark` varchar(300) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `companyId` (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk COMMENT='批次表';

/*Data for the table `salary` */

/*Table structure for table `salary_common_template` */

DROP TABLE IF EXISTS `salary_common_template`;

CREATE TABLE `salary_common_template` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(20) DEFAULT NULL COMMENT '列名称',
  `col_index` varchar(3) DEFAULT NULL COMMENT '列号',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb4 COMMENT='固定值模板表';

/*Data for the table `salary_common_template` */

insert  into `salary_common_template`(`id`,`name`,`col_index`,`remark`) values (55,'发放日期','1',''),(56,'员工手机号','0',NULL);

/*Table structure for table `salary_custom_template` */

DROP TABLE IF EXISTS `salary_custom_template`;

CREATE TABLE `salary_custom_template` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `company_id` varchar(30) DEFAULT NULL COMMENT '公司id模板id',
  `name` varchar(20) DEFAULT NULL COMMENT '列名称',
  `col_index` varchar(3) DEFAULT NULL COMMENT '列号',
  `category` int(3) DEFAULT '0' COMMENT '类别.1收入合计,2支出合计,3实际收入,0无分组',
  PRIMARY KEY (`id`),
  KEY `companyId` (`company_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1656 DEFAULT CHARSET=gbk COMMENT='自定义模板表';

/*Data for the table `salary_custom_template` */

/*Table structure for table `salary_import` */

DROP TABLE IF EXISTS `salary_import`;

CREATE TABLE `salary_import` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `batch_no` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '批次号',
  `user_id` bigint(30) DEFAULT NULL COMMENT '员工编号(客户提供)/手机号码',
  `issue_time` date DEFAULT NULL COMMENT '工资发放时间',
  `real_income` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '实际收入',
  `total_revenue` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '收入合计',
  `total_expenditure` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '支出合计',
  `salary_remark` text COLLATE utf8mb4_bin COMMENT '工资备注',
  `special_deduction` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '专项扣除',
  `unit_expenditure` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '单位支出',
  `special_info` text COLLATE utf8mb4_bin COMMENT '个性化工资信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `company_id` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '公司id',
  PRIMARY KEY (`id`),
  KEY `companyid` (`company_id`),
  KEY `userid` (`user_id`),
  KEY `batchNo` (`batch_no`)
) ENGINE=InnoDB AUTO_INCREMENT=6617364 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='自定义数据表';

/*Data for the table `salary_import` */

/*Table structure for table `salary_template_alternative` */

DROP TABLE IF EXISTS `salary_template_alternative`;

CREATE TABLE `salary_template_alternative` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(20) DEFAULT NULL COMMENT '列名称',
  `category` int(3) DEFAULT NULL COMMENT '类别.1收入合计,2支出合计,3实际收入,0无分组',
  `company_id` varchar(30) DEFAULT NULL COMMENT '公司id',
  PRIMARY KEY (`id`),
  KEY `companyId` (`company_id`)
) ENGINE=InnoDB AUTO_INCREMENT=290 DEFAULT CHARSET=utf8mb4 COMMENT='模板字段备选表';

/*Data for the table `salary_template_alternative` */

/*Table structure for table `salary_user` */

DROP TABLE IF EXISTS `salary_user`;

CREATE TABLE `salary_user` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `mobile` varchar(11) NOT NULL COMMENT '用户名/手机号',
  `password` varchar(50) DEFAULT '0477d3da16cb08766b45682299b8a211' COMMENT '密码',
  `company_id` varchar(30) DEFAULT NULL COMMENT '公司id/公司编号',
  `name` varchar(20) DEFAULT NULL COMMENT '员工姓名',
  `openId` varchar(32) DEFAULT NULL COMMENT '用户唯一标识',
  `dept` varchar(50) DEFAULT NULL COMMENT '部门',
  PRIMARY KEY (`id`),
  KEY `companyId` (`company_id`),
  KEY `mobile` (`mobile`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='公司员工表';

/*Data for the table `salary_user` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
