# 问题 1

查询所有国家的官方语言, 没有的以 null 表示.

出现, 直接外连接和外链接后用 where 再过滤不一致的答案. 即解法一(正确):

```sql
SELECT
	co.name,
	cl.Language
FROM
	Country co
left JOIN
	CountryLanguage cl
on 
	co.code = cl.CountryCode
	AND
	cl.isOfficial = 'T';
	
输出: 287 rows in set (0.00 sec)
```

解法二(错误):

```sql
SELECT
	co.name,
	cl.Language
FROM
	Country co
left JOIN
	CountryLanguage cl
on 
	co.code = cl.CountryCode
WHERE
	cl.IsOfficial = 'T';
	
输出: 238 rows in set (0.00 sec)
```



# 解答

首先, 国家表一共 239 个国家:

```sql
SELECT COUNT(name) FROM Country;

239
```

语言表中, 国家有:

```sql
SELECT COUNT(DISTINCT CountryCode) FROM CountryLanguage;

233
```

也就是说 有 239 - 233 = 6 个国家没有语言, 或通过以下 sql 查询:

```sql
SELECT
	COUNT(co.name)
FROM
	Country co
left JOIN
	CountryLanguage cl
on 
	co.code = cl.CountryCode
WHERE
	cl.CountryCode is null;

6
```

接着, 从下面这个查询可知, 语言的 `isOfficial` 只能是 T 或 F

```sql
SELECT DISTINCT IsOfficial from CountryLanguage;

+------------+
| IsOfficial |
+------------+
| T          |
| F          |
+------------+
```

再查看语言表中语言的情况:

```sql
SELECT count(DISTINCT CountryCode, IsOfficial) from CountryLanguage;

390

SELECT count(DISTINCT CountryCode, IsOfficial) from CountryLanguage WHERE IsOfficial= 'T';

190

SELECT count(DISTINCT CountryCode, IsOfficial) from CountryLanguage WHERE IsOfficial= 'F';

200
```

那么一定有 233 - 190 = 43 个国家是**没有官方语言的**, 这 43 个在通过以下这个语句外连接时, 会产生 43 + 6 = 49 个 null 数据, 这一点可以通过以下 **正确的方法** 验证:

```sql
SELECT
	co.name,
	cl.Language
FROM
	Country co
left JOIN
	CountryLanguage cl
on 
	co.code = cl.CountryCode
	AND
	cl.isOfficial = 'T';
	
输出: 287 rows in set (0.00 sec)

SELECT
	co.name,
	cl.Language
FROM
	Country co
left JOIN
	CountryLanguage cl
on 
	co.code = cl.CountryCode
	AND
	cl.isOfficial = 'T'
WHERE
	cl.`Language` is null;
	
输出: 49 rows in set (0.00 sec)
```

以上是正确的方法.

第二种加 `WHERE` 错误的原因是, 如果外联只有 `co.code = cl.CountryCode` 这一个条件, 

先去掉 6 个没有语言的国家不说, 剩下 233 个国家的 `isOfficial` 只能是 T 或者 F, 这里出现不了 `null` !!! 通过以下语言可以验证:

```sql
SELECT
	co.name,
	cl.Language
FROM
	Country co
left JOIN
	CountryLanguage cl
on 
	co.code = cl.CountryCode
WHERE
	cl.`Language` is null;

输出: 6 rows in set (0.00 sec)
```

所以, 再用 `where cl.isOfficial = 'T'  `来筛选, 只能把里面的 F 去掉, 并不能把某些 F 变成 `null`, 也就是说, **有些国家的语言, 没有 T 只有 F 的, 在第一种方法里记录就是 null, 但在第二种方法里就只有 F, 所以应该将 F 变成 `null`,   但 `WHERE` 并不能完成这个操作.**

所以,  `where cl.isOfficia ` 之后, 会去掉若干个没有官方语言的记录,  所以, 通过 `where cl.isOfficial = 'T'` 小于 287 个.

而 `where` 下面条件越多, 数据会越少, 所以, 只要使用了 `where`, 就一定错了.

```sql
SELECT
	co.name,
	cl.Language
FROM
	Country co
left JOIN
	CountryLanguage cl
on 
	co.code = cl.CountryCode
WHERE
	cl.IsOfficial = 'T';
	
输出: 238 rows in set (0.00 sec)
```

