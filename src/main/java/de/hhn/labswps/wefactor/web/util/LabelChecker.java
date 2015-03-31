package de.hhn.labswps.wefactor.web.util;

import java.util.ArrayList;
import java.util.List;

import de.hhn.labswps.wefactor.domain.Account;
import de.hhn.labswps.wefactor.domain.Entry;
import de.hhn.labswps.wefactor.domain.MasterEntry;
import de.hhn.labswps.wefactor.domain.ProposalEntry;
import de.hhn.labswps.wefactor.domain.ProposalEntry.Status;
import de.hhn.labswps.wefactor.domain.VersionEntry;

public class LabelChecker {

    private static final String PROPOSAL_MADE_ICON = "fa-folder";
    private static final String VERSION_MADE_ICON = "fa-check";
    private static final String PROPSAL_REJECTED_ICON = "fa-times";

    public boolean isNew(Entry entry) {
        return entry.getCreatedDate().getTime() - System.currentTimeMillis() < 1000
                * 60 * 60 * 24 * 3;
    }

    public List<String> resolveIcons(Entry entry, Account currentUser) {
        List<String> retVal = new ArrayList<String>();

        if (versionMade(entry, currentUser)) {
            retVal.add(VERSION_MADE_ICON);
        } else if (proposalRejected(entry, currentUser)) {
            retVal.add(PROPSAL_REJECTED_ICON);
        }

        else if (proposalMade(entry, currentUser)) {
            retVal.add(PROPOSAL_MADE_ICON);
        }

        return retVal;
    }

    private boolean proposalMade(Entry entry, Account currentUser) {
        if (entry instanceof MasterEntry) {
            MasterEntry me = (MasterEntry) entry;
            if (!me.getProposals().isEmpty()) {
                for (ProposalEntry p : me.getProposals()) {
                    if (p.getStatusAsType() == Status.NEW
                            && p.getAccount().equals(currentUser)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean proposalRejected(Entry entry, Account currentUser) {
        if (entry instanceof MasterEntry) {
            MasterEntry me = (MasterEntry) entry;
            if (!me.getProposals().isEmpty()) {
                for (ProposalEntry p : me.getProposals()) {
                    if (p.getStatusAsType() == Status.REJECTED
                            && p.getAccount().equals(currentUser)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean versionMade(Entry entry, Account currentUser) {
        if (entry instanceof MasterEntry) {
            MasterEntry me = (MasterEntry) entry;
            if (!me.getVersions().isEmpty()) {
                for (VersionEntry v : me.getVersions()) {
                    if (v.getAccount().equals(currentUser)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
