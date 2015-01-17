package de.hhn.labswps.wefactor.web;

import java.security.Principal;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.hhn.labswps.wefactor.web.util.SocialControllerUtil;

/**
 * The controller for base navigation.
 */
@Controller
@RequestMapping("/")
public class IndexController {

    /** The util. */
    @Autowired
    private SocialControllerUtil util;

    /**
     * Index.
     *
     * @param request
     *            the request
     * @param session
     *            the session
     * @param currentUser
     *            the current user
     * @param locale
     *            the locale
     * @param model
     *            the model
     * @return the string
     */
    @RequestMapping(method = RequestMethod.GET)
    public String index(final HttpServletRequest request,
            final HttpSession session, final Principal currentUser,
            final Locale locale, final Model model) {

        if (currentUser == null) {
            return "landing";
        } else {

            this.util.setModel(request, currentUser, model);
            return "forward:/entries/all";
        }

    }
}