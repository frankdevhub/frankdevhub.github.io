package com.frankdevhub.site.configuration;

import com.frankdevhub.site.core.utils.ViewMapper;
import com.frankdevhub.site.entities.ConfigProperties;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

@Configuration
@AutoConfigureAfter(MybatisConfig.class)
public class MyBatisMapperScannerConfig {

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.frankdevhub.site.mapper");

        ConfigProperties props = new ConfigProperties();
        props.setProperty("mappers", ViewMapper.class.getName())
                .setProperty("notEmpty", "false")
                .setProperty("IDENTITY", "MYSQL");

        mapperScannerConfigurer.setProperties(props);
        return mapperScannerConfigurer;
    }
}
