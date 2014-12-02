package de.hhn.labswps.wefactor.web.DataObjects;

import javax.validation.constraints.Size;

public class UserProfileFormDataObject {

    @Size(min = 3, max = 30)
    private String displayName;

    @Size(max = 30)
    private String firstName;

    @Size(max = 30)
    private String lastName;

    @Size(max = 300)
    private String description;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
