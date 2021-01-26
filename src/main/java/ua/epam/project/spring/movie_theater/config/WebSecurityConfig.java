package ua.epam.project.spring.movie_theater.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final DataSource dataSource;

    @Autowired
    public WebSecurityConfig(@Qualifier("dataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/css/**", "/js/**", "/img/**").permitAll()
                .and()
                .authorizeRequests()
                    .antMatchers("/","/home/**", "/register", "/seats").permitAll()
                    .antMatchers("/admin/**").access("hasAuthority('ADMIN')")
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/home")
                    .usernameParameter("email")
                    .defaultSuccessUrl("/home", true)
                    .permitAll()
                .and()
                    .logout()
                    .logoutSuccessUrl("/home")
                    .permitAll();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .jdbcAuthentication()
            .dataSource(dataSource)
            .passwordEncoder(passwordEncoder())
            .usersByUsernameQuery("select email, password, enabled from user where email = ?")
            .authoritiesByUsernameQuery("select user.email, user_role.roles from user join user_role on user_role.user_id = user.id where user.email = ?");
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }
}
