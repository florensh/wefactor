package de.hhn.labswps.wefactor.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@DiscriminatorValue(value = "Master")
@Where(clause = "inactive = 'N'")
// @SQLDelete(sql = "UPDATE entry set inactive = 'Y' WHERE Id = ?")
@JsonIgnoreProperties({ "id", "softDeleted", "account", "createdBy",
        "lastModifiedBy", "orderedVersions", "versions", "proposals" })
public class MasterEntry extends Entry {

    private Set<VersionEntry> versions = new HashSet<VersionEntry>();

    public void addVersion(VersionEntry version) {
        version.setMasterOfVersion(this);
        this.versions.add(version);
    }

    @OneToMany(mappedBy = "masterOfVersion")
    public Set<VersionEntry> getVersions() {
        return versions;
    }

    public void removeVersion(VersionEntry version) {
        this.versions.remove(version);
    }

    public void setVersions(Set<VersionEntry> versions) {
        this.versions = versions;
    }

    @Transient
    public List<Entry> getOrderedVersions() {
        List<Entry> retVal = new ArrayList<Entry>();
        retVal.add(this);
        if (this.versions != null && !this.versions.isEmpty()) {
            retVal.addAll(this.versions);
        }
        return retVal;
    }

    private Set<ProposalEntry> proposals = new HashSet<ProposalEntry>();

    public void addProposal(ProposalEntry proposal) {
        proposal.setMasterOfProposal(this);
        this.proposals.add(proposal);
    }

    @OneToMany(mappedBy = "masterOfProposal")
    public Set<ProposalEntry> getProposals() {
        return proposals;
    }

    public void removeProposal(ProposalEntry proposal) {
        this.proposals.remove(proposal);
    }

    public void setProposals(Set<ProposalEntry> proposals) {
        this.proposals = proposals;
    }
}
