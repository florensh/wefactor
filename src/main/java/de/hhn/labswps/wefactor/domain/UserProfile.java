package de.hhn.labswps.wefactor.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "userprofile")
public class UserProfile extends User {

    private String userId;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private String name;

    private String firstName;

    private String lastName;

    private String username;

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

    public UserProfile(String userId, String name, String firstName,
            String lastName, String email, String username) {
        this.userId = userId;
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;

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

            // Try with email if still null
            if (name == null) {
                name = email;
            }

            // Try with username if still null
            if (name == null) {
                name = username;
            }

            // If still null set name to UNKNOWN
            if (name == null) {
                name = "UNKNOWN";
            }
        }
    }

    public UserProfile(String userId,
            org.springframework.social.connect.UserProfile up) {
        this.userId = userId;
        this.name = up.getName();
        this.firstName = up.getFirstName();
        this.lastName = up.getLastName();
        this.email = up.getEmail();
        this.username = up.getUsername();
        this.roles = "USER";
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

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    @Override
    @Column(name = "roles")
    public String getRoles() {
        return super.getRoles();
    }

    @Override
    public void setRoles(String roles) {
        super.setRoles(roles);
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

    public String getUsername() {
        return username;
    }

    public String toString() {
        return "name = " + name + ", firstName = " + firstName
                + ", lastName = " + lastName + ", email = " + email
                + ", username = " + username;
    }
}