package de.hhn.labswps.wefactor.domain;

/**
 * The Class User.
 */
public abstract class User {

    /** The email. */
    protected String email;

    /** The password. */
    protected String password;

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
}