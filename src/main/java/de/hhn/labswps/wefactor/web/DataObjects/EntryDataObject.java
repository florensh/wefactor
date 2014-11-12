package de.hhn.labswps.wefactor.web.DataObjects;

import org.hibernate.validator.constraints.NotEmpty;

public class EntryDataObject {

    @NotEmpty
    private String description;

    @NotEmpty
    // @Max(50)
    private String title;

    private Long id;

    @NotEmpty
    private String code;

    public String getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }

    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

}
