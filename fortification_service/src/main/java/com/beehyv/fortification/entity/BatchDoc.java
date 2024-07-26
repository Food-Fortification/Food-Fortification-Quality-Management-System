package com.beehyv.fortification.entity;

import lombok.*;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE batch_doc SET is_deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted is null or is_deleted <> true")
public class BatchDoc extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "batch_id")
    private Batch batch;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "category_doc_id")
    private CategoryDoc categoryDoc;
    private String name;
    private String path;
    @Temporal(TemporalType.DATE)
    private Date expiry;

    public BatchDoc(Long id) {
        this.id = id;
    }
}

