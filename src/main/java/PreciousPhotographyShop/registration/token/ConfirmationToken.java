package PreciousPhotographyShop.registration.token;

import PreciousPhotographyShop.users.UserEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ConfirmationToken {

    @Id
    @Column(name="user_id")
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiredAt;

    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "User_Id"
    )
    private UserEntity userEntity;


    public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiredAt, UserEntity userEntity){
        this.token = token;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
        this.userEntity = userEntity;
    }

    public ConfirmationToken() {

    }

    /*
        Getters
     */
    public LocalDateTime getConfirmedAt() {
        return confirmedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }

    public String getId() {
        return id;
    }

    public String getToken() {
        return token;
    }


    /*
        Setters
     */
    public void setConfirmedAt(LocalDateTime confirmedAt) {
        this.confirmedAt = confirmedAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setExpiredAt(LocalDateTime expiredAt) {
        this.expiredAt = expiredAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setToken(String token) {
        this.token = token;
    }

}


