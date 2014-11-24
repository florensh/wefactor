package de.hhn.labswps.wefactor.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "userconnection")
public class UserConnection {

    private String userId;

    private String providerId;
    private String providerUserId;
    private int rank;
    private String displayName;
    private String profileUrl;
    private String imageUrl;
    private String accessToken;
    private String secret;
    private String refreshToken;
    private Long expireTime;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserConnection() {

    }

    public UserConnection(String userId, String providerId,
            String providerUserId, int rank, String displayName,
            String profileUrl, String imageUrl, String accessToken,
            String secret, String refreshToken, Long expireTime) {
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

    public String toString() {
        return "userId = " + userId + ", providerId = " + providerId
                + ", providerUserId = " + providerUserId + ", rank = " + rank
                + ", displayName = " + displayName + ", profileUrl = "
                + profileUrl + ", imageUrl = " + imageUrl + ", accessToken = "
                + accessToken + ", secret = " + secret + ", refreshToken = "
                + refreshToken + ", expireTime = " + expireTime;
    }

    public String getUserId() {
        return userId;
    }

    public String getProviderId() {
        return providerId;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public int getRank() {
        return rank;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getSecret() {
        return secret;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public Long getExpireTime() {
        return expireTime;
    }
}