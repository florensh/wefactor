package de.hhn.labswps.wefactor.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.hhn.labswps.wefactor.service.JournalService;
import de.hhn.labswps.wefactor.web.util.ViewUtil;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/administration")
public class AdminController extends BaseController {

    @Autowired
    private JournalService journalService;

    @RequestMapping(method = RequestMethod.GET)
    public String admin(final Model model) {

        ViewUtil.showMessage(
                "Manage weFactor! There are currently 143 users online.",
                "143", model);

        return "administration";
    }

}
