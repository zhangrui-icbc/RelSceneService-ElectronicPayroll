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
  `name` varchar(20) DEFAULT NULL COMMENT '列名称',
  `col_index` varchar(3) DEFAULT NULL COMMENT '列号',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4 COMMENT='固定值模板表';

/*Data for the table `reimbursement_common_template` */

insert  into `reimbursement_common_template`(`id`,`name`,`col_index`,`remark`) values (16,'手机号码','0','11'),(54,'报销日期','1','111');

/*Table structure for table `reimbursement_custom_template` */

DROP TABLE IF EXISTS `reimbursement_custom_template`;

CREATE TABLE `reimbursement_custom_template` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `company_id` varchar(30) DEFAULT NULL COMMENT '公司id模板id',
  `name` varchar(20) DEFAULT NULL COMMENT '列名称',
  `col_index` varchar(3) DEFAULT NULL COMMENT '列号',
  `category` int(3) DEFAULT '0' COMMENT '类别.1收入合计,2支出合计,3实际收入,总计项:收入合计11,支出合计22,实际收入33',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1103 DEFAULT CHARSET=gbk COMMENT='自定义模板表';

/*Data for the table `reimbursement_custom_template` */

/*Table structure for table `reimbursement_import` */

DROP TABLE IF EXISTS `reimbursement_import`;

CREATE TABLE `reimbursement_import` (
  `reimbursement_item_id` int(9) DEFAULT NULL COMMENT '每一项的id',
  `reimbursement_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '一批一条',
  `template_id` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '模板ID公司id',
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
  `name` varchar(20) DEFAULT NULL COMMENT '列名称',
  `category` int(3) DEFAULT NULL COMMENT '类别.1收入合计,2支出合计,3实际收入,总计项:收入合计11,支出合计22,实际收入33',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8mb4 COMMENT='模板字段备选表';

/*Data for the table `reimbursement_template_alternative` */

insert  into `reimbursement_template_alternative`(`id`,`name`,`category`) values (93,'交通费',NULL),(94,'住宿费',NULL),(96,'话费',NULL),(98,'餐费',NULL),(99,'差旅费',NULL),(100,'洗衣费',NULL);

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1112 DEFAULT CHARSET=gbk COMMENT='自定义模板表';

/*Data for the table `salary_custom_template` */

/*Table structure for table `salary_import` */

DROP TABLE IF EXISTS `salary_import`;

CREATE TABLE `salary_import` (
  `salary_item_id` int(9) DEFAULT NULL COMMENT '每一项的id',
  `salary_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '一批一条',
  `template_id` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '模板ID公司id',
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
  `name` varchar(20) DEFAULT NULL COMMENT '列名称',
  `category` int(3) DEFAULT NULL COMMENT '类别.1收入合计,2支出合计,3实际收入,0无分组',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=183 DEFAULT CHARSET=utf8mb4 COMMENT='模板字段备选表';

/*Data for the table `salary_template_alternative` */

insert  into `salary_template_alternative`(`id`,`name`,`category`) values (95,'工资总额',1),(96,'基本工资',1),(97,'应付工资合计',1),(98,'应扣款合计',2),(99,'应补款合计',1),(100,'工资合计',1),(101,'社保',2),(104,'收入额',1),(106,'当月实发工资',1),(107,'单位支出',44),(163,'子女教育',6),(164,'继续教育',6),(165,'专项附加扣除',66),(167,'通讯补贴',1),(168,'午餐费补贴',1),(170,'节日补贴',1),(173,'养老保险',4),(174,'医疗保险',4),(175,'失业保险',4),(176,'住房公积金',4),(177,'其他扣款',4),(178,'个人所得税',2),(179,'赡养老人',4),(180,'绩效工资',1),(182,'备注',55);

/*Table structure for table `salary_user` */

DROP TABLE IF EXISTS `salary_user`;

CREATE TABLE `salary_user` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `mobile` varchar(11) NOT NULL COMMENT '用户名/手机号',
  `password` varchar(50) DEFAULT '0477d3da16cb08766b45682299b8a211' COMMENT '密码',
  `company_id` varchar(30) DEFAULT NULL COMMENT '公司id/公司编号',
  `name` varchar(20) DEFAULT NULL COMMENT '员工姓名',
  `openId` varchar(32) DEFAULT NULL COMMENT '用户唯一标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=196 DEFAULT CHARSET=utf8 COMMENT='公司员工表';

/*Data for the table `salary_user` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
