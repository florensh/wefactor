package de.hhn.labswps.wefactor.web;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.hhn.labswps.wefactor.domain.Entry;
import de.hhn.labswps.wefactor.domain.MasterEntryRepository;
import de.hhn.labswps.wefactor.domain.UserProfile;
import de.hhn.labswps.wefactor.domain.UserProfileRepository;
import de.hhn.labswps.wefactor.service.EntryService;
import de.hhn.labswps.wefactor.web.DataObjects.EntryList;
import de.hhn.labswps.wefactor.web.DataObjects.SearchBoxDataObject;
import de.hhn.labswps.wefactor.web.util.ViewUtil;

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

    @Autowired
    private EntryService entryService;

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
    @RequestMapping(value = "/search/entries", method = RequestMethod.GET)
    public String executeSearch(
            @Valid final SearchBoxDataObject searchBoxDataObject,
            final BindingResult result, final Model m,
            final HttpServletRequest request, final Principal currentUser,
            final Pageable pageable) {
        if (result.hasErrors()) {
            return "editprofile";
        }

        String p = request.getParameter("searchtext");

        final UserProfile profile = this.userProfileRepository
                .findByUsername(currentUser.getName());

        final Page<Entry> entries = this.entryService.search(
                searchBoxDataObject.getSearchtext(), profile.getAccount(),
                pageable);

        final EntryList elist = new EntryList(entries, "?searchtext=" + p);

        ViewUtil.showMessage("We've found " + elist.getTotalElements()
                + " results for " + searchBoxDataObject.getSearchtext(),
                searchBoxDataObject.getSearchtext(), m);

        m.addAttribute("page", elist);

        return "entries";
    }
}
