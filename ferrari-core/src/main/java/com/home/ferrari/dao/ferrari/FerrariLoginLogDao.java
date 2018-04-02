/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年10月28日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.home.ferrari.dao.ferrari;

import com.home.ferrari.vo.ferrari.user.FerrariLoginLog;

/** 
* @ClassName: FerrariLoginLogDao 
* @Description: 用户登录日志DAO
* @author zengbin
* @date 2015年10月28日 下午3:23:23 
*/
public interface FerrariLoginLogDao {
    Integer insertFerrariLoginLog(FerrariLoginLog ferrariLoginLog);
}
