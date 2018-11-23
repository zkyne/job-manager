package com.zkyne.jobmanager.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: SysUser
 * @Description:
 * @Author: zkyne
 * @Date: 2018/7/18 8:51
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUser implements Serializable{

    public static final int NORMAL_STATUS = 0;
    public static final int FORBIDDEN_STATUS = 1;
    public static final int ONLINE_STATUS = 2;

    private static final long serialVersionUID = 8715041364243768711L;
    private Long userId;

    private String userName;

    private String password;

    private String loginIp;

    private String email;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}
