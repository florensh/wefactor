package de.hhn.labswps.wefactor.web;

import java.security.Principal;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import de.hhn.labswps.wefactor.domain.Account;
import de.hhn.labswps.wefactor.domain.AccountRepository;
import de.hhn.labswps.wefactor.domain.TimelineEventRepository;
import de.hhn.labswps.wefactor.domain.UserProfile;
import de.hhn.labswps.wefactor.domain.UserProfileRepository;

@Controller
public class GroupController {

    @Autowired
    private TimelineEventRepository timelineEventRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping("/user/group")
    public String goToGroup(HttpServletRequest request, HttpSession session,
            Principal currentUser, Locale locale, Model model) {

        UserProfile profile = this.userProfileRepository
                .findByUsername(currentUser.getName());

        Account a = profile.getAccount();

        Pageable topFive = new PageRequest(0, 6);

        model.addAttribute("events", this.timelineEventRepository
                .findByTargetOrderByEventDateDesc(a, topFive));

        return "group";

    }

}
