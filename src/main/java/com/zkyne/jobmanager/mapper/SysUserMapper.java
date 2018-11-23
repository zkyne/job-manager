package com.zkyne.jobmanager.mapper;

import com.zkyne.jobmanager.po.SysUser;

/**
 * @ClassName: SysUserMapper
 * @Description:
 * @Author: zkyne
 * @Date: 2018/7/26 17:41
 */
public interface SysUserMapper {
    /**
     * 查询用户
     * @param userName
     * @return
     */
    SysUser findByUserName(String userName);

}
