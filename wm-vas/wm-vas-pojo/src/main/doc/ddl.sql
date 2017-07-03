#######增值服务数据库脚本
#######负责人：黄振盛
#######开  发：任欢、邓建东、覃永波
#######测  试：杨珊珊

DROP DATABASE IF EXISTS `wmvas`;

CREATE DATABASE `wmvas` CHARACTER SET utf8 COLLATE utf8_general_ci;

USE `wmvas`;

DROP TABLE IF EXISTS business_info;
DROP TABLE IF EXISTS business_store_share;
DROP TABLE IF EXISTS charge_info;
DROP TABLE IF EXISTS charge_info_err_log;
DROP TABLE IF EXISTS charge_pay_http_log;
DROP TABLE IF EXISTS charge_payment_info;
DROP TABLE IF EXISTS charge_refund;
DROP TABLE IF EXISTS supplier_area_business;
DROP TABLE IF EXISTS supplier_business;
DROP TABLE IF EXISTS supplier_info;


CREATE TABLE `business_info` (
   `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '服务业务信息id',
   `business_code` varchar(100) NOT NULL COMMENT '服务业务编号',
   `business_name` varchar(100) NOT NULL DEFAULT '' COMMENT '服务业务名称',
   `parent_code` varchar(100) NOT NULL DEFAULT '' COMMENT '父级业务编号',
   `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
   `business_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '服务状态；1：可服务，2：停止服务',
   `divide_rule` tinyint(4) NOT NULL DEFAULT '0' COMMENT '分成规则：1.全额，2差额',
   PRIMARY KEY (`id`),
   UNIQUE KEY `business_code` (`business_code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


CREATE TABLE `business_store_share` (
   `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '服务业务网点信息表',
   `business_code` varchar(100) NOT NULL DEFAULT '' COMMENT '服务类型编码',
   `country_code` varchar(100) NOT NULL DEFAULT '' COMMENT '县域运营中心编号id',
   `country_name` varchar(100) NOT NULL DEFAULT '' COMMENT '县域运营中心名称',
   `country_share_ratio` float(5,2) NOT NULL DEFAULT '0.00' COMMENT '总利润分成百分比率',
   `store_share_ratio` float(5,2) NOT NULL DEFAULT '0.00' COMMENT '总利润分成百分比率',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


CREATE TABLE `charge_info` (
   `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '缴费单信息id',
   `charge_code` varchar(100) NOT NULL DEFAULT '' COMMENT '缴费订单编号',
   `open_platform_no` varchar(100) NOT NULL DEFAULT '' COMMENT '开放平台的订单编号',
   `supplier_code` varchar(100) NOT NULL DEFAULT '' COMMENT '供应商编号',
   `supplier_name` varchar(100) NOT NULL DEFAULT '' COMMENT '供应商名称',
   `charge_org_code` varchar(100) NOT NULL DEFAULT '' COMMENT '收费机构编号',
   `charge_org_name` varchar(100) NOT NULL DEFAULT '' COMMENT '收费机构名称',
   `business_code` varchar(100) NOT NULL DEFAULT '' COMMENT '父级服务业务编号',
   `business_name` varchar(100) NOT NULL DEFAULT '' COMMENT '父级服务业务名称',
   `sub_business_code` varchar(100) NOT NULL DEFAULT '' COMMENT '子分类编码',
   `sub_business_name` varchar(100) NOT NULL DEFAULT '' COMMENT '子分类名称',
   `recharge_user_code` varchar(100) NOT NULL DEFAULT '' COMMENT '充值缴费账号(话费存电话号码，水电煤气费存户号)',
   `recharge_user_name` varchar(100) NOT NULL DEFAULT '' COMMENT '缴费账户用户名称',
   `recharge_user_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '缴费账户类型 1.户号 2.条形码',
   `recharge_user_addr` varchar(100) NOT NULL DEFAULT '' COMMENT '缴费账户用户地址',
   `city` varchar(100) NOT NULL DEFAULT '' COMMENT '市',
   `province` varchar(100) NOT NULL DEFAULT '' COMMENT '省',
   `country` varchar(100) NOT NULL DEFAULT '' COMMENT '县',
   `country_code` varchar(100) NOT NULL DEFAULT '' COMMENT '县运营中心编号id',
   `country_name` varchar(100) NOT NULL DEFAULT '' COMMENT '县运营中心名称',
   `mobile` varchar(15) NOT NULL DEFAULT '' COMMENT '会员用户电话',
   `pay_user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '会员用户姓名',
   `pay_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '会员用户id',
   `biz_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '付费用户biz_id：id',
   `biz_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '付费用户账户角色类型biz_type：角色类型 2:平台管理 3:县域 4:网点，5：供应商 9：会员',
   `store_code` varchar(100) NOT NULL DEFAULT '' COMMENT '网点编号id',
   `store_name` varchar(100) NOT NULL DEFAULT '' COMMENT '网点名称',
   `costing_amount` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '成本价',
   `original_amount` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '应缴金额（元）',
   `real_amount` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '实缴金额（元）',
   `refund_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '退款状态：0.初始状态，未发生退款，1.退款中 。2.已退款，3，退款失败',
   `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
   `pay_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '支付状态：1：待支付。2：支付中。3：缴费中。4：交易成功，5：交易失败，6：交易关闭，7：支付异常，8，缴费异常，9，撤销,10扣款成功',
   `store_info` varchar(100) NOT NULL DEFAULT '' COMMENT '网点其他信息',
   `lift_coefficient` float(5,2) NOT NULL DEFAULT '0.00' COMMENT '提点系数',
   `platform_divided_ratio` float(5,2) NOT NULL DEFAULT '0.00' COMMENT '平台和县域，平台分成比',
   `area_divided_ratio` float(5,2) NOT NULL DEFAULT '0.00' COMMENT '平台和县域，县域分成比',
   `area_commission_ratio` float(5,2) NOT NULL DEFAULT '0.00' COMMENT '县域和网点，县域佣金比',
   `store_commission_ratio` float(5,2) NOT NULL DEFAULT '0.00' COMMENT '县域和网点，网点佣金比',
   `bill_year_month` char(15) NOT NULL DEFAULT '' COMMENT '缴费账单日期YYYYMM。',
   `product_id` varchar(100) NOT NULL DEFAULT '' COMMENT '供应商产品id',
   `member_mobile` char(15) NOT NULL DEFAULT '' COMMENT '会员手机号码',
   `member_name` varchar(100) NOT NULL DEFAULT '' COMMENT '会员姓名',
   `ext_content` varchar(300) NOT NULL DEFAULT '' COMMENT '充值扩展字段',
   PRIMARY KEY (`id`),
   UNIQUE KEY `charge_code` (`charge_code`),
   UNIQUE KEY `open_platform_no` (`open_platform_no`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;


CREATE TABLE `charge_info_err_log` (
   `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '缴费单信息id',
   `charge_code` varchar(100) NOT NULL DEFAULT '' COMMENT '缴费订单编号',
   `open_platform_no` varchar(100) NOT NULL DEFAULT '' COMMENT '开放平台的订单编号',
   `refund_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '退款状态：0.初始状态，未发生退款，1.退款中 。2.已退款，3，退款失败',
   `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
   `pay_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '支付状态：1：待支付。2：支付中。3：缴费中。4：交易成功，5：交易失败，6：交易关闭，7：支付异常，8，缴费异常，9，撤销,10扣款成功，11:结算中，12:已结算；13.结算失败',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `charge_pay_http_log` (
   `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `log_code` varchar(100) DEFAULT NULL COMMENT '日志编号,记录一次请求标记',
   `charge_code` varchar(100) DEFAULT NULL COMMENT '业务编号',
   `send_time` timestamp NULL DEFAULT NULL COMMENT '发送请求时间',
   `receive_time` timestamp NULL DEFAULT NULL COMMENT '收到消息时间',
   `send_mothed` varchar(200) DEFAULT NULL COMMENT '发送的本地方法',
   `receive_mothed` varchar(200) DEFAULT NULL COMMENT '请求对像的方法，如资金服务中心',
   `remark` varchar(1000) DEFAULT NULL COMMENT '业务类型描述，如下单、退款调用',
   `send_data` text COMMENT '发送内容',
   `receive_data` text COMMENT '收到消息内容',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8;


CREATE TABLE `charge_payment_info` (
   `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '缴费单支付信息表',
   `charge_code` varchar(100) NOT NULL DEFAULT '' COMMENT '缴费订单号',
   `real_amount` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '实付金额',
   `pay_way` tinyint(4) NOT NULL DEFAULT '0' COMMENT '支付方式：1：会员支付，2：网点支付',
   `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
   PRIMARY KEY (`id`),
   UNIQUE KEY `charge_code` (`charge_code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


CREATE TABLE `charge_refund` (
   `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '缴费单退款id',
   `charge_code` varchar(100) NOT NULL DEFAULT '' COMMENT '缴费订单编号',
   `refund_code` varchar(100) NOT NULL DEFAULT '' COMMENT '退款编号',
   `refund_amount` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '退款金额',
   `refund_time` timestamp NULL DEFAULT NULL COMMENT '退款时间',
   `user_name` varchar(100) NOT NULL DEFAULT '' COMMENT '退款操作用户姓名',
   `user_mobile` varchar(15) NOT NULL DEFAULT '' COMMENT '退款操作用户手机号码',
   `user_code` varchar(100) NOT NULL DEFAULT '' COMMENT '退款操作用户编号',
   `remark` varchar(500) NOT NULL DEFAULT '' COMMENT '备注信息',
   PRIMARY KEY (`id`),
   UNIQUE KEY `refund_code` (`refund_code`),
   UNIQUE KEY `charge_code` (`charge_code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


CREATE TABLE `supplier_area_business` (
   `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '供应商可服务区域id',
   `province` varchar(20) NOT NULL DEFAULT '' COMMENT '省',
   `city` varchar(20) NOT NULL DEFAULT '' COMMENT '市',
   `country` varchar(20) NOT NULL DEFAULT '' COMMENT '县',
   `supplier_code` varchar(100) NOT NULL DEFAULT '' COMMENT '供应商编号',
   `business_code` varchar(100) NOT NULL DEFAULT '' COMMENT '服务业务编码',
   `supplier_order` int(10) NOT NULL DEFAULT '0' COMMENT '供应商排序比重',
   `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
   `country_code` varchar(100) NOT NULL DEFAULT '' COMMENT '县域运营中心编号id',
   `country_name` varchar(100) NOT NULL DEFAULT '' COMMENT '县域运营中心名称',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


CREATE TABLE `supplier_business` (
   `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '供应商服务业务',
   `supplier_code` varchar(100) NOT NULL DEFAULT '' COMMENT '供应商编号',
   `business_code` varchar(50) NOT NULL DEFAULT '' COMMENT '服务业务编码',
   `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
   `share_way` tinyint(4) NOT NULL DEFAULT '0' COMMENT '利润分成方式 1.不可分成 2.全额分成 ，3.差额分成',
   `total_share_ratio` float(5,2) NOT NULL DEFAULT '0.00' COMMENT '总利润分成百分比率',
   `platform_share_ratio` float(5,2) NOT NULL DEFAULT '0.00' COMMENT '平台分成百分比率',
   `area_share_ratio` float(5,2) NOT NULL DEFAULT '0.00' COMMENT '县域分成百分比率',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


CREATE TABLE `supplier_info` (
   `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '供应商信息表',
   `supplier_code` varchar(100) NOT NULL COMMENT '供应商编码',
   `shop_supplier_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '商家系统中的供应商id',
   `shop_supplier_no` varchar(100) NOT NULL DEFAULT '' COMMENT '商家系统中的供应商编号',
   `supplier_name` varchar(100) NOT NULL DEFAULT '' COMMENT '供应商名称',
   `company` varchar(100) NOT NULL DEFAULT '' COMMENT '公司名称',
   `email` varchar(50) NOT NULL DEFAULT '' COMMENT '供应商邮箱',
   `mobile` char(15) NOT NULL DEFAULT '' COMMENT '供应商手机号码',
   `telphone` char(15) NOT NULL DEFAULT '' COMMENT '供应商座机联系',
   `Apply_explain` varchar(500) NOT NULL DEFAULT '' COMMENT '供应商申请对接说明',
   `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
   `check_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '审核状态;1.待审核。2.已审核。3.已终止服务 。4.已经删除',
   `check_time` datetime DEFAULT NULL COMMENT '供应商审核时间',
   PRIMARY KEY (`id`),
   UNIQUE KEY `supplier_code` (`supplier_code`),
   UNIQUE KEY `shop_supplier_id` (`shop_supplier_id`),
   UNIQUE KEY `shop_supplier_no` (`shop_supplier_no`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

#########初始化数据
INSERT INTO `wmvas`.`business_info`(`business_code`,`business_name`,`parent_code`,`create_time`,`business_status`,`divide_rule`)
VALUES
   ('DJDF','缴电费','',CURRENT_TIMESTAMP(),1,1);



