package com.worthytrip.template.db.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * Created by xubin on 2018/3/2.
 */
@Configuration
@ConfigurationProperties(prefix = DataSourceAirfront.DB_PREFIX)
@MapperScan(basePackages = "com.worthytrip.template.mapper.airfront", sqlSessionFactoryRef = DataSourceAirfront.DB_FACTORY)
public class DataSourceAirfront extends DataSourceBase {
    public static final String DB_ALIAS = "airfrontDataSource";
    public static final String DB_PREFIX = "jdbc.airfront";
    public static final String DB_FACTORY = "airfrontSqlSessionFactory";
    public static final String DB_TRANS = "airfrontTransaction";

    private Properties properties = new Properties();

    @Bean(name = DB_ALIAS)
    @Primary
    public DataSource airfrontDataSource() throws Exception {
        setdbserviceWhileIdle(true);
        setValidationQuery("SELECT 1");
        return BasicDataSourceFactory.createDataSource(properties);
    }

    @Bean(name = DB_FACTORY)
    @Primary
    public SqlSessionFactory airfrontSqlSessionFactory(@Qualifier(DB_ALIAS) DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:mapper/airfront/*.xml"));
        return bean.getObject();
    }

    @Bean(name = DB_TRANS)
    @Primary
    public DataSourceTransactionManager airfrontTransactionManager(@Qualifier(DB_ALIAS) DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Value("${jdbc.driverClassName}")
    private String driverClassName;
    private String url;
    private String username;
    private String password;
    private boolean dbserviceWhileIdle;
    private String validationQuery;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        this.properties.put("url", this.url);
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
        this.properties.put("driverClassName", this.driverClassName);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        this.properties.put("username", this.username);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        this.properties.put("password", this.password);
    }

    public boolean isdbserviceWhileIdle() {
        return dbserviceWhileIdle;
    }

    public void setdbserviceWhileIdle(boolean dbserviceWhileIdle) {
        this.dbserviceWhileIdle = dbserviceWhileIdle;
        this.properties.put("dbserviceWhileIdle", String.valueOf(this.dbserviceWhileIdle));
    }

    public String getValidationQuery() {
        return validationQuery;
    }

    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
        this.properties.put("validationQuery", this.validationQuery);
    }
}
