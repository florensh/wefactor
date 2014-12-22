package de.hhn.labswps.wefactor.web.DataObjects;

import java.util.Set;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class EntryDataObject {

    @Size(min = 10, max = 200)
    private String changes;

    @NotEmpty
    private String code;

    @NotEmpty
    private String description;

    private String editMode;

    private Long id;

    @NotEmpty
    private String language;

    private String tagString;

    @Size(min = 10, max = 200)
    private String teaser;

    @NotEmpty
    @Size(min = 5, max = 50)
    private String title;

    public String getChanges() {
        return changes;
    }

    public String getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }

    public String getEditMode() {
        return editMode;
    }

    public Long getId() {
        return this.id;
    }

    public String getLanguage() {
        return language;
    }

    public String[] getTags() {
        if (this.tagString != null && !this.tagString.isEmpty()) {
            String[] tags_a = tagString.split("]");
            String[] tags = new String[tags_a.length];
            for (int i = 0; i < tags_a.length; i++) {
                tags[i] = tags_a[i].substring(1);
            }

            return tags;
        } else {
            return new String[0];
        }
    }

    public String getTagString() {
        return this.tagString;
    }

    public String getTeaser() {
        return teaser;
    }

    public String getTitle() {
        return this.title;
    }

    public void setChanges(String changes) {
        this.changes = changes;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setEditMode(String editMode) {
        this.editMode = editMode;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setTags(Set<String> tags) {
        this.tagString = "";
        for (String s : tags) {
            this.tagString = this.tagString + "[" + s + "]";
        }
    }

    public void setTagString(String tagString) {
        this.tagString = tagString;
    }

    public void setTeaser(String teaser) {
        this.teaser = teaser;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

}
