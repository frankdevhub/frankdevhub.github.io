package com.frankdevhub.seo.data.json;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * <p>Title:EFMappingJackson2HttpMessageConverter.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: www.frankdevhub.site</p>
 * <p>github: https://github.com/frankdevhub</p>
 *
 * @author frankdevhub
 * @date:2019-04-20 22:42
 */

public class EFMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
    public EFMappingJackson2HttpMessageConverter() {
        super(new EFObjectMapper());
    }
}
