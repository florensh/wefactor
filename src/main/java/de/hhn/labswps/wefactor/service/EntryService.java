package de.hhn.labswps.wefactor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import de.hhn.labswps.wefactor.domain.Account;
import de.hhn.labswps.wefactor.domain.Entry;
import de.hhn.labswps.wefactor.domain.Group;
import de.hhn.labswps.wefactor.domain.MasterEntryRepository;

@Service
public class EntryService {

    @Autowired
    private MasterEntryRepository masterEntryRepository;

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
        // TODO Auto-generated method stub
        return this.masterEntryRepository.findByGroup(group, pageable);
    }

}
