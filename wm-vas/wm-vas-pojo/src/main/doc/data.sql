truncate table charge_info;
truncate table charge_payment_info;
truncate table business_info;
truncate table supplier_info;
truncate table supplier_business;
truncate table supplier_area_business;
truncate table business_store_share;


 INSERT INTO `vas-n`.`business_info` (`business_code`, `business_name`, `parent_code`, `business_status`)
 VALUES ('MPCZ', '话费充值', '', '1');
 INSERT INTO `vas-n`.`business_info` (`business_code`, `business_name`, `parent_code`, `business_status`)
 VALUES ('LTCZ', '联通充值', 'MPCZ', '1');
 INSERT INTO `vas-n`.`business_info` (`business_code`, `business_name`, `parent_code`, `business_status`)
 VALUES ('YDCZ', '移动充值', 'MPCZ', '1');
 INSERT INTO `vas-n`.`business_info` (`business_code`, `business_name`, `parent_code`, `business_status`)
 VALUES ('DXCZ', '电信充值', 'MPCZ', '1');
 INSERT INTO `vas-n`.`business_info` (`business_code`, `business_name`, `parent_code`, `business_status`)
 VALUES ('DJDF', '缴电费', '', '1');
 INSERT INTO `vas-n`.`business_info` (`business_code`, `business_name`, `parent_code`, `business_status`)
 VALUES ('DJSF', '缴水费', '', '1');
 INSERT INTO `vas-n`.`business_info` (`business_code`, `business_name`, `parent_code`, `business_status`)
 VALUES ('JMQF', '缴煤气费', '', '1');
 INSERT INTO `vas-n`.`business_info` (`business_code`, `business_name`, `parent_code`, `business_status`)
 VALUES ('YXDSF', '有线电视费', '', '1');
 INSERT INTO `vas-n`.`business_info` (`business_code`, `business_name`, `parent_code`, `business_status`)
 VALUES ('GHKD', '固话宽带', '', '1');
 INSERT INTO `vas-n`.`business_info` (`business_code`, `business_name`, `parent_code`, `business_status`)
 VALUES ('YDKD', '移动宽带', 'GHKD', '1');
 INSERT INTO `vas-n`.`business_info` (`business_code`, `business_name`, `parent_code`, `business_status`)
 VALUES ('DXKD', '电信宽带', 'GHKD', '1');
 INSERT INTO `vas-n`.`business_info` (`business_code`, `business_name`, `parent_code`, `business_status`)
 VALUES ('LTKD', '联通宽带', 'GHKD', '1');
 INSERT INTO `vas-n`.`business_info` (`business_code`, `business_name`, `parent_code`, `business_status`)
 VALUES ('DTKD', '铁通宽带', 'GHKD', '1');
 INSERT INTO `vas-n`.`business_info` (`business_code`, `business_name`, `parent_code`, `business_status`)
 VALUES ('JTFK', '交通罚款', '', '1');

 INSERT INTO supplier_info (`supplier_code`, `supplier_name`, `company`, `email`, `mobile`, `telphone`, `Apply_explain`,`check_status`,`supplier_token`)
 VALUES ('111', '任欢', '深圳市深海梦想合伙人有限公司', '469656844@qq.com', '18676823505', '07363505', '企业合伙人', '1','222');
 INSERT INTO supplier_info (`supplier_code`, `supplier_name`, `company`, `email`, `mobile`, `telphone`, `Apply_explain`,`check_status`,`supplier_token`)
 VALUES ('112', '李华', '深圳市华响世纪有限公司', '2318784005@qq.com', '18676823555', '07363505', '企业合伙人2', '1','333');
 INSERT INTO supplier_info (`supplier_code`, `supplier_name`, `company`, `email`, `mobile`, `telphone`, `Apply_explain`,`check_status`,`supplier_token`)
 VALUES ('113', '覃永波', '深圳市永波科技有限公司', '2318784006@qq.com', '18676823506', '07363505', '企业合伙人3', '1','444');
 INSERT INTO supplier_info (`supplier_code`, `supplier_name`, `company`, `email`, `mobile`, `telphone`, `Apply_explain`,`check_status`,`supplier_token`)
 VALUES ('114', '邓建东', '深圳市建东科技有限公司', '2318784007@qq.com', '18676823507', '07363505', '企业合伙人4', '1','555');
 INSERT INTO supplier_info (`supplier_code`, `shop_supplier_id`, `shop_supplier_no`, `supplier_name`, `company`, `email`, `mobile`, `telphone`, `Apply_explain`,`check_status`,`supplier_token`)
 VALUES ('100000','164','OFPAY001', '张三', '欧飞话费充值有限公司AAAAAAAAAA', '2318784007@qq.com', '15800000000', '07363505', '企业合伙人4', '1','555');

 INSERT INTO supplier_business (supplier_code, business_code, create_time)
 VALUES ('111','LTCZ','2016-9-23 15:00:00');
 INSERT INTO supplier_business (supplier_code, business_code, create_time)
 VALUES ('111','DJDF','2016-9-23 15:00:00');
 INSERT INTO supplier_business (supplier_code, business_code, create_time)
 VALUES ('111','JMQF','2016-9-23 15:00:00');
 INSERT INTO supplier_business (supplier_code, business_code, create_time)
 VALUES ('112','LTCZ','2016-9-23 15:00:00');
 INSERT INTO supplier_business (supplier_code, business_code, create_time)
 VALUES ('112','DJDF','2016-9-23 15:00:00');
 INSERT INTO supplier_business (supplier_code, business_code, create_time)
 VALUES ('112','JMQF','2016-9-23 15:00:00');
 INSERT INTO supplier_business (supplier_code, business_code, create_time)
 VALUES ('113','LTCZ','2016-9-23 15:00:00');
 INSERT INTO supplier_business (supplier_code, business_code, create_time)
 VALUES ('113','DJDF','2016-9-23 15:00:00');
 INSERT INTO supplier_business (supplier_code, business_code, create_time)
 VALUES ('113','JMQF','2016-9-23 15:00:00');
 INSERT INTO supplier_business (supplier_code, business_code, create_time)
 VALUES ('114','LTCZ','2016-9-23 15:00:00');
 INSERT INTO supplier_business (supplier_code, business_code, create_time)
 VALUES ('114','DJDF','2016-9-23 15:00:00');
 INSERT INTO supplier_business (supplier_code, business_code, create_time)
 VALUES ('114','JMQF','2016-9-23 15:00:00');
 INSERT INTO supplier_business (supplier_code, business_code, create_time)
 VALUES ('100001','DJDF','2016-9-23 15:00:00');

 INSERT INTO supplier_area_business (`province`, `city`, `country`, `supplier_code`, `business_code`, `supplier_order`, `country_code`, `country_name`)
 VALUES ('湖南省', '长沙市', '宁乡县', '111', 'LTCZ', '1', '123', '宁乡运营中心');
 INSERT INTO supplier_area_business (`province`, `city`, `country`, `supplier_code`, `business_code`, `supplier_order`,  `country_code`, `country_name`)
 VALUES ('湖南省', '长沙市', '长沙县', '111', 'DJDF', '1', '124', '长沙县运营中心');
 INSERT INTO supplier_area_business (`province`, `city`, `country`, `supplier_code`, `business_code`, `supplier_order`,  `country_code`, `country_name`)
 VALUES ('湖北省', '潜江市', '潜江保护区', '100001', 'DJDF', '1', '124', '潜江运营中心');
 INSERT INTO supplier_area_business (`province`, `city`, `country`, `supplier_code`, `business_code`, `supplier_order`,  `country_code`, `country_name`)
 VALUES ('湖南省', '常德市', '安乡县', '111', 'JMQF', '1', '124', '安乡县运营中心');
 INSERT INTO supplier_area_business (`province`, `city`, `country`, `supplier_code`, `business_code`, `supplier_order`, `country_code`, `country_name`)
 VALUES ('湖南省', '长沙市', '宁乡县', '112', 'LTCZ', '2', '123', '宁乡运营中心');
 INSERT INTO supplier_area_business (`province`, `city`, `country`, `supplier_code`, `business_code`, `supplier_order`,  `country_code`, `country_name`)
 VALUES ('湖南省', '长沙市', '长沙县', '112', 'DJDF', '2', '124', '长沙县运营中心');
 INSERT INTO supplier_area_business (`province`, `city`, `country`, `supplier_code`, `business_code`, `supplier_order`,  `country_code`, `country_name`)
 VALUES ('湖南省', '常德市', '安乡县', '112', 'JMQF', '2', '124', '安乡县运营中心');
 INSERT INTO supplier_area_business (`province`, `city`, `country`, `supplier_code`, `business_code`, `supplier_order`, `country_code`, `country_name`)
 VALUES ('湖南省', '长沙市', '宁乡县', '113', 'LTCZ', '3', '123', '宁乡运营中心');
 INSERT INTO supplier_area_business (`province`, `city`, `country`, `supplier_code`, `business_code`, `supplier_order`,  `country_code`, `country_name`)
 VALUES ('湖南省', '长沙市', '长沙县', '113', 'DJDF', '3', '124', '长沙县运营中心');
 INSERT INTO supplier_area_business (`province`, `city`, `country`, `supplier_code`, `business_code`, `supplier_order`,  `country_code`, `country_name`)
 VALUES ('湖南省', '常德市', '安乡县', '113', 'JMQF', '3', '124', '安乡县运营中心');
 INSERT INTO supplier_area_business (`province`, `city`, `country`, `supplier_code`, `business_code`, `supplier_order`, `country_code`, `country_name`)
 VALUES ('湖南省', '长沙市', '宁乡县', '114', 'LTCZ', '4', '123', '宁乡运营中心');
 INSERT INTO supplier_area_business (`province`, `city`, `country`, `supplier_code`, `business_code`, `supplier_order`,  `country_code`, `country_name`)
 VALUES ('湖南省', '长沙市', '长沙县', '114', 'DJDF', '4', '124', '长沙县运营中心');
 INSERT INTO supplier_area_business (`province`, `city`, `country`, `supplier_code`, `business_code`, `supplier_order`,  `country_code`, `country_name`)
 VALUES ('湖南省', '常德市', '安乡县', '114', 'JMQF', '4', '124', '安乡县运营中心');

 INSERT INTO `vas-n`.`charge_info`
 (`charge_code`, `open_platform_no`,`supplier_code`, `supplier_name`,charge_org_code, `charge_org_name`, `business_code`, `business_name`, `recharge_user_code`, `recharge_user_name`, `province`, `city`, `country`, `country_code`, `country_name`, `recharge_user_addr`, `mobile`, `store_code`, `store_name`, `costing_amount`, `original_amount`, `real_amount`, `pay_status`,  pay_user_id,  pay_user_type, pay_user_name, create_time,  sub_business_code,  sub_business_name,  platform_divided_ratio,  area_divided_ratio,  area_commission_ratio,  store_commission_ratio,  lift_coefficient, product_id, bill_year_month)
 VALUES
 ('3000','10000', '111', '任欢', '0000112','湖南省常德市电力局', 'DJDF', '缴电费', '000000101', '小欢', '湖南省', '常德市', '安乡县', '124', '安乡县运营中心', '三仙咀社区', '18676823505', '48', '打石一路飞天网点', '90', '100', '150', '1', '51', '4', '任欢', '2016-10-19 10:36:00', 'DJDF', '缴电费', '60', '40', '70', '30', '10' , '10020','201602') ;
  INSERT INTO `vas-n`.`charge_info`
 (`charge_code`, `open_platform_no`,`supplier_code`, `supplier_name`,charge_org_code, `charge_org_name`, `business_code`, `business_name`, `recharge_user_code`, `recharge_user_name`, `province`, `city`, `country`, `country_code`, `country_name`, `recharge_user_addr`, `mobile`, `store_code`, `store_name`, `costing_amount`, `original_amount`, `real_amount`, `pay_status`,  pay_user_id,  pay_user_type, pay_user_name, create_time,  sub_business_code,  sub_business_name,  platform_divided_ratio,  area_divided_ratio,  area_commission_ratio,  store_commission_ratio,  lift_coefficient, product_id, bill_year_month)
 VALUES
 ('3001','10001', '111', '任欢', '0000112','湖南省常德市电力局', 'DJDF', '缴电费', '000000101', '小欢', '湖南省', '常德市', '安乡县', '124', '安乡县运营中心', '三仙咀社区', '18676823505', '48', '打石一路飞天网点', '90', '100', '150', '1', '51', '4', '任欢', '2016-10-19 10:36:00', 'DJDF', '缴电费', '60', '40', '70', '30', '10' , '10020','201602') ;
  INSERT INTO `vas-n`.`charge_info` 
 (`charge_code`, `open_platform_no`,`supplier_code`, `supplier_name`,charge_org_code, `charge_org_name`, `business_code`, `business_name`, `recharge_user_code`, `recharge_user_name`, `province`, `city`, `country`, `country_code`, `country_name`, `recharge_user_addr`, `mobile`, `store_code`, `store_name`, `costing_amount`, `original_amount`, `real_amount`, `pay_status`,  pay_user_id,  pay_user_type, pay_user_name, create_time,  sub_business_code,  sub_business_name,  platform_divided_ratio,  area_divided_ratio,  area_commission_ratio,  store_commission_ratio,  lift_coefficient, product_id, bill_year_month)
 VALUES
 ('3002','10002', '111', '任欢', '0000112','湖南省常德市电力局', 'DJDF', '缴电费', '000000101', '小欢', '湖南省', '常德市', '安乡县', '124', '安乡县运营中心', '三仙咀社区', '18676823505', '48', '打石一路飞天网点', '90', '100', '150', '1', '51', '4', '任欢', '2016-10-19 10:36:00', 'DJDF', '缴电费', '60', '40', '70', '30', '10' , '10020','201602') ;
  INSERT INTO `vas-n`.`charge_info`
 (`charge_code`, `open_platform_no`,`supplier_code`, `supplier_name`,charge_org_code, `charge_org_name`, `business_code`, `business_name`, `recharge_user_code`, `recharge_user_name`, `province`, `city`, `country`, `country_code`, `country_name`, `recharge_user_addr`, `mobile`, `store_code`, `store_name`, `costing_amount`, `original_amount`, `real_amount`, `pay_status`,  pay_user_id,  pay_user_type, pay_user_name, create_time,  sub_business_code,  sub_business_name,  platform_divided_ratio,  area_divided_ratio,  area_commission_ratio,  store_commission_ratio,  lift_coefficient, product_id, bill_year_month)
 VALUES
 ('3003','10003', '111', '任欢', '0000112','湖南省常德市电力局', 'DJDF', '缴电费', '000000101', '小欢', '湖南省', '常德市', '安乡县', '124', '安乡县运营中心', '三仙咀社区', '18676823505', '48', '打石一路飞天网点', '90', '100', '150', '1', '51', '4', '任欢', '2016-10-19 10:36:00', 'DJDF', '缴电费', '60', '40', '70', '30', '10' , '10020','201602') ;


 INSERT INTO charge_payment_info ( charge_code, create_time, pay_way, real_amount, serial_number )
 VALUES ('3000', '2016-10-09 16:17:31', '1', '0.00', '11111');