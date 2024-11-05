package com.beehyv.iam.model;

import java.time.LocalDateTime;
import com.beehyv.iam.helper.EncryptionHelper;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@SQLDelete(sql = "UPDATE user SET is_deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted is null or is_deleted <> true")
public class User extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String password;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "manufacturer_id",referencedColumnName = "id")
    private Manufacturer manufacturer;
    private Date lastLogin;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "lab_user_id",referencedColumnName = "id")
    private LabUsers labUsers;
    @OneToMany(mappedBy = "user",  cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserRoleCategory> roleCategories;

    @OneToMany(mappedBy = "user",  cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<NotificationUserToken> notificationUserTokens;

    private LocalDateTime notificationLastSeenTime;

    public User(Long id){
        this.id = id;
    }

    public void setUserName(String userName){
        try {
            this.userName = EncryptionHelper.encrypt(userName);
        }
        catch (Exception e){
            log.info("Failed to encrypt username", e);
        }
    }
    public String getUserName(){
        try{
            return EncryptionHelper.decrypt(this.userName);
        }
        catch (Exception e){
            log.info("Failed to decrypt username", e);
            return null;
        }
    }
    public void setEmail(String email){
        try {
            this.email = EncryptionHelper.encrypt(email);
        }
        catch (Exception e){
            log.info("Failed to encrypt email", e);
        }
    }
    public String getEmail(){
        try{
            return EncryptionHelper.decrypt(this.email);
        }
        catch (Exception e){
            log.info("Failed to decrypt email", e);
            return null;
        }
    }
    public void setFirstName(String firstName){
        try {
            this.firstName = EncryptionHelper.encrypt(firstName);
        }
        catch (Exception e){
            log.info("Failed to encrypt firstName", e);
        }
    }
    public String getFirstName(){
        try{
            return EncryptionHelper.decrypt(this.firstName);
        }
        catch (Exception e){
            log.info("Failed to decrypt firstName", e);
            return null;
        }
    }
    public void setLastName(String lastName){
        try {
            this.lastName = EncryptionHelper.encrypt(lastName);
        }
        catch (Exception e){
            log.info("Failed to encrypt lastName", e);
        }
    }
    public String getLastName(){
        try{
            return EncryptionHelper.decrypt(this.lastName);
        }
        catch (Exception e){
            log.info("Failed to decrypt lastName", e);
            return null;
        }
    }

}
