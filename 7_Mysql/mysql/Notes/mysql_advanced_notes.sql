-- 字符集问题

-- 创建库
CREATE DATABASE mydb ; 

-- 使用库
USE mydb ;

-- 创建表
CREATE TABLE mytbl(
   id INT(11) PRIMARY KEY AUTO_INCREMENT ,
   NAME VARCHAR(50)
);

-- 插入数据
INSERT INTO mytbl VALUES(1001,"zs");
INSERT INTO mytbl VALUES(1002,"张三");
-- 插入中文报错
ERROR 1366 (HY000): Incorrect STRING VALUE: '\xE5\xBC\xA0\xE4\xB8\x89' FOR COLUMN 'NAME' AT ROW 1

-- 查看表使用的编码
SHOW CREATE TABLE mytbl ;
-- 查看库使用的编码`bigdata_0115`
SHOW CREATE DATABASE mydb ;

-- 查看mysql整个字符集配置
SHOW VARIABLES LIKE '%character%';
+--------------------------+----------------------------+
| Variable_name            | VALUE                      |
+--------------------------+----------------------------+
| character_set_client     | utf8                       |
| character_set_connection | utf8                       |
| character_set_database   | latin1                     |
| character_set_filesystem | BINARY                     |
| character_set_results    | utf8                       |
| character_set_server     | latin1                     |
| character_set_system     | utf8                       |
| character_sets_dir       | /usr/SHARE/mysql/charsets/ |
+--------------------------+----------------------------+


-- 修改字符集

-- 修改库和表的编码
-- 改库
ALTER DATABASE mydb CHARACTER SET 'utf8';
-- 改表
ALTER TABLE mytbl CONVERT TO CHARACTER  SET 'utf8' ;


-- sql_mode

-- 建表
CREATE TABLE mytbl2 (id INT,NAME VARCHAR(200),age INT,dept INT);
INSERT INTO mytbl2 VALUES(1,'zhang3',33,101);
INSERT INTO mytbl2 VALUES(2,'li4',34,101);
INSERT INTO mytbl2 VALUES(3,'wang5',34,102);
INSERT INTO mytbl2 VALUES(4,'zhao6',34,102);
INSERT INTO mytbl2 VALUES(5,'tian7',36,102);

查询每个dept中年龄最大的人：
+------+--------+------+------+
| id   | NAME   | age  | dept |
+------+--------+------+------+
|    1 | zhang3 |   33 |  101 |
|    2 | li4    |   34 |  101 |
|    3 | wang5  |   34 |  102 |
|    4 | zhao6  |   34 |  102 |
|    5 | tian7  |   36 |  102 |
+------+--------+------+------+

-- 错误的写法: 
SELECT NAME,dept,MAX(age) FROM mytbl2 GROUP BY dept;

+--------+------+----------+
| NAME   | dept | MAX(age) |
+--------+------+----------+
| zhang3 |  101 |       34 |
| wang5  |  102 |       36 |
+--------+------+----------+

-- 正确的写法
SELECT id,NAME,ab.dept,ab.maxage 
FROM mytbl2 m INNER JOIN
(SELECT dept,MAX(age)maxage FROM mytbl2 GROUP BY dept)ab 
ON ab.dept=m.dept AND m.age=ab.maxage;

+------+--------+
| dept | maxage |
+------+--------+
|  101 |     34 |
|  102 |     36 |
+------+--------+

+------+--------+------+------+
| id   | NAME   | age  | dept |
+------+--------+------+------+
|    1 | zhang3 |   33 |  101 |
|    2 | li4    |   34 |  101 |
|    3 | wang5  |   34 |  102 |
|    4 | zhao6  |   34 |  102 |
|    5 | tian7  |   36 |  102 |
+------+--------+------+------+

+------+-------+------+--------+
| id   | NAME  | dept | maxage |
+------+-------+------+--------+
|    2 | li4   |  101 |     34 |
|    5 | tian7 |  102 |     36 |
+------+-------+------+--------+


-- 注意的问题:
-- 分组:  group by以后， select后面只能跟 组标识(分组字段) 及 聚合函数(组函数)



-- 修改user表支持远程连接
-- 查看user表中的用户
SELECT HOST,USER,authentication_string FROM USER ;

-- 修改root用户支持任意ip连接
UPDATE USER SET HOST ='%' WHERE USER = 'root';
FLUSH PRIVILEGES;


-- 存储引擎
-- 查看mysql支持的存储引擎
SHOW ENGINES;

-- mysql默认的存储引擎
-- mysql自带的库默认使用MyISAM
-- 用户自己创建的库默认使用InnoDB

SHOW CREATE TABLE mysql.user ;  -- MyISAM
SHOW CREATE TABLE mydb.mytbl ;  -- InnoDB

-- 建表指定使用存储引擎
CREATE TABLE mydb.mytbl3(
  id INT(11) PRIMARY KEY AUTO_INCREMENT,
  NAME VARCHAR(50)
)ENGINE=MYISAM;

SHOW CREATE TABLE mydb.mytbl3; 


-- 多表关联的方式: 
-- 内连接  A inner join B on A.id = B.id 
   -- 内连接的结果集(不考虑过滤条件):	取交集

-- 外连接  
   -- 左外连接   A left outer join B on A.id = B.id 
   -- 右外连接   A right outer join B on A.id = B.id 
   
   -- 举例:  A left outer join B   <=>  B right outer join  A 
   
   -- 关键点  明确主(驱动表)从(匹配表)表
   -- 如何明确主从表: 左外连左主右从，右外连左从右主。
   -- 外连接的结果集(不考虑过滤条件): 
   -- 驱动表的数据都要，匹配表的数据只取能与驱动表匹配的数据
   -- 如果驱动表中的数据没有与匹配表匹配上，则结果集中该数据所
   -- 对应的匹配的字段的值通过null来补。
   
 
 -- Mysql的索引结构
 -- 索引就是一种数据结构，用于提升查询效率。
 -- Mysql的索引结构使用的B+tree , 所谓的自平衡树，能够在数据比较极端的情况下进行
 -- 自动平衡。
 -- Mysql使用B+tree作为索引主要基于如下两点来考虑：
 -- 1. B+tree的磁盘IO更少
 -- 2. B+tree数据的查找的效率更加稳定。因为数据都在叶子节点.
 
 
 -- 索引的分类
 -- 查询表的索引
 SHOW INDEX FROM xxx表 
 -- 删除表的索引
 DROP INDEX  索引名 ON xxx表

 
 -- 1.单值索引 INDEX
 -- 1.1 随表一起建立
 CREATE TABLE customer (
  id INT(10) AUTO_INCREMENT ,
  customer_no VARCHAR(200),
  customer_name VARCHAR(200),
  PRIMARY KEY(id),
  KEY (customer_name)  -- 给customer_name列建立索引
);

SHOW INDEX FROM customer ;
DROP INDEX customer_name ON customer ;
 
 -- 1.2 单独建立
CREATE INDEX customer_index_name ON customer(customer_name);
 
  
 -- 2. 唯一索引 UNIQUE INDEX 
DROP TABLE customer;

SHOW INDEX FROM customer ; 
DROP INDEX customer_no ON customer ; 
 
 -- 2.1 随表一起建立
 CREATE TABLE customer (
  id INT(10)  AUTO_INCREMENT ,
  customer_no
  VARCHAR(200),
  customer_name VARCHAR(200),
  PRIMARY KEY(id),
  KEY (customer_name),
  UNIQUE (customer_no)
);
 
 -- 2.2 单独建立
 CREATE UNIQUE INDEX customer_u_index_no ON customer(customer_no);
 
 
 -- 3. 主键索引
 -- 3.1 随表一起建立
 DROP TABLE customer ;
 
 CREATE TABLE customer (
 id INT(10) AUTO_INCREMENT ,
 customer_no
 VARCHAR(200),
 customer_name VARCHAR(200),
 PRIMARY KEY(id) 
);
 CREATE TABLE customer (
 id INT(10) PRIMARY KEY  AUTO_INCREMENT ,
 customer_no
 VARCHAR(200),
 customer_name VARCHAR(200)
);


SHOW INDEX FROM customer ;

 -- 3.2 单独建立
 CREATE TABLE customer (
 id INT(10),
 customer_no
 VARCHAR(200),
 customer_name VARCHAR(200)
);
 ALTER TABLE customer ADD PRIMARY KEY  customer(id);
 
 ALTER TABLE customer DROP PRIMARY KEY ; 
 
 
 -- 4.复合索引
 -- 4.1 随表一起建立
 DROP TABLE customer ;
 CREATE TABLE customer (
  id INT(10)  AUTO_INCREMENT ,
  customer_no VARCHAR(200),
  customer_name VARCHAR(200),
  PRIMARY KEY(id),
  KEY (customer_name),
  UNIQUE (customer_name),
  KEY (customer_no,customer_name)
);

SHOW INDEX FROM customer ;
DROP INDEX  customer_index_no_name ON customer ; 
 -- 4.2 单独建立 
CREATE INDEX customer_index_no_name ON customer(customer_name,customer_no);


-- 待解释问题: 复合索引中的序号问题(Seq_in_index)


-- 作业:
1. MySQL的安装和基本配置
2. 写SQL
3. 掌握索引的分类及创建方式

=========================================================================

EXPLAIN: SQL分析

EXPLAIN SELECT * FROM  mydb.mytbl


-- 准备数据
-- 批量数据脚本
-- 在Linux中连入到Mysql中执行:  source /opt/software/batch.sql 

SELECT COUNT(*) FROM emp ;  
SELECT * FROM emp LIMIT 5 ;

SELECT COUNT(*) FROM dept; 
SELECT * FROM dept LIMIT 5 ;


-- 单表使用索引失效的情况

--  全值匹配
CREATE INDEX idx_age_deptid_name ON emp(age,deptid,NAME);
SHOW INDEX FROM emp ; 
CALL proc_drop_index("mydb","emp");  -- 调用存储过程
DESC emp 
EXPLAIN SELECT SQL_NO_CACHE * FROM emp WHERE emp.age=30  
EXPLAIN SELECT SQL_NO_CACHE * FROM emp WHERE emp.age=30 AND deptid=4
EXPLAIN SELECT SQL_NO_CACHE * FROM emp WHERE emp.age=30 AND deptid=4 AND emp.name = 'abcd'


-- 最佳左前缀法则
CREATE INDEX idx_age_deptid_name ON emp(age,deptid,NAME);
SHOW INDEX FROM emp ;
EXPLAIN SELECT SQL_NO_CACHE * FROM emp WHERE emp.deptid=4  
EXPLAIN SELECT SQL_NO_CACHE * FROM emp WHERE emp.age=30 AND emp.name='abcd'
EXPLAIN SELECT SQL_NO_CACHE * FROM emp WHERE deptid=4 AND  emp.age=30 AND emp.name = 'abcd' -- 优化器


-- 索引列做计算  和 范围查询
-- 计算
EXPLAIN SELECT SQL_NO_CACHE * FROM emp WHERE age=30;
EXPLAIN SELECT SQL_NO_CACHE * FROM emp WHERE LEFT(age,3)=30;

SHOW INDEX FROM emp ; 

CREATE INDEX idx_name ON emp(NAME);
EXPLAIN SELECT SQL_NO_CACHE * FROM emp WHERE NAME='30000';
EXPLAIN SELECT SQL_NO_CACHE * FROM emp WHERE NAME=30000;  -- 发生了类型转换

-- 范围查询
DROP INDEX idx_name ON emp ;
SHOW INDEX FROM emp ;
EXPLAIN SELECT SQL_NO_CACHE * FROM emp WHERE emp.age=30 AND deptid=5 AND emp.name = 'abcd';
EXPLAIN SELECT SQL_NO_CACHE * FROM emp WHERE emp.age=30 AND deptid<5 AND emp.name = 'abcd';

CALL proc_drop_index("mydb","emp");
CREATE INDEX idx_age_name_deptid ON emp(age,NAME,deptid);



-- 使用否定类型的判断
-- !=  <>  is not null 
EXPLAIN SELECT SQL_NO_CACHE *  FROM emp WHERE emp.age = 30 ;
EXPLAIN SELECT SQL_NO_CACHE *  FROM emp WHERE emp.age != 30 ;

EXPLAIN SELECT * FROM emp WHERE age IS NULL 
EXPLAIN SELECT * FROM emp WHERE age IS NOT NULL
DESC emp ;


-- 使用like
CREATE INDEX idx_name_emp ON emp (NAME);

EXPLAIN SELECT * FROM emp WHERE NAME LIKE '%a' ;
EXPLAIN SELECT * FROM emp WHERE NAME LIKE '_a' ;
EXPLAIN SELECT * FROM emp WHERE NAME LIKE 'a%' ;
EXPLAIN SELECT * FROM emp WHERE NAME LIKE '%a%' ;

-- or
EXPLAIN SELECT * FROM emp WHERE age = 30 OR age = 40 ;

-- 覆盖索引 index
-- 不要写select *

EXPLAIN  SELECT  *  FROM  emp 

EXPLAIN  SELECT deptid  FROM emp 

EXPLAIN  SELECT  age,deptId,NAME  FROM emp WHERE age = 30 


--  视图
--  视图可以理解为虚拟的表，封装了一段SQL
-- 1. 如果一个真实的表有10个字段，但是只有3个字段是可以暴露给程序员查询的,怎么办?
SELECT * FROM emp LIMIT 10 
CREATE VIEW emp_view AS SELECT empno ,NAME FROM emp LIMIT 10 
SELECT * FROM emp_view; 
 
-- 2. 如果一个业务需求经常变，对应的SQL也要跟着变，怎么办？
-- 查询10条数据
SELECT * FROM emp LIMIT 10 
-- 变需求: 查询15条数据
SELECT * FROM emp LIMIT 15 

CREATE VIEW emp_limit AS SELECT * FROM emp LIMIT 15 ;
DROP VIEW emp_limit; 

SELECT * FROM emp_limit ; 

-- 3. ....

 



