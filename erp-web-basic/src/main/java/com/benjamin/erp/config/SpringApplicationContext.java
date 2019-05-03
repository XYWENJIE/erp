package com.benjamin.erp.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import com.benjamin.erp.Realm;
import com.benjamin.erp.application.ErpWebApplication;

import org.activiti.engine.FormService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringExpressionManager;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@ComponentScan(basePackages = {"com.benjamin.erp.activiti","com.benjamin.erp.service"})
@EnableJpaRepositories(basePackages = "com.benjamin.erp.repository")
public class SpringApplicationContext {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/erp");
		dataSource.setUsername("root");
		dataSource.setPassword("huang1100");
		Properties properties = new Properties();
		properties.setProperty("useUnicode", "true");
		properties.setProperty("characterEncoding", "UTF-8");
		dataSource.setConnectionProperties(properties);
		return dataSource;
	}
	
	@Bean
	public ShiroFilterFactoryBean shiroFilter(DefaultSecurityManager securityManager) {
		logger.info("加载ShiroFilter拦截器功能");
		ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
		filterFactoryBean.setSecurityManager(securityManager);
		return filterFactoryBean;
	}
	
	@Profile("default")
	@Bean("securityManager")
	public DefaultWebSecurityManager defaultSecurityManager(Realm simpleRealm) {
		DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
		manager.setRealm(simpleRealm);
		return manager;
	}
	
	@Profile("test")
	@Bean("securityManager")
	public DefaultSecurityManager testSecurityManager(Realm simpleRealm) {
		DefaultSecurityManager manager = new DefaultSecurityManager();
		manager.setRealm(simpleRealm);
		SecurityUtils.setSecurityManager(manager);
		return manager;
	}
	
	@Bean("simpleRealm")
	public Realm simpleRealm(HashedCredentialsMatcher credentialsMatcher) {
		Realm realm = new Realm();
		realm.setCredentialsMatcher(credentialsMatcher);
		return realm;
	}

	@Bean
	public HashedCredentialsMatcher credentialsMatcher(){
		HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
		credentialsMatcher.setHashAlgorithmName("MD5");
		return credentialsMatcher;
	}

	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean managerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		managerFactoryBean.setDataSource(this.dataSource());
		managerFactoryBean.setJpaVendorAdapter(this.jpaVendorAdapter());
		managerFactoryBean.setPackagesToScan("com.benjamin.erp.domain");
		Map<String,Object> jpaProperties = new HashMap<>();
		jpaProperties.put("hibernate.format_sql", true);
		managerFactoryBean.setJpaPropertyMap(jpaProperties);
		return managerFactoryBean;
	}
	
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setDatabase(Database.MYSQL);
		jpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5InnoDBDialect");
		jpaVendorAdapter.setGenerateDdl(true);
		return jpaVendorAdapter;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager manager = new JpaTransactionManager();
		manager.setDataSource(this.dataSource());
		return manager;
	}
	
	@Bean
	public ErpWebApplication erpWebApplication(){
		return new ErpWebApplication();
	}
	
	// Activiti流程功能
	@Bean
	public SpringProcessEngineConfiguration processEngineConfiguration() {
		SpringProcessEngineConfiguration configuration = new SpringProcessEngineConfiguration();
		configuration.setDataSource(this.dataSource());
		configuration.setTransactionManager(this.transactionManager());
		configuration.setJobExecutorActivate(false);
		//configuration.setAsyncExecutorActivate(true);
		configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_CREATE_DROP);
		//configuration.setExpressionManager(new SpringExpressionManager());
		
		return configuration;
	}
	
	@Bean
	public ProcessEngineFactoryBean processEngineFactory(SpringProcessEngineConfiguration processEngineConfiguration) {
		ProcessEngineFactoryBean engineFactoryBean = new ProcessEngineFactoryBean();
		engineFactoryBean.setProcessEngineConfiguration(processEngineConfiguration);
		return engineFactoryBean;
	}
	
	@Bean
	public RuntimeService runtimeService(ProcessEngine processEngineFactory){
		return processEngineFactory.getRuntimeService();
	}
	
	@Bean
	public TaskService taskService(ProcessEngine processEngineFactory) {
		return processEngineFactory.getTaskService();
	}
	
	@Bean
	public RepositoryService repositoryService(ProcessEngine processEngineFactory) {
		return processEngineFactory.getRepositoryService();
	}
	
	@Bean
	public IdentityService identityService(ProcessEngine processEngineFactory) {
		return processEngineFactory.getIdentityService();
	}
	
	@Bean
	public FormService formService(ProcessEngine processEngineFactory) {
		return processEngineFactory.getFormService();
	}
}
