package de.hhn.labswps.wefactor.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Tag")
@JsonIgnoreProperties({ "id", "entries", "createdBy", "lastModifiedBy" })
public class Tag extends BaseEntity {
    private Set<Entry> entries = new HashSet<Entry>();

    private String name;

    public Tag() {

    }

    public Tag(String name) {
        this.name = name;
    }

    @ManyToMany(mappedBy = "tags")
    public Set<Entry> getEntries() {
        return entries;
    }

    public String getName() {
        return name;
    }

    public void setEntries(Set<Entry> entries) {
        this.entries = entries;
    }

    public void setName(String name) {
        this.name = name;
    }

}
