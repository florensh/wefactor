package de.hhn.labswps.wefactor.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@DiscriminatorValue(value = "Master")
@Where(clause = "inactive = 'N'")
// @SQLDelete(sql = "UPDATE entry set inactive = 'Y' WHERE Id = ?")
@JsonIgnoreProperties({ "id", "softDeleted" })
public class MasterEntry extends Entry {

    private Set<VersionEntry> versions = new HashSet<VersionEntry>();

    public void addVersion(VersionEntry version) {
        version.setMasterEntry(this);
        this.versions.add(version);
    }

    @OneToMany(mappedBy = "masterEntry")
    public Set<VersionEntry> getVersions() {
        return versions;
    }

    public void removeVersion(VersionEntry version) {
        this.versions.remove(version);
    }

    public void setVersions(Set<VersionEntry> versions) {
        this.versions = versions;
    }

}
