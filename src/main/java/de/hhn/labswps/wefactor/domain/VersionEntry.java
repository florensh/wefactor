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

@Entity
@DiscriminatorValue(value = "Version")
@Where(clause = "inactive = 'N'")
// @SQLDelete(sql = "UPDATE entry set inactive = 'Y' WHERE Id = ?")
@JsonIgnoreProperties({ "id", "softDeleted", "account", "createdBy",
        "lastModifiedBy", "orderedVersions", "orderedVersionIds",
        "orderedVersionTypes", "masterOfVersion", "ratings" })
public class VersionEntry extends Entry {

    public VersionEntry() {

    }

    private MasterEntry masterOfVersion;

    public VersionEntry(ProposalEntry pe) {

        this.masterOfVersion = pe.getMasterOfProposal();
        this.setAccount(pe.getAccount());
        this.setChanges(pe.getChanges());
        this.setEntryCodeText(pe.getEntryCodeText());
        this.setEntryDate(pe.getEntryDate());
        this.setEntryDescription(pe.getEntryDescription());
        this.setLanguage(pe.getLanguage());
        this.setName(pe.getName());
        this.setTeaser(pe.getTeaser());
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "masterOfVersion")
    public MasterEntry getMasterOfVersion() {
        return masterOfVersion;
    }

    public void setMasterOfVersion(MasterEntry masterEntry) {
        this.masterOfVersion = masterEntry;
    }

    @Override
    @Transient
    public List<Entry> getOrderedVersions() {
        List<Entry> retVal = new ArrayList<Entry>();
        retVal.add(this);
        retVal.add(masterOfVersion);
        Collections.sort(retVal);
        return retVal;
    }

    @Override
    @Transient
    protected Entry getParent() {
        return masterOfVersion;
    }

}
