package com.zkyne.jobmanager.service;

import com.zkyne.jobmanager.common.util.Page;
import com.zkyne.jobmanager.po.Crontab;

import java.util.List;

/**
 * Crontab Manager.
 */
public interface ICrontabService {
    /**
     * 保存任务
     *
     * @param crontab
     * @return
     */
    int saveCrontab(Crontab crontab);

    /**
     * 更新任务
     *
     * @param crontab
     * @return
     */
    int updateById(Crontab crontab);

    /**
     * 删除任务
     *
     * @param jobId
     * @return
     */
    int deleteById(String jobId);

    /**
     * 查询单个任务
     *
     * @param jobId
     * @return
     */
    Crontab findById(String jobId);

    /**
     * 查询单个任务
     *
     * @param jobUrl
     * @return
     */
    Crontab findByJobUrl(String jobUrl);

    /**
     * 分页查询
     *
     * @param pageNo
     * @param pageSize
     * @param key
     * @return
     */
    Page<Crontab> selectByPage(Integer pageNo, Integer pageSize, String key);

    /**
     * 查询所有有效活动
     *
     * @return
     */
    List<Crontab> selectAllEnableCrontab();

}
