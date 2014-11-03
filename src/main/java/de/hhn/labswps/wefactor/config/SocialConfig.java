package de.hhn.labswps.wefactor.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.security.SocialUserDetailsService;

import de.hhn.labswps.wefactor.domain.UserConnectionRepository;
import de.hhn.labswps.wefactor.domain.UserProfileRepository;
import de.hhn.labswps.wefactor.domain.UsersConnectionRepositoryDelegate;
import de.hhn.labswps.wefactor.service.AccountConnectionSignUpService;
import de.hhn.labswps.wefactor.service.SimpleSignInAdapter;

/**
 * Created by magnus on 18/08/14.
 */
@Configuration
@EnableSocial
public class SocialConfig implements SocialConfigurer {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private SocialUserDetailsService socialUserDetailsService;

    @Autowired
    private UserConnectionRepository userConnectionRepository;

    @Override
    public void addConnectionFactories(
            ConnectionFactoryConfigurer connectionFactoryConfigurer,
            Environment environment) {
        connectionFactoryConfigurer
                .addConnectionFactory(new FacebookConnectionFactory(
                        environment.getProperty("spring.social.facebook.appId"),
                        environment
                                .getProperty("spring.social.facebook.appSecret")));

        connectionFactoryConfigurer
                .addConnectionFactory(new GoogleConnectionFactory(environment
                        .getProperty("spring.social.google.appId"), environment
                        .getProperty("spring.social.google.appSecret")));

    }

    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(
            ConnectionFactoryLocator connectionFactoryLocator) {
        UsersConnectionRepositoryDelegate repository = new UsersConnectionRepositoryDelegate(
                connectionFactoryLocator, Encryptors.noOpText(),
                userConnectionRepository);
        repository.setConnectionSignUp(new AccountConnectionSignUpService(
                userProfileRepository));
        return repository;
    }

    @Bean
    public ProviderSignInController providerSignInController(
            ConnectionFactoryLocator connectionFactoryLocator,
            UsersConnectionRepository usersConnectionRepository) {
        return new ProviderSignInController(connectionFactoryLocator,
                usersConnectionRepository,
                new SimpleSignInAdapter(socialUserDetailsService,
                        new HttpSessionRequestCache()));
    }
}
