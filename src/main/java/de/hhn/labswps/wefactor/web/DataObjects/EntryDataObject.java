package de.hhn.labswps.wefactor.web.DataObjects;

import java.util.Set;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * The Class EntryDataObject.
 */
public class EntryDataObject {

    /** The changes. */
    @Size(min = 10, max = 200)
    private String changes;

    /** The code. */
    @NotEmpty
    private String code;

    /** The description. */
    @NotEmpty
    private String description;

    /** The edit mode. */
    private String editMode;

    /** The id. */
    private Long id;

    /** The group. */
    private String group;

    /**
     * Gets the group.
     *
     * @return the group
     */
    public final String getGroup() {
        return this.group;
    }

    /**
     * Sets the group.
     *
     * @param group
     *            the new group
     */
    public final void setGroup(final String group) {
        this.group = group;
    }

    /** The language. */
    @NotEmpty
    private String language;

    /** The tag string. */
    private String tagString;

    /** The teaser. */
    @Size(min = 10, max = 200)
    private String teaser;

    /** The title. */
    @NotEmpty
    @Size(min = 5, max = 50)
    private String title;

    /**
     * Gets the changes.
     *
     * @return the changes
     */
    public final String getChanges() {
        return this.changes;
    }

    /**
     * Gets the code.
     *
     * @return the code
     */
    public final String getCode() {
        return this.code;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public final String getDescription() {
        return this.description;
    }

    /**
     * Gets the edits the mode.
     *
     * @return the edits the mode
     */
    public final String getEditMode() {
        return this.editMode;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public final Long getId() {
        return this.id;
    }

    /**
     * Gets the language.
     *
     * @return the language
     */
    public final String getLanguage() {
        return this.language;
    }

    /**
     * Gets the tags.
     *
     * @return the tags
     */
    public final String[] getTags() {
        if ((this.tagString != null) && !this.tagString.isEmpty()) {
            final String[] tags_a = this.tagString.split("]");
            final String[] tags = new String[tags_a.length];
            for (int i = 0; i < tags_a.length; i++) {
                tags[i] = tags_a[i].substring(1);
            }

            return tags;
        } else {
            return new String[0];
        }
    }

    /**
     * Gets the tag string.
     *
     * @return the tag string
     */
    public final String getTagString() {
        return this.tagString;
    }

    /**
     * Gets the teaser.
     *
     * @return the teaser
     */
    public final String getTeaser() {
        return this.teaser;
    }

    /**
     * Gets the title.
     *
     * @return the title
     */
    public final String getTitle() {
        return this.title;
    }

    /**
     * Sets the changes.
     *
     * @param changes
     *            the new changes
     */
    public final void setChanges(final String changes) {
        this.changes = changes;
    }

    /**
     * Sets the code.
     *
     * @param code
     *            the new code
     */
    public final void setCode(final String code) {
        this.code = code;
    }

    /**
     * Sets the description.
     *
     * @param description
     *            the new description
     */
    public final void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Sets the edits the mode.
     *
     * @param editMode
     *            the new edits the mode
     */
    public final void setEditMode(final String editMode) {
        this.editMode = editMode;
    }

    /**
     * Sets the id.
     *
     * @param id
     *            the new id
     */
    public final void setId(final Long id) {
        this.id = id;
    }

    /**
     * Sets the language.
     *
     * @param language
     *            the new language
     */
    public final void setLanguage(final String language) {
        this.language = language;
    }

    /**
     * Sets the tags.
     *
     * @param tags
     *            the new tags
     */
    public final void setTags(final Set<String> tags) {
        this.tagString = "";
        for (final String s : tags) {
            this.tagString = this.tagString + "[" + s + "]";
        }
    }

    /**
     * Sets the tag string.
     *
     * @param tagString
     *            the new tag string
     */
    public final void setTagString(final String tagString) {
        this.tagString = tagString;
    }

    /**
     * Sets the teaser.
     *
     * @param teaser
     *            the new teaser
     */
    public final void setTeaser(final String teaser) {
        this.teaser = teaser;
    }

    /**
     * Sets the title.
     *
     * @param title
     *            the new title
     */
    public final void setTitle(final String title) {
        this.title = title;
    }

}
