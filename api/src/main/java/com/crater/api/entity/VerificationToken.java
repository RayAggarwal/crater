package com.crater.api.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Entity(name = "verificationToken")
public class VerificationToken {

    @Id
    @Column(columnDefinition = "int4")
    private Long id;

    private String token;

    private Date expiryTime;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "id", columnDefinition = "int4")
    private Owner owner;

    private Date creationDate;

    public static final Long MAX_EXISTANCE = TimeUnit.DAYS.toMillis(7);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Date expiryTime) {
        this.expiryTime = expiryTime;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
