MySQL 数据库工作模式是C/S架构
mysqld.exe 就是服务器, ServerSocket
mysql.exe 就是客户端, Socket.

通过Socket进行连接
要想连接服务器, 必须要提供主机ip地址(-h), 端口号(-P), 用户名(-u), 密码(-p)

set MYSQL_HOME=D:\MyWork\MySQL\mysql-5.7.28-winx64
set PATH=%MYSQL_HOME%\bin;%PATH%
rem /d的目的是不需要切换盘就能直接跑到指定的目标目录中.
cd /d %MYSQL_HOME%
mysql -h127.0.0.1 -P3306 -uroot -p"123456"
rem 万一发生什么问题, 暂停一下看看问题
pause 


在桌面上右击 "新建" -> "文本文件"
把文件名改为"启动mysql客户端.bat", 右击此文件 -> "编辑"
把上面几行内容复制到文件中, 保存
在桌面右击"启动mysql客户端.bat", -> "以管理员身份运行"


成功画面如下 :
D:\MyWork\MySQL\mysql-5.7.28-winx64>mysql -h127.0.0.1 -P3306 -uroot -p"123456"
mysql: [Warning] Using a password on the command line interface can be insecure.
Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 9
Server version: 5.7.28 MySQL Community Server (GPL)

Copyright (c) 2000, 2019, Oracle and/or its affiliates. All rights reserved.

Oracle is a registered trademark of Oracle Corporation and/or its
affiliates. Other names may be trademarks of their respective
owners.

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

mysql> _

常用的SQL命令 :
注意 : 所有的sql都必须以;为结尾和动令.

查看服务器中的数据库
show databases;

每个数据库是一个在Data目录下的一个单独的子目录.

查看编码设置
show variables like 'char%';

+--------------------------+-----------------------------------------------------+
| Variable_name            | Value                                               |
+--------------------------+-----------------------------------------------------+
| character_set_client     | gbk                                                 |
| character_set_connection | gbk                                                 |
| character_set_database   | utf8                                                |
| character_set_filesystem | binary                                              |
| character_set_results    | gbk                                                 |
| character_set_server     | utf8                                                |
| character_set_system     | utf8                                                |
| character_sets_dir       | D:\MyWork\MySQL\mysql-5.7.28-winx64\share\charsets\ |
+--------------------------+-----------------------------------------------------+

1) 创建自定义数据库
create database company charset utf8;

2) 切换工作数据库
use company;

4) 查看数据库中的所有表
show tables;
+-------------------+
| Tables_in_company |
+-------------------+
| departments       |
| employees         |
| jobs              |
| locations         |
+-------------------+

3) 把外部的.sql文件中的数据导入到当前数据库中, 右面的文件的路径以实际为准.
source d:/company.sql;

5) 查看表中的数据
select * from employees;

6) 丢弃数据库
drop database company;

练习 : 创建数据库world, 并把d:/world.sql数据导入到world数据库中.
1) create database world charset utf8;
2) use world;
3) source d:/world.sql;
4) show tables;
+-----------------+
| Tables_in_world |
+-----------------+
| city            |
| country         |
| countrylanguage |
+-----------------+
5) select * from country;


mysqld是服务器 : 管理数据库
	数据库1(目录)
		表1(多个文件), .frm是表结构, .ibd是表的数据
			记录(数据)
			记录2
			记录3.....
		表2
		表3
		
	数据库2(目录)
	数据库3(目录)

研究表就是研究表的列的定义, 像属性(数据类型, 列名)

创建表 : 注意点 : 数据库中没有像java中的char数据类型, 数据库中的char就是定长字符串
create table customer (
	id int,
	name varchar(30),
	gender char(1),
	phone varchar(30),
	email varchar(50)
);

查看表结构
describe customer;
desc customer; 
+--------+-------------+------+-----+---------+-------+
| Field  | Type        | Null | Key | Default | Extra |
+--------+-------------+------+-----+---------+-------+
| id     | int(11)     | YES  |     | NULL    |       |
| name   | varchar(30) | YES  |     | NULL    |       |
| gender | char(1)     | YES  |     | NULL    |       |
| phone  | varchar(30) | YES  |     | NULL    |       |
| email  | varchar(50) | YES  |     | NULL    |       |
+--------+-------------+------+-----+---------+-------+

丢弃表 : 把表结构及其中的数据全部删除
drop table customer;

向表中插入数据
insert into 表名(列名列表) values(列对应的值的列表);

insert into customer (
	id,
	name,
	gender,
	phone, 
	email
) values (
	1,
	'张三',
	'男',
	'213234',
	'zhang3@qq.com'
);

insert into customer (
	id,
	name,
	gender,
	phone, 
	email
) values (
	2,
	'李四',
	'女',
	'2321234',
	'li4@qq.com'
);

insert into customer (
	id,
	name,
	gender,
	phone, 
	email
) values (
	3,
	'王五',
	'男',
	'3341234',
	'w5@qq.com'
);

修改数据 

如果这样写会修改所有数据, 这样不好
update customer set 
	name = '王老五',
	phone = '1323434',
	email = 'wl5@qq.com';
	
update customer set 
	name = '张三',
	email = 'z3@qq.com'
where
	id = 1;

删除数据

如果这样写会删除所有数据, 这样不好
delete from customer;

delete from customer where id = 2;

插入数据 insert : C 
查看数据 select : R  
修改数据 update : U 
删除数据 delete : D 

CRUD操作针对的是表中的数据, 这样的语句 称为DML(数据操纵语言)


如果show variables like 'char%'; 结果如下, 可以执行语句  set names gbk; 
+--------------------------+-----------------------------------------------------+
| Variable_name            | Value                                               |
+--------------------------+-----------------------------------------------------+
| character_set_client     | utf8                                                |
| character_set_connection | utf8                                                |
| character_set_database   | utf8                                                |
| character_set_filesystem | binary                                              |
| character_set_results    | utf8                                                |
| character_set_server     | utf8                                                |
| character_set_system     | utf8                                                |
| character_sets_dir       | D:\Mywork\Mysql\mysql-5.7.28-winx64\share\charsets\ |
+--------------------------+-----------------------------------------------------+


查询所有数据
select * from departments;

查询指定列的数据
SELECT department_id, location_id
FROM   departments;


关系型数据库体现 就是 表与表之间是有联系的, 不是孤立的.

+----------------+---------------------------------------------------------------------------------------+------+-----+---------+-------+
| Field          | Type                                                                                  | Null | Key | Default | Extra |
+----------------+---------------------------------------------------------------------------------------+------+-----+---------+-------+
| Code(代码)           | char(3)                                                                               | NO   | PRI |         |       |
| Name           | char(52)                                                                              | NO   |     |         |       |
| Continent(大洲)      | enum('Asia','Europe','North America','Africa','Oceania','Antarctica','South America') | NO   |     | Asia    |       |
| Region(地区)         | char(26)                                                                              | NO   |     |         |       |
| SurfaceArea(面积)    | float(10,2)                                                                           | NO   |     | 0.00    |       |
| IndepYear      | smallint(6)                                                                           | YES  |     | NULL    |       |
| Population(人口)     | int(11)                                                                               | NO   |     | 0       |       |
| LifeExpectancy(平均寿命) | float(3,1)                                                                            | YES  |     | NULL    |       |
| GNP(国民生产总值)            | float(10,2)                                                                           | YES  |     | NULL    |       |
| GNPOld         | float(10,2)                                                                           | YES  |     | NULL    |       |
| LocalName      | char(45)                                                                              | NO   |     |         |       |
| GovernmentForm | char(45)                                                                              | NO   |     |         |       |
| HeadOfState(领导人)    | char(60)                                                                              | YES  |     | NULL    |       |
| Capital(首都)        | int(11)                                                                               | YES  |     | NULL    |       |
| Code2          | char(2)                                                                               | NO   |     |         |       |
+----------------+---------------------------------------------------------------------------------------+------+-----+---------+-------+

select * from country;

select 
	code,
	name,
	continent
from 
	country;
	
select 
	continent,
	code,
	name 
from 
	country;
	
查询的本质 : 把基表切开, 再看要select什么列, 就把什么列取出来, 再重新粘成一个临时的虚表, 最终显示给客户端

注意点 : 
SQL 语言大小写不敏感。 
SQL 可以写在一行或者多行
关键字不能被缩写也不能分行
各子句一般要分行写。
使用缩进提高语句的可读性。

别名的作用是在虚表显示的时候体现 出来, 便于查看或计算.
列的别名, as关键字可以省略
SELECT 
	last_name AS name, 
	commission_pct comm
FROM   employees;


别名加上""的好处是可以包含空格, 并最终原样显示
SELECT 
	last_name AS "name", 
	commission_pct "个人 奖金"
FROM   employees;


查询国家的大洲和人口及名称和代码, 大洲和人口起别名, 就是汉字.
select 
	continent as 大洲,
	population "人口",
	name,
	code 
from 
	country;

select 只能控制列, 只能控制虚表将来要取哪些列出来.

where 可以控制行, where后面跟上条件布尔(判定器)

select 
	first_name,
	salary,
	department_id dept
from 
	employees
where 
	department_id = 90;
	
where中不可以使用虚表的列(别名), 因为它早于select执行.
select 
	first_name,
	salary,
	department_id dept
from 
	employees
where -- 在执行where时没有虚表, 只面向基表.
	dept = 90;
	
执行顺序 : from -> where -> select 

练习 : 
查询所有亚洲国家的代码, 大洲和名称, 人口, 要求大洲起别名
select
	code,
	continent 大洲,
	name,
	population
from 
	country 
where 
	continent = 'asia';
	
查询所有中国的城市.
+-------------+----------+------+-----+---------+----------------+
| Field       | Type     | Null | Key | Default | Extra          |
+-------------+----------+------+-----+---------+----------------+
| ID          | int(11)  | NO   | PRI | NULL    | auto_increment |
| Name        | char(35) | NO   |     |         |                |
| CountryCode | char(3)  | NO   | MUL |         |                |
| District    | char(20) | NO   |     |         |                |
| Population  | int(11)  | NO   |     | 0       |                |
+-------------+----------+------+-----+---------+----------------+
select 
	*
from 
	city 
where 
	countrycode = 'chn';

--between a and b 表示一个区间, 包括a和b
SELECT last_name, salary
FROM   employees
WHERE  salary BETWEEN 2500 AND 3500;


--in(集合), 只要等于集合中的任意一个就可以
SELECT employee_id, last_name, salary, manager_id
FROM   employees
WHERE  manager_id IN (100, 101, 201);

--like '字符串' 和 = 一样的
--like '_%' 才是模糊查询

_表示任意一个字符
%表示任意个任意字符

SELECT first_name
FROM employees
WHERE first_name LIKE 'steven';

select
	code,
	name,
	continent
from 
	country 
where 
	name like '_hi%'; -- 第一个肯定有, 但是不确定, 第2个字母是h第3个一定i, 后面任意.
	
select
	code,
	name,
	continent
from 
	country 
where 
	name like '%hi%'; -- 国家名称中只要包含了hi就行
	
-- 第2个字母是o, 其他随便
SELECT last_name
FROM   employees
WHERE  last_name LIKE '_o%';

-- 查询城市名称中包含ing的
select 
	*
from 
	city 
where 
	name like '%ing%';
	
-- 查询国家名称第3个字母是a的国家
select 
	code,
	name,
	population
from 
	country 
where 
	name like '__a%';

null是特殊值, 是一个特殊数据, 表示的是空, 什么也没有, 所以它参与比较运算逻辑运算, 结果一定是false

select 
	code,
	name,
	continent,
	population,
	capital
from 
	country 
where 
	capital = null;
	
null有一个特殊的专门操作符 is 

select 
	code,
	name,
	continent,
	population,
	capital
from 
	country 
where 
	capital is null;
	
-- 查看所有有首都的国家
select 
	code,
	name,
	continent,
	population,
	capital
from 
	country 
where 
	capital is not null;
	
-- 查询哪些国家尚未独立.
select 
	name,
	continent,
	indepyear
from 
	country 
where 
	indepyear is null;
	
And表示逻辑与, 是并且
SELECT 
	employee_id, 
	last_name, 
	job_id, 
	salary
FROM   
	employees
WHERE  
		salary >=10000
	AND    
		job_id LIKE '%MAN%';
		
OR表示逻辑或, 是或者
SELECT 
	employee_id, 
	last_name, 
	job_id, 
	salary
FROM   
	employees
WHERE  
		salary >=10000
	OR    
		job_id LIKE '%MAN%';
		
-- 查询所有亚洲国家人口在5000万以上的国家名称, 代码和人口.
select 
	code,
	name,
	population
from 
	country 
where 
		continent = 'asia'
	and 
		population > 50000000;

-- 查询中国哪些城市人口小于50万.
select 
	*
from 
	city 
where 
		countrycode = 'chn'
	and 
		population < 500000;

		
--去重使用distinct, distinct作用于后面的所有列的组合值		
--全球有哪些大洲, 	
--distinct前面不允许出现别的列.
select 
	distinct 
		continent,
		region
from 
	country;
	
-- 查询中国有哪些不同的省份
select 
	distinct district
from 
	city 
where 
	countrycode = 'chn';

-- 查询全球各有哪些不同的政府组织.
select 
	distinct GovernmentForm
from 
	country;

order by  排序依据的列, 作用是把虚表的显示结果按指定的顺序	
	
select 
	id,
	name,
	district dist,
	population pop
from 
	city 
where 
		countrycode = 'chn'
	and 
		population < 200000
order by 
	dist desc; --desc表示降序  asc; -- asc 表示升序
	
执行顺序 : from -> where -> select -> order by 
排序永远是最后最后才执行..总是放在最后

-- order by 后面如果有多个列, 先按第一列为依据排序, 再在第一个列排好序的基础上, 以第二列为依据再微排.
SELECT 
	last_name, department_id, salary
FROM   
	employees
ORDER BY 
	department_id asc, 
	salary DESC;

	
-- 查看所有中国城市人口小于10万的城市, 按照省份升序显示, 省份内部再按名称降序.
select 
	id,
	name,
	district dist,
	population pop
from 
	city 
where 
		countrycode = 'chn'
	and 
		population < 100000
order by 
	dist asc,
	name desc;
	
-- 查看国家的人口, 以大洲排序, 查看每个大洲中人口最多的国家
select 
	name,
	continent,
	population
from 
	country 
order by 
	continent,
	population;

-- 查看最富有的国家
select 
	code,
	name,
	continent,
	gnp
from 
	country 
order by 
	gnp desc;
	
-- 查看亚洲最少人口的国家 
select 
	code,
	name,
	population
from 
	country 
where 
	continent = 'asia'
order by 
	population desc;
	
-- 查看中国共使用多少种语言
select 
	*
from 
	countrylanguage
where 
	countrycode = 'chn';

	
执行顺序 : from 确定基表, where 过滤基表的行, select 确定要哪些列, order by 确定虚表显示时的顺序

mysql> select * from city where name = 'london';
+------+--------+-------------+----------+------------+
| ID   | Name   | CountryCode | District | Population |
+------+--------+-------------+----------+------------+
|  456 | London | GBR         | England  |    7285000 |
| 1820 | London | CAN         | Ontario  |     339917 |
+------+--------+-------------+----------+------------+

select * from country where code in('gbr', 'can');
+------+----------------+---------------+-----------------+-------------+-----------+------------+----------------+------------+------------+----------------+-------------------------------------+--------------+---------+-------+
| Code | Name           | Continent     | Region          | SurfaceArea | IndepYear | Population | LifeExpectancy | GNP        | GNPOld     | LocalName      | GovernmentForm                      | HeadOfState  | Capital | Code2 |
+------+----------------+---------------+-----------------+-------------+-----------+------------+----------------+------------+------------+----------------+-------------------------------------+--------------+---------+-------+
| CAN  | Canada         | North America | North America   |  9970610.00 |      1867 |   31147000 |           79.4 |  598862.00 |  625626.00 | Canada         | Constitutional Monarchy, Federation | Elisabeth II |    1822 | CA    |
| GBR  | United Kingdom | Europe        | British Islands |   242900.00 |      1066 |   59623400 |           77.7 | 1378330.00 | 1296830.00 | United Kingdom | Constitutional Monarchy             | Elisabeth II |     456 | GB    |
+------+----------------+---------------+-----------------+-------------+-----------+------------+----------------+------------+------------+----------------+-------------------------------------+--------------+---------+-------+

为了便于研究, 建成新表
create table city2 select * from city where name = 'london';
create table country2 select * from country where code in('gbr', 'can');
+------+--------+-------------+----------+------------+
| ID   | Name   | CountryCode | District | Population |
+------+--------+-------------+----------+------------+
|  456 | London | GBR         | England  |    7285000 |
| 1820 | London | CAN         | Ontario  |     339917 |
+------+--------+-------------+----------+------------+
+------+----------------+---------------+-----------------+-------------+-----------+------------+----------------+------------+------------+----------------+-------------------------------------+--------------+---------+-------+
| Code | Name           | Continent     | Region          | SurfaceArea | IndepYear | Population | LifeExpectancy | GNP        | GNPOld     | LocalName      | GovernmentForm                      | HeadOfState  | Capital | Code2 |
+------+----------------+---------------+-----------------+-------------+-----------+------------+----------------+------------+------------+----------------+-------------------------------------+--------------+---------+-------+
| CAN  | Canada         | North America | North America   |  9970610.00 |      1867 |   31147000 |           79.4 |  598862.00 |  625626.00 | Canada         | Constitutional Monarchy, Federation | Elisabeth II |    1822 | CA    |
| GBR  | United Kingdom | Europe        | British Islands |   242900.00 |      1066 |   59623400 |           77.7 | 1378330.00 | 1296830.00 | United Kingdom | Constitutional Monarchy             | Elisabeth II |     456 | GB    |
+------+----------------+---------------+-----------------+-------------+-----------+------------+----------------+------------+------------+----------------+-------------------------------------+--------------+---------+-------+
表联接 : 
select 
	*
from 
	city2,
	country2;
+------+--------+-------------+----------+------------+------+----------------+---------------+-----------------+-------------+-----------+------------+----------------+------------+------------+----------------+-------------------------------------+--------------+---------+-------+
| ID   | Name   | CountryCode | District | Population | Code | Name           | Continent     | Region          | SurfaceArea | IndepYear | Population | LifeExpectancy | GNP        | GNPOld     | LocalName      | GovernmentForm                      | HeadOfState  | Capital | Code2 |
+------+--------+-------------+----------+------------+------+----------------+---------------+-----------------+-------------+-----------+------------+----------------+------------+------------+----------------+-------------------------------------+--------------+---------+-------+
|  456 | London | GBR         | England  |    7285000 | CAN  | Canada         | North America | North America   |  9970610.00 |      1867 |   31147000 |           79.4 |  598862.00 |  625626.00 | Canada         | Constitutional Monarchy, Federation | Elisabeth II |    1822 | CA    |
| 1820 | London | CAN         | Ontario  |     339917 | CAN  | Canada         | North America | North America   |  9970610.00 |      1867 |   31147000 |           79.4 |  598862.00 |  625626.00 | Canada         | Constitutional Monarchy, Federation | Elisabeth II |    1822 | CA    |
|  456 | London | GBR         | England  |    7285000 | GBR  | United Kingdom | Europe        | British Islands |   242900.00 |      1066 |   59623400 |           77.7 | 1378330.00 | 1296830.00 | United Kingdom | Constitutional Monarchy             | Elisabeth II |     456 | GB    |
| 1820 | London | CAN         | Ontario  |     339917 | GBR  | United Kingdom | Europe        | British Islands |   242900.00 |      1066 |   59623400 |           77.7 | 1378330.00 | 1296830.00 | United Kingdom | Constitutional Monarchy             | Elisabeth II |     456 | GB    |
+------+--------+-------------+----------+------------+------+----------------+---------------+-----------------+-------------+-----------+------------+----------------+------------+------------+----------------+-------------------------------------+--------------+---------+-------+
表联接中的结果绝大多数都是垃圾, 所以必须过滤
select 
	*
from 
	city2,
	country2
where 
	countrycode = code;
+------+--------+-------------+----------+------------+------+----------------+---------------+-----------------+-------------+-----------+------------+----------------+------------+------------+----------------+-------------------------------------+--------------+---------+-------+
| ID   | Name   | CountryCode | District | Population | Code | Name           | Continent     | Region          | SurfaceArea | IndepYear | Population | LifeExpectancy | GNP        | GNPOld     | LocalName      | GovernmentForm                      | HeadOfState  | Capital | Code2 |
+------+--------+-------------+----------+------------+------+----------------+---------------+-----------------+-------------+-----------+------------+----------------+------------+------------+----------------+-------------------------------------+--------------+---------+-------+
| 1820 | London | CAN         | Ontario  |     339917 | CAN  | Canada         | North America | North America   |  9970610.00 |      1867 |   31147000 |           79.4 |  598862.00 |  625626.00 | Canada         | Constitutional Monarchy, Federation | Elisabeth II |    1822 | CA    |
|  456 | London | GBR         | England  |    7285000 | GBR  | United Kingdom | Europe        | British Islands |   242900.00 |      1066 |   59623400 |           77.7 | 1378330.00 | 1296830.00 | United Kingdom | Constitutional Monarchy             | Elisabeth II |     456 | GB    |
+------+--------+-------------+----------+------------+------+----------------+---------------+-----------------+-------------+-----------+------------+----------------+------------+------------+----------------+-------------------------------------+--------------+---------+-------+

-- 避免不同表的同名列必须加上表限定, 即使没有冲突, 也应该加上表限定, 提高可读性
select 
	city2.id,
	city2.name City,
	city2.district,
	city2.population CityPop,
	country2.name Country,
	country2.population countryPop,
	country2.capital
from 
	city2,
	country2 
where 
	city2.countrycode = country2.code;
	
	
-- 查询时也可以给表起别名, 好处时在使用表名时简单.
select 
	ci.id,
	ci.name City,
	ci.district,
	ci.population CityPop,
	co.name Country,
	co.population countryPop,
	co.capital
from 
	city2 as ci ,
	country2 co
where 
	ci.countrycode = co.code;


实表 -> 库中实实在在的表. DDL语句才能改
基表 -> from 后面的表
虚表 -> 结果集就是虚表.
	
-- from 总是最先执行. 所以表名一旦改变, 原名就不能用..
-- 下面的sql是错误的.
select 
	city2.id,
	city2.name City,
	city2.district,
	city2.population CityPop,
	country2.name Country,
	country2.population countryPop,
	country2.capital
from 
	city2 as ci ,
	country2 co
where 
	city2.countrycode = country2.code;
	
-- 查询所有国家的名称和国家的首都的名称
select
	co.name country ,
	city.name capital
from
	country co,
	city 
where 
	co.capital = city.id;
	
-- 查询所有国家及国家的所有城市	
select
	co.name country ,
	city.name city
from
	country co,
	city 
where 
	co.code = city.countrycode;

	
select 
	ci.id,
	ci.name City,
	ci.district,
	ci.population CityPop,
	co.name Country,
	co.population countryPop,
	co.capital
from 
	city2 as ci ,
	country2 co
where 
		ci.countrycode = co.code
	and 
		ci.population > 1000000;

--- 逗号联接本质就是内联接
select 
	ci.id,
	ci.name City,
	ci.district,
	ci.population CityPop,
	co.name Country,
	co.population countryPop,
	co.capital
from 
	city2 as ci ,
	country2 co
where 
		ci.countrycode = co.code -- 联接条件 
	and 
		ci.population > 1000000;
		
-- SQL99标准不建议用上面的写法, 表和表直接inner join, 联接条件使用on
-- where 和 on 在内联接中是可以互相代替的, 但是决不要这样做.
select 
	ci.id,
	ci.name City,
	ci.district,
	ci.population CityPop,
	co.name Country,
	co.population countryPop,
	co.capital
from 
	city2 as ci
inner join 
	country2 co 
on  
	ci.countrycode = co.code 
where  
	ci.population > 1000000;
	
	
--inner join 和 on 必须成对出现. 
--inner 可以省略
select 
	ci.id,
	ci.name City,
	ci.district,
	ci.population CityPop,
	co.name Country,
	co.population countryPop,
	co.capital,
	cl.language
from 
	city2 as ci
join 
	country2 co 
on  
	ci.countrycode = co.code 
join 
	countrylanguage cl 
on 
	co.code = cl.countrycode 
where  
	ci.population > 1000000;


-- 查询所有亚洲和欧洲国家名称及国家的首都
select 
	co.continent,
	co.name country,
	ci.name captial
from 
	country co 
join 
	city ci 
on 
	co.capital = ci.id 
where 
		co.continent = 'asia' 
	or 
		co.continent = 'europe'
order by 
	country;
	
	
复习 : 
C/S 模式

客户端要想连接服务器, 需要提供主机IP地址, 端口号, 用户名, 密码.
mysql -h127.0.0.1 -P3306 -uroot -p123456 world 
mysql --host=127.0.0.1 --port=3306 --user=root --password=123456 world 
	
查看当前库中的表
show tables;

跨库查看表
show tables from 库名;
	
查看表结构
describe 表名;
desc 库名.表名;

查看表的建表语句, 可以查看到列详细的信息
show create table 表名;
查看主键和外键约束的名称, 表使用存储引擎, 自增值, 默认字符集.

CREATE TABLE `city` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` char(35) NOT NULL DEFAULT '',
  `CountryCode` char(3) NOT NULL DEFAULT '',
  `District` char(20) NOT NULL DEFAULT '',
  `Population` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`),
  KEY `CountryCode` (`CountryCode`),
  CONSTRAINT `city_ibfk_1` FOREIGN KEY (`CountryCode`) REFERENCES `country` (`Code`)
) ENGINE=InnoDB AUTO_INCREMENT=4080 DEFAULT CHARSET=latin1;

"`" 符号称为飘号, 或反引号. 专门在数据库中用于包围数据库对象的名称
数据库对象 : 数据库, 表, 列, 索引, 约束, 事件, 函数, 存储过程......

存储引擎 : 决定表如何处理数据, 如果保存数据.........
InnoDB : 支持外键, 事务等高级特性

show engines;
+--------------------+---------+----------------------------------------------------------------+--------------+------+------------+
| Engine             | Support | Comment                                                        | Transactions | XA   | Savepoints |
+--------------------+---------+----------------------------------------------------------------+--------------+------+------------+
| InnoDB             | DEFAULT | Supports transactions, row-level locking, and foreign keys     | YES          | YES  | YES        |
| MRG_MYISAM         | YES     | Collection of identical MyISAM tables                          | NO           | NO   | NO         |
| MEMORY             | YES     | Hash based, stored in memory, useful for temporary tables      | NO           | NO   | NO         |
| BLACKHOLE          | YES     | /dev/null storage engine (anything you write to it disappears) | NO           | NO   | NO         |
| MyISAM             | YES     | MyISAM storage engine                                          | NO           | NO   | NO         |
| CSV                | YES     | CSV storage engine                                             | NO           | NO   | NO         |
| ARCHIVE            | YES     | Archive storage engine                                         | NO           | NO   | NO         |
| PERFORMANCE_SCHEMA | YES     | Performance Schema                                             | NO           | NO   | NO         |
| FEDERATED          | NO      | Federated MySQL storage engine                                 | NULL         | NULL | NULL       |
+--------------------+---------+----------------------------------------------------------------+--------------+------+------------+

MyISAM是mysql早期默认引擎 :
	速度超快, 不支持事务,外键
InnoDB 
	速度很快, 支持事务, 外键 
	
查看表中数据
*代表任意列
select * from 表名;

查询常量, 表达式, 变量, 函数调用.

select 200 from dual;
select 'abc' from dual;	
select 2*(3+5)-5*((3-2)*4);

变量 :
用户变量 : 必须先声明后使用
set @var1 = 200, @var2 = 'abc';
系统变量 :
show variables;
select @@变量名;

函数
select database(); 查看当前工作数据库
select version(); 查看服务器版本
select now(); 查看当前时间
	
select 
	'abc',
	29*9-3,
	now(),
	@@version,
	version(),
	country.*
from
	country
where 
	name = 'china';
	
执行顺序 : 
from -> join -> on -> where -> select -> order by 

-- 查询所有亚洲和非洲国家的名称和首都及官方语言, 用语言排序
+----------------+---------------------------------------------------------------------------------------+------+-----+---------+-------+
| Field          | Type                                                                                  | Null | Key | Default | Extra |
+----------------+---------------------------------------------------------------------------------------+------+-----+---------+-------+
| Code           | char(3)                                                                               | NO   | PRI |         |       |
| Name           | char(52)                                                                              | NO   |     |         |       |
| Continent      | enum('Asia','Europe','North America','Africa','Oceania','Antarctica','South America') | NO   |     | Asia    |       |
| Region         | char(26)                                                                              | NO   |     |         |       |
| SurfaceArea    | float(10,2)                                                                           | NO   |     | 0.00    |       |
| IndepYear      | smallint(6)                                                                           | YES  |     | NULL    |       |
| Population     | int(11)                                                                               | NO   |     | 0       |       |
| LifeExpectancy | float(3,1)                                                                            | YES  |     | NULL    |       |
| GNP            | float(10,2)                                                                           | YES  |     | NULL    |       |
| GNPOld         | float(10,2)                                                                           | YES  |     | NULL    |       |
| LocalName      | char(45)                                                                              | NO   |     |         |       |
| GovernmentForm | char(45)                                                                              | NO   |     |         |       |
| HeadOfState    | char(60)                                                                              | YES  |     | NULL    |       |
| Capital        | int(11)                                                                               | YES  |     | NULL    |       |
| Code2          | char(2)                                                                               | NO   |     |         |       |
+----------------+---------------------------------------------------------------------------------------+------+-----+---------+-------+
+-------------+----------+------+-----+---------+----------------+
| Field       | Type     | Null | Key | Default | Extra          |
+-------------+----------+------+-----+---------+----------------+
| ID          | int(11)  | NO   | PRI | NULL    | auto_increment |
| Name        | char(35) | NO   |     |         |                |
| CountryCode | char(3)  | NO   | MUL |         |                |
| District    | char(20) | NO   |     |         |                |
| Population  | int(11)  | NO   |     | 0       |                |
+-------------+----------+------+-----+---------+----------------+
+-------------+---------------+------+-----+---------+-------+
| Field       | Type          | Null | Key | Default | Extra |
+-------------+---------------+------+-----+---------+-------+
| CountryCode | char(3)       | NO   | PRI |         |       |
| Language    | char(30)      | NO   | PRI |         |       |
| IsOfficial  | enum('T','F') | NO   |     | F       |       |
| Percentage  | float(4,1)    | NO   |     | 0.0     |       |
+-------------+---------------+------+-----+---------+-------+
select 
	co.name country,
	ci.name capital,
	cl.language 
from 
	country co 
join 
	city ci  
on 
	co.capital = ci.id 
join 
	countrylanguage cl 
on 
	co.code = cl.countrycode 
where 
		co.continent in ('asia', 'africa')
	and 
		cl.isofficial = 'T'
order by 
	cl.language;


select 
	ci.name city,
	co.name country,
	ci.population cityPop,
	co.population countryPop
from 
	city2 ci 
join 
	country2 co 
on 
	ci.countrycode = co.code;

-- 查询国家及首都
select 
	co.name country,
	ci.name capital
from 
	country2 co 
join 
	city2 ci 
on 
	co.capital = ci.id;
	
内联接的特点 : 只保存符合联接条件的记录.
外联接的特点 : 除了联接条件为真的记录, 还要确保某张表的条件为假的记录. 保证某张表的数据的严格的完整性.
-- 内联没有左右之分, 而外联有, outer关键字可以省略
-- 左外联接保证左表数据完整
select 
	co.name country,
	ci.name capital
from 
	country2 co 
left outer join 
	city2 ci 
on 
	co.capital = ci.id;
	
-- 右外联接保证右表数据完整
select 
	co.name country,
	ci.name capital
from 
	country2 co 
right join 
	city2 ci 
on 
	co.capital = ci.id;

-- 查看所有国家的名称和首都, 如果没有首都也显示.
select 
	co.name,
	ci.name catital
from 
	country co 
left join 
	city ci 
on 
	co.capital = ci.id
order by 
	capital desc;
	

-- 查看所有城市及城市是否是首都, 如果是,显示国家
select 
	co.name,
	ci.name catital
from 
	country co 
right join 
	city ci 
on 
	co.capital = ci.id
order by 
	capital desc;
	
-- 查看所有国家的名称和官方语言, 如果没有官方语言的也显示.
-- 这是正确的做法
select 
	co.name,
	cl.*
from 
	country co 
left join 
	countrylanguage cl 
on 
		co.code = cl.countrycode
	and 
		cl.isofficial = 't' 
order by 
	co.name,
	cl.language desc;

-- 这个算法还有问题, 有待改进... 知道错了, 错在哪里???
select 
	co.name,
	cl.*
from 
	country co 
left join 
	countrylanguage cl 
on 
	co.code = cl.countrycode
where  
		cl.isofficial = 't'
	or 
		cl.isofficial is null 
order by 
	co.name,
	cl.language desc;
	
-- 所有国家及有官方语言的国家和官方语言, 238
select 
	co.name,
	cl.language,
	cl.isofficial
from 
	country co 
left join 
	countrylanguage cl 
on 
	co.code = cl.countrycode
where  
	cl.isofficial = 't'; 
	
-- 190个国家肯定有官方语言	
select 
	distinct co.name
from 
	country co 
left join 
	countrylanguage cl 
on 
	co.code = cl.countrycode
where  
	cl.isofficial = 't'; 
	
6个国家完全没有语言
238个国家有官方语言
190个国家肯定有官方语言

238 - 190 = 48个国家有多个官方语言
239 + 48 = 287是正确的记录数
239 - 6 - 190 = 233 - 190 = 43个国家没有官方语言
238 + 43 = 281是有语言并有可能没有官方语言的国家

244 == 238 + 6 , 滤掉的就是43个没有官方语言的国家
所以这个题用where绝对做不出来, 因为where条件中无法精确判断出某个国家是否没有官方语言.
select 
	co.name,
	cl.*
from 
	country co 
left join 
	countrylanguage cl 
on 
	co.code = cl.countrycode
where  
		cl.isofficial = 't'
	or 
		cl.isofficial is null 
order by 
	co.name,
	cl.language desc;	

-- 完全没有语言的国家	
select 
	co.name
from 
	country co 
left join 
	countrylanguage cl 
on 
	co.code = cl.countrycode
where  
	cl.language is null;
	

-- 所有国家,及官方语言的个数
select 
	co.name,
	count(cl.isofficial) ct
from 
	country co 
left join 
	countrylanguage cl 
on 
		co.code = cl.countrycode
	and  
		cl.isofficial = 'T'
group by 
	co.name
order by 
	ct desc;
	
单行函数, 是作用于每一条 记录的函数, 最后的结果是基表有多少记录就有多少
select 
	upper(name)
from 
	country;
	
select 
	concat('country:',name)
from 
	country;
	
-- 查询所有国家, 把国家代码和名称及大洲用逗号隔开连接成一个列, 显示这个列
select 
	concat(concat(concat(concat(code, ','),name), ','), continent)
from 
	country;

组函数, 作用于一组数据, 使用循环处理一组数组, 最后产生一个结果. 本质就是统计运算
组函数的结果仅仅是表示它的结果数据, 数据是谁的, 不能确定
组函数在使用时不可以和普通列混用.

count 计数

-- 统计表中的记录数
select 
	count(name)
from 
	country;
	
select 
	count(capital) -- 一组数据中如果有为null值的, 不参与统计
from 
	country;
	
--查看表中的真实记录数, 用*最好最快
select 
	count(*)
from 
	country;

avg 求平均值
select 
	avg(population)
from 
	country
where 
	continent = 'asia';

max 最大值 
min 最小值 
select 
	max(name),
	min(surfacearea) -- 
from 
	country;

sum 求和 

+----------------+---------------------------------------------------------------------------------------+------+-----+---------+-------+
| Field          | Type                                                                                  | Null | Key | Default | Extra |
+----------------+---------------------------------------------------------------------------------------+------+-----+---------+-------+
| Code           | char(3)                                                                               | NO   | PRI |         |       |
| Name           | char(52)                                                                              | NO   |     |         |       |
| Continent      | enum('Asia','Europe','North America','Africa','Oceania','Antarctica','South America') | NO   |     | Asia    |       |
| Region         | char(26)                                                                              | NO   |     |         |       |
| SurfaceArea    | float(10,2)                                                                           | NO   |     | 0.00    |       |
| IndepYear      | smallint(6)                                                                           | YES  |     | NULL    |       |
| Population     | int(11)                                                                               | NO   |     | 0       |       |
| LifeExpectancy | float(3,1)                                                                            | YES  |     | NULL    |       |
| GNP            | float(10,2)                                                                           | YES  |     | NULL    |       |
| GNPOld         | float(10,2)                                                                           | YES  |     | NULL    |       |
| LocalName      | char(45)                                                                              | NO   |     |         |       |
| GovernmentForm | char(45)                                                                              | NO   |     |         |       |
| HeadOfState    | char(60)                                                                              | YES  |     | NULL    |       |
| Capital        | int(11)                                                                               | YES  |     | NULL    |       |
| Code2          | char(2)                                                                               | NO   |     |         |       |
+----------------+---------------------------------------------------------------------------------------+------+-----+---------+-------+
-- 查询人均寿命最大和最小
select 
	max(LifeExpectancy),
	min(LifeExpectancy)
from
	country;
-- 查询亚洲国家人均寿命最大和最小
select 
	max(LifeExpectancy),
	min(LifeExpectancy)
from
	country
where 
	continent = 'asia';
	
	
group by 作用是把结果集中有相同数据的记录合到一个组中, 不同的数据就有不同的组, 再在组内进行统计运算
有group by, 必须要把分组依据的列放在select中
select 
	continent,
	--name, 逻辑上不通, 它是代表个体信息的列.
	max(population),
	min(gnp)
from 
	country 
group by 
	continent;


-- 这种分组不好, 效果和普通查询一样	
select 
	continent, 
	max(population), 
	min(gnp), 
	name
from 
	country
group by -- 后面有两个列的话, 会以2个列的组合值为分级依据
	continent, 
	name;


如果出现"每", "各" 需求时要分组

SELECT   
	department_id, 
	AVG(salary)
FROM     
	company.employees
GROUP BY 
	department_id ;

-- 查询国家表中各地区的人均寿命和最大国土面积.
select 
	region,
	continent,
	avg(LifeExpectancy),
	max(surfaceArea)
from 
	country 
group by
	continent,
	region;


-- 对于分组结果的过滤, 必须使用having
select 
	continent,
	max(population) maxPop,
	min(gnp)
from 
	country 
group by 
	continent
having
	max(population) > 100000000;
	
--------------------------------------------------
select 
	continent,
	max(population) maxPop,
	min(gnp)
from 
	country 
group by 
	continent
having
	maxPop > 100000000; -- 在having中是否可以使用列的别名????
	
-- 错误!!!
select 
	continent,
	max(population),
	min(gnp)
from 
	country 
where 
	max(population) > 100000000
group by 
	continent
	
查询 : 
1) from 确定第一基表
2) join 确定第二基表
3) join 是内还是外
4) 有join必须要有on 
5) 如果还有, 继续join和on 
6) where 针对以上的大基表进行基本的行过滤
7) 是否要分组, 如果要分组, 使用group by, 分组依据的列很重要.
8) 第一时间把分组依据的列放在select后面
9) 继续考虑要选择哪些列..
10) 产生了虚表后, 如果还相再进一步进行过滤,必须使用having 
11) 如果要排序, 再排序 order by 


列出所有在超过10个国家中使用的语言。
select 
	language,
	count(*)
from 
	countrylanguage 
group by 
	language
having 
	count(*) > 10;
		
查询每个国家各有多少个城市和每个国家的城市总人口数
select 
	countrycode,
	count(*),
	sum(population)
from 
	city 
group by 
	countrycode;

列出不同的国家(country code)有居民超过7,000,000的城市， 它们有多少？
select 
	countrycode,
	count(*)
from 
	city 
where 
	population > 7000000
group by 
	countrycode;

查询中国的每个省的总城市数量和总人口数
select 
	district,
	count(*),
	sum(population)
from 
	city 
where 
	countrycode = 'chn'
group by 
	district
order by 
	sum(population);
	
-- 逻辑对不对??
select 
	countrycode,
	district,
	count(*),
	sum(population)
from 
	city 
group by 
	countrycode,
	district
having 
	countrycode = 'chn'
order by 
	sum(population);


每个国家各有多少种语言
select 
	countrycode,
	count(*)
from 
	countrylanguage 
group by 
	countrycode;
	
Sweden国家说的是什么语言？
select 
	co.name,
	cl.language,
	cl.isofficial
from 
	countrylanguage cl 
join 
	country  co 
on 
	cl.countrycode = co.code 
where 
	co.name = 'sweden';

哪些国家没有列出任何使用语言？
select 
	co.name,
	cl.language
from 
	country co 
left join 
	countrylanguage cl 
on 
	co.code = cl.countrycode 
where 
	cl.language is null;
	
	
select 
	co.name,
	count(cl.language) ct 
from 
	country co 
left join 
	countrylanguage cl 
on 
	co.code = cl.countrycode 
group by 
	co.name 
having 
	ct = 0;

**列出在城市表中80%人口居住在城市的国家
select
	co.code,
	co.name,
	sum(ci.population) / co.population as rate -- 国家人口对于所有城市来说是群体概念
from 
	city ci 
join 
	country  co 
on 
	ci.countrycode = co.code 
group by 
	co.code  -- 主键
having 
	rate > 0.8;


查询哪些国家的语言超过10种	, 这个题能否用where过滤??
select 
	co.name,
	count(cl.language) ct 
from 
	country co 
left join 
	countrylanguage cl 
on 
	co.code = cl.countrycode 
group by 
	co.name 
having 
	ct > 10;
	
	
	
-- 可以在group by后面加上列, 表明它是群体概念
-- 也可以通过一个不起作用的组函数来使得它变成群概念	

关键看分组依据... 
中国, 12亿, 北京 5000000 
中国, 12亿, 上海 7000000 
中国, 12亿, 成都 2000000
以于以上3条记录来说, 中国和中国相关的所有数据都是群体, 只有城市信息是个体

日本, 5亿, 东京 6000000
日本, 5亿, 仙台 2000000 
日本, 5亿, 京都 100000 
以上3条记录的群体概念就是日本和日本相关所有数据. 

select
	co.code,
	sum(ci.population) / co.population
from
	country co
join
	city ci
on
	co.code = ci.countrycode
group by
	co.code,
	co.population
having
	sum(ci.population) / co.population > 0.8;
	

子查询解决的问题是 : 需要多步查询完成的, 想用一个查询完成
--查看全球平均寿命最高的国家
select 
	name,
	continent,
	LifeExpectancy
from 
	country 
where 
	LifeExpectancy = (select max(LifeExpectancy) from country)
	
	
--查看哪些国家人口是超过全球平均人口的
select 
	name,
	population
from 
	country 
where 
	population > (select avg(population) from country);

--查看哪些国家面积小于英国
select 
	name,
	surfaceArea
from 
	country 
where 
	surfaceArea <= (select surfaceArea from country where code = 'gbr')
order by 
	surfaceArea;
	
-- 查询各大洲中那些超过本洲平均人口的国家.
select 
	co1.name,
	co1.continent,
	co1.population,
	co2.avgPop
from 
	country co1 
join 
	(select 
		continent,
		avg(population) avgPop
	from 
		country 
	group by 
		continent
	) as co2 
on 
	co1.continent = co2.continent
where 
	co1.population > co2.avgPop 

-- 查看哪些中国城市人口是大于本省平均人口的城市

CRUD -- DML 数据操作语言, 主要处理数据, 构造事务的主体(一个事务是由多个DML构成)
create, alter, drop -- DDL 数据定义语言, 主要处理数据库对象(库,表,列,约束....), 非正规事务终结者
grant revoke, commit, rollback -- DCL 数据控制语言.  正规事务终结者


创建数据库
create database if not exists 数据库名 charset 字符集;
create database if not exists school charset utf8;
create database `create` charset utf8; -- 强烈不建议使用关键字作为库名


修改数据库 : 只能修改字符集
alter database school charset gbk;

丢弃数据库 
drop database if exists school;

创建表 :
1) 全新方式建表 :
	数据类型 :
		int, bigint, char, varchar, longtext, double, decimal, date, datetime
	
	create table if not exists 表名 (
		列名1 数据类型1(长度) 其他选项(列级约束),
		列名2 数据类型2(长度) 其他选项,
		.......,
		表级约束1,
		表级约束2,
		......
	) engine 引擎 charset 字符集;
	
	create table if not exists teacher (
		id int auto_increment,
		name varchar(20) not null,
		age int,
		salary double,
		primary key(id)
	);
	
	insert into teacher (
		id,
		name,
		age,
		salary 
	) values (
		1,
		'龙哥',
		20,
		20000
	);
	
	insert into teacher (
		id,
		name,
		age,
		salary 
	) values (
		1, -- 这条记录的id值和已有数据冲突了, 也无法插入
		'宁姐',
		21,
		30000
	);
	
	insert into teacher (
		id,
		name,
		age,
		salary 
	) values (
		null, -- 如果主键没有自增, 添加此条记录就失败
		'宁姐',
		21,
		30000
	);
	
	主键 : 是表级约束, 主键表示的列是非空唯一
	
	create table if not exists classes (
		id int auto_increment, -- 自增的列必须是主键
		name varchar(20),
		begindate date,
		master int,
		room char(3),
		primary key(id)
	);
	
	建表 : student 有id, name, phone, class_id, address
	
	create table if not exists student (
		id int auto_increment,
		name varchar(20),
		phone varchar(20),
		class_id int,
		address varchar(100),
		primary key(id)
	);
	
2) : 基于子查询建表 : 复制的新表不包含原表的约束... 不能完成表的克隆.
	create table country2 as select * from world.country where continent = 'asia';
	create table country3 as select * from world.country;
	
3) : 完全复制表结构的建表, 包含约束. 但是没有数据.
	create table 新表 like 已有表;
	
修改表 : alter table 表名 支持若干子句

添加新列
alter table 表名 
add 新列 新数据类型(长度) 其他选项;

alter table teacher 
add column phone varchar(20) not null; -- 在最后新加列

alter table teacher 
add column gender enum('男', '女') default '男' after name; -- 在指定列后面添加新列

alter table teacher 
add column personId varchar(50) first; -- 在最前面添加新列

修改列(数据类型等) : 
alter table 表名 
modify 列名 新数据类型(新长度) 新其他选项

alter table teacher 
modify salary int;

修改列(列名和其他)
alter table 表名 
change 老列名 新列名 新数据类型(新长度) 新其他选项;

alter table teacher
change phone mobile varchar(20) not null;
	
丢弃列 -- 引发列对应的数据全部丢弃. 不可逆.
alter table 表名 
drop column 列名;

alter table teacher 
drop column personId;

-- 给学生表的name后面添加新列age, 在最后再添加一列 gender 
-- 把学生的phone列改成mobile
	
	




