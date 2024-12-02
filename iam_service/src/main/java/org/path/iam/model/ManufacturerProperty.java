package org.path.iam.model;

import org.path.iam.service.EncryptionService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Slf4j
@SQLDelete(sql = "UPDATE manufacturer_property SET is_deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted is null or is_deleted <> true")
public class ManufacturerProperty extends Base {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String value;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "batch_id")
    private Manufacturer manufacturer;

    public ManufacturerProperty(Long id) {
        this.id = id;
    }

    public void setValue(String value) {
        try {
            this.value = (String) EncryptionService.encryptField(name, value);
        } catch (Exception e) {
            throw new RuntimeException("Failed to encrypt " + name, e);
        }
    }
    public String getValue(){
        try{
            return (String) EncryptionService.decryptField(name, this.value);
        }
        catch (Exception e){
            log.info("Failed to decrypt " + name, e);
            return null;
        }
    }
}
