-- 创建数据库
CREATE DATABASE IF NOT EXISTS linitly_boot DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_general_ci;
USE linitly_boot;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(190) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `INSTANCE_NAME` varchar(190) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(190) DEFAULT NULL,
  `JOB_GROUP` varchar(190) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(190) NOT NULL,
  `JOB_GROUP` varchar(190) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `qrtz_locks` VALUES ('quartzScheduler', 'TRIGGER_ACCESS');

DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(190) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `JOB_NAME` varchar(190) NOT NULL,
  `JOB_GROUP` varchar(190) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(190) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `sys_quartz_job`;
CREATE TABLE `sys_quartz_job` (
  `id`                          bigint            NOT NULL  AUTO_INCREMENT    COMMENT '主键',
  `job_name`                    varchar(20)       NOT NULL                    COMMENT '任务名称',
  `job_class_name`              varchar(100)      NOT NULL                    COMMENT '执行类',
  `cron_expression`             varchar(100)      NOT NULL                    COMMENT 'cron表达式',
  `description`                 varchar(255)      DEFAULT ''                  COMMENT '描述',
  `status`                      int(1)            DEFAULT 0                   COMMENT '任务状态',
  `enabled`                     int(1)            NOT NULL  DEFAULT 1         COMMENT '启用状态(1:启用(默认);0:禁用;)',
  `created_user_id`             bigint            NOT NULL  DEFAULT 0         COMMENT '创建人id',
  `created_time`                datetime          DEFAULT CURRENT_TIMESTAMP   COMMENT '创建时间',
  `last_modified_user_id`       bigint            NOT NULL  DEFAULT 0         COMMENT '最后修改人id',
  `last_modified_time`          datetime          DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  PRIMARY KEY (id),
  UNIQUE KEY un_group_name (job_name)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '定时任务信息表' ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id`                          bigint            NOT NULL  AUTO_INCREMENT    COMMENT '主键',
  `name`                        VARCHAR(20)       NOT NULL  DEFAULT ''        COMMENT '部门名称',
  `parent_id`                   bigint            NOT NULL  DEFAULT 0         COMMENT '上级部门id',
  `sort`                        int(11)           NOT NULL  DEFAULT 0         COMMENT '当前层级的排序',
  `child_number`                int(11)                     DEFAULT 0         COMMENT '子部门数',
  `remark`                      VARCHAR(255)                DEFAULT ''        COMMENT '备注',
  `enabled`                     int(1)            NOT NULL  DEFAULT 1         COMMENT '启用状态(1:启用(默认);0:禁用;)',
  `created_user_id`             bigint            NOT NULL  DEFAULT 0         COMMENT '创建人id',
  `created_time`                datetime          DEFAULT CURRENT_TIMESTAMP   COMMENT '创建时间',
  `last_modified_user_id`       bigint            NOT NULL  DEFAULT 0         COMMENT '最后修改人id',
  `last_modified_time`          datetime          DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  PRIMARY KEY (id),
  INDEX (`name`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统部门' ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post` (
  `id`                          bigint            NOT NULL  AUTO_INCREMENT    COMMENT '主键',
  `name`                        VARCHAR(20)       NOT NULL  DEFAULT ''        COMMENT '岗位名称',
  `sys_dept_id`                 bigint            NOT NULL                    COMMENT '系统部门id',
  `enabled`                     int(1)            NOT NULL  DEFAULT 1         COMMENT '启用状态(1:启用(默认);0:禁用;)',
  `created_user_id`             bigint            NOT NULL  DEFAULT 0         COMMENT '创建人id',
  `created_time`                datetime          DEFAULT CURRENT_TIMESTAMP   COMMENT '创建时间',
  `last_modified_user_id`       bigint            NOT NULL  DEFAULT 0         COMMENT '最后修改人id',
  `last_modified_time`          datetime          DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  PRIMARY KEY (id)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统岗位' ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `sys_admin_user`;
CREATE TABLE `sys_admin_user` (
  `id`                          bigint            NOT NULL  AUTO_INCREMENT    COMMENT '主键',
  `username`                    VARCHAR(32)       NOT NULL  DEFAULT ''        COMMENT '登录用户名',
  `mobile_number`               VARCHAR(13)       NOT NULL  DEFAULT ''        COMMENT '手机号',
  `salt`                        VARCHAR(32)       NOT NULL  DEFAULT ''        COMMENT '密码盐',
  `password`                    VARCHAR(32)       NOT NULL  DEFAULT ''        COMMENT '加密密码',
  `job_number`                  VARCHAR(32)       NOT NULL  DEFAULT ''        COMMENT '工号',
  `nick_name`                   VARCHAR(32)       NOT NULL  DEFAULT ''        COMMENT '昵称',
  `real_name`                   VARCHAR(16)                 DEFAULT ''        COMMENT '真实姓名',
  `email`                       VARCHAR(32)                 DEFAULT ''        COMMENT '邮箱',
  `sex`                         INT(1)            NOT NULL  DEFAULT 0         COMMENT '性别(1:男;2:女;)',
  `head_img_url`                VARCHAR(255)                DEFAULT ''        COMMENT '用户头像url地址',
  `enabled`                     int(1)            NOT NULL  DEFAULT 1         COMMENT '启用状态(1:启用(默认);0:禁用;)',
  `created_user_id`             bigint            NOT NULL  DEFAULT 0         COMMENT '创建人id',
  `created_time`                datetime          DEFAULT CURRENT_TIMESTAMP   COMMENT '创建时间',
  `last_modified_user_id`       bigint            NOT NULL  DEFAULT 0         COMMENT '最后修改人id',
  `last_modified_time`          datetime          DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  PRIMARY KEY (id),
  UNIQUE KEY un_mobile_number (`mobile_number`),
  UNIQUE KEY un_username (`username`),
  UNIQUE KEY un_job_number (`job_number`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统用户' ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id`                          bigint            NOT NULL  AUTO_INCREMENT    COMMENT '主键',
  `name`                        VARCHAR(16)       NOT NULL  DEFAULT ''        COMMENT '角色名',
  `code`                        VARCHAR(100)      NOT NULL  DEFAULT ''        COMMENT '角色代码',
  `description`                 VARCHAR(255)                DEFAULT ''        COMMENT '角色描述',
  `enabled`                     int(1)            NOT NULL  DEFAULT 1         COMMENT '启用状态(1:启用(默认);0:禁用;)',
  `created_user_id`             bigint            NOT NULL  DEFAULT 0         COMMENT '创建人id',
  `created_time`                datetime          DEFAULT CURRENT_TIMESTAMP   COMMENT '创建时间',
  `last_modified_user_id`       bigint            NOT NULL  DEFAULT 0         COMMENT '最后修改人id',
  `last_modified_time`          datetime          DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  PRIMARY KEY (id),
  UNIQUE KEY un_name (`name`),
  UNIQUE KEY un_code (`code`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统角色' ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `sys_admin_user_role`;
CREATE TABLE `sys_admin_user_role` (
  `id`                          bigint            NOT NULL  AUTO_INCREMENT    COMMENT '主键',
  `admin_user_id`               bigint            NOT NULL  DEFAULT 0         COMMENT '用户id',
  `role_id`                     bigint            NOT NULL  DEFAULT 0         COMMENT '角色id',
  PRIMARY KEY (id)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统用户-角色关联表' ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `sys_admin_user_post`;
CREATE TABLE `sys_admin_user_post` (
  `id`                          bigint            NOT NULL  AUTO_INCREMENT    COMMENT '主键',
  `admin_user_id`               bigint            NOT NULL  DEFAULT 0         COMMENT '用户id',
  `post_id`                     bigint            NOT NULL  DEFAULT 0         COMMENT '岗位id',
  PRIMARY KEY (id)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统用户-岗位关联表' ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id`                          bigint            NOT NULL  AUTO_INCREMENT    COMMENT '主键',
  `name`                        VARCHAR(16)       NOT NULL  DEFAULT ''        COMMENT '菜单名',
  `url`                         VARCHAR(100)                DEFAULT ''        COMMENT '菜单url',
  `description`                 VARCHAR(255)                DEFAULT ''        COMMENT '菜单描述',
  `parent_id`                   bigint            NOT NULL  DEFAULT 0         COMMENT '上级菜单id',
  `sort`                        int(11)           NOT NULL  DEFAULT 0         COMMENT '当前层级的排序',
  `child_number`                int(11)                     DEFAULT 0         COMMENT '子菜单数',
  `enabled`                     int(1)            NOT NULL  DEFAULT 1         COMMENT '启用状态(1:启用(默认);0:禁用;)',
  `created_user_id`             bigint            NOT NULL  DEFAULT 0         COMMENT '创建人id',
  `created_time`                datetime          DEFAULT CURRENT_TIMESTAMP   COMMENT '创建时间',
  `last_modified_user_id`       bigint            NOT NULL  DEFAULT 0         COMMENT '最后修改人id',
  `last_modified_time`          datetime          DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  PRIMARY KEY (id),
  UNIQUE KEY un_name (`name`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统菜单' ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id`                          bigint            NOT NULL  AUTO_INCREMENT    COMMENT '主键',
  `role_id`                     bigint            NOT NULL  DEFAULT 0         COMMENT '角色id',
  `menu_id`                     bigint            NOT NULL  DEFAULT 0         COMMENT '菜单id',
  PRIMARY KEY (id)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统角色-菜单关联表' ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `sys_function_permission`;
CREATE TABLE `sys_function_permission` (
  `id`                          bigint            NOT NULL  AUTO_INCREMENT    COMMENT '主键',
  `name`                        VARCHAR(16)       NOT NULL  DEFAULT ''        COMMENT '权限名',
  `code`                        VARCHAR(100)      NOT NULL  DEFAULT ''        COMMENT '权限代码',
  `description`                 VARCHAR(255)                DEFAULT ''        COMMENT '权限描述',
  `sys_menu_id`                 bigint            NOT NULL                    COMMENT '所属菜单id',
  `enabled`                     int(1)            NOT NULL  DEFAULT 1         COMMENT '启用状态(1:启用(默认);0:禁用;)',
  `created_user_id`             bigint            NOT NULL  DEFAULT 0         COMMENT '创建人id',
  `created_time`                datetime          DEFAULT CURRENT_TIMESTAMP   COMMENT '创建时间',
  `last_modified_user_id`       bigint            NOT NULL  DEFAULT 0         COMMENT '最后修改人id',
  `last_modified_time`          datetime          DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  PRIMARY KEY (id),
  UNIQUE KEY un_name (`name`),
  UNIQUE KEY un_code (`code`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统功能权限' ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `sys_role_function_permission`;
CREATE TABLE `sys_role_function_permission` (
  `id`                          bigint            NOT NULL  AUTO_INCREMENT    COMMENT '主键',
  `role_id`                     bigint            NOT NULL  DEFAULT 0         COMMENT '角色id',
  `function_permission_id`      bigint            NOT NULL  DEFAULT 0         COMMENT '功能权限id',
  PRIMARY KEY (id)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统角色-功能权限关联表' ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `sys_data_dict`;
CREATE TABLE `sys_data_dict` (
  `id`                          bigint            NOT NULL  AUTO_INCREMENT    COMMENT '主键',
  `name`                        VARCHAR(16)       NOT NULL  DEFAULT ''        COMMENT '字典名称',
  `code`                        VARCHAR(32)       NOT NULL  DEFAULT ''        COMMENT '字典编码',
  `description`                 VARCHAR(100)                DEFAULT ''        COMMENT '字典描述',
  `enabled`                     int(1)            NOT NULL  DEFAULT 1         COMMENT '启用状态(1:启用(默认);0:禁用;)',
  `created_user_id`             bigint            NOT NULL  DEFAULT 0         COMMENT '创建人id',
  `created_time`                datetime          DEFAULT CURRENT_TIMESTAMP   COMMENT '创建时间',
  `last_modified_user_id`       bigint            NOT NULL  DEFAULT 0         COMMENT '最后修改人id',
  `last_modified_time`          datetime          DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  PRIMARY KEY (id),
  UNIQUE KEY un_name (`name`),
  UNIQUE KEY un_code (`code`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '数据字典' ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `sys_data_dict_item`;
CREATE TABLE `sys_data_dict_item` (
  `id`                          bigint            NOT NULL  AUTO_INCREMENT    COMMENT '主键',
  `value`                       VARCHAR(16)       NOT NULL  DEFAULT ''        COMMENT '字典值',
  `text`                        VARCHAR(32)       NOT NULL  DEFAULT ''        COMMENT '字典文本',
  `sort`                        int(11)           NOT NULL  DEFAULT 0         COMMENT '排序',
  `sys_data_dict_id`            bigint            NOT NULL  DEFAULT 0         COMMENT '字典id',
  `enabled`                     int(1)            NOT NULL  DEFAULT 1         COMMENT '启用状态(1:启用(默认);0:禁用;)',
  `created_user_id`             bigint            NOT NULL  DEFAULT 0         COMMENT '创建人id',
  `created_time`                datetime          DEFAULT CURRENT_TIMESTAMP   COMMENT '创建时间',
  `last_modified_user_id`       bigint            NOT NULL  DEFAULT 0         COMMENT '最后修改人id',
  `last_modified_time`          datetime          DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  PRIMARY KEY (id),
  UNIQUE KEY `index_dict_id_value` (`sys_data_dict_id`, `value`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '数据字典详情' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS=1;