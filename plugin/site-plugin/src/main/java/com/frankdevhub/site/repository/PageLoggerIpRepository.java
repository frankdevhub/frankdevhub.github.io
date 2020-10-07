package com.frankdevhub.site.repository;

import com.frankdevhub.site.core.repository.MyBatisRepository;
import com.frankdevhub.site.core.utils.SpringUtils;
import com.frankdevhub.site.entities.PageLoggerIpRecord;
import com.frankdevhub.site.mapper.PageLoggerIpMapper;

import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

@Repository
public class PageLoggerIpRepository extends MyBatisRepository {

	private static final String IP_DATE = "date";

	private PageLoggerIpMapper getMapper() {
		return SpringUtils.getBean(PageLoggerIpMapper.class);
	}

	public Integer insertSelective(PageLoggerIpRecord record) {
		if (null == record.getId())
			record.setId(UUID.randomUUID().toString());
		record.doCreateEntity();
		return getMapper().insertSelective(record);
	}

	public List<PageLoggerIpRecord> selectByExample(Long startDateTime, Long endDateTime, Boolean asend) {
		Example example = new Example(PageLoggerIpRecord.class);
		example.createCriteria().andBetween(IP_DATE, startDateTime, endDateTime);
		if (asend)
			example.setOrderByClause(IP_DATE + "ASEC");
		else
			example.setOrderByClause(IP_DATE + "DSEC");
		return getMapper().selectByExample(example);
	}
}
