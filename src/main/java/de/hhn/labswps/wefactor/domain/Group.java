package de.hhn.labswps.wefactor.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import de.hhn.labswps.wefactor.specification.WeFactorValues;

/**
 * The Class Group represents a union of users to concentrate on a certain
 * topic.
 */
@Entity
@Table(name = "t_group")
@JsonIgnoreProperties({ "id", "createdBy", "lastModifiedBy",
        "hibernateLazyInitializer", "handler", "members" })
public class Group extends BaseEntity {

    /** The description. */
    private String description;

    /** The entries. */
    private Set<Entry> entries = new HashSet<Entry>();

    /** The image url. */
    private String imageUrl;

    /** The members. */
    private Set<Account> members = new HashSet<Account>();

    /** The name. */
    private String name;

    /**
     * Instantiates a new group.
     */
    public Group() {
        this.imageUrl = WeFactorValues.DEFAULT_GROUP_IMAGE_URL;
    }

    /**
     * Instantiates a new group.
     *
     * @param name
     *            the name
     * @param description
     *            the description
     */
    public Group(final String name, final String description) {
        this.name = name;
        this.description = description;
        this.imageUrl = WeFactorValues.DEFAULT_GROUP_IMAGE_URL;
    }

    /**
     * Adds the member.
     *
     * @param theAccount
     *            the the account
     */
    public void addMember(final Account theAccount) {
        this.members.add(theAccount);
        theAccount.addGroup(this);
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
     * Gets the entries.
     *
     * @return the entries
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "group")
    public Set<Entry> getEntries() {
        return this.entries;
    }

    /**
     * Gets the image url.
     *
     * @return the image url
     */
    public String getImageUrl() {
        return this.imageUrl;
    }

    /**
     * Gets the members.
     *
     * @return the members
     */
    @ManyToMany(mappedBy = "groups", fetch = FetchType.EAGER)
    public Set<Account> getMembers() {
        return this.members;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Removes the member.
     *
     * @param theAccount
     *            the the account
     */
    public void removeMember(final Account theAccount) {
        this.members.remove(theAccount);
        theAccount.removeGroup(this);
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

    /**
     * Sets the entries.
     *
     * @param entries
     *            the new entries
     */
    public void setEntries(final Set<Entry> entries) {
        this.entries = entries;
    }

    /**
     * Sets the image url.
     *
     * @param imageUrl
     *            the new image url
     */
    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * Sets the members.
     *
     * @param members
     *            the new members
     */
    public void setMembers(final Set<Account> members) {
        this.members = members;
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

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return this.name;
    }

}
