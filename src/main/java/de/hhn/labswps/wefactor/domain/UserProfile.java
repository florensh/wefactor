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

@Entity
@Table(name = "userprofile")
public class UserProfile extends User implements Serializable {

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

    private Long id;

    public UserProfile() {

    }

    public UserProfile(final Account account,
            final org.springframework.social.connect.UserProfile up) {
        this.name = up.getName();
        this.firstName = up.getFirstName();
        this.lastName = up.getLastName();
        this.email = up.getEmail();
        this.username = up.getUsername();
        this.account = account;
        account.addProfile(this);
        this.account.roles = "USER";
        this.password = up.getUsername(); // TODO improve!!!
    }

    public UserProfile(final Account account, final String email,
            final String username, final String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.account = account;
        account.addProfile(this);
        this.account.roles = "USER";

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

    private void fixName() {
        // Is the name null?
        if (this.name == null) {

            // Ok, lets try with first and last name...
            this.name = this.firstName;

            if (this.lastName != null) {
                if (this.name == null) {
                    this.name = this.lastName;
                } else {
                    this.name += " " + this.lastName;
                }
            }

            // Try with username if still null
            if (this.name == null) {
                this.name = this.username;
            }

            // Try with email if still null
            if (this.name == null) {
                this.name = this.email;
            }

            // If still null set name to UNKNOWN
            if (this.name == null) {
                this.name = "UNKNOWN";
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