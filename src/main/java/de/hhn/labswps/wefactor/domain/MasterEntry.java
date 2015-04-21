package de.hhn.labswps.wefactor.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A MasterEntry is a special kind of entry and represents the root. It is
 * mainly the first Version of an entry.
 */
@Entity
@DiscriminatorValue(value = "Master")
@Where(clause = "inactive = 'N'")
// @SQLDelete(sql = "UPDATE entry set inactive = 'Y' WHERE Id = ?")
@JsonIgnoreProperties({ "id", "parent", "softDeleted", "createdBy",
        "lastModifiedBy", "orderedVersions", "orderedVersionIds",
        "orderedVersionTypes", "versions", "proposals", "sortedProposals",
        "ratings", "hibernateLazyInitializer", "handler", "headVersion",
        "group" })
public class MasterEntry extends Entry {

    /** The versions. */
    private Set<VersionEntry> versions = new HashSet<VersionEntry>();

    /**
     * Adds the version.
     *
     * @param version
     *            the version
     */
    public void addVersion(final VersionEntry version) {
        version.setMasterOfVersion(this);
        this.versions.add(version);
    }

    /**
     * Gets the versions.
     *
     * @return the versions
     */
    @OneToMany(mappedBy = "masterOfVersion")
    public Set<VersionEntry> getVersions() {
        return this.versions;
    }

    /**
     * Removes the version.
     *
     * @param version
     *            the version
     */
    public void removeVersion(final VersionEntry version) {
        this.versions.remove(version);
    }

    /**
     * Sets the versions.
     *
     * @param versions
     *            the new versions
     */
    public void setVersions(final Set<VersionEntry> versions) {
        this.versions = versions;
    }

    /*
     * (non-Javadoc)
     * @see de.hhn.labswps.wefactor.domain.Entry#getOrderedVersions()
     */
    @Override
    @Transient
    public List<Entry> getOrderedVersions() {
        final List<Entry> retVal = new ArrayList<Entry>();
        retVal.add(this);
        if ((this.versions != null) && !this.versions.isEmpty()) {
            retVal.addAll(this.versions);
        }

        Collections.sort(retVal);

        return retVal;
    }

    /** The proposals. */
    private Set<ProposalEntry> proposals = new HashSet<ProposalEntry>();

    /**
     * Adds the proposal.
     *
     * @param proposal
     *            the proposal
     */
    public void addProposal(final ProposalEntry proposal) {
        proposal.setMasterOfProposal(this);
        this.proposals.add(proposal);
    }

    /**
     * Gets the proposals.
     *
     * @return the proposals
     */
    @OneToMany(mappedBy = "masterOfProposal")
    public Set<ProposalEntry> getProposals() {
        return this.proposals;
    }

    @Transient
    public List<ProposalEntry> getSortedProposals() {
        List<ProposalEntry> list = new ArrayList<ProposalEntry>(this.proposals);
        Collections.sort(list);
        return list;
    }

    /**
     * Removes the proposal.
     *
     * @param proposal
     *            the proposal
     */
    public void removeProposal(final ProposalEntry proposal) {
        this.proposals.remove(proposal);
    }

    /**
     * Sets the proposals.
     *
     * @param proposals
     *            the new proposals
     */
    public void setProposals(final Set<ProposalEntry> proposals) {
        this.proposals = proposals;
    }

    /**
     * Gets the amount of proposals by type.
     *
     * @param status
     *            the status
     * @return the amount of proposals by type
     */
    public int getAmountOfProposalsByType(final String status) {
        return (int) this.getProposals().stream()
                .filter(x -> status.equals(x.getStatus())).count();

    }

    /*
     * (non-Javadoc)
     * @see de.hhn.labswps.wefactor.domain.Entry#getParent()
     */
    @Override
    @Transient
    public final Entry getParent() {
        // MasterEntry is the root
        return this;
    }

    /*
     * (non-Javadoc)
     * @see de.hhn.labswps.wefactor.domain.Entry#getVersionDisplayText()
     */
    @Override
    @Transient
    @JsonProperty("versionDisplayText")
    public String getVersionDisplayText() {
        return this.getTeaser();
    }

}
