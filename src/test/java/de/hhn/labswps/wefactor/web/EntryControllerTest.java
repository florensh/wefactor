package de.hhn.labswps.wefactor.web;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.springframework.http.MediaType;

import de.hhn.labswps.wefactor.BaseWebTest;
import de.hhn.labswps.wefactor.domain.Entry;
import de.hhn.labswps.wefactor.domain.MasterEntry;
import de.hhn.labswps.wefactor.domain.Tag;
import de.hhn.labswps.wefactor.web.DataObjects.EntryDataObject;

public class EntryControllerTest extends BaseWebTest {

    @Test
    public void showEntryEditPage() throws Exception {

        getMockMvc()
                .perform(get("/user/entry/add").principal(getTestPrincipal()))
                .andExpect(status().isOk()).andExpect(view().name("entryedit"));

    }

    @Test
    public void storeEntry() throws Exception {

        Tag tag = new Tag();
        tag.setName("Test");

        this.tagRepository.save(tag);
        Set<String> tags = new HashSet<String>();
        tags.add(tag.getName());
        tags.add("New");

        EntryDataObject entryDO = new EntryDataObject();
        entryDO.setChanges("sdfjla sdfl sfdjlsfkjljljkl");
        entryDO.setCode("alsfj l");
        entryDO.setDescription("lasdjflslkjlj k lj kj kl  dfl");
        entryDO.setLanguage("java");
        entryDO.setTeaser("sdfshfdl sljklkjlflj sf jsf lj f");
        entryDO.setTitle("lkasfj lsjdf lsdkfj ");
        entryDO.setTagString("[Test]");
        entryDO.setEditMode("MASTER");

        testSaveEntry(entryDO);

        String searchText = entryDO.getDescription();
        List<Entry> entries = this.masterEntryRepository.search(searchText,
                getTestProfile().getAccount());

        assertThat(entries, not(empty()));
        MasterEntry entry = (MasterEntry) entries.get(0);
        entryDO.setId(entry.getId());

        // request edit page
        getMockMvc()
                .perform(
                        get("/user/entry/edit").principal(getTestPrincipal())
                                .param("id", entry.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(
                        model().attribute("entry",
                                hasProperty("id", not(nullValue()))))
                .andExpect(view().name("entryedit"));

        // save changes
        testSaveEntry(entryDO);

        // save as proposal
        entryDO.setEditMode("PROPOSAL");
        testSaveEntry(entryDO);

        entry = masterEntryRepository.findOne(entry.getId());

        // accept proposal
        getMockMvc()
                .perform(
                        get(
                                "/proposal/accept?id="
                                        + entry.getProposals().iterator()
                                                .next().getId()).principal(
                                getTestPrincipal()))
                .andExpect(status().isOk())
                .andExpect(
                        view().name(
                                "forward:/entry/details?id=" + entry.getId()));

        // reject proposal
        getMockMvc()
                .perform(
                        get(
                                "/proposal/reject?id="
                                        + entry.getProposals().iterator()
                                                .next().getId()).principal(
                                getTestPrincipal()))
                .andExpect(status().isOk())
                .andExpect(
                        view().name(
                                "forward:/entry/details?id=" + entry.getId()));

        // save rating
        getMockMvc().perform(
                get("/rating/save/MasterEntry/" + entry.getId() + "/5")
                        .principal(getTestPrincipal())).andExpect(
                status().isOk());

        getMockMvc().perform(
                get("/rating/save/MasterEntry/" + entry.getId() + "/5")
                        .principal(getTestPrincipal())).andExpect(
                status().isOk());

    }

    @Test
    public void showEntries() throws Exception {
        getMockMvc().perform(get("/entries/all").principal(getTestPrincipal()))
                .andExpect(status().isOk()).andExpect(view().name("entries"));

    }

    private void testSaveEntry(EntryDataObject entryDO) throws Exception {

        String id = null;
        if (entryDO.getId() != null) {
            id = entryDO.getId().toString();
        }

        getMockMvc()
                .perform(
                        post("/user/entry/save")
                                .principal(getTestPrincipal())
                                .accept(MediaType.APPLICATION_FORM_URLENCODED)
                                .contentType(
                                        MediaType.APPLICATION_FORM_URLENCODED)
                                .param("teaser", entryDO.getTeaser())
                                .param("description", entryDO.getDescription())
                                .param("changes", entryDO.getChanges())
                                .param("language", entryDO.getLanguage())
                                .param("code", entryDO.getCode())
                                .param("title", entryDO.getTitle())
                                .param("editMode", entryDO.getEditMode())
                                .param("id", id)
                                .param("tagString", entryDO.getTagString())
                                .sessionAttr("entryDataObject", entryDO))
                .andExpect(status().isOk())
                .andExpect(
                        model().attribute("entry",
                                hasProperty("id", not(nullValue()))))
                .andExpect(view().name("entrydetails"));

    }

}
