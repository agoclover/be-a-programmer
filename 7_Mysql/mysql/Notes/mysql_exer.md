# 20200428 - 作业

## D1L1 基本 SQL-SELECT 语句

1.	下面的语句是否可以执行成功  

```sql
select last_name , job_id , salary as sal
from employees; 
```
答: 可以执行成功.

2. 下面的语句是否可以执行成功  

```sql
select  *  from employees; 
```

答: 可以成功.

3. sql 乘法练习

```sql
select
	employee_id,
	last_name,
	salary * 12 "Annual Salary"
from employees;
```

4.	显示表 departments 的结构，并查询其中的全部数据

```sql
> describe departments;
+-----------------+------------+------+-----+---------+----------------+
| Field           | Type       | Null | Key | Default | Extra          |
+-----------------+------------+------+-----+---------+----------------+
| department_id   | int        | NO   | PRI | NULL    | auto_increment |
| department_name | varchar(3) | YES  |     | NULL    |                |
| manager_id      | int        | YES  |     | NULL    |                |
| location_id     | int        | YES  | MUL | NULL    |                |
+-----------------+------------+------+-----+---------+----------------+

> desc departments;
```

二者功能相同.

5.	显示出表 employees 中的全部job_id（不能重复）

```sql
SELECT
		DISTINCT
				jon_id
FROM 
		employees;
```

6.	显示出表 employees 的全部列，各个列之间用逗号连接，列头显示成 OUT_PUT

```sql
SELECT CONCAT(employee_id,",",first_name,",",last_name,",",email,",
",phone_number,",",job_id,",",salary,",",IFNULL(commission_pct,0),",",
IFNULL(manager_id,0),",",department_id) out_put 
FROM employees;
```

## D1L2 过滤和排序数据

```SQL
mysql> DESC EMPLOYEES;
+----------------+--------------+------+-----+---------+----------------+
| Field          | Type         | Null | Key | Default | Extra          |
+----------------+--------------+------+-----+---------+----------------+
| employee_id    | int          | NO   | PRI | NULL    | auto_increment |
| first_name     | varchar(20)  | YES  |     | NULL    |                |
| last_name      | varchar(25)  | YES  |     | NULL    |                |
| email          | varchar(25)  | YES  |     | NULL    |                |
| phone_number   | varchar(20)  | YES  |     | NULL    |                |
| job_id         | varchar(10)  | YES  | MUL | NULL    |                |
| salary         | double(10,2) | YES  |     | NULL    |                |
| commission_pct | double(4,2)  | YES  |     | NULL    |                |
| manager_id     | int          | YES  |     | NULL    |                |
| department_id  | int          | YES  | MUL | NULL    |                |
+----------------+--------------+------+-----+---------+----------------+
```

查询工资大于12000的员工姓名和工资

```sql
SELECT
		CONCAT(first_name, ' ', last_name) name,
		salary
FROM
		employees
WHERE
		salary > 12000;

+-------------------+----------+
| name              | salary   |
+-------------------+----------+
| Steven King       | 24000.00 |
| Neena Kochhar     | 17000.00 |
| Lex De Haan       | 17000.00 |
| John Russell      | 14000.00 |
| Karen Partners    | 13500.00 |
| Michael Hartstein | 13000.00 |
+-------------------+----------+
```

查询员工号为176的员工的姓名和部门号

```sql
SELECT
		CONCAT(first_name, ' ', last_name) name,
		department_id
FROM
		employees
WHERE
		employee_id = 176;
		
+-----------------+---------------+
| name            | department_id |
+-----------------+---------------+
| Jonathon Taylor |            80 |
+-----------------+---------------+
```

选择工资不在5000到12000的员工的姓名和工资

```sql
select
		concat(first_name, " ", last_name) name,
		salary
from
		employees
where
			salary < 5000
		or 
			salary > 12000;
```

选择在20或50号部门工作的员工姓名和部门号

```sql
select
		concat(first_name, ' ', last_name) name,
		department_id
from
		employees
where
				department_id >= 20
		AND
				department_id <= 50;
```

选择公司中没有管理者的员工姓名及job_id

```sql
select
		first_name,
		last_name,
		job_id
from
		employees
where
		manager_id is null;
```

选择公司中有奖金的员工姓名，工资和奖金级别

`commission_pct` 为员工提成.

```sql
select
		first_name,
		last_name,
		salary,
		commission_pct
from
		employees
where
		commission_pct is not null;
```

选择员工姓名的第三个字母是 a 的员工姓名

```sql
select
		first_name,
		last_name
from
		employees
where
		first_name like '__a%';
```

选择姓名中有字母 a 和 e 的员工姓名

```sql
select last_name from employees
where last_name like '%a%e%' or last_name like '%e%a%';
```

或

```sql
select last_name from employees
where last_name like '%a%' and last_name like '%e%';
```



## D1L3 多表查询

显示所有员工的姓名，部门号和部门名称

```sql
select e.first_name, e.last_name, e.department_id, d.department_name
from employees e inner join departments d on e.department_id = d.department_id;
```

 查询 90 号部门员工的 job_id 和 90 号部门的 location_id

```sql
select e.job_id, d.location_id
from employees e inner join departments d on e.department_id = d.department_id
where e.department_id = 90;
```

选择所有有奖金的员工的 last_name , department_name , location_id , city

```sql
select e.last_name, d.department_name, d.location_id, l.city
from employees e 
inner join departments d on e.department_id = d.department_id
inner join locations l on d.location_id = l.location_id
where e.commission_pct is not null;
```

选择city在Toronto工作的员工的last_name , job_id , department_id , department_name 

```sql
select e.last_name, e.job_id, e.department_id, d.department_name
from employees e
inner join departments d on e.department_id = d.department_id
inner join locations l on d.location_id = l.location_id
where l.city like 'Toronto';
```

选择指定员工的姓名，员工号，以及他的管理者的姓名和员工号，结果类似于下面的格式

| **employees** | **Emp#** | **manager** | **Mgr** |
| ------------- | -------- | ----------- | ------- |
| **kochhar**   | **101**  | **king**    | **100** |

```sql
select e.last_name employees, e.employee_id emp, m.last_name manager, m.employee_id
from employees e
inner join employees m on e.manager_id = m.employee_id;
```



## D1L4 单行函数

显示系统时间(注：日期+时间)

```sql
SELECT NOW();

2020-04-28 18:55:33
```

查询员工号，姓名，工资，以及工资提高百分之 20% 后的结果（new salary）

```sql
SELECT
employee_id,
CONCAT(first_name, ' ', last_name) name,
salary,
1.2 * salary "new salary"
FROM
employees;
```

将员工的姓名按**首字母**排序，并写出姓名的长度（length）

```sql
SELECT CONCAT(first_name, ' ', last_name) name, LENGTH(CONCAT(first_name, ' ', last_name)) -1 length
FROM employees;
```

做一个查询，产生下面的结果

```sql
SELECT
CONCAT(last_name,' earns ', salary, ' monthly  but wants ', salary * 3)
FROM
employees;
```

<last_name> earns <salary> monthly but wants <salary*3>

| **Dream  Salary**                             |
| --------------------------------------------- |
| **King  earns 24000 monthly but wants 72000** |

## D2L5 分组函数

where子句可否使用组函数进行过滤? 

```sql
-- 不可以, 先执行 where, 再执行 group by
```

查询公司员工工资的最大值，最小值，平均值，总和

```sql
SELECT
MAX(salary),
MIN(salary),
AVG(salary),
SUM(salary)
FROM
employees;
```

查询各job_id的员工工资的最大值，最小值，平均值，总和

```sql
SELECT
job_id,
MAX(salary),
MIN(salary),
AVG(salary),
SUM(salary)
FROM
employees
GROUP BY
job_id;
```

选择具有各个job_id的员工人数

```sql
SELECT
job_id,
COUNT(*)
FROM
employees
GROUP BY
job_id;
```

查询员工最高工资和最低工资的差距（DIFFERENCE）

```sql
SELECT
MAX(salary) - MIN(salary) DIFFERENCE
FROM
employees;
```

查询各个管理者手下员工的最低工资，其中最低工资不能低于6000，没有管理者的员工不计算在内

```sql
SELECT
manager_id,
MIN(salary)
FROM
employees
WHERE
manager_id is not null
GROUP BY
manager_id
HAVING MIN(salary) >= 6000;
```

查询所有部门的名字，location_id，员工数量和工资平均值

```sql
SELECT
d.department_name,
l.location_id,
COUNT(*),
AVG(salary)
FROM
employees e
INNER JOIN
departments d
ON
e.department_id = d.department_id
INNER JOIN
locations l
ON
d.location_id = l.location_id
GROUP BY
e.department_id;
```

## D2L6 子查询

查询和Zlotkey相同部门的员工姓名和工资

```sql
SELECT first_name, last_name, salary
FROM employees
WHERE department_id = (SELECT department_id FROM employees WHERE first_name = 'Zlotkey' or last_name = 'Zlotkey');
```

查询工资比公司平均工资高的员工的员工号，姓名和工资

```sql
SELECT employee_id, first_name, last_name, salary
FROM employees
WHERE salary > 
(SELECT AVG(salary) FROM employees);
```

查询各部门中工资比本部门平均工资高的员工的员工号, 姓名和工资

```sql
SELECT e1.employee_id, e1.last_name, e1.salary, e1.department_id, e2.avg
FROM employees e1
INNER JOIN 
(SELECT department_id, AVG(salary) avg FROM employees GROUP BY department_id) e2
on e1.department_id = e2.department_id
WHERE e1.salary > e2.avg;
```

查询和姓名中包含字母u的员工在相同部门的员工的员工号和姓名

```sql
SELECT employee_id, first_name, last_name
FROM employees
WHERE department_id in 
(SELECT DISTINCT department_id FROM employees WHERE CONCAT(first_name,last_name) LIKE '%u%');
```

查询在部门的location_id为1700的部门工作的员工的员工号

```sql
SELECT employee_id, department_id
FROM employees
WHERE department_id in
(SELECT department_id FROM departments WHERE location_id = 1700);
```

查询管理者是King的员工姓名和工资

```sql
SELECT last_name, salary, manager_id
FROM employees
WHERE manager_id in 
(SELECT employee_id FROM employees WHERE last_name = 'King');
```

## D2L7 创建和管理表

```sql
create database if not exists exer;
```

创建表 dept1

| name | Null? | type        |
| ---- | ----- | ----------- |
| id   |       | int(7)      |
| name |       | varchar(25) |

```sql
create table if not exists dept1(
id int(7),
name varchar(25)
);
```

将表departments中的数据插入新表 dept2 中

```sql
create table if not exists dept2
as select * from company.departments;
```

创建表 emp5

| name       | Null? | type         |
| ---------- | ----- | ------------ |
| id         |       | int(7)       |
| First_name |       | Varchar (25) |
| Last_name  |       | Varchar(25)  |
| Dept_id    |       | int(7)       |

```sql
create table if not exists emp5(
id int(7),
first_name varchar(25),
last_name varchar(25),
dept_id int(7));
```

将列 Last_name 的长度增加到50

```sql
alter table emp5
modify last_name varchar(50);
```

根据表 employees 创建 employees2 (有所有原表的格式信息但没有数据)

```sql
create table if not exists employees2 like company.employees;
```

删除表 emp5

```sql
drop table if exists emp5;
```

将表 employees2 重命名为 emp5

```sql
alter table employees2 rename emp5;
```

在表 dept1 和 emp5 中添加新列 test_column，并检查所作的操作

```sql
alter table dept1 add test_column varchar(5);
alter table emp5 add test_column varchar(5);
```

直接删除表 emp5 中的列 dept_id

```sql
alter table emp5 drop column dept_id;
```



# 20200428 - 课堂练习

```sql
-- 列出所有在超过10个国家中使用的语言
SELECT
cl.`Language`,
COUNT(co.`Name`)
FROM
Country co
INNER JOIN
CountryLanguage cl
ON
co.code = cl.CountryCode
GROUP BY cl.`Language`
HAVING COUNT(co.`Name`) > 10;
-- 查询每个国家各有多少个城市和每个国家的城市总人口数
SELECT
co.`Name`,
COUNT(ci.ID),
SUM(ci.Population)
FROM
Country co
INNER JOIN
City ci
ON
co.`Code` = ci.CountryCode
GROUP BY co.`Name`
ORDER BY co.`Name`;
-- 列出不同的国家(country code)有居民超过7,000,000的城市， 它们有多少？
SELECT
co.`Name`,
count(*)
FROM
Country co
INNER JOIN
City ci
ON
co.`Code` = ci.CountryCode
WHERE
ci.Population > 7000000
GROUP BY co.`Code`;
--查询中国的每个省的总城市数量和总人口数
SELECT
District province,
COUNT(*) totalCityAmount,
SUM(Population) totoalPopulation
FROM
City
WHERE CountryCode = 'CHN'
GROUP BY District;
-- 每个国家各有多少种语言
SELECT
co.`Name`,
COUNT(`Language`) "使用语言的数量"
FROM
Country co
LEFT JOIN
CountryLanguage cl
ON
co.`Code` = cl.CountryCode
GROUP BY co.`Name`, co.`Code`
ORDER BY 使用语言的数量;
-- Sweden国家说的是什么语言？
SELECT `Language`
FROM
CountryLanguage
WHERE
CountryCode = (SELECT `Code` FROM Country WHERE name = 'Sweden')
and IsOfficial = 'T';
-- 哪些国家没有列出任何使用语言？
SELECT
co.`Name`
FROM
Country co
LEFT JOIN
CountryLanguage cl
on co.`Code` = cl.CountryCode
WHERE cl.`Language` is null;
-- **列出在城市表中80%人口居住在城市的国家
SELECT
co.`Name` 国家,
SUM(ci.Population) 城市人口,
co.Population 总人口,
SUM(ci.Population) / co.Population 城市人口百分比
FROM
Country co
INNER JOIN
City ci
ON
co.`Code` = ci.CountryCode
GROUP BY
co.code, -- 主键
HAVING
0.8 * co.Population < SUM(ci.Population);
-- 查询哪些国家的语言超过10种
SELECT
co.`Name`,
COUNT(`Language`)
FROM
Country co
INNER JOIN
CountryLanguage cl
ON
co.`Code` = cl.CountryCode
GROUP BY
co.`Name`
HAVING
COUNT(`Language`) > 10;

-- 查询各大洲中比本大洲平均人口高的国家
SELECT
co.name,
co.Continent,
co.Population,
co2.avgpopu
FROM
Country co
inner JOIN
(SELECT Continent, AVG(Population) avgpopu from Country GROUP BY Continent) co2
ON
co.Continent = co2.Continent
WHERE
co.Population > co2.avgpopu
ORDER BY co.Continent;

-- 查看哪些中国城市人口是大于本省平均人口的城市
SELECT
ci1.`Name` 城市,
ci1.Population 人口,
ci1.District 省份,
ci2.avgpop 本省平均人口
FROM
City ci1
INNER JOIN
(SELECT District, AVG(Population) avgpop FROM City WHERE CountryCode = 'CHN' GROUP BY District) ci2
ON
ci1.District = ci2.District
and ci1.CountryCode = 'CHN'
WHERE
ci1.Population > ci2.avgpop;
```



# 20200429 - 晨测

1 哪些国家没有列出任何使用语言？(2种做法)

```sql
SELECT
co.name
FROM Country co
LEFT JOIN CountryLanguage cl ON co.`Code` = cl.CountryCode
WHERE cl.`Language` is null;

SELECT `Name` FROM Country
WHERE `Code` not in (SELECT DISTINCT CountryCode FROM CountryLanguage);
```

2 列出在城市表中80%人口居住在城市的国家

```sql
SELECT
co.`Name`,
co.Population,
co2.citypop,
co2.citypop / co.Population
FROM Country co
INNER JOIN (SELECT CountryCode, SUM(Population) citypop FROM City GROUP BY CountryCode) co2
on co.`Code` = co2.CountryCode
WHERE co2.citypop > 0.8 * co.Population;
```

3 查询人均寿命最长和最短的国家的名称及人均寿命

```sql
SELECT `Name`, LifeExpectancy
FROM Country
where LifeExpectancy in (SELECT MAX(LifeExpectancy) max, MIN(LifeExpectancy) min FROM Country) li;

SELECT `Name`, LifeExpectancy FROM Country
where LifeExpectancy in 
((SELECT MAX(LifeExpectancy) FROM Country),
(SELECT MIN(LifeExpectancy) FROM Country));
```

4 查询所有国家的名称和首都名称和官方语言,没有首都和官方语言的也要显示.

```sql
SELECT co.`Name`, ci.`Name`, cl.`Language`
FROM Country co
LEFT JOIN City ci on co.`Code` = ci.CountryCode
LEFT JOIN CountryLanguage cl on co.`Code` = cl.CountryCode and cl.IsOfficial = 'T';
```

5 查询亚洲国家的各省的总城市数量和平均人口数, 哪些平均人口大于50万, 降序显示总城市数量.]

```sql
SELECT CountryCode, District, COUNT(*) "总城市数量", AVG(Population) "平均人口", AVG(Population) > 500000 "平均人口是否大于50万"
FROM City
WHERE CountryCode in (SELECT `Code` from Country WHERE Continent = 'Asia')
GROUP BY CountryCode, District
ORDER BY 总城市数量 desc;
```

