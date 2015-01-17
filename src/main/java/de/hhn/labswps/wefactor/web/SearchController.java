package de.hhn.labswps.wefactor.web;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.hhn.labswps.wefactor.domain.Entry;
import de.hhn.labswps.wefactor.domain.MasterEntryRepository;
import de.hhn.labswps.wefactor.domain.UserProfile;
import de.hhn.labswps.wefactor.domain.UserProfileRepository;
import de.hhn.labswps.wefactor.web.DataObjects.EntryList;
import de.hhn.labswps.wefactor.web.DataObjects.ScreenMessageObject;
import de.hhn.labswps.wefactor.web.DataObjects.SearchBoxDataObject;

/**
 * The controller for search requests.
 */
@Controller
public class SearchController {

    /** The master entry repository. */
    @Autowired
    private MasterEntryRepository masterEntryRepository;

    /** The user profile repository. */
    @Autowired
    private UserProfileRepository userProfileRepository;

    /**
     * Execute search.
     *
     * @param searchBoxDataObject
     *            the search box data object
     * @param result
     *            the result
     * @param m
     *            the m
     * @param currentUser
     *            the current user
     * @return the string
     */
    @RequestMapping(value = "/search/entries", method = RequestMethod.POST)
    public String executeSearch(
            @Valid final SearchBoxDataObject searchBoxDataObject,
            final BindingResult result, final Model m,
            final Principal currentUser) {
        if (result.hasErrors()) {
            return "editprofile";
        }

        final UserProfile profile = this.userProfileRepository
                .findByUsername(currentUser.getName());

        final List<Entry> entries = this.masterEntryRepository.search(
                searchBoxDataObject.getSearchtext(), profile.getAccount());

        final EntryList elist = new EntryList(entries);

        final ScreenMessageObject sm = new ScreenMessageObject("We've found "
                + elist.size() + " results for "
                + searchBoxDataObject.getSearchtext());
        sm.setStrong(searchBoxDataObject.getSearchtext());
        m.addAttribute(ScreenMessageObject.MODEL_NAME, sm);

        m.addAttribute("entries", elist);

        return "entries";
    }
}
