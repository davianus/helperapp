package at.ac.tuwien.mase.backend.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

/**
 * Created by lobmaier on 27.12.2015.
 */
@EnableWebSecurity
@EnableWebMvcSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/user").permitAll()
                .anyRequest().fullyAuthenticated()
                .and()
            .httpBasic()
                .and()
            .csrf().disable();
    }
}
