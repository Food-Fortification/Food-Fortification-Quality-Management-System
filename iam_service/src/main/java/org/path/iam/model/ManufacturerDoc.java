package org.path.iam.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE manufacturer_doc SET is_deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted is null or is_deleted <> true")
public class ManufacturerDoc extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "manufacturer_id",referencedColumnName = "id")
    private Manufacturer manufacturer;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "category_doc_id",referencedColumnName = "id")
    private CategoryDoc categoryDoc;

    private String docName;
    private String docPath;
    private LocalDate docExpiry;

}
