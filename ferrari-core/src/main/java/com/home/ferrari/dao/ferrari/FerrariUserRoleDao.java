/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年10月28日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.home.ferrari.dao.ferrari;

import org.apache.ibatis.annotations.Param;

import com.home.ferrari.vo.ferrari.user.FerrariUserRole;

/** 
* @ClassName: FerrariUserRoleDao 
* @Description: 用户角色信息DAO
* @author zengbin
* @date 2016年05月01日 下午3:23:23 
*/
public interface FerrariUserRoleDao {
    Integer insertFerrariUserRole(FerrariUserRole ferrariUserRole);
    Integer deleteFerrariUserRole(@Param(value = "userId") Integer userId);
    FerrariUserRole getFerrariUserRoleByUserAndRole(@Param(value = "userId") Integer userId, @Param(value = "roleId") Integer roleId);
    FerrariUserRole getFerrariUserRoleByUserId(@Param(value = "userId") Integer userId);
    Integer countFerrariUserByRoleId(@Param(value = "roleId") Integer roleId);
}
