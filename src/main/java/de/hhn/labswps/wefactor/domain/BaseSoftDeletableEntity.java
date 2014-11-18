package de.hhn.labswps.wefactor.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

@MappedSuperclass
@Where(clause = "inactive = 'N'")
public class BaseSoftDeletableEntity extends BaseEntity {

    private boolean isSoftDeleted;

    @Type(type = "yes_no")
    @Column(name = "Inactive", nullable = false, length = 1)
    public boolean isSoftDeleted() {
        return isSoftDeleted;
    }

    public void setSoftDeleted(boolean isSoftDeleted) {
        this.isSoftDeleted = isSoftDeleted;
    }

}