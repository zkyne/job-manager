package com.zkyne.jobmanager.service;

import com.zkyne.jobmanager.po.Crontab;
import com.zkyne.jobmanager.common.util.Page;

import java.util.List;

/**
 * Crontab Manager.
 */
public interface ICrontabService {

    int saveCrontab(Crontab crontab);

    int updateById(Crontab crontab);

    int deleteById(String jobId);

    Crontab findById(String jobId);

    Crontab findByJobUrl(String jobUrl);

    Page<Crontab> selectByPage(Integer pageNo, Integer pageSize, String key);

    List<Crontab> selectAllEnableCrontab();

}
