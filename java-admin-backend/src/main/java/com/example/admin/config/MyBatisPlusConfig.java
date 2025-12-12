package com.example.admin.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * MyBatis-Plus 手动配置类
 * 用于解决 Spring Boot 4.0.0 与 MyBatis-Plus 3.5.9 的兼容性问题
 */
@Configuration
public class MyBatisPlusConfig {

    /**
     * 配置 SqlSessionFactory
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        
        // 配置 MyBatis 映射文件路径
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath*:/mapper/**/*.xml");
        sqlSessionFactoryBean.setMapperLocations(resources);
        
        // 设置类型别名包
        sqlSessionFactoryBean.setTypeAliasesPackage("com.example.admin.entity");
        
        return sqlSessionFactoryBean.getObject();
    }
    
    /**
     * 配置事务管理器
     */
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
