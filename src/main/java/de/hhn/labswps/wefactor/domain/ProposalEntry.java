package de.hhn.labswps.wefactor.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@DiscriminatorValue(value = "Proposal")
@Where(clause = "inactive = 'N'")
// @SQLDelete(sql = "UPDATE entry set inactive = 'Y' WHERE Id = ?")
@JsonIgnoreProperties({ "id", "parent", "softDeleted", "createdBy",
        "lastModifiedBy", "orderedVersions", "orderedVersionIds",
        "orderedVersionTypes", "masterOfProposal", "group" })
public class ProposalEntry extends Entry {

    public enum Status {

        //@formatter:off
        NEW("new"), 
        ACCEPTED("accepted"), 
        REJECTED("rejected");
        //@formatter:on

        String displayname;

        private Status(String displayName) {
            this.displayname = displayName;
        }

        public String getDisplayName() {
            return this.displayname;
        }

    }

    public String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Transient
    public Status getStatusAsType() {
        return Status.valueOf(this.status);
    }

    private MasterEntry masterOfProposal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "masterOfProposal")
    public MasterEntry getMasterOfProposal() {
        return masterOfProposal;
    }

    public void setMasterOfProposal(MasterEntry masterEntry) {
        this.masterOfProposal = masterEntry;
    }

    @Override
    @Transient
    public List<Entry> getOrderedVersions() {
        List<Entry> retVal = new ArrayList<Entry>();
        retVal.add(this);
        retVal.add(masterOfProposal);
        Collections.sort(retVal);
        return retVal;
    }

    @Override
    @Transient
    public Entry getParent() {
        return masterOfProposal;
    }

    @Override
    @Transient
    @JsonProperty("versionDisplayText")
    public String getVersionDisplayText() {
        return getChanges();
    }

}
