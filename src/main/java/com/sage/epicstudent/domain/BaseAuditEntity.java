package com.sage.epicstudent.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@MappedSuperclass
public abstract class BaseAuditEntity {

    @Column(name = "created_time", nullable = false)
    public Date createdTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified_time", nullable = false)
    public Date lastModifiedTime;

    @PrePersist
    protected void onCreate() {
        lastModifiedTime = createdTime = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        lastModifiedTime = new Date();
    }
}

