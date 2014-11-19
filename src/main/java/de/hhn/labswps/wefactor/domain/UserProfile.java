package de.hhn.labswps.wefactor.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import de.hhn.labswps.wefactor.specification.WeFactorValues;
import de.hhn.labswps.wefactor.specification.WeFactorValues.ProviderIdentification;

@Entity
@Table(name = "userprofile")
public class UserProfile extends User implements Serializable {

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /**
     *
     */
    private static final long serialVersionUID = 4919241877246969045L;

    private Account account;

    private String name;

    private String firstName;

    private String lastName;

    private String username;

    private String description;

    private String providerId;

    private Long id;

    private String imageUrl;

    public UserProfile() {

    }

    public UserProfile(final Account account,
            final org.springframework.social.connect.UserProfile up,
            String imageUrl, ProviderIdentification providerId) {
        account.addProfile(this);
        // this.name = up.getName();
        this.firstName = up.getFirstName();
        this.lastName = up.getLastName();
        this.email = up.getEmail();
        this.username = up.getUsername();
        this.providerId = providerId.name();
        this.imageUrl = fixImageUrl(imageUrl);

        this.account = account;
        this.account.roles = "USER";
        this.password = up.getUsername(); // TODO improve!!!
        fixName();
    }

    public UserProfile(final Account account, final String email,
            final String username, final String password,
            ProviderIdentification providerIdentification) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.account = account;
        account.addProfile(this);
        this.account.roles = "USER";
        this.providerId = providerIdentification.name();
        this.imageUrl = WeFactorValues.DEFAULT_IMAGE_URL;

        this.fixName();
    }

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
        this.account.roles = "USER";

        this.fixName();
    }

    private String fixImageUrl(String imageUrl) {

        ProviderIdentification providerId = getProviderIdAsType();
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

    private void fixName() {
        // Is the name null?
        if (this.name == null) {

            if (ProviderIdentification.WEFACTOR.equals(this
                    .getProviderIdAsType())) {
                this.name = this.username;

            } else {
                this.name = this.email;
            }

        }
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "myAccount", nullable = false)
    public Account getAccount() {
        return this.account;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    @Column(name = "email", unique = true)
    public String getEmail() {
        return this.email;
    }

    public String getFirstName() {
        return this.firstName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return this.id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getName() {
        return this.name;
    }

    @Override
    @Column(name = "password")
    public String getPassword() {
        return super.getPassword();
    }

    @Column(name = "providerId")
    public String getProviderId() {
        return providerId;
    }

    @Transient
    public ProviderIdentification getProviderIdAsType() {
        return ProviderIdentification.valueOf(this.providerId);

    }

    @Transient
    public String getUserId() {
        return String.valueOf(this.id);
    }

    @Column(unique = true)
    public String getUsername() {
        return this.username;
    }

    public void setAccount(final Account account) {
        this.account = account;
        if (account != null) {
            account.addProfile(this);

        }
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public void setPassword(final String password) {
        super.setPassword(password);
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "name = " + this.name + ", firstName = " + this.firstName
                + ", lastName = " + this.lastName + ", email = " + this.email
                + ", username = " + this.username;
    }
}