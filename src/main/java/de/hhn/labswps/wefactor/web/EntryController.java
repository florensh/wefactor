package de.hhn.labswps.wefactor.web;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.hhn.labswps.wefactor.web.DataObjects.EntriesFilterDataObject;

@Controller
public class EntryController {

    @RequestMapping(value = "/entries/{scope}/{id}", method = RequestMethod.GET)
    public String showEntries(@PathVariable final String scope,
            @PathVariable final String id, final HttpServletRequest request,
            final Principal currentUser, final Model model) {

        final EntriesFilterDataObject filter = new EntriesFilterDataObject();
        model.addAttribute("entriesFilterDataObject", filter);

        return "entries";
    }

}
