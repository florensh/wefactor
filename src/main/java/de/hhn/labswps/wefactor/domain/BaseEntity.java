package de.hhn.labswps.wefactor.domain;

import java.util.Date;

import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * The Class BaseEntity.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    /** The created by. */
    @CreatedBy
    private Account createdBy;

    /** The created date. */
    @CreatedDate
    private Date createdDate = new Date();

    /** The id. */
    private Long id;

    /** The last modified by. */
    @LastModifiedBy
    private Account lastModifiedBy;

    /** The last modified date. */
    @LastModifiedDate
    private Date lastModifiedDate = new Date();

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final BaseEntity other = (BaseEntity) obj;
        if (this.id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    /**
     * Gets the created by.
     *
     * @return the created by
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @NotNull
    public Account getCreatedBy() {
        return this.createdBy;
    }

    /**
     * Gets the created date.
     *
     * @return the created date
     */
    @NotNull
    @Type(type = "timestamp")
    public Date getCreatedDate() {
        return this.createdDate;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return this.id;
    }

    /**
     * Gets the last modified by.
     *
     * @return the last modified by
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @NotNull
    public Account getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    /**
     * Gets the last modified date.
     *
     * @return the last modified date
     */
    @NotNull
    @Type(type = "timestamp")
    public Date getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result)
                + ((this.id == null) ? 0 : this.id.hashCode());
        return result;
    }

    /**
     * Sets the created by.
     *
     * @param createdBy
     *            the new created by
     */
    public void setCreatedBy(final Account createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Sets the created date.
     *
     * @param createdDate
     *            the new created date
     */
    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
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

    /**
     * Sets the last modified by.
     *
     * @param lastModifiedBy
     *            the new last modified by
     */
    public void setLastModifiedBy(final Account lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    /**
     * Sets the last modified date.
     *
     * @param lastModifiedDate
     *            the new last modified date
     */
    public void setLastModifiedDate(final Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

}
