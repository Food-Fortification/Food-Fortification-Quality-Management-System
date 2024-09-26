package com.beehyv.iam.model;

import java.time.LocalDateTime;
import com.beehyv.iam.helper.EncryptionHelper;
import lombok.*;
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
@SQLDelete(sql = "UPDATE user SET is_deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted is null or is_deleted <> true")
public class User extends Base{
    @Autowired
    @Transient
    private EncryptionHelper encryptionHelper;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @Email
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
            this.userName = encryptionHelper.encrypt(userName);
        }
        catch (Exception e){
            throw new RuntimeException("Failed to encrypt username", e);
        }
    }
    public String getUserName(){
        try{
            return encryptionHelper.decrypt(this.userName);
        }
        catch (Exception e){
            throw new RuntimeException("Failed to decrypt username", e);
        }
    }
    public void setEmail(String email){
        try {
            this.email = encryptionHelper.encrypt(email);
        }
        catch (Exception e){
            throw new RuntimeException("Failed to encrypt email", e);
        }
    }
    public String getEmail(){
        try{
            return encryptionHelper.decrypt(this.email);
        }
        catch (Exception e){
            throw new RuntimeException("Failed to decrypt email", e);
        }
    }
    public void setFirstName(String firstName){
        try {
            this.firstName = encryptionHelper.encrypt(firstName);
        }
        catch (Exception e){
            throw new RuntimeException("Failed to encrypt firstName", e);
        }
    }
    public String getFirstName(){
        try{
            return encryptionHelper.decrypt(this.firstName);
        }
        catch (Exception e){
            throw new RuntimeException("Failed to decrypt firstName", e);
        }
    }
    public void setLastName(String lastName){
        try {
            this.lastName = encryptionHelper.encrypt(lastName);
        }
        catch (Exception e){
            throw new RuntimeException("Failed to encrypt lastName", e);
        }
    }
    public String getLastName(){
        try{
            return encryptionHelper.decrypt(this.lastName);
        }
        catch (Exception e){
            throw new RuntimeException("Failed to decrypt lastName", e);
        }
    }

}
