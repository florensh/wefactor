package de.hhn.labswps.wefactor.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;

/**
 * The Class UsersConnectionRepositoryDelegate.
 */
public class UsersConnectionRepositoryDelegate implements
        UsersConnectionRepository {

    /** The user connection repository. */
    private UserConnectionRepository userConnectionRepository;

    /** The connection sign up. */
    private ConnectionSignUp connectionSignUp;

    /** The connection factory locator. */
    private ConnectionFactoryLocator connectionFactoryLocator;

    /** The text encryptor. */
    private TextEncryptor textEncryptor;

    /**
     * Instantiates a new users connection repository delegate.
     *
     * @param connectionFactoryLocator
     *            the connection factory locator
     * @param textEncryptor
     *            the text encryptor
     * @param userConnectionRepository
     *            the user connection repository
     */
    public UsersConnectionRepositoryDelegate(
            ConnectionFactoryLocator connectionFactoryLocator,
            TextEncryptor textEncryptor,
            UserConnectionRepository userConnectionRepository) {
        this.connectionFactoryLocator = connectionFactoryLocator;
        this.textEncryptor = textEncryptor;
        this.userConnectionRepository = userConnectionRepository;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.social.connect.UsersConnectionRepository#
     * findUserIdsWithConnection(org.springframework.social.connect.Connection)
     */
    @Override
    public List<String> findUserIdsWithConnection(Connection<?> connection) {
        ConnectionKey key = connection.getKey();

        List<String> localUserIds = new ArrayList<String>();
        List<UserConnection> uc = this.userConnectionRepository
                .findByProviderIdAndProviderUserId(key.getProviderId(),
                        key.getProviderUserId());

        for (UserConnection conn : uc) {
            localUserIds.add(conn.getUserId());
        }

        if (localUserIds.size() == 0 && connectionSignUp != null) {
            String newUserId = connectionSignUp.execute(connection);
            if (newUserId != null) {
                createConnectionRepository(newUserId).addConnection(connection);
                return Arrays.asList(newUserId);
            }
        }
        return localUserIds;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.social.connect.UsersConnectionRepository#
     * findUserIdsConnectedTo(java.lang.String, java.util.Set)
     */
    @Override
    public Set<String> findUserIdsConnectedTo(String providerId,
            Set<String> providerUserIds) {
        Set<String> userIds = new HashSet<String>();

        List<UserConnection> connections = this.userConnectionRepository
                .findByProviderIdAndProviderUserIdIn(providerId,
                        new ArrayList<String>(providerUserIds));

        for (UserConnection c : connections) {
            userIds.add(c.getUserId());
        }

        return userIds;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.social.connect.UsersConnectionRepository#
     * createConnectionRepository(java.lang.String)
     */
    @Override
    public ConnectionRepository createConnectionRepository(String userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }
        return new ConnectionRepositoryDelegate(userId,
                connectionFactoryLocator, textEncryptor,
                userConnectionRepository);
    }

    /**
     * Sets the connection sign up.
     *
     * @param connectionSignUp
     *            the new connection sign up
     */
    public void setConnectionSignUp(ConnectionSignUp connectionSignUp) {
        this.connectionSignUp = connectionSignUp;
    }

}
