package com.edison.saas.bootstrap.application.config;

import com.edison.saas.common.framework.app.ApplicationProperties;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("com.edison.saas.bootstrap.application")
@EntityScan({"com.edison.saas.bootstrap.application"})
@Import({DefaultView.class})
public class BootstrapApplicationConfig {


    @Bean
    @ConfigurationProperties(prefix = "application")
    public ApplicationProperties applicationProperties(){
        return new ApplicationProperties();
    }

}
