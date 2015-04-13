package de.hhn.labswps.wefactor.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import de.hhn.labswps.wefactor.specification.WeFactorValues;
import de.hhn.labswps.wefactor.specification.WeFactorValues.ProviderIdentification;

/**
 * The UserProfile contains data to identify one user to others.
 */
@Entity
@Table(name = "userprofile")
@JsonIgnoreProperties({ "account", "createdBy", "lastModifiedBy" })
public class UserProfile extends User implements Serializable {

    /**
     * Gets the serialversionuid.
     *
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 4919241877246969045L;

    /** The account. */
    private Account account;

    /** The name. */
    private String name;

    /** The first name. */
    private String firstName;

    /** The last name. */
    private String lastName;

    /** The username. */
    private String username;

    /** The description. */
    private String description;

    /** The provider id. */
    private String providerId;

    /** The id. */
    private Long id;

    /** The image url. */
    private String imageUrl;

    /**
     * Instantiates a new user profile.
     */
    public UserProfile() {

    }

    /**
     * Instantiates a new user profile.
     *
     * @param account
     *            the account
     * @param up
     *            the up
     * @param imageUrl
     *            the image url
     * @param providerId
     *            the provider id
     */
    public UserProfile(final Account account,
            final org.springframework.social.connect.UserProfile up,
            final String imageUrl, final ProviderIdentification providerId) {
        account.addProfile(this);
        // this.name = up.getName();
        this.firstName = up.getFirstName();
        this.lastName = up.getLastName();
        this.email = up.getEmail();
        this.username = up.getUsername();
        this.providerId = providerId.name();
        this.imageUrl = this.fixImageUrl(imageUrl);

        this.account = account;
        this.account.setRoles(WeFactorValues.Role.ROLE_USER.name());
        this.password = up.getUsername(); // TODO improve!!!
        this.fixName();
    }

    /**
     * Instantiates a new user profile.
     *
     * @param account
     *            the account
     * @param email
     *            the email
     * @param username
     *            the username
     * @param password
     *            the password
     * @param providerIdentification
     *            the provider identification
     */
    public UserProfile(final Account account, final String email,
            final String username, final String password,
            final ProviderIdentification providerIdentification) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.account = account;
        account.addProfile(this);
        // this.account.setRoles(WeFactorValues.Role.ROLE_USER.name());
        this.providerId = providerIdentification.name();
        this.imageUrl = WeFactorValues.DEFAULT_IMAGE_URL;

        this.fixName();
    }

    /**
     * Instantiates a new user profile.
     *
     * @param account
     *            the account
     * @param name
     *            the name
     * @param firstName
     *            the first name
     * @param lastName
     *            the last name
     * @param email
     *            the email
     * @param username
     *            the username
     */
    public UserProfile(final Account account, final String name,
            final String firstName, final String lastName, final String email,
            final String username) {
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.account = account;
        account.addProfile(this);
        // this.account.setRoles(WeFactorValues.Role.ROLE_USER.name());

        this.fixName();
    }

    public UserProfile(final Account account, final String name,
            final String firstName, final String lastName, final String email,
            final String username, final String password,
            final ProviderIdentification providerIdentification) {
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.account = account;
        this.providerId = providerIdentification.name();
        this.imageUrl = WeFactorValues.DEFAULT_IMAGE_URL;
        account.addProfile(this);
        // this.account.setRoles(WeFactorValues.Role.ROLE_USER.name());

        this.fixName();
    }

    /**
     * Fix image url.
     *
     * @param imageUrl
     *            the image url
     * @return the string
     */
    private String fixImageUrl(final String imageUrl) {

        final ProviderIdentification providerId = this.getProviderIdAsType();
        String retVal = null;

        switch (providerId) {
            case GOOGLE:
                retVal = imageUrl.replace("sz=50", "sz=150");
                break;

            default:
                break;
        }

        return retVal;
    }

    /**
     * Fix name.
     */
    private void fixName() {
        // Is the name null?
        if (this.name == null) {

            if (ProviderIdentification.WEFACTOR.equals(this
                    .getProviderIdAsType())) {
                this.name = this.username;

            } else if (ProviderIdentification.LDAP.equals(this
                    .getProviderIdAsType())) {
                this.name = this.firstName + " " + this.lastName;
            } else {
                this.name = this.email;
            }

        }
    }

    /**
     * Gets the account.
     *
     * @return the account
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "myAccount", nullable = false)
    public Account getAccount() {
        return this.account;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    @Lob
    public String getDescription() {
        return this.description;
    }

    /*
     * (non-Javadoc)
     * @see de.hhn.labswps.wefactor.domain.User#getEmail()
     */
    @Override
    @Column(name = "email", unique = true)
    public String getEmail() {
        return this.email;
    }

    /**
     * Gets the first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return this.id;
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
     * Gets the last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /*
     * (non-Javadoc)
     * @see de.hhn.labswps.wefactor.domain.User#getPassword()
     */
    @Override
    @Column(name = "password")
    public String getPassword() {
        return super.getPassword();
    }

    /**
     * Gets the provider id.
     *
     * @return the provider id
     */
    @Column(name = "providerId")
    public String getProviderId() {
        return this.providerId;
    }

    /**
     * Gets the provider id as type.
     *
     * @return the provider id as type
     */
    @Transient
    public ProviderIdentification getProviderIdAsType() {
        return ProviderIdentification.valueOf(this.providerId);

    }

    /**
     * Gets the user id.
     *
     * @return the user id
     */
    @Transient
    public String getUserId() {
        return String.valueOf(this.id);
    }

    /**
     * Gets the username.
     *
     * @return the username
     */
    @Column(unique = true)
    public String getUsername() {
        return this.username;
    }

    /**
     * Sets the account.
     *
     * @param account
     *            the new account
     */
    public void setAccount(final Account account) {
        this.account = account;
        if (account != null) {
            account.addProfile(this);

        }
    }

    /**
     * Sets the description.
     *
     * @param description
     *            the new description
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Sets the first name.
     *
     * @param firstName
     *            the new first name
     */
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
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
     * Sets the image url.
     *
     * @param imageUrl
     *            the new image url
     */
    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * Sets the last name.
     *
     * @param lastName
     *            the new last name
     */
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    /**
     * Sets the name.
     *
     * @param name
     *            the new name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /*
     * (non-Javadoc)
     * @see de.hhn.labswps.wefactor.domain.User#setPassword(java.lang.String)
     */
    @Override
    public void setPassword(final String password) {
        super.setPassword(password);
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
     * Sets the username.
     *
     * @param username
     *            the new username
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "name = " + this.name + ", firstName = " + this.firstName
                + ", lastName = " + this.lastName + ", email = " + this.email
                + ", username = " + this.username;
    }
}