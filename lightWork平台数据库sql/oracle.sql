/*
Navicat Oracle Data Transfer
Oracle Client Version : 11.2.0.3.0

Source Server         : iwaterOrcl
Source Server Version : 110200
Source Host           : 127.0.0.1:1521
Source Schema         : IWATER

Target Server Type    : ORACLE
Target Server Version : 110200
File Encoding         : 65001

Date: 2017-03-16 11:06:25
*/


-- ----------------------------
-- Table structure for rel_role_power
-- ----------------------------
DROP TABLE "IWATER"."rel_role_power";
CREATE TABLE "IWATER"."rel_role_power" (
"role_code" NVARCHAR2(100) NULL ,
"power_code" NVARCHAR2(50) NULL ,
"power_owner" NVARCHAR2(50) NULL ,
"power_follower" NVARCHAR2(50) NULL ,
"power_type" NVARCHAR2(50) NULL ,
"del_modified" NVARCHAR2(50) NULL ,
"gmt_date" NVARCHAR2(50) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON COLUMN "IWATER"."rel_role_power"."role_code" IS '角色代码';
COMMENT ON COLUMN "IWATER"."rel_role_power"."power_code" IS '权限资源代码';
COMMENT ON COLUMN "IWATER"."rel_role_power"."power_owner" IS '所属菜单代码(类型是页面使用)';
COMMENT ON COLUMN "IWATER"."rel_role_power"."power_follower" IS '权限资源跟从(类型是页面使用)';
COMMENT ON COLUMN "IWATER"."rel_role_power"."power_type" IS '权限资源类型';
COMMENT ON COLUMN "IWATER"."rel_role_power"."del_modified" IS '删除标志位';
COMMENT ON COLUMN "IWATER"."rel_role_power"."gmt_date" IS '编辑时间';

-- ----------------------------
-- Records of rel_role_power
-- ----------------------------
INSERT INTO "IWATER"."rel_role_power" VALUES ('ROLE_FILL', 'ceshijiekou', null, null, '001', '1', '2017-03-03 17:10:47');
INSERT INTO "IWATER"."rel_role_power" VALUES ('ROLE_FILL', 'caidan', null, null, '002', '1', '2017-03-03 17:10:47');
INSERT INTO "IWATER"."rel_role_power" VALUES ('ROLE_FILL', 'roleManager', null, null, '002', '1', '2017-03-03 17:10:47');
INSERT INTO "IWATER"."rel_role_power" VALUES ('ROLE_FILL', 'quanxian', null, null, '002', '1', '2017-03-03 17:10:47');
INSERT INTO "IWATER"."rel_role_power" VALUES ('ROLE_FILL', 'xitong', null, null, '002', '1', '2017-03-03 17:10:47');
INSERT INTO "IWATER"."rel_role_power" VALUES ('ROLE_FILL', 'yonghu', null, null, '002', '1', '2017-03-03 17:10:48');
INSERT INTO "IWATER"."rel_role_power" VALUES ('ROLE_FILL', 'organManager', null, null, '002', '1', '2017-03-03 17:10:48');
INSERT INTO "IWATER"."rel_role_power" VALUES ('ROLE_LOOK', 'yonghu', null, null, '002', '1', '2017-03-06 09:26:03');
INSERT INTO "IWATER"."rel_role_power" VALUES ('ROLE_LOOK', 'addUser', 'yonghu', 'upload', '004', '1', '2017-03-06 09:26:03');
INSERT INTO "IWATER"."rel_role_power" VALUES ('ROLE_LOOK', 'userManager', 'yonghu', 'add,query,del', '004', '1', '2017-03-06 09:26:03');
INSERT INTO "IWATER"."rel_role_power" VALUES ('ROLE_OPERATION', 'quanxian1', null, null, '001', '1', '2017-03-06 09:28:23');
INSERT INTO "IWATER"."rel_role_power" VALUES ('ROLE_OPERATION', 'ceshijiekou', null, null, '001', '1', '2017-03-06 09:28:23');
INSERT INTO "IWATER"."rel_role_power" VALUES ('ROLE_OPERATION', 'queryItem', null, null, '002', '1', '2017-03-06 09:28:23');
INSERT INTO "IWATER"."rel_role_power" VALUES ('ROLE_OPERATION', 'baseparam', null, null, '002', '1', '2017-03-06 09:28:23');
INSERT INTO "IWATER"."rel_role_power" VALUES ('ROLE_OPERATION', 'db', null, null, '002', '1', '2017-03-06 09:28:23');
INSERT INTO "IWATER"."rel_role_power" VALUES ('ROLE_OPERATION', 'developManager', null, null, '002', '1', '2017-03-06 09:28:23');

-- ----------------------------
-- Table structure for rel_user_org
-- ----------------------------
DROP TABLE "IWATER"."rel_user_org";
CREATE TABLE "IWATER"."rel_user_org" (
"user_code" NVARCHAR2(100) NULL ,
"org_code" NVARCHAR2(50) NULL ,
"del_modified" NVARCHAR2(50) NULL ,
"gmt_date" NVARCHAR2(50) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON COLUMN "IWATER"."rel_user_org"."user_code" IS '账号';
COMMENT ON COLUMN "IWATER"."rel_user_org"."org_code" IS '组织机构代码';
COMMENT ON COLUMN "IWATER"."rel_user_org"."del_modified" IS '删除标志位';
COMMENT ON COLUMN "IWATER"."rel_user_org"."gmt_date" IS '编辑时间';

-- ----------------------------
-- Records of rel_user_org
-- ----------------------------
INSERT INTO "IWATER"."rel_user_org" VALUES ('zhouzhiruo', '4181DF8FE6AB47C8B1409A6E84378C96', '1', '2017-02-09 14:47:50');
INSERT INTO "IWATER"."rel_user_org" VALUES ('zhouzhiruo', '69D19966AB634461A1B01AABDED96A8C', '1', '2017-02-09 14:47:50');
INSERT INTO "IWATER"."rel_user_org" VALUES ('nba', '5C8443E9EBC141F5941DE026BA7A8F1B', '1', '2017-02-10 08:59:27');
INSERT INTO "IWATER"."rel_user_org" VALUES ('mingren', '5CD2F9A2E8374B25966B4E295F686A9B', '1', '2017-02-10 17:21:06');
INSERT INTO "IWATER"."rel_user_org" VALUES ('mingren', '6517E64369F2441F8B2D60BE6AF7B792', '1', '2017-02-10 17:21:06');
INSERT INTO "IWATER"."rel_user_org" VALUES ('linghuchong', '5C8443E9EBC141F5941DE026BA7A8F1B', '1', '2017-02-10 17:22:32');
INSERT INTO "IWATER"."rel_user_org" VALUES ('linghuchong', '2D393FF4787B428395A208ACB1D64BD2', '1', '2017-02-10 17:22:32');
INSERT INTO "IWATER"."rel_user_org" VALUES ('lishimin', '5C8443E9EBC141F5941DE026BA7A8F1B', '1', '2017-02-13 17:32:52');
INSERT INTO "IWATER"."rel_user_org" VALUES ('lishimin', '2D393FF4787B428395A208ACB1D64BD2', '1', '2017-02-13 17:32:52');
INSERT INTO "IWATER"."rel_user_org" VALUES ('wendasdong', 'b', '1', '2017-02-14 13:04:56');
INSERT INTO "IWATER"."rel_user_org" VALUES ('wendasdong', 'b2', '1', '2017-02-14 13:04:56');
INSERT INTO "IWATER"."rel_user_org" VALUES ('ouyang', 'c', '1', '2017-02-14 13:07:29');
INSERT INTO "IWATER"."rel_user_org" VALUES ('ouyang', 'c1', '1', '2017-02-14 13:07:29');
INSERT INTO "IWATER"."rel_user_org" VALUES ('user', 'ul', '1', '2017-02-16 16:06:12');
INSERT INTO "IWATER"."rel_user_org" VALUES ('user', 'b1', '1', '2017-02-16 16:06:12');
INSERT INTO "IWATER"."rel_user_org" VALUES ('user', 'b11', '1', '2017-02-16 16:06:12');
INSERT INTO "IWATER"."rel_user_org" VALUES ('user', 'aadasd2', '1', '2017-02-16 16:06:12');
INSERT INTO "IWATER"."rel_user_org" VALUES ('ym', 'a', '1', '2017-02-28 15:49:41');
INSERT INTO "IWATER"."rel_user_org" VALUES ('ym', 'a2', '1', '2017-02-28 15:49:41');
INSERT INTO "IWATER"."rel_user_org" VALUES ('ym', 'adasd2', '1', '2017-02-28 15:49:41');
INSERT INTO "IWATER"."rel_user_org" VALUES ('gaoh', 'a', '1', '2017-03-13 15:26:14');

-- ----------------------------
-- Table structure for rel_user_role
-- ----------------------------
DROP TABLE "IWATER"."rel_user_role";
CREATE TABLE "IWATER"."rel_user_role" (
"user_code" NVARCHAR2(255) NULL ,
"role_code" NVARCHAR2(255) NULL ,
"del_modified" NVARCHAR2(255) NULL ,
"gmt_date" NVARCHAR2(255) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON COLUMN "IWATER"."rel_user_role"."user_code" IS '账号';
COMMENT ON COLUMN "IWATER"."rel_user_role"."role_code" IS '角色代码';
COMMENT ON COLUMN "IWATER"."rel_user_role"."del_modified" IS '删除标志位';
COMMENT ON COLUMN "IWATER"."rel_user_role"."gmt_date" IS '编辑时间';

-- ----------------------------
-- Records of rel_user_role
-- ----------------------------
INSERT INTO "IWATER"."rel_user_role" VALUES ('admin', 'ROLE_ADMIN', '1', '2017-03-02 10:22:32');
INSERT INTO "IWATER"."rel_user_role" VALUES ('gaoh', 'ROLE_ADMIN', '1', '2017-03-13 15:26:14');

-- ----------------------------
-- Table structure for sys_cache
-- ----------------------------
DROP TABLE "IWATER"."sys_cache";
CREATE TABLE "IWATER"."sys_cache" (
"cache_id" NVARCHAR2(100) NOT NULL ,
"add_date" NVARCHAR2(100) NULL ,
"up_date" NVARCHAR2(100) NULL ,
"user_info" NVARCHAR2(100) NULL ,
"cache_menu" NVARCHAR2(100) NULL ,
"cache_menu_id" NVARCHAR2(100) NULL ,
"cache_package" NVARCHAR2(100) NULL ,
"cache_role" NVARCHAR2(100) NULL ,
"cache_idname" NVARCHAR2(100) NULL ,
"cache_type" NVARCHAR2(50) NULL ,
"cache_state" NVARCHAR2(50) NULL ,
"cache_rate" NVARCHAR2(100) NULL ,
"cache_refreshtime" NVARCHAR2(100) NULL ,
"cache_modified" NVARCHAR2(50) NULL ,
"rate_name" NVARCHAR2(100) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON COLUMN "IWATER"."sys_cache"."cache_id" IS '主键';
COMMENT ON COLUMN "IWATER"."sys_cache"."add_date" IS '添加时间';
COMMENT ON COLUMN "IWATER"."sys_cache"."up_date" IS '修改时间';
COMMENT ON COLUMN "IWATER"."sys_cache"."user_info" IS '操作者';
COMMENT ON COLUMN "IWATER"."sys_cache"."cache_menu" IS '菜单名称';
COMMENT ON COLUMN "IWATER"."sys_cache"."cache_menu_id" IS '菜单id';
COMMENT ON COLUMN "IWATER"."sys_cache"."cache_package" IS '包名称';
COMMENT ON COLUMN "IWATER"."sys_cache"."cache_role" IS '角色名';
COMMENT ON COLUMN "IWATER"."sys_cache"."cache_idname" IS '<select>id名称';
COMMENT ON COLUMN "IWATER"."sys_cache"."cache_type" IS '缓存类型1,刷新2，同步';
COMMENT ON COLUMN "IWATER"."sys_cache"."cache_state" IS '缓存状态0，不存在1，未启动2，正常运行';
COMMENT ON COLUMN "IWATER"."sys_cache"."cache_rate" IS '刷新频率（秒）';
COMMENT ON COLUMN "IWATER"."sys_cache"."cache_refreshtime" IS '最后刷新时间';
COMMENT ON COLUMN "IWATER"."sys_cache"."cache_modified" IS '是否删除，0删除1未删除';
COMMENT ON COLUMN "IWATER"."sys_cache"."rate_name" IS '刷新频率，中文表示';

-- ----------------------------
-- Records of sys_cache
-- ----------------------------
INSERT INTO "IWATER"."sys_cache" VALUES ('ad386af451f84136984da535c097bb4b', '2017-03-14 14:39:50', null, null, '测试', null, 'com.baosight.iwater.system.cacheManager.dao.CacheManagerMapper', 'rolename', 'selectByPrimaryKey', '1', '0', '0 0/1 * * * ?', null, '1', '分:0分钟后开始,间隔1分钟;');
INSERT INTO "IWATER"."sys_cache" VALUES ('da72a1a3e33a4d01a06f0c306348043d', '2017-02-07 16:43:26', '2017-02-09 10:11:31', null, '3234', null, 'com.baosight.iwater.system.cacheManager.dao.CacheManagerMapper', 'rolename', 'findAll', '1', '0', '0/10 * * * * ?', '2017-03-14 15:16:10', '1', '秒:0秒钟后开始,间隔10秒钟;');

-- ----------------------------
-- Table structure for sys_code_dic
-- ----------------------------
DROP TABLE "IWATER"."sys_code_dic";
CREATE TABLE "IWATER"."sys_code_dic" (
"dic_id" NVARCHAR2(40) NULL ,
"dic_code" NVARCHAR2(40) NULL ,
"dic_name" NVARCHAR2(255) NULL ,
"parent_code" NVARCHAR2(40) NULL ,
"dic_desc" NVARCHAR2(255) NULL ,
"create_date" NVARCHAR2(40) NULL ,
"update_date" NVARCHAR2(40) NULL ,
"is_usable" NVARCHAR2(20) NULL ,
"seq" NVARCHAR2(40) NULL ,
"create_name" NVARCHAR2(255) NULL ,
"update_name" NVARCHAR2(255) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON COLUMN "IWATER"."sys_code_dic"."dic_id" IS '字典ID';
COMMENT ON COLUMN "IWATER"."sys_code_dic"."dic_code" IS '字典编码';
COMMENT ON COLUMN "IWATER"."sys_code_dic"."dic_name" IS '字典名称';
COMMENT ON COLUMN "IWATER"."sys_code_dic"."parent_code" IS '父ID';
COMMENT ON COLUMN "IWATER"."sys_code_dic"."dic_desc" IS '字典说明';
COMMENT ON COLUMN "IWATER"."sys_code_dic"."create_date" IS '创建时间';
COMMENT ON COLUMN "IWATER"."sys_code_dic"."update_date" IS '修改时间';
COMMENT ON COLUMN "IWATER"."sys_code_dic"."is_usable" IS '是否可用';
COMMENT ON COLUMN "IWATER"."sys_code_dic"."seq" IS '排序号';
COMMENT ON COLUMN "IWATER"."sys_code_dic"."create_name" IS '填写人';
COMMENT ON COLUMN "IWATER"."sys_code_dic"."update_name" IS '修改人';

-- ----------------------------
-- Records of sys_code_dic
-- ----------------------------
INSERT INTO "IWATER"."sys_code_dic" VALUES ('root', 'root', '系统字典', '-1', null, null, null, '1', '1', null, null);
INSERT INTO "IWATER"."sys_code_dic" VALUES ('test2', 'test2', '测试2', 'root', null, null, null, '1', '2', null, null);
INSERT INTO "IWATER"."sys_code_dic" VALUES ('EF09AC07C7134613AC7FADFEF4AAE25F', 'test12', '测试12', 'test2', '测试节点', '2017-02-22 11:17:07', null, '1', null, 'admin', null);
INSERT INTO "IWATER"."sys_code_dic" VALUES ('280492A4A7904B58882DFF534D24E896', 'test13', 'tessssssss', 'test2', 'tesssss', '2017-02-24 11:13:46', null, '1', null, 'admin', null);
INSERT INTO "IWATER"."sys_code_dic" VALUES ('BCB48724892545108B4E8979019A4997', 'menu_sex', '性别', 'root', null, '2017-03-01 12:44:06', null, '1', null, 'admin', null);
INSERT INTO "IWATER"."sys_code_dic" VALUES ('0B543A0584F747998F616D476DD91319', '01', '男', 'menu_sex', null, '2017-03-01 12:44:55', null, '1', null, 'admin', null);
INSERT INTO "IWATER"."sys_code_dic" VALUES ('0E2EF9E28A7745EB974578E473E768E0', '02', '女', 'menu_sex', null, '2017-03-01 12:45:04', null, '1', null, 'admin', null);
INSERT INTO "IWATER"."sys_code_dic" VALUES ('358EBDF301514D0CAC38E5DAC6508A7C', 'role_type', '角色类型', 'root', '角色的类型', '2017-03-01 12:55:46', null, '1', null, 'admin', null);
INSERT INTO "IWATER"."sys_code_dic" VALUES ('3A35A84C891D4E2D8D0C9E8E6422A2E7', 'role_type_001', '浏览类', 'role_type', null, '2017-03-01 12:56:43', null, '1', null, 'admin', null);
INSERT INTO "IWATER"."sys_code_dic" VALUES ('80695CE2BE644291A19795AD8149EF86', 'role_type_002', '填报类', 'role_type', null, '2017-03-01 12:57:42', null, '1', null, 'admin', null);
INSERT INTO "IWATER"."sys_code_dic" VALUES ('2257EA7CB3B543D5901FE64F19D09AB6', 'role_type_003', '操作类', 'role_type', null, '2017-03-01 12:58:02', null, '1', null, 'admin', null);
INSERT INTO "IWATER"."sys_code_dic" VALUES ('FD28B11D29944EC1876FF583066BC952', 'role_type_004', '管理类', 'role_type', null, '2017-03-01 12:58:19', null, '1', null, 'admin', null);
INSERT INTO "IWATER"."sys_code_dic" VALUES ('2B1245C7BDB94BC2AA9FBC72575C5C98', 'power_type', '权限类型', 'root', null, '2017-03-01 12:59:55', null, '1', null, 'admin', null);
INSERT INTO "IWATER"."sys_code_dic" VALUES ('097D0F502799455FA6DD2E968C78C879', '001', '接口', 'power_type', null, '2017-03-01 13:00:33', null, '1', null, 'admin', null);
INSERT INTO "IWATER"."sys_code_dic" VALUES ('E535FA2F42A9493680A1F2231DF0C3DD', '002', '菜单', 'power_type', null, '2017-03-01 13:01:01', null, '1', null, 'admin', null);
INSERT INTO "IWATER"."sys_code_dic" VALUES ('28A7DC14979F4F3C890E3D72B286288E', '004', '页面', 'power_type', null, '2017-03-01 13:01:22', null, '1', null, 'admin', null);
INSERT INTO "IWATER"."sys_code_dic" VALUES ('45B2D31C140049FB845CD9AB034ABB8D', 'user_post', '用户职务', 'root', null, '2017-03-01 13:02:51', null, '1', null, 'admin', null);
INSERT INTO "IWATER"."sys_code_dic" VALUES ('A0BBC2B09C5C4A838EAC328AF0ECF14E', 'user_post_001', '总经理', 'user_post', null, '2017-03-01 13:03:19', null, '1', null, 'admin', null);
INSERT INTO "IWATER"."sys_code_dic" VALUES ('1CD1A09E7157462EB62A0AAB3305EF91', 'user_post_002', '经理', 'user_post', null, '2017-03-01 13:03:36', null, '1', null, 'admin', null);
INSERT INTO "IWATER"."sys_code_dic" VALUES ('DA8B6E2931DD4249B2240EC823030AB1', 'user_post_004', '其他', 'user_post', null, '2017-03-01 13:05:24', null, '1', null, 'admin', null);
INSERT INTO "IWATER"."sys_code_dic" VALUES ('E3079B276AEB401A8BFD8E5268D2EBBB', 'btn_type', '按钮类型', 'root', null, '2017-03-01 13:15:14', null, '1', '1', 'admin', null);
INSERT INTO "IWATER"."sys_code_dic" VALUES ('7C2CF270F69249829C8C1A0BEEC8C7D7', 'add', '新增/注册', 'btn_type', null, '2017-03-01 13:15:37', null, '1', null, 'admin', null);
INSERT INTO "IWATER"."sys_code_dic" VALUES ('A6CC5B66280D452A9A36DA96BBCF4C6B', 'update', '修改', 'btn_type', null, '2017-03-01 13:15:57', null, '1', null, 'admin', null);
INSERT INTO "IWATER"."sys_code_dic" VALUES ('9BBB2428B60D4366B696E5758F769DC7', 'query', '查询/搜索', 'btn_type', null, '2017-03-01 13:16:30', null, '1', null, 'admin', null);
INSERT INTO "IWATER"."sys_code_dic" VALUES ('A89C5A40CDD24BEA9095A426322D7E53', 'upload', '上传', 'btn_type', null, '2017-03-01 13:16:50', null, '1', null, 'admin', null);
INSERT INTO "IWATER"."sys_code_dic" VALUES ('0CDA3D8A60814D3BA696655A3E0ABF28', 'download', '下载', 'btn_type', null, '2017-03-01 13:17:07', null, '1', null, 'admin', null);
INSERT INTO "IWATER"."sys_code_dic" VALUES ('E958615E22724805A690927C795B1090', 'del', '删除', 'btn_type', null, '2017-03-01 13:17:19', null, '1', null, 'admin', null);

-- ----------------------------
-- Table structure for sys_db
-- ----------------------------
DROP TABLE "IWATER"."sys_db";
CREATE TABLE "IWATER"."sys_db" (
"ui_id" NVARCHAR2(100) NOT NULL ,
"add_date" NVARCHAR2(50) NULL ,
"up_date" NVARCHAR2(50) NULL ,
"user_info" NVARCHAR2(50) NULL ,
"db_source" NVARCHAR2(50) NULL ,
"db_ip" NVARCHAR2(50) NULL ,
"db_port" NVARCHAR2(50) NULL ,
"db_maxlink" NVARCHAR2(50) NULL ,
"db_waittime" NVARCHAR2(50) NULL ,
"db_username" NVARCHAR2(50) NULL ,
"db_pwd" NVARCHAR2(50) NULL ,
"is_del" NVARCHAR2(50) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON COLUMN "IWATER"."sys_db"."ui_id" IS '主键';
COMMENT ON COLUMN "IWATER"."sys_db"."add_date" IS '添加时间';
COMMENT ON COLUMN "IWATER"."sys_db"."up_date" IS '修改时间';
COMMENT ON COLUMN "IWATER"."sys_db"."user_info" IS '操作者';
COMMENT ON COLUMN "IWATER"."sys_db"."db_source" IS '数据源';
COMMENT ON COLUMN "IWATER"."sys_db"."db_ip" IS 'ip';
COMMENT ON COLUMN "IWATER"."sys_db"."db_port" IS '端口';
COMMENT ON COLUMN "IWATER"."sys_db"."db_maxlink" IS '最大连接数';
COMMENT ON COLUMN "IWATER"."sys_db"."db_waittime" IS '等待时间';
COMMENT ON COLUMN "IWATER"."sys_db"."db_username" IS '账号';
COMMENT ON COLUMN "IWATER"."sys_db"."db_pwd" IS '密码';
COMMENT ON COLUMN "IWATER"."sys_db"."is_del" IS '删除标志位';

-- ----------------------------
-- Records of sys_db
-- ----------------------------

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE "IWATER"."sys_log";
CREATE TABLE "IWATER"."sys_log" (
"ui_id" NVARCHAR2(100) NOT NULL ,
"add_date" NVARCHAR2(50) NULL ,
"up_date" NVARCHAR2(50) NULL ,
"log_desc" NVARCHAR2(200) NULL ,
"log_method" NVARCHAR2(200) NULL ,
"log_user" NVARCHAR2(50) NULL ,
"log_type" NVARCHAR2(255) NULL ,
"log_IP" NVARCHAR2(200) NULL ,
"log_excode" NVARCHAR2(200) NULL ,
"log_exdesc" NVARCHAR2(200) NULL ,
"log_params" NVARCHAR2(200) NULL ,
"log_date" NVARCHAR2(50) NULL ,
"del_modified" NVARCHAR2(50) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON COLUMN "IWATER"."sys_log"."ui_id" IS '主键';
COMMENT ON COLUMN "IWATER"."sys_log"."add_date" IS '添加时间';
COMMENT ON COLUMN "IWATER"."sys_log"."up_date" IS '修改时间';
COMMENT ON COLUMN "IWATER"."sys_log"."log_desc" IS '用途';
COMMENT ON COLUMN "IWATER"."sys_log"."log_method" IS '方法';
COMMENT ON COLUMN "IWATER"."sys_log"."log_type" IS '0表示controll，1表示service';
COMMENT ON COLUMN "IWATER"."sys_log"."log_IP" IS '请求IP';
COMMENT ON COLUMN "IWATER"."sys_log"."log_excode" IS '异常代码';
COMMENT ON COLUMN "IWATER"."sys_log"."log_exdesc" IS '异常描述';
COMMENT ON COLUMN "IWATER"."sys_log"."log_params" IS '请求参数';
COMMENT ON COLUMN "IWATER"."sys_log"."log_date" IS '操作时间';
COMMENT ON COLUMN "IWATER"."sys_log"."del_modified" IS '删除标志位';

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO "IWATER"."sys_log" VALUES ('026568F268E8474BB51599F6DD592418', '2017-03-16 10:57:43', null, '权限资源分页条件查询', 'com.baosight.iwater.system.power.controller.SystemPowerController.list()', 'admin', '0', '127.0.0.1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('0C1A750B38B84C94925CEB59E7F677E8', '2017-03-16 10:51:40', null, '菜单分页条件查询', 'com.baosight.iwater.system.menu.controller.SystemMenuController.list()', 'admin', '0', '0:0:0:0:0:0:0:1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('0F80944075654724938454774F9B322D', '2017-03-16 10:53:21', null, '删除权限资源', 'com.baosight.iwater.system.power.controller.SystemPowerController.del()', 'admin', '0', '127.0.0.1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('0FF4BE1FC4374229870833B9203604B1', '2017-03-16 10:53:24', null, '权限资源分页条件查询', 'com.baosight.iwater.system.power.controller.SystemPowerController.list()', 'admin', '0', '127.0.0.1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('18ACC8C5853445BEA2100ABB708FA02E', '2017-03-16 10:57:45', null, '查询单个权限资源', 'com.baosight.iwater.system.power.controller.SystemPowerController.get()', 'admin', '0', '127.0.0.1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('20B79F89B3F346E293E9950BAE4C337E', '2017-03-16 10:53:13', null, '权限资源分页条件查询', 'com.baosight.iwater.system.power.controller.SystemPowerController.list()', 'admin', '0', '127.0.0.1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('29421B9B33164038AAE6E9C8B2327BEF', '2017-03-16 10:52:44', null, '得到用户菜单', 'com.baosight.iwater.system.login.controller.SystemLoginController.getMenu()', 'admin', '0', '127.0.0.1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('2B78485D245D458F91114D0C625DAE4B', '2017-03-16 10:57:00', null, '用户分页条件查询', 'com.baosight.iwater.system.user.controller.SystemUserController.list()', 'admin', '0', '127.0.0.1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('2C606C9BED6540FF9EAC27BD92BC1B6F', '2017-03-16 11:01:10', null, '生成json', 'com.baosight.iwater.system.queryManager.controller.SystemQueryManagerController.insert()', 'admin', '0', '0:0:0:0:0:0:0:1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('2F5A6B779E064B3DBB6D6799D996F85E', '2017-03-16 10:53:24', null, '删除权限资源', 'com.baosight.iwater.system.power.controller.SystemPowerController.del()', 'admin', '0', '127.0.0.1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('387F2BEF9086475292C26C53A28290FD', '2017-03-16 10:53:05', null, '查询单个权限资源', 'com.baosight.iwater.system.power.controller.SystemPowerController.get()', 'admin', '0', '127.0.0.1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('43147D9078D049A18DB29EDAEA983937', '2017-03-16 10:53:22', null, '权限资源分页条件查询', 'com.baosight.iwater.system.power.controller.SystemPowerController.list()', 'admin', '0', '127.0.0.1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('43A082176B524A55BE4AAF4FA3D8AB14', '2017-03-16 10:49:03', null, '得到用户菜单', 'com.baosight.iwater.system.login.controller.SystemLoginController.getMenu()', 'admin', '0', '127.0.0.1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('443737E4176E4EC7B76E372BFF2DEDD6', '2017-03-16 10:43:10', null, '生成json', 'com.baosight.iwater.system.queryManager.controller.SystemQueryManagerController.insert()', 'admin', '0', '0:0:0:0:0:0:0:1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('4B4F5B2516374AC8B5E729B65E64A350', '2017-03-16 10:53:31', null, 'properties文件中项目前缀', 'com.baosight.iwater.system.menu.controller.SystemMenuController.menuItem()', 'admin', '0', '127.0.0.1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('4CC264A061B54DBEBFCCA39547C2862E', '2017-03-16 10:52:58', null, '权限资源分页条件查询', 'com.baosight.iwater.system.power.controller.SystemPowerController.list()', 'admin', '0', '127.0.0.1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('4F09C7C30B4D46509773607DE5D34781', '2017-03-16 10:53:28', null, '删除权限资源', 'com.baosight.iwater.system.power.controller.SystemPowerController.del()', 'admin', '0', '127.0.0.1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('5BFD00CC34E74C548BA8B59C4F01577F', '2017-03-16 10:53:03', null, '权限资源分页条件查询', 'com.baosight.iwater.system.power.controller.SystemPowerController.list()', 'admin', '0', '127.0.0.1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('5C0031AF3A714B8B96C406900B1A6E1D', '2017-03-16 10:53:11', null, '删除权限资源', 'com.baosight.iwater.system.power.controller.SystemPowerController.del()', 'admin', '0', '127.0.0.1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('61C3B0CF88474C788C1568D55622AD6A', '2017-03-16 10:53:28', null, '权限资源分页条件查询', 'com.baosight.iwater.system.power.controller.SystemPowerController.list()', 'admin', '0', '127.0.0.1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('6B3C01145A26426EAA6BFB310F1D6DCB', '2017-03-16 10:49:19', null, '生成json', 'com.baosight.iwater.system.queryManager.controller.SystemQueryManagerController.insert()', 'admin', '0', '0:0:0:0:0:0:0:1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('6E4FE1B23B39410CB0C3556F602CD1A2', '2017-03-16 10:57:24', null, '查询所有', 'com.baosight.iwater.system.role.controller.SystemRoleController.allList()', 'admin', '0', '127.0.0.1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('7503E6E0436D4A8DB1AAD736D5F35D11', '2017-03-16 10:49:03', null, '用户分页条件查询', 'com.baosight.iwater.system.user.controller.SystemUserController.list()', 'admin', '0', '127.0.0.1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('78EA17C6E5744D50A819170A6E0AD55A', '2017-03-16 10:58:00', null, '权限资源全部查询', 'com.baosight.iwater.system.power.controller.SystemPowerController.allList()', 'admin', '0', '127.0.0.1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('7E09C10A1DBA4823A933BA680D88970F', '2017-03-16 10:18:28', null, '生成json', 'com.baosight.iwater.system.queryManager.controller.SystemQueryManagerController.insert()', 'admin', '0', '0:0:0:0:0:0:0:1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('837272A4E89348BB994C7DB43DB4E63D', '2017-03-16 10:57:58', null, '分页条件查询', 'com.baosight.iwater.system.role.controller.SystemRoleController.list()', 'admin', '0', '127.0.0.1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('90461DCB5DD94722A3C3F74FA9130F97', '2017-03-16 10:53:10', null, '删除权限资源', 'com.baosight.iwater.system.power.controller.SystemPowerController.del()', 'admin', '0', '127.0.0.1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('90620CB20A9443C98DB7982C32A3F23F', '2017-03-16 10:53:18', null, '删除权限资源', 'com.baosight.iwater.system.power.controller.SystemPowerController.del()', 'admin', '0', '127.0.0.1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('908A1DF85985463DBAA749819FB337D3', '2017-03-16 10:57:31', null, '查询所有组织机构', 'com.baosight.iwater.system.organization.controller.SystemOrganizationController.selectAll()', 'admin', '0', '127.0.0.1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('9A0C8DA2AB0245C88F12116197222CE9', '2017-03-16 10:57:03', null, '查询单个用户', 'com.baosight.iwater.system.user.controller.SystemUserController.get()', 'admin', '0', '127.0.0.1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('9C9D00332D114CCFA6B2D645EBAE84D2', '2017-03-16 10:53:16', null, '权限资源分页条件查询', 'com.baosight.iwater.system.power.controller.SystemPowerController.list()', 'admin', '0', '127.0.0.1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('9DD0CBD8E6EE4091BA82F79B9B74CAB5', '2017-03-16 10:53:16', null, '删除权限资源', 'com.baosight.iwater.system.power.controller.SystemPowerController.del()', 'admin', '0', '127.0.0.1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('9ECC58245B674E2098912796B35B2282', '2017-03-16 10:48:26', null, '用户分页条件查询', 'com.baosight.iwater.system.user.controller.SystemUserController.list()', 'admin', '0', '127.0.0.1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('9FDD5086B78441A4AEB71710961DC2F9', '2017-03-16 10:47:38', null, '生成json', 'com.baosight.iwater.system.queryManager.controller.SystemQueryManagerController.insert()', 'admin', '0', '0:0:0:0:0:0:0:1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('A3ADE41D4F474C6E90F4FFA6C8AB36F8', '2017-03-16 10:53:31', null, '查询单个权限资源', 'com.baosight.iwater.system.power.controller.SystemPowerController.get()', 'admin', '0', '127.0.0.1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('A69B658B6FBE46EAA4A2F4AD06619C7F', '2017-03-16 10:58:00', null, '查询单个角色', 'com.baosight.iwater.system.role.controller.SystemRoleController.get()', 'admin', '0', '127.0.0.1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('A76ABDB3FB0B412EACAE2A86BE3FF61B', '2017-03-16 10:53:26', null, '删除权限资源', 'com.baosight.iwater.system.power.controller.SystemPowerController.del()', 'admin', '0', '127.0.0.1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('B38E032A3D9B4BB3B6E10B334001B79D', '2017-03-16 10:53:12', null, '权限资源分页条件查询', 'com.baosight.iwater.system.power.controller.SystemPowerController.list()', 'admin', '0', '127.0.0.1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('B6D80E61FB9F4E77A8732050213F4E7B', '2017-03-16 10:53:13', null, '删除权限资源', 'com.baosight.iwater.system.power.controller.SystemPowerController.del()', 'admin', '0', '127.0.0.1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('BDCF72E1BA7846DD9B26577FF35A172B', '2017-03-16 10:18:00', null, '得到用户菜单', 'com.baosight.iwater.system.login.controller.SystemLoginController.getMenu()', 'admin', '0', '0:0:0:0:0:0:0:1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('C2C41EDFF2A44A5F99D71A667BF52C71', '2017-03-16 10:51:35', null, '得到用户菜单', 'com.baosight.iwater.system.login.controller.SystemLoginController.getMenu()', 'admin', '0', '0:0:0:0:0:0:0:1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('C4DEDC55DCAB4D4785307630594AFF5A', '2017-03-16 10:57:48', null, 'properties文件中项目前缀', 'com.baosight.iwater.system.menu.controller.SystemMenuController.menuItem()', 'admin', '0', '127.0.0.1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('C7D58A97733C4C6FB6BA56665BC13865', '2017-03-16 10:53:26', null, '权限资源分页条件查询', 'com.baosight.iwater.system.power.controller.SystemPowerController.list()', 'admin', '0', '127.0.0.1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('D1E9ED1765154CB783A9945467B6D092', '2017-03-16 11:00:50', null, '得到用户菜单', 'com.baosight.iwater.system.login.controller.SystemLoginController.getMenu()', 'admin', '0', '0:0:0:0:0:0:0:1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('D217DDBB16564E63A74A27EA9A96567A', '2017-03-16 10:48:13', null, '得到用户菜单', 'com.baosight.iwater.system.login.controller.SystemLoginController.getMenu()', 'admin', '0', '127.0.0.1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('D679705E9D75434094134B08A6E687D9', '2017-03-16 10:57:48', null, '查询单个权限资源', 'com.baosight.iwater.system.power.controller.SystemPowerController.get()', 'admin', '0', '127.0.0.1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('DE4C293CD11C4577BA09FFE7AFF79F73', '2017-03-16 10:55:13', null, '权限资源分页条件查询', 'com.baosight.iwater.system.power.controller.SystemPowerController.list()', 'admin', '0', '127.0.0.1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('E13E641D0FA34D7DB38A8DE1FE3C62C4', '2017-03-16 10:42:58', null, '得到用户菜单', 'com.baosight.iwater.system.login.controller.SystemLoginController.getMenu()', 'admin', '0', '0:0:0:0:0:0:0:1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('E23F65E8BD1C411FB11B036C73320064', '2017-03-16 10:53:19', null, '权限资源分页条件查询', 'com.baosight.iwater.system.power.controller.SystemPowerController.list()', 'admin', '0', '127.0.0.1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('E421D82A451E449E93261F6E71745322', '2017-03-16 10:57:45', null, 'properties文件中项目前缀', 'com.baosight.iwater.system.menu.controller.SystemMenuController.menuItem()', 'admin', '0', '127.0.0.1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('EB2E5F29D2A84E609AB7D9E642033E14', '2017-03-16 10:44:12', null, '生成json', 'com.baosight.iwater.system.queryManager.controller.SystemQueryManagerController.insert()', 'admin', '0', '0:0:0:0:0:0:0:1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('F16228B73C9741DD93092E50586A43BB', '2017-03-16 10:58:02', null, '权限资源全部查询', 'com.baosight.iwater.system.power.controller.SystemPowerController.allList()', 'admin', '0', '127.0.0.1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('F488300526C94870B163A664D5D0A1F0', '2017-03-16 10:53:05', null, 'properties文件中项目前缀', 'com.baosight.iwater.system.menu.controller.SystemMenuController.menuItem()', 'admin', '0', '127.0.0.1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('F5404D36DA404B759455F7930BBEA6FA', '2017-03-16 10:47:27', null, '得到用户菜单', 'com.baosight.iwater.system.login.controller.SystemLoginController.getMenu()', 'admin', '0', '0:0:0:0:0:0:0:1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('F63081CF4E5D4B44B554491455608FA2', '2017-03-16 10:53:10', null, '权限资源分页条件查询', 'com.baosight.iwater.system.power.controller.SystemPowerController.list()', 'admin', '0', '127.0.0.1', null, null, null, null, '1');
INSERT INTO "IWATER"."sys_log" VALUES ('FE9E46699D80405C96068BCDA414D9E4', '2017-03-16 10:57:22', null, '查询单个用户', 'com.baosight.iwater.system.user.controller.SystemUserController.get()', 'admin', '0', '127.0.0.1', null, null, null, null, '1');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE "IWATER"."sys_menu";
CREATE TABLE "IWATER"."sys_menu" (
"ui_id" NVARCHAR2(100) NOT NULL ,
"add_date" NVARCHAR2(50) NULL ,
"up_date" NVARCHAR2(50) NULL ,
"user_info" NVARCHAR2(50) NULL ,
"menu_zhname" NVARCHAR2(50) NULL ,
"menu_enname" NVARCHAR2(50) NULL ,
"menu_code" NVARCHAR2(50) NULL ,
"item_prefix" NVARCHAR2(100) NULL ,
"parent_menu" NVARCHAR2(100) NULL ,
"menu_url" NVARCHAR2(200) NULL ,
"menu_sort" NVARCHAR2(50) NULL ,
"menu_class" NVARCHAR2(50) NULL ,
"pic_class" NVARCHAR2(50) NULL ,
"is_del" NVARCHAR2(50) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON COLUMN "IWATER"."sys_menu"."ui_id" IS '主键';
COMMENT ON COLUMN "IWATER"."sys_menu"."add_date" IS '添加时间';
COMMENT ON COLUMN "IWATER"."sys_menu"."up_date" IS '修改时间';
COMMENT ON COLUMN "IWATER"."sys_menu"."user_info" IS '操作者';
COMMENT ON COLUMN "IWATER"."sys_menu"."menu_zhname" IS '菜单名称(中)';
COMMENT ON COLUMN "IWATER"."sys_menu"."menu_enname" IS '菜单名称(英)';
COMMENT ON COLUMN "IWATER"."sys_menu"."menu_code" IS '菜单编号';
COMMENT ON COLUMN "IWATER"."sys_menu"."item_prefix" IS '所属项目';
COMMENT ON COLUMN "IWATER"."sys_menu"."parent_menu" IS '父级菜单';
COMMENT ON COLUMN "IWATER"."sys_menu"."menu_url" IS '菜单访问路径';
COMMENT ON COLUMN "IWATER"."sys_menu"."menu_sort" IS '菜单排序';
COMMENT ON COLUMN "IWATER"."sys_menu"."menu_class" IS '菜单样式';
COMMENT ON COLUMN "IWATER"."sys_menu"."pic_class" IS '菜单图标样式';
COMMENT ON COLUMN "IWATER"."sys_menu"."is_del" IS '删除标志位';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO "IWATER"."sys_menu" VALUES ('0876545D979B409F9F21CA89A3A93555', '2017-02-28 13:10:15', '2017-03-02 13:44:53', 'admin', '缓存管理', 'cache managerment', 'cacheManager', 'iwater', 'C155B9F07E5843B6B9A5A09E0FF9FAD7', '#!/cache/cacheManager/', '13', 'icon_org', 'icon_org', '1');
INSERT INTO "IWATER"."sys_menu" VALUES ('2B70B395E6024A3C8C7EC4F666F7D70A', '2017-02-28 13:06:07', '2017-03-02 13:44:48', 'admin', '日志管理', 'Log managerment', 'logManager', 'iwater', 'C155B9F07E5843B6B9A5A09E0FF9FAD7', '#!/log/logManager/', '12', 'icon_org', 'icon_org', '1');
INSERT INTO "IWATER"."sys_menu" VALUES ('38A2333243F346C0A933333A13752787', '2017-02-17 10:02:18', '2017-03-02 13:44:36', 'admin', '菜单管理', 'caidan', 'caidan', 'iwater', 'C0037B0DB70A49C585A9EA00B666D51A', '#!/menu/menuManager/', '7', 'icon_org', 'icon_org', '1');
INSERT INTO "IWATER"."sys_menu" VALUES ('7849DFFCF2E247DC8C293BAC989A0AF5', '2017-02-28 13:11:23', '2017-03-02 13:44:23', 'admin', '组织机构管理', 'organization managerment', 'organManager', 'iwater', 'C0037B0DB70A49C585A9EA00B666D51A', '#!/org/orgManager', '4', 'icon_org', 'icon_org', '1');
INSERT INTO "IWATER"."sys_menu" VALUES ('8B1965D60E3743FEA0CB90E62CDC3FC2', '2017-02-28 13:03:25', '2017-03-02 13:44:09', 'admin', '查询条件配置', 'Query configuration', 'queryItem', 'iwater', 'C155B9F07E5843B6B9A5A09E0FF9FAD7', '#!/query/queryManager/', '3', 'icon_org', 'icon_org', '1');
INSERT INTO "IWATER"."sys_menu" VALUES ('C0037B0DB70A49C585A9EA00B666D51A', '2017-02-17 10:01:34', '2017-03-02 13:43:05', 'admin', '系统管理', 'xitongguanli', 'xitong', 'iwater', '0', 'useless', '1', 'bga', 'icon_grounp icon_grounpab', '1');
INSERT INTO "IWATER"."sys_menu" VALUES ('C155B9F07E5843B6B9A5A09E0FF9FAD7', '2017-02-28 13:07:36', '2017-03-02 13:43:47', 'admin', '开发管理', 'Development management', 'developManager', 'iwater', '0', 'useless', '2', 'bga', 'icon_grounp icon_grounpbb', '1');
INSERT INTO "IWATER"."sys_menu" VALUES ('C790BF782B2E47F8A92E767D36D5E083', '2017-02-17 10:04:22', '2017-03-02 13:44:43', 'admin', '字典管理', 'zidianguanli', 'zidian', 'iwater', 'C0037B0DB70A49C585A9EA00B666D51A', '#!/dic/dicManager/', '7', 'icon_org', 'icon_org', '1');
INSERT INTO "IWATER"."sys_menu" VALUES ('CBD2F4A9711E4C82998B0C4A28837810', '2017-03-01 15:20:28', '2017-03-02 13:43:42', 'admin', '数据库配置', 'Database configuration', 'db', 'iwater', 'C155B9F07E5843B6B9A5A09E0FF9FAD7', '#!/db/dbManager/', '1', 'icon_org', 'icon_org', '1');
INSERT INTO "IWATER"."sys_menu" VALUES ('DF969EDD8BDD4B7C9E53F1E15DC9E0B7', '2017-02-28 13:02:19', '2017-03-02 13:43:53', 'admin', '基础参数配置', 'Basic parameter configuration management', 'baseparam', 'iwater', 'C155B9F07E5843B6B9A5A09E0FF9FAD7', '#!/bs/bsManager/', '2', 'icon_org', 'icon_org', '1');
INSERT INTO "IWATER"."sys_menu" VALUES ('E50D09C0CF484222A6E5160F75106167', '2017-02-17 10:02:48', '2017-03-02 13:44:02', 'admin', '用户管理', 'yonghu', 'yonghu', 'iwater', 'C0037B0DB70A49C585A9EA00B666D51A', '#!/user/userManager/', '2', 'icon_org', 'icon_org', '1');
INSERT INTO "IWATER"."sys_menu" VALUES ('E79D7382A39D47A38CE1A1B709AEB8C2', '2017-02-28 13:04:48', '2017-03-02 13:44:15', 'admin', '角色管理', 'Role management', 'roleManager', 'iwater', 'C0037B0DB70A49C585A9EA00B666D51A', '#!/role/roleManager', '3', 'icon_org', 'icon_org', '1');
INSERT INTO "IWATER"."sys_menu" VALUES ('EBD9BE1EB20144A99397D2A0868E7F36', '2017-02-17 10:03:47', '2017-03-02 13:44:29', 'admin', '权限资源管理', 'quanxianziyuan', 'quanxian', 'iwater', 'C0037B0DB70A49C585A9EA00B666D51A', '#!/power/powerManager', '6', 'icon_org', 'icon_org', '1');

-- ----------------------------
-- Table structure for sys_org
-- ----------------------------
DROP TABLE "IWATER"."sys_org";
CREATE TABLE "IWATER"."sys_org" (
"ui_id" NVARCHAR2(100) NOT NULL ,
"add_date" NVARCHAR2(50) NULL ,
"up_date" NVARCHAR2(50) NULL ,
"user_info" NVARCHAR2(50) NULL ,
"gmt_modified" NVARCHAR2(50) NULL ,
"org_zhname" NVARCHAR2(50) NULL ,
"org_enname" NVARCHAR2(50) NULL ,
"org_url" NVARCHAR2(50) NULL ,
"org_parent" NVARCHAR2(50) NULL ,
"org_code" NVARCHAR2(50) NULL ,
"org_shortname" NVARCHAR2(50) NULL ,
"org_sort" NVARCHAR2(50) NULL ,
"del_modified" NVARCHAR2(50) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON COLUMN "IWATER"."sys_org"."ui_id" IS '主键';
COMMENT ON COLUMN "IWATER"."sys_org"."add_date" IS '添加时间';
COMMENT ON COLUMN "IWATER"."sys_org"."up_date" IS '修改时间';
COMMENT ON COLUMN "IWATER"."sys_org"."user_info" IS '操作者';
COMMENT ON COLUMN "IWATER"."sys_org"."gmt_modified" IS '是否同步';
COMMENT ON COLUMN "IWATER"."sys_org"."org_zhname" IS '机构名称(中)';
COMMENT ON COLUMN "IWATER"."sys_org"."org_enname" IS '机构名称(英)';
COMMENT ON COLUMN "IWATER"."sys_org"."org_url" IS '机构访问地址';
COMMENT ON COLUMN "IWATER"."sys_org"."org_parent" IS '机构父节点';
COMMENT ON COLUMN "IWATER"."sys_org"."org_code" IS '机构父节点';
COMMENT ON COLUMN "IWATER"."sys_org"."org_shortname" IS '机构缩写';
COMMENT ON COLUMN "IWATER"."sys_org"."org_sort" IS '机构排序';
COMMENT ON COLUMN "IWATER"."sys_org"."del_modified" IS '删除标志位';

-- ----------------------------
-- Records of sys_org
-- ----------------------------
INSERT INTO "IWATER"."sys_org" VALUES ('032C716611324A6194F98EE01C6606B0', null, null, null, null, '一级部门a', 'unknown', 'www.baidu.com', null, 'a', '文化部', '1', '1');
INSERT INTO "IWATER"."sys_org" VALUES ('0D66EC88393A4F7FA9F65A609BD8E2FE', null, null, null, null, '一级部门b', null, null, null, null, null, '2', '1');
INSERT INTO "IWATER"."sys_org" VALUES ('0D66EC8839bhcjsbhhd9F65A609BD8E2F', null, null, null, null, '一级部门d', 'unkown', '13054545445', null, 'b', '维稳部', '4', '1');
INSERT INTO "IWATER"."sys_org" VALUES ('2595FEDA0DF748B7A3A4176B400F2538', null, null, null, null, '一级部门c', 'unknown', 'www.baidu.com', null, 'c', '交通运输部', '3', '1');
INSERT INTO "IWATER"."sys_org" VALUES ('2D393FF4787B428395A208ACB1D64BD2', null, null, null, null, '二级部门a2', 'unknow', '13659595959', '032C716611324A6194F98EE01C6606B0', 'a1', '总参一部', '2', '1');
INSERT INTO "IWATER"."sys_org" VALUES ('4181DF8FE6AB47C8B1409A6E84378C96', null, null, null, null, '二级部门a1', 'unknown', null, '032C716611324A6194F98EE01C6606B0', 'a2', null, '1', '1');
INSERT INTO "IWATER"."sys_org" VALUES ('43557EE3C0D849C1990331C7145B4884', null, '2017-02-14 10:46:33', null, null, '二级部门b1', 'unknown', 'www.baidu.com', '0D66EC88393A4F7FA9F65A609BD8E2FE', 'b1', '中宣部g', '1', '1');
INSERT INTO "IWATER"."sys_org" VALUES ('47646B852A3943F7805732BBD885630C', null, null, null, null, '二级部门b2', 'unknown', 'www.baidu.com', '0D66EC88393A4F7FA9F65A609BD8E2FE', 'b2', '司法部', '2', '1');
INSERT INTO "IWATER"."sys_org" VALUES ('5C8443E9EBC141F5941DE026BA7A8F1B', null, null, null, null, '二级部门c1', 'unknown', null, '2595FEDA0DF748B7A3A4176B400F2538', 'c1', null, '1', '1');
INSERT INTO "IWATER"."sys_org" VALUES ('5CD2F9A2E8374B25966B4E295F686A9B', null, null, null, null, '二级部门c2', 'unknown', null, '2595FEDA0DF748B7A3A4176B400F2538', 'c2', null, '2', '1');
INSERT INTO "IWATER"."sys_org" VALUES ('6517E64369F2441F8B2D60BE6AF7B792', null, null, null, null, '二级部门c3', 'unknown', 'www.baidu.com', '2595FEDA0DF748B7A3A4176B400F2538', 'c3', 'd1545', '3', '1');
INSERT INTO "IWATER"."sys_org" VALUES ('69D19966AB634461A1B01AABDED96A8C', null, null, null, null, '三级部门b11', 'unknown', '5454', '43557EE3C0D849C1990331C7145B4884', 'b11', '公安部', '1', '1');
INSERT INTO "IWATER"."sys_org" VALUES ('730EC422E45A489CA3E14074C45342F1', null, null, null, null, '三级部门b12', 'unknown', 'www.baidu.com', '43557EE3C0D849C1990331C7145B4884', 'b12', '民政部', '2', '1');
INSERT INTO "IWATER"."sys_org" VALUES ('E82B6A9B1073439BB93E63C6FF1830DF', '2017-02-15 10:54:17', '2017-02-15 12:51:27', null, null, '三级部门a11', 'abc2', 'http:2', '4181DF8FE6AB47C8B1409A6E84378C96', 'adasd2', 'suo2', '12', '1');

-- ----------------------------
-- Table structure for sys_power
-- ----------------------------
DROP TABLE "IWATER"."sys_power";
CREATE TABLE "IWATER"."sys_power" (
"ui_id" NVARCHAR2(100) NULL ,
"add_date" NVARCHAR2(50) NULL ,
"up_date" NVARCHAR2(50) NULL ,
"user_info" NVARCHAR2(50) NULL ,
"power_type" NVARCHAR2(50) NULL ,
"power_owner" NVARCHAR2(50) NULL ,
"power_parent" NVARCHAR2(50) NULL ,
"power_prefix" NVARCHAR2(50) NULL ,
"power_name" NVARCHAR2(50) NULL ,
"power_code" NVARCHAR2(50) NULL ,
"power_url" NVARCHAR2(1000) NULL ,
"power_token" NVARCHAR2(50) NULL ,
"power_follower" NVARCHAR2(50) NULL ,
"del_modified" NVARCHAR2(50) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON COLUMN "IWATER"."sys_power"."ui_id" IS '主键';
COMMENT ON COLUMN "IWATER"."sys_power"."add_date" IS '添加时间';
COMMENT ON COLUMN "IWATER"."sys_power"."up_date" IS '修改时间';
COMMENT ON COLUMN "IWATER"."sys_power"."user_info" IS '操作者';
COMMENT ON COLUMN "IWATER"."sys_power"."power_type" IS '权限类型';
COMMENT ON COLUMN "IWATER"."sys_power"."power_owner" IS '权限权属(页面使用 判断菜单)';
COMMENT ON COLUMN "IWATER"."sys_power"."power_parent" IS '父菜单ui_id (仅菜单资源使用)';
COMMENT ON COLUMN "IWATER"."sys_power"."power_prefix" IS '权限所属项目';
COMMENT ON COLUMN "IWATER"."sys_power"."power_name" IS '对应名称';
COMMENT ON COLUMN "IWATER"."sys_power"."power_code" IS '对应代码';
COMMENT ON COLUMN "IWATER"."sys_power"."power_url" IS '对应地址';
COMMENT ON COLUMN "IWATER"."sys_power"."power_token" IS 'token(访问公共接口使用)';
COMMENT ON COLUMN "IWATER"."sys_power"."power_follower" IS '权限跟从';
COMMENT ON COLUMN "IWATER"."sys_power"."del_modified" IS '删除标志位';

-- ----------------------------
-- Records of sys_power
-- ----------------------------
INSERT INTO "IWATER"."sys_power" VALUES ('test1', null, '2017-02-24 15:06:30', null, '001', null, null, 'iwater', '接口权限1', 'quanxian1', '/system/someonelike', 'qwe123', null, '1');
INSERT INTO "IWATER"."sys_power" VALUES ('C0037B0DB70A49C585A9EA00B666D51A', '2017-02-17 10:01:34', '2017-03-02 13:43:05', 'admin', '002', null, '0', 'iwater', '系统管理', 'xitong', 'useless', null, null, '1');
INSERT INTO "IWATER"."sys_power" VALUES ('38A2333243F346C0A933333A13752787', '2017-02-17 10:02:18', '2017-03-02 13:44:36', 'admin', '002', null, 'C0037B0DB70A49C585A9EA00B666D51A', 'iwater', '菜单管理', 'caidan', '#!/menu/menuManager/', null, null, '1');
INSERT INTO "IWATER"."sys_power" VALUES ('E50D09C0CF484222A6E5160F75106167', '2017-02-17 10:02:48', '2017-03-02 13:44:02', 'admin', '002', null, 'C0037B0DB70A49C585A9EA00B666D51A', 'iwater', '用户管理', 'yonghu', '#!/user/userManager/', null, null, '1');
INSERT INTO "IWATER"."sys_power" VALUES ('EBD9BE1EB20144A99397D2A0868E7F36', '2017-02-17 10:03:47', '2017-03-02 13:44:29', 'admin', '002', null, 'C0037B0DB70A49C585A9EA00B666D51A', 'iwater', '权限资源管理', 'quanxian', '#!/power/powerManager', null, null, '1');
INSERT INTO "IWATER"."sys_power" VALUES ('C790BF782B2E47F8A92E767D36D5E083', '2017-02-17 10:04:22', '2017-03-02 13:44:43', 'admin', '002', null, 'C0037B0DB70A49C585A9EA00B666D51A', 'iwater', '字典管理', 'zidian', '#!/dic/dicManager/', null, null, '1');
INSERT INTO "IWATER"."sys_power" VALUES ('E89C965EE229498A95F5FF4D39E6C279', '2017-02-24 15:12:28', null, null, '001', null, null, 'iwater', '测试接口9', 'ceshijekou', '/system/localhost/', 'i123456', null, '1');
INSERT INTO "IWATER"."sys_power" VALUES ('F295DDDFDCBF423A8011502ED68C67BB', '2017-02-27 16:12:14', '2017-03-01 09:55:41', 'admin', '004', 'yonghu', null, 'iwater', '用户管理页面', 'userManager', '/system/user/**,
/system/organization/**', null, 'add,update,query,download,del', '1');
INSERT INTO "IWATER"."sys_power" VALUES ('DF969EDD8BDD4B7C9E53F1E15DC9E0B7', '2017-02-28 13:02:19', '2017-03-02 13:43:53', 'admin', '002', null, 'C155B9F07E5843B6B9A5A09E0FF9FAD7', 'iwater', '基础参数配置', 'baseparam', '#!/bs/bsManager/', null, null, '1');
INSERT INTO "IWATER"."sys_power" VALUES ('8B1965D60E3743FEA0CB90E62CDC3FC2', '2017-02-28 13:03:25', '2017-03-02 13:44:09', 'admin', '002', null, 'C155B9F07E5843B6B9A5A09E0FF9FAD7', 'iwater', '查询条件配置', 'queryItem', '#!/query/queryManager/', null, null, '1');
INSERT INTO "IWATER"."sys_power" VALUES ('E79D7382A39D47A38CE1A1B709AEB8C2', '2017-02-28 13:04:48', '2017-03-02 13:44:15', 'admin', '002', null, 'C0037B0DB70A49C585A9EA00B666D51A', 'iwater', '角色管理', 'roleManager', '#!/role/roleManager', null, null, '1');
INSERT INTO "IWATER"."sys_power" VALUES ('2B70B395E6024A3C8C7EC4F666F7D70A', '2017-02-28 13:06:07', '2017-03-02 13:44:48', 'admin', '002', null, 'C155B9F07E5843B6B9A5A09E0FF9FAD7', 'iwater', '日志管理', 'logManager', '#!/log/logManager/', null, null, '1');
INSERT INTO "IWATER"."sys_power" VALUES ('C155B9F07E5843B6B9A5A09E0FF9FAD7', '2017-02-28 13:07:36', '2017-03-02 13:43:47', 'admin', '002', null, '0', 'iwater', '开发管理', 'developManager', 'useless', null, null, '1');
INSERT INTO "IWATER"."sys_power" VALUES ('0876545D979B409F9F21CA89A3A93555', '2017-02-28 13:10:15', '2017-03-02 13:44:53', 'admin', '002', null, 'C155B9F07E5843B6B9A5A09E0FF9FAD7', 'iwater', '缓存管理', 'cacheManager', '#!/cache/cacheManager/', null, null, '1');
INSERT INTO "IWATER"."sys_power" VALUES ('7849DFFCF2E247DC8C293BAC989A0AF5', '2017-02-28 13:11:23', '2017-03-02 13:44:23', 'admin', '002', null, 'C0037B0DB70A49C585A9EA00B666D51A', 'iwater', '组织机构管理', 'organManager', '#!/org/orgManager', null, null, '1');
INSERT INTO "IWATER"."sys_power" VALUES ('0A1A58B707DF4812A6AD48702AE8C7BB', '2017-03-01 09:57:56', '2017-03-01 10:01:08', 'admin', '004', 'yonghu', null, 'iwater', '新增用户', 'addUser', '/system/organization/allList,
/system/role/list,
/system/user/**', null, 'upload', '1');
INSERT INTO "IWATER"."sys_power" VALUES ('CBD2F4A9711E4C82998B0C4A28837810', '2017-03-01 15:20:28', '2017-03-02 13:43:42', 'admin', '002', null, 'C155B9F07E5843B6B9A5A09E0FF9FAD7', 'iwater', '数据库配置', 'db', '#!/db/dbManager/', null, null, '1');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE "IWATER"."sys_role";
CREATE TABLE "IWATER"."sys_role" (
"ui_id" NVARCHAR2(100) NOT NULL ,
"add_date" NVARCHAR2(50) NULL ,
"up_date" NVARCHAR2(50) NULL ,
"user_info" NVARCHAR2(50) NULL ,
"role_type" NVARCHAR2(50) NULL ,
"role_zhname" NVARCHAR2(50) NULL ,
"role_enname" NVARCHAR2(50) NULL ,
"role_code" NVARCHAR2(50) NULL ,
"is_del" NVARCHAR2(50) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON COLUMN "IWATER"."sys_role"."ui_id" IS '主键';
COMMENT ON COLUMN "IWATER"."sys_role"."add_date" IS '添加时间';
COMMENT ON COLUMN "IWATER"."sys_role"."up_date" IS '修改时间';
COMMENT ON COLUMN "IWATER"."sys_role"."user_info" IS '操作者';
COMMENT ON COLUMN "IWATER"."sys_role"."role_type" IS '角色类型';
COMMENT ON COLUMN "IWATER"."sys_role"."role_zhname" IS '角色名称(中)';
COMMENT ON COLUMN "IWATER"."sys_role"."role_enname" IS '角色名称(英)';
COMMENT ON COLUMN "IWATER"."sys_role"."role_code" IS '角色代码';
COMMENT ON COLUMN "IWATER"."sys_role"."is_del" IS '删除标志位';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO "IWATER"."sys_role" VALUES ('0CC5CA770E374603B47F6C0D66151B88', '2017-03-02 10:23:42', '2017-03-06 09:26:02', 'admin', 'role_type_001', '浏览测试角色', 'role_look', 'ROLE_LOOK', '1');
INSERT INTO "IWATER"."sys_role" VALUES ('54155D29A91D4D92A5FF7A85F7B89225', '2017-03-03 17:14:52', '2017-03-06 09:28:23', 'admin', 'role_type_003', '操作测试2', 'operation', 'ROLE_OPERATION', '1');
INSERT INTO "IWATER"."sys_role" VALUES ('TEST1', null, '2017-03-02 10:19:27', 'admin', 'role_type_004', '最高管理员', 'super manager', 'ROLE_ADMIN', '1');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE "IWATER"."sys_user";
CREATE TABLE "IWATER"."sys_user" (
"ui_id" NVARCHAR2(100) NOT NULL ,
"add_date" NVARCHAR2(50) NULL ,
"up_date" NVARCHAR2(50) NULL ,
"user_info" NVARCHAR2(50) NULL ,
"user_code" NVARCHAR2(50) NULL ,
"user_pwd" NVARCHAR2(100) NULL ,
"user_name" NVARCHAR2(50) NULL ,
"user_mail" NVARCHAR2(50) NULL ,
"user_post" NVARCHAR2(50) NULL ,
"user_tel" NVARCHAR2(50) NULL ,
"user_phone" NVARCHAR2(50) NULL ,
"del_modified" NVARCHAR2(50) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON COLUMN "IWATER"."sys_user"."ui_id" IS '主键';
COMMENT ON COLUMN "IWATER"."sys_user"."add_date" IS '添加时间';
COMMENT ON COLUMN "IWATER"."sys_user"."up_date" IS '修改时间';
COMMENT ON COLUMN "IWATER"."sys_user"."user_info" IS '操作者';
COMMENT ON COLUMN "IWATER"."sys_user"."user_code" IS '账户';
COMMENT ON COLUMN "IWATER"."sys_user"."user_pwd" IS '密码';
COMMENT ON COLUMN "IWATER"."sys_user"."user_name" IS '真实姓名';
COMMENT ON COLUMN "IWATER"."sys_user"."user_mail" IS '邮箱';
COMMENT ON COLUMN "IWATER"."sys_user"."user_post" IS '职务';
COMMENT ON COLUMN "IWATER"."sys_user"."user_tel" IS '联系电话(手)';
COMMENT ON COLUMN "IWATER"."sys_user"."user_phone" IS '联系电话(座)';
COMMENT ON COLUMN "IWATER"."sys_user"."del_modified" IS '删除标志位';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO "IWATER"."sys_user" VALUES ('117F07684ACC40B68F60B64F24B3239B', '2017-03-01 10:14:28', '2017-03-02 09:53:35', 'admin', 'tingyu', '$2a$10$90qg6A5JJ81uRWA.vcidEexazLdzaTiKh3SxRyy58njW5fkJ9/tzm', '张廷玉', 'yu@baosight.com', 'user_post_001', '13652636325', '0716-5896262', '1');
INSERT INTO "IWATER"."sys_user" VALUES ('A2A614F1C7014B29B750C2AE931DCBF0', '2017-02-24 15:40:49', '2017-03-02 10:22:32', 'admin', 'admin', '$2a$10$viRepHg.Zt9JxBC3o8vxneRXC1zO3zW82jbCVR232CsLD2hbcR7La', '管理员', 'baosight@shanshan.com', 'user_post_001', '13685828585', '071685959696', '1');
INSERT INTO "IWATER"."sys_user" VALUES ('F74C5BED394340F3970C56908E00E653', '2017-03-13 15:15:02', '2017-03-13 15:26:14', 'admin', 'gaoh', '$2a$10$XUcKl0efN6PigH55ruNgxergdSRoeFEjGsyA5OZK9tk9o5b5sAuHK', 'gaoh', null, 'user_post_004', '15611111111', null, '1');
INSERT INTO "IWATER"."sys_user" VALUES ('FDD214DA26C04C9A8CDDBE91C1D3D323', '2017-02-17 11:22:06', '2017-02-28 15:49:41', null, 'ym', '$2a$10$d2uxDkoJRdTTTTMnqoPbbuAJqQPAgcjVTNkw0/ufHXZ7SikUz0F.e', '袁敏', null, '001', '15921593569', null, '1');

-- ----------------------------
-- Indexes structure for table sys_cache
-- ----------------------------

-- ----------------------------
-- Checks structure for table sys_cache
-- ----------------------------
ALTER TABLE "IWATER"."sys_cache" ADD CHECK ("cache_id" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table sys_cache
-- ----------------------------
ALTER TABLE "IWATER"."sys_cache" ADD PRIMARY KEY ("cache_id");

-- ----------------------------
-- Indexes structure for table sys_db
-- ----------------------------

-- ----------------------------
-- Checks structure for table sys_db
-- ----------------------------
ALTER TABLE "IWATER"."sys_db" ADD CHECK ("ui_id" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table sys_db
-- ----------------------------
ALTER TABLE "IWATER"."sys_db" ADD PRIMARY KEY ("ui_id");

-- ----------------------------
-- Indexes structure for table sys_log
-- ----------------------------

-- ----------------------------
-- Checks structure for table sys_log
-- ----------------------------
ALTER TABLE "IWATER"."sys_log" ADD CHECK ("ui_id" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table sys_log
-- ----------------------------
ALTER TABLE "IWATER"."sys_log" ADD PRIMARY KEY ("ui_id");

-- ----------------------------
-- Indexes structure for table sys_menu
-- ----------------------------

-- ----------------------------
-- Checks structure for table sys_menu
-- ----------------------------
ALTER TABLE "IWATER"."sys_menu" ADD CHECK ("ui_id" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table sys_menu
-- ----------------------------
ALTER TABLE "IWATER"."sys_menu" ADD PRIMARY KEY ("ui_id");

-- ----------------------------
-- Indexes structure for table sys_org
-- ----------------------------

-- ----------------------------
-- Checks structure for table sys_org
-- ----------------------------
ALTER TABLE "IWATER"."sys_org" ADD CHECK ("ui_id" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table sys_org
-- ----------------------------
ALTER TABLE "IWATER"."sys_org" ADD PRIMARY KEY ("ui_id");

-- ----------------------------
-- Indexes structure for table sys_role
-- ----------------------------

-- ----------------------------
-- Checks structure for table sys_role
-- ----------------------------
ALTER TABLE "IWATER"."sys_role" ADD CHECK ("ui_id" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table sys_role
-- ----------------------------
ALTER TABLE "IWATER"."sys_role" ADD PRIMARY KEY ("ui_id");

-- ----------------------------
-- Indexes structure for table sys_user
-- ----------------------------

-- ----------------------------
-- Checks structure for table sys_user
-- ----------------------------
ALTER TABLE "IWATER"."sys_user" ADD CHECK ("ui_id" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table sys_user
-- ----------------------------
ALTER TABLE "IWATER"."sys_user" ADD PRIMARY KEY ("ui_id");
