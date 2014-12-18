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
import de.hhn.labswps.wefactor.web.DataObjects.EntryList;
import de.hhn.labswps.wefactor.web.DataObjects.ScreenMessageObject;
import de.hhn.labswps.wefactor.web.DataObjects.SearchBoxDataObject;

@Controller
public class SearchController {

    @Autowired
    private MasterEntryRepository masterEntryRepository;

    @RequestMapping(value = "/search/entries", method = RequestMethod.POST)
    public String executeSearch(@Valid SearchBoxDataObject searchBoxDataObject,
            BindingResult result, Model m, Principal currentUser) {
        if (result.hasErrors()) {
            return "editprofile";
        }

        List<Entry> entries = this.masterEntryRepository
                .findByEntryDescriptionContainingOrNameContainingOrTeaserContainingOrAccountProfilesNameContaining(
                        searchBoxDataObject.getSearchtext(),
                        searchBoxDataObject.getSearchtext(),
                        searchBoxDataObject.getSearchtext(),
                        searchBoxDataObject.getSearchtext());

        EntryList elist = new EntryList(entries);

        ScreenMessageObject sm = new ScreenMessageObject("We've found "
                + elist.size() + " results for "
                + searchBoxDataObject.getSearchtext());
        sm.setStrong(searchBoxDataObject.getSearchtext());
        m.addAttribute(ScreenMessageObject.MODEL_NAME, sm);

        m.addAttribute("entries", elist);

        return "entries";
    }
}
