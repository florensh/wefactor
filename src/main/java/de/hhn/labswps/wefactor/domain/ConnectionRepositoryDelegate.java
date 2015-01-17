package de.hhn.labswps.wefactor.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.NotConnectedException;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * The Class ConnectionRepositoryDelegate.
 */
public class ConnectionRepositoryDelegate implements ConnectionRepository {

    /** The user id. */
    private String userId;

    /** The connection factory locator. */
    private ConnectionFactoryLocator connectionFactoryLocator;

    /** The text encryptor. */
    private TextEncryptor textEncryptor;

    /** The user connection repository. */
    private UserConnectionRepository userConnectionRepository;

    /**
     * Instantiates a new connection repository delegate.
     *
     * @param userId
     *            the user id
     * @param connectionFactoryLocator
     *            the connection factory locator
     * @param textEncryptor
     *            the text encryptor
     * @param userConnectionRepository
     *            the user connection repository
     */
    public ConnectionRepositoryDelegate(String userId,
            ConnectionFactoryLocator connectionFactoryLocator,
            TextEncryptor textEncryptor,
            UserConnectionRepository userConnectionRepository) {
        this.userId = userId;
        this.connectionFactoryLocator = connectionFactoryLocator;
        this.textEncryptor = textEncryptor;
        this.userConnectionRepository = userConnectionRepository;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.springframework.social.connect.ConnectionRepository#findAllConnections
     * ()
     */
    @Override
    public MultiValueMap<String, Connection<?>> findAllConnections() {
        List<UserConnection> allUserConnections = (List<UserConnection>) userConnectionRepository
                .findAll();

        List<Connection<?>> resultList = transform(allUserConnections);

        MultiValueMap<String, Connection<?>> connections = new LinkedMultiValueMap<String, Connection<?>>();
        Set<String> registeredProviderIds = connectionFactoryLocator
                .registeredProviderIds();
        for (String registeredProviderId : registeredProviderIds) {
            connections.put(registeredProviderId,
                    Collections.<Connection<?>> emptyList());
        }
        for (Connection<?> connection : resultList) {
            String providerId = connection.getKey().getProviderId();
            if (connections.get(providerId).size() == 0) {
                connections.put(providerId, new LinkedList<Connection<?>>());
            }
            connections.add(providerId, connection);
        }
        return connections;
    }

    /**
     * Transform.
     *
     * @param allUserConnections
     *            the all user connections
     * @return the list
     */
    private List<Connection<?>> transform(
            List<UserConnection> allUserConnections) {

        List<Connection<?>> result = new ArrayList<Connection<?>>();
        for (UserConnection c : allUserConnections) {
            result.add(transform(c));
        }

        return result;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.springframework.social.connect.ConnectionRepository#findConnections
     * (java.lang.String)
     */
    @Override
    public List<Connection<?>> findConnections(String providerId) {
        List<UserConnection> allUserConnections = (List<UserConnection>) userConnectionRepository
                .findByProviderId(providerId);

        List<Connection<?>> resultList = transform(allUserConnections);
        return resultList;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.springframework.social.connect.ConnectionRepository#findConnections
     * (java.lang.Class)
     */
    @Override
    public <A> List<Connection<A>> findConnections(Class<A> apiType) {
        List<?> connections = findConnections(getProviderId(apiType));
        return (List<Connection<A>>) connections;
    }

    /**
     * Gets the provider id.
     *
     * @param <A>
     *            the generic type
     * @param apiType
     *            the api type
     * @return the provider id
     */
    private <A> String getProviderId(Class<A> apiType) {
        return connectionFactoryLocator.getConnectionFactory(apiType)
                .getProviderId();
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.social.connect.ConnectionRepository#
     * findConnectionsToUsers(org.springframework.util.MultiValueMap)
     */
    @Override
    public MultiValueMap<String, Connection<?>> findConnectionsToUsers(
            MultiValueMap<String, String> providerUsers) {
        if (providerUsers == null || providerUsers.isEmpty()) {
            throw new IllegalArgumentException(
                    "Unable to execute find: no providerUsers provided");
        }

        List<UserConnection> conn = new ArrayList<UserConnection>();

        for (Iterator<Entry<String, List<String>>> it = providerUsers
                .entrySet().iterator(); it.hasNext();) {

            Entry<String, List<String>> entry = it.next();
            String providerId = entry.getKey();
            List<String> providerUserIds = entry.getValue();

            List<UserConnection> providerConnections = this.userConnectionRepository
                    .findByProviderIdAndProviderUserIdIn(providerId,
                            providerUserIds);

            conn.addAll(providerConnections);

        }

        List<Connection<?>> resultList = transform(conn);

        MultiValueMap<String, Connection<?>> connectionsForUsers = new LinkedMultiValueMap<String, Connection<?>>();

        for (Connection<?> connection : resultList) {
            String providerId = connection.getKey().getProviderId();
            List<String> userIds = providerUsers.get(providerId);
            List<Connection<?>> connections = connectionsForUsers
                    .get(providerId);
            if (connections == null) {
                connections = new ArrayList<Connection<?>>(userIds.size());
                for (int i = 0; i < userIds.size(); i++) {
                    connections.add(null);
                }
                connectionsForUsers.put(providerId, connections);
            }
            String providerUserId = connection.getKey().getProviderUserId();
            int connectionIndex = userIds.indexOf(providerUserId);
            connections.set(connectionIndex, connection);
        }
        return connectionsForUsers;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.springframework.social.connect.ConnectionRepository#getConnection
     * (org.springframework.social.connect.ConnectionKey)
     */
    @Override
    public Connection<?> getConnection(ConnectionKey connectionKey) {
        UserConnection conn = this.userConnectionRepository
                .findByUserIdAndProviderIdAndProviderUserId(userId,
                        connectionKey.getProviderId(),
                        connectionKey.getProviderUserId());
        return transform(conn);
    }

    /**
     * Transform.
     *
     * @param conn
     *            the conn
     * @return the connection
     */
    private Connection<?> transform(UserConnection conn) {
        ConnectionData cd = createConnectionData(conn);
        ConnectionFactory<?> connectionFactory = connectionFactoryLocator
                .getConnectionFactory(conn.getProviderId());
        return connectionFactory.createConnection(cd);

    }

    /**
     * Creates the connection data.
     *
     * @param conn
     *            the conn
     * @return the connection data
     */
    private ConnectionData createConnectionData(UserConnection conn) {
        return new ConnectionData(conn.getProviderId(),
                conn.getProviderUserId(), conn.getDisplayName(),
                conn.getProfileUrl(), conn.getImageUrl(),
                conn.getAccessToken(), conn.getSecret(),
                conn.getRefreshToken(), conn.getExpireTime());
    }

    /*
     * (non-Javadoc)
     * @see
     * org.springframework.social.connect.ConnectionRepository#getConnection
     * (java.lang.Class, java.lang.String)
     */
    @Override
    public <A> Connection<A> getConnection(Class<A> apiType,
            String providerUserId) {
        String providerId = getProviderId(apiType);
        return (Connection<A>) getConnection(new ConnectionKey(providerId,
                providerUserId));
    }

    /*
     * (non-Javadoc)
     * @see
     * org.springframework.social.connect.ConnectionRepository#getPrimaryConnection
     * (java.lang.Class)
     */
    @Override
    public <A> Connection<A> getPrimaryConnection(Class<A> apiType) {
        String providerId = getProviderId(apiType);
        Connection<A> connection = (Connection<A>) findPrimaryConnection(providerId);
        if (connection == null) {
            throw new NotConnectedException(providerId);
        }
        return connection;
    }

    /**
     * Find primary connection.
     *
     * @param providerId
     *            the provider id
     * @return the connection
     */
    private Connection<?> findPrimaryConnection(String providerId) {
        List<UserConnection> conn = this.userConnectionRepository
                .findByUserIdAndProviderIdOrderByRankDesc(userId, providerId);
        List<Connection<?>> connections = transform(conn);
        if (connections.size() > 0) {
            return connections.get(0);
        } else {
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * org.springframework.social.connect.ConnectionRepository#findPrimaryConnection
     * (java.lang.Class)
     */
    @Override
    public <A> Connection<A> findPrimaryConnection(Class<A> apiType) {
        String providerId = getProviderId(apiType);
        return (Connection<A>) findPrimaryConnection(providerId);
    }

    /*
     * (non-Javadoc)
     * @see
     * org.springframework.social.connect.ConnectionRepository#addConnection
     * (org.springframework.social.connect.Connection)
     */
    @Override
    public void addConnection(Connection<?> connection) {
        UserConnection conn = transform(connection);
        this.userConnectionRepository.save(conn);

    }

    /**
     * Transform.
     *
     * @param c
     *            the c
     * @return the user connection
     */
    private UserConnection transform(Connection<?> c) {
        ConnectionData data = c.createData();
        int rank = 0; // TODO
        return new UserConnection(userId, data.getProviderId(),
                data.getProviderUserId(), rank, data.getDisplayName(),
                data.getProfileUrl(), data.getImageUrl(),
                data.getAccessToken(), data.getSecret(),
                data.getRefreshToken(), data.getExpireTime());
    }

    /*
     * (non-Javadoc)
     * @see
     * org.springframework.social.connect.ConnectionRepository#updateConnection
     * (org.springframework.social.connect.Connection)
     */
    @Override
    public void updateConnection(Connection<?> connection) {
        UserConnection conn = transform(connection);
        UserConnection fromDb = this.userConnectionRepository
                .findByUserIdAndProviderIdAndProviderUserId(conn.getUserId(),
                        conn.getProviderId(), conn.getProviderUserId());
        updateUserConnection(fromDb, conn);
        this.userConnectionRepository.save(fromDb);

    }

    /**
     * Update user connection.
     *
     * @param fromDb
     *            the from db
     * @param conn
     *            the conn
     */
    private void updateUserConnection(UserConnection fromDb, UserConnection conn) {
        fromDb.setAccessToken(conn.getAccessToken());
        fromDb.setDisplayName(conn.getDisplayName());
        fromDb.setExpireTime(conn.getExpireTime());
        fromDb.setImageUrl(conn.getImageUrl());
        fromDb.setProfileUrl(conn.getProfileUrl());
        fromDb.setProviderId(conn.getProviderId());
        fromDb.setProviderUserId(conn.getProviderUserId());
        fromDb.setRank(conn.getRank());
        fromDb.setRefreshToken(conn.getRefreshToken());
        fromDb.setSecret(conn.getSecret());
        fromDb.setUserId(conn.getUserId());

    }

    /*
     * (non-Javadoc)
     * @see
     * org.springframework.social.connect.ConnectionRepository#removeConnections
     * (java.lang.String)
     */
    @Override
    public void removeConnections(String providerId) {
        this.userConnectionRepository.deleteByProviderId(providerId);

    }

    /*
     * (non-Javadoc)
     * @see
     * org.springframework.social.connect.ConnectionRepository#removeConnection
     * (org.springframework.social.connect.ConnectionKey)
     */
    @Override
    public void removeConnection(ConnectionKey connectionKey) {
        this.userConnectionRepository
                .deleteByUserIdAndProviderIdAndProviderUserId(userId,
                        connectionKey.getProviderId(),
                        connectionKey.getProviderUserId());

    }

}
