package com.edison.saas.bootstrap.security.app.config;


import com.edison.saas.bootstrap.security.app.business.authorize.filter.JumpAuthenticationProvider;
import com.edison.saas.bootstrap.security.app.business.authorize.filter.PlatformJumpAuthenticationFilter;
import com.edison.saas.bootstrap.security.app.business.authorize.spring.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Created by gonghongrui on 2017/1/10.
 */
//@Configuration
@EnableWebSecurity
//用于@PreAuthorize的生效,基于方法的权限控制
@EnableGlobalMethodSecurity(prePostEnabled = true)
//覆盖默认的spring security提供的配置
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EntityScan({"com.edison.saas.bootstrap.security"})
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//	@Autowired
//	private SecurityUserService securityUserService;


//    @Autowired
//    private PlatformJumpAuthenticationFilter platformJumpAuthenticationFilter;

    @Value("${management.security.enabled:true}")
    private boolean securityEnable;

    @Autowired
    private RestAuthenticationSuccessHandler restAuthenticationSuccessHandler;

    @Autowired
    private RestAuthenticationFailureHandler restAuthenticationFailureHandler;

    @Autowired
    private RestLogoutSuccessHandler restLogoutSuccessHandler;

    @Autowired
    private RestAccessDeniedHandler restAccessDeniedHandler;

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {


//		http.formLogin().loginPage("/login").permitAll()
//				.and().authorizeRequests().antMatchers("/health", "/css/**").anonymous()
//				.and().authorizeRequests().anyRequest().authenticated();
        http.authorizeRequests().antMatchers("/security/login/jump").permitAll();
        http.formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/security/login/authenticate").permitAll()//配置的这个URL会被拦截, 并进入认证过滤器
                //禁用CSRF保护
                .and().csrf().disable()
                .addFilterBefore(jumpAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)//在用户名密码验证前处理jump_token验证
                .addFilterBefore(testSecurityFilter(), PlatformJumpAuthenticationFilter.class)
                .authorizeRequests()

                //配置那些路径可以不用权限访问
                .antMatchers(HttpMethod.OPTIONS, "/**/*").permitAll()
                //任何访问都必须授权
                .anyRequest().fullyAuthenticated()
                .and()
                .formLogin()
                //登陆成功后的处理，因为是API的形式所以不用跳转页面
                .successHandler(restAuthenticationSuccessHandler)
                //登陆失败后的处理
                .failureHandler(restAuthenticationFailureHandler)
                .and()
                //登出后的处理
                .logout().logoutSuccessHandler(restLogoutSuccessHandler)

                .invalidateHttpSession(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .deleteCookies("JSESSIONID").invalidateHttpSession(true)
                .permitAll()
                .and()
                //访问资源不通过后的处理
                .exceptionHandling().accessDeniedHandler(restAccessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and().headers().frameOptions().disable()
        ;


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//                auth.userDetailsService(securityUserService())
//                .passwordEncoder(passwordEncoder());
        auth.authenticationProvider(remoteAuthenticationProvider())
                .authenticationProvider(jumpAuthenticationProvider());

    }



    @Override
    public void configure(WebSecurity web) throws Exception {

        String[] antMatchers = {"/login.html", "/ext_/**"
                , "/**/*.html", "/**.html"
                , "/assets/**", "/fonts/**", "/maps/**", "/scripts/**", "/styles/**", "/**/*.js"
                , "/favicon.ico", "/**/*.jpg", "/**/*.png", "/**/*.gif"
                , "/**/*.css"
                , "/ext_*/**/*", "/current/app", "/swagger-resources"//, "/actuator"
        };

        if (!securityEnable) {
            String[] testMatchers = {"/security/login/getResource", "/security/login", "/login.html", "/ext_/**"
                    , "/**.html", "/assets/**", "/fonts/**", "/maps/**", "/scripts/**", "/styles/**", "/**/*.js", "/**/*.html"
                    , "/favicon.ico", "/**/*.jpg", "/**/*.png", "/**/*.gif"//, "/actuator"
                    , "/**/*.css"
                    , "/ext_*/**/*"
                    , "/**/*"
            };
            antMatchers = testMatchers;
        }

        //解决静态资源被拦截的问题
        web.ignoring().antMatchers(antMatchers).antMatchers("/current/app");
        //忽略swagger相关的接口
        web.ignoring().antMatchers("/v2/api-docs", "/swagger*/**");

    }


    @Autowired
    private AuthenticationManager authenticationManager;

    @Bean
    public PlatformJumpAuthenticationFilter jumpAuthenticationFilter() throws Exception {
        PlatformJumpAuthenticationFilter platformJumpAuthenticationFilter = new PlatformJumpAuthenticationFilter();
//        platformJumpAuthenticationFilter.setAuthenticationManager(authenticationManager());
        platformJumpAuthenticationFilter.setAuthenticationManager(authenticationManager);
        platformJumpAuthenticationFilter.setAuthenticationSuccessHandler(restAuthenticationSuccessHandler);
        platformJumpAuthenticationFilter.setAuthenticationFailureHandler(restAuthenticationFailureHandler);
        return platformJumpAuthenticationFilter;
    }

    @Bean
//    public FilterRegistrationBean corsFilterBean() {
    public FilterRegistrationBean testFilter() {

        FilterRegistrationBean bean = new FilterRegistrationBean(testSecurityFilter());
        bean.setOrder(0);
        return bean;
    }

    @Bean
    public TestSecurityFilter testSecurityFilter(){
        return new TestSecurityFilter();
    }

    @Bean
    public CorsFilter corsFilter() {

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*"); // 1允许任何域名使用
        corsConfiguration.addAllowedHeader("*"); // 2允许任何头
        corsConfiguration.addAllowedMethod("*"); // 3允许任何方法（post、get等）

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration); // 4
        return new CorsFilter(source);
    }

    @Bean
    public JumpAuthenticationProvider jumpAuthenticationProvider(){
        return new JumpAuthenticationProvider();
    }

    @Bean
    public RemoteAuthenticationProvider remoteAuthenticationProvider(){
        return new RemoteAuthenticationProvider();
    }

    @Bean
    @ConditionalOnMissingBean(org.springframework.security.crypto.password.PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        //密码加密
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();

    }

    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    SecurityUserService securityUserService() {
        return new SecurityUserService();
    }

    /**登录成功后的处理*/
    @Bean
    @ConditionalOnMissingBean(RestAuthenticationSuccessHandler.class)
    RestAuthenticationSuccessHandler restAuthenticationSuccessHandler(){
        return new RestAuthenticationSuccessHandler();
    }
    /**登录失败后的处理*/
    @Bean
    @ConditionalOnMissingBean(RestAuthenticationFailureHandler.class)
    RestAuthenticationFailureHandler restAuthenticationFailureHandler(){
        return new RestAuthenticationFailureHandler();
    }
    /**
     * 登出成功后的处理
     */
    @Bean
    @ConditionalOnMissingBean(RestLogoutSuccessHandler.class)
    RestLogoutSuccessHandler restLogoutSuccessHandler(){
        return new RestLogoutSuccessHandler();
    }

    /**
     * 认证不成功的处理
     */
    @Bean
    @ConditionalOnMissingBean(RestAccessDeniedHandler.class)
    RestAccessDeniedHandler restAccessDeniedHandler() {

        return new RestAccessDeniedHandler();
    }

    /**
     * 权限不通过的处理
     */
    @Bean
    @ConditionalOnMissingBean(RestAuthenticationEntryPoint.class)
   RestAuthenticationEntryPoint restAuthenticationEntryPoint() {

        return new RestAuthenticationEntryPoint();
    }

}
