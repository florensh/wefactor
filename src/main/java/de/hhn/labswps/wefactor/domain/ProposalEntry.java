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
@JsonIgnoreProperties({ "id", "softDeleted", "account", "createdBy",
        "lastModifiedBy", "masterOfProposal" })
public class ProposalEntry extends Entry {

    private MasterEntry masterOfProposal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "masterOfProposal")
    public MasterEntry getMasterOfProposal() {
        return masterOfProposal;
    }

    public void setMasterOfProposal(MasterEntry masterEntry) {
        this.masterOfProposal = masterEntry;
    }

}
