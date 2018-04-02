--手动导入的门店
select count(*) from t_tesla_shop tt where tt.remark='sddr';

修改表
  t_ferrari_role  
  	增加role_type
	修改user_id为唯一索引   
  t_tesla_shop_trace
  	增加role_type
  	增加is_remark
新增表
  t_ferrari_files
  t_tesla_files
刷数据
  UPDATE t_tesla_shop_trace t1
	SET t1.role_type = (
		SELECT
			t2.role_type
		FROM
			t_ferrari_role t2,
			t_ferrari_user_role t3
		WHERE
			t2.id = t3.role_id
		AND t3.user_id = t1.ferrari_user_id
	);
  UPDATE t_tesla_shop_trace t1
	SET t1.role_type = 3
	WHERE
		t1.role_type IS NULL;
  update t_tesla_shop_trace set is_remark=1 where operate_type=5;

-------------------------------bug fix----------------------------------
修改
  t_ferrari_files
  t_tesla_files
两个表全部同步下
