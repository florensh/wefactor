package de.hhn.labswps.wefactor.service;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import de.hhn.labswps.wefactor.domain.Account;
import de.hhn.labswps.wefactor.domain.AccountRepository;
import de.hhn.labswps.wefactor.domain.Entry;
import de.hhn.labswps.wefactor.domain.Group;
import de.hhn.labswps.wefactor.domain.MasterEntry;
import de.hhn.labswps.wefactor.domain.MasterEntryRepository;
import de.hhn.labswps.wefactor.domain.ProposalEntry;
import de.hhn.labswps.wefactor.domain.ProposalEntryRepository;
import de.hhn.labswps.wefactor.domain.UserProfile;
import de.hhn.labswps.wefactor.domain.UserProfileRepository;
import de.hhn.labswps.wefactor.domain.VersionEntry;
import de.hhn.labswps.wefactor.domain.VersionEntryRepository;

@Service
public class EntryService {

    @Autowired
    private MasterEntryRepository masterEntryRepository;

    @Autowired
    private ProposalEntryRepository proposalEntryRepository;

    @Autowired
    private VersionEntryRepository versionEntryRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    public Page<Entry> getEntryList(Account account, Pageable pageable) {

        final Page<Entry> list = this.masterEntryRepository
                .findDistinctByGroupIsNullOrGroupMembersOrderByEntryDateDesc(
                        account, pageable);
        return list;
    }

    public Page<Entry> getEntryListByUser(Long id, Pageable pageable) {
        final Page<Entry> list = this.masterEntryRepository.findByAccountId(id,
                pageable);
        return list;
    }

    public Page<Entry> getProposalListByUser(Long id, Pageable pageable) {
        final Page<Entry> list = this.masterEntryRepository
                .findByProposalsAccountIdOrderByProposalsCreatedDateDesc(id,
                        pageable);
        return list;
    }

    public Page<Entry> getVersionListByUser(Long id, Pageable pageable) {
        final Page<Entry> list = this.masterEntryRepository
                .findByVersionsAccountIdOrderByVersionsCreatedDateDesc(id,
                        pageable);
        return list;
    }

    public Page<Entry> getEntryListByTag(String tag, Pageable pageable) {
        final Page<Entry> list = this.masterEntryRepository
                .findDistinctByTagsNameOrVersionsTagsName(tag, tag, pageable);
        return list;
    }

    public Page<Entry> search(String searchtext, Account account,
            Pageable pageable) {
        return this.masterEntryRepository.search(searchtext, account, pageable);
    }

    public Page<Entry> getEntryListByGroup(Group group, Pageable pageable) {
        return this.masterEntryRepository.findByGroup(group, pageable);
    }

    public Entry addWatcher(Long id, String type, Principal currentUser) {
        Entry entry = getEntry(id, type);
        UserProfile userProfile = this.userProfileRepository
                .findByUsername(currentUser.getName());
        Account account = userProfile.getAccount();

        entry.addWatcher(account);

        saveEntry(entry);
        this.accountRepository.save(account);

        return entry;
    }

    public Entry removeWatcher(Long id, String type, Principal currentUser) {
        Entry entry = getEntry(id, type);
        UserProfile userProfile = this.userProfileRepository
                .findByUsername(currentUser.getName());
        Account account = userProfile.getAccount();

        entry.removeWatcher(account);
        saveEntry(entry);
        this.accountRepository.save(account);

        return entry;
    }

    private void saveEntry(Entry entry) {
        if (entry instanceof MasterEntry) {
            this.masterEntryRepository.save((MasterEntry) entry);
        } else if (entry instanceof VersionEntry) {
            this.versionEntryRepository.save((VersionEntry) entry);
        } else if (entry instanceof ProposalEntry) {
            this.proposalEntryRepository.save((ProposalEntry) entry);
        } else {
            throw new UnsupportedOperationException();
        }

    }

    public Entry getEntry(long id, String type) {
        Entry entry = null;

        if (MasterEntry.class.getSimpleName().equals(type)) {
            final MasterEntry me = this.masterEntryRepository.findOne(id);
            entry = me;

        } else if (VersionEntry.class.getSimpleName().equals(type)) {
            final VersionEntry ve = this.versionEntryRepository.findOne(id);
            entry = ve;

        }

        return entry;
    }

}
