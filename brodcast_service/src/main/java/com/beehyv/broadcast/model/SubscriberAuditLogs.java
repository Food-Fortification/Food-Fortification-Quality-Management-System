package com.beehyv.broadcast.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE subscriber_audit_logs SET is_deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted is null or is_deleted <> true")
public class SubscriberAuditLogs extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "subscriber_id")
    private Subscriber subscriber;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(name = "response", columnDefinition = "TEXT")
    private String response;

    private Integer responseCode;
}
