--2018-2-7 添加通知书与检测作业关联表
CREATE TABLE `6c_notice_check` (
  `id` VARCHAR(50) NOT NULL,
  `notice_id` VARCHAR(50) DEFAULT NULL,
  `check_id` VARCHAR(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8

ALTER TABLE 1c_defect_package MODIFY COLUMN photo text COMMENT '照片';
ALTER TABLE 2c_defect_package MODIFY COLUMN photo text COMMENT '照片';
ALTER TABLE 3c_defect_package MODIFY COLUMN visible_light_photo text COMMENT '可见光照片';
ALTER TABLE 3c_defect_package MODIFY COLUMN infra_red_photo text COMMENT '红外线照片';
ALTER TABLE 3c_defect_package MODIFY COLUMN t_photo text COMMENT '温度照片';

ALTER TABLE 4c_defect_package MODIFY COLUMN photo text COMMENT '照片';
ALTER TABLE 5c_defect MODIFY COLUMN pantograph_photo text COMMENT '受电弓图';
ALTER TABLE 5c_defect MODIFY COLUMN skate_photo text COMMENT '滑板图';
ALTER TABLE 5c_defect MODIFY COLUMN locomotive_photo text COMMENT '车号图';

ALTER TABLE 5c_point_hisotry_data MODIFY COLUMN pantograph_photo text COMMENT '受电弓图';
ALTER TABLE 5c_point_hisotry_data MODIFY COLUMN skate_photo text COMMENT '滑板图';
ALTER TABLE 5c_point_hisotry_data MODIFY COLUMN locomotive_photo text COMMENT '车号图';

-- 2018/03/09

-- 新增组件 车辆选择
REPLACE INTO `p_dictionary_item` VALUES('0aedc45fa4d042e7872dd8265cf5d523', '车辆选择', 'trainSelect', 'groupware', '3545', '0', null,'2018-03-09 09:15:02', null,'2018-03-09 09:16:12', null,null,'6d85d1bb43c24b82a744e75be59ef437');

-- 12345C 添加字段 车辆编号，2C配置为不开启
REPLACE INTO `sys_property_resources` VALUES('7cb72901d66744ca8c3bade1f16d134f', '车辆编号', '车辆编号', 'TG016287', 'string', '1', 'd1c10451641642459653bd9fec07d370', 'train_id', 'trainName', 'text', null,'1', '0', '1', '16287', '0', null,'trainId', null,'0', '0', '0', '20', null,'0');
REPLACE INTO `sys_property_resources` VALUES('e751397a458d46d0aeb121fd7764bd65', '车辆编号', '车辆编号', 'TG016288', 'string', '1', '79f4da0333254d0d9dedb55176008839', 'train_id', 'trainName', 'text', null,'0', '0', '0', '16288', '0', null,'trainId', null,'0', '0', '0', '20', null,'0');
REPLACE INTO `sys_property_resources` VALUES('079b3dab78d14b478d0ceefc35a8b42a', '车辆编号', '车辆编号', 'TG016289', 'string', '1', '028d5979bf6344e0b0baf854d0b3450c', 'train_id', 'trainName', 'text', null,'1', '0', '1', '16289', '0', null,'trainId', null,'0', '0', '0', '20', null,'0');
REPLACE INTO `sys_property_resources` VALUES('8c27b4955f7e43e78ba70b45c0fa868f', '车辆编号', '车辆编号', 'TG016290', 'string', '1', '89a1d14693434a7ea4268b020214a782', 'train_id', 'trainName', 'text', null,'1', '0', '1', '16290', '0', null,'trainId', null,'0', '0', '0', '20', null,'0');
REPLACE INTO `sys_property_resources` VALUES('ffe961f59b5e4aa7bf3625914be6fb76', '车辆编号', '车辆编号', 'TG016291', 'string', '1', '17ab9f9b7704460caa4861d5686ff6ac', 'train_id', 'trainName', 'text', null,'1', '0', '1', '16291', '0', null,'trainId', null,'0', '0', '0', null,null,'0');

-- 1C检测作业处级与段级 添加 优良扣分数之和  合格扣分数之和 不合格里程详情
ALTER TABLE 6c_check_1c ADD good_points int(11) COMMENT '优良扣分数之和';
ALTER TABLE 6c_check_1c ADD qualified_points int(11) COMMENT '合格扣分数之和';
ALTER TABLE 6c_check_1c ADD unqualified_detail varchar(200) DEFAULT NULL COMMENT '不合格里程详情';
ALTER TABLE 6c_check_1c_section ADD good_points int(11) COMMENT '优良扣分数之和';
ALTER TABLE 6c_check_1c_section ADD qualified_points int(11) COMMENT '合格扣分数之和';
ALTER TABLE 6c_check_1c_section ADD unqualified_detail varchar(200) DEFAULT NULL COMMENT '不合格里程详情';

-- 计划添加字段-联系人
ALTER TABLE 6c_plan ADD contacts varchar(100) COMMENT '联系人';
ALTER TABLE 6c_plan MODIFY COLUMN start_psa_id text COMMENT '上车地点';
ALTER TABLE 6c_plan MODIFY COLUMN end_psa_id text COMMENT '下车地点';

-- 计划部门中间表
CREATE TABLE `6c_plan_dept` (
  `plan_id` TEXT,
  `dept_id` TEXT
) ENGINE=INNODB DEFAULT CHARSET=utf8

-- 2018/03/13
-- 1C检测作业因原有统计合格、优良、不合格里程逻辑改变 所以需要处理现场已经存在的数据
update 6c_check_1c set qualified_mileage = qualified_mileage + good_mileage;
update 6c_check_1c_section set qualified_mileage = qualified_mileage + good_mileage;

--1C质量检测缺陷资源数据
REPLACE INTO `sys_resources` VALUES('b91f7b29dfbe4b0c87d3e8c99da5103d', '1C质量检测缺陷', 'C1CheckDefect', null,'d1c10451641642459653bd9fec07d370', null,'0,42378514749f4574be195d9fe4a4c0ec,d1c10451641642459653bd9fec07d370,', null,'C1CheckDefect', '390');


CREATE TABLE `6c_defect_repeat_count` (
  `id` VARCHAR(50) NOT NULL,
  `check_date_year` VARCHAR(50) DEFAULT NULL COMMENT '日期年',
  `line_id` VARCHAR(50) DEFAULT NULL COMMENT '线路ID',
  `plliar_id` VARCHAR(50) DEFAULT NULL COMMENT '支柱ID',
  `defect_type` VARCHAR(50) DEFAULT NULL COMMENT '缺陷类型',
   `defect_level` VARCHAR(50) DEFAULT NULL COMMENT '缺陷等级',
   `system_id` VARCHAR(50) DEFAULT NULL COMMENT 'C类型',
   `count` INT(11) DEFAULT NULL COMMENT '缺陷统计',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8

ALTER TABLE 6c_defect ADD defect_repeat_count_id varchar(50) COMMENT '缺陷重复';


ALTER TABLE 6c_check_1c ADD plan_id varchar(50) COMMENT '计划ID';



ALTER TABLE 6c_defect_repeat_count ADD glb varchar(50) COMMENT '公里标';
ALTER TABLE 6c_defect_repeat_count ADD direction varchar(50) COMMENT '行别';

-- 2018-3-20
CREATE TABLE `6c_plan_base` (
  `id` VARCHAR(50) NOT NULL,
  `plan_date` DATE DEFAULT NULL COMMENT '计划检测日期',
  `complete_date` DATE DEFAULT NULL COMMENT '实际检测日期',
  `train_number` VARCHAR(50) DEFAULT NULL COMMENT '车次',
  `train_id` VARCHAR(50) DEFAULT NULL COMMENT '机车',
  `contacts` VARCHAR(300) DEFAULT NULL COMMENT '联系人',
  `plan_status` VARCHAR(50) DEFAULT NULL COMMENT '计划状态',
  `audit_status` VARCHAR(50) DEFAULT NULL COMMENT '计划审核状态',
  `remark` VARCHAR(100) DEFAULT NULL COMMENT '备注',
  `system_id` VARCHAR(50) DEFAULT NULL COMMENT 'C类型',
  `del_flag` SMALLINT(1) DEFAULT '0',
  `create_ip` VARCHAR(50) DEFAULT NULL,
  `create_by` VARCHAR(50) DEFAULT NULL,
  `create_date` DATETIME DEFAULT NULL,
  `update_ip` VARCHAR(50) DEFAULT NULL,
  `update_by` VARCHAR(50) DEFAULT NULL,
  `update_date` DATETIME DEFAULT NULL,
  `delete_ip` VARCHAR(50) DEFAULT NULL,
  `delete_by` VARCHAR(50) DEFAULT NULL,
  `delete_date` DATETIME DEFAULT NULL,
  `sort` INT(10) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  KEY `sort` (`sort`)
) ENGINE=INNODB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;

CREATE TABLE `6c_plan_detail` (
  `id` VARCHAR(50) NOT NULL,
  `line_id` VARCHAR(50) DEFAULT NULL COMMENT '线路',
  `direction` VARCHAR(50) DEFAULT NULL COMMENT '行别',
  `check_region` TEXT COMMENT '检测区间',
  `patcher` VARCHAR(50) DEFAULT NULL COMMENT '添乘人',
  `plan_base_id` VARCHAR(50) DEFAULT NULL COMMENT '计划Id',
  `del_flag` SMALLINT(1) DEFAULT '0',
  `create_ip` VARCHAR(50) DEFAULT NULL,
  `create_by` VARCHAR(50) DEFAULT NULL,
  `create_date` DATETIME DEFAULT NULL,
  `update_ip` VARCHAR(50) DEFAULT NULL,
  `update_by` VARCHAR(50) DEFAULT NULL,
  `update_date` DATETIME DEFAULT NULL,
  `delete_ip` VARCHAR(50) DEFAULT NULL,
  `delete_by` VARCHAR(50) DEFAULT NULL,
  `delete_date` DATE DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE `6c_plan_execute` (
  `id` VARCHAR(50) NOT NULL,
  `plan_base_id` VARCHAR(50) DEFAULT NULL COMMENT '计划ID',
  `plan_detail_id` VARCHAR(50) DEFAULT NULL COMMENT '计划线路ID',
  `start_station` VARCHAR(300) COMMENT '上车站',
  `end_station` VARCHAR(300) COMMENT '下车站',
  `responsible_dept_id` VARCHAR(50) DEFAULT NULL COMMENT '负责部门',
  `dept_id` VARCHAR(50) DEFAULT NULL COMMENT '添乘部门',
  `patcher` VARCHAR(50) DEFAULT NULL COMMENT '添乘人',
  `add_date` DATETIME DEFAULT NULL COMMENT '添乘时间',
  `implementation` VARCHAR(300) DEFAULT NULL COMMENT '执行情况',
  `section_id` VARCHAR(50) DEFAULT NULL COMMENT '段Id',
  `system_id` VARCHAR(50) DEFAULT NULL COMMENT 'C类型',
  `execute_status` VARCHAR(50) DEFAULT NULL COMMENT '状态',
  `remark` TEXT COMMENT '备注',
  `del_flag` SMALLINT(1) DEFAULT '0',
  `create_ip` VARCHAR(50) DEFAULT NULL,
  `create_by` VARCHAR(50) DEFAULT NULL,
  `create_date` DATETIME DEFAULT NULL,
  `update_ip` VARCHAR(50) DEFAULT NULL,
  `update_by` VARCHAR(50) DEFAULT NULL,
  `update_date` DATETIME DEFAULT NULL,
  `delete_ip` VARCHAR(50) DEFAULT NULL,
  `delete_by` VARCHAR(50) DEFAULT NULL,
  `delete_date` DATETIME DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

ALTER TABLE 6c_check_1c ADD train_id varchar(50) COMMENT '机车ID';

ALTER TABLE 6c_defect ADD repeat_count int(11) COMMENT '缺陷重复次数';

-- 2018-3-27
CREATE TABLE `6c_defect_assortment` (
  `id` VARCHAR(50) NOT NULL,
  `equ_id` VARCHAR(50) DEFAULT NULL COMMENT '设备位置',
  `defect_type_id` VARCHAR(50) DEFAULT NULL COMMENT '缺陷类型',
  `defect_describe` VARCHAR(300) DEFAULT NULL COMMENT '缺陷描述',
  `data_variable` VARCHAR(50) DEFAULT NULL COMMENT '数据变量',
  `defect_data_level` VARCHAR(50) DEFAULT NULL COMMENT '缺陷数据等级',
  `system_id` VARCHAR(50) DEFAULT NULL COMMENT 'C类型',
  `sort` INT(11) NOT NULL AUTO_INCREMENT COMMENT '排序',
  `del_flag` SMALLINT(1) DEFAULT '0',
  `create_ip` VARCHAR(50) DEFAULT NULL,
  `create_by` VARCHAR(50) DEFAULT NULL,
  `create_date` DATETIME DEFAULT NULL,
  `update_ip` VARCHAR(50) DEFAULT NULL,
  `update_by` VARCHAR(50) DEFAULT NULL,
  `update_date` DATETIME DEFAULT NULL,
  `delete_ip` VARCHAR(50) DEFAULT NULL,
  `delete_by` VARCHAR(50) DEFAULT NULL,
  `delete_date` DATETIME DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sort` (`sort`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='6C缺陷分类';

ALTER TABLE 6c_c1_info ADD rawdata_status varchar(50) COMMENT '数据状态';

ALTER TABLE 6c_defect_repeat_count change plliar_id pillar_id varchar(50) COMMENT '支柱';

DROP TABLE IF EXISTS `6c_defect_repeat_count`;

CREATE TABLE `6c_defect_repeat_count` (
  `id` varchar(50) NOT NULL,
  `count` int(11) DEFAULT NULL COMMENT '默认间距（20m）统计重复次数',
  `count1` int(11) DEFAULT NULL COMMENT '间距20m统计重复次数',
  `count2` int(11) DEFAULT NULL COMMENT '间距30m统计重复次数',
  `count3` int(11) DEFAULT NULL COMMENT '间距40m统计重复次数',
  `defect_repeat_ids` text,
  `defect_id1` varchar(50) DEFAULT NULL COMMENT '间距20m统计缺陷id',
  `defect_id2` varchar(50) DEFAULT NULL COMMENT '间距30m统计缺陷id',
  `defect_id3` varchar(50) DEFAULT NULL COMMENT '间距40m统计缺陷id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE 6c_defect_repeat_count ADD defect_id4 varchar(50) COMMENT '间距50m统计缺陷id';

-- 2018-4-8
CREATE TABLE `6c_mileage_statistics` (
  `id` VARCHAR(50) NOT NULL,
  `statistics_date` DATETIME DEFAULT NULL COMMENT '统计日期',
  `equ_num` VARCHAR(50) DEFAULT NULL COMMENT '设备编号',
  `line_id` VARCHAR(50) DEFAULT NULL COMMENT '线路id',
  `mileage` DECIMAL(10,3) DEFAULT NULL COMMENT '行驶里程数（公里）',
  `alarm_num` INT(10) DEFAULT NULL COMMENT '报警数',
  `section_id` VARCHAR(50) DEFAULT NULL COMMENT '段别',
  `del_flag` SMALLINT(1) DEFAULT '0',
  `create_ip` VARCHAR(50) DEFAULT NULL,
  `create_by` VARCHAR(50) DEFAULT NULL,
  `create_date` DATETIME DEFAULT NULL,
  `update_ip` VARCHAR(50) DEFAULT NULL,
  `update_by` VARCHAR(50) DEFAULT NULL,
  `update_date` DATETIME DEFAULT NULL,
  `delete_ip` VARCHAR(50) DEFAULT NULL,
  `delete_by` VARCHAR(50) DEFAULT NULL,
  `delete_date` DATETIME DEFAULT NULL,
  `sort` INT(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  KEY `sort` (`sort`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- 2018-4-12
CREATE TABLE `6c_plan_execute_record` (
  `id` VARCHAR(50) NOT NULL,
  `plan_execute_id` VARCHAR(50) DEFAULT NULL COMMENT '计划执行ID',
  `dept_id` VARCHAR(50) DEFAULT NULL COMMENT '添乘部门',
  `patcher` VARCHAR(50) DEFAULT NULL COMMENT '添乘人',
  `multiplication_date` DATETIME DEFAULT NULL COMMENT '添乘时间',
  `start_station` VARCHAR(100) DEFAULT NULL COMMENT '上车站',
  `end_station` VARCHAR(100) DEFAULT NULL COMMENT '下车站',
  `implementation` VARCHAR(300) DEFAULT NULL COMMENT '执行情况',
  `record_status` VARCHAR(50) DEFAULT NULL COMMENT '状态',
  `remark` TEXT COMMENT '备注',
  `del_flag` SMALLINT(1) DEFAULT '0',
  `create_ip` VARCHAR(50) DEFAULT NULL,
  `create_by` VARCHAR(50) DEFAULT NULL,
  `create_date` DATETIME DEFAULT NULL,
  `update_ip` VARCHAR(50) DEFAULT NULL,
  `update_by` VARCHAR(50) DEFAULT NULL,
  `update_date` DATETIME DEFAULT NULL,
  `delete_ip` VARCHAR(50) DEFAULT NULL,
  `delete_by` VARCHAR(50) DEFAULT NULL,
  `delete_date` DATETIME DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


-- 2018-4-17
ALTER TABLE 3c_defect_package ADD panorama_photo text COMMENT '全景照片';
ALTER TABLE 3c_defect_package ADD alarm_status VARCHAR(100) COMMENT '报警状态';
-- 2018-5-25

ALTER TABLE 4c_defect_package ADD original_photo text COMMENT '照片';

ALTER TABLE 6c_defect_handle_info ADD comment text COMMENT '驳回意见';

CREATE TABLE `6c_metadata` (
  `id` varchar(50) NOT NULL,
  `name` varchar(50) DEFAULT NULL COMMENT '标题',
  `type` varchar(50) DEFAULT NULL COMMENT '文件类型',
  `code` varchar(50) DEFAULT NULL COMMENT '文件编码',
  `uploader` varchar(50) DEFAULT NULL COMMENT '上传者',
  `upload_date` datetime DEFAULT NULL COMMENT '上传时间',
  `data` text COMMENT '文件',
  `remark` text COMMENT '备注',
  `del_flag` smallint(6) DEFAULT NULL,
  `create_by` varchar(50) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `create_ip` varchar(50) DEFAULT NULL,
  `update_by` varchar(50) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_ip` varchar(50) DEFAULT NULL,
  `delete_by` varchar(50) DEFAULT NULL,
  `delete_date` datetime DEFAULT NULL,
  `delete_ip` varchar(50) DEFAULT NULL,
  `sort` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '排序',
  PRIMARY KEY (`id`),
  KEY `sort` (`sort`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8

-- 2018-5-30 
ALTER TABLE 6c_defect_check_handle ADD last_static_measurement  varchar(100) DEFAULT NULL COMMENT '上一次静态测量值';
ALTER TABLE 6c_defect_check_handle ADD rail_standard_value varchar(100) DEFAULT NULL COMMENT '轨面标准值';
ALTER TABLE 6c_defect_check_handle ADD  side_critical_value varchar(100) DEFAULT NULL COMMENT '侧面临界值';

ALTER TABLE 3c_defect_package ADD   arcing_time datetime DEFAULT NULL COMMENT '燃弧时间';

ALTER TABLE 4c_defect_package ADD  original_photo text COMMENT '原图';

ALTER TABLE 6c_metadata ADD system_id varchar(50) DEFAULT NULL COMMENT 'C类型';
-- 2018-6-14
ALTER TABLE 6c_check_1c_section ADD dept_id varchar(50) COMMENT '工区';
ALTER TABLE 6c_check_1c_section ADD work_shop_id varchar(50) COMMENT '车间';
-- 2018-7-02
ALTER TABLE `togest_platform`.`6c_defect` ADD COLUMN `typical_defect` TINYINT(1) NULL COMMENT '典型缺陷' AFTER `system_id`; 
ALTER TABLE `togest_platform`.`6c_c1_info` ADD COLUMN `defectdata_status` VARCHAR(50) NULL COMMENT '缺陷数据状态' AFTER `sort`; 
ALTER TABLE `togest_platform`.`6c_defect_handle_info` ADD COLUMN `confirm_status` TINYINT(1) NULL COMMENT '确认状态' AFTER `is_needreform`; 
-- 2018-7-16
  ALTER TABLE `togest_platform`.`6c_plan_execute` ADD COLUMN `audit_person` VARCHAR(50) NULL COMMENT '审批人' AFTER `dept_id`; 
  ALTER TABLE `togest_platform`.`6c_plan_execute` ADD COLUMN `audit_date` DATETIME NULL COMMENT '审批时间', AFTER `audit_person`; 
  ALTER TABLE `togest_platform`.`6c_plan_execute` ADD COLUMN `confirm_person` VARCHAR(50) NULL COMMENT '确认人' AFTER `add_date`; 
  ALTER TABLE `togest_platform`.`6c_plan_execute` ADD COLUMN  `confirm_date` DATETIME DEFAULT NULL COMMENT '确认时间' AFTER `confirm_person`; 
-- 2018-7-17
ALTER TABLE `togest_platform`.`6c_notice_section` ADD COLUMN `send_person` VARCHAR(32) NULL COMMENT '下发人' AFTER `status`, ADD COLUMN `send_date` DATETIME NULL COMMENT '下发日期' AFTER `send_person`; 
-- 2018-7-25
CREATE TABLE `6c_info` (
  `id` varchar(50) NOT NULL,
  `source` varchar(50) DEFAULT NULL COMMENT '数据源',
  `check_date` date DEFAULT NULL COMMENT '日期',
  `line_id` varchar(50) DEFAULT NULL COMMENT '线路',
  `direction` varchar(50) DEFAULT NULL COMMENT '行别',
  `start_station` varchar(50) DEFAULT NULL COMMENT '起始站',
  `end_station` varchar(50) DEFAULT NULL COMMENT '结束站',
  `analyst` varchar(50) DEFAULT NULL COMMENT '分析人',
  `analy_date` date DEFAULT NULL COMMENT '分析时间',
  `upload_person` varchar(50) DEFAULT NULL COMMENT '上传人',
  `upload_date` date DEFAULT NULL COMMENT '上传时间',
  `data_size` varchar(50) DEFAULT NULL COMMENT '容量大小',
  `package_name` varchar(300) DEFAULT NULL COMMENT '包名',
  `package_path` varchar(300) DEFAULT NULL COMMENT '包路径',
  `analy_status` varchar(50) DEFAULT NULL COMMENT '分析状态',
  `defect_data_status` varchar(50) DEFAULT NULL COMMENT '缺陷数据状态',
  `rawdata_status` varchar(50) DEFAULT NULL COMMENT '原始数据状态',
  `system_id` varchar(50) DEFAULT NULL COMMENT '所属类型',
  `section_id` varchar(50) DEFAULT NULL COMMENT '段别',
  `del_flag` smallint(1) DEFAULT '0' COMMENT '删除状态',
  `sort` int(10) NOT NULL AUTO_INCREMENT COMMENT '排序',
  `create_ip` varchar(50) DEFAULT NULL,
  `create_by` varchar(50) DEFAULT NULL,
  `create_date` date DEFAULT NULL,
  `update_ip` varchar(50) DEFAULT NULL,
  `update_by` varchar(50) DEFAULT NULL,
  `update_date` date DEFAULT NULL,
  `delete_ip` varchar(50) DEFAULT NULL,
  `delete_by` varchar(50) DEFAULT NULL,
  `delete_date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sort` (`sort`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

ALTER TABLE `togest_platform`.`6c_c1_info` DROP COLUMN `package_name`,DROP COLUMN `package_path`,DROP COLUMN `defect_data_status`,DROP COLUMN `rawdata_status`
ALTER TABLE `togest_platform`.`6c_c2_info` DROP COLUMN `package_name`,DROP COLUMN `package_path`,DROP COLUMN `defect_data_status`,DROP COLUMN `rawdata_status`
ALTER TABLE `togest_platform`.`6c_c3_info` DROP COLUMN `package_name`,DROP COLUMN `package_path`,DROP COLUMN `defect_data_status`,DROP COLUMN `rawdata_status`
ALTER TABLE `togest_platform`.`6c_c4_info` DROP COLUMN `package_name`,DROP COLUMN `package_path`,DROP COLUMN `defect_data_status`,DROP COLUMN `rawdata_status` 

-- 2018-7-26
ALTER TABLE `togest_platform`.`6c_c1_info` ADD COLUMN `check_date` DATE NULL COMMENT '日期' AFTER `source`, ADD COLUMN `line_id` VARCHAR(50) NULL COMMENT '线路' AFTER `check_date`, ADD COLUMN `direction` VARCHAR(50) NULL COMMENT '行别' AFTER `line_id`, ADD COLUMN `start_station` VARCHAR(50) NULL COMMENT '起始站' AFTER `direction`, ADD COLUMN `end_station` VARCHAR(50) NULL COMMENT '结束站' AFTER `start_station`, ADD COLUMN `analyst` VARCHAR(50) NULL COMMENT '分析人' AFTER `end_station`, ADD COLUMN `analy_date` DATE NULL COMMENT '分析时间' AFTER `analyst`, ADD COLUMN `upload_person` VARCHAR(50) NULL COMMENT '上传人' AFTER `analy_date`, ADD COLUMN `upload_date` DATE NULL COMMENT '上传时间' AFTER `upload_person`, ADD COLUMN `data_size` VARCHAR(50) NULL COMMENT '容量大小' AFTER `upload_date`, ADD COLUMN `package_name` VARCHAR(300) NULL COMMENT '包名' AFTER `data_size`, ADD COLUMN `package_path` VARCHAR(300) NULL COMMENT '包路径' AFTER `package_name`, ADD COLUMN `analy_status` VARCHAR(50) NULL COMMENT '分析状态' AFTER `package_path`, ADD COLUMN `defect_data_status` VARCHAR(50) NULL COMMENT '缺陷数据状态' AFTER `analy_status`, ADD COLUMN `rawdata_status` VARCHAR(50) NULL COMMENT '原始数据状态' AFTER `defect_data_status`, ADD COLUMN `system_id` VARCHAR(50) NULL COMMENT '所属类型' AFTER `rawdata_status`, ADD COLUMN `section_id` VARCHAR(50) NULL COMMENT '段别' AFTER `system_id`; 
ALTER TABLE `togest_platform`.`6c_c2_info` ADD COLUMN `check_date` DATE NULL COMMENT '日期' AFTER `source`, ADD COLUMN `line_id` VARCHAR(50) NULL COMMENT '线路' AFTER `check_date`, ADD COLUMN `direction` VARCHAR(50) NULL COMMENT '行别' AFTER `line_id`, ADD COLUMN `start_station` VARCHAR(50) NULL COMMENT '起始站' AFTER `direction`, ADD COLUMN `end_station` VARCHAR(50) NULL COMMENT '结束站' AFTER `start_station`, ADD COLUMN `analyst` VARCHAR(50) NULL COMMENT '分析人' AFTER `end_station`, ADD COLUMN `analy_date` DATE NULL COMMENT '分析时间' AFTER `analyst`, ADD COLUMN `upload_person` VARCHAR(50) NULL COMMENT '上传人' AFTER `analy_date`, ADD COLUMN `upload_date` DATE NULL COMMENT '上传时间' AFTER `upload_person`, ADD COLUMN `data_size` VARCHAR(50) NULL COMMENT '容量大小' AFTER `upload_date`, ADD COLUMN `package_name` VARCHAR(300) NULL COMMENT '包名' AFTER `data_size`, ADD COLUMN `package_path` VARCHAR(300) NULL COMMENT '包路径' AFTER `package_name`, ADD COLUMN `analy_status` VARCHAR(50) NULL COMMENT '分析状态' AFTER `package_path`, ADD COLUMN `defect_data_status` VARCHAR(50) NULL COMMENT '缺陷数据状态' AFTER `analy_status`, ADD COLUMN `rawdata_status` VARCHAR(50) NULL COMMENT '原始数据状态' AFTER `defect_data_status`, ADD COLUMN `system_id` VARCHAR(50) NULL COMMENT '所属类型' AFTER `rawdata_status`, ADD COLUMN `section_id` VARCHAR(50) NULL COMMENT '段别' AFTER `system_id`; 
ALTER TABLE `togest_platform`.`6c_c3_info` ADD COLUMN `check_date` DATE NULL COMMENT '日期' AFTER `source`, ADD COLUMN `line_id` VARCHAR(50) NULL COMMENT '线路' AFTER `check_date`, ADD COLUMN `direction` VARCHAR(50) NULL COMMENT '行别' AFTER `line_id`, ADD COLUMN `start_station` VARCHAR(50) NULL COMMENT '起始站' AFTER `direction`, ADD COLUMN `end_station` VARCHAR(50) NULL COMMENT '结束站' AFTER `start_station`, ADD COLUMN `analyst` VARCHAR(50) NULL COMMENT '分析人' AFTER `end_station`, ADD COLUMN `analy_date` DATE NULL COMMENT '分析时间' AFTER `analyst`, ADD COLUMN `upload_person` VARCHAR(50) NULL COMMENT '上传人' AFTER `analy_date`, ADD COLUMN `upload_date` DATE NULL COMMENT '上传时间' AFTER `upload_person`, ADD COLUMN `data_size` VARCHAR(50) NULL COMMENT '容量大小' AFTER `upload_date`, ADD COLUMN `package_name` VARCHAR(300) NULL COMMENT '包名' AFTER `data_size`, ADD COLUMN `package_path` VARCHAR(300) NULL COMMENT '包路径' AFTER `package_name`, ADD COLUMN `analy_status` VARCHAR(50) NULL COMMENT '分析状态' AFTER `package_path`, ADD COLUMN `defect_data_status` VARCHAR(50) NULL COMMENT '缺陷数据状态' AFTER `analy_status`, ADD COLUMN `rawdata_status` VARCHAR(50) NULL COMMENT '原始数据状态' AFTER `defect_data_status`, ADD COLUMN `system_id` VARCHAR(50) NULL COMMENT '所属类型' AFTER `rawdata_status`, ADD COLUMN `section_id` VARCHAR(50) NULL COMMENT '段别' AFTER `system_id`; 
ALTER TABLE `togest_platform`.`6c_c4_info` ADD COLUMN `check_date` DATE NULL COMMENT '日期' AFTER `source`, ADD COLUMN `line_id` VARCHAR(50) NULL COMMENT '线路' AFTER `check_date`, ADD COLUMN `direction` VARCHAR(50) NULL COMMENT '行别' AFTER `line_id`, ADD COLUMN `start_station` VARCHAR(50) NULL COMMENT '起始站' AFTER `direction`, ADD COLUMN `end_station` VARCHAR(50) NULL COMMENT '结束站' AFTER `start_station`, ADD COLUMN `analyst` VARCHAR(50) NULL COMMENT '分析人' AFTER `end_station`, ADD COLUMN `analy_date` DATE NULL COMMENT '分析时间' AFTER `analyst`, ADD COLUMN `upload_person` VARCHAR(50) NULL COMMENT '上传人' AFTER `analy_date`, ADD COLUMN `upload_date` DATE NULL COMMENT '上传时间' AFTER `upload_person`, ADD COLUMN `data_size` VARCHAR(50) NULL COMMENT '容量大小' AFTER `upload_date`, ADD COLUMN `package_name` VARCHAR(300) NULL COMMENT '包名' AFTER `data_size`, ADD COLUMN `package_path` VARCHAR(300) NULL COMMENT '包路径' AFTER `package_name`, ADD COLUMN `analy_status` VARCHAR(50) NULL COMMENT '分析状态' AFTER `package_path`, ADD COLUMN `defect_data_status` VARCHAR(50) NULL COMMENT '缺陷数据状态' AFTER `analy_status`, ADD COLUMN `rawdata_status` VARCHAR(50) NULL COMMENT '原始数据状态' AFTER `defect_data_status`, ADD COLUMN `system_id` VARCHAR(50) NULL COMMENT '所属类型' AFTER `rawdata_status`, ADD COLUMN `section_id` VARCHAR(50) NULL COMMENT '段别' AFTER `system_id`; 

ALTER TABLE `togest_platform`.`6c_info` ADD COLUMN `plan_id` VARCHAR(50) NULL COMMENT '计划执行ID' AFTER `id`; 
ALTER TABLE `togest_platform`.`6c_c1_info` ADD COLUMN `plan_id` VARCHAR(50) NULL COMMENT '计划执行ID' AFTER `id`; 
ALTER TABLE `togest_platform`.`6c_c2_info` ADD COLUMN `plan_id` VARCHAR(50) NULL COMMENT '计划执行ID' AFTER `id`; 
ALTER TABLE `togest_platform`.`6c_c3_info` ADD COLUMN `plan_id` VARCHAR(50) NULL COMMENT '计划执行ID' AFTER `id`; 
ALTER TABLE `togest_platform`.`6c_c4_info` ADD COLUMN `plan_id` VARCHAR(50) NULL COMMENT '计划执行ID' AFTER `id`; 

-- 2018-7-27
ALTER TABLE `togest_platform`.`6c_plan_execute` ADD COLUMN `actual_add_date` DATETIME NULL COMMENT '实际添乘日期' AFTER `confirm_date`, ADD COLUMN `actual_patcher` VARCHAR(50) NULL COMMENT '实际添乘人' AFTER `actual_add_date`, ADD COLUMN `actual_check_region` VARCHAR(50) NULL COMMENT '实际检测区间' AFTER `actual_patcher`, ADD COLUMN `actual_add_train_number` VARCHAR(50) NULL COMMENT '实际添乘车次' AFTER `actual_check_region`, ADD COLUMN `actual_add_traffic_number` VARCHAR(50) NULL COMMENT '实际添乘机车号' AFTER `actual_add_train_number`, ADD COLUMN `equ_no` VARCHAR(50) NULL COMMENT '设备编号' AFTER `actual_add_traffic_number`, ADD COLUMN `equ_operation` VARCHAR(300) NULL COMMENT '设备运行情况' AFTER `equ_no`; 
-- 2018-8-01
ALTER TABLE `togest_platform`.`2c_defect_package` ADD COLUMN `equ_name` VARCHAR(50) NULL COMMENT '设备名称' AFTER `analytical_date`;
-- 2018-8-03
ALTER TABLE `togest_platform`.`2c_defect_package` DROP COLUMN `equ_name`;
ALTER TABLE `togest_platform`.`6c_defect` ADD COLUMN `equ_name` VARCHAR(50) NULL COMMENT '设备名称' AFTER `geom`, ADD COLUMN `is_show` SMALLINT(1) NULL COMMENT '是否显示' AFTER `equ_name`; 

ALTER TABLE 6c_defect MODIFY COLUMN speed decimal(10,3) NULL COMMENT '速度';

-- 2018-8-13
ALTER TABLE `togest_platform`.`4c_defect_package` ADD COLUMN `testing_person` VARCHAR(50) NULL COMMENT '检测人员' AFTER `analytical_date`; 
ALTER TABLE `togest_platform`.`2c_defect_package` ADD COLUMN `testing_person` VARCHAR(50) NULL COMMENT '检测人员' AFTER `analytical_date`, ADD COLUMN `original_photo` TEXT NULL COMMENT '原图' AFTER `testing_person`; ||||||| .r20859

ALTER TABLE `togest_platform`.`6c_info` CHANGE `data_size` `data_size` DECIMAL(10,5) NULL COMMENT '容量大小'; 
ALTER TABLE `togest_platform`.`6c_c1_info` CHANGE `data_size` `data_size` DECIMAL(10,5) NULL COMMENT '容量大小'; 
ALTER TABLE `togest_platform`.`6c_c2_info` CHANGE `data_size` `data_size` DECIMAL(10,5) NULL COMMENT '容量大小'; 
ALTER TABLE `togest_platform`.`6c_c3_info` CHANGE `data_size` `data_size` DECIMAL(10,5) NULL COMMENT '容量大小'; 
ALTER TABLE `togest_platform`.`6c_c4_info` CHANGE `data_size` `data_size` DECIMAL(10,5) NULL COMMENT '容量大小'; 

-- 2018-8-15
ALTER TABLE `togest_platform`.`6c_defect` ADD COLUMN `defect_assortment_id` VARCHAR(50) NULL COMMENT '缺陷分类ID' AFTER `id`; 
ALTER TABLE `togest_platform`.`6c_defect` ADD COLUMN `defect_data_category` VARCHAR(50) NULL COMMENT '缺陷类别(字典)' AFTER `defect_assortment_id`; 
ALTER TABLE `togest_platform`.`6c_defect` ADD COLUMN `equ_position` VARCHAR(50) NULL COMMENT '设备位置'; 

ALTER TABLE `togest_platform`.`6c_defect_assortment` ADD COLUMN `defect_data_category` VARCHAR(50) NULL COMMENT '缺陷类别(字典)' AFTER `system_id`, ADD COLUMN `min_speed` DECIMAL(10,3) NULL COMMENT '最小速度' AFTER `defect_data_category`, ADD COLUMN `max_speed` DECIMAL(10,3) NULL COMMENT '最大速度' AFTER `min_speed`; 

CREATE TABLE `6c_equ_position` (
  `id` varchar(50) NOT NULL,
  `equ_id` varchar(50) DEFAULT NULL COMMENT '关联设备',
  `code` varchar(50) DEFAULT NULL COMMENT '编码',
  `name` varchar(50) DEFAULT NULL COMMENT '设备位置名',
  `system_id` varchar(50) DEFAULT NULL COMMENT 'C类型',
  `remark` varchar(50) DEFAULT NULL COMMENT '备注',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `del_flag` smallint(1) DEFAULT '0',
  `create_ip` varchar(50) DEFAULT NULL,
  `create_by` varchar(50) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_ip` varchar(50) DEFAULT NULL,
  `update_by` varchar(50) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `delete_ip` varchar(50) DEFAULT NULL,
  `delete_by` varchar(50) DEFAULT NULL,
  `delete_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- 2018-8-17
ALTER TABLE `togest_platform`.`6c_defect_assortment` ADD COLUMN `speed_type` VARCHAR(50) NULL COMMENT '速度等级(等级)' AFTER `defect_data_level`; 

-- 2018-8-22

CREATE TABLE `6c_defect_handle_deadline` (
  `id` VARCHAR(50) NOT NULL,
  `defect_data_level` VARCHAR(50) DEFAULT NULL COMMENT '缺陷数据等级',
  `defect_data_category` VARCHAR(50) DEFAULT NULL COMMENT '缺陷类别(字典)',
  `review_deadline` DECIMAL(10,3) DEFAULT NULL COMMENT '复核期限',
  `reform_deadline` DECIMAL(10,3) DEFAULT NULL COMMENT '整改期限',
  `sort` INT(11) NOT NULL AUTO_INCREMENT COMMENT '排序',
  `del_flag` SMALLINT(1) DEFAULT '0',
  `create_ip` VARCHAR(50) DEFAULT NULL,
  `create_by` VARCHAR(50) DEFAULT NULL,
  `create_date` DATETIME DEFAULT NULL,
  `update_ip` VARCHAR(50) DEFAULT NULL,
  `update_by` VARCHAR(50) DEFAULT NULL,
  `update_date` DATETIME DEFAULT NULL,
  `delete_ip` VARCHAR(50) DEFAULT NULL,
  `delete_by` VARCHAR(50) DEFAULT NULL,
  `delete_date` DATETIME DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sort` (`sort`)
) ENGINE=INNODB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8 COMMENT='缺陷复核整改期限'
-- 2018-8-23
ALTER TABLE `togest_platform`.`6c_defect` ADD COLUMN `defect_data_level` VARCHAR(50) NULL COMMENT '缺陷数据等级' AFTER `defect_assortment_id`; 
ALTER TABLE `togest_platform`.`6c_defect_handle_info` ADD COLUMN `check_timeouted` TINYINT(1) NULL COMMENT '复核超时' AFTER `is_needreform`; 
ALTER TABLE `togest_platform`.`6c_defect_handle_deadline` CHANGE `review_deadline` `review_deadline` INT NULL COMMENT '复核期限', CHANGE `reform_deadline` `reform_deadline` INT NULL COMMENT '整改期限'; 
-- 2018-10-08
ALTER TABLE `togest_platform`.`6c_defect` ADD COLUMN `manual_flag` TINYINT(1) NULL COMMENT '人工缺陷分级标识' AFTER `typical_defect`; 

-- 2018-10-25
ALTER TABLE 6c_defect MODIFY COLUMN check_time VARCHAR(50) COMMENT '检测时间';

--2018-11-12
ALTER TABLE `6c_plan_detail`
ADD COLUMN `start_station_name`  varchar(60) NULL DEFAULT NULL COMMENT '上车站名称' AFTER `end_station`,
ADD COLUMN `end_station_name`  varchar(60) NULL DEFAULT NULL COMMENT '下车站名称' AFTER `start_station_name`;

ALTER TABLE `6c_info`
ADD COLUMN `start_station_name`  varchar(60) NULL DEFAULT NULL COMMENT '上车站名称' AFTER `end_station`,
ADD COLUMN `end_station_name`  varchar(60) NULL DEFAULT NULL COMMENT '下车站名称  ' AFTER `start_station_name`;

ALTER TABLE `6c_c2_info`
ADD COLUMN `start_station_name`  varchar(60) NULL DEFAULT NULL COMMENT '上车站名称 ' AFTER `end_station`,
ADD COLUMN `end_station_name`  varchar(60) NULL DEFAULT NULL COMMENT '下车站名称 ' AFTER `start_station_name`;

--2018-11-16 4c缺陷导入
ALTER TABLE `4c_defect_package`
ADD COLUMN `note`  text NULL COMMENT '备注' AFTER `original_photo`;

-- 2019-01-25
ALTER TABLE `togest_platform`.`p_department_extend`     ADD COLUMN `change_flag` VARCHAR(50) NULL COMMENT '变更标志';

-- 2019-01-28
ALTER TABLE `sys_property_resources`
MODIFY COLUMN `custom_sort`  int(11) NOT NULL AUTO_INCREMENT AFTER `edit_tag`;

--2019-02-27
ALTER TABLE `togest_platform`.`6c_defect_repeat_count`    ADD COLUMN `count4` int(11) NULL COMMENT '间距50m统计重复次数';
ALTER TABLE `togest_platform`.`6c_defect_repeat_count`    ADD COLUMN `count5` int(11) NULL COMMENT '间距100m统计重复次数';
ALTER TABLE `togest_platform`.`6c_defect_repeat_count`    ADD COLUMN `count6` int(11) NULL COMMENT '间距150m统计重复次数';
ALTER TABLE `togest_platform`.`6c_defect_repeat_count`    ADD COLUMN `count7` int(11) NULL COMMENT '间距200m统计重复次数';
ALTER TABLE `togest_platform`.`6c_defect_repeat_count`    ADD COLUMN `defect_id5` VARCHAR(50) NULL COMMENT '间距100m统计缺陷id';
ALTER TABLE `togest_platform`.`6c_defect_repeat_count`    ADD COLUMN `defect_id6` VARCHAR(50) NULL COMMENT '间距150m统计缺陷id';
ALTER TABLE `togest_platform`.`6c_defect_repeat_count`    ADD COLUMN `defect_id7` VARCHAR(50) NULL COMMENT '间距200m统计缺陷id';