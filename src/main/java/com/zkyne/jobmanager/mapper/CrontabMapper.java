package com.zkyne.jobmanager.mapper;

import com.zkyne.jobmanager.po.Crontab;
import com.zkyne.jobmanager.vo.CrontabQueryVo;

import java.util.List;

/**
 * @ClassName: CrontabMapper
 * @Description:
 * @Author: zkyne
 * @Date: 2018/7/26 17:39
 */
public interface CrontabMapper {
    /**
     * 插入
     * @param crontab
     * @return
     */
    int insertSelective(Crontab crontab);

    /**
     * 更新
     * @param crontab
     * @return
     */
    int updateByPrimaryKeySelective(Crontab crontab);

    /**
     * 通过主键删除
     * @param jobId
     * @return
     */
    int deleteByPrimaryKey(String jobId);

    /**
     * 通过逐渐查询
     * @param jobId
     * @return
     */
    Crontab selectByPrimaryKey(String jobId);

    /**
     * 分页查询count
     * @param crontabQueryVo
     * @return
     */
    int countByQueryVo(CrontabQueryVo crontabQueryVo);

    /**
     * 分页查询select
     * @param crontabQueryVo
     * @return
     */
    List<Crontab> selectByQueryVo(CrontabQueryVo crontabQueryVo);

    /**
     * 查询
     * @param jobUrl
     * @return
     */
    Crontab findByJobUrl(String jobUrl);

    /**
     * 查询所有有效任务
     * @return
     */
    List<Crontab> selectAllEnableCrontab();
}
