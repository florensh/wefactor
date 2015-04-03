package de.hhn.labswps.wefactor.config;

import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.social.security.SpringSocialConfigurer;
import org.springframework.web.filter.CharacterEncodingFilter;

import de.hhn.labswps.wefactor.service.RepositoryUserDetailsService;
import de.hhn.labswps.wefactor.service.SimpleSocialUserDetailSevice;

/**
 * The security configuration of the web application.
 */
@Configuration
@EnableWebMvcSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    /** The Constant BCRYPT_PASSWORD_ENCODER_STRENGTH. */
    private static final int BCRYPT_PASSWORD_ENCODER_STRENGTH = 10;

    @Value("${allowSignup}")
    Boolean allowSignup;

    @Value("${allowLDAP}")
    Boolean allowLDAP;

    /*
     * (non-Javadoc)
     * @see org.springframework.security.config.annotation.web.configuration.
     * WebSecurityConfigurerAdapter
     * #configure(org.springframework.security.config
     * .annotation.web.builders.WebSecurity)
     */
    @Override
    public void configure(final WebSecurity web) throws Exception {
        web
        // Spring Security ignores request to static resources such as CSS or JS
        // files.
        .ignoring().antMatchers("/static/**");
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.security.config.annotation.web.configuration.
     * WebSecurityConfigurerAdapter
     * #configure(org.springframework.security.config
     * .annotation.web.builders.HttpSecurity)
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {

        final CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter, CsrfFilter.class);

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
                        "/user/register/**").permitAll()
                // The rest of the our application is protected.
                .antMatchers("/user/**").authenticated()
                // .hasRole("USER")
                // Adds the SocialAuthenticationFilter to Spring Security's
                // filter chain.
                .and()
                .apply(new SpringSocialConfigurer().postLoginUrl("/")
                        .alwaysUsePostLoginUrl(true));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.security.config.annotation.web.configuration.
     * WebSecurityConfigurerAdapter
     * #configure(org.springframework.security.config
     * .annotation.authentication.builders.AuthenticationManagerBuilder)
     */
    @Override
    protected void configure(final AuthenticationManagerBuilder auth)
            throws Exception {

        // ldap
        if (Boolean.TRUE.equals(this.allowLDAP)) {
            auth.ldapAuthentication()
                    .userDnPatterns("uid={0},ou=people,dc=hs-heilbronn, dc=de")
                    .groupSearchBase("dc=hs-heilbronn, dc=de").contextSource()
                    .url("ldaps://zld0-master.hs-heilbronn.de").port(636)
                    // .ldif("classpath:test-server.ldif")
                    .and().userDetailsContextMapper(userDetailsContextMapper())

            ;

        }

        // // ldap dummy
        // auth.ldapAuthentication().userDnPatterns("uid={0},ou=people")
        // .userDnPatterns("uid={0},ou=people")
        // .groupSearchBase("ou=groups").contextSource()
        // .ldif("classpath:test-server.ldif").and()
        // .userDetailsContextMapper(userDetailsContextMapper())
        //
        // ;

        // normal sign up
        if (Boolean.TRUE.equals(this.allowSignup)) {
            auth.userDetailsService(this.userDetailsService()).passwordEncoder(
                    this.passwordEncoder());

        }

    }

    /**
     * Creates a bean of Type PasswordEncoder.
     *
     * @return the bean passwordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(BCRYPT_PASSWORD_ENCODER_STRENGTH);
    }

    /**
     * Creates a bean of type SocialUserDetailsService.
     *
     * @return the bean socialUserDetailsService
     */
    @Bean
    public SocialUserDetailsService socialUserDetailsService() {
        return new SimpleSocialUserDetailSevice();
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.security.config.annotation.web.configuration.
     * WebSecurityConfigurerAdapter#userDetailsService()
     */
    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        return new RepositoryUserDetailsService();
    }

    @Bean
    public UserDetailsContextMapper userDetailsContextMapper() {
        return new CustomUserDetailsContextMapper();
    }

}