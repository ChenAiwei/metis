package com.boot.metis.server.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.boot.metis.common.util.DESCryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * DruidConfig
 *
 * @author Administrator
 * @Date 2020年1月9日19:06:23
 */
@Slf4j
@Configuration
public class DruidConfig {
	@Value("${spring.datasource.druid.mysql.url}")
	private String dbUrl;

	@Value("${spring.datasource.druid.mysql.username}")
	private String username;

	@Value("${spring.datasource.druid.mysql.password}")
	private String password;

	@Value("${spring.datasource.druid.mysql.driver-class-name}")
	private String driverClassName;

	@Value("${spring.datasource.druid.mysql.initialSize}")
	private int initialSize;

	@Value("${spring.datasource.druid.mysql.minIdle}")
	private int minIdle;

	@Value("${spring.datasource.druid.mysql.maxActive}")
	private int maxActive;

	@Value("${spring.datasource.druid.mysql.maxWait}")
	private int maxWait;

	@Value("${spring.datasource.druid.mysql.timeBetweenEvictionRunsMillis}")
	private int timeBetweenEvictionRunsMillis;

	@Value("${spring.datasource.druid.mysql.minEvictableIdleTimeMillis}")
	private int minEvictableIdleTimeMillis;

	@Value("${spring.datasource.druid.mysql.validationQuery}")
	private String validationQuery;

	@Value("${spring.datasource.druid.mysql.testWhileIdle}")
	private boolean testWhileIdle;

	@Value("${spring.datasource.druid.mysql.testOnBorrow}")
	private boolean testOnBorrow;

	@Value("${spring.datasource.druid.mysql.testOnReturn}")
	private boolean testOnReturn;

	@Value("${spring.datasource.druid.mysql.poolPreparedStatements}")
	private boolean poolPreparedStatements;

	@Value("${spring.datasource.druid.mysql.maxPoolPreparedStatementPerConnectionSize}")
	private int maxPoolPreparedStatementPerConnectionSize;

	@Value("${spring.datasource.druid.mysql.filters}")
	private String filters;

	@Value("${spring.datasource.druid.mysql.connectionProperties}")
	private String connectionProperties;

	@Value("${spring.datasource.druid.mysql.publicKey}")
	private String publicKey;


	@Bean // 声明其为Bean实例
	@Primary // 在同样的DataSource中，首先使用被标注的DataSource
	public DataSource dataSource() {
		DruidDataSource datasource = new DruidDataSource();

		datasource.setUrl(DESCryptUtil.decrypt(this.dbUrl, this.publicKey));
		datasource.setUsername(DESCryptUtil.decrypt(this.username, this.publicKey));
		datasource.setPassword(DESCryptUtil.decrypt(this.password, this.publicKey));
		datasource.setDriverClassName(driverClassName);

		// configuration
		datasource.setInitialSize(initialSize);
		datasource.setMinIdle(minIdle);
		datasource.setMaxActive(maxActive);
		datasource.setMaxWait(maxWait);
		datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		datasource.setValidationQuery(validationQuery);
		datasource.setTestWhileIdle(testWhileIdle);
		datasource.setTestOnBorrow(testOnBorrow);
		datasource.setTestOnReturn(testOnReturn);
		datasource.setPoolPreparedStatements(poolPreparedStatements);
		datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
		try {
			datasource.setFilters(filters);
		} catch (SQLException e) {
			log.error("druid configuration initialization filter");
		}
		datasource.setConnectionProperties(connectionProperties);
		return datasource;
	}

	/**
	 * 配置一个管理后台的Servlet
	 */
	@Bean
	public ServletRegistrationBean statViewServlet() {
		ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
		Map<String, String> initParams = new HashMap<>();

		initParams.put("loginUsername", "admin");
		initParams.put("loginPassword", "123456");
		//默认就是允许所有访问
		initParams.put("allow", "");

		bean.setInitParameters(initParams);
		return bean;
	}


	//2、配置一个web监控的filter
	@Bean
	public FilterRegistrationBean webStatFilter() {
		FilterRegistrationBean bean = new FilterRegistrationBean();
		bean.setFilter(new WebStatFilter());

		Map<String, String> initParams = new HashMap<>();
		initParams.put("exclusions", "*.vue,*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");

		bean.setInitParameters(initParams);

		bean.setUrlPatterns(Arrays.asList("/*"));

		return bean;
	}
}
