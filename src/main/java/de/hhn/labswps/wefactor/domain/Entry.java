package de.hhn.labswps.wefactor.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class Entry.
 * 
 * @author Patrick Wohlgemuth
 */
@Entity
@Table(name = "entry")
@Where(clause = "inactive = 'N'")
// @SQLDelete(sql = "UPDATE entry set inactive = 'Y' WHERE Id = ?")
@JsonIgnoreProperties({ "id", "softDeleted", "Account", "createdBy",
        "lastModifiedBy" })
@DiscriminatorColumn(name = "ENTRY_TYPE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Entry extends BaseSoftDeletableEntity implements
        Comparable<Entry> {

    private Account account;

    /** The entry code text. */
    private String entryCodeText = null;

    private String name = null;

    /** The entry date. */
    private Date entryDate;

    private String language;

    private String teaser;

    private EntryStatistics statistics = new EntryStatistics();

    @Embedded
    public EntryStatistics getStatistics() {
        return statistics;
    }

    public void setStatistics(EntryStatistics statistics) {
        this.statistics = statistics;
    }

    private String changes;

    public String getChanges() {
        return changes;
    }

    public void setChanges(String changes) {
        this.changes = changes;
    }

    /** The entry description. */
    private String entryDescription = null;

    @ManyToOne
    @JoinColumn(name = "myAccount", nullable = false)
    public Account getAccount() {
        return account;
    }

    @Transient
    public String getDescriptionShort() {
        int maxLength = 100;
        if (this.entryDescription != null) {
            if (this.entryDescription.length() > maxLength) {
                return this.entryDescription.substring(0, maxLength) + "...";

            } else {
                return this.entryDescription;
            }
        } else {
            return "";
        }
    }

    /**
     * Gets the entry code text.
     *
     * @return the entry code text
     */
    @Lob
    public String getEntryCodeText() {
        return this.entryCodeText;
    }

    /**
     * Gets the entry date.
     *
     * @return the entry date
     */
    public Date getEntryDate() {
        return this.entryDate;
    }

    @Transient
    public String getEntryDateAsString() {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        return df.format(this.entryDate);
    }

    /**
     * Gets the entry description.
     *
     * @return the entry description
     */
    @Lob
    public String getEntryDescription() {
        return this.entryDescription;
    }

    public String getLanguage() {
        return language;
    }

    public String getName() {
        return name;
    }

    public String getTeaser() {
        return teaser;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * Sets the entry code text.
     *
     * @param entryCodeTextParam
     *            the new entry code text
     */
    public void setEntryCodeText(final String entryCodeTextParam) {
        this.entryCodeText = entryCodeTextParam;
    }

    /**
     * Sets the entry date.
     *
     * @param entyDate
     *            the new entry date
     */
    public void setEntryDate(final Date entyDate) {
        this.entryDate = entyDate;
    }

    /**
     * Sets the entry description.
     *
     * @param entryDescriptionParam
     *            the new entry description
     */
    public void setEntryDescription(final String entryDescriptionParam) {
        this.entryDescription = entryDescriptionParam;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTeaser(String teaser) {
        this.teaser = teaser;
    }

    @Transient
    public String getType() {
        return this.getClass().getSimpleName();
    }

    @Transient
    public abstract List<Entry> getOrderedVersions();

    @Transient
    public String[] getOrderedVersionIds() {
        List<Entry> orderedVersions = getOrderedVersions();
        String[] ids = new String[orderedVersions.size()];

        for (int i = 0; i < orderedVersions.size(); i++) {
            ids[i] = orderedVersions.get(i).getId().toString();
        }

        return ids;
    }

    @Transient
    public String[] getOrderedVersionTypes() {
        List<Entry> orderedVersions = getOrderedVersions();
        String[] ids = new String[orderedVersions.size()];

        for (int i = 0; i < orderedVersions.size(); i++) {
            ids[i] = orderedVersions.get(i).getClass().getSimpleName();
        }

        return ids;
    }

    private Set<EntryRating> ratings = new HashSet<EntryRating>();

    @OneToMany(mappedBy = "entry")
    public Set<EntryRating> getRatings() {
        return ratings;
    }

    public void setRatings(Set<EntryRating> ratings) {
        this.ratings = ratings;
    }

    public void addRating(EntryRating rating) {
        this.ratings.add(rating);
        rating.setEntry(this);
    }

    @Transient
    @JsonProperty("rankingValue")
    public Double getRankingValue() {

        if (this.ratings.isEmpty()) {
            return new Double("0.0");
        }

        Double d = new Double("0.0");
        int a = 0;
        for (EntryRating ra : ratings) {
            d = d + ra.getValue();
            a++;
        }

        return d / a;
    }

    @Transient
    @JsonProperty("rankingCount")
    public int getRatingCount() {
        return this.ratings.size();
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Transient
    public int getVersionnumber() {
        return getParent().getOrderedVersions().size()
                - getParent().getOrderedVersions().indexOf(this);
    }

    @Transient
    public abstract Entry getParent();

    @Override
    public int compareTo(Entry o) {
        if (this.entryDate.before(o.entryDate)) {
            return 1;
        } else if (this.entryDate.after(o.entryDate)) {
            return -1;
        } else {
            return 0;
        }
    }

    @Transient
    public abstract String getVersionDisplayText();

}
