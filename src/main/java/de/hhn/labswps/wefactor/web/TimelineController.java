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

/**
 * The controller for timeline related requests.
 */
@Controller
public class TimelineController {

    /**
     * The Enum Scope.
     */
    public enum Scope {

        /** The scope user. */
        USER,
        /** The scope group. */
        GROUP
    }

    /** The timeline event repository. */
    @Autowired
    private TimelineEventRepository timelineEventRepository;

    /** The group repository. */
    @Autowired
    private GroupRepository groupRepository;

    /** The user profile repository. */
    @Autowired
    private UserProfileRepository userProfileRepository;

    /** The account repository. */
    @Autowired
    private AccountRepository accountRepository;

    /**
     * Go to timeline.
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
    @RequestMapping("/user/timeline")
    public String goToTimeline(final HttpServletRequest request,
            final HttpSession session, final Principal currentUser,
            final Locale locale, final Model model) {

        final UserProfile profile = this.userProfileRepository
                .findByUsername(currentUser.getName());

        final Account a = profile.getAccount();

        final Pageable topFive = new PageRequest(0, 6);

        if ((a.getGroups() != null) && !a.getGroups().isEmpty()) {
            model.addAttribute("events", this.timelineEventRepository
                    .findByTargetAccountOrTargetGroupInOrderByEventDateDesc(a,
                            a.getGroups(), topFive));

        } else {
            model.addAttribute("events", this.timelineEventRepository
                    .findByTargetAccountOrderByEventDateDesc(a, topFive));

        }

        return "timeline";

    }

    /**
     * Gets the timeline entry.
     *
     * @param id
     *            the id
     * @param scope
     *            the scope
     * @param currentPage
     *            the current page
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
     * @return the timeline entry
     */
    @RequestMapping("/timelineAjax/{scope}/{currentPage}")
    public String getTimelineEntry(
            @RequestParam(value = "id", required = true) final Long id,
            @PathVariable final String scope,
            @PathVariable final int currentPage,
            final HttpServletRequest request, final HttpSession session,
            final Principal currentUser, final Locale locale, final Model model) {

        final UserProfile profile = this.userProfileRepository
                .findByUsername(currentUser.getName());

        final Account a = profile.getAccount();

        final Pageable topTwo = new PageRequest(currentPage, 3);

        List<TimelineEvent> result = null;

        switch (Scope.valueOf(scope.toUpperCase())) {
            case GROUP:
                result = this.timelineEventRepository
                        .findByTargetGroupOrderByEventDateDesc(
                                this.groupRepository.findOne(id), topTwo);
                break;

            case USER:

                if ((a.getGroups() != null) && !a.getGroups().isEmpty()) {
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
