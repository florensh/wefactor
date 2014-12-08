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

@Entity
@Table(name = "account")
@JsonIgnoreProperties({ "id", "entries", "createdBy", "lastModifiedBy" })
public class Account {

    private Set<UserProfile> profiles = new HashSet<UserProfile>();

    private Set<Entry> entries = new HashSet<Entry>();

    private Long id;

    protected String roles;

    public Account() {

    }

    public Account(final Role role) {
        this.addRole(role);
    }

    public void addEntry(final Entry entry) {
        this.entries.add(entry);
    }

    public void addProfile(final UserProfile profile) {
        this.profiles.add(profile);
    }

    public void addRole(final Role role) {
        if (this.roles == null || this.roles.isEmpty()) {
            this.roles = role.name();
        } else {
            final String[] rolesAsArray = this.roles.split(",");
            if (!Arrays.asList(rolesAsArray).contains(role.name())) {
                this.roles = this.roles + ", " + role.name();
            }
        }
    }

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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    public Set<Entry> getEntries() {
        return this.entries;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return this.id;
    }

    @Transient
    public UserProfile getPrimaryProfile() {
        return this.profiles.iterator().next();
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    public Set<UserProfile> getProfiles() {
        return this.profiles;
    }

    @Column(name = "roles")
    public String getRoles() {
        return this.roles;
    }

    public void removeEntry(final Entry entry) {
        this.entries.remove(entry);
    }

    public void removeProfile(final UserProfile profile) {
        this.profiles.remove(profile);
    }

    public void setEntries(final Set<Entry> entries) {
        this.entries = entries;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setProfiles(final Set<UserProfile> profiles) {
        this.profiles = profiles;
    }

    public void setRoles(final String roles) {
        this.roles = roles;
    }

}
