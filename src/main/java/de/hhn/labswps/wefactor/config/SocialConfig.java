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
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.security.SocialUserDetailsService;

import de.hhn.labswps.wefactor.domain.AccountRepository;
import de.hhn.labswps.wefactor.domain.UserConnectionRepository;
import de.hhn.labswps.wefactor.domain.UserProfileRepository;
import de.hhn.labswps.wefactor.domain.UsersConnectionRepositoryDelegate;
import de.hhn.labswps.wefactor.service.AccountConnectionSignUpService;
import de.hhn.labswps.wefactor.service.SimpleSignInAdapter;

/**
 * The social configuration of the web application.
 */
@Configuration
@EnableSocial
public class SocialConfig implements SocialConfigurer {

    /** The data source. */
    @Autowired
    private DataSource dataSource;

    /** The user profile repository. */
    @Autowired
    private UserProfileRepository userProfileRepository;

    /** The account repository. */
    @Autowired
    private AccountRepository accountRepository;

    /** The social user details service. */
    @Autowired
    private SocialUserDetailsService socialUserDetailsService;

    /** The user connection repository. */
    @Autowired
    private UserConnectionRepository userConnectionRepository;

    /*
     * (non-Javadoc)
     * @see org.springframework.social.config.annotation.SocialConfigurer#
     * addConnectionFactories
     * (org.springframework.social.config.annotation.ConnectionFactoryConfigurer
     * , org.springframework.core.env.Environment)
     */
    @Override
    public void addConnectionFactories(
            final ConnectionFactoryConfigurer connectionFactoryConfigurer,
            final Environment environment) {

        connectionFactoryConfigurer
                .addConnectionFactory(new GoogleConnectionFactory(environment
                        .getProperty("spring.social.google.appId"), environment
                        .getProperty("spring.social.google.appSecret")));

    }

    /*
     * (non-Javadoc)
     * @see
     * org.springframework.social.config.annotation.SocialConfigurer#getUserIdSource
     * ()
     */
    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.social.config.annotation.SocialConfigurer#
     * getUsersConnectionRepository
     * (org.springframework.social.connect.ConnectionFactoryLocator)
     */
    @Override
    public UsersConnectionRepository getUsersConnectionRepository(
            final ConnectionFactoryLocator connectionFactoryLocator) {
        final UsersConnectionRepositoryDelegate repository = new UsersConnectionRepositoryDelegate(
                connectionFactoryLocator, Encryptors.noOpText(),
                this.userConnectionRepository);
        repository.setConnectionSignUp(new AccountConnectionSignUpService(
                this.userProfileRepository, this.accountRepository));
        return repository;
    }

    /**
     * Provider sign in controller.
     *
     * @param connectionFactoryLocator
     *            the connection factory locator
     * @param usersConnectionRepository
     *            the users connection repository
     * @return the provider sign in controller
     */
    @Bean
    public ProviderSignInController providerSignInController(
            final ConnectionFactoryLocator connectionFactoryLocator,
            final UsersConnectionRepository usersConnectionRepository) {
        return new ProviderSignInController(connectionFactoryLocator,
                usersConnectionRepository, new SimpleSignInAdapter(
                        this.socialUserDetailsService,
                        new HttpSessionRequestCache()));
    }
}
