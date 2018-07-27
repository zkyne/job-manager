package com.zkyne.jobmanager.service.impl;

import com.zkyne.jobmanager.mapper.SysUserMapper;
import com.zkyne.jobmanager.po.SysUser;
import com.zkyne.jobmanager.service.ISysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * SysUser Manager.
 */
@Service
public class SysUserServiceImpl implements ISysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser findByUserName(String userName) {
        return sysUserMapper.findByUserName(userName);
    }
}
