package de.hhn.labswps.wefactor.service;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import de.hhn.labswps.wefactor.domain.AccountRepository;
import de.hhn.labswps.wefactor.domain.EntryRatingRepository;
import de.hhn.labswps.wefactor.domain.GroupRepository;
import de.hhn.labswps.wefactor.domain.MasterEntryRepository;
import de.hhn.labswps.wefactor.domain.TagRepository;
import de.hhn.labswps.wefactor.domain.TimelineEventRepository;
import de.hhn.labswps.wefactor.domain.UserProfileRepository;
import de.hhn.labswps.wefactor.service.util.TestDataUtil;

/**
 * The Class TestDataService creates test data e.g. users, groups, entries.
 */
@Service
public class TestDataService {

    /** The Constant ACTIVE_ENVIRONMENT. */
    private static final String ACTIVE_ENVIRONMENT = "dev";

    /** The env. */
    @Autowired
    private Environment env;

    /** The group repository. */
    @Autowired
    private GroupRepository groupRepository;

    /** The timeline event repository. */
    @Autowired
    private TimelineEventRepository timelineEventRepository;

    /** The tag repository. */
    @Autowired
    private TagRepository tagRepository;

    /** The entry rating repository. */
    @Autowired
    private EntryRatingRepository entryRatingRepository;

    /** The master entry repository. */
    @Autowired
    private MasterEntryRepository masterEntryRepository;

    /**
     * Generate data.
     *
     * @throws Exception
     *             the exception
     */
    @PostConstruct
    public void generateData() throws Exception {
        if (Arrays.asList(env.getActiveProfiles()).contains(ACTIVE_ENVIRONMENT)) {
            TestDataUtil util = new TestDataUtil(this.accountRepository,
                    this.entryRatingRepository, this.groupRepository,
                    this.masterEntryRepository, this.tagRepository,
                    this.userProfileRepository, this.timelineEventRepository);

            util.generateData();

        }

    }

    /** The account repository. */
    @Autowired
    private AccountRepository accountRepository;

    /** The user profile repository. */
    @Autowired
    private UserProfileRepository userProfileRepository;

}
