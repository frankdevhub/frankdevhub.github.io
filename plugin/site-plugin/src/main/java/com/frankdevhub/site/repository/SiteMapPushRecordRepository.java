package com.frankdevhub.site.repository;

import com.frankdevhub.site.core.repository.MyBatisRepository;
import com.frankdevhub.site.core.utils.SpringUtils;
import com.frankdevhub.site.entities.SiteMapPushRecord;
import com.frankdevhub.site.mapper.SiteMapPushRecordMapper;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("all")
public class SiteMapPushRecordRepository extends MyBatisRepository {

	private SiteMapPushRecordMapper getMapper() {
		return SpringUtils.getBean(SiteMapPushRecordMapper.class);
	}

	public Integer insertSelective(SiteMapPushRecord entity) {
		entity.doCreateEntity();
		return getMapper().insertSelective(entity);
	}
}
