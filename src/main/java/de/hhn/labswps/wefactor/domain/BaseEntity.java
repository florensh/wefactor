package de.hhn.labswps.wefactor.domain;

import java.util.Date;

import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
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

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @CreatedDate
    private Date createdDate = new Date();

    @LastModifiedDate
    private Date lastModifiedDate = new Date();

    @CreatedBy
    private Account createdBy;

    @LastModifiedBy
    private Account lastModifiedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @NotNull
    public Account getCreatedBy() {
        return createdBy;
    }

    @NotNull
    @Type(type = "timestamp")
    public Date getCreatedDate() {
        return createdDate;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @NotNull
    public Account getLastModifiedBy() {
        return lastModifiedBy;
    }

    @NotNull
    @Type(type = "timestamp")
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setCreatedBy(Account createdBy) {
        this.createdBy = createdBy;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setLastModifiedBy(Account lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

}
