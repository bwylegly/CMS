package com.wylegly.clinic.config;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

// Config class for integration of Spring and Hibernate
@Configuration
@PropertySource("classpath:database.properties")
@EnableTransactionManagement
@ComponentScan(basePackages = "com.wylegly.clinic")
public class AppContext {

	// Variable that hold all resources/properties 
	// etc provided under src/main/resources
	@Autowired
	private Environment environment;
	
	// Define shared Local Session Factory in Spring application contexts
	@Bean
	public LocalSessionFactoryBean sessionFactory() {
			LocalSessionFactoryBean sessionFactory = 
					new LocalSessionFactoryBean();
			
			sessionFactory.setDataSource(dataSource());
			
			sessionFactory.setPackagesToScan(
					new String[] {
							"com.wylegly.clinic.domain",
							"com.wylegly.clinic.logging"
						}
					);
			
			sessionFactory.setHibernateProperties(hibernateProperties());
			
			return sessionFactory;
	}
	
	// Define Data source
	@Bean
	public DataSource dataSource() {
		// Create connection pool
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		
		// Configure data source
		try {
			dataSource.setDriverClass(
					environment.getRequiredProperty("jdbc.driverClassName")
					);
		} catch (IllegalStateException | PropertyVetoException e) {
			e.printStackTrace();
		}
		// Configure jdbc properties
		
		dataSource.setJdbcUrl(
				environment.getRequiredProperty("jdbc.url")
				);
		dataSource.setUser(
				environment.getRequiredProperty("jdbc.username")
				);
		dataSource.setPassword(
				environment.getRequiredProperty("jdbc.password")
				);
		
		// Configure connection pool properties
		
		dataSource.setMinPoolSize(
				Integer.parseInt(environment.getRequiredProperty("connection.pool.minPoolSize"))
				);
		dataSource.setMaxPoolSize(
				Integer.parseInt(environment.getRequiredProperty("connection.pool.maxPoolSize"))
				);
		dataSource.setInitialPoolSize(
				Integer.parseInt(environment.getRequiredProperty("connection.pool.initialPoolSize"))
				);
		dataSource.setMaxIdleTime(
				Integer.parseInt(environment.getRequiredProperty("connection.pool.maxIdleTime"))
				);
		
		return dataSource;
	}

	public Properties hibernateProperties() {
		Properties properties = new Properties();
		
		properties.put("hibernate.dialect",
				environment.getRequiredProperty("hibernate.dialect"));
		properties.put("hibernate.show_sql",
				environment.getRequiredProperty("hibernate.show_sql"));
		properties.put("hibernate.format_sql",
				environment.getRequiredProperty("hibernate.format_sql"));
		
		return properties;
	}
	
	@Bean
	public HibernateTransactionManager transactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		
		transactionManager.setSessionFactory(sessionFactory().getObject());
		return transactionManager;
	}
}
