package com.trip.config;

import java.sql.Driver;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class MybatisConfig {

    @Value("${spring.datasource.driver-class-name}")
    private String driver;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public DataSource dataSource() throws ClassNotFoundException {
        SimpleDriverDataSource ds = new SimpleDriverDataSource();
        ds.setDriverClass((Class<Driver>) Class.forName(driver));
        ds.setUrl(url);
        ds.setUsername(userName);
        ds.setPassword(password);

        return ds;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(@Autowired DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setConfigLocation(
                new PathMatchingResourcePatternResolver().getResource("classpath:/mybatis/mybatisConfig.xml"));
        return bean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSession(@Autowired SqlSessionFactory fac) {
        return new SqlSessionTemplate(fac);
    }
}
