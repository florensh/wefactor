package de.hhn.labswps.wefactor.web;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.hhn.labswps.wefactor.domain.AccountRepository;
import de.hhn.labswps.wefactor.domain.Entry;
import de.hhn.labswps.wefactor.domain.Group;
import de.hhn.labswps.wefactor.domain.GroupRepository;
import de.hhn.labswps.wefactor.domain.ObjectIdentification;
import de.hhn.labswps.wefactor.domain.TimelineEvent;
import de.hhn.labswps.wefactor.domain.TimelineEventRepository;
import de.hhn.labswps.wefactor.domain.UserProfile;
import de.hhn.labswps.wefactor.domain.UserProfileRepository;
import de.hhn.labswps.wefactor.service.EntryService;
import de.hhn.labswps.wefactor.specification.WeFactorValues.EventType;
import de.hhn.labswps.wefactor.web.DataObjects.EntryList;
import de.hhn.labswps.wefactor.web.DataObjects.GroupDataObject;
import de.hhn.labswps.wefactor.web.DataObjects.ScreenMessageObject;
import de.hhn.labswps.wefactor.web.DataObjects.SearchBoxDataObject;
import de.hhn.labswps.wefactor.web.util.DataUtils;

/**
 * The controller for group related requests.
 */
@Controller
public class GroupController {

    /** The Constant GROUP_DETAILS_LINK. */
    public static final String GROUP_DETAILS_LINK = "/user/group";

    /** The timeline event repository. */
    @Autowired
    private TimelineEventRepository timelineEventRepository;

    /** The user profile repository. */
    @Autowired
    private UserProfileRepository userProfileRepository;

    /** The account repository. */
    @Autowired
    private AccountRepository accountRepository;

    /** The group repository. */
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private EntryService entryService;

    /**
     * Go to group.
     *
     * @param id
     *            the id
     * @param model
     *            the model
     * @param currentUser
     *            the current user
     * @return the string
     */
    @RequestMapping(GROUP_DETAILS_LINK)
    public String goToGroup(@RequestParam("id") final Long id,
            final ModelMap model, final Principal currentUser,
            final Pageable pageable) {

        // Laden der groupe
        final Group group = this.groupRepository.findOne(id);
        model.addAttribute("group", group);

        // Laden der events
        final Pageable topFive = new PageRequest(0,
                TimelineEventRepository.DEFAULT_PAGE_SIZE);
        model.addAttribute("events", this.timelineEventRepository
                .findByTargetGroupOrderByEventDateDesc(group, topFive));

        final Page<Entry> page = this.entryService.getEntryListByGroup(group,
                pageable);
        model.addAttribute("entries", new EntryList(page, ""));

        return "group";

    }

    /**
     * Join group.
     *
     * @param id
     *            the id
     * @param model
     *            the model
     * @param currentUser
     *            the current user
     * @return the string
     */
    @RequestMapping("/user/group/join")
    public String joinGroup(@RequestParam("id") final Long id,
            final ModelMap model, final Principal currentUser) {

        final String secUser = currentUser.getName();
        final UserProfile profile = this.userProfileRepository
                .findByUsername(secUser);

        // Laden der groupe
        final Group group = this.groupRepository.findOne(id);
        group.addMember(profile.getAccount());

        this.groupRepository.save(group);
        this.accountRepository.save(profile.getAccount());

        final ObjectIdentification oid = DataUtils.createObjectIdentification(
                group, Group.class.getSimpleName());
        final TimelineEvent event = new TimelineEvent(new Date(),
                profile.getAccount(), group, EventType.USER_JOINED_GROUP, oid);

        this.timelineEventRepository.save(event);

        return this.goToGroupBrowser(model, currentUser);

    }

    /**
     * Leave group.
     *
     * @param id
     *            the id
     * @param model
     *            the model
     * @param currentUser
     *            the current user
     * @return the string
     */
    @RequestMapping("/user/group/leave")
    public String leaveGroup(@RequestParam("id") final Long id,
            final ModelMap model, final Principal currentUser) {

        final String secUser = currentUser.getName();
        final UserProfile profile = this.userProfileRepository
                .findByUsername(secUser);

        // Laden der groupe
        final Group group = this.groupRepository.findOne(id);
        group.removeMember(profile.getAccount());

        this.groupRepository.save(group);
        this.accountRepository.save(profile.getAccount());

        final ObjectIdentification oid = DataUtils.createObjectIdentification(
                group, Group.class.getSimpleName());
        final TimelineEvent event = new TimelineEvent(new Date(),
                profile.getAccount(), group, EventType.USER_LEFT_GROUP, oid);

        this.timelineEventRepository.save(event);

        return this.goToGroupBrowser(model, currentUser);

    }

    /**
     * Go to group browser.
     *
     * @param model
     *            the model
     * @param currentUser
     *            the current user
     * @return the string
     */
    @RequestMapping("/user/groupbrowser")
    public String goToGroupBrowser(final ModelMap model,
            final Principal currentUser) {

        final String secUser = currentUser.getName();
        final UserProfile profile = this.userProfileRepository
                .findByUsername(secUser);

        model.addAttribute("groups",
                this.groupRepository.findByMembers(profile.getAccount()));

        return "groupbrowser";
    }

    /**
     * Go to group edit.
     *
     * @param model
     *            the model
     * @param currentUser
     *            the current user
     * @return the string
     */
    @RequestMapping("/user/group/add")
    public String goToGroupEdit(final ModelMap model,
            final Principal currentUser) {

        final String secUser = currentUser.getName();
        final UserProfile profile = this.userProfileRepository
                .findByUsername(secUser);

        model.addAttribute("groupDataObject", new GroupDataObject());

        return "groupedit";
    }

    /**
     * Submit group edit form.
     *
     * @param groupDataObject
     *            the group data object
     * @param result
     *            the result
     * @param m
     *            the m
     * @param currentUser
     *            the current user
     * @return the string
     */
    @RequestMapping(value = "/user/group/save", method = RequestMethod.POST)
    public String submitGroupEditForm(
            @Valid final GroupDataObject groupDataObject,
            final BindingResult result, final ModelMap m,
            final Principal currentUser) {
        if (result.hasErrors()) {
            return "groupedit";
        }

        final UserProfile up = this.userProfileRepository
                .findByUsername(currentUser.getName());

        final Group group = new Group(groupDataObject.getName(),
                groupDataObject.getDescription());

        group.addMember(up.getAccount());

        this.groupRepository.save(group);
        this.accountRepository.save(up.getAccount());

        return this.goToGroupBrowser(m, currentUser);
    }

    /**
     * Execute search.
     *
     * @param searchBoxDataObject
     *            the search box data object
     * @param result
     *            the result
     * @param m
     *            the m
     * @param currentUser
     *            the current user
     * @return the string
     */
    @RequestMapping(value = "/search/groups", method = RequestMethod.POST)
    public String executeSearch(
            @Valid final SearchBoxDataObject searchBoxDataObject,
            final BindingResult result, final Model m,
            final Principal currentUser) {
        if (result.hasErrors()) {
            return "editprofile";
        }

        final List<Group> groups = this.groupRepository
                .findByNameContaining(searchBoxDataObject.getSearchtext());

        final ScreenMessageObject sm = new ScreenMessageObject("We've found "
                + groups.size() + " results for "
                + searchBoxDataObject.getSearchtext());
        sm.setStrong(searchBoxDataObject.getSearchtext());
        m.addAttribute(ScreenMessageObject.MODEL_NAME, sm);

        m.addAttribute("groups", groups);

        return "groupbrowser";
    }

}
