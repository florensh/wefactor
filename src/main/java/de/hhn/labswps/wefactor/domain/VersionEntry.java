package de.hhn.labswps.wefactor.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@DiscriminatorValue(value = "Version")
@Where(clause = "inactive = 'N'")
// @SQLDelete(sql = "UPDATE entry set inactive = 'Y' WHERE Id = ?")
@JsonIgnoreProperties({ "id", "softDeleted", "account", "createdBy",
        "lastModifiedBy", "masterOfVersion" })
public class VersionEntry extends Entry {

    private MasterEntry masterOfVersion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "masterOfVersion")
    public MasterEntry getMasterOfVersion() {
        return masterOfVersion;
    }

    public void setMasterOfVersion(MasterEntry masterEntry) {
        this.masterOfVersion = masterEntry;
    }

}
