# MySQL练习题

## 1. 组合两个表

需求：编写一个 SQL 查询，对两表进行关联，展示列为：
FirstName, LastName, City, State

展示效果：

| FirstName | LastName | City          | State    |
| --------- | -------- | ------------- | -------- |
| Allen     | Wang     | New York City | New York |

```mysql
Create table Person (PersonId int, FirstName varchar(255), LastName varchar(255));

Create table Address (AddressId int, PersonId int, City varchar(255), State varchar(255));

insert into Person (PersonId, LastName, FirstName) values (1, 'Wang', 'Allen');
insert into Address (AddressId, PersonId, City, State) values (1, 1, 'New York City', 'New York');
```

最终SQL:

```mysql
select
     p.FirstName,
     p.LastName,
     a.City,
     a.State
from 
     Person as p 
left join 
     Address as a 
on 
     p.PersonId = a.PersonId;
```

## 2. 第二高的薪水

**需求二**：编写一个 SQL 查询，获取 Employee 表中第二高的薪水（Salary）。如果不存在第二高的薪水，那么查询应返回 null。

展示效果：

| SecondHighestSalary |
| ------------------- |
| 200                 |

建表语句：

```mysql
Create table If Not Exists Employee (Id int, Salary int);

insert into Employee (Id, Salary) values (1, 100);
insert into Employee (Id, Salary) values (2, 200);
insert into Employee (Id, Salary) values (3, 300);
```

最终SQL:

```mysql
方法一：
select (
	     select 
               DISTINCT Salary
	     from  
               Employee
	     order by
               Salary DESC
	     limit 1,1
      )  as SecondHighestSalary;
方法二：
select 
       max(Salary) as SecondHighestSalary 
from 
       Employee
where 
       Salary < (select
                       max(Salary) 
                 from 
                       Employee
                );
```

提示：LIMIT 子句可以被用于强制 SELECT 语句返回指定的记录数。LIMIT 接受一个或两个数字参数。参数必须是一个整数常量。如果给定两个参数，第一个参数指定第一个返回记录行的偏移量，第二个参数指定返回记录行的最大数目。



**需求二**：编写一个 SQL 查询，获取 Employee 表中第 n 高的薪水（Salary）。

```msyql
方法一：
CREATE FUNCTION getNthHighestSalary(N INT) RETURNS INT
BEGIN
  SET n = N-1;
  RETURN (     
  SELECT DISTINCT Salary FROM Employee ORDER BY Salary DESC LIMIT n,1
  );
END

select getNthHighestSalary(2) ;

方案二：
CREATE FUNCTION getNthHighestSalary(N INT) RETURNS INT
BEGIN
  RETURN (     
  SELECT  IF(count<N,NULL,min) 
  FROM
    (SELECT MIN(Salary) AS min, COUNT(1) AS count
    FROM
      (SELECT DISTINCT Salary
      FROM Employee ORDER BY Salary DESC LIMIT N) AS a
    ) as b
  );
END
```

## 3. 分数排名

需求：编写一个 SQL 查询来实现分数排名。如果两个分数相同，则两个分数排名（Rank）相同。请注意，平分后的下一个名次应该是下一个连续的整数值。换句话说，名次之间不应该有“间隔”。

展示效果：

| Score | Rank |
| ----- | ---- |
| 4.00  | 1    |
| 4.00  | 1    |
| 3.85  | 2    |
| 3.65  | 3    |
| 3.65  | 3    |
| 3.50  | 4    |

```mysql
Create table If Not Exists Scores (Id int, Score DECIMAL(3,2));

insert into Scores (Id, Score) values (1, 3.5);
insert into Scores (Id, Score) values (2, 3.65);
insert into Scores (Id, Score) values (3, 4.0);
insert into Scores (Id, Score) values (4, 3.85);
insert into Scores (Id, Score) values (5, 4.0);
insert into Scores (Id, Score) values (6, 3.65);
```

最终SQL:

```mysql
select 
      a.Score as score , 
      (select 
              count(distinct b.Score) 
       from 
              Scores b 
       where 
              b.Score >=a.Score) as rank
from 
     Scores a 
order by 
     Score DESC;
```

## 4. 连续出现的数字

需求：编写一个 SQL 查询，查找所有至少连续出现三次的数字。

展示效果：

| ConsecutiveNums |
| --------------- |
| 1               |

```mysql
Create table If Not Exists Logs (Id int, Num int);

insert into Logs (Id, Num) values (1, 1);
insert into Logs (Id, Num) values (2, 1);
insert into Logs (Id, Num) values (3, 1);
insert into Logs (Id, Num) values (4, 2);
insert into Logs (Id, Num) values (5, 1);
insert into Logs (Id, Num) values (6, 2);
insert into Logs (Id, Num) values (7, 2);
```

最终SQL:

```mysql
SELECT *
FROM
    Logs l1,
    Logs l2,
    Logs l3
WHERE
    l1.Id = l2.Id - 1 AND l1.Num = l2.Num
    AND l2.Id = l3.Id - 1 AND l2.Num = l3.Num;
```

## 5. 超过经理收入的员工

需求：Employee 表包含所有员工，他们的经理也属于员工。每个员工都有一个 Id，此外还有一列对应员工的经理的 Id。

数据样式：

| Id   | Name  | Salary | ManagerId |
| ---- | ----- | ------ | --------- |
| 1    | Joe   | 70000  | 3         |
| 2    | Henry | 80000  | 4         |
| 3    | Sam   | 60000  | null      |
| 4    | Max   | 90000  | null      |

展示效果：

| Employee |
| -------- |
| Joe      |

```mysql
create table If Not Exists Employee (Id int, Name varchar(255), Salary int, ManagerId int);

insert into Employee (Id, Name, Salary, ManagerId) values (1, 'Joe', 70000, 3);
insert into Employee (Id, Name, Salary, ManagerId) values (2, 'Henry', 80000, 4);
insert into Employee (Id, Name, Salary, ManagerId) values (3, 'Sam', 60000, null);
insert into Employee (Id, Name, Salary, ManagerId) values (4, 'Max', 90000, null);
```

最终SQL:

```mysql
SELECT
     a.NAME AS Employee
FROM 
     Employee AS a 
JOIN 
     Employee AS b
ON 
     a.ManagerId = b.Id 
AND 
     a.Salary > b.Salary;
```

## 6. 查找重复的邮箱

需求：编写一个 SQL 查询，查找 Person 表中所有重复的电子邮箱。

展示效果：

| Email   |
| ------- |
| a@b.com |

```mysql
Create table If Not Exists Person (Id int, Email varchar(255))

insert into Person (Id, Email) values (1, 'a@b.com')
insert into Person (Id, Email) values (2, 'c@d.com')
insert into Person (Id, Email) values (3, 'a@b.com')
```

最终SQL:

```mysql
select
      Email
from 
      Person
group by 
      Email
having 
      count(Email) > 1;
```

## 7. 从不订购的客户

需求：某网站包含两个表，Customers 表和 Orders 表。编写一个 SQL 查询，找出所有从不订购任何东西的客户。

展示效果：

| Customers |
| --------- |
| Henry     |
| Max       |

```mysql
Create table If Not Exists Customers (Id int, Name varchar(255));
Create table If Not Exists Orders (Id int, CustomerId int);

insert into Customers (Id, Name) values (1, 'Joe');
insert into Customers (Id, Name) values (2, 'Henry');
insert into Customers (Id, Name) values (3, 'Sam');
insert into Customers (Id, Name) values (4, 'Max');

insert into Orders (Id, CustomerId) values (1, 3);
insert into Orders (Id, CustomerId) values (2, 1);

```

最终SQL:

```mysql
select 
     customers.name as 'Customers'
from 
     customers
where 
     customers.id not in(
        select
              customerid 
        from 
              orders
     );
```

## 8. 部门工资最高的员工

**需求一**：编写一个 SQL 查询，找出每个部门工资最高的员工。例如，根据上述给定的表格，Max 在 IT 部门有最高工资，Henry 在 Sales 部门有最高工资。

展示效果：

| Department | Employee | Salary |
| ---------- | -------- | ------ |
| IT         | Jim      | 90000  |
| IT         | Max      | 90000  |
| Sales      | Henry    | 80000  |

```mysql
Create table If Not Exists Employee (Id int, Name varchar(255), Salary int, DepartmentId int);
Create table If Not Exists Department (Id int, Name varchar(255));

insert into Employee (Id, Name, Salary, DepartmentId) values (1, 'Joe', 70000, 1);
insert into Employee (Id, Name, Salary, DepartmentId) values (2, 'Jim', 90000, 1);
insert into Employee (Id, Name, Salary, DepartmentId) values (3, 'Henry', 80000, 2);
insert into Employee (Id, Name, Salary, DepartmentId) values (4, 'Sam', 60000, 2);
insert into Employee (Id, Name, Salary, DepartmentId) values (5, 'Max', 90000, 1);
insert into Employee (Id, Name, Salary, DepartmentId) values (6, 'Randy', 85000, 1);
insert into Employee (Id, Name, Salary, DepartmentId) values (7, 'Will', 70000, 1);

insert into Department (Id, Name) values (1, 'IT');
insert into Department (Id, Name) values (2, 'Sales');
```

最终SQL:

```mysql
SELECT
    Department.name AS 'Department',
    Employee.name AS 'Employee',
    Salary
FROM
    Employee
        JOIN
    Department ON Employee.DepartmentId = Department.Id
WHERE
    (Employee.DepartmentId , Salary) IN
    (   SELECT
            DepartmentId, MAX(Salary)
        FROM
            Employee
        GROUP BY DepartmentId
	);
```

**需求二**：编写一个 SQL 查询，找出每个部门获得前三高工资的所有员工。

展示效果：

| Department | Employee | Salary |
| ---------- | -------- | ------ |
| IT         | Max      | 90000  |
| IT         | Randy    | 85000  |
| IT         | Joe      | 85000  |
| IT         | Will     | 70000  |
| Sales      | Henry    | 80000  |
| Sales      | Sam      | 60000  |

最终SQL:

```mysql
SELECT
    d.Name AS 'Department', e1.Name AS 'Employee', e1.Salary
FROM
    Employee e1
        JOIN
    Department d ON e1.DepartmentId = d.Id
WHERE
    3 > (SELECT
            COUNT(DISTINCT e2.Salary)
        FROM
            Employee e2
        WHERE
            e2.Salary > e1.Salary
                AND e1.DepartmentId = e2.DepartmentId
        );
```

## 9. 删除重复的电子邮箱

需求：编写一个 SQL 查询，来删除 Person 表中所有重复的电子邮箱，重复的邮箱里只保留 Id 最小 的那个。

展示效果：

| Id   | Email            |
| ---- | ---------------- |
| 1    | john@example.com |
| 2    | bob@example.com  |

```mysql
Create table If Not Exists Person (Id int, email varchar(255));

insert into Person (Id, email) values (1, 'john@example.com');
insert into Person (Id, email) values (2, 'bob@example.com');
insert into Person (Id, email) values (3, 'john@example.com');
```

最终SQL:

```mysql
DELETE 
      p1 
FROM 
      Person p1,
      Person p2
WHERE
      p1.Email = p2.Email AND p1.Id > p2.Id;
```

## 10. 上升的温度

需求：编写一个 SQL 查询，来查找与之前（昨天的）日期相比温度更高的所有日期的 Id。

| Id   |
| ---- |
| 2    |
| 4    |

```mysql
Create table If Not Exists Weather (Id int, RecordDate date, Temperature int);

insert into Weather (Id, RecordDate, Temperature) values (1, '2015-01-01', 10);
insert into Weather (Id, RecordDate, Temperature) values (2, '2015-01-02', 25);
insert into Weather (Id, RecordDate, Temperature) values (3, '2015-01-03', 20);
insert into Weather (Id, RecordDate, Temperature) values (4, '2015-01-04', 30);
```

最终SQL:

```mysql
SELECT
    weather.id AS 'Id'
FROM
    weather
JOIN
    weather w 
ON 
    DATEDIFF(weather.RecordDate, w.RecordDate) = 1
AND weather.Temperature > w.Temperature;
```

## 11. 行程和用户

需求：写一段 SQL 语句查出 2019年10月1日 至 2019年10月3日 期间非禁止用户的取消率。基于上表，你的 SQL 语句应返回如下结果，取消率（Cancellation Rate）保留两位小数。

取消率的计算方式如下：(被司机或乘客取消的非禁止用户生成的订单数量) / (非禁止用户生成的订单总数)

Trips表：所有出租车的行程信息。每段行程有唯一键 Id，Client_Id 和 Driver_Id 是 Users 表中 Users_Id 的外键。Status 是枚举类型，枚举成员为 (‘completed’, ‘cancelled_by_driver’, ‘cancelled_by_client’)。

| Id   | Client_Id | Driver_Id | City_Id | Status              | Request_at |
| ---- | --------- | --------- | ------- | ------------------- | ---------- |
| 1    | 1         | 10        | 1       | completed           | 2019-10-01 |
| 2    | 2         | 11        | 1       | cancelled_by_driver | 2019-10-01 |
| 3    | 3         | 12        | 6       | completed           | 2019-10-01 |
| 4    | 4         | 13        | 6       | cancelled_by_client | 2019-10-01 |
| 5    | 1         | 10        | 1       | completed           | 2019-10-02 |
| 6    | 2         | 11        | 6       | completed           | 2019-10-02 |
| 7    | 3         | 12        | 6       | completed           | 2019-10-02 |
| 8    | 2         | 12        | 12      | completed           | 2019-10-03 |
| 9    | 3         | 10        | 12      | completed           | 2019-10-03 |
| 10   | 4         | 13        | 12      | cancelled_by_driver | 2019-10-03 |

Users 表存所有用户。每个用户有唯一键 Users_Id。Banned 表示这个用户是否被禁止，Role 则是一个表示（‘client’, ‘driver’, ‘partner’）的枚举类型。

| Users_Id | Banned | Cancellation Rate |
| -------- | ------ | ----------------- |
| 1        | No     | client            |
| 2        | Yes    | client            |
| 3        | No     | client            |
| 4        | No     | client            |
| 10       | No     | driver            |
| 11       | No     | driver            |
| 12       | No     | driver            |
| 13       | No     | driver            |

展示效果：

| Day        | Cancellation Rate |
| ---------- | ----------------- |
| 2019-10-01 | 0.33              |
| 2019-10-02 | 0.00              |
| 2019-10-03 | 0.50              |

```mysql
Create table If Not Exists Trips (Id int, Client_Id int, Driver_Id int, City_Id int, Status ENUM('completed', 'cancelled_by_driver', 'cancelled_by_client'), Request_at varchar(50));

Create table If Not Exists Users (Users_Id int, Banned varchar(50), Role ENUM('client', 'driver', 'partner'));

insert into Trips (Id, Client_Id, Driver_Id, City_Id, Status, Request_at) values (1, 1, 10, 1, 'completed', '2019-10-01');
insert into Trips (Id, Client_Id, Driver_Id, City_Id, Status, Request_at) values (2, 2, 11, 1, 'cancelled_by_driver', '2019-10-01');
insert into Trips (Id, Client_Id, Driver_Id, City_Id, Status, Request_at) values (3, 3, 12, 6, 'completed', '2019-10-01');
insert into Trips (Id, Client_Id, Driver_Id, City_Id, Status, Request_at) values (4, 4, 13, 6, 'cancelled_by_client', '2019-10-01');
insert into Trips (Id, Client_Id, Driver_Id, City_Id, Status, Request_at) values (5, 1, 10, 1, 'completed', '2019-10-02');
insert into Trips (Id, Client_Id, Driver_Id, City_Id, Status, Request_at) values (6, 2, 11, 6, 'completed', '2019-10-02');
insert into Trips (Id, Client_Id, Driver_Id, City_Id, Status, Request_at) values (7, 3, 12, 6, 'completed', '2019-10-02');
insert into Trips (Id, Client_Id, Driver_Id, City_Id, Status, Request_at) values (8, 2, 12, 12, 'completed', '2019-10-03');
insert into Trips (Id, Client_Id, Driver_Id, City_Id, Status, Request_at) values (9, 3, 10, 12, 'completed', '2019-10-03');
insert into Trips (Id, Client_Id, Driver_Id, City_Id, Status, Request_at) values (10, 4, 13, 12, 'cancelled_by_driver', '2019-10-03');

insert into Users (Users_Id, Banned, Role) values (1, 'No', 'client');
insert into Users (Users_Id, Banned, Role) values (2, 'Yes', 'client');
insert into Users (Users_Id, Banned, Role) values (3, 'No', 'client');
insert into Users (Users_Id, Banned, Role) values (4, 'No', 'client');
insert into Users (Users_Id, Banned, Role) values (10, 'No', 'driver');
insert into Users (Users_Id, Banned, Role) values (11, 'No', 'driver');
insert into Users (Users_Id, Banned, Role) values (12, 'No', 'driver');
insert into Users (Users_Id, Banned, Role) values (13, 'No', 'driver');
```

最终SQL:

```mysql
方法一：
SELECT 
      T.request_at AS `Day`, 
	  ROUND(
            SUM(IF(T.STATUS = 'completed',0,1))/ COUNT(T.STATUS),
            2
            ) AS `Cancellation Rate`
FROM 
      Trips AS T
JOIN 
      Users AS U1 
ON 
      T.client_id = U1.users_id AND U1.banned ='No'
JOIN 
      Users AS U2 
ON 
      T.driver_id = U2.users_id AND U2.banned ='No'
WHERE 
      T.request_at BETWEEN '2019-10-01' AND '2019-10-03'
GROUP BY 
      T.request_at;

方法二：
SELECT 
      T.request_at AS `Day`, 
	  ROUND(
			SUM(IF(T.STATUS = 'completed',0,1))/ COUNT(T.STATUS),
			2
	        ) AS `Cancellation Rate`
FROM 
     trips AS T 
LEFT JOIN (
	        SELECT
                  users_id
	        FROM
                  users
	        WHERE  
                  banned = 'Yes'
           ) AS A 
ON 
     T.Client_Id = A.users_id
LEFT JOIN (
	        SELECT
                  users_id
	        FROM 
                  users
	        WHERE 
                  banned = 'Yes'
           ) AS A1
ON 
     T.Driver_Id = A1.users_id
WHERE 
     A.users_id IS NULL 
     AND A1.users_id IS NULL 
     AND T.request_at BETWEEN '2019-10-01' AND '2019-10-03'
GROUP BY 
     T.request_at;

方法三：
SELECT 
     T.request_at AS `Day`, 
	 ROUND(
           SUM(IF(T.STATUS = 'completed',0,1))/ COUNT(T.STATUS),
		   2
	       ) AS `Cancellation Rate`
FROM
     trips AS T
WHERE 
     T.Client_Id NOT IN (
	                      SELECT 
                                 users_id
	                      FROM 
                                 users
	                      WHERE 
                                 banned = 'Yes'
     )
AND
     T.Driver_Id NOT IN (
     	                  SELECT 
                                 users_id
     	                  FROM  
                                 users
     	                  WHERE 
                                 banned = 'Yes'
     )
AND 
     T.request_at BETWEEN '2019-10-01' AND '2019-10-03';
```

## 12. 游戏玩法分析

**需求一**：写一条 SQL 查询语句获取每位玩家 第一次登陆平台的日期。

 Activity表：显示了某些游戏的玩家的活动情况。


| player_id | device_id | event_date | games_played |
| --------- | --------- | ---------- | ------------ |
| 1         | 2         | 2016-03-01 | 5            |
| 1         | 2         | 2016-05-02 | 6            |
| 2         | 3         | 2017-06-25 | 1            |
| 3         | 1         | 2016-03-02 | 0            |
| 3         | 4         | 2018-07-03 | 5            |

展示效果：

| player_id | first_login |
| --------- | ----------- |
| 1         | 2016-03-01  |
| 2         | 2017-06-25  |
| 3         | 2016-03-02  |

```mysql
Create table If Not Exists Activity (player_id int, device_id int, event_date date, games_played int);

insert into Activity (player_id, device_id, event_date, games_played) values (1, 2, '2016-03-01', 5);
insert into Activity (player_id, device_id, event_date, games_played) values (1, 2, '2016-05-02', 6);
insert into Activity (player_id, device_id, event_date, games_played) values (2, 3, '2017-06-25', 1);
insert into Activity (player_id, device_id, event_date, games_played) values (3, 1, '2016-03-02', 0);
insert into Activity (player_id, device_id, event_date, games_played) values (3, 4, '2018-07-03', 5);
```

最终SQL:

```mysql
select 
      player_id, 
      min(event_date) as first_login 
from 
      Activity 
group by 
      player_id;
```

**需求二**：描述每一个玩家首次登陆的设备名称

| player_id | device_id |
| --------- | --------- |
| 1         | 2         |
| 2         | 3         |
| 3         | 1         |

最终SQL:

```mysql
select 
      player_id,
      device_id 
from
     (select *  
      from 
          Activity
      where
          (player_id,event_date) in (select
                                           player_id, 
                                           device_id
                                           min(event_date)
                                      from
                                           Activity 
                                      group by 
                                           player_id
                                      )
      ) as t;
```

**需求三**：编写一个 SQL 查询，同时报告每组玩家和日期，以及玩家到目前为止玩了多少游戏。也就是说，在此日期之前玩家所玩的游戏总数。详细情况请查看示例。

| player_id | event_date | games_played_so_far |
| --------- | ---------- | ------------------- |
| 1         | 2016-03-01 | 5                   |
| 1         | 2016-05-02 | 11                  |
| 2         | 2017-06-25 | 1                   |
| 3         | 2016-03-02 | 0                   |
| 3         | 2018-07-03 | 5                   |

提示：对于 ID 为 3 的玩家，2018-07-03 共玩了 0+5=5 个游戏。

最终SQL:

```mysql
//方法一
SELECT C.player_id,C.event_date,C.games_played_so_far
FROM (
      SELECT 
      	A.player_id,
      	A.event_date,
          @sum_cnt:=
      		if(A.player_id = @pre_id AND A.event_date != @pre_date,
      			@sum_cnt + A.games_played,
      			A.games_played 
      		)
      		AS `games_played_so_far`,
          @pre_id:=A.player_id AS `player_ids`,
          @pre_date:=A.event_date AS `event_dates`
      FROM 
          activity AS A,
          (SELECT @pre_id:=NULL,@pre_date:=NULL,@sum_cnt:=0) AS B
      order BY 
          A.player_id,A.event_date
) AS C

//方法二
SELECT 
      B.player_id,
      B.event_date,
      SUM(A.games_played) AS `games_played_so_far`
FROM 
      Activity AS A
JOIN 
      Activity AS B 
ON 
      A.player_id = B.player_id 
      AND A.event_date <= B.event_date
GROUP BY 
      B.player_id,B.event_date;
```

**需求四**：编写一个 SQL 查询，报告在首次登录的第二天再次登录的玩家的百分比，四舍五入到小数点后两位。换句话说，您需要计算从首次登录日期开始至少连续两天登录的玩家的数量，然后除以玩家总数。



| fraction |
| -------- |
| 0.00     |

提示：对于 ID 为 1 的玩家，2016-05-02 共玩了 5+6=11 个游戏. 
对于 ID 为 3 的玩家，2018-07-03 共玩了 0+5=5 个游戏。

最终SQL:

```mysql
select 
      round(
            sum(case when datediff(a.event_date,b.first_date)=1 then 1 else 0 end)
               /
               (select count(distinct(player_id)) from activity)
            ,2 ) as fraction
from 
      activity a,
     (select 
             player_id,
             min(event_date) first_date 
      from 
             activity 
      group by 
             player_id
     ) b
where 
      a.player_id=b.player_id;
```

**需求五**：编写一个 SQL 查询，报告每个安装日期、当天安装游戏的玩家数量和第一天的保留时间。



| install_dt | installs | Day1_retention |
| ---------- | -------- | -------------- |
| 2016-03-01 | 2        | 0.50           |
| 2017-06-25 | 1        | 0.00           |

提示：玩家 1 和 3 在 2016-03-01 安装了游戏，但只有玩家 1 在 2016-03-02 重新登录，所以 2016-03-01 的第一天保留时间是 1/2=0.50
玩家 2 在 2017-06-25 安装了游戏，但在 2017-06-26 没有重新登录，因此 2017-06-25 的第一天保留为 0/1=0.00

最终SQL:

```mysql
#方法一
SELECT
      A.install_date,
      COUNT(A.player_id) AS `installs`,
      COUNT(AA.player_id) AS `return_cnt`
FROM 
   (SELECT 
           player_id,
           MIN(event_date) AS `install_date`
	FROM 
           Activity
	GROUP BY 
           player_id
    ) AS A
left JOIN 
    Activity AS AA 
ON 
    AA.event_date = DATE_ADD(A.install_date,INTERVAL 1 DAY) AND AA.player_id = A.player_id
GROUP BY
    A.install_date;

#方法二
SELECT 
      A.event_date AS `install_dt`,
      COUNT(A.player_id) AS `installs`,
      round(COUNT(C.player_id)/COUNT(A.player_id),2) AS `Day1_retention`
FROM
      Activity AS A 
left JOIN 
      Activity AS B
ON 
      A.player_id = B.player_id AND A.event_date > B.event_date
left JOIN 
      Activity AS C
ON    
      A.player_id = C.player_id AND C.event_date = DATE_ADD(A.event_date,INTERVAL 1 DAY)
WHERE 
      B.event_date IS NULL
GROUP BY 
      A.event_date;
```

## 13. 员工薪水中位数

需求：请编写SQL查询来查找每个公司的薪水中位数。挑战点：你是否可以在不使用任何内置的SQL函数的情况下解决此问题。

展示效果：

| Id   | Company | Salary |
| ---- | ------- | ------ |
| 5    | A       | 451    |
| 6    | A       | 513    |
| 12   | B       | 234    |
| 9    | B       | 1154   |
| 14   | C       | 2645   |

```mysql
Create table If Not Exists Employee (Id int, Company varchar(255), Salary int);

insert into Employee (Id, Company, Salary) values (1, 'A', 2341);
insert into Employee (Id, Company, Salary) values (2, 'A', 341);
insert into Employee (Id, Company, Salary) values (3, 'A', 15);
insert into Employee (Id, Company, Salary) values (4, 'A', 15314);
insert into Employee (Id, Company, Salary) values (5, 'A', 451);
insert into Employee (Id, Company, Salary) values (6, 'A', 513);
insert into Employee (Id, Company, Salary) values (7, 'B', 15);
insert into Employee (Id, Company, Salary) values (8, 'B', 13);
insert into Employee (Id, Company, Salary) values (9, 'B', 1154);
insert into Employee (Id, Company, Salary) values (10, 'B', 1345);
insert into Employee (Id, Company, Salary) values (11, 'B', 1221);
insert into Employee (Id, Company, Salary) values (12, 'B', 234);
insert into Employee (Id, Company, Salary) values (13, 'C', 2345);
insert into Employee (Id, Company, Salary) values (14, 'C', 2645);
insert into Employee (Id, Company, Salary) values (15, 'C', 2645);
insert into Employee (Id, Company, Salary) values (16, 'C', 2652);
insert into Employee (Id, Company, Salary) values (17, 'C', 65);
```

最终SQL:

```mysql
select 
     b.id,
     b.company,
     b.salary
from 
    (select
           id,
           company,
           salary,
           case @com when company then @rk:=@rk+1 else @rk:=1 end rk,
           @com:=company
    from 
           employee,
           (select @rk:=0, @com:='') a
    order by
           company,salary
    ) b
left join 
    (select
           company,
           count(1)/2 cnt
     from 
           employee
     group by company
    ) c
on 
     b.company=c.company
where
     b.rk in (cnt+0.5,cnt+1,cnt);
```

## 14. 至少有5名直接下属的经理

需求：Employee 表，请编写一个SQL查询来查找至少有5名直接下属的经理。

展示效果：

| Name |
| ---- |
| John |

```mysql
Create table If Not Exists Employee (Id int, Name varchar(255), Department varchar(255), ManagerId int);

insert into Employee (Id, Name, Department, ManagerId) values (101, 'John', 'A', null);
insert into Employee (Id, Name, Department, ManagerId) values (102, 'Dan', 'A', 101);
insert into Employee (Id, Name, Department, ManagerId) values (103, 'James', 'A', 101);
insert into Employee (Id, Name, Department, ManagerId) values (104, 'Amy', 'A', 101);
insert into Employee (Id, Name, Department, ManagerId) values (105, 'Anne', 'A', 101);
insert into Employee (Id, Name, Department, ManagerId) values (106, 'Ron', 'B', 101);
```

最终SQL:

```mysql
SELECT
    Name
FROM
    Employee AS t1 
JOIN 
   (SELECT
        ManagerId
    FROM
        Employee
    GROUP BY 
        ManagerId
    HAVING
        COUNT(ManagerId) >= 5
    ) AS t2
ON  
    t1.Id = t2.ManagerId;
```

## 15. 给定数字的频率查询中位数

需求：编写一个 SQL 查询，满足条件：无论 person 是否有地址信息，都需要基于上述两表提供 person 的以下信息：
FirstName, LastName, City, State

展示效果：

| median |
| ------ |
| 0.0000 |

```mysql
Create table If Not Exists Numbers (Number int, Frequency int);

insert into Numbers (Number, Frequency) values (0, 7);
insert into Numbers (Number, Frequency) values (1, 1);
insert into Numbers (Number, Frequency) values (2, 3);
insert into Numbers (Number, Frequency) values (3, 1);
```

最终SQL:

```mysql
select
      avg(t.number) as median
from
      (select
             n1.number,
             n1.frequency,
             (select 
                   sum(frequency) 
              from 
                   numbers n2
              where 
                   n2.number<=n1.number
             ) as asc_frequency,
             (select
                   sum(frequency)
              from 
                   numbers n3 
              where 
                   n3.number>=n1.number
             ) as desc_frequency
      from 
             numbers n1
      ) t
where 
      t.asc_frequency>= (select sum(frequency) from numbers)/2
      and t.desc_frequency>= (select sum(frequency) from numbers)/2;
```

## 16. 当选者

需求：请编写 sql 语句来找到当选者（CandidateId）的名字

展示效果：

| Name |
| ---- |
| B    |

```mysql
Create table If Not Exists Candidate (id int, Name varchar(255));
Create table If Not Exists Vote (id int, CandidateId int);

insert into Candidate (id, Name) values (1, 'A');
insert into Candidate (id, Name) values (2, 'B');
insert into Candidate (id, Name) values (3, 'C');
insert into Candidate (id, Name) values (4, 'D');
insert into Candidate (id, Name) values (5, 'E');

insert into Vote (id, CandidateId) values (1, 2);
insert into Vote (id, CandidateId) values (2, 44);
insert into Vote (id, CandidateId) values (3, 3);
insert into Vote (id, CandidateId) values (4, 2);
insert into Vote (id, CandidateId) values (5, 5);
```

最终SQL:

```mysql
SELECT
    name AS 'Name'
FROM
    Candidate
JOIN
    (SELECT
        Candidateid
    FROM
        Vote
    GROUP BY 
        Candidateid
    ORDER BY 
        COUNT(*) DESC
    LIMIT 1
    ) AS winner
WHERE
    Candidate.id = winner.Candidateid;
```

## 17. 员工奖金

需求：选出所有 bonus < 1000 的员工的 name 及其 bonus。

展示效果：

| name | bonus |
| ---- | ----- |
| John | null  |
| Dan  | 500   |
| Brad | null  |

```mysql
Create table If Not Exists Employee (EmpId int, Name varchar(255), Supervisor int, Salary int);
Create table If Not Exists Bonus (EmpId int, Bonus int);

insert into Employee (EmpId, Name, Supervisor, Salary) values (3, 'Brad', null, 4000);
insert into Employee (EmpId, Name, Supervisor, Salary) values (1, 'John', 3, 1000);
insert into Employee (EmpId, Name, Supervisor, Salary) values (2, 'Dan', 3, 2000);
insert into Employee (EmpId, Name, Supervisor, Salary) values (4, 'Thomas', 3, 4000);

insert into Bonus (EmpId, Bonus) values (2, 500);
insert into Bonus (EmpId, Bonus) values (4, 2000);
```

最终SQL:

```mysql
SELECT
    Employee.name, 
    Bonus.bonus
FROM
    Employee
LEFT JOIN
    Bonus
ON 
    Employee.empid = Bonus.empid
WHERE
    bonus < 1000 OR bonus IS NULL;
```

## 18. 最高回答率

需求：请编写SQL查询来找到具有最高回答率的问题。

展示效果：

| survey_log |
| ---------- |
| 285        |

```mysql
Create table If Not Exists survey_log (uid int, action varchar(255), question_id int, answer_id int, q_num int, timestamp int);

insert into survey_log (uid, action, question_id, answer_id, q_num, timestamp) values (5, 'show', 285, null, 1, 123);
insert into survey_log (uid, action, question_id, answer_id, q_num, timestamp) values (5, 'answer', 285, 124124, 1, '124');
insert into survey_log (uid, action, question_id, answer_id, q_num, timestamp) values (5, 'show', 369, null, 2, 125);
insert into survey_log (uid, action, question_id, answer_id, q_num, timestamp) values (5, 'skip', 369, null, 2, 126);
```

最终SQL:

```mysql
#方法一
SELECT 
    question_id as survey_log
FROM
   (SELECT 
         question_id,
         SUM(case when action="answer" THEN 1 ELSE 0 END) as num_answer,
         SUM(case when action="show" THEN 1 ELSE 0 END) as num_show
	FROM 
         survey_log
	GROUP BY 
         question_id
    ) as tbl
ORDER BY
    (num_answer / num_show) DESC
LIMIT 1;

#方法二
SELECT 
    question_id AS 'survey_log'
FROM
    survey_log
GROUP BY
    question_id
ORDER BY
    COUNT(answer_id) / COUNT(IF(action = 'show', 1, 0)) DESC
LIMIT 1;
```

## 19. 员工累计薪水

需求：查询一个员工三个月内的累计薪水，但是不包括最近一个月的薪水。

展示效果：

| Id   | Month | Salary |
| ---- | ----- | ------ |
| 1    | 3     | 90     |
| 1    | 2     | 50     |
| 1    | 1     | 20     |
| 2    | 1     | 20     |
| 3    | 3     | 100    |
| 3    | 2     | 40     |

```mysql
Create table If Not Exists Employee (Id int, Month int, Salary int);

insert into Employee (Id, Month, Salary) values (1, 1, 20);
insert into Employee (Id, Month, Salary) values (2, 1, 20);
insert into Employee (Id, Month, Salary) values (1, 2, 30);
insert into Employee (Id, Month, Salary) values (2, 2, 30);
insert into Employee (Id, Month, Salary) values (3, 2, 40);
insert into Employee (Id, Month, Salary) values (1, 3, 40);
insert into Employee (Id, Month, Salary) values (3, 3, 60);
insert into Employee (Id, Month, Salary) values (1, 4, 60);
insert into Employee (Id, Month, Salary) values (3, 4, 70);
```

最终SQL:

```mysql
SELECT
    E1.id,
    E1.month,
    (IFNULL(E1.salary, 0) + IFNULL(E2.salary, 0) + IFNULL(E3.salary, 0)) AS Salary
FROM
    (SELECT
        id, MAX(month) AS month
    FROM
        Employee
    GROUP BY 
        id
    HAVING 
        COUNT(*) > 1) AS maxmonth
    LEFT JOIN
        Employee E1 
    ON 
        (maxmonth.id = E1.id AND maxmonth.month > E1.month)
    LEFT JOIN
        Employee E2 
    ON 
        (E2.id = E1.id AND E2.month = E1.month - 1)
    LEFT JOIN 
        Employee E3 
    ON
        (E3.id = E1.id AND E3.month = E1.month - 2)
ORDER BY 
    id ASC , month DESC;
```

## 20. 统计各专业人数

需求：查询 department 表中每个专业的学生人数 （即使没有学生的专业也需列出）。

展示效果：

| dept_name   | student_number |
| ----------- | -------------- |
| Engineering | 2              |
| Science     | 1              |
| Law         | 0              |

```mysql
CREATE TABLE IF NOT EXISTS student (student_id INT,student_name VARCHAR(45), gender VARCHAR(6), dept_id INT);
CREATE TABLE IF NOT EXISTS department (dept_id INT, dept_name VARCHAR(255));

insert into student (student_id, student_name, gender, dept_id) values (1, 'Jack', 'M', 1);
insert into student (student_id, student_name, gender, dept_id) values (2, 'Jane', 'F', 1);
insert into student (student_id, student_name, gender, dept_id) values (3, 'Mark', 'M', 2);

insert into department (dept_id, dept_name) values (1, 'Engineering');
insert into department (dept_id, dept_name) values (2, 'Science');
insert into department (dept_id, dept_name) values (3, 'Law');
```

最终SQL:

```mysql
SELECT
    dept_name,
    COUNT(student_id) AS student_number
FROM
    department
LEFT OUTER JOIN
    student 
ON
    department.dept_id = student.dept_id
GROUP BY 
    department.dept_name
ORDER BY 
    student_number DESC, 
    department.dept_name;
```

## 21. 寻找用户推荐人

需求：写一个查询语句，返回一个编号列表，列表中编号的推荐人的编号都 **不是** 2

展示效果：

| name |
| ---- |
| Will |
| Jane |
| Bill |
| Zack |

```mysql
CREATE TABLE IF NOT EXISTS customer (id INT,name VARCHAR(25),referee_id INT);

insert into customer (id, name, referee_id) values (1, 'Will', null);
insert into customer (id, name, referee_id) values (2, 'Jane', null);
insert into customer (id, name, referee_id) values (3, 'Alex', 2);
insert into customer (id, name, referee_id) values (4, 'Bill', null);
insert into customer (id, name, referee_id) values (5, 'Zack', 1);
insert into customer (id, name, referee_id) values (6, 'Mark', 2);
```

最终SQL:

```mysql
SELECT 
      name
FROM 
      customer
WHERE 
      referee_id <> 2 OR referee_id IS NULL;
```

## 22. 2016年的投资

需求：写一个查询语句，将 2016 年 (TIV_2016) 所有成功投资的金额加起来，保留 2 位小数。

展示效果：

| TIV_2016 |
| -------- |
| 45.00    |

```mysql
CREATE TABLE IF NOT EXISTS insurance (PID INTEGER(11), TIV_2015 NUMERIC(15,2), TIV_2016 NUMERIC(15,2), LAT NUMERIC(5,2), LON NUMERIC(5,2) );

insert into insurance (PID, TIV_2015, TIV_2016, LAT, LON) values (1, 10, 5, 10, 10);
insert into insurance (PID, TIV_2015, TIV_2016, LAT, LON) values (2, 20, 20, 20, 20);
insert into insurance (PID, TIV_2015, TIV_2016, LAT, LON) values (3, 10, 30, 20, 20);
insert into insurance (PID, TIV_2015, TIV_2016, LAT, LON) values (4, 10, 40, 40, 40);
```

提示：

对于一个投保人，他在 2016 年成功投资的条件是：

他在 2015 年的投保额 (TIV_2015) 至少跟一个其他投保人在 2015 年的投保额相同。
他所在的城市必须与其他投保人都不同（也就是说维度和经度不能跟其他任何一个投保人完全相同）。

就如最后一个投保人，第一个投保人同时满足两个条件：

1. 他在 2015 年的投保金额 TIV_2015 为 10 ，与第三个和第四个投保人在 2015 年的投保金额相同。
2. 他所在城市的经纬度是独一无二的。

第二个投保人两个条件都不满足。他在 2015 年的投资 TIV_2015 与其他任何投保人都不相同。
且他所在城市的经纬度与第三个投保人相同。基于同样的原因，第三个投保人投资失败。

最终SQL:

```mysql
SELECT
    SUM(insurance.TIV_2016) AS TIV_2016
FROM
    insurance
WHERE
    insurance.TIV_2015 IN(
                          SELECT
                                TIV_2015
                          FROM
                                insurance
                          GROUP BY 
                                TIV_2015
                          HAVING 
                                COUNT(*) > 1
                          )
                      AND 
                          CONCAT(LAT, LON) IN(
                                              SELECT
                                                    CONCAT(LAT, LON)
                                              FROM
                                                    insurance
                                              GROUP BY 
                                                    LAT , LON
                                              HAVING COUNT(*) = 1
    );
```

## 23. 订单最多的客户

需求：在表 orders 中找到订单数最多客户对应的 customer_number 。

展示效果：

| customer_number |
| --------------- |
| 3               |

```mysql
Create table If Not Exists orders (order_number int, customer_number int, order_date date, required_date date, shipped_date date, status char(15), comment char(200), key(order_number));

insert into orders (order_number, customer_number) values (1, 1);
insert into orders (order_number, customer_number) values (2, 2);
insert into orders (order_number, customer_number) values (3, 3);
insert into orders (order_number, customer_number) values (4, 3);
```

最终SQL:

```mysql
SELECT
    customer_number
FROM
    orders
GROUP BY 
    customer_number
ORDER BY
    COUNT(*) DESC
LIMIT 1;
```

进阶： 如果有多位顾客订单数并列最多，你能找到他们所有的 customer_number 吗？

## 24. 大的国家

需求：编写一个SQL查询，输出表中所有大国家的名称、人口和面积。

展示效果：

| name        | population | area    |
| ----------- | ---------- | ------- |
| Afghanistan | 25500100   | 652230  |
| Algeria     | 37100000   | 2381741 |

```mysql
Create table If Not Exists World (name varchar(255), continent varchar(255), area int, population int, gdp bigint);

insert into World (name, continent, area, population, gdp) values ('Afghanistan', 'Asia', 652230, 25500100, 20343000000);
insert into World (name, continent, area, population, gdp) values ('Albania', 'Europe', 28748, 2831741, 12960000000);
insert into World (name, continent, area, population, gdp) values ('Algeria', 'Africa', 2381741, 37100000, 188681000000);
insert into World (name, continent, area, population, gdp) values ('Andorra', 'Europe', 468, 78115, 3712000000);
insert into World (name, continent, area, population, gdp) values ('Angola', 'Africa', 1246700, 20609294, 100990000000);
```

最终SQL:

```mysql
#方法一：or
select 
      w.name,
      w.population,
      w.area
from 
      world w
where 
      w.area >3000000 or w.population >25000000

#方法二：union
select 
      w.name,
      w.population,
      w.area
from 
      world w
where 
      w.area>3000000
union
select
      w.name,
      w.population,
      w.area
from 
      world w
where 
      w.population>25000000
```

## 25. 超过五名学生的课

需求：编写一个 SQL 查询，列出所有超过或等于5名学生的课。

展示效果：

| class |
| ----- |
| Math  |

```mysql
Create table If Not Exists courses (student varchar(255), class varchar(255));

insert into courses (student, class) values ('A', 'Math');
insert into courses (student, class) values ('B', 'English');
insert into courses (student, class) values ('C', 'Math');
insert into courses (student, class) values ('D', 'Biology');
insert into courses (student, class) values ('E', 'Math');
insert into courses (student, class) values ('F', 'Computer');
insert into courses (student, class) values ('G', 'Math');
insert into courses (student, class) values ('H', 'Math');
insert into courses (student, class) values ('I', 'Math');
```

最终SQL:

```mysql
select
      class 
from 
      courses 
group by 
      class
having 
      count(distinct student)>=5 ;
```

## 26. 好友申请

需求一：写一个查询语句，求出好友申请的通过率，用 2 位小数表示。通过率由接受好友申请的数目除以申请总数。

展示效果：

| accept_rate |
| ----------- |
| 0.80        |

```mysql
Create table If Not Exists friend_request ( sender_id INT NOT NULL, send_to_id INT NULL, request_date DATE NULL);
Create table If Not Exists request_accepted ( requester_id INT NOT NULL, accepter_id INT NULL, accept_date DATE NULL);

insert into friend_request (sender_id, send_to_id, request_date) values (1, 2, '2016/06/01');
insert into friend_request (sender_id, send_to_id, request_date) values (1, 3, '2016/06/01');
insert into friend_request (sender_id, send_to_id, request_date) values (1, 4, '2016/06/01');
insert into friend_request (sender_id, send_to_id, request_date) values (2, 3, '2016/06/02');
insert into friend_request (sender_id, send_to_id, request_date) values (3, 4, '2016/06/09');

insert into request_accepted (requester_id, accepter_id, accept_date) values (1, 2, '2016/06/03');
insert into request_accepted (requester_id, accepter_id, accept_date) values (1, 3, '2016/06/08');
insert into request_accepted (requester_id, accepter_id, accept_date) values (2, 3, '2016/06/08');
insert into request_accepted (requester_id, accepter_id, accept_date) values (3, 4, '2016/06/09');
insert into request_accepted (requester_id, accepter_id, accept_date) values (3, 4, '2016/06/10');
```

最终SQL:

```mysql
select
      round(
            ifnull(
                   (select count(*) from (select distinct requester_id, accepter_id from request_accepted) as A)
                   /
                   (select count(*) from (select distinct sender_id, send_to_id from friend_request) as B)
            , 0)
      , 2) as accept_rate;
```



需求二：写一个查询语句，求出谁拥有最多的好友和他拥有的好友数目。

展示效果：

| id   | num  |
| ---- | ---- |
| 3    | 3    |

最终SQL:

```mysql
select ids as id, cnt as num
from
    (select
           ids,
           count(*) as cnt
     from
           (select 
                  requester_id as ids 
            from
                  request_accepted
            union all
            select
                  accepter_id 
            from
                  request_accepted
            ) as tbl1
     group by ids
     ) as tbl2
order by 
     cnt desc
limit 1;
```

## 27. 体育馆人流量

需求：请编写一个查询语句，找出人流量的高峰期。高峰期时，至少连续三行记录中的人流量不少于100。

展示效果：

| id   | visit_date | people |
| ---- | ---------- | ------ |
| 5    | 2017-01-05 | 145    |
| 6    | 2017-01-06 | 1455   |
| 7    | 2017-01-07 | 199    |
| 8    | 2017-01-08 | 188    |

```mysql
Create table If Not Exists stadium (id int, visit_date DATE NULL, people int);

insert into stadium (id, visit_date, people) values (1, '2017-01-01', 10);
insert into stadium (id, visit_date, people) values (2, '2017-01-02', 109);
insert into stadium (id, visit_date, people) values (3, '2017-01-03', 150);
insert into stadium (id, visit_date, people) values (4, '2017-01-04', 99);
insert into stadium (id, visit_date, people) values (5, '2017-01-05', 145);
insert into stadium (id, visit_date, people) values (6, '2017-01-06', 1455);
insert into stadium (id, visit_date, people) values (7, '2017-01-07', 199);
insert into stadium (id, visit_date, people) values (8, '2017-01-08', 188);
```

最终SQL:

```mysql
SELECT 
     distinct a.*
FROM 
     stadium as a,
     stadium as b,
     stadium as c
where
     ((a.id = b.id-1 and b.id+1 = c.id) or(a.id-1 = b.id and a.id+1 = c.id) or(a.id-1 = c.id and c.id-1 = b.id))
      and 
     (a.people>=100 and b.people>=100 and c.people>=100)
order by 
     a.id;
```

## 28. 连续空余座位

需求：编写一个 SQL 查询，获取所有空余座位，并将它们按照 seat_id 排序

展示效果：

| seat_id |
| ------- |
| 3       |
| 4       |
| 5       |

```mysql
Create table If Not Exists cinema (seat_id int primary key auto_increment, free bool);

insert into cinema (seat_id, free) values (1, 1);
insert into cinema (seat_id, free) values (2, 0);
insert into cinema (seat_id, free) values (3, 1);
insert into cinema (seat_id, free) values (4, 1);
insert into cinema (seat_id, free) values (5, 1);
```

最终SQL:

```mysql
select 
     distinct a.seat_id
from 
     cinema a 
join 
     cinema b
on 
     abs(a.seat_id - b.seat_id) = 1 and a.free = true and b.free = true
order by 
     a.seat_id;
```

## 29. 销售员

需求：输出所有表 salesperson中，没有向公司 'RED' 销售任何东西的销售员。

展示效果：

| name |
| ---- |
| Amy  |
| Mark |
| Alex |

```mysql
Create table If Not Exists salesperson (sales_id int, name varchar(255), salary int,commission_rate int, hire_date varchar(255));
Create table If Not Exists company (com_id int, name varchar(255), city varchar(255));
Create table If Not Exists orders (order_id int, order_date varchar(255), com_id int, sales_id int, amount int);

insert into salesperson (sales_id, name, salary, commission_rate, hire_date) values (1,'John',100000, 6, '4/1/2006');
insert into salesperson (sales_id, name, salary, commission_rate, hire_date) values (2,'Amy', 12000, 5, '5/1/2010');
insert into salesperson (sales_id, name, salary, commission_rate, hire_date) values (3,'Mark',65000, 12, '12/25/2008');
insert into salesperson (sales_id, name, salary, commission_rate, hire_date) values (4,'Pam', 25000, 25, '1/1/2005');
insert into salesperson (sales_id, name, salary, commission_rate, hire_date) values (5,'Alex', 5000, 10, '2/3/2007');

insert into company (com_id, name, city) values (1, 'RED', 'Boston');
insert into company (com_id, name, city) values (2, 'ORANGE', 'New York');
insert into company (com_id, name, city) values (3, 'YELLOW', 'Boston');
insert into company (com_id, name, city) values (4, 'GREEN', 'Austin');

insert into orders (order_id, order_date, com_id, sales_id, amount) values (1, '1/1/2014', 3, 4, 10000);
insert into orders (order_id, order_date, com_id, sales_id, amount) values (2, '2/1/2014', 4, 5, 5000);
insert into orders (order_id, order_date, com_id, sales_id, amount) values (3, '3/1/2014', 1, 1, 50000);
insert into orders (order_id, order_date, com_id, sales_id, amount) values (4, '4/1/2014', 1, 4, 25000);

```

最终SQL:

```mysql
SELECT
    s.name
FROM
    salesperson s
WHERE
    s.sales_id NOT IN (
                       SELECT
                             o.sales_id
                       FROM
                             orders o
                       LEFT JOIN
                             company c 
                       ON
                             o.com_id = c.com_id
                       WHERE
                             c.name = 'RED'
    );
```

## 30. 节点树

需求：写一个查询语句，输出所有节点的编号和节点的类型，并将结果按照节点编号排序。

表 tree，**id** 是树节点的编号， **p_id** 是它父节点的 **id 。**

树中每个节点属于以下三种类型之一：

​		叶子：如果这个节点没有任何孩子节点。

​		根：如果这个节点是整棵树的根，即没有父节点。

​		内部节点：如果这个节点既不是叶子节点也不是根节点。

| id   | p_id |
| ---- | ---- |
| 1    | null |
| 2    | 1    |
| 3    | 1    |
| 4    | 2    |
| 5    | 2    |

展示效果：

| id   | Type  |
| ---- | ----- |
| 1    | Root  |
| 2    | Inner |
| 3    | Leaf  |
| 4    | Leaf  |
| 5    | Leaf  |

解释:

节点 1 是根节点，因为它的父节点是 NULL ，同时它有孩子节点 2 和 3 。

节点 2 是内部节点，因为它有父节点 1 ，也有孩子节点 4 和 5 。

节点 3, 4 和 5 都是叶子节点，因为它们都有父节点同时没有孩子节点。

```mysql
Create table If Not Exists tree (id int, p_id int);

insert into tree (id, p_id) values (1, null);
insert into tree (id, p_id) values (2, 1);
insert into tree (id, p_id) values (3, 1);
insert into tree (id, p_id) values (4, 2);
insert into tree (id, p_id) values (5, 2);
```

最终SQL:

```mysql
方法一：
SELECT
    id, 'Root' AS Type
FROM
    tree
WHERE
    p_id IS NULL

UNION

SELECT
    id, 'Leaf' AS Type
FROM
    tree
WHERE
    id NOT IN (SELECT DISTINCT
            p_id
        FROM
            tree
        WHERE
            p_id IS NOT NULL)
        AND p_id IS NOT NULL

UNION

SELECT
    id, 'Inner' AS Type
FROM
    tree
WHERE
    id IN (SELECT DISTINCT
            p_id
        FROM
            tree
        WHERE
            p_id IS NOT NULL)
        AND p_id IS NOT NULL
ORDER BY id;

方法二：
SELECT
      id AS `Id`,
      CASE 
          WHEN tree.id = (SELECT atree.id FROM tree atree WHERE atree.p_id IS NULL) THEN 'Root'
          WHEN tree.id IN (SELECT atree.p_id FROM tree atree) THEN 'Inner'
          ELSE 'Leaf'
      END AS Type
FROM
      tree
ORDER BY `Id`;

方法三：
SELECT
    atree.id,
    IF(ISNULL(atree.p_id),'Root', IF(atree.id IN (SELECT p_id FROM tree), 'Inner','Leaf')) Type
FROM
    tree atree
ORDER BY atree.id;
```

## 31. 判断是否是三角形

需求：编写一个 SQL 查询，判断三条线段是否能形成一个三角形。

展示效果：

| x    | y    | z    | triangle |
| ---- | ---- | ---- | -------- |
| 13   | 15   | 15   | No       |
| 10   | 20   | 15   | Yes      |

```mysql
Create table If Not Exists triangle (x int, y int, z int);

insert into triangle (x, y, z) values (13, 15, 30);
insert into triangle (x, y, z) values (10, 20, 15);
```

最终SQL:

```mysql
select
      x,
      y,
      z,
      if((x + y <= z or x + z <= y or y + z <= x), "No", "Yes") as triangle
from triangle;
```

## 32. 平面上的最近距离

需求：写一个查询语句找到两点之间的最近距离，保留 2 位小数。

展示效果：

| shortest |
| -------- |
| 1.00     |

```mysql
CREATE TABLE If Not Exists point_2d (x INT NOT NULL, y INT NOT NULL);

insert into point_2d (x, y) values (-1, -1);
insert into point_2d (x, y) values (0, 0);
insert into point_2d (x, y) values (-1, -2);
```

最终SQL:

```mysql
#方法一：
SELECT
    ROUND(SQRT(MIN((POW(p1.x - p2.x, 2) + POW(p1.y - p2.y, 2)))), 2) AS shortest
FROM
    point_2d p1
JOIN
    point_2d p2 
ON 
    p1.x != p2.x OR p1.y != p2.y;

#方法二：
SELECT
    ROUND(SQRT(MIN((POW(p1.x - p2.x, 2) + POW(p1.y - p2.y, 2)))),2) AS shortest
FROM
    point_2d p1
JOIN
    point_2d p2 
ON (p1.x <= p2.x AND p1.y < p2.y) OR (p1.x <= p2.x AND p1.y > p2.y) OR (p1.x < p2.x AND p1.y = p2.y);
```

## 33. 直线上最近距离

需求：找到这些点中最近两个点之间的距离。

展示效果：

| shortest |
| -------- |
| 1        |

```mysql
CREATE TABLE If Not Exists point (x INT NOT NULL, UNIQUE INDEX x_UNIQUE (x ASC));

insert into point (x) values (-1);
insert into point (x) values (0);
insert into point (x) values (2);
```

最终SQL:

```mysql
SELECT
    MIN(ABS(p1.x - p2.x)) AS shortest
FROM
    point p1
JOIN
    point p2 
ON p1.x != p2.x;
```

## 34. 二级关注者

需求：对每一个关注者(follower)，查询他的关注者数目。

展示效果：

| follower | num  |
| -------- | ---- |
| B        | 2    |
| D        | 1    |

```mysql
Create table If Not Exists follow (followee varchar(255), follower varchar(255));

insert into follow (followee, follower) values ('A', 'B');
insert into follow (followee, follower) values ('B', 'C');
insert into follow (followee, follower) values ('B', 'D');
insert into follow (followee, follower) values ('D', 'E');
```

解释：

以A为主体，A为被关注者，B为被关注者，求出关注B的关注者。这里需要注意，被关注者永远不会被他 / 她自己关注。
将结果按照字典序返回。

最终SQL:

```mysql
select 
      followee as 'follower',
      count(distinct follower) as num
from
      follow
where
      followee in(select follower from follow)
group by 
      1
order by
      1;
```

## 35. 平均工资

需求：写一个查询语句，求出在每一个工资发放日，每个部门的平均工资与公司的平均工资的比较结果 （高 / 低 / 相同）

展示效果：

| pay_month | department_id | comparison |
| --------- | ------------- | ---------- |
| 2017-03   | 1             | higher     |
| 2017-03   | 2             | lower      |
| 2017-02   | 1             | same       |
| 2017-02   | 2             | same       |

```mysql
Create table If Not Exists salary (id int, employee_id int, amount int, pay_date date);
Create table If Not Exists employee (employee_id int, department_id int);

insert into salary (id, employee_id, amount, pay_date) values (1, 1, 9000, '2017/03/31');
insert into salary (id, employee_id, amount, pay_date) values (2, 2, 6000, '2017/03/31');
insert into salary (id, employee_id, amount, pay_date) values (3, 3, 10000, '2017/03/31');
insert into salary (id, employee_id, amount, pay_date) values (4, 1, 7000, '2017/02/28');
insert into salary (id, employee_id, amount, pay_date) values (5, 2, 6000, '2017/02/28');
insert into salary (id, employee_id, amount, pay_date) values (6, 3, 8000, '2017/02/28');

insert into employee (employee_id, department_id) values (1, 1);
insert into employee (employee_id, department_id) values (2, 2);
insert into employee (employee_id, department_id) values (3, 2);
```

解释:

在三月，公司的平均工资是 (9000+6000+10000)/3 = 8333.33...

由于部门 1 里只有一个 employee_id 为 1 的员工，所以部门 1 的平均工资就是此人的工资 9000 。因为 9000 > 8333.33 ，所以比较结果是 'higher'。

第二个部门的平均工资为 employee_id 为 2 和 3 两个人的平均工资，为 (6000+10000)/2=8000 。因为 8000 < 8333.33 ，所以比较结果是 'lower' 。

 在二月用同样的公式求平均工资并比较，比较结果为 'same' ，因为部门 1 和部门 2 的平均工资与公司的平均工资相同，都是 7000 。

最终SQL:

```mysql
select 
      department_salary.pay_month,
      department_id,
      case
          when department_avg>company_avg then 'higher'
          when department_avg<company_avg then 'lower'
          else 'same'
      end as comparison
from
     (select
            department_id,
            avg(amount) as department_avg,
            date_format(pay_date, '%Y-%m') as pay_month
      from 
            salary 
      join
            employee
      on  
            salary.employee_id = employee.employee_id
     group by
            department_id, pay_month
     ) as department_salary
join
    (select
           avg(amount) as company_avg,
           date_format(pay_date, '%Y-%m') as pay_month 
     from 
           salary
     group by
           date_format(pay_date, '%Y-%m')
     ) as company_salary
on 
     department_salary.pay_month = company_salary.pay_month;
```

## 36. 学生地理信息报告

需求：写一个查询语句实现对大洲（continent）列的 透视表 操作，使得每个学生按照姓名的字母顺序依次排列在对应的大洲下面。输出的标题应依次为美洲（America）、亚洲（Asia）和欧洲（Europe）。数据保证来自美洲的学生不少于来自亚洲或者欧洲的学生。

展示效果：

| America | Asia | Europe |
| ------- | ---- | ------ |
| Jack    | Xi   | Pascal |
| Jane    |      |        |

```mysql
Create table If Not Exists student (name varchar(50), continent varchar(7));

insert into student (name, continent) values ('Jane', 'America');
insert into student (name, continent) values ('Pascal', 'Europe');
insert into student (name, continent) values ('Xi', 'Asia');
insert into student (name, continent) values ('Jack', 'America');
```

最终SQL:

```mysql
SELECT 
      MAX(if(A.continent = 'America',A.NAME,NULL)) AS `America`,
      MAX(if(A.continent = 'Asia',A.NAME,NULL)) AS `Asia`,
      MAX(if(A.continent = 'Europe',A.NAME,NULL)) AS `Europe`
FROM
     (SELECT 
             S1.continent,
             S1.NAME,
             S1.row_id,
             COUNT(*) AS `trank`
	  FROM 
	        (SELECT
                    S.*,
                    @row_id:=(@row_id + 1) AS `row_id`
		     FROM
                    student AS S,
                    (SELECT @row_id:=0) AS T
             ) AS S1 
	  JOIN 
	        (SELECT
                    S.*,
                    @n_row_id:=(@n_row_id + 1) AS `n_row_id`
		     FROM 
                    student AS S,
                    (SELECT @n_row_id:=0) AS T
	         ) AS S2 
	  ON 
            (S1.continent = S2.continent AND
            (S1.NAME > S2.NAME OR (S1.NAME = S2.NAME AND S1.row_id >= S2.n_row_id)))
	  group BY
             S1.continent,S1.NAME,S1.row_id
	  order BY
             S1.continent,S1.NAME
      ) AS A
GROUP BY 
      A.trank;
```

## 37. 只出现一次的最大数字

需求：编写一个 SQL 查询，找到只出现过一次的数字中，最大的一个数字。如果没有只出现一次的数字，输出 null 。

展示效果：

| num  |
| ---- |
| 6    |

```mysql
Create table If Not Exists my_numbers (num int);

insert into my_numbers (num) values (8);
insert into my_numbers (num) values (8);
insert into my_numbers (num) values (3);
insert into my_numbers (num) values (3);
insert into my_numbers (num) values (1);
insert into my_numbers (num) values (4);
insert into my_numbers (num) values (5);
insert into my_numbers (num) values (6);
```

最终SQL:

```mysql
select 
      ifnull((SELECT 
                    num
              FROM 
                    my_numbers
              group by 
                    num
              having
                    count(*) = 1
              order by 
                    num desc
              limit 1), null) as num;
```

## 38. 有趣的电影

需求：编写一个 SQL 查询，找出所有影片描述为非 boring (不无聊) 的并且 id 为奇数 的影片，结果请按等级 rating 排列

展示效果：

| id   | movie      | description | rating |
| ---- | ---------- | ----------- | ------ |
| 5    | House card | Interesting | 9.1    |
| 1    | War        | great 3D    | 8.9    |

```mysql
Create table If Not Exists cinema (id int, movie varchar(255), description varchar(255), rating float(2, 1));

insert into cinema (id, movie, description, rating) values (1, 'War', 'great 3D', 8.9);
insert into cinema (id, movie, description, rating) values (2, 'Science', 'fiction', 8.5);
insert into cinema (id, movie, description, rating) values (3, 'irish', 'boring', 6.2);
insert into cinema (id, movie, description, rating) values (4, 'Ice song', 'Fantacy', 8.6);
insert into cinema (id, movie, description, rating) values (5, 'House card', 'Interesting', 9.1);
```

最终SQL:

```mysql
select 
      id,
      movie,
      description,
      rating
from 
      cinema
where 
      mod(id, 2) = 1 and description != 'boring'
order by 
      rating DESC;
```

## 39. 换座位

需求：编写一个 SQL 查询，小美是一所中学的信息科技老师，她有一张 seat 座位表，平时用来储存学生名字和与他们相对应的座位 id。

展示效果：

| id   | student |
| ---- | ------- |
| 1    | Doris   |
| 2    | Abbot   |
| 3    | Green   |
| 4    | Emerson |
| 5    | Jeames  |

```mysql
Create table If Not Exists seat(id int, student varchar(255));

insert into seat (id, student) values (1, 'Abbot');
insert into seat (id, student) values (2, 'Doris');
insert into seat (id, student) values (3, 'Emerson');
insert into seat (id, student) values (4, 'Green');
insert into seat (id, student) values (5, 'Jeames');
```

最终SQL:

```mysql
#方法一
select 
      a.id,
      ifnull(b.student,a.student) as student 
from 
      seat as a 
left 
      join seat as b 
on 
      (a.id%2=1 && a.id=b.id-1) || (a.id%2=0 && a.id=b.id+1) 
order by 
      a.id;

#方法二
select 
      if(id%2=0,id-1,if(id=cnt,id,id+1)) as id,
      student
from 
      (select 
             count(*) as cnt
       from 
             seat
      )as a,
      seat
order by id;

#方法三      
select 
      b.id,
      a.student
from 
      seat as a,
      seat as b,
      (select
             count(*) as cnt
       from
             seat
      ) as c 
where 
      b.id=1^(a.id-1)+1 || (c.cnt%2 && b.id=c.cnt && a.id=c.cnt);
```

## 40. 交换工资

需求：编写一个 SQL 查询，判断三条线段是否能形成一个三角形。

展示效果：

| id   | name | sex  | salary |
| ---- | ---- | ---- | ------ |
| 1    | A    | f    | 2500   |
| 2    | B    | m    | 1500   |
| 3    | C    | f    | 5500   |
| 4    | D    | m    | 500    |

```mysql
create table if not exists salary(id int, name varchar(100), sex char(1), salary int);

insert into salary (id, name, sex, salary) values (1, 'A', 'm', 2500);
insert into salary (id, name, sex, salary) values (2, 'B', 'f', 1500);
insert into salary (id, name, sex, salary) values (3, 'C', 'm', 5500);
insert into salary (id, name, sex, salary) values (4, 'D', 'f', 500);

```

最终SQL:

```mysql
UPDATE salary
SET
    sex = CASE sex
               WHEN 'm' THEN 'f'
               ELSE 'm'
          END;
```

## 41. 买下所有产品的用户

需求：编写一个 SQL 查询，从 Customer 表中查询购买了 Product 表中所有产品的客户的 id。

展示效果：

| customer_id |
| ----------- |
| 1           |
| 3           |

```mysql
Create table If Not Exists Customer (customer_id int, product_key int)
Create table Product (product_key int)

insert into Customer (customer_id, product_key) values (1, 5)
insert into Customer (customer_id, product_key) values (2, '6')
insert into Customer (customer_id, product_key) values (3, 5)
insert into Customer (customer_id, product_key) values (3, '6')
insert into Customer (customer_id, product_key) values (1, '6')

insert into Product (product_key) values (5)
insert into Product (product_key) values ('6')
```

最终SQL:

```mysql
select customer_id
from 
(select customer_id,count(distinct product_key) as num 
 from Customer
 group by customer_id
) t
join (
    select count(product_key) as num
    from Product
) m 
on t.num = m.num;
```

## 42. 合作过至少三次的演员和导演

需求：编写一个 SQL 查询，查询语句获取合作过至少三次的演员和导演的 id 对 (actor_id, director_id)

展示效果：

| actor_id | director_id |
| -------- | ----------- |
| 1        | 15          |

```mysql
Create table If Not Exists ActorDirector (actor_id int, director_id int, timestamp int);

insert into ActorDirector (actor_id, director_id, timestamp) values (1, 1, 0);
insert into ActorDirector (actor_id, director_id, timestamp) values (1, 1, 1);
insert into ActorDirector (actor_id, director_id, timestamp) values (1, 1, 2);
insert into ActorDirector (actor_id, director_id, timestamp) values (1, 2, 3);
insert into ActorDirector (actor_id, director_id, timestamp) values (1, 2, 4);
insert into ActorDirector (actor_id, director_id, timestamp) values (2, 1, 5);
insert into ActorDirector (actor_id, director_id, timestamp) values (2, 1, 6);
```

最终SQL:

```mysql
select 
      actor_id as ACTOR_ID ,
      director_id as DIRECTOR_ID
from 
      ActorDirector 
group by 
      actor_id,director_id 
having 
      count(*)>=3;
```

## 43. 产品销售分析

需求一：获取产品表 `Product` 中所有的 **产品名称 product name** 以及 该产品在 `Sales` 表中相对应的 **上市年份 year** 和 **价格 price**。

展示效果：

| product_name | year | price |
| ------------ | ---- | ----- |
| Nokia        | 2008 | 5000  |
| Nokia        | 2009 | 5000  |
| Apple        | 2011 | 9000  |

```mysql
Create table Sales (sale_id int, product_id int, year int, quantity int, price int);
Create table Product (product_id int, product_name varchar(10));

insert into Sales (sale_id, product_id, year, quantity, price) values (1, 100, 2008, 10, 5000);
insert into Sales (sale_id, product_id, year, quantity, price) values (2, 100, 2009, 12, 5000);
insert into Sales (sale_id, product_id, year, quantity, price) values (7, 200, 2011, 15, 9000);

insert into Product (product_id, product_name) values (100, 'Nokia');
insert into Product (product_id, product_name) values (200, 'Apple');
insert into Product (product_id, product_name) values (300, 'Samsung');
```

最终SQL:

```mysql
select 
      product_name,
      year,
      price
from 
      Sales 
inner join 
      Product
on
      Sales.product_id = Product.product_id;
```

需求二：按产品 id product_id 来统计每个产品的销售总量。

展示效果：

| product_id | total_quantity |
| ---------- | -------------- |
| 100        | 22             |
| 200        | 15             |

最终SQL:

```mysql
SELECT
    product_id, 
    SUM(quantity) as total_quantity
FROM
    Sales
GROUP BY
    product_id;
```

需求三：选出每个销售产品的 第一年 的 产品 id、年份、数量 和 价格。

展示效果：

| product_id | first_year | quantity | price |
| ---------- | ---------- | -------- | ----- |
| 100        | 2008       | 10       | 5000  |
| 200        | 2011       | 15       | 9000  |

最终SQL:

```mysql
select 
      product_id,
      year as first_year, 
      quantity,
      price
from 
      Sales
where 
     (product_id , year) in(
                            select
                                  product_id ,
                                  min(year)
                            from
                                  Sales
                            group by 
                                  product_id
                            );
```

## 44. 项目员工

需求一：查询每一个项目中员工的平均工作年限，精确到小数点后两位。

展示效果：

| project_id | average_years |
| ---------- | ------------- |
| 1          | 2.00          |
| 2          | 2.50          |

```mysql
Create table If Not Exists Project (project_id int, employee_id int);
Create table If Not Exists Employee (employee_id int, name varchar(10), experience_years int);

insert into Project (project_id, employee_id) values (1, 1);
insert into Project (project_id, employee_id) values (1, 2);
insert into Project (project_id, employee_id) values (1, 3);
insert into Project (project_id, employee_id) values (2, 1);
insert into Project (project_id, employee_id) values (2, 4);

insert into Employee (employee_id, name, experience_years) values (1, 'Khaled', 3);
insert into Employee (employee_id, name, experience_years) values (2, 'Ali', 2);
insert into Employee (employee_id, name, experience_years) values (3, 'John', 1);
insert into Employee (employee_id, name, experience_years) values (4, 'Doe', 2);

```

最终SQL:

```mysql
select 
      project_id ,
      round(avg(experience_years),2) as average_years
from 
      Project
left join 
      Employee
on 
      Project.employee_id = Employee.employee_id
group by 
      project_id
order by
      project_id;
```

需求二：报告所有雇员最多的项目。

展示效果：

| project_id |
| ---------- |
| 1          |

最终SQL:

```mysql
SELECT 
      project_id
FROM 
      Project
GROUP BY 
      project_id
HAVING 
      COUNT(employee_id) = (SELECT
                                  MAX(count_employee_id)
                            FROM
                                (SELECT 
                                       project_id,
                                       COUNT(employee_id) AS count_employee_id
                                 FROM
                                       Project
                                 GROUP BY 
                                       project_id
                                ) As temp
                           );
```

需求三：报告在每一个项目中经验最丰富的雇员是谁。如果出现经验年数相同的情况，请报告所有具有最大经验年数的员工。

展示效果：

| project_id | employee_id |
| ---------- | ----------- |
| 1          | 1           |
| 1          | 3           |
| 2          | 1           |

最终SQL:

```mysql
select 
      p.project_id,
      p.employee_id
from 
      Project p
join 
      Employee e
on 
      p.employee_id = e.employee_id
where 
     (p.project_id, e.experience_years) in (select
                                                  p.project_id,
                                                  max(e.experience_years)
                                            from
                                                  project p 
                                            join
                                                  employee e
                                            on 
                                                  p.employee_id = e.employee_id
                                            group by
                                                  p.project_id
                                           );
```

## 45. 销售分析

需求一：编写一个 SQL 查询，查询总销售额最高的销售者，如果有并列的，就都展示出来。

展示效果：

| seller_id |
| --------- |
| 1         |
| 3         |

```mysql
Create table If Not Exists Product (product_id int, product_name varchar(10), unit_price int);
Create table If Not Exists Sales (seller_id int, product_id int,buyer_id int, sale_date date, quantity int, price int);

insert into Product (product_id, product_name, unit_price) values (1, 'S8', 1000);
insert into Product (product_id, product_name, unit_price) values (2, 'G4', 800);
insert into Product (product_id, product_name, unit_price) values (3, 'iPhone', 1400);

insert into Sales (seller_id, product_id, buyer_id, sale_date, quantity, price) values (1, 1, 1,'2019-01-21', 2, 2000);
insert into Sales (seller_id, product_id, buyer_id, sale_date, quantity, price) values (1, 2, 2,'2019-02-17', 1, 800);
insert into Sales (seller_id, product_id, buyer_id, sale_date, quantity, price) values (2, 1, 3,'2019-06-02', 1, 800);
insert into Sales (seller_id, product_id, buyer_id, sale_date, quantity, price) values (3, 3, 3,'2019-05-13', 2, 2800);
```

最终SQL:

```mysql
select 
      seller_id 
from 
      Sales
group by 
      seller_id
having 
      sum(price) = (select
                          sum(price) as ye_ji
                    from  
                          Sales
                    group by
                          seller_id
                    order by 
                          ye_ji desc
                    limit 1
                   );
```

需求二：编写一个 SQL 查询，查询购买了 S8 手机却没有购买 iPhone 的买家。注意这里 S8 和 iPhone 是 Product 表中的产品。

展示效果：

| buyer_id |
| -------- |
| 1        |

最终SQL:

```mysql
#方法一
select 
      distinct buyer_id
from 
      product p 
inner join 
      sales s
on 
      p.product_id=s.product_id
where 
      product_name='S8' and buyer_id not in (select
                                                    buyer_id
                                              from
                                                    product p
                                              inner join
                                                    sales s
                                              on
                                                    p.product_id=s.product_id
                                              where
                                                    product_name='iPhone'
                                              );


#方法二
select 
      s8 as buyer_id
from 
      (select 
             distinct buyer_id s8
       from
             product p 
       inner join 
             sales s
       on
             p.product_id=s.product_id
       where
             product_name='S8') t1
left join
      (select
             distinct buyer_id ip
       from
             product p
       inner join 
             sales s
       on
             p.product_id=s.product_id
       where
             product_name='iPhone'
      ) t2
on 
       s8=ip
where 
       ip is null;
```

需求三：编写一个 SQL 查询，报告2019年春季才售出的产品。即在2019-01-01至2019-03-31（含）之间。

展示效果：

| product_id | product_name |
| ---------- | ------------ |
| 1          | S8           |
| 2          | G4           |

最终SQL:

```mysql
SELECT 
      s.product_id,
      product_name
FROM 
      Sales s
JOIN 
      Product p
ON 
      s.product_id = p.product_id
GROUP BY 
      s.product_id
HAVING 
      sale_date >= '2019-01-01' AND 
      sale_date <= '2019-03-31';
```

## 46. 小众书籍

需求：筛选出订单总量 少于10本 的 书籍 。

展示效果：

| book_id | name               |
| ------- | ------------------ |
| 1       | "Kalila And Demna" |
| 2       | "28 Letters"       |
| 5       | "The Hunger Games" |

```mysql
Create table If Not Exists Books (book_id int, name varchar(50), available_from date);
Create table If Not Exists Orders (order_id int, book_id int, quantity int, dispatch_date date);

insert into Books (book_id, name, available_from) values (1, 'Kalila And Demna', '2010-01-01');
insert into Books (book_id, name, available_from) values (2, '28 Letters', '2012-05-12');
insert into Books (book_id, name, available_from) values (3, 'The Hobbit', '2019-06-10');
insert into Books (book_id, name, available_from) values (4, '13 Reasons Why', '2019-06-01');
insert into Books (book_id, name, available_from) values (5, 'The Hunger Games', '2008-09-21');

insert into Orders (order_id, book_id, quantity, dispatch_date) values (1, 1, 2, '2018-07-26');
insert into Orders (order_id, book_id, quantity, dispatch_date) values (2, 1, 1, '2018-11-05');
insert into Orders (order_id, book_id, quantity, dispatch_date) values (3, 3, 8, '2019-06-11');
insert into Orders (order_id, book_id, quantity, dispatch_date) values (4, 4, 6, '2019-06-05');
insert into Orders (order_id, book_id, quantity, dispatch_date) values (5, 4, 5, '2019-06-20');
insert into Orders (order_id, book_id, quantity, dispatch_date) values (6, 5, 9, '2009-02-02');
insert into Orders (order_id, book_id, quantity, dispatch_date) values (7, 5, 8, '2010-04-13');
```

最终SQL:

```mysql
select 
      t1.book_id,
      t1.name
from 
      (select * from books where available_from <date_sub('2019-06-23',interval 1 Month)) t1 
left join 
      (select 
             *,
             case
                  when dispatch_date between '2018-06-23' and '2019-06-23' then quantity
                  else 0 
              end num 
       from orders
      )  t2 
on 
     t1.book_id=t2.book_id 
group by 
     t1.book_id 
having 
     sum(if(t2.num is null,0,t2.num))<10;
```

## 47. 每日新用户统计

需求一：查询每一个项目中员工的平均工作年限，精确到小数点后两位。

展示效果：

| login_date | user_count |
| ---------- | ---------- |
| 2019-05-01 | 1          |
| 2019-06-21 | 2          |

```mysql
Create table If Not Exists Traffic (user_id int, activity ENUM('login', 'logout', 'jobs', 'groups', 'homepage'), activity_date date);

insert into Traffic (user_id, activity, activity_date) values (1, 'login', '2019-05-01');
insert into Traffic (user_id, activity, activity_date) values (1, 'homepage', '2019-05-01');
insert into Traffic (user_id, activity, activity_date) values (1, 'logout', '2019-05-01');
insert into Traffic (user_id, activity, activity_date) values (2, 'login', '2019-06-21');
insert into Traffic (user_id, activity, activity_date) values (2, 'logout', '2019-06-21');
insert into Traffic (user_id, activity, activity_date) values (3, 'login', '2019-01-01');
insert into Traffic (user_id, activity, activity_date) values (3, 'jobs', '2019-01-01');
insert into Traffic (user_id, activity, activity_date) values (3, 'logout', '2019-01-01');
insert into Traffic (user_id, activity, activity_date) values (4, 'login', '2019-06-21');
insert into Traffic (user_id, activity, activity_date) values (4, 'groups', '2019-06-21');
insert into Traffic (user_id, activity, activity_date) values (4, 'logout', '2019-06-21');
insert into Traffic (user_id, activity, activity_date) values (5, 'login', '2019-03-01');
insert into Traffic (user_id, activity, activity_date) values (5, 'logout', '2019-03-01');
insert into Traffic (user_id, activity, activity_date) values (5, 'login', '2019-06-21');
insert into Traffic (user_id, activity, activity_date) values (5, 'logout', '2019-06-21');
```

最终SQL:

```mysql
select 
    minx as login_date,
    count(user_id) as user_count
from 
   (select 
          user_id,
          min(activity_date) as minx
    from 
          Traffic
    where
          activity='login'
    group by 
          user_id
    having 
          datediff('2019-06-30',minx)<=90
    )s
group by 
    minx;
```

## 48. 每位学生的最高成绩

需求一：查询每一个项目中员工的平均工作年限，精确到小数点后两位。

展示效果：

| student_id | average_years | grade |
| ---------- | ------------- | ----- |
| 1          | 2             | 99    |
| 2          | 2             | 95    |
| 3          | 3             | 82    |

```mysql
Create table If Not Exists Enrollments (student_id int, course_id int, grade int);

insert into Enrollments (student_id, course_id, grade) values (2, 2, 95);
insert into Enrollments (student_id, course_id, grade) values (2, 3, 95);
insert into Enrollments (student_id, course_id, grade) values (1, 1, 90);
insert into Enrollments (student_id, course_id, grade) values (1, 2, 99);
insert into Enrollments (student_id, course_id, grade) values (3, 1, 80);
insert into Enrollments (student_id, course_id, grade) values (3, 2, 75);
insert into Enrollments (student_id, course_id, grade) values (3, 3, 82);
```

最终SQL:

```mysql
select 
      t.student_id,
      if(count(e.grade) > 1 ,min(e.course_id),course_id) as course_id,
      t.max1 as grade
from 
      Enrollments e 
right join
     (select
            student_id,
            max(grade) as max1 
      from 
            Enrollments
      group by
            student_id 
     )t
on
      t.student_id=e.student_id and
      t.max1 = e.grade
group by 
      e.student_id 
order by
      t.student_id;
```

## 49. Reported Posts

需求一：Write an SQL query that reports the number of posts reported yesterday for each report reason. Assume today is 2019-07-05.

展示效果：

| report_reason | report_count |
| ------------- | ------------ |
| spam          | 1            |
| racism        | 2            |

```mysql
Create table If Not Exists Actions (user_id int, post_id int, action_date date, action ENUM('view', 'like', 'reaction', 'comment', 'report', 'share'), extra varchar(10));

insert into Actions (user_id, post_id, action_date, action, extra) values (1, 1, '2019-07-01', 'view', null);
insert into Actions (user_id, post_id, action_date, action, extra) values (1, 1, '2019-07-01', 'like', null);
insert into Actions (user_id, post_id, action_date, action, extra) values (1, 1, '2019-07-01', 'share', null);
insert into Actions (user_id, post_id, action_date, action, extra) values (2, 4, '2019-07-04', 'view', null);
insert into Actions (user_id, post_id, action_date, action, extra) values (2, 4, '2019-07-04', 'report', 'spam');
insert into Actions (user_id, post_id, action_date, action, extra) values (3, 4, '2019-07-04', 'view', null);
insert into Actions (user_id, post_id, action_date, action, extra) values (3, 4, '2019-07-04', 'report', 'spam');
insert into Actions (user_id, post_id, action_date, action, extra) values (4, 3, '2019-07-02', 'view', null);
insert into Actions (user_id, post_id, action_date, action, extra) values (4, 3, '2019-07-02', 'report', 'spam');
insert into Actions (user_id, post_id, action_date, action, extra) values (5, 2, '2019-07-04', 'view', null);
insert into Actions (user_id, post_id, action_date, action, extra) values (5, 2, '2019-07-04', 'report', 'racism');
insert into Actions (user_id, post_id, action_date, action, extra) values (5, 5, '2019-07-04', 'view', null);
insert into Actions (user_id, post_id, action_date, action, extra) values (5, 5, '2019-07-04', 'report', 'racism');
```

最终SQL:

```mysql
select 
      extra report_reason,
      count(distinct post_id) report_count 
from 
      Actions 
where 
      datediff('2019-07-05', action_date) = 1 and
      extra is not null and
      action = 'report' 
group by
      extra;
```

需求二：Write an SQL query to find the average for daily percentage of posts that got removed after being reported as spam, rounded to 2 decimal places.

展示效果：

| average_daily_percent |
| --------------------- |
| 50.00                 |

The percentage for 2019-07-04 is 50% because only one post of two spam reported posts was removed.
The percentage for 2019-07-02 is 100% because one post was reported as spam and it was removed.
The other days had no spam reports so the average is (50 + 100) / 2 = 75%
Note that the output is only one number and that we do not care about the remove dates.

```mysql
create table if not exists Removals (post_id int, remove_date date);

insert into Removals (post_id, remove_date) values (2, '2019-07-20');
insert into Removals (post_id, remove_date) values (3, '2019-07-18');
```

最终SQL:

```mysql
SELECT 
      round(
             SUM(delCount / spamCount * 100) 
             /
             COUNT(DISTINCT action_date),
          2) AS average_daily_percent
FROM 
     (SELECT
             action_date,
             COUNT(distinct a.post_id) AS spamCount, 
             count(distinct b.post_id) AS delCount
	  FROM
             Actions a
      LEFT JOIN
             Removals b 
      ON 
             a.post_id = b.post_id
      where
             a.extra = 'spam'
	  GROUP BY 
             a.action_date
     ) a;
```

## 50. Active Businesses

需求：Write an SQL query to find all active businesses. An active business is a business that has more than one event type with occurences greater than the average occurences of that event type among all businesses.

展示效果：

| business_id |
| ----------- |
| 1           |

Average for 'reviews', 'ads' and 'page views' are (7+3)/2=5, (11+7+6)/3=8, (3+12)/2=7.5 respectively.
Business with id 1 has 7 'reviews' events (more than 5) and 11 'ads' events (more than 8) so it is an active business.

```mysql
Create table If Not Exists Events (business_id int, event_type varchar(10), occurences int);

insert into Events (business_id, event_type, occurences) values (1, 'reviews', 7);
insert into Events (business_id, event_type, occurences) values (3, 'reviews', 3);
insert into Events (business_id, event_type, occurences) values (1, 'ads', 11);
insert into Events (business_id, event_type, occurences) values (2, 'ads', 7);
insert into Events (business_id, event_type, occurences) values (3, 'ads', 6);
insert into Events (business_id, event_type, occurences) values (1, 'page views', 3);
insert into Events (business_id, event_type, occurences) values (2, 'page views', 12);
```

最终SQL:

```mysql
SELECT 
      DISTINCT(business_id) 
FROM 
      Events e 
LEFT JOIN
      Events tmp
ON    
      e.event_type = tmp.event_type 
WHERE
      e.occurences > tmp.avg_count
GROUP BY 
      business_id
HAVING 
      COUNT(1) > 1
```

## 51. User Purchase Platform

需求一：Write an SQL query to find the total number of users and the total amount spent using mobile only, desktop only and both mobile and desktop together for each date.

展示效果：

| spend_date | platform | total_amount | total_users |
| ---------- | -------- | ------------ | ----------- |
| 2019-07-01 | desktop  | 100          | 1           |
| 2019-07-01 | mobile   | 100          | 1           |
| 2019-07-01 | both     | 200          | 1           |
| 2019-07-02 | desktop  | 100          | 1           |
| 2019-07-02 | mobile   | 100          | 1           |
| 2019-07-02 | both     | 0            | 0           |

```mysql
Create table If Not Exists Spending (user_id int, spend_date date, platform ENUM('desktop', 'mobile'), amount int);

insert into Spending (user_id, spend_date, platform, amount) values (1, '2019-07-01', 'mobile', 100);
insert into Spending (user_id, spend_date, platform, amount) values (1, '2019-07-01', 'desktop', 100);
insert into Spending (user_id, spend_date, platform, amount) values (2, '2019-07-01', 'mobile', 100);
insert into Spending (user_id, spend_date, platform, amount) values (2, '2019-07-02', 'mobile', 100);
insert into Spending (user_id, spend_date, platform, amount) values (3, '2019-07-01', 'desktop', 100);
insert into Spending (user_id, spend_date, platform, amount) values (3, '2019-07-02', 'desktop', 100);
```

On 2019-07-01, user 1 purchased using both desktop and mobile, user 2 purchased using mobile only and user 3 purchased using desktop only.
On 2019-07-02, user 2 purchased using mobile only, user 3 purchased using desktop only and no one purchased using both platforms.

最终SQL:

```mysql
select 
      temp1.spend_date,
      temp1.platform, 
      ifnull(temp3.total_amount, 0) total_amount, 
      ifnull(temp3.total_users,0) total_users
from
     (select
            distinct(spend_date),
            p.platform   
      from 
            Spending,
           (select
                  'desktop' as platform union
            select 
                  'mobile' as platform union
            select
                  'both' as platform
           ) as p 
       ) as temp1
left join 
      (select
             spend_date,
             platform,
             sum(amount) as total_amount,
             count(user_id) total_users
       from
            (select
                   spend_date,
                   user_id, 
                   case count(distinct platform)
                        when 1 then platform
                        when 2 then 'both'
                   end as  platform, 
                   sum(amount) as amount
             from 
                   Spending
             group by
                   spend_date,
                   user_id
            ) as temp2
      group by
             spend_date,
             platform
      ) as  temp3
on 
      temp1.platform = temp3.platform and
      temp1.spend_date = temp3.spend_date;
```

## 52. User Activity for the Past 30 Days

需求一：Write an SQL query to find the average for daily percentage of posts that got removed after being reported as spam, rounded to 2 decimal places.

展示效果：

| day        | active_users |
| ---------- | ------------ |
| 2019-07-20 | 2            |
| 2019-07-21 | 2            |

```mysql
Create table If Not Exists Activity (user_id int, session_id int, activity_date date, activity_type ENUM('open_session', 'end_session', 'scroll_down', 'send_message'));

insert into Activity (user_id, session_id, activity_date, activity_type) values (1, 1, '2019-07-20', 'open_session');
insert into Activity (user_id, session_id, activity_date, activity_type) values (1, 1, '2019-07-20', 'scroll_down');
insert into Activity (user_id, session_id, activity_date, activity_type) values (1, 1, '2019-07-20', 'end_session');
insert into Activity (user_id, session_id, activity_date, activity_type) values (2, 4, '2019-07-20', 'open_session');
insert into Activity (user_id, session_id, activity_date, activity_type) values (2, 4, '2019-07-21', 'send_message');
insert into Activity (user_id, session_id, activity_date, activity_type) values (2, 4, '2019-07-21', 'end_session');
insert into Activity (user_id, session_id, activity_date, activity_type) values (3, 2, '2019-07-21', 'open_session');
insert into Activity (user_id, session_id, activity_date, activity_type) values (3, 2, '2019-07-21', 'send_message');
insert into Activity (user_id, session_id, activity_date, activity_type) values (3, 2, '2019-07-21', 'end_session');
insert into Activity (user_id, session_id, activity_date, activity_type) values (4, 3, '2019-06-25', 'open_session');
insert into Activity (user_id, session_id, activity_date, activity_type) values (4, 3, '2019-06-25', 'end_session');
```

最终SQL:

```mysql
select 
      t.activity_date as day,
      count(distinct t.user_id) as active_users
from 
     (select 
            activity_date,
            user_id
      from
            Activity
      where 
            datediff('2019-07-27',activity_date) <30 and
            datediff( '2019-07-27', activity_date) >=0
      group by
            user_id,
            activity_date
      having 
            count(activity_type)>0
     ) as t
group by 
      t.activity_date;
```

需求二：编写SQL查询以查找截至2019年7月27日（含）的30天内每个用户的平均会话数，四舍五入到小数点后两位。我们要为用户计算的会话是在该时间段内至少进行了一项活动的会话。

展示效果：

| average_sessions_per_user |
| ------------------------- |
| 1.00                      |

最终SQL:

```mysql
SELECT 
      ROUND(IFNULL(AVG(count_session_id), 0), 2) AS average_sessions_per_user
FROM
     (SELECT
            COUNT(DISTINCT session_id) AS count_session_id
      FROM
            Activity
      WHERE 
            activity_date BETWEEN DATE_SUB("2019-07-27", INTERVAL 29 DAY) AND "2019-07-27"
      GROUP BY
            user_id
      ) AS temp;
```

## 52. 文章浏览

需求一：查询每一个项目中员工的平均工作年限，精确到小数点后两位。

展示效果：

| id   |
| ---- |
| 4    |
| 7    |

```mysql
Create table If Not Exists Views (article_id int, author_id int, viewer_id int, view_date date);
Truncate table Views;
insert into Views (article_id, author_id, viewer_id, view_date) values (1, 3, 5, '2019-08-01');
insert into Views (article_id, author_id, viewer_id, view_date) values (3, 4, 5, '2019-08-01');
insert into Views (article_id, author_id, viewer_id, view_date) values (1, 3, '6', '2019-08-02');
insert into Views (article_id, author_id, viewer_id, view_date) values (2, '7', '7', '2019-08-01');
insert into Views (article_id, author_id, viewer_id, view_date) values (2, '7', '6', '2019-08-02');
insert into Views (article_id, author_id, viewer_id, view_date) values (4, '7', 1, '2019-07-22');
insert into Views (article_id, author_id, viewer_id, view_date) values (3, 4, 4, '2019-07-21');
insert into Views (article_id, author_id, viewer_id, view_date) values (3, 4, 4, '2019-07-21');
```

最终SQL:

```mysql
select 
      distinct viewer_id as id
from 
      Views 
where 
      viewer_id = author_id
order by 
      viewer_id;
```

需求二：Write an SQL query to find all the people who viewed more than one article on the same date, sorted in ascending order by their id.

展示效果：

| project_id |
| ---------- |
| 5          |
| 6          |

最终SQL:

```mysql
SELECT 
      DISTINCT viewer_id as id 
FROM 
      views
GROUP BY 
      viewer_id,view_date
HAVING
      COUNT(DISTINCT article_id)>=2
ORDER BY 
      viewer_id;
```

## 53. Market Analysis

需求一：Write an SQL query to find for each user, the join date and the number of orders they made as a buyer in 2019.

展示效果：

| buyer_id | join_date  | orders_in_2019 |
| -------- | ---------- | -------------- |
| 1        | 2018-01-01 | 1              |
| 2        | 2018-02-09 | 2              |
| 3        | 2018-01-19 | 0              |
| 4        | 2018-05-21 | 0              |

```mysql
Create table If Not Exists Users (user_id int, join_date date, favorite_brand varchar(10));
create table if not exists Orders (order_id int, order_date date, item_id int, buyer_id int, seller_id int);
create table if not exists Items (item_id int, item_brand varchar(10));

insert into Users (user_id, join_date, favorite_brand) values (1, '2018-01-01', 'Lenovo');
insert into Users (user_id, join_date, favorite_brand) values (2, '2018-02-09', 'Samsung');
insert into Users (user_id, join_date, favorite_brand) values (3, '2018-01-19', 'LG');
insert into Users (user_id, join_date, favorite_brand) values (4, '2018-05-21', 'HP');

insert into Orders (order_id, order_date, item_id, buyer_id, seller_id) values (1, '2019-08-01', 4, 1, 2);
insert into Orders (order_id, order_date, item_id, buyer_id, seller_id) values (2, '2018-08-02', 2, 1, 3);
insert into Orders (order_id, order_date, item_id, buyer_id, seller_id) values (3, '2019-08-03', 3, 2, 3);
insert into Orders (order_id, order_date, item_id, buyer_id, seller_id) values (4, '2018-08-04', 1, 4, 2);
insert into Orders (order_id, order_date, item_id, buyer_id, seller_id) values (5, '2018-08-04', 1, 3, 4);
insert into Orders (order_id, order_date, item_id, buyer_id, seller_id) values (6, '2019-08-05', 2, 2, 4);

insert into Items (item_id, item_brand) values (1, 'Samsung');
insert into Items (item_id, item_brand) values (2, 'Lenovo');
insert into Items (item_id, item_brand) values (3, 'LG');
insert into Items (item_id, item_brand) values (4, 'HP');
```

最终SQL:

```mysql
SELECT 
       user_id AS buyer_id,
       join_date,
       IFNULL(COUNT(buyer_Id), 0) AS orders_in_2019
FROM 
       Users u 
LEFT JOIN
       Orders o
ON  
       U.user_id = o.buyer_id AND
       order_date >= '2019-01-01'
GROUP BY
       user_id
ORDER BY
       user_id;


```

需求二：Write an SQL query to find for each user, whether the brand of the second item (by date) they sold is their favorite brand. If a user sold less than two items, report the answer for that user as no.

展示效果：

| product_id | 2nd_item_fav_brand |
| ---------- | ------------------ |
| 1          | no                 |
| 2          | no                 |
| 3          | yes                |
| 4          | no                 |

最终SQL:

```mysql
select 
      user_id as seller_id,
      case
         when t3.item_brand is null then 'no'
         when t3.item_brand = u.favorite_brand then 'yes'
         else 'no' 
      end as 2nd_item_fav_brand
from 
      Users u
left join 
     (select
            order_id,
            order_date,
            t2.item_id,
            buyer_id,
            seller_id,
            i.item_brand as item_brand
      from 
           (select
                  order_id,
                  order_date,
                  item_id,
                  buyer_id,
                  seller_id,
                  cast(if(@prev = seller_id,@rank := @rank + 1,@rank := 1) as unsigned) as rank,
                  @prev := seller_id as prev
            from 
                 (select
                         order_id,
                         order_date,
                         item_id,
                         buyer_id,
                         seller_id
                  from
                         Orders
                  group by
                         seller_id,
                         order_date
                 ) as t1,
                 (select 
                         @rank := 0,
                         @prev := null
                 ) as init) t2,
                 Items i
            where
                  rank = 2 and
                  t2.item_id = i.item_id
     ) as t3
on 
       u.user_id = t3.seller_id;
```

## 54. Product Price at a Given Date

需求一：Write an SQL query to find the prices of all products on 2019-08-16. Assume the price of all products before any change is 10.

展示效果：

| project_id | price |
| ---------- | ----- |
| 2          | 50    |
| 1          | 35    |
| 3          | 10    |

```mysql
Create table If Not Exists Products (product_id int, new_price int, change_date date);
Truncate table Products;
insert into Products (product_id, new_price, change_date) values (1, 20, '2019-08-14');
insert into Products (product_id, new_price, change_date) values (2, 50, '2019-08-14');
insert into Products (product_id, new_price, change_date) values (1, 30, '2019-08-15');
insert into Products (product_id, new_price, change_date) values (1, 35, '2019-08-16');
insert into Products (product_id, new_price, change_date) values (2, 65, '2019-08-17');
insert into Products (product_id, new_price, change_date) values (3, 20, '2019-08-18');
```

最终SQL:

```mysql
SELECT
    * 
FROM 
    (SELECT 
           product_id,
           new_price AS price
     FROM 
           Products
     WHERE (product_id, change_date) IN (
                                          SELECT
                                                product_id,
                                                MAX(change_date)
                                          FROM 
                                                Products
                                          WHERE 
                                                change_date <= '2019-08-16'
                                          GROUP BY
                                                product_id
                                         )
     UNION
     SELECT
            DISTINCT product_id, 10 AS price
     FROM 
            Products
     WHERE 
            product_id NOT IN (SELECT
                                     product_id
                               FROM  
                                     Products
                               WHERE change_date <= '2019-08-16'
                              )
     ) tmp
ORDER BY 
     price DESC;
```

## 55. Immediate Food Delivery

需求一：Write an SQL query to find the percentage of immediate orders in the table, rounded to 2 decimal places.

展示效果：

| immediate_percentage |
| -------------------- |
| 42.86                |

```mysql
Create table If Not Exists Delivery (delivery_id int, customer_id int, order_date date, customer_pref_delivery_date date);
Truncate table Delivery;
insert into Delivery (delivery_id, customer_id, order_date, customer_pref_delivery_date) values (1, 1, '2019-08-01', '2019-08-02');
insert into Delivery (delivery_id, customer_id, order_date, customer_pref_delivery_date) values (2, 5, '2019-08-02', '2019-08-02');
insert into Delivery (delivery_id, customer_id, order_date, customer_pref_delivery_date) values (3, 1, '2019-08-11', '2019-08-11');
insert into Delivery (delivery_id, customer_id, order_date, customer_pref_delivery_date) values (4, 3, '2019-08-24', '2019-08-26');
insert into Delivery (delivery_id, customer_id, order_date, customer_pref_delivery_date) values (5, 4, '2019-08-21', '2019-08-22');
insert into Delivery (delivery_id, customer_id, order_date, customer_pref_delivery_date) values (6, 2, '2019-08-11', '2019-08-13');
insert into Delivery (delivery_id, customer_id, order_date, customer_pref_delivery_date) values (7, 4, '2019-08-09', '2019-08-09');
```

最终SQL:

```mysql
SELECT ROUND(
    (SELECT COUNT(delivery_id)
    FROM Delivery
    WHERE order_date = customer_pref_delivery_date)
    * 100 / COUNT(delivery_id)
        , 2) 
AS immediate_percentage
FROM Delivery;
```

需求二：Write an SQL query to find the percentage of immediate orders in the first orders of all customers, rounded to 2 decimal places.

展示效果：

| immediate_percentage |
| -------------------- |
| 40.00                |

最终SQL:

```mysql
select
      round(
               count(case when d.order_date = d.customer_pref_delivery_date then 1 end)
               * 
               100/count(*),
            2) as immediate_percentage
from 
     Delivery d,
    (select
           delivery_id,
           customer_id,
           min(order_date) as order_date
     from
           Delivery
     group by
           customer_id
    ) as t
where
     d.customer_id = t.customer_id
     and d.order_date = t.order_date;
```

## 56. 重新格式化部门表

需求一：编写一个 SQL 查询来重新格式化表，使得新的表中有一个部门 id 列和一些对应 每个月 的收入（revenue）列。

展示效果：

| id   | Jan_Revenue | Feb_Revenue | Mar_Revenue | ...  | Dec_Revenue |
| ---- | ----------- | ----------- | ----------- | ---- | ----------- |
| 1    | 8000        | 7000        | 6000        | ...  | null        |
| 2    | 9000        | null        | null        | ...  | null        |
| 3    | null        | 10000       | null        | ...  | null        |

```mysql
Create table If Not Exists Department (id int, revenue int, month varchar(5));
Truncate table Department;
insert into Department (id, revenue, month) values (1, 8000, 'Jan');
insert into Department (id, revenue, month) values (2, 9000, 'Jan');
insert into Department (id, revenue, month) values (3, 10000, 'Feb');
insert into Department (id, revenue, month) values (1, 7000, 'Feb');
insert into Department (id, revenue, month) values (1, 6000, 'Mar');
```

最终SQL:

```mysql
SELECT 
      DISTINCT id AS "id",
      SUM(IF (month = "Jan", revenue, null)) AS "Jan_Revenue",
      SUM(IF (month = "Feb", revenue, null)) AS "Feb_Revenue",
      SUM(IF (month = "Mar", revenue, null)) AS "Mar_Revenue",
      SUM(IF (month = "Apr", revenue, null)) AS "Apr_Revenue",
      SUM(IF (month = "May", revenue, null)) AS "May_Revenue",
      SUM(IF (month = "Jun", revenue, null)) AS "Jun_Revenue",
      SUM(IF (month = "Jul", revenue, null)) AS "Jul_Revenue",
      SUM(IF (month = "Aug", revenue, null)) AS "Aug_Revenue",
      SUM(IF (month = "Sep", revenue, null)) AS "Sep_Revenue",
      SUM(IF (month = "Oct", revenue, null)) AS "Oct_Revenue",
      SUM(IF (month = "Nov", revenue, null)) AS "Nov_Revenue",
      SUM(IF (month = "Dec", revenue, null)) AS "Dec_Revenue" 
FROM 
      Department
GROUP BY id;
```

## 57. 每月交易

需求一：查询每一个项目中员工的平均工作年限，精确到小数点后两位。

展示效果：

| month   | country | trans_count | approved_count | trans_total_amount | approved_total_amount |
| ------- | ------- | ----------- | -------------- | ------------------ | --------------------- |
| 2018-12 | US      | 2           | 1              | 3000               | 1000                  |
| 2019-01 | US      | 1           | 1              | 2000               | 2000                  |
| 2019-01 | DE      | 1           | 1              | 2000               | 2000                  |
| 2019-05 | US      | 2           | 1              | 3000               | 1000                  |
| 2019-06 | US      | 3           | 2              | 12000              | 8000                  |

```mysql
create table if not exists Transactions (id int, country varchar(4), state enum('approved', 'declined'), amount int, trans_date date);
create table if not exists Chargebacks (trans_id int, trans_date date);

insert into Transactions (id, country, state, amount, trans_date) values (101, 'US', 'approved', 1000, '2018-12-18');
insert into Transactions (id, country, state, amount, trans_date) values (102, 'US', 'declined', 2000, '2018-12-19');
insert into Transactions (id, country, state, amount, trans_date) values (103, 'US', 'approved', 2000, '2019-01-01');
insert into Transactions (id, country, state, amount, trans_date) values (104, 'DE', 'approved', 2000, '2019-01-07');
insert into Transactions (id, country, state, amount, trans_date) values (105, 'US', 'approved', 1000, '2019-05-18');
insert into Transactions (id, country, state, amount, trans_date) values (106, 'US', 'declined', 2000, '2019-05-19');
insert into Transactions (id, country, state, amount, trans_date) values (107, 'US', 'approved', 3000, '2019-06-10');
insert into Transactions (id, country, state, amount, trans_date) values (108, 'US', 'declined', 4000, '2019-06-13');
insert into Transactions (id, country, state, amount, trans_date) values (109, 'US', 'approved', 5000, '2019-06-15');

insert into Chargebacks (trans_id, trans_date) values (102, '2019-05-29');
insert into Chargebacks (trans_id, trans_date) values (101, '2019-06-30');
insert into Chargebacks (trans_id, trans_date) values (105, '2019-09-18');
```

最终SQL:

```mysql
select
      date_format(trans_date,'%Y-%m') as month,
      country,
      count(*) as trans_count,
      sum(if(state='approved',1,0)) as approved_count,
      sum(amount) as trans_total_amount,
      sum(if(state='approved',amount,0)) as approved_total_amount
from 
      Transactions t
group by
      date_format(trans_date,'%Y-%m'),
      country;
```

需求二：编写一个 SQL 查询，以查找每个月和每个国家/地区的已批准交易的数量及其总金额、退单的数量及其总金额。

展示效果：

| month   | country | approved_count | approved_amount | chargeback_count | chargeback_amount |
| ------- | ------- | -------------- | --------------- | ---------------- | ----------------- |
| 2018-12 | US      | 1              | 1000            | 0                | 0                 |
| 2019-01 | DE      | 1              | 2000            | 0                | 0                 |
| 2019-01 | US      | 1              | 2000            | 0                | 0                 |
| 2019-05 | US      | 1              | 1000            | 1                | 2000              |
| 2019-06 | US      | 2              | 8000            | 1                | 1000              |
| 2019-09 | US      | 0              | 0               | 1                | 1000              |

最终SQL:

```mysql
SELECT
       month as MONTH,
       country as COUNTRY,
       SUM(IF(type = 'approved', 1, 0)) AS APPROVED_COUNT,
       SUM(IF(type = 'approved', amount, 0)) AS APPROVED_AMOUNT,
       SUM(IF(type = 'chargeback', 1, 0)) AS CHARGEBACK_COUNT,
       SUM(IF(type = 'chargeback', amount, 0)) AS CHARGEBACK_AMOUNT
FROM 
      (SELECT 
              date_format(t.trans_date,'%Y-%m') AS month,
              t.country,
              amount,
              'approved' AS type
        FROM
              Transactions AS t
        WHERE 
              state = 'approved'
        UNION ALL
        SELECT
              date_format(c.trans_date,'%Y-%m') AS month,
              t.country,
              amount,
              'chargeback' AS type
         FROM 
              Transactions AS t
         INNER JOIN
              Chargebacks AS c 
         ON t.id = c.trans_id
         ) AS tt
GROUP BY 
         tt.month,
         tt.country;
```

## 58. 锦标赛优胜者

需求一：编写一个 SQL 查询来查找每组中的获胜者。每组的获胜者是在组内得分最高的选手。如果平局，得分最低的选手获胜。

展示效果：

| group_id | player_id |
| -------- | --------- |
| 1        | 15        |
| 2        | 35        |
| 3        | 40        |

```mysql
Create table If Not Exists Players (player_id int, group_id int);
Create table If Not Exists Matches (match_id int, first_player int, second_player int, first_score int, second_score int);
Truncate table Players;
insert into Players (player_id, group_id) values (10, 2);
insert into Players (player_id, group_id) values (15, 1);
insert into Players (player_id, group_id) values (20, 3);
insert into Players (player_id, group_id) values (25, 1);
insert into Players (player_id, group_id) values (30, 1);
insert into Players (player_id, group_id) values (35, 2);
insert into Players (player_id, group_id) values (40, 3);
insert into Players (player_id, group_id) values (45, 1);
insert into Players (player_id, group_id) values (50, 2);
Truncate table Matches;
insert into Matches (match_id, first_player, second_player, first_score, second_score) values (1, 15, 45, 3, 0);
insert into Matches (match_id, first_player, second_player, first_score, second_score) values (2, 30, 25, 1, 2);
insert into Matches (match_id, first_player, second_player, first_score, second_score) values (3, 30, 15, 2, 0);
insert into Matches (match_id, first_player, second_player, first_score, second_score) values (4, 40, 20, 5, 2);
insert into Matches (match_id, first_player, second_player, first_score, second_score) values (5, 35, 50, 1, 1);
```

最终SQL:

```mysql
select 
      group_id,
      player_id
from 
    (select 
           group_id,
           player_id,
           sum(
               case
                   when player_id = first_player then first_score
                   when player_id = second_player then second_score
               end
               ) as totalScores
     from 
          Players p,
          Matches m
     where
          p.player_id = m.first_player or
          p.player_id = m.second_player
     group by
          group_id,
          player_id
     order by
          group_id,
          totalScores desc,
          player_id
    ) as temp
group by 
     group_id
order by 
     group_id,
     totalScores desc,
     player_id;
```

## 59. Last Person to Fit in the Elevator

需求：Queue table is ordered by turn in the example for simplicity.
In the example George Washington(id 5), John Adams(id 3) and Thomas Jefferson(id 6) will enter the elevator as their weight sum is 250 + 350 + 400 = 1000.
Thomas Jefferson(id 6) is the last person to fit in the elevator because he has the last turn in these three people.

展示效果：

| person_name      |
| ---------------- |
| Thomas Jefferson |

```mysql
Create table If Not Exists Queue (person_id int, person_name varchar(30), weight int, turn int);
Truncate table Queue;
insert into Queue (person_id, person_name, weight, turn) values (5, 'George Washington', 250, 1);
insert into Queue (person_id, person_name, weight, turn) values (4, 'Thomas Jefferson', 175, 5);
insert into Queue (person_id, person_name, weight, turn) values (3, 'John Adams', 350, 2);
insert into Queue (person_id, person_name, weight, turn) values (6, 'Thomas Jefferson', 400, 3);
insert into Queue (person_id, person_name, weight, turn) values (1, 'James Elephant', 500, 6);
insert into Queue (person_id, person_name, weight, turn) values (2, 'Will Johnliams', 200, 4);
```

最终SQL:

```mysql
select 
      person_name
from 
      Queue q1
where 
     (select
           sum(weight)
      from
           Queue
      where turn <= q1.turn) <= 1000
order by 
      turn desc 
limit 1;
```

## 60. Queries Quality and Percentage

需求：Write an SQL query to find each query_name, the quality and poor_query_percentage.

展示效果：

| query_name | quality | poor_query_percentage |
| ---------- | ------- | --------------------- |
| Dog        | 2.50    | 33.33                 |
| Cat        | 0.66    | 33.33                 |

```mysql
Create table If Not Exists Queries (query_name varchar(30), result varchar(50), position int, rating int);
Truncate table Queries;
insert into Queries (query_name, result, position, rating) values ('Dog', 'Golden Retriever', 1, 5);
insert into Queries (query_name, result, position, rating) values ('Dog', 'German Shepherd', 2, 5);
insert into Queries (query_name, result, position, rating) values ('Dog', 'Mule', '200', 1);
insert into Queries (query_name, result, position, rating) values ('Cat', 'Shirazi', 5, 2);
insert into Queries (query_name, result, position, rating) values ('Cat', 'Siamese', 3, 3);
insert into Queries (query_name, result, position, rating) values ('Cat', 'Sphynx', 7, 4);
```

最终SQL:

```mysql
select
      query_name,
      round(avg(rating/position), 2) as quality ,
      round((count(if(rating<3, True, null)) / count(query_name)) *100 , 2) as poor_query_percentage
from
      Queries
group by 
      query_name;
```

## 61. Team Scores in Football Tournament

需求一：You would like to compute the scores of all teams after all matches. Points are awarded as follows:
A team receives three points if they win a match (Score strictly more goals than the opponent team).
A team receives one point if they draw a match (Same number of goals as the opponent team).
A team receives no points if they lose a match (Score less goals than the opponent team).
Write an SQL query that selects the team_id, team_name and num_points of each team in the tournament after all described matches. Result table should be ordered by num_points (decreasing order). In case of a tie, order the records by team_id (increasing order).

展示效果：

| team_id | team_name   | num_points |
| ------- | ----------- | ---------- |
| 10      | Leetcode FC | 7          |
| 20      | NewYork FC  | 3          |
| 50      | Toronto FC  | 3          |
| 30      | Atlanta FC  | 1          |
| 40      | Chicago FC  | 0          |

```mysql
Create table If Not Exists Teams (team_id int, team_name varchar(30));
Create table If Not Exists Matches (match_id int, host_team int, guest_team int, host_goals int, guest_goals int);
Truncate table Teams;
insert into Teams (team_id, team_name) values (10, 'Leetcode FC');
insert into Teams (team_id, team_name) values (20, 'NewYork FC');
insert into Teams (team_id, team_name) values (30, 'Atlanta FC');
insert into Teams (team_id, team_name) values (40, 'Chicago FC');
insert into Teams (team_id, team_name) values (50, 'Toronto FC');
Truncate table Matches;
insert into Matches (match_id, host_team, guest_team, host_goals, guest_goals) values (1, 10, 20, 30, 0);
insert into Matches (match_id, host_team, guest_team, host_goals, guest_goals) values (2, 30, 10, 2, 2);
insert into Matches (match_id, host_team, guest_team, host_goals, guest_goals) values (3, 10, 50, 5, 1);
insert into Matches (match_id, host_team, guest_team, host_goals, guest_goals) values (4, 20, 30, 1, 0);
insert into Matches (match_id, host_team, guest_team, host_goals, guest_goals) values (5, 50, 30, 1, 0);
```

最终SQL:

```mysql
SELECT 
     *
FROM
    (SELECT 
           a.team_id,
           MAX(team_name) AS team_name,
           SUM(
                CASE 
			        WHEN a.team_id = b.host_team THEN 
				    CASE 
					    WHEN b.host_goals > b.guest_goals THEN 3
					    WHEN b.host_goals = b.guest_goals THEN 1
			            ELSE 0
				    END
			        WHEN a.team_id = b.guest_team THEN 
				    CASE 
					    WHEN b.host_goals < b.guest_goals THEN 3
					    WHEN b.host_goals = b.guest_goals THEN 1
					    ELSE 0
				    END
			        ELSE 0
		       END
           ) AS num_points
	FROM 
         Teams a
    LEFT JOIN
         Matches b
    ON 
         a.team_id = b.host_team OR 
         a.team_id = b.guest_team
	GROUP BY a.team_id
    ) a 
ORDER BY
    a.num_points DESC,
    a.team_id;
```

## 62. 报告系统状态的连续日期

需求：系统 每天 运行一个任务。每个任务都独立于先前的任务。任务的状态可以是失败或是成功。编写一个 SQL 查询 2019-01-01 到 2019-12-31 期间任务连续同状态 period_state 的起止日期（start_date 和 end_date）。即如果任务失败了，就是失败状态的起止日期，如果任务成功了，就是成功状态的起止日期。最后结果按照起始日期 start_date 排序

展示效果：

| period_state | start date | end date   |
| ------------ | ---------- | ---------- |
| present      | 2019-01-01 | 2019-01-03 |
| missing      | 2019-01-04 | 2019-01-05 |
| present      | 2019-01-06 | 2019-01-06 |

```mysql
Create table If Not Exists Failed (fail_date date);
Create table If Not Exists Succeeded (success_date date);
Truncate table Failed;
insert into Failed (fail_date) values ('2018-12-28');
insert into Failed (fail_date) values ('2018-12-29');
insert into Failed (fail_date) values ('2019-01-04');
insert into Failed (fail_date) values ('2019-01-05');
Truncate table Succeeded;
insert into Succeeded (success_date) values ('2018-12-30');
insert into Succeeded (success_date) values ('2018-12-31');
insert into Succeeded (success_date) values ('2019-01-01');
insert into Succeeded (success_date) values ('2019-01-02');
insert into Succeeded (success_date) values ('2019-01-03');
insert into Succeeded (success_date) values ('2019-01-06');
```

最终SQL:

```mysql
select 
      if(str=1,'succeeded','failed') as period_state ,
      min(date) as start_date,
      max(date) as end_date
from 
     (select 
            @diff := @diff+ if(num = 1 , 1,0) as diff,
            date,
            str
      from
           (select 
                  case 
                      when @str = str and  date_add(@pre,interval 1 day) = date  then @num := @num +1
                      when @str:=str then  @num := 1
                      else @num := 1
                  end as num,
                  @pre := date,
                  date,
                  str
            from 
                 (select 
                        fail_date as date ,
                        0 as 'str'
                  from 
                        Failed 
                  union  
                  select
                        success_date,
                        1
                  from 
                        Succeeded 
                 ) s,
                 (select @pre:=null,@num:=0,@str := null) s1
            where 
                  date between '2019-01-01' and '2019-12-31'
            order by
                  date 
           ) s,
           (select @diff:=0)  s1
     ) ys
group by 
      diff,
      str;
```

## 62. 每个帖子的评论数

需求一：编写 SQL 语句以查找每个帖子的评论数。结果表应包含帖子的 post_id 和对应的评论数 number_of_comments 并且按 post_id 升序排列。Submissions 可能包含重复的评论。您应该计算每个帖子的唯一评论数。Submissions 可能包含重复的帖子。您应该将它们视为一个帖子。

展示效果：

| post_id | number_of_comments |
| ------- | ------------------ |
| 1       | 3                  |
| 2       | 2                  |
| 12      | 0                  |

```mysql
Create table If Not Exists Submissions (sub_id int, parent_id int);
Truncate table Submissions;
insert into Submissions (sub_id, parent_id) values (1, null);
insert into Submissions (sub_id, parent_id) values (2, null);
insert into Submissions (sub_id, parent_id) values (1, null);
insert into Submissions (sub_id, parent_id) values (12, null);
insert into Submissions (sub_id, parent_id) values (3, 1);
insert into Submissions (sub_id, parent_id) values (5, 2);
insert into Submissions (sub_id, parent_id) values (3, 1);
insert into Submissions (sub_id, parent_id) values (4, 1);
insert into Submissions (sub_id, parent_id) values (9, 1);
insert into Submissions (sub_id, parent_id) values (10, 2);
insert into Submissions (sub_id, parent_id) values (6, 7);
```

最终SQL:

```mysql
SELECT
	  post_id,
	  COUNT( DISTINCT S2.sub_id ) AS number_of_comments 
FROM
	(SELECT
           DISTINCT sub_id AS post_id 
     FROM 
           Submissions
     WHERE 
           parent_id IS NULL
    ) S1
LEFT JOIN
     Submissions S2
ON
     S1.post_id = S2.parent_id 
GROUP BY
     S1.post_id;
```

## 63. Average Selling Price

需求一：Write an SQL query to find the average selling price for each product.  average_price should be rounded to 2 decimal places.

The query result format is in the following example:

展示效果：

| product_id | average_price |
| ---------- | ------------- |
| 1          | 6.96          |
| 2          | 16.96         |

```mysql
Create table If Not Exists Prices (product_id int, start_date date, end_date date, price int);
Create table If Not Exists UnitsSold (product_id int, purchase_date date, units int);
Truncate table Prices;
insert into Prices (product_id, start_date, end_date, price) values (1, '2019-02-17', '2019-02-28', 5);
insert into Prices (product_id, start_date, end_date, price) values (1, '2019-03-01', '2019-03-22', 20);
insert into Prices (product_id, start_date, end_date, price) values (2, '2019-02-01', '2019-02-20', 15);
insert into Prices (product_id, start_date, end_date, price) values (2, '2019-02-21', '2019-03-31', 30);
Truncate table UnitsSold;
insert into UnitsSold (product_id, purchase_date, units) values (1, '2019-02-25', 100);
insert into UnitsSold (product_id, purchase_date, units) values (1, '2019-03-01', 15);
insert into UnitsSold (product_id, purchase_date, units) values (2, '2019-02-10', 200);
insert into UnitsSold (product_id, purchase_date, units) values (2, '2019-03-22', 30);
```

最终SQL:

```mysql
select
      product_id,
      round(sum(a)/sum(units),2) as average_price
from
   (select 
          p.product_id as product_id,
          price,units,
          price * units as a
    from 
          Prices p 
    left join
          UnitsSold u
    on 
          p.product_id=u.product_id and 
          purchase_date<=end_date and 
          purchase_date>=start_date
   )t
group by 
    product_id;
```

## 64. Page Recommendations 

需求一：Write an SQL query to recommend pages to the user with user_id = 1 using the pages that your friends liked. It should not recommend pages you already liked. Return result table in any order without duplicates.

展示效果：

| recommended_page |
| ---------------- |
| 23               |
| 24               |
| 56               |
| 33               |
| 77               |

```mysql
Create table If Not Exists Friendship (user1_id int, user2_id int);
Create table If Not Exists Likes (user_id int, page_id int);
Truncate table Friendship;
insert into Friendship (user1_id, user2_id) values (1, 2);
insert into Friendship (user1_id, user2_id) values (1, 3);
insert into Friendship (user1_id, user2_id) values (1, 4);
insert into Friendship (user1_id, user2_id) values (2, 3);
insert into Friendship (user1_id, user2_id) values (2, 4);
insert into Friendship (user1_id, user2_id) values (2, 5);
insert into Friendship (user1_id, user2_id) values (6, 1);
Truncate table Likes;
insert into Likes (user_id, page_id) values (1, 88);
insert into Likes (user_id, page_id) values (2, 23);
insert into Likes (user_id, page_id) values (3, 24);
insert into Likes (user_id, page_id) values (4, 56);
insert into Likes (user_id, page_id) values (5, 11);
insert into Likes (user_id, page_id) values (6, 33);
insert into Likes (user_id, page_id) values (2, 77);
insert into Likes (user_id, page_id) values (3, 77);
insert into Likes (user_id, page_id) values (6, 88);
```

解释：

User one is friend with users 2, 3, 4 and 6.

Suggested pages are 23 from user 2, 24 from user 3, 56 from user 3 and 33 from user 6.

Page 77 is suggested from both user 2 and user 3.
Page 88 is not suggested because user 1 already likes it.

最终SQL:

```mysql
select 
      distinct page_id as recommended_page
from 
      Likes,
      friendship
where 
      page_id not in(select 
                            page_id 
                     from 
                            likes
                     where 
                            user_id=1
                    ) and
      user_id in (select
                        user1_id
                  from
                        friendship
                  where user2_id=1
                 ) or
      user_id in (select
                        user2_id 
                  from 
                        friendship 
                  where
                        user1_id=1);
```

## 65. All People Report to the Given Manager 

需求一：Write an SQL query to find employee_id of all employees that directly or indirectly report their work to the head of the company.

The indirect relation between managers will not exceed 3 managers as the company is small. Return result table in any order without duplicates.

展示效果：

| employee_id |
| ----------- |
| 2           |
| 77          |
| 4           |
| 7           |

```mysql
Create table If Not Exists Employees (employee_id int, employee_name varchar(30), manager_id int);
Truncate table Employees;
insert into Employees (employee_id, employee_name, manager_id) values (1, 'Boss', 1);
insert into Employees (employee_id, employee_name, manager_id) values (3, 'Alice', 3);
insert into Employees (employee_id, employee_name, manager_id) values (2, 'Bob', 1);
insert into Employees (employee_id, employee_name, manager_id) values (4, 'Daniel', 2);
insert into Employees (employee_id, employee_name, manager_id) values (7, 'Luis', 4);
insert into Employees (employee_id, employee_name, manager_id) values (8, 'John', 3);
insert into Employees (employee_id, employee_name, manager_id) values (9, 'Angela', 8);
insert into Employees (employee_id, employee_name, manager_id) values (77, 'Robert', 1);
```

提示：

The head of the company is the employee with employee_id 1.

The employees with employee_id 2 and 77 report their work directly to the head of the company.

The employee with employee_id 4 report his work indirectly to the head of the company 4 --> 2 --> 1. 

The employee with employee_id 7 report his work indirectly to the head of the company 7 --> 4 --> 2 --> 1.

The employees with employee_id 3, 8 and 9 don't report their work to head of company directly or indirectly. 

最终SQL:

```mysql
select
      employee_id EMPLOYEE_ID
from 
      employees
where 
      manager_id=1 and 
      employee_id!=1
union
select
      a1.employee_id
from 
      employees a1,
     (select 
            employee_id
      from
            employees
      where
            manager_id=1 and
            employee_id!=1
     ) a
where
     manager_id=a.employee_id
union
select 
     a2.employee_id
from 
     employees a2,
    (select
           a1.employee_id employee_id
    from 
           employees a1,
           (select 
                  employee_id
            from
                  employees
            where
                  manager_id=1 and
                  employee_id!=1
           ) a
    where 
           manager_id=a.employee_id
    ) a3
where 
    manager_id=a3.employee_id
order by 
    employee_id;
```