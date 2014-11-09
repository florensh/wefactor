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
    @NotNull
    @Type(type = "timestamp")
    private Date createdDate = new Date();

    @LastModifiedDate
    @NotNull
    @Type(type = "timestamp")
    private Date lastModifiedDate = new Date();

    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @NotNull
    private Account createdBy;

    @LastModifiedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @NotNull
    private Account lastModifiedBy;

}
