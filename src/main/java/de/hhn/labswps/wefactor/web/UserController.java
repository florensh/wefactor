package de.hhn.labswps.wefactor.web;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.hhn.labswps.wefactor.domain.UserProfile;
import de.hhn.labswps.wefactor.domain.UserProfileRepository;
import de.hhn.labswps.wefactor.service.SignUpService;
import de.hhn.labswps.wefactor.web.DataObjects.RegisterFormDataObject;
import de.hhn.labswps.wefactor.web.DataObjects.UserProfileFormDataObject;

/**
 * The controller for user related requests.
 */
@Controller
public class UserController {

    /** The user details service. */
    @Autowired
    private UserDetailsService userDetailsService;

    /** The user profile repository. */
    @Autowired
    private UserProfileRepository userProfileRepository;

    /** The sign up service. */
    @Autowired
    private SignUpService signUpService;

    /**
     * Show signin page.
     *
     * @param request
     *            the request
     * @param currentUser
     *            the current user
     * @param model
     *            the model
     * @return the string
     */
    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String showSigninPage(final HttpServletRequest request,
            final Principal currentUser, final Model model) {
        return "signin";
    }

    /**
     * Show signup page.
     *
     * @param model
     *            the model
     * @return the string
     */
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String showSignupPage(final Model model) {
        model.addAttribute("registerFormDataObject",
                new RegisterFormDataObject());
        return "registration";
    }

    /**
     * Show edit profile page.
     *
     * @param model
     *            the model
     * @param currentUser
     *            the current user
     * @return the string
     */
    @PreAuthorize("USER")
    @RequestMapping(value = "/user/profile/edit", method = RequestMethod.GET)
    public String showEditProfilePage(final Model model,
            final Principal currentUser) {

        final UserProfileFormDataObject data = new UserProfileFormDataObject();

        final UserProfile up = this.userProfileRepository
                .findByUsername(currentUser.getName());

        data.setDisplayName(up.getName());
        data.setFirstName(up.getFirstName());
        data.setLastName(up.getLastName());
        data.setDescription(up.getDescription());

        model.addAttribute("userProfileFormDataObject", data);
        return "editprofile";
    }

    /**
     * Submit user edit profile form.
     *
     * @param id
     *            the id
     * @param userProfileFormDataObject
     *            the user profile form data object
     * @param result
     *            the result
     * @param m
     *            the m
     * @param currentUser
     *            the current user
     * @return the string
     */
    @PreAuthorize("USER")
    @RequestMapping(value = "/user/profile/save", method = RequestMethod.POST)
    public String submitUserEditProfileForm(
            @RequestParam("id") final String id,
            @Valid final UserProfileFormDataObject userProfileFormDataObject,
            final BindingResult result, final Model m,
            final Principal currentUser) {
        if (result.hasErrors()) {
            return "editprofile";
        }

        final UserProfile up = this.userProfileRepository
                .findByUsername(currentUser.getName());

        up.setName(userProfileFormDataObject.getDisplayName());
        up.setFirstName(userProfileFormDataObject.getFirstName());
        up.setLastName(userProfileFormDataObject.getLastName());
        up.setDescription(userProfileFormDataObject.getDescription());

        this.userProfileRepository.save(up);

        return this.showUserProfile(Long.valueOf(id), m);
    }

    /**
     * Submit register form.
     *
     * @param registerFormDataObject
     *            the register form data object
     * @param result
     *            the result
     * @param m
     *            the m
     * @return the string
     */
    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public String submitRegisterForm(
            @Valid final RegisterFormDataObject registerFormDataObject,
            final BindingResult result, final Model m) {
        if (result.hasErrors()) {
            return "registration";
        }

        this.signUpService.execute(registerFormDataObject.getUsername(),
                registerFormDataObject.getEmail(),
                registerFormDataObject.getPassword());

        final UserDetails user = this.userDetailsService
                .loadUserByUsername(registerFormDataObject.getUsername());

        if (user == null) {
            return "registration";
        }

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(user, null, user
                        .getAuthorities()));

        return "redirect:/";
    }

    /**
     * Show user profile.
     *
     * @param id
     *            the id
     * @param model
     *            the model
     * @return the string
     */
    @RequestMapping(value = "/user/profile/details", method = RequestMethod.GET)
    public String showUserProfile(@RequestParam("id") final Long id,
            final Model model) {
        final UserProfile profile = this.userProfileRepository.findOne(id);
        model.addAttribute("profile", profile);
        return "profile";
    }

    @RequestMapping(value = "/help", method = RequestMethod.GET)
    public String showHelpPage(final HttpServletRequest request,
            final Principal currentUser, final Model model) {
        return "help";
    }

}