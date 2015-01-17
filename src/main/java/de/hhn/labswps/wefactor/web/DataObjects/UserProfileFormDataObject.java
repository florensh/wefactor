package de.hhn.labswps.wefactor.web.DataObjects;

import javax.validation.constraints.Size;

/**
 * The Class UserProfileFormDataObject.
 */
public class UserProfileFormDataObject {

    /** The display name. */
    @Size(min = 3, max = 30)
    private String displayName;

    /** The first name. */
    @Size(max = 30)
    private String firstName;

    /** The last name. */
    @Size(max = 30)
    private String lastName;

    /** The description. */
    @Size(max = 300)
    private String description;

    /**
     * Gets the first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return this.firstName;
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
     * Gets the display name.
     *
     * @return the display name
     */
    public String getDisplayName() {
        return this.displayName;
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
     * Gets the last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return this.lastName;
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
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return this.description;
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

}
