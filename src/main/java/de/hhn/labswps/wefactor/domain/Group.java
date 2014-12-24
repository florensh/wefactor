package de.hhn.labswps.wefactor.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "t_group")
@JsonIgnoreProperties({ "id", "createdBy", "lastModifiedBy",
        "hibernateLazyInitializer", "handler", "members" })
public class Group extends BaseEntity {

    private String description;

    private Set<Entry> entries = new HashSet<Entry>();

    private String imageUrl;

    private Set<Account> members = new HashSet<Account>();

    private String name;

    public String getDescription() {
        return description;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "group")
    public Set<Entry> getEntries() {
        return entries;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @ManyToMany(mappedBy = "groups")
    public Set<Account> getMembers() {
        return members;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEntries(Set<Entry> entries) {
        this.entries = entries;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setMembers(Set<Account> members) {
        this.members = members;
    }

    public void setName(String name) {
        this.name = name;
    }

}
