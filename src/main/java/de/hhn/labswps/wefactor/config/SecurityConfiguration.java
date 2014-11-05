package de.hhn.labswps.wefactor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.social.security.SpringSocialConfigurer;

import de.hhn.labswps.wefactor.service.RepositoryUserDetailsService;
import de.hhn.labswps.wefactor.service.SimpleSocialUserDetailSevice;

@Configuration
@EnableWebMvcSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
        // Spring Security ignores request to static resources such as CSS or JS
        // files.
        .ignoring().antMatchers("/static/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        // Configures form login
        .formLogin()
                .loginPage("/signin")
                .loginProcessingUrl("/login/authenticate")
                .failureUrl("/signin?error=bad_credentials")
                // Configures the logout function
                .and()
                .logout()
                .deleteCookies("JSESSIONID")
                .logoutUrl("/logout")
                .logoutSuccessUrl("/signin")
                // Configures url based authorization
                .and()
                .authorizeRequests()
                // Anyone can access the urls
                .antMatchers("/auth/**", "/signin", "/signup/**",
                        "/user/register/**")
                .permitAll()
                // The rest of the our application is protected.
                .antMatchers("/test")
                .hasRole("USER")
                // Adds the SocialAuthenticationFilter to Spring Security's
                // filter chain.
                .and()
                .apply(new SpringSocialConfigurer().postLoginUrl("/")
                        .alwaysUsePostLoginUrl(true));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(
                passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public SocialUserDetailsService socialUserDetailsService() {
        return new SimpleSocialUserDetailSevice();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new RepositoryUserDetailsService();
    }

}