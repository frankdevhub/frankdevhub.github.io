package com.frankdevhub.site.repository;

import com.frankdevhub.site.core.repository.MyBatisRepository;
import com.frankdevhub.site.core.utils.SpringUtils;
import com.frankdevhub.site.entities.PageLoggerIpEntity;
import com.frankdevhub.site.mapper.PageLoggerIpMapper;

import tk.mybatis.mapper.entity.Example;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class PageLoggerIpRepository extends MyBatisRepository {

	private static final String IP_DATE = "date";
	private static final String IP_ADDRESS = "ipAddress";

	private PageLoggerIpMapper getMapper() {
		return SpringUtils.getBean(PageLoggerIpMapper.class);
	}

	public Integer insertSelective(PageLoggerIpEntity entity) {
		entity.doCreateEntity();
		return getMapper().insertSelective(entity);
	}

	public List<PageLoggerIpEntity> selectByExample(Long startDateTime, Long endDateTime, Boolean asend) {
		Example example = new Example(PageLoggerIpEntity.class);
		example.createCriteria().andBetween(IP_DATE, startDateTime, endDateTime);
		if (asend)
			example.setOrderByClause(IP_DATE + "ASEC");
		else
			example.setOrderByClause(IP_DATE + "DSEC");
		return getMapper().selectByExample(example);
	}
}
