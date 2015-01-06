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
 * The Class VersionEntry.
 */
@Entity
@DiscriminatorValue(value = "Version")
@Where(clause = "inactive = 'N'")
// @SQLDelete(sql = "UPDATE entry set inactive = 'Y' WHERE Id = ?")
@JsonIgnoreProperties({ "id", "parent", "softDeleted", "createdBy",
    "lastModifiedBy", "orderedVersions", "orderedVersionIds",
    "orderedVersionTypes", "masterOfVersion", "ratings", "headVersion",
"group" })
public class VersionEntry extends Entry {

    /**
     * Instantiates a new version entry.
     */
    public VersionEntry() {

    }

    /** The master of version. */
    private MasterEntry masterOfVersion;

    /**
     * Instantiates a new version entry.
     *
     * @param pe
     *            the pe
     */
    public VersionEntry(final ProposalEntry pe) {

        this.masterOfVersion = pe.getMasterOfProposal();
        this.setAccount(pe.getAccount());
        this.setChanges(pe.getChanges());
        this.setEntryCodeText(pe.getEntryCodeText());
        this.setEntryDate(pe.getEntryDate());
        this.setEntryDescription(pe.getEntryDescription());
        this.setLanguage(pe.getLanguage());
        this.setName(pe.getName());
        this.setTeaser(pe.getTeaser());

        if (!pe.getTags().isEmpty()) {
            for (final Tag tag : pe.getTags()) {
                this.addTag(tag);
            }
        }
    }

    /**
     * Gets the master of version.
     *
     * @return the master of version
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "masterOfVersion")
    public MasterEntry getMasterOfVersion() {
        return this.masterOfVersion;
    }

    /**
     * Sets the master of version.
     *
     * @param masterEntry
     *            the new master of version
     */
    public void setMasterOfVersion(final MasterEntry masterEntry) {
        this.masterOfVersion = masterEntry;
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
        retVal.add(this.masterOfVersion);
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
        return this.masterOfVersion;
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
