package de.hhn.labswps.wefactor.web;

import java.security.Principal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import de.hhn.labswps.wefactor.domain.Account;
import de.hhn.labswps.wefactor.domain.Entry;
import de.hhn.labswps.wefactor.domain.EntryRating;
import de.hhn.labswps.wefactor.domain.EntryRatingRepository;
import de.hhn.labswps.wefactor.domain.Group;
import de.hhn.labswps.wefactor.domain.GroupRepository;
import de.hhn.labswps.wefactor.domain.MasterEntry;
import de.hhn.labswps.wefactor.domain.MasterEntryRepository;
import de.hhn.labswps.wefactor.domain.ObjectIdentification;
import de.hhn.labswps.wefactor.domain.ProposalEntry;
import de.hhn.labswps.wefactor.domain.ProposalEntry.Status;
import de.hhn.labswps.wefactor.domain.ProposalEntryRepository;
import de.hhn.labswps.wefactor.domain.Tag;
import de.hhn.labswps.wefactor.domain.TagRepository;
import de.hhn.labswps.wefactor.domain.UserProfile;
import de.hhn.labswps.wefactor.domain.UserProfileRepository;
import de.hhn.labswps.wefactor.domain.VersionEntry;
import de.hhn.labswps.wefactor.domain.VersionEntryRepository;
import de.hhn.labswps.wefactor.service.EntryService;
import de.hhn.labswps.wefactor.service.JournalService;
import de.hhn.labswps.wefactor.service.NotificationService;
import de.hhn.labswps.wefactor.specification.WeFactorValues;
import de.hhn.labswps.wefactor.specification.WeFactorValues.EventType;
import de.hhn.labswps.wefactor.web.DataObjects.EntryDataObject;
import de.hhn.labswps.wefactor.web.DataObjects.EntryList;
import de.hhn.labswps.wefactor.web.util.DataUtils;
import de.hhn.labswps.wefactor.web.util.ViewUtil;

/**
 * The controller for entry related requests.
 */
@Controller
public class EntryController {

    /** The Constant ENTRY_DETAILS_LINK. */
    public static final String ENTRY_DETAILS_LINK = "/entry/details";

    /**
     * The Enum EntryScope.
     */
    public enum EntryScope {

        /** The all. */
        ALL,
        /** The user. */
        USER,
        /** The tag. */
        TAG;
    }

    /**
     * The Enum EntryEditMode.
     */
    private enum EntryEditMode {

        /** The proposal. */
        PROPOSAL,
        /** The master. */
        MASTER;
    }

    @Autowired
    private EntryService entryService;

    /** The entry repository. */
    @Autowired
    private MasterEntryRepository entryRepository;

    /** The version entry repository. */
    @Autowired
    private VersionEntryRepository versionEntryRepository;

    @Autowired
    private JournalService journalService;

    /** The entry rating repository. */
    @Autowired
    private EntryRatingRepository entryRatingRepository;

    /** The proposal entry repository. */
    @Autowired
    private ProposalEntryRepository proposalEntryRepository;

    /** The group repository. */
    @Autowired
    private GroupRepository groupRepository;

    /** The user profile repository. */
    @Autowired
    private UserProfileRepository userProfileRepository;

    /** The tag repository. */
    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private NotificationService notificationService;

    /**
     * Show entries.
     *
     * @param id
     *            the id
     * @param scope
     *            the scope
     * @param request
     *            the request
     * @param currentUser
     *            the current user
     * @param model
     *            the model
     * @return the string
     */

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/entries/{scope}", method = RequestMethod.GET)
    public String showEntries(
            @RequestParam(value = "id", required = false) final String id,
            @PathVariable final String scope, final HttpServletRequest request,
            final Principal currentUser, final Model model, Pageable pageable) {

        final UserProfile profile = this.userProfileRepository
                .findByUsername(currentUser.getName());

        String uri = request.getRequestURI();

        final EntryScope entryScope = EntryScope.valueOf(scope.toUpperCase());

        switch (entryScope) {
            case ALL:

                EntryList page = new EntryList(this.entryService.getEntryList(
                        profile.getAccount(), pageable), uri);

                model.addAttribute("page", page);
                ViewUtil.showMessage("Explore weFactor! There are currently "
                        + page.getTotalElements() + " entries.",
                        String.valueOf(page.getTotalElements()), model);

                break;

            case USER:

                EntryList page2 = new EntryList(
                        this.entryService.getEntryListByUser(Long.valueOf(id),
                                pageable), "");
                model.addAttribute("page", page2);

                ViewUtil.showMessage(
                        "Your entries! You have " + page2.getTotalElements()
                                + " entries created yet.",
                        String.valueOf(page2.getTotalElements()), model);

                break;

            case TAG:

                EntryList page3 = new EntryList(
                        this.entryService.getEntryListByTag(id, pageable), uri);
                model.addAttribute("page", page3);

                ViewUtil.showMessage("We've found " + page3.getTotalElements()
                        + " entries for the tag " + id,
                        String.valueOf(page3.getTotalElements()), model);

                break;

            default:
                break;
        }

        return "entries";
    }

    @RequestMapping(value = "/entries/user/proposals", method = RequestMethod.GET)
    public String showProposals(
            @RequestParam(value = "id", required = true) final String id,
            final Model model, Pageable pageable) {

        EntryList page = new EntryList(this.entryService.getProposalListByUser(
                Long.valueOf(id), pageable), "");

        model.addAttribute("page", page);

        ViewUtil.showMessage(
                "Your proposals! You have " + page.getTotalElements()
                        + " proposals created yet.",
                String.valueOf(page.getTotalElements()), model);

        return "entries";

    }

    @RequestMapping(value = "/entries/user/versions", method = RequestMethod.GET)
    public String showVersions(
            @RequestParam(value = "id", required = true) final String id,
            final Model model, Pageable pageable) {

        EntryList page = new EntryList(this.entryService.getVersionListByUser(
                Long.valueOf(id), pageable), "");

        model.addAttribute("page", page);

        ViewUtil.showMessage(
                "Your versions! You have " + page.getTotalElements()
                        + " versions created yet.",
                String.valueOf(page.getTotalElements()), model);

        return "entries";

    }

    /**
     * Show entry details.
     *
     * @param id
     *            the id
     * @param model
     *            the model
     * @param currentUser
     *            the current user
     * @return the string
     */
    @RequestMapping(value = ENTRY_DETAILS_LINK, method = RequestMethod.GET)
    public String showEntryDetails(@RequestParam("id") final Long id,
            final ModelMap model, final Principal currentUser) {
        final MasterEntry entry = this.entryRepository.findOne(id);
        entry.getStatistics().addViews(1);
        this.entryRepository.save(entry);

        model.addAttribute("entry", entry);
        return "entrydetails";
    }

    /**
     * Show entry code raw.
     *
     * @param id
     *            the id
     * @param type
     *            the type
     * @param model
     *            the model
     * @param currentUser
     *            the current user
     * @return the string
     */
    @RequestMapping(value = "/entry/details/raw", method = RequestMethod.GET)
    public String showEntryCodeRaw(@RequestParam("id") final Long id,
            @RequestParam("type") final String type, final ModelMap model,
            final Principal currentUser) {

        Entry entry = null;
        if (MasterEntry.class.getSimpleName().equals(type)) {
            entry = this.entryRepository.findOne(id);

        } else if (VersionEntry.class.getSimpleName().equals(type)) {
            entry = this.versionEntryRepository.findOne(id);

        } else if (ProposalEntry.class.getSimpleName().equals(type)) {
            entry = this.proposalEntryRepository.findOne(id);

        }

        model.addAttribute("code", entry.getEntryCodeText());
        return "rawCode";
    }

    /**
     * Show proposal details.
     *
     * @param id
     *            the id
     * @param model
     *            the model
     * @param currentUser
     *            the current user
     * @return the string
     */
    @RequestMapping(value = "/proposal/details", method = RequestMethod.GET)
    public String showProposalDetails(@RequestParam("id") final Long id,
            final ModelMap model, final Principal currentUser) {

        final Entry entry = this.proposalEntryRepository.findOne(id);

        // checkForOwner(entry, currentUser);

        model.addAttribute("entry", entry);
        return "proposaldetails";
    }

    private void checkForOwner(Entry entry, Principal currentUser) {

        UserProfile up = this.userProfileRepository.findByUsername(currentUser
                .getName());
        Account account = up.getAccount();

        if (!account.equals(entry.getAccount())) {
            throw new IllegalArgumentException(
                    "User is not allowed to do that!");

        }

    }

    /**
     * Show entry edit page.
     *
     * @param id
     *            the id
     * @param model
     *            the model
     * @param currentUser
     *            the current user
     * @return the string
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "user/entry/edit", method = RequestMethod.GET)
    public String showEntryEditPage(@RequestParam("id") final Long id,
            final ModelMap model, final Principal currentUser) {

        UserProfile profile = this.userProfileRepository
                .findByUsername(currentUser.getName());
        Account account = profile.getAccount();

        MasterEntry entry = this.entryRepository.findOne(id);
        if (!entry.getVersions().isEmpty() || !entry.getProposals().isEmpty()
                || !account.equals(entry.getAccount())) {
            throw new IllegalArgumentException();

        }

        this.fillupModelForEntryEdit(model, currentUser, id,
                EntryEditMode.MASTER);
        return "entryedit";
    }

    /**
     * Show entry edit page for new propose.
     *
     * @param id
     *            the id
     * @param model
     *            the model
     * @param currentUser
     *            the current user
     * @return the string
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "user/entry/propose", method = RequestMethod.GET)
    public String showEntryEditPageForNewPropose(
            @RequestParam("id") final Long id, final ModelMap model,
            final Principal currentUser) {

        this.fillupModelForEntryEdit(model, currentUser, id,
                EntryEditMode.PROPOSAL);
        return "entryedit";
    }

    /**
     * Fillup model for entry edit.
     *
     * @param model
     *            the model
     * @param currentUser
     *            the current user
     * @param id
     *            the id
     * @param editMode
     *            the edit mode
     */
    private void fillupModelForEntryEdit(final ModelMap model,
            final Principal currentUser, final Long id,
            final EntryEditMode editMode) {
        final String secUser = currentUser.getName();
        final UserProfile profile = this.userProfileRepository
                .findByUsername(secUser);

        final Entry master = this.entryRepository.findOne(id);
        Entry entry = master.getHeadVersion();

        // if (entry.getAccount().getId() != profile.getAccount().getId()) {
        // throw new BadCredentialsException(
        // "na na na na, wer wird denn hier!!!");
        // }

        final EntryDataObject entryDataObject = new EntryDataObject();
        entryDataObject.setCode(entry.getEntryCodeText());
        entryDataObject.setDescription(entry.getEntryDescription());
        entryDataObject.setTitle(entry.getName());
        entryDataObject.setId(entry.getId());
        entryDataObject.setTeaser(entry.getTeaser());
        entryDataObject.setLanguage(entry.getLanguage());
        entryDataObject.setTags(entry.getTagAsStrings());
        entryDataObject.setEditMode(editMode.name());
        if (entry.getGroup() != null) {
            entryDataObject.setGroup(entry.getGroup().getId().toString());

        }
        if (editMode.equals(EntryEditMode.MASTER)) {
            entryDataObject.setChanges(entry.getChanges());

        }

        final String[] tags = this
                .resolveTagsAsStrings((List<Tag>) this.tagRepository.findAll());
        model.addAttribute("tags", tags);

        model.addAttribute("entryDataObject", entryDataObject);
        model.addAttribute("entry", entry);
        model.addAttribute("editMode", editMode.name());
        model.addAttribute("languages",
                WeFactorValues.ProgrammingLanguage.values());

        model.addAttribute("groups",
                this.groupRepository.findByMembers(profile.getAccount()));

    }

    /**
     * Delete entry page.
     *
     * @param id
     *            the id
     * @param model
     *            the model
     * @param currentUser
     *            the current user
     * @return the string
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "user/entry/remove", method = RequestMethod.GET)
    public String deleteEntryPage(@RequestParam("id") final Long id,
            final ModelMap model, final Principal currentUser) {

        MasterEntry entry = this.entryRepository.findOne(id);
        checkForOwner(entry, currentUser);
        if (entry.getVersions().isEmpty() && entry.getProposals().isEmpty()) {
            this.entryRepository.delete(id);
            this.journalService
                    .writeEntry(
                            currentUser.getName(),
                            de.hhn.labswps.wefactor.domain.JournalEntry.EventType.REMOVE_ENTRY);

        } else {
            throw new IllegalArgumentException();
        }

        return "forward:/";
    }

    /**
     * Accept proposal.
     *
     * @param id
     *            the id
     * @param model
     *            the model
     * @param currentUser
     *            the current user
     * @return the string
     */
    @RequestMapping(value = "/proposal/accept", method = RequestMethod.GET)
    public String acceptProposal(@RequestParam("id") final Long id,
            final ModelMap model, final Principal currentUser) {

        final ProposalEntry pe = this.proposalEntryRepository.findOne(id);
        doAcceptProposal(pe, currentUser);

        final UserProfile up = this.userProfileRepository
                .findByUsername(currentUser.getName());
        final Account account = up.getAccount();

        final MasterEntry me = pe.getMasterOfProposal();
        checkForOwner(me, currentUser);

        final ObjectIdentification oid = DataUtils.createObjectIdentification(
                me, Entry.class.getSimpleName());

        sendNotificationToTimeline(account, pe.getAccount(),
                EventType.PROPOSAL_ACCEPTED, oid);

        sendNotificationMail(account, pe.getAccount(),
                EventType.PROPOSAL_ACCEPTED, oid);

        this.journalService
                .writeEntry(
                        currentUser.getName(),
                        de.hhn.labswps.wefactor.domain.JournalEntry.EventType.ACCEPT_PROPOSAL);

        return "forward:/entry/details?id="
                + String.valueOf(pe.getMasterOfProposal().getId());
    }

    private void doAcceptProposal(ProposalEntry pe, Principal currentUser) {

        final UserProfile up = this.userProfileRepository
                .findByUsername(currentUser.getName());
        final Account account = up.getAccount();

        final MasterEntry me = pe.getMasterOfProposal();
        final VersionEntry ve = new VersionEntry(pe);
        pe.setStatus(Status.ACCEPTED.name());

        this.proposalEntryRepository.save(pe);
        this.versionEntryRepository.save(ve);
        this.entryRepository.save(me);
    }

    /**
     * Reject proposal.
     *
     * @param id
     *            the id
     * @param model
     *            the model
     * @param currentUser
     *            the current user
     * @return the string
     */
    @RequestMapping(value = "/proposal/reject", method = RequestMethod.GET)
    public String rejectProposal(@RequestParam("id") final Long id,
            final ModelMap model, final Principal currentUser) {

        final UserProfile up = this.userProfileRepository
                .findByUsername(currentUser.getName());
        final Account account = up.getAccount();

        final ProposalEntry pe = this.proposalEntryRepository.findOne(id);
        checkForOwner(pe.getMasterOfProposal(), currentUser);

        pe.setStatus(Status.REJECTED.name());

        this.proposalEntryRepository.save(pe);

        final ObjectIdentification oid = DataUtils.createObjectIdentification(
                pe.getMasterOfProposal(), Entry.class.getSimpleName());

        sendNotificationToTimeline(account, pe.getAccount(),
                EventType.PROPOSAL_REJECTED, oid);

        sendNotificationMail(account, pe.getAccount(),
                EventType.PROPOSAL_REJECTED, oid);

        this.journalService
                .writeEntry(
                        currentUser.getName(),
                        de.hhn.labswps.wefactor.domain.JournalEntry.EventType.REJECT_PROPSAL);

        return "forward:/entry/details?id="
                + String.valueOf(pe.getMasterOfProposal().getId());
    }

    /**
     * Show add entry page.
     *
     * @param request
     *            the request
     * @param currentUser
     *            the current user
     * @param model
     *            the model
     * @return the string
     */
    @RequestMapping(value = "/user/entry/add", method = RequestMethod.GET)
    public String showAddEntryPage(final HttpServletRequest request,
            final Principal currentUser, final Model model) {

        final UserProfile profile = this.userProfileRepository
                .findByUsername(currentUser.getName());

        final EntryDataObject ed = new EntryDataObject();
        ed.setEditMode(EntryEditMode.MASTER.name());

        model.addAttribute("entryDataObject", ed);
        model.addAttribute("languages",
                WeFactorValues.ProgrammingLanguage.values());

        model.addAttribute("groups",
                this.groupRepository.findByMembers(profile.getAccount()));

        model.addAttribute("editMode", EntryEditMode.MASTER.name());
        final String[] tags = this
                .resolveTagsAsStrings((List<Tag>) this.tagRepository.findAll());
        model.addAttribute("tags", tags);

        return "entryedit";
    }

    /**
     * Resolve tags as strings.
     *
     * @param tags
     *            the tags
     * @return the string[]
     */
    private String[] resolveTagsAsStrings(final List<Tag> tags) {
        final String[] retVal = new String[tags.size()];
        for (int i = 0; i < tags.size(); i++) {
            retVal[i] = tags.get(i).getName();
        }

        return retVal;
    }

    /**
     * Submit entry form.
     *
     * @param entryDataObject
     *            the entry data object
     * @param result
     *            the result
     * @param m
     *            the m
     * @param currentUser
     *            the current user
     * @return the string
     */
    @RequestMapping(value = "/user/entry/save", method = RequestMethod.POST)
    public String submitEntryForm(@Valid final EntryDataObject entryDataObject,
            final BindingResult result, final ModelMap m,
            final Principal currentUser) {
        if (result.hasErrors()) {
            m.addAttribute("languages",
                    WeFactorValues.ProgrammingLanguage.values());
            return "entryedit";
        }

        final Entry toSave = this.saveEntry(entryDataObject, currentUser, m);

        return this.showEntryDetails(toSave.getId(), m, currentUser);
    }

    /**
     * Save entry.
     *
     * @param entryDataObject
     *            the entry data object
     * @param currentUser
     *            the current user
     * @param m
     * @return the entry
     */
    private Entry saveEntry(final EntryDataObject entryDataObject,
            final Principal currentUser, ModelMap m) {

        Entry retVal = null;
        final String secUser = currentUser.getName();
        final UserProfile profile = this.userProfileRepository
                .findByUsername(secUser);

        switch (EntryEditMode.valueOf(entryDataObject.getEditMode())) {
            case MASTER:
                final MasterEntry toSave = this.saveAsMasterEntry(
                        entryDataObject, currentUser);
                retVal = toSave;

                ViewUtil.showMessage("You successfully created new entry "
                        + toSave.getName(), toSave.getName(), m);

                break;

            case PROPOSAL:
                final ProposalEntry proposal = this.saveAsProposalEntry(
                        entryDataObject, currentUser);
                retVal = proposal.getMasterOfProposal();
                if (profile.getAccount().equals(retVal.getAccount())) {
                    this.doAcceptProposal(proposal, currentUser);

                }

                ViewUtil.showMessage(
                        "You successfully created a new proposal for  "
                                + proposal.getMasterOfProposal().getName(),
                        proposal.getMasterOfProposal().getName(), m);

                this.journalService
                        .writeEntry(
                                currentUser.getName(),
                                de.hhn.labswps.wefactor.domain.JournalEntry.EventType.NEW_PROPOSAL);

                break;

            default:
                break;
        }

        return retVal;
    }

    /**
     * Save as proposal entry.
     *
     * @param entryDataObject
     *            the entry data object
     * @param currentUser
     *            the current user
     * @return the master entry
     */
    private ProposalEntry saveAsProposalEntry(
            final EntryDataObject entryDataObject, final Principal currentUser) {
        MasterEntry master;
        if (entryDataObject.getId() != null) {
            master = this.entryRepository.findOne(entryDataObject.getId());
            if (master == null) {
                VersionEntry version = this.versionEntryRepository
                        .findOne(entryDataObject.getId());
                master = version.getMasterOfVersion();
            }

        } else {
            throw new IllegalArgumentException();
        }

        final String secUser = currentUser.getName();
        final UserProfile profile = this.userProfileRepository
                .findByUsername(secUser);

        List<ProposalEntry> list = this.proposalEntryRepository
                .findByAccountAndNameAndEntryDescriptionAndLanguageAndChangesAndTeaserAndEntryCodeTextAndMasterOfProposalAndStatus(
                        profile.getAccount(), entryDataObject.getTitle(),
                        entryDataObject.getDescription(),
                        entryDataObject.getLanguage(),
                        entryDataObject.getChanges(),
                        entryDataObject.getTeaser(), entryDataObject.getCode(),
                        master, Status.NEW.name());

        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }

        final ProposalEntry pe = new ProposalEntry();

        this.fillupWithPersistedTags(pe, entryDataObject.getTags());
        pe.setEntryCodeText(entryDataObject.getCode());
        pe.setLanguage(entryDataObject.getLanguage());
        pe.setTeaser(entryDataObject.getTeaser());
        pe.setEntryDate(new Date());
        pe.setEntryDescription(entryDataObject.getDescription());
        pe.setName(entryDataObject.getTitle());
        pe.setAccount(profile.getAccount());
        pe.setStatus(Status.NEW.name());
        pe.setChanges(entryDataObject.getChanges());

        this.proposalEntryRepository.save(pe);
        master.addProposal(pe);

        master = this.entryRepository.save(master);

        final ObjectIdentification oid = DataUtils.createObjectIdentification(
                pe.getMasterOfProposal(), Entry.class.getSimpleName());

        sendNotificationToTimeline(profile.getAccount(), pe
                .getMasterOfProposal().getAccount(), EventType.MADE_PROPOSAL,
                oid);

        sendNotificationMail(profile.getAccount(), pe.getMasterOfProposal()
                .getAccount(), EventType.MADE_PROPOSAL, oid);

        return pe;
    }

    /**
     * Save as master entry.
     *
     * @param entryDataObject
     *            the entry data object
     * @param currentUser
     *            the current user
     * @return the master entry
     */
    private MasterEntry saveAsMasterEntry(
            final EntryDataObject entryDataObject, final Principal currentUser) {
        boolean edit = false;
        MasterEntry toSave;
        boolean doubleEntry = false;
        final String secUser = currentUser.getName();
        final UserProfile profile = this.userProfileRepository
                .findByUsername(secUser);

        if (entryDataObject.getId() != null) {
            toSave = this.entryRepository.findOne(entryDataObject.getId());
            edit = true;

        } else {
            List<Entry> list = this.entryRepository
                    .findByAccountAndNameAndEntryDescriptionAndLanguageAndChangesAndTeaserAndEntryCodeText(
                            profile.getAccount(), entryDataObject.getTitle(),
                            entryDataObject.getDescription(),
                            entryDataObject.getLanguage(),
                            entryDataObject.getChanges(),
                            entryDataObject.getTeaser(),
                            entryDataObject.getCode());

            if (list != null && !list.isEmpty()) {
                doubleEntry = true;
                toSave = (MasterEntry) list.get(0);
            } else {
                toSave = new MasterEntry();

            }
        }

        if (!doubleEntry) {
            this.fillupWithPersistedTags(toSave, entryDataObject.getTags());

            toSave.setEntryCodeText(entryDataObject.getCode());
            toSave.setLanguage(entryDataObject.getLanguage());
            toSave.setTeaser(entryDataObject.getTeaser());
            toSave.setEntryDate(new Date());
            toSave.setEntryDescription(entryDataObject.getDescription());
            toSave.setName(entryDataObject.getTitle());
            toSave.setAccount(profile.getAccount());
            toSave.setChanges(entryDataObject.getChanges());

            if ((entryDataObject.getGroup() != null)
                    && !entryDataObject.getGroup().isEmpty()) {
                toSave.setGroup(this.groupRepository.findOne(Long
                        .valueOf(entryDataObject.getGroup())));
            } else {
                toSave.setGroup(null);
            }

            toSave = this.entryRepository.save(toSave);

            // store event if entry in group
            if (toSave.getGroup() != null) {
                final ObjectIdentification oid = DataUtils
                        .createObjectIdentification(toSave,
                                Entry.class.getSimpleName());

                sendNotificationToTimeline(toSave.getAccount(),
                        toSave.getGroup(), EventType.MADE_ENTRY, oid);

                sendNotificationMail(toSave.getAccount(), toSave.getGroup(),
                        EventType.MADE_ENTRY, oid);
            }

            if (edit) {
                this.journalService
                        .writeEntry(
                                currentUser.getName(),
                                de.hhn.labswps.wefactor.domain.JournalEntry.EventType.EDIT_ENTRY);
            } else {
                this.journalService
                        .writeEntry(
                                currentUser.getName(),
                                de.hhn.labswps.wefactor.domain.JournalEntry.EventType.NEW_ENTRY);
            }

        }

        return toSave;
    }

    /**
     * Fillup with persisted tags.
     *
     * @param toSave
     *            the to save
     * @param tags
     *            the tags
     */
    private void fillupWithPersistedTags(final Entry toSave, final String[] tags) {

        final Set<String> newTags = new HashSet<String>();
        for (final String tag : tags) {
            boolean found = false;
            for (final Tag t : toSave.getTags()) {
                if (t.getName().equals(tag)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                newTags.add(tag);
            }
        }

        for (final String s : newTags) {
            Tag tag = this.tagRepository.findByName(s);
            if (tag == null) {
                tag = new Tag(s);
                tag = this.tagRepository.save(tag);
            }
            toSave.addTag(tag);
        }

    }

    /**
     * Gets the entry.
     *
     * @param type
     *            the type
     * @param id
     *            the id
     * @return the entry
     */
    @RequestMapping(value = "/entryajax/{type}/{id}")
    public @ResponseBody Entry getEntry(@PathVariable final String type,
            @PathVariable final Long id) {

        Entry entry = null;
        if (MasterEntry.class.getSimpleName().equals(type)) {
            entry = this.entryRepository.findOne(id);

        } else if (VersionEntry.class.getSimpleName().equals(type)) {
            entry = this.versionEntryRepository.findOne(id);

        } else if (ProposalEntry.class.getSimpleName().equals(type)) {
            entry = this.proposalEntryRepository.findOne(id);

        }
        return entry;

    }

    /**
     * Save rating.
     *
     * @param type
     *            the type
     * @param id
     *            the id
     * @param rating
     *            the rating
     * @param currentUser
     *            the current user
     * @return the entry
     */
    @RequestMapping(value = "/rating/save/{type}/{id}/{rating}")
    public @ResponseBody Entry saveRating(@PathVariable final String type,
            @PathVariable final Long id, @PathVariable final Integer rating,
            final Principal currentUser) {

        Entry entry = null;

        if (MasterEntry.class.getSimpleName().equals(type)) {
            final MasterEntry me = this.entryRepository.findOne(id);
            final EntryRating entryRating = addRating(me, rating, currentUser);
            this.entryRatingRepository.save(entryRating);
            this.entryRepository.save(me);
            entry = me;

        } else if (VersionEntry.class.getSimpleName().equals(type)) {
            final VersionEntry ve = this.versionEntryRepository.findOne(id);
            final EntryRating entryRating = addRating(ve, rating, currentUser);
            this.entryRatingRepository.save(entryRating);
            this.versionEntryRepository.save(ve);
            entry = ve;

        }

        this.journalService
                .writeEntry(
                        currentUser.getName(),
                        de.hhn.labswps.wefactor.domain.JournalEntry.EventType.RATE_ENTRY);
        return entry;

    }

    @RequestMapping(value = "/watch/{type}/{id}")
    public @ResponseBody Entry saveWatcher(@PathVariable final String type,
            @PathVariable final Long id, final Principal currentUser) {

        Entry entry = this.entryService.addWatcher(id, type, currentUser);

        return entry;

    }

    @RequestMapping(value = "/stopwatch/{type}/{id}")
    public @ResponseBody Entry removeWatcher(@PathVariable final String type,
            @PathVariable final Long id, final Principal currentUser) {

        Entry entry = this.entryService.removeWatcher(id, type, currentUser);

        return entry;

    }

    private EntryRating addRating(Entry me, Integer rating,
            Principal currentUser) {

        final UserProfile up = this.userProfileRepository
                .findByUsername(currentUser.getName());

        final Account account = up.getAccount();
        EntryRating entryRating = me.getRatingOfUser(account);

        if (entryRating == null) {
            entryRating = new EntryRating();
            entryRating.setAccount(account);
            me.addRating(entryRating);

        }

        entryRating.setValue(rating);

        return entryRating;

    }

    private void sendNotificationMail(Account source, Account target,
            EventType eventType, ObjectIdentification oid) {

        this.notificationService.sendMailNotificationsForEvent(source, target,
                eventType, oid);

    }

    private void sendNotificationMail(Account account, Group group,
            EventType madeEntry, ObjectIdentification oid) {

        this.notificationService.sendMailNotificationsForEvent(account, group,
                EventType.MADE_ENTRY, oid);

    }

    private void sendNotificationToTimeline(Account source, Account target,
            EventType eventType, ObjectIdentification oid) {

        this.notificationService.sendToTimeline(source, target, eventType, oid);

    }

    private void sendNotificationToTimeline(Account account, Group group,
            EventType madeEntry, ObjectIdentification oid) {

        this.notificationService.sendToTimeline(account, group,
                EventType.MADE_ENTRY, oid);

    }

}
