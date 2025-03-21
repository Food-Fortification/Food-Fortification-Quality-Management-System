package org.path.iam.model;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Base implements Serializable {
    @CreatedBy
    @Column(updatable = false)
    protected String createdBy;
    @CreatedDate
    @Column(updatable = false)
    protected LocalDateTime createdDate;
    @LastModifiedBy
    @Column(insertable = false)
    protected String modifiedBy;
    @LastModifiedDate
    @Column(insertable = false)
    protected LocalDateTime modifiedDate;
    @ManyToOne(cascade = CascadeType.DETACH)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "status_id")
    protected Status status;

    @Column(unique = true,nullable = false, updatable = false)
    protected String uuid;

    @Column(columnDefinition = "boolean default false")
    protected Boolean isDeleted;

    @PreRemove
    public void deleteEntity() {
        this.isDeleted = true;
    }

    @PrePersist
    public void initEntity() {
        this.setUuid(UUID.randomUUID().toString());
        this.isDeleted = false;
    }
}
