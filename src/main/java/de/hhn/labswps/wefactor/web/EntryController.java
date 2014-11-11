package de.hhn.labswps.wefactor.web;

import java.security.Principal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.hhn.labswps.wefactor.domain.Entry;
import de.hhn.labswps.wefactor.domain.EntryRepository;
import de.hhn.labswps.wefactor.domain.UserProfile;
import de.hhn.labswps.wefactor.domain.UserProfileRepository;
import de.hhn.labswps.wefactor.web.DataObjects.EntriesFilterDataObject;
import de.hhn.labswps.wefactor.web.DataObjects.EntryDataObject;

@Controller
public class EntryController {

    private enum EntryScope {
        ALL, USER;
    }

    @Autowired
    private EntryRepository entryRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @RequestMapping(value = "/entries/{scope}", method = RequestMethod.GET)
    public String showEntries(
            @RequestParam(value = "id", required = false) Long id,
            @PathVariable final String scope, final HttpServletRequest request,
            final Principal currentUser, final Model model) {

        final EntriesFilterDataObject filter = new EntriesFilterDataObject();
        model.addAttribute("entriesFilterDataObject", filter);

        EntryScope entryScope = EntryScope.valueOf(scope.toUpperCase());

        switch (entryScope) {
            case ALL:
                model.addAttribute("entries", entryRepository.findAll());

                break;

            case USER:
                model.addAttribute("entries",
                        entryRepository.findByAccountId(id));

                break;

            default:
                break;
        }

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
    public String showEntryDetails(@RequestParam("id") Long id, ModelMap model,
            Principal currentUser) {
        Entry entry = this.entryRepository.findOne(id);

        model.addAttribute("entry", entry);
        return "entrydetails";
    }

    @Secured({ "USER" })
    @RequestMapping(value = "user/entry/edit", method = RequestMethod.GET)
    public String showEntryEditPage(@RequestParam("id") Long id,
            ModelMap model, Principal currentUser) {

        String secUser = currentUser.getName();
        UserProfile profile = this.userProfileRepository
                .findByUsername(secUser);

        Entry entry = this.entryRepository.findOne(id);

        if (entry.getAccount().getId() != profile.getAccount().getId()) {
            throw new BadCredentialsException(
                    "na na na na, wer wird denn hier!!!");
        }

        EntryDataObject entryDataObject = new EntryDataObject();
        entryDataObject.setCode(entry.getEntryCodeText());
        entryDataObject.setDescription(entry.getEntryDescription());
        entryDataObject.setTitle(entry.getName());
        entryDataObject.setId(entry.getId());

        model.addAttribute("entryDataObject", entryDataObject);

        return "entryedit";
    }

    @RequestMapping(value = "/user/entry/add", method = RequestMethod.GET)
    public String showAddEntryPage(final HttpServletRequest request,
            final Principal currentUser, final Model model) {

        model.addAttribute("entryDataObject", new EntryDataObject());

        return "entryedit";
    }

    @RequestMapping(value = "/user/entry/save", method = RequestMethod.POST)
    public String submitEntryForm(@Valid EntryDataObject entryDataObject,
            BindingResult result, Model m, Principal currentUser) {
        if (result.hasErrors()) {
            return "editprofile";
        }

        String secUser = currentUser.getName();
        UserProfile profile = this.userProfileRepository
                .findByUsername(secUser);

        Entry toSave = new Entry();
        toSave.setEntryCodeText(entryDataObject.getCode());
        toSave.setEntryDate(new Date());
        toSave.setEntryDescription(entryDataObject.getDescription());
        toSave.setName(entryDataObject.getTitle());
        toSave.setAccount(profile.getAccount());

        toSave = this.entryRepository.save(toSave);

        return "redirect:/entry/details?id=" + String.valueOf(toSave.getId());
    }

}
