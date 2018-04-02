/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年10月28日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.home.ferrari.dao.tesla;

import org.apache.ibatis.annotations.Param;

import com.home.ferrari.vo.tesla.user.TeslaUser;

/** 
* @ClassName: TeslaUserDao 
* @Description: 用户信息DAO
* @author zengbin
* @date 2015年10月28日 下午3:23:23 
*/
public interface TeslaUserDao {
	TeslaUser getTeslaUserByMobileAndPassword(@Param(value = "mobile") String mobile,@Param(value = "password") String password);
    TeslaUser getTeslaUserByMobile(@Param(value = "mobile") String mobile);
    TeslaUser getTeslaUserById(@Param(value = "id") Integer id);
    TeslaUser getTeslaUserByShopId(@Param(value = "shopId") Integer shopId);
    Integer insertTeslaUser(TeslaUser teslaUser);
    Integer updateTeslaUser(TeslaUser teslaUser);
    Integer updateTeslaUserByShopId(TeslaUser teslaUser);
}
