package de.hhn.labswps.wefactor.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
public abstract class Entry extends BaseSoftDeletableEntity {

    private Account account;

    /** The entry code text. */
    private String entryCodeText = null;

    private String name = null;

    /** The entry date. */
    private Date entryDate;

    private String language;

    private String teaser;

    /** The entry description. */
    private String entryDescription = null;

    /** The id. */
    private Long id;

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

    /**
     * Gets the id.
     *
     * @return the id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
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

    /**
     * Sets the id.
     *
     * @param idParam
     *            the new id
     */
    public void setId(final Long idParam) {
        this.id = idParam;
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

}
