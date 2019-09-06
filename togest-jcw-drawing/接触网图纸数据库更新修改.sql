--2018-10-14 接触网图纸表新增 线路id字段
ALTER TABLE `jcw_equ_data_drawing`
ADD COLUMN `line_id`  varchar(50) NULL DEFAULT NULL COMMENT '线路id' AFTER `code`;

--2018-10-19 接触网规章制度
CREATE TABLE `jcw_equ_rules_regulation` (
  `id` varchar(50) NOT NULL,
  `name` varchar(100) DEFAULT NULL COMMENT '名称',
  `code` varchar(50) DEFAULT NULL COMMENT '编码',
  `type` varchar(50) DEFAULT NULL COMMENT '类型',
  `assortment_id` varchar(50) DEFAULT NULL COMMENT '分类id',
  `version` varchar(50) DEFAULT NULL COMMENT '版本',
  `upload_date` datetime DEFAULT NULL COMMENT '上传时间',
  `file_id` varchar(50) DEFAULT NULL COMMENT '附件ID',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `field1` varchar(50) DEFAULT NULL,
  `field2` varchar(50) DEFAULT NULL,
  `field3` varchar(50) DEFAULT NULL,
  `field4` varchar(50) DEFAULT NULL,
  `field5` varchar(50) DEFAULT NULL,
  `field6` varchar(50) DEFAULT NULL,
  `field7` int(11) DEFAULT NULL,
  `field8` int(11) DEFAULT NULL,
  `field9` datetime DEFAULT NULL,
  `field10` datetime DEFAULT NULL,
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `del_flag` smallint(6) DEFAULT NULL,
  `create_by` varchar(50) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` varchar(50) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `delete_by` varchar(50) DEFAULT NULL,
  `delete_date` datetime DEFAULT NULL,
  `pavilion_id` varchar(50) DEFAULT NULL COMMENT '所亭id',
  `psa_id` varchar(50) DEFAULT NULL COMMENT '站场id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='规章制度表';