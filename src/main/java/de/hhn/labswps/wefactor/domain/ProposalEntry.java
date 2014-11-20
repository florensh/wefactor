package de.hhn.labswps.wefactor.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@DiscriminatorValue(value = "Proposal")
@Where(clause = "inactive = 'N'")
// @SQLDelete(sql = "UPDATE entry set inactive = 'Y' WHERE Id = ?")
@JsonIgnoreProperties({ "id", "softDeleted" })
public class ProposalEntry extends Entry {

    private MasterEntry masterEntry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "masterEntry")
    public MasterEntry getMasterEntry() {
        return masterEntry;
    }

    public void setMasterEntry(MasterEntry masterEntry) {
        this.masterEntry = masterEntry;
    }

}
