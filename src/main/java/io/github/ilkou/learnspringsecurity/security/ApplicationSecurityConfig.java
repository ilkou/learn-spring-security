package io.github.ilkou.learnspringsecurity.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static io.github.ilkou.learnspringsecurity.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true) // when using annotations instead of matchers
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

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
                .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails ilkou = User.builder()
                .username("ilkou")
                .password(passwordEncoder.encode("test"))
//                .roles(ADMIN.name())
                .authorities(ADMIN.getGrantedAuthorities())
                .build();

        UserDetails achraf = User.builder()
                .username("achraf")
                .password(passwordEncoder.encode("1234"))
//                .roles(CUSTOMER.name())
                .authorities(CUSTOMER.getGrantedAuthorities())
                .build();

        UserDetails saad = User.builder()
                .username("saad")
                .password(passwordEncoder.encode("1234"))
//                .roles(CUSTOMER.name())
                .authorities(ADMINTRAINEE.getGrantedAuthorities())
                .build();
        return new InMemoryUserDetailsManager(ilkou, achraf, saad);
    }
}
