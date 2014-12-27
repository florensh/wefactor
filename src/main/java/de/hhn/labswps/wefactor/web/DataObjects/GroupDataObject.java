package de.hhn.labswps.wefactor.web.DataObjects;

import javax.validation.constraints.Size;

public class GroupDataObject {

    @Size(min = 3, max = 50)
    private String name;

    @Size(min = 10, max = 300)
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
