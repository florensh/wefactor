package de.hhn.labswps.wefactor.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import de.hhn.labswps.wefactor.specification.WeFactorValues;

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

    public Group() {
        this.imageUrl = WeFactorValues.DEFAULT_GROUP_IMAGE_URL;
    }

    public Group(String name, String description) {
        this.name = name;
        this.description = description;
        this.imageUrl = WeFactorValues.DEFAULT_GROUP_IMAGE_URL;
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

    public void addMember(Account theAccount) {
        this.members.add(theAccount);
        theAccount.addGroup(this);
    }

    public void removeMember(Account theAccount) {
        this.members.remove(theAccount);
        theAccount.removeGroup(this);
    }

}
