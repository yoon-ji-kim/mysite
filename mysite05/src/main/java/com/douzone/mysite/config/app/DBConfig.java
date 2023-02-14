package com.douzone.mysite.config.app;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

//Connection Pool DataSource 대체
@Configuration
@PropertySource("classpath:com/douzone/mysite/app/jdbc.properties")
public class DBConfig {
	@Autowired
	private Environment env;
	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
		dataSource.setUrl(env.getProperty("jdbc.url"));
		dataSource.setUsername(env.getProperty("jdbc.username"));
		dataSource.setPassword(env.getProperty("jdbc.password"));
		dataSource.setInitialSize(env.getProperty("jdbc.initialSize", Integer.class));	//처음 ConnectionPool 몇 개 만들지
		dataSource.setMaxActive(env.getProperty("jdbc.maxActive", Integer.class));	//최대 개수를 유지하다 라운딩
		return dataSource;
	}
}
