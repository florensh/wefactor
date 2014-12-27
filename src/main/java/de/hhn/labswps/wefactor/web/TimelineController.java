package de.hhn.labswps.wefactor.web;

import java.security.Principal;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.social.ResourceNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import de.hhn.labswps.wefactor.domain.Account;
import de.hhn.labswps.wefactor.domain.AccountRepository;
import de.hhn.labswps.wefactor.domain.GroupRepository;
import de.hhn.labswps.wefactor.domain.TimelineEvent;
import de.hhn.labswps.wefactor.domain.TimelineEventRepository;
import de.hhn.labswps.wefactor.domain.UserProfile;
import de.hhn.labswps.wefactor.domain.UserProfileRepository;

@Controller
public class TimelineController {

    public enum Scope {
        USER, GROUP
    }

    @Autowired
    private TimelineEventRepository timelineEventRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping("/user/timeline")
    public String goToTimeline(HttpServletRequest request, HttpSession session,
            Principal currentUser, Locale locale, Model model) {

        UserProfile profile = this.userProfileRepository
                .findByUsername(currentUser.getName());

        Account a = profile.getAccount();

        Pageable topFive = new PageRequest(0, 6);

        if (a.getGroups() != null && !a.getGroups().isEmpty()) {
            model.addAttribute("events", this.timelineEventRepository
                    .findByTargetAccountOrTargetGroupInOrderByEventDateDesc(a,
                            a.getGroups(), topFive));

        } else {
            model.addAttribute("events", this.timelineEventRepository
                    .findByTargetAccountOrderByEventDateDesc(a, topFive));

        }

        return "timeline";

    }

    @RequestMapping("/timelineAjax/{scope}/{currentPage}")
    public String getTimelineEntry(
            @RequestParam(value = "id", required = true) Long id,
            @PathVariable String scope, @PathVariable int currentPage,
            HttpServletRequest request, HttpSession session,
            Principal currentUser, Locale locale, Model model) {

        UserProfile profile = this.userProfileRepository
                .findByUsername(currentUser.getName());

        Account a = profile.getAccount();

        Pageable topTwo = new PageRequest(currentPage, 3);

        List<TimelineEvent> result = null;

        switch (Scope.valueOf(scope.toUpperCase())) {
            case GROUP:
                result = this.timelineEventRepository
                        .findByTargetGroupOrderByEventDateDesc(
                                this.groupRepository.findOne(id), topTwo);
                break;

            case USER:

                if (a.getGroups() != null && !a.getGroups().isEmpty()) {
                    result = this.timelineEventRepository
                            .findByTargetAccountOrTargetGroupInOrderByEventDateDesc(
                                    a, a.getGroups(), topTwo);

                } else {
                    result = this.timelineEventRepository
                            .findByTargetAccountOrderByEventDateDesc(a, topTwo);

                }
                break;

            default:
                break;
        }

        if (result.isEmpty()) {
            throw new ResourceNotFoundException(null, null);
        }

        model.addAttribute("events", result);

        return "timelineEntryList";
    }

}
