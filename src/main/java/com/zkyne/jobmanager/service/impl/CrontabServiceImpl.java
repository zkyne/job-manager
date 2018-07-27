package com.zkyne.jobmanager.service.impl;

import com.zkyne.jobmanager.mapper.CrontabMapper;
import com.zkyne.jobmanager.po.Crontab;
import com.zkyne.jobmanager.service.ICrontabService;
import com.zkyne.jobmanager.common.util.Page;
import com.zkyne.jobmanager.vo.CrontabQueryVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Crontab Manager.
 */
@Service
public class CrontabServiceImpl implements ICrontabService {

	@Resource
	private CrontabMapper crontabMapper;

	@Override
	public int saveCrontab(Crontab crontab) {
		return crontabMapper.insertSelective(crontab);
	}

	@Override
	public int updateById(Crontab crontab) {
		return crontabMapper.updateByPrimaryKeySelective(crontab);
	}

	@Override
	public int deleteById(String jobId) {
		return crontabMapper.deleteByPrimaryKey(jobId);
	}

	@Override
	public Crontab findById(String jobId) {
		return crontabMapper.selectByPrimaryKey(jobId);
	}

	@Override
	public Crontab findByJobUrl(String jobUrl) {
		return crontabMapper.findByJobUrl(jobUrl);
	}

	@Override
	public Page<Crontab> selectByPage(Integer pageNo, Integer pageSize, String key) {
		CrontabQueryVo crontabQueryVo = new CrontabQueryVo(pageNo,pageSize);
		crontabQueryVo.setKeyLike(key);
		Page<Crontab> page = new Page<>();
		page.setCurpage(crontabQueryVo.getPageNo());
		page.setPagesize(crontabQueryVo.getPageSize());
		page.setCount(crontabMapper.countByQueryVo(crontabQueryVo));
		page.setResult(crontabMapper.selectByQueryVo(crontabQueryVo));
		return page;
	}

	@Override
	public List<Crontab> selectAllEnableCrontab() {
		return crontabMapper.selectAllEnableCrontab();
	}

}
