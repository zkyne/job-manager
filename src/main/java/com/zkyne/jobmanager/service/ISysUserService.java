package com.zkyne.jobmanager.service;

import com.zkyne.jobmanager.po.SysUser;

/**
 * SysUser Manager.
 */
public interface ISysUserService {

    SysUser findByUserName(String userName);
}
