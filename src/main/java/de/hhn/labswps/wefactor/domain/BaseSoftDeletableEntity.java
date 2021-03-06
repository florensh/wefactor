package de.hhn.labswps.wefactor.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

/**
 * Extend from the Class BaseSoftDeletableEntity to make an Entity soft
 * deletable.
 * That means that the object will not be removed from the database if a delete
 * call is done.
 * The object will be flagged as inactive instead.
 */
@MappedSuperclass
@Where(clause = "inactive = 'N'")
public abstract class BaseSoftDeletableEntity extends BaseEntity {

    /** The is soft deleted. */
    private boolean isSoftDeleted;

    /**
     * Checks if is soft deleted.
     *
     * @return true, if is soft deleted
     */
    @Type(type = "yes_no")
    @Column(name = "Inactive", nullable = false, length = 1)
    public boolean isSoftDeleted() {
        return this.isSoftDeleted;
    }

    /**
     * Sets the soft deleted.
     *
     * @param isSoftDeleted
     *            the new soft deleted
     */
    public void setSoftDeleted(final boolean isSoftDeleted) {
        this.isSoftDeleted = isSoftDeleted;
    }

}