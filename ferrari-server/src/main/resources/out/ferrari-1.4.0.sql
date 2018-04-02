----------------1.4.1 已上线-----------------------
1.b2b 
  t_b2b_brand
  t_b2b_category
  t_b2b_product
  t_b2b_product_text
  t_b2b_report
2.临时需求
  t_tesla_shop  
  		company_name	varchar(64)
  t_ferrari_user
  		p_password		varchar(64)
  		
----------------1.4.2 已上线-----------------------
1.故障
  t_break_document
  t_break_trace
  t_break_type
  t_break_typelist
2.消息
  t_quest_template 
        temp_type     	int  消息模板  1问卷调研 2合同模板 3培训模板
        update t_quest_template set temp_type=1;
  t_quest_investigate
  		msg_type        int  消息类型  1调研消息 2普通(平台)消息 3合同消息 4培训消息
		title	 varchar(128)  消息标题
		text     mediumtext	   消息内容
		update t_quest_investigate set msg_type=1;
  t_shop_investigate   
        msg_type        int  消息类型  1调研消息 2普通(平台)消息 3合同消息 4培训消息
        sub_msg_type    int  消息发送范围
        is_read			int  是否阅读  0未阅读  1已阅读
		is_read  DEFAULT '0' COMMENT '是否阅读  0未阅读    1已阅读'
		UNIQUE KEY `s_inv_inx` (`shop_id`,`investigate_id`,`sub_msg_type`) USING BTREE
		update t_shop_investigate set msg_type=1,sub_msg_type=1,is_read=1;
  新增表  t_quest_role_type
        t_company_message
  update t_quest_template set temp_type=1;      
  update t_shop_investigate set msg_type=1,sub_msg_type=4,is_read=1;
3.权限重构（强制用户登录，刷新redis）
  修改t_ferrari_menu表（备份t_ferrari_menu，将测试库的表全表迁移过去）
    增加parent_id 父级菜单id   int
    增加is_menu 是否菜单  1是  0否   int   DEFAULT '1'
      创建存过
				# 菜单过程
				DROP PROCEDURE IF EXISTS getMenuChildLst;
				CREATE PROCEDURE getMenuChildLst (IN rootId INT)
				 BEGIN
					  CREATE TEMPORARY TABLE IF NOT EXISTS tmpLst (sno int primary key auto_increment,id int,depth int);
					  DELETE FROM tmpLst;
					  CALL createMenuChildLst(rootId,0);
						SELECT
							t2.id menuId,
						    t2.parent_id parentMenuId,
						    t2.is_menu isMenu,
					      	t2.menu_name menuName,
						    t2.menu_en_name menuEnName,
						    t2.menu_url menuUrl
						FROM
							tmpLst t1,
							t_ferrari_menu t2
						WHERE
							t1.id = t2.id
						ORDER BY
							t1.id;
				 END;
				
				# 递归过程
				DROP PROCEDURE IF EXISTS createMenuChildLst;
				CREATE PROCEDURE createMenuChildLst (IN rootId INT,IN nDepth INT)
				 BEGIN
					 DECLARE done INT DEFAULT 0;
					 DECLARE b INT;
					 DECLARE cur1 CURSOR FOR SELECT id FROM t_ferrari_menu where parent_id=rootId;
					 DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
					
					 insert into tmpLst values (null,rootId,nDepth);
					
					 SET @@max_sp_recursion_depth = 25; 
					
					 OPEN cur1;
						FETCH cur1 INTO b;
						WHILE done=0 DO
					     CALL createMenuChildLst(b,nDepth+1);
					     FETCH cur1 INTO b;
						END WHILE;
					 CLOSE cur1;
				 END;  

4.资料中心
  t_ferrari_files
				 
----------------1.4.3 未上线-----------------------
1.联合对账
  t_account_header
  t_account_list
  t_account_shop
  t_account_invoice
  t_account_upload
2.投诉处理
  t_complain
  t_complain_shop
  t_complain_shop_remaind
  t_complain_trace				 
  t_resource				 
				 
				 
				 
				 
				 
		 
				 


				 
				 
				 