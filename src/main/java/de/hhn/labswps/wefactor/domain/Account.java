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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import de.hhn.labswps.wefactor.specification.WeFactorValues.Role;

/**
 * The Account represents a user.
 */
@Entity
@Table(name = "account")
@JsonIgnoreProperties({ "id", "entries", "createdBy", "lastModifiedBy",
    "hibernateLazyInitializer", "handler" })
public final class Account {

    /** The set of entries a user created. */
    private Set<Entry> entries = new HashSet<Entry>();

    /** The unique identification of an account. */
    private Long id;

    /**
     * The set of profiles a user can have. This can also be profiles from
     * social providers like google
     */
    private Set<UserProfile> profiles = new HashSet<UserProfile>();

    /** The roles as comma separated String. */
    private String roles;

    /**
     * Default constructor. Necessary for JPA
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
     * Adds a profile to that account.
     *
     * @param profile
     *            the profile to add
     */
    public void addProfile(final UserProfile profile) {
        this.profiles.add(profile);
    }

    /**
     * Adds a role to the account.
     *
     * @param role
     *            the role to add
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

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final Account other = (Account) obj;
        if (this.id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    /**
     * Provides the authorities for spring security.
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

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result)
                + ((this.id == null) ? 0 : this.id.hashCode());
        return result;
    }

    /**
     * Sets the entries.
     *
     * @param theEntries
     *            the new entries
     */
    public void setEntries(final Set<Entry> theEntries) {
        this.entries = theEntries;
    }

    /**
     * Sets the id.
     *
     * @param theId
     *            the new id
     */
    public void setId(final Long theId) {
        this.id = theId;
    }

    /**
     * Sets the profiles.
     *
     * @param theProfiles
     *            the new profiles
     */
    public void setProfiles(final Set<UserProfile> theProfiles) {
        this.profiles = theProfiles;
    }

    /**
     * Sets the roles.
     *
     * @param theRoles
     *            the new roles
     */
    public void setRoles(final String theRoles) {
        this.roles = theRoles;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Account [id=" + this.id + ", roles=" + this.roles + "]";
    }

}
