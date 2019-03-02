package com.edison.saas.bootstrap.security.app.config;


import com.edison.saas.bootstrap.security.app.business.authorize.SecurityUtil;
import com.edison.saas.common.framework.app.ApplicationProperties;
import com.edison.saas.platform.services.platform.business.security.account.vo.AccountVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.edison.saas.bootstrap.security")
@EntityScan({"com.edison.saas.bootstrap.security"})
public class BootstrapLocalSecurityConfig {

    @Value("${application.code:-1}")
    private String appCode;

    @ConditionalOnMissingBean
    @Bean
    public SecurityUtil securityUtil(){
        return new SecurityUtil(){

            @Override
            public AccountVO getAccount() {

                AccountVO account = new AccountVO();
                account.setName("开发中使用的默认用户");
                account.setNickName("除了开发环境, 你绝对不应该看到我");
                account.setId(-1L);
                account.setTid(-1L);

                return null;
            }

            @Override
            public Long getTenantId() {
                return -1L;
            }

            @Override
            public String getAppCode() {
                return appCode;
            }

            @Override
            public Long getAccountId() {
                return -1L;
            }
        };
    }


    @Bean
    @ConfigurationProperties(prefix = "application")
    public ApplicationProperties applicationProperties(){
        return new ApplicationProperties();
    }

}
