package org.path.lab.entity;

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
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Base implements Serializable {
    @CreatedBy
    protected String createdBy;
    @CreatedDate
    protected LocalDateTime createdDate;
    @LastModifiedBy
    protected String modifiedBy;
    @LastModifiedDate
    protected LocalDateTime modifiedDate;
    @Column(unique = true, updatable = false, name = "uuid_v2")
    protected String uuid;
    @Column(columnDefinition = "tinyint(1) default 0")
    protected Boolean isDeleted = false;
    @ManyToOne(cascade = CascadeType.DETACH)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "status_id")
    private Status status;

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
