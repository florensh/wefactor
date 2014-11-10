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

@Entity
@Table(name = "userprofile")
public class UserProfile extends User implements Serializable {

    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "myAccount", nullable = false)
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
        if (account != null) {
            account.addProfile(this);

        }
    }

    private String userId;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private String name;

    private String firstName;

    private String lastName;

    private String username;

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserProfile() {

    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserProfile(Account account, String userId, String name,
            String firstName, String lastName, String email, String username) {
        this.userId = userId;
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.account = account;
        account.addProfile(this);
        this.account.roles = "USER";

        fixName();
    }

    public UserProfile(Account account, String userId, String email,
            String username, String password) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.password = password;
        this.account = account;
        account.addProfile(this);
        this.account.roles = "USER";

        fixName();
    }

    private void fixName() {
        // Is the name null?
        if (name == null) {

            // Ok, lets try with first and last name...
            name = firstName;

            if (lastName != null) {
                if (name == null) {
                    name = lastName;
                } else {
                    name += " " + lastName;
                }
            }

            // Try with username if still null
            if (name == null) {
                name = username;
            }

            // Try with email if still null
            if (name == null) {
                name = email;
            }

            // If still null set name to UNKNOWN
            if (name == null) {
                name = "UNKNOWN";
            }
        }
    }

    public UserProfile(Account account, String userId,
            org.springframework.social.connect.UserProfile up) {
        this.userId = userId;
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

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Column(name = "email", unique = true)
    public String getEmail() {
        return email;
    }

    @Override
    @Column(name = "password")
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
    }

    @Column(unique = true)
    public String getUsername() {
        return username;
    }

    public String toString() {
        return "name = " + name + ", firstName = " + firstName
                + ", lastName = " + lastName + ", email = " + email
                + ", username = " + username;
    }
}