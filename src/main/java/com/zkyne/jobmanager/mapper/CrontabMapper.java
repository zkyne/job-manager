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

    int insertSelective(Crontab crontab);

    int updateByPrimaryKeySelective(Crontab crontab);

    int deleteByPrimaryKey(String jobId);

    Crontab selectByPrimaryKey(String jobId);

    int countByQueryVo(CrontabQueryVo crontabQueryVo);

    List<Crontab> selectByQueryVo(CrontabQueryVo crontabQueryVo);

    Crontab findByJobUrl(String jobUrl);

    List<Crontab> selectAllEnableCrontab();
}
