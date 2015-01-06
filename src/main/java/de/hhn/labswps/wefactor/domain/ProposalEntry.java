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

/**
 * The Class ProposalEntry.
 */
@Entity
@DiscriminatorValue(value = "Proposal")
@Where(clause = "inactive = 'N'")
// @SQLDelete(sql = "UPDATE entry set inactive = 'Y' WHERE Id = ?")
@JsonIgnoreProperties({ "id", "parent", "softDeleted", "createdBy",
    "lastModifiedBy", "orderedVersions", "orderedVersionIds",
    "orderedVersionTypes", "masterOfProposal", "headVersion", "group" })
public class ProposalEntry extends Entry {

    /**
     * The Enum Status.
     */
    public enum Status {

        //@formatter:off
        NEW("new"),
        ACCEPTED("accepted"),
        REJECTED("rejected");
        //@formatter:on

        /** The displayname. */
        String displayname;

        /**
         * Instantiates a new status.
         *
         * @param displayName
         *            the display name
         */
        private Status(final String displayName) {
            this.displayname = displayName;
        }

        /**
         * Gets the display name.
         *
         * @return the display name
         */
        public String getDisplayName() {
            return this.displayname;
        }

    }

    /** The status. */
    public String status;

    /**
     * Gets the status.
     *
     * @return the status
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * Sets the status.
     *
     * @param status
     *            the new status
     */
    public void setStatus(final String status) {
        this.status = status;
    }

    /**
     * Gets the status as type.
     *
     * @return the status as type
     */
    @Transient
    public Status getStatusAsType() {
        return Status.valueOf(this.status);
    }

    /** The master of proposal. */
    private MasterEntry masterOfProposal;

    /**
     * Gets the master of proposal.
     *
     * @return the master of proposal
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "masterOfProposal")
    public MasterEntry getMasterOfProposal() {
        return this.masterOfProposal;
    }

    /**
     * Sets the master of proposal.
     *
     * @param masterEntry
     *            the new master of proposal
     */
    public void setMasterOfProposal(final MasterEntry masterEntry) {
        this.masterOfProposal = masterEntry;
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
        retVal.add(this.masterOfProposal);
        Collections.sort(retVal);
        return retVal;
    }

    /*
     * (non-Javadoc)
     * @see de.hhn.labswps.wefactor.domain.Entry#getParent()
     */
    @Override
    @Transient
    public Entry getParent() {
        return this.masterOfProposal;
    }

    /*
     * (non-Javadoc)
     * @see de.hhn.labswps.wefactor.domain.Entry#getVersionDisplayText()
     */
    @Override
    @Transient
    @JsonProperty("versionDisplayText")
    public String getVersionDisplayText() {
        return this.getChanges();
    }

}
