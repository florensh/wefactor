package de.hhn.labswps.wefactor.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import de.hhn.labswps.wefactor.specification.WeFactorValues.Role;

/**
 * The class account is the root of the class hierarchy which represents an
 * user.
 * An account can have several user profiles with different user names and
 * passwords.
 * But all data related to the user for example groups, entries,... are
 * connected to the class account.
 */
@Entity
@Table(name = "account")
@JsonIgnoreProperties({ "id", "entries", "createdBy", "lastModifiedBy",
        "hibernateLazyInitializer", "handler", "groups" })
public class Account {

    /** The entries. */
    private Set<Entry> entries = new HashSet<Entry>();

    /** The groups. */
    private Set<Group> groups = new HashSet<Group>();

    /** The id. */
    private Long id;

    /** The profiles. */
    private Set<UserProfile> profiles = new HashSet<UserProfile>();

    /** The roles. */
    protected String roles;

    /**
     * Instantiates a new account.
     */
    public Account() {

    }

    /**
     * Instantiates a new account.
     *
     * @param role
     *            the role
     */
    public Account(final Role role) {
        this.addRole(role);
    }

    /**
     * Adds the entry.
     *
     * @param entry
     *            the entry
     */
    public void addEntry(final Entry entry) {
        this.entries.add(entry);
    }

    /**
     * Adds the group.
     *
     * @param group
     *            the group
     */
    public void addGroup(final Group group) {
        this.groups.add(group);
    }

    /**
     * Adds the profile.
     *
     * @param profile
     *            the profile
     */
    public void addProfile(final UserProfile profile) {
        this.profiles.add(profile);
    }

    /**
     * Adds the role.
     *
     * @param role
     *            the role
     */
    public void addRole(final Role role) {
        if ((this.roles == null) || this.roles.isEmpty()) {
            this.roles = role.name();
        } else {
            final String[] rolesAsArray = this.roles.split(",");
            if (!Arrays.asList(rolesAsArray).contains(role.name())) {
                this.roles = this.roles + ", " + role.name();
            }
        }
    }

    /**
     * Gets the authorities.
     *
     * @return the authorities
     */
    @Transient
    public Collection<GrantedAuthority> getAuthorities() {

        final Collection<GrantedAuthority> authorities = new ArrayList<>();

        if (this.roles != null) {
            for (final String role : this.roles.split(",")) {
                final SimpleGrantedAuthority authority = new SimpleGrantedAuthority(
                        role);
                authorities.add(authority);
            }

        }
        return authorities;
    }

    /**
     * Gets the entries.
     *
     * @return the entries
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    public Set<Entry> getEntries() {
        return this.entries;
    }

    /**
     * Gets the groups.
     *
     * @return the groups
     */
    @ManyToMany
    @JoinTable(name = "ACCOUNT_GROUP", joinColumns = { @JoinColumn(name = "ACCOUNT_ID", referencedColumnName = "ID") }, inverseJoinColumns = { @JoinColumn(name = "GROUP_ID", referencedColumnName = "ID") })
    public Set<Group> getGroups() {
        return this.groups;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return this.id;
    }

    /**
     * Gets the primary profile.
     *
     * @return the primary profile
     */
    @Transient
    public UserProfile getPrimaryProfile() {
        return this.profiles.iterator().next();
    }

    /**
     * Gets the profiles.
     *
     * @return the profiles
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    public Set<UserProfile> getProfiles() {
        return this.profiles;
    }

    /**
     * Gets the roles.
     *
     * @return the roles
     */
    @Column(name = "roles")
    public String getRoles() {
        return this.roles;
    }

    /**
     * Removes the entry.
     *
     * @param entry
     *            the entry
     */
    public void removeEntry(final Entry entry) {
        this.entries.remove(entry);
    }

    /**
     * Removes the group.
     *
     * @param g
     *            the g
     */
    public void removeGroup(final Group g) {
        this.groups.remove(g);
    }

    /**
     * Removes the profile.
     *
     * @param profile
     *            the profile
     */
    public void removeProfile(final UserProfile profile) {
        this.profiles.remove(profile);
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
     * Sets the groups.
     *
     * @param groups
     *            the new groups
     */
    public void setGroups(final Set<Group> groups) {
        this.groups = groups;
    }

    /**
     * Sets the id.
     *
     * @param id
     *            the new id
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Sets the profiles.
     *
     * @param profiles
     *            the new profiles
     */
    public void setProfiles(final Set<UserProfile> profiles) {
        this.profiles = profiles;
    }

    /**
     * Sets the roles.
     *
     * @param roles
     *            the new roles
     */
    public void setRoles(final String roles) {
        this.roles = roles;
    }

}
