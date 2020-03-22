package com.frankdevhub.site.repository;

import org.springframework.stereotype.Repository;

import com.frankdevhub.site.core.repository.MyBatisRepository;
import com.frankdevhub.site.core.utils.SpringUtils;
import com.frankdevhub.site.entities.SiteMapPushRecord;
import com.frankdevhub.site.mapper.SiteMapPushRecordMapper;

@Repository
public class SiteMapPushRecordRepository extends MyBatisRepository {

	private SiteMapPushRecordMapper getMapper() {
		return SpringUtils.getBean(SiteMapPushRecordMapper.class);
	}

	public Integer insertSelective(SiteMapPushRecord entity) {
		entity.doCreateEntity();
		return getMapper().insertSelective(entity);
	}
}
