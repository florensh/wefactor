package de.hhn.labswps.wefactor.web;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.hhn.labswps.wefactor.domain.EntryRepository;
import de.hhn.labswps.wefactor.web.DataObjects.EntriesFilterDataObject;

@Controller
public class EntryController {

    @Autowired
    private EntryRepository entryRepository;

    @RequestMapping(value = "/entries/{scope}/{id}", method = RequestMethod.GET)
    public String showEntries(@PathVariable final String scope,
            @PathVariable final String id, final HttpServletRequest request,
            final Principal currentUser, final Model model) {

        final EntriesFilterDataObject filter = new EntriesFilterDataObject();
        model.addAttribute("entriesFilterDataObject", filter);

        return "entries";
    }

    @RequestMapping(value = "/entries/explore", method = RequestMethod.GET)
    public String showAllEntries(final HttpServletRequest request,
            final Principal currentUser, final Model model) {

        final EntriesFilterDataObject filter = new EntriesFilterDataObject();
        model.addAttribute("entriesFilterDataObject", filter);

        model.addAttribute("entries", entryRepository.findAll());

        return "entries";
    }

    @RequestMapping(value = "/user/entry", method = RequestMethod.GET)
    public String showEntry(final HttpServletRequest request,
            final Principal currentUser, final Model model) {

        return "entrydetails";
    }

    @RequestMapping(value = "/entries/edittest", method = RequestMethod.GET)
    public String showEditEntryPage(final HttpServletRequest request,
            final Principal currentUser, final Model model) {

        return "entryedit";
    }

    @RequestMapping(value = "/entry/details", method = RequestMethod.GET)
    public String showEntryDetails(@RequestParam("id") int id, ModelMap model) {
        System.out.println(id);
        return "entrydetails";
    }

}
