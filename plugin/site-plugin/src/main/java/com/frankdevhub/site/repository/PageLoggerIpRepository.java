package com.frankdevhub.site.repository;


import com.frankdevhub.site.core.repository.MyBatisRepository;
import com.frankdevhub.site.core.utils.SpringUtils;
import com.frankdevhub.site.entities.PageLoggerIpEntity;
import com.frankdevhub.site.mapper.PageLoggerIpMapper;
import org.springframework.stereotype.Repository;


@Repository
public class PageLoggerIpRepository extends MyBatisRepository {

    private PageLoggerIpMapper getMapper() {
        return SpringUtils.getBean(PageLoggerIpMapper.class);
    }

    public Integer insertSelective(PageLoggerIpEntity entity) {
        entity.doCreateEntity();
        return getMapper().insertSelective(entity);
    }
}
