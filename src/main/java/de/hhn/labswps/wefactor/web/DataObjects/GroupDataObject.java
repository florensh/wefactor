package de.hhn.labswps.wefactor.web.DataObjects;

import javax.validation.constraints.Size;

/**
 * The Class GroupDataObject.
 */
public class GroupDataObject {

    /** The name. */
    @Size(min = 3, max = 50)
    private String name;

    /** The description. */
    @Size(min = 10, max = 300)
    private String description;

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
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
