package de.hhn.labswps.wefactor.web.DataObjects;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import de.hhn.labswps.wefactor.web.validation.constraints.FieldMatch;
import de.hhn.labswps.wefactor.web.validation.constraints.UniqueEmail;
import de.hhn.labswps.wefactor.web.validation.constraints.UniqueUsername;

/**
 * The Class RegisterFormDataObject.
 */
@FieldMatch.List({ @FieldMatch(first = "password", second = "repeatPassword", message = "The password fields must match") })
public class RegisterFormDataObject {

    /** The username. */
    @Size(min = 3, max = 30)
    @UniqueUsername(message = "already exists")
    private String username;

    /** The email. */
    @Email
    @UniqueEmail(message = "already exists")
    @NotEmpty
    private String email;

    /** The password. */
    @Size(min = 8, max = 30)
    private String password;

    /** The repeat password. */
    private String repeatPassword;

    /**
     * Gets the username.
     *
     * @return the username
     */
    public String getUsername() {
        return this.username;
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

    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Sets the email.
     *
     * @param email
     *            the new email
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets the password.
     *
     * @param password
     *            the new password
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * Gets the repeat password.
     *
     * @return the repeat password
     */
    public String getRepeatPassword() {
        return this.repeatPassword;
    }

    /**
     * Sets the repeat password.
     *
     * @param repeatPassword
     *            the new repeat password
     */
    public void setRepeatPassword(final String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

}
