package com.frankdevhub.site;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.frankdevhub.site" })
@MapperScan(basePackages = "com.frankdevhub.site.mapper")
@EnableTransactionManagement
public class SiteApp {
	public static void main(String[] args) {
		SpringApplication.run(SiteApp.class, args);
	}
}
