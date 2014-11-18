package de.hhn.labswps.wefactor.web.DataObjects;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class EntryDataObject {

    @NotEmpty
    private String description;

    @NotEmpty
    @Size(min = 5, max = 30)
    private String title;

    private Long id;

    @NotEmpty
    private String code;

    @NotEmpty
    private String language;

    @Size(min = 10, max = 200)
    private String teaser;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTeaser() {
        return teaser;
    }

    public void setTeaser(String teaser) {
        this.teaser = teaser;
    }

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
