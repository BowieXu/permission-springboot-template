package com.worthytrip.template.db.config;

import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by xubin on 2018/3/2.
 */
@PropertySource(value = { "file:/data/config/jdbc.properties" })
// @PropertySource(value = { "classpath:jdbc.properties" })
@EnableWebMvc
public class DataSourceBase extends WebMvcConfigurerAdapter{
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**");
    }
}
