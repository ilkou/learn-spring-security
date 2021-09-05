package io.github.ilkou.learnspringsecurity.security;

import io.github.ilkou.learnspringsecurity.auth.ApplicationUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true) // when using annotations instead of matchers
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserService applicationUserService;

    // use CRSF protection for any request that could be processed by a browser by normal users
    // here we are using them in postman (non-browser client)
    // ps: add it in Headers as "X-XSRF-TOKEN": "the value of the token "
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // * Enabling csrf
//                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                .and()
                // or simply:
//                 .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                // * Disabling csrf
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/index.html", "/css/*", "/js/*").permitAll()
//                .antMatchers("/api/v1/customers/*").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())
//                .antMatchers(HttpMethod.GET, "/api/v1/products/*").hasAnyRole(ADMIN.name(), CUSTOMER.name())
//                .antMatchers(HttpMethod.GET, "/api/v1/products").hasAnyRole(ADMIN.name(), CUSTOMER.name())
//                .antMatchers(HttpMethod.POST, "/api/v1/products").hasAuthority(PRODUCT_WRITE.getPermission())
//                .antMatchers(HttpMethod.PUT, "/api/v1/products/*").hasAuthority(PRODUCT_WRITE.getPermission())
//                .antMatchers(HttpMethod.DELETE, "/api/v1/products/*").hasAuthority(PRODUCT_WRITE.getPermission())
                .anyRequest()
                .authenticated()
                .and()
                // * form based auth: SESSION ID that expires after 30mins of inactivity by default
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .defaultSuccessUrl("/products", true)
                    .passwordParameter("password")
                    .usernameParameter("username")
                .and()
                .rememberMe() // default is 2 weeks
                    .tokenValiditySeconds(((int) TimeUnit.DAYS.toSeconds(21)))
                    .key("FNYdeHDScgYdvTdSgVWrCwWE") // something very secure :3
                    .rememberMeParameter("remember-me")
                .and()
                .logout()
                    .logoutUrl("/logout")
                // https://docs.spring.io/spring-security/site/docs/3.2.8.RELEASE/apidocs/org/springframework/security/config/annotation/web/configurers/LogoutConfigurer.html#logoutUrl
                // if CSRF is enabled then:
//                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID", "remember-me")
                    .logoutSuccessUrl("/login");
                // * Basic Auth
//                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserService);
        return provider;
    }

//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        UserDetails ilkou = User.builder()
//                .username("ilkou")
//                .password(passwordEncoder.encode("test"))
////                .roles(ADMIN.name())
//                .authorities(ADMIN.getGrantedAuthorities())
//                .build();
//
//        UserDetails achraf = User.builder()
//                .username("achraf")
//                .password(passwordEncoder.encode("1234"))
////                .roles(CUSTOMER.name())
//                .authorities(CUSTOMER.getGrantedAuthorities())
//                .build();
//
//        UserDetails saad = User.builder()
//                .username("saad")
//                .password(passwordEncoder.encode("1234"))
////                .roles(CUSTOMER.name())
//                .authorities(ADMINTRAINEE.getGrantedAuthorities())
//                .build();
//        return new InMemoryUserDetailsManager(ilkou, achraf, saad);
//    }
}
