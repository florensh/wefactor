package de.hhn.labswps.wefactor.domain;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.hhn.labswps.wefactor.BaseTest;

/**
 * The Class EntryRepositoryTest.
 */
public class EntryRepositoryTest extends BaseTest {

    /** The entry repository. */
    @Autowired
    private EntryRepository entryRepository;

    /**
     * Basic test.
     */
    @Test
    public final void basicTest() {

        Entry entry = new Entry();
        entryRepository.save(entry);

    }
}
