package com.example.demo.config;

import com.example.demo.model.MyUser;
import com.example.demo.model.Role;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService((UserDetailsService) userService)
                .passwordEncoder(passwordEncoder());
    }

    @PostConstruct
    public void createUser(){
        List<MyUser> users = (List<MyUser>) userService.findAll();
        if(users.isEmpty()){
            MyUser user = new MyUser();
            user.setUserId(1L);
            user.setUserName("admin");
            user.setPassword(passwordEncoder().encode("admin"));
            Role role= new Role();
            role.setRoleId(1L);
            user.setRole(role);
            userService.save(user);
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/home").permitAll()
                .antMatchers("/editUser").access("hasRole('ADMIN')")
                .antMatchers("/deleteUser").access("hasRole('ADMIN')")
                .antMatchers("/listUser").access("hasRole('ADMIN')")
                .antMatchers("/product/**").access("hasRole('ADMIN')")
//                .antMatchers("/home/**").access("hasRole('USER')")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/home/login")
//                .loginProcessingUrl("/authenticateTheUser")
//                .defaultSuccessUrl("/home", true)
//                .successHandler(customSuccessHandler)
                .usernameParameter("userName").passwordParameter("password")
                .and()
                .logout()
                .logoutSuccessUrl("/home")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/home/logout"))
                .permitAll()
                .and().exceptionHandling().accessDeniedPage("/error");
        http.csrf().disable();
        http.authorizeRequests().and()
                .rememberMe().tokenRepository(this.persistentTokenRepository())
                .tokenValiditySeconds(1 * 24 * 60 * 60);
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);
        return db;
    }

}
