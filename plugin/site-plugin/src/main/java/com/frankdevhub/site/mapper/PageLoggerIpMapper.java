package com.frankdevhub.site.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.frankdevhub.site.core.utils.ViewMapper;
import com.frankdevhub.site.entities.PageLoggerIpRecord;

@Mapper
public interface PageLoggerIpMapper extends ViewMapper<PageLoggerIpRecord> {
}
