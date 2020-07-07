-- 建库
create database if not exists mydb ;
-- 使用库
use mydb ;
-- 建表
CREATE TABLE if not exists  `dept` (
 `id` INT(11) NOT NULL AUTO_INCREMENT,
 `deptName` VARCHAR(30) DEFAULT NULL,
 `address` VARCHAR(40) DEFAULT NULL,
  ceo INT NULL ,
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
-- 建表
CREATE TABLE if not exists `emp` (
 `id` INT(11) NOT NULL AUTO_INCREMENT,
 `empno` INT NOT NULL ,
 `name` VARCHAR(20) DEFAULT NULL,
 `age` INT(3) DEFAULT NULL,
 `deptId` INT(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
 #CONSTRAINT `fk_dept_id` FOREIGN KEY (`deptId`) REFERENCES `t_dept` (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- 设置信任存储函数创建者
set global log_bin_trust_function_creators=1;

-- 随机生成字符串函数
DELIMITER $$
CREATE FUNCTION rand_string(n INT) RETURNS VARCHAR(255)
BEGIN    
DECLARE chars_str VARCHAR(100) DEFAULT 'abcdefghijklmnopqrstuvwxyzABCDEFJHIJKLMNOPQRSTUVWXYZ';
 DECLARE return_str VARCHAR(255) DEFAULT '';
 DECLARE i INT DEFAULT 0;
 WHILE i < n DO  
 SET return_str =CONCAT(return_str,SUBSTRING(chars_str,FLOOR(1+RAND()*52),1));  
 SET i = i + 1;
 END WHILE;
 RETURN return_str;
END $$

-- 随机生成数字函数
DELIMITER $$
CREATE FUNCTION  rand_num (from_num INT ,to_num INT) RETURNS INT(11)
BEGIN   
 DECLARE i INT DEFAULT 0;  
 SET i = FLOOR(from_num +RAND()*(to_num -from_num+1))   ;
RETURN i;  
 END$$


-- 创建往emp表插入数据的存储过程
DELIMITER $$
CREATE PROCEDURE  insert_emp(  START INT ,  max_num INT )
BEGIN  
DECLARE i INT DEFAULT 0;   
#set autocommit =0 把autocommit设置成0  
 SET autocommit = 0;    
 REPEAT  
 SET i = i + 1;  
 INSERT INTO emp (empno, NAME ,age ,deptid ) VALUES ((START+i) ,rand_string(6)   , rand_num(30,50),rand_num(1,10000));  
 UNTIL i = max_num  
 END REPEAT;  
 COMMIT;  
 END$$


-- 创建往dept插入数据的存储过程
DELIMITER $$
CREATE PROCEDURE `insert_dept`(  max_num INT )
BEGIN  
DECLARE i INT DEFAULT 0;   
 SET autocommit = 0;    
 REPEAT  
 SET i = i + 1;  
 INSERT INTO dept ( deptname,address,ceo ) VALUES (rand_string(8),rand_string(10),rand_num(1,500000));  
 UNTIL i = max_num  
 END REPEAT;  
 COMMIT;  
 END$$

-- 调用存储过程
-- 往dept表插入1万条数据

DELIMITER ;
CALL insert_dept(10000);

-- 往emp表插入50万条数据
CALL insert_emp(100000,500000);

-- 创建批量删除某个表索引的存储过程
DELIMITER $$
CREATE  PROCEDURE `proc_drop_index`(dbname VARCHAR(200),tablename VARCHAR(200))
BEGIN
       DECLARE done INT DEFAULT 0;
       DECLARE ct INT DEFAULT 0;
       DECLARE _index VARCHAR(200) DEFAULT '';
       DECLARE _cur CURSOR FOR  SELECT   index_name   FROM information_schema.STATISTICS   WHERE table_schema=dbname AND table_name=tablename AND seq_in_index=1 AND    index_name <>'PRIMARY'  ;
       DECLARE  CONTINUE HANDLER FOR NOT FOUND set done=2 ;      
        OPEN _cur;
        FETCH   _cur INTO _index;
        WHILE  _index<>'' DO 
               SET @str = CONCAT("drop index ",_index," on ",tablename ); 
               PREPARE sql_str FROM @str ;
               EXECUTE  sql_str;
               DEALLOCATE PREPARE sql_str;
               SET _index=''; 
               FETCH   _cur INTO _index; 
        END WHILE;
   CLOSE _cur;
   END$$
DELIMITER ;