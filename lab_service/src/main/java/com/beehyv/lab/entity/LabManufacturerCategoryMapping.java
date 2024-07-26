package com.beehyv.lab.entity;
import lombok.*;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name="lab_manufacturer_category")
@Entity
@SQLDelete(sql = "UPDATE lab_manufacturer_category SET is_deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted is null or is_deleted <> true")
public class  LabManufacturerCategoryMapping  extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long categoryId;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "lab_id")
    private Lab lab;

    private Long manufacturerId;
}