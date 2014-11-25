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
        "lastModifiedBy", "masterEntry" })
public class VersionEntry extends Entry {

    private Entry masterEntry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "masterEntry")
    public Entry getMasterEntry() {
        return masterEntry;
    }

    public void setMasterEntry(Entry masterEntry) {
        this.masterEntry = masterEntry;
    }

}
