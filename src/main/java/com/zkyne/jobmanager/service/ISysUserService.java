package com.zkyne.jobmanager.service;

import com.zkyne.jobmanager.po.SysUser;

/**
 * SysUser Manager.
 */
public interface ISysUserService {
    /**
     * 查询用户
     *
     * @param userName
     * @return
     */
    SysUser findByUserName(String userName);
}
