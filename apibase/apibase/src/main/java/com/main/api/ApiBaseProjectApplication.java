package com.main.api;

import com.main.api.config.AppProperties;
import com.main.api.config.BaseProjectSwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@ServletComponentScan
@EnableConfigurationProperties(AppProperties.class)
@EntityScan(basePackages = {"com.base.common.model"})
@ComponentScan(basePackages = {"com.base.common", "com.main.api"})
@Import({BaseProjectSwaggerConfiguration.class})
public class ApiBaseProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiBaseProjectApplication.class, args);
    }

    @PostConstruct
    public void init(){
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

}
