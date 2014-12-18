package de.hhn.labswps.wefactor.web;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.hhn.labswps.wefactor.domain.UserProfile;
import de.hhn.labswps.wefactor.domain.UserProfileRepository;
import de.hhn.labswps.wefactor.service.SignUpService;
import de.hhn.labswps.wefactor.web.DataObjects.RegisterFormDataObject;
import de.hhn.labswps.wefactor.web.DataObjects.UserProfileFormDataObject;

@Controller
public class UserController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private SignUpService signUpService;

    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String showSigninPage(HttpServletRequest request,
            Principal currentUser, Model model) {
        return "signin";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String showSignupPage(Model model) {
        model.addAttribute("registerFormDataObject",
                new RegisterFormDataObject());
        return "registration";
    }

    // @RequestMapping(value = "/user/profile", method = RequestMethod.GET)
    // public String showSettings(Model model) {
    // return "profile";
    // }

    @RequestMapping(value = "/user/profile/edit", method = RequestMethod.GET)
    public String showEditProfilePage(Model model, Principal currentUser) {

        UserProfileFormDataObject data = new UserProfileFormDataObject();

        UserProfile up = this.userProfileRepository.findByUsername(currentUser
                .getName());

        data.setDisplayname(up.getName());
        data.setFirstName(up.getFirstName());
        data.setLastName(up.getLastName());
        data.setDescription(up.getDescription());

        model.addAttribute("userProfileFormDataObject", data);
        return "editprofile";
    }

    @RequestMapping(value = "/user/profile/save", method = RequestMethod.POST)
    public String submitUserEditProfileForm(@RequestParam("id") String id,
            @Valid UserProfileFormDataObject userProfileFormDataObject,
            BindingResult result, Model m, Principal currentUser) {
        if (result.hasErrors()) {
            return "editprofile";
        }

        UserProfile up = this.userProfileRepository.findByUsername(currentUser
                .getName());

        up.setName(userProfileFormDataObject.getDisplayname());
        up.setFirstName(userProfileFormDataObject.getFirstName());
        up.setLastName(userProfileFormDataObject.getLastName());
        up.setDescription(userProfileFormDataObject.getDescription());

        this.userProfileRepository.save(up);

        return "redirect:/user/profile/details?id=" + id;
    }

    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public String submitRegisterForm(
            @Valid RegisterFormDataObject registerFormDataObject,
            BindingResult result, Model m) {
        if (result.hasErrors()) {
            return "registration";
        }

        this.signUpService.execute(registerFormDataObject.getUsername(),
                registerFormDataObject.getEmail(),
                registerFormDataObject.getPassword());

        UserDetails user = userDetailsService
                .loadUserByUsername(registerFormDataObject.getUsername());

        if (user == null) {
            return "registration";
        }

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(user, null, user
                        .getAuthorities()));

        return "redirect:/";
    }

    @RequestMapping(value = "/user/profile/details", method = RequestMethod.GET)
    public String showUserProfile(@RequestParam("id") Long id, ModelMap model) {
        UserProfile profile = this.userProfileRepository.findOne(id);
        model.addAttribute("profile", profile);
        return "profile";
    }

}