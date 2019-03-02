package com.edison.saas.bootstrap.security.app.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
//@ConditionalOnClass({ AuthorServer.class })
//@EnableConfigurationProperties(AuthorProperties.class)
@Import({WebSecurityConfig.class, TestSecurityFilter.class })
@ComponentScan("com.edison.saas.bootstrap.security")
public class LocalSecurityAutoConfiguration {

//    @Resource
//    private AuthorProperties authorProperties;

//    @Bean
//    @ConditionalOnMissingBean(AuthorServer.class)
//    @ConditionalOnProperty(name = "custom.author.enabled", matchIfMissing = true)
//    public AuthorServer authorResolver() {
//        AuthorServer authorServer = new AuthorServer();
//        authorServer.setAuthor(authorProperties.getAuthor());
//        return authorServer;
//    }
}