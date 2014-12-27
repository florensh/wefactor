package de.hhn.labswps.wefactor.web;

import java.security.Principal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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
import de.hhn.labswps.wefactor.domain.GroupRepository;
import de.hhn.labswps.wefactor.domain.MasterEntry;
import de.hhn.labswps.wefactor.domain.MasterEntryRepository;
import de.hhn.labswps.wefactor.domain.ObjectIdentification;
import de.hhn.labswps.wefactor.domain.ProposalEntry;
import de.hhn.labswps.wefactor.domain.ProposalEntry.Status;
import de.hhn.labswps.wefactor.domain.ProposalEntryRepository;
import de.hhn.labswps.wefactor.domain.Tag;
import de.hhn.labswps.wefactor.domain.TagRepository;
import de.hhn.labswps.wefactor.domain.TimelineEvent;
import de.hhn.labswps.wefactor.domain.TimelineEventRepository;
import de.hhn.labswps.wefactor.domain.UserProfile;
import de.hhn.labswps.wefactor.domain.UserProfileRepository;
import de.hhn.labswps.wefactor.domain.VersionEntry;
import de.hhn.labswps.wefactor.domain.VersionEntryRepository;
import de.hhn.labswps.wefactor.specification.WeFactorValues;
import de.hhn.labswps.wefactor.specification.WeFactorValues.EventType;
import de.hhn.labswps.wefactor.web.DataObjects.EntriesFilterDataObject;
import de.hhn.labswps.wefactor.web.DataObjects.EntryDataObject;
import de.hhn.labswps.wefactor.web.DataObjects.EntryList;
import de.hhn.labswps.wefactor.web.util.DataUtils;

@Controller
public class EntryController {

    public static final String ENTRY_DETAILS_LINK = "/entry/details";

    private enum EntryScope {
        ALL, USER, TAG;
    }

    private enum EntryEditMode {
        PROPOSAL, MASTER;
    }

    @Autowired
    private MasterEntryRepository entryRepository;

    @Autowired
    private VersionEntryRepository versionEntryRepository;

    @Autowired
    private EntryRatingRepository entryRatingRepository;

    @Autowired
    private ProposalEntryRepository proposalEntryRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TimelineEventRepository timelineEventRepository;

    @RequestMapping(value = "/entries/{scope}", method = RequestMethod.GET)
    public String showEntries(
            @RequestParam(value = "id", required = false) String id,
            @PathVariable final String scope, final HttpServletRequest request,
            final Principal currentUser, final Model model) {

        UserProfile profile = this.userProfileRepository
                .findByUsername(currentUser.getName());

        final EntriesFilterDataObject filter = new EntriesFilterDataObject();
        model.addAttribute("entriesFilterDataObject", filter);

        EntryScope entryScope = EntryScope.valueOf(scope.toUpperCase());

        switch (entryScope) {
            case ALL:
                List<Entry> list = (List<Entry>) entryRepository
                        .findDistinctByGroupIsNullOrGroupMembers(profile
                                .getAccount());
                EntryList eList = new EntryList();
                eList.addAll(list);
                model.addAttribute("entries", eList);

                break;

            case USER:
                model.addAttribute(
                        "entries",
                        new EntryList(entryRepository.findByAccountId(Long
                                .valueOf(id))));

                break;

            case TAG:

                // model.addAttribute("entries", new EntryList(tagRepository
                // .findByName(id).getEntries()));

                model.addAttribute(
                        "entries",
                        new EntryList(entryRepository
                                .findDistinctByTagsNameOrVersionsTagsName(id, id)));

                break;

            default:
                break;
        }

        return "entries";
    }

    @RequestMapping(value = "/entries/explore", method = RequestMethod.GET)
    public String showAllEntries(final HttpServletRequest request,
            final Principal currentUser, final Model model) {

        final EntriesFilterDataObject filter = new EntriesFilterDataObject();
        model.addAttribute("entriesFilterDataObject", filter);

        List<MasterEntry> list = (List<MasterEntry>) entryRepository.findAll();
        EntryList eList = new EntryList();
        eList.addAll(list);
        model.addAttribute("entries", eList);

        return "entries";
    }

    @RequestMapping(value = "/user/entry", method = RequestMethod.GET)
    public String showEntry(final HttpServletRequest request,
            final Principal currentUser, final Model model) {

        return "entrydetails";
    }

    @RequestMapping(value = ENTRY_DETAILS_LINK, method = RequestMethod.GET)
    public String showEntryDetails(@RequestParam("id") Long id, ModelMap model,
            Principal currentUser) {
        MasterEntry entry = this.entryRepository.findOne(id);
        entry.getStatistics().addViews(1);
        this.entryRepository.save(entry);

        model.addAttribute("entry", entry);
        return "entrydetails";
    }

    @RequestMapping(value = "/entry/details/raw", method = RequestMethod.GET)
    public String showEntryCodeRaw(@RequestParam("id") Long id,
            @RequestParam("type") String type, ModelMap model,
            Principal currentUser) {

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

    @RequestMapping(value = "/proposal/details", method = RequestMethod.GET)
    public String showProposalDetails(@RequestParam("id") Long id,
            ModelMap model, Principal currentUser) {
        Entry entry = this.proposalEntryRepository.findOne(id);

        model.addAttribute("entry", entry);
        return "proposaldetails";
    }

    @Secured({ "USER" })
    @RequestMapping(value = "user/entry/edit", method = RequestMethod.GET)
    public String showEntryEditPage(@RequestParam("id") Long id,
            ModelMap model, Principal currentUser) {

        fillupModelForEntryEdit(model, currentUser, id, EntryEditMode.MASTER);
        return "entryedit";
    }

    @Secured({ "USER" })
    @RequestMapping(value = "user/entry/propose", method = RequestMethod.GET)
    public String showEntryEditPageForNewPropose(@RequestParam("id") Long id,
            ModelMap model, Principal currentUser) {

        fillupModelForEntryEdit(model, currentUser, id, EntryEditMode.PROPOSAL);
        return "entryedit";
    }

    private void fillupModelForEntryEdit(ModelMap model, Principal currentUser,
            Long id, EntryEditMode editMode) {
        String secUser = currentUser.getName();
        UserProfile profile = this.userProfileRepository
                .findByUsername(secUser);

        Entry entry = this.entryRepository.findOne(id);

        // if (entry.getAccount().getId() != profile.getAccount().getId()) {
        // throw new BadCredentialsException(
        // "na na na na, wer wird denn hier!!!");
        // }

        EntryDataObject entryDataObject = new EntryDataObject();
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

        String[] tags = resolveTagsAsStrings((List<Tag>) this.tagRepository
                .findAll());
        model.addAttribute("tags", tags);

        model.addAttribute("entryDataObject", entryDataObject);
        model.addAttribute("entry", entry);
        model.addAttribute("editMode", editMode.name());
        model.addAttribute("languages",
                WeFactorValues.ProgrammingLanguage.values());

        model.addAttribute("groups",
                this.groupRepository.findByMembers(profile.getAccount()));

    }

    @Secured({ "USER" })
    @RequestMapping(value = "user/entry/remove", method = RequestMethod.GET)
    public String deleteEntryPage(@RequestParam("id") Long id, ModelMap model,
            Principal currentUser) {

        this.entryRepository.delete(id);

        return "forward:/";
    }

    @RequestMapping(value = "/proposal/accept", method = RequestMethod.GET)
    public String acceptProposal(@RequestParam("id") Long id, ModelMap model,
            Principal currentUser) {

        ProposalEntry pe = this.proposalEntryRepository.findOne(id);

        UserProfile up = this.userProfileRepository.findByUsername(currentUser
                .getName());
        Account account = up.getAccount();

        MasterEntry me = pe.getMasterOfProposal();
        VersionEntry ve = new VersionEntry(pe);
        pe.setStatus(Status.ACCEPTED.name());

        this.proposalEntryRepository.save(pe);
        this.versionEntryRepository.save(ve);
        this.entryRepository.save(me);

        ObjectIdentification oid = DataUtils.createObjectIdentification(me,
                Entry.class.getSimpleName());
        TimelineEvent event = new TimelineEvent(new Date(), account,
                pe.getAccount(), EventType.PROPOSAL_ACCEPTED, oid);

        this.timelineEventRepository.save(event);

        return "forward:/entry/details?id="
                + String.valueOf(pe.getMasterOfProposal().getId());
    }

    @RequestMapping(value = "/proposal/reject", method = RequestMethod.GET)
    public String rejectProposal(@RequestParam("id") Long id, ModelMap model,
            Principal currentUser) {

        UserProfile up = this.userProfileRepository.findByUsername(currentUser
                .getName());
        Account account = up.getAccount();

        ProposalEntry pe = this.proposalEntryRepository.findOne(id);

        pe.setStatus(Status.REJECTED.name());

        this.proposalEntryRepository.save(pe);

        ObjectIdentification oid = DataUtils.createObjectIdentification(
                pe.getMasterOfProposal(), Entry.class.getSimpleName());
        TimelineEvent event = new TimelineEvent(new Date(), account,
                pe.getAccount(), EventType.PROPOSAL_REJECTED, oid);

        this.timelineEventRepository.save(event);

        return "forward:/entry/details?id="
                + String.valueOf(pe.getMasterOfProposal().getId());
    }

    @RequestMapping(value = "/user/entry/add", method = RequestMethod.GET)
    public String showAddEntryPage(final HttpServletRequest request,
            final Principal currentUser, final Model model) {

        UserProfile profile = this.userProfileRepository
                .findByUsername(currentUser.getName());

        EntryDataObject ed = new EntryDataObject();
        ed.setEditMode(EntryEditMode.MASTER.name());

        model.addAttribute("entryDataObject", ed);
        model.addAttribute("languages",
                WeFactorValues.ProgrammingLanguage.values());

        model.addAttribute("groups",
                this.groupRepository.findByMembers(profile.getAccount()));

        model.addAttribute("editMode", EntryEditMode.MASTER.name());
        String[] tags = resolveTagsAsStrings((List<Tag>) this.tagRepository
                .findAll());
        model.addAttribute("tags", tags);

        return "entryedit";
    }

    private String[] resolveTagsAsStrings(List<Tag> tags) {
        String[] retVal = new String[tags.size()];
        for (int i = 0; i < tags.size(); i++) {
            retVal[i] = tags.get(i).getName();
        }

        return retVal;
    }

    @RequestMapping(value = "/user/entry/save", method = RequestMethod.POST)
    public String submitEntryForm(@Valid EntryDataObject entryDataObject,
            BindingResult result, ModelMap m, Principal currentUser) {
        if (result.hasErrors()) {
            m.addAttribute("languages",
                    WeFactorValues.ProgrammingLanguage.values());
            return "entryedit";
        }

        Entry toSave = saveEntry(entryDataObject, currentUser);

        // store event if entry in group
        if (toSave.getGroup() != null) {
            ObjectIdentification oid = DataUtils.createObjectIdentification(
                    toSave, Entry.class.getSimpleName());
            TimelineEvent event = new TimelineEvent(new Date(),
                    toSave.getAccount(), toSave.getGroup(),
                    EventType.MADE_ENTRY, oid);
            this.timelineEventRepository.save(event);
        }

        return showEntryDetails(toSave.getId(), m, currentUser);
    }

    private Entry saveEntry(EntryDataObject entryDataObject,
            Principal currentUser) {

        Entry retVal = null;

        switch (EntryEditMode.valueOf(entryDataObject.getEditMode())) {
            case MASTER:
                MasterEntry toSave = saveAsMasterEntry(entryDataObject,
                        currentUser);
                retVal = toSave;

                break;

            case PROPOSAL:
                MasterEntry entryWithProposal = saveAsProposalEntry(
                        entryDataObject, currentUser);
                retVal = entryWithProposal;

                break;

            default:
                break;
        }

        return retVal;
    }

    private MasterEntry saveAsProposalEntry(EntryDataObject entryDataObject,
            Principal currentUser) {
        MasterEntry toSave;
        if (entryDataObject.getId() != null) {
            toSave = this.entryRepository.findOne(entryDataObject.getId());

        } else {
            throw new IllegalArgumentException();
        }

        String secUser = currentUser.getName();
        UserProfile profile = this.userProfileRepository
                .findByUsername(secUser);

        ProposalEntry pe = new ProposalEntry();

        fillupWithPersistedTags(pe, entryDataObject.getTags());
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
        toSave.addProposal(pe);

        toSave = this.entryRepository.save(toSave);

        ObjectIdentification oid = DataUtils.createObjectIdentification(
                pe.getMasterOfProposal(), Entry.class.getSimpleName());
        TimelineEvent event = new TimelineEvent(new Date(),
                profile.getAccount(), pe.getMasterOfProposal().getAccount(),
                EventType.MADE_PROPOSAL, oid);
        this.timelineEventRepository.save(event);

        return toSave;
    }

    private MasterEntry saveAsMasterEntry(EntryDataObject entryDataObject,
            Principal currentUser) {
        MasterEntry toSave;
        if (entryDataObject.getId() != null) {
            toSave = this.entryRepository.findOne(entryDataObject.getId());

        } else {
            toSave = new MasterEntry();
        }

        String secUser = currentUser.getName();
        UserProfile profile = this.userProfileRepository
                .findByUsername(secUser);

        fillupWithPersistedTags(toSave, entryDataObject.getTags());

        toSave.setEntryCodeText(entryDataObject.getCode());
        toSave.setLanguage(entryDataObject.getLanguage());
        toSave.setTeaser(entryDataObject.getTeaser());
        toSave.setEntryDate(new Date());
        toSave.setEntryDescription(entryDataObject.getDescription());
        toSave.setName(entryDataObject.getTitle());
        toSave.setAccount(profile.getAccount());
        toSave.setChanges(entryDataObject.getChanges());

        if (entryDataObject.getGroup() != null
                && !entryDataObject.getGroup().isEmpty()) {
            toSave.setGroup(this.groupRepository.findOne(Long
                    .valueOf(entryDataObject.getGroup())));
        } else {
            toSave.setGroup(null);
        }

        toSave = this.entryRepository.save(toSave);
        return toSave;
    }

    private void fillupWithPersistedTags(Entry toSave, String[] tags) {

        Set<String> newTags = new HashSet<String>();
        for (int i = 0; i < tags.length; i++) {
            boolean found = false;
            for (Tag t : toSave.getTags()) {
                if (t.getName().equals(tags[i])) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                newTags.add(tags[i]);
            }
        }

        for (String s : newTags) {
            Tag tag = this.tagRepository.findByName(s);
            if (tag == null) {
                tag = new Tag(s);
                tag = this.tagRepository.save(tag);
            }
            toSave.addTag(tag);
        }

    }

    @RequestMapping(value = "/entryajax/{type}/{id}")
    public @ResponseBody Entry getEntry(@PathVariable String type,
            @PathVariable Long id) {

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

    @RequestMapping(value = "/rating/save/{type}/{id}/{rating}")
    public @ResponseBody Entry saveRating(@PathVariable String type,
            @PathVariable Long id, @PathVariable Integer rating,
            Principal currentUser) {

        UserProfile up = this.userProfileRepository.findByUsername(currentUser
                .getName());
        Account account = up.getAccount();

        Entry entry = null;
        EntryRating entryRating = new EntryRating();
        entryRating.setValue(rating);
        entryRating.setAccount(account);

        if (MasterEntry.class.getSimpleName().equals(type)) {
            MasterEntry me = this.entryRepository.findOne(id);
            me.addRating(entryRating);
            this.entryRatingRepository.save(entryRating);
            this.entryRepository.save(me);
            entry = me;

        } else if (VersionEntry.class.getSimpleName().equals(type)) {
            VersionEntry ve = this.versionEntryRepository.findOne(id);
            ve.addRating(entryRating);
            this.entryRatingRepository.save(entryRating);
            this.versionEntryRepository.save(ve);
            entry = ve;

        }
        return entry;

    }

}
