package com.zkyne.jobmanager.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: Crontab
 * @Description:
 * @Author: zkyne
 * @Date: 2018/7/18 9:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Crontab implements Serializable {

    public static final int USABLE = 0;
    public static final int DISABLED = 1;

    private static final long serialVersionUID = 8878517545785711887L;

    private String jobId;

    private String descript;

    private Date performdate;

    private String cronExp;

    private String url;

    private Integer status;

}
