package de.hhn.labswps.wefactor.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The Class UserConnection.
 */
@Entity
@Table(name = "userconnection")
public class UserConnection {

    /** The user id. */
    private String userId;

    /** The provider id. */
    private String providerId;

    /** The provider user id. */
    private String providerUserId;

    /** The rank. */
    private int rank;

    /** The display name. */
    private String displayName;

    /** The profile url. */
    private String profileUrl;

    /** The image url. */
    private String imageUrl;

    /** The access token. */
    private String accessToken;

    /** The secret. */
    private String secret;

    /** The refresh token. */
    private String refreshToken;

    /** The expire time. */
    private Long expireTime;

    /**
     * Sets the user id.
     *
     * @param userId
     *            the new user id
     */
    public void setUserId(final String userId) {
        this.userId = userId;
    }

    /**
     * Sets the provider id.
     *
     * @param providerId
     *            the new provider id
     */
    public void setProviderId(final String providerId) {
        this.providerId = providerId;
    }

    /**
     * Sets the provider user id.
     *
     * @param providerUserId
     *            the new provider user id
     */
    public void setProviderUserId(final String providerUserId) {
        this.providerUserId = providerUserId;
    }

    /**
     * Sets the rank.
     *
     * @param rank
     *            the new rank
     */
    public void setRank(final int rank) {
        this.rank = rank;
    }

    /**
     * Sets the display name.
     *
     * @param displayName
     *            the new display name
     */
    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }

    /**
     * Sets the profile url.
     *
     * @param profileUrl
     *            the new profile url
     */
    public void setProfileUrl(final String profileUrl) {
        this.profileUrl = profileUrl;
    }

    /**
     * Sets the image url.
     *
     * @param imageUrl
     *            the new image url
     */
    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * Sets the access token.
     *
     * @param accessToken
     *            the new access token
     */
    public void setAccessToken(final String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Sets the secret.
     *
     * @param secret
     *            the new secret
     */
    public void setSecret(final String secret) {
        this.secret = secret;
    }

    /**
     * Sets the refresh token.
     *
     * @param refreshToken
     *            the new refresh token
     */
    public void setRefreshToken(final String refreshToken) {
        this.refreshToken = refreshToken;
    }

    /**
     * Sets the expire time.
     *
     * @param expireTime
     *            the new expire time
     */
    public void setExpireTime(final Long expireTime) {
        this.expireTime = expireTime;
    }

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Gets the id.
     *
     * @return the id
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Sets the id.
     *
     * @param id
     *            the new id
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Instantiates a new user connection.
     */
    public UserConnection() {

    }

    /**
     * Instantiates a new user connection.
     *
     * @param userId
     *            the user id
     * @param providerId
     *            the provider id
     * @param providerUserId
     *            the provider user id
     * @param rank
     *            the rank
     * @param displayName
     *            the display name
     * @param profileUrl
     *            the profile url
     * @param imageUrl
     *            the image url
     * @param accessToken
     *            the access token
     * @param secret
     *            the secret
     * @param refreshToken
     *            the refresh token
     * @param expireTime
     *            the expire time
     */
    public UserConnection(final String userId, final String providerId,
            final String providerUserId, final int rank,
            final String displayName, final String profileUrl,
            final String imageUrl, final String accessToken,
            final String secret, final String refreshToken,
            final Long expireTime) {
        this.userId = userId;
        this.providerId = providerId;
        this.providerUserId = providerUserId;
        this.rank = rank;
        this.displayName = displayName;
        this.profileUrl = profileUrl;
        this.imageUrl = imageUrl;
        this.accessToken = accessToken;
        this.secret = secret;
        this.refreshToken = refreshToken;
        this.expireTime = expireTime;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "userId = " + this.userId + ", providerId = " + this.providerId
                + ", providerUserId = " + this.providerUserId + ", rank = "
                + this.rank + ", displayName = " + this.displayName
                + ", profileUrl = " + this.profileUrl + ", imageUrl = "
                + this.imageUrl + ", accessToken = " + this.accessToken
                + ", secret = " + this.secret + ", refreshToken = "
                + this.refreshToken + ", expireTime = " + this.expireTime;
    }

    /**
     * Gets the user id.
     *
     * @return the user id
     */
    public String getUserId() {
        return this.userId;
    }

    /**
     * Gets the provider id.
     *
     * @return the provider id
     */
    public String getProviderId() {
        return this.providerId;
    }

    /**
     * Gets the provider user id.
     *
     * @return the provider user id
     */
    public String getProviderUserId() {
        return this.providerUserId;
    }

    /**
     * Gets the rank.
     *
     * @return the rank
     */
    public int getRank() {
        return this.rank;
    }

    /**
     * Gets the display name.
     *
     * @return the display name
     */
    public String getDisplayName() {
        return this.displayName;
    }

    /**
     * Gets the profile url.
     *
     * @return the profile url
     */
    public String getProfileUrl() {
        return this.profileUrl;
    }

    /**
     * Gets the image url.
     *
     * @return the image url
     */
    public String getImageUrl() {
        return this.imageUrl;
    }

    /**
     * Gets the access token.
     *
     * @return the access token
     */
    public String getAccessToken() {
        return this.accessToken;
    }

    /**
     * Gets the secret.
     *
     * @return the secret
     */
    public String getSecret() {
        return this.secret;
    }

    /**
     * Gets the refresh token.
     *
     * @return the refresh token
     */
    public String getRefreshToken() {
        return this.refreshToken;
    }

    /**
     * Gets the expire time.
     *
     * @return the expire time
     */
    public Long getExpireTime() {
        return this.expireTime;
    }
}