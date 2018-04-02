1. 创建表
	t_capacity  t_capacity_header  t_shop_capacity   t_shop_capacity_header   t_shop_capacity_inputval    t_capacity_id
2. 数据订正
   UPDATE t_tesla_shop SET city = REPLACE ( city, '市', '' );
   UPDATE t_tesla_shop SET province = REPLACE ( province, '省', '' );
   insert into t_capacity(id,parent_id,name,deep,node_count,group_id,is_leaf,create_time,modify_time) values(1,0,'整车销售信息',0,0,'',0,now(),now());
   insert into t_capacity(id,parent_id,name,deep,node_count,group_id,is_leaf,create_time,modify_time) values(2,0,'机修钣喷售后信息',0,0,'',0,now(),now());
   insert into t_capacity(id,parent_id,name,deep,node_count,group_id,is_leaf,create_time,modify_time) values(3,0,'美容安装售后信息',0,0,'',0,now(),now());
3. 创建能力模型存过
# 入口过程
DROP PROCEDURE IF EXISTS getChildLst;
CREATE PROCEDURE getChildLst (IN rootId INT)
 BEGIN
	  CREATE TEMPORARY TABLE IF NOT EXISTS tmpLst (sno int primary key auto_increment,id int,depth int);
	  DELETE FROM tmpLst;
	  CALL createChildLst(rootId,0);
		SELECT
			t2.id id,
		    t2.parent_id parentId,
	      	t2.name name,
		    t2.deep deep,
		    t2.node_count nodeCount,
		    t2.group_id groupId,
		    t2.is_leaf isLeaf
		FROM
			tmpLst t1,
			t_capacity t2
		WHERE
			t1.id = t2.id
		ORDER BY
			t1.id;
 END;

# 递归过程
DROP PROCEDURE IF EXISTS createChildLst;
CREATE PROCEDURE createChildLst (IN rootId INT,IN nDepth INT)
 BEGIN
	 DECLARE done INT DEFAULT 0;
	 DECLARE b INT;
	 DECLARE cur1 CURSOR FOR SELECT id FROM t_capacity where parent_id=rootId;
	 DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
	
	 insert into tmpLst values (null,rootId,nDepth);
	
	 SET @@max_sp_recursion_depth = 25; 
	
	 OPEN cur1;
		FETCH cur1 INTO b;
		WHILE done=0 DO
	     CALL createChildLst(b,nDepth+1);
	     FETCH cur1 INTO b;
		END WHILE;
	 CLOSE cur1;
 END;
 3. 创建门店能力模型存过
 # 入口过程
DROP PROCEDURE IF EXISTS getShopChildLst;
CREATE PROCEDURE getShopChildLst (IN rootId INT,IN shopId INT)
 BEGIN
	  CREATE TEMPORARY TABLE IF NOT EXISTS tmpLst2 (sno int primary key auto_increment,id int,depth int,shop_id INT);
	  DELETE FROM tmpLst2;
	  CALL createShopChildLst(rootId,0,shopId);
		SELECT
			t2.id id,
			t2.shop_id shopId,
			t2.capacity_id capacityId,
		    t2.parent_capacity_id parentCapacityId,
	      	t2.name name,
		    t2.deep deep,
		    t2.node_count nodeCount,
		    t2.group_id groupId,
		    t2.is_leaf isLeaf
		FROM
			tmpLst2 t1,
			t_shop_capacity t2
		WHERE
			t1.id = t2.capacity_id and t2.shop_id=shopId
		ORDER BY
			t1.id;
 END;

# 递归过程
DROP PROCEDURE IF EXISTS createShopChildLst;
CREATE PROCEDURE createShopChildLst (IN rootId INT,IN nDepth INT,IN shopId INT)
 BEGIN
	 DECLARE done INT DEFAULT 0;
	 DECLARE b INT;
	 DECLARE cur1 CURSOR FOR SELECT capacity_id FROM t_shop_capacity where parent_capacity_id=rootId and shop_id=shopId;
	 DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
	
	 insert into tmpLst2 values (null,rootId,nDepth,shopId);
	
	 SET @@max_sp_recursion_depth = 25; 
	
	 OPEN cur1;
		FETCH cur1 INTO b;
		WHILE done=0 DO
	     CALL createShopChildLst(b,nDepth+1,shopId);
	     FETCH cur1 INTO b;
		END WHILE;
	 CLOSE cur1;
 END;