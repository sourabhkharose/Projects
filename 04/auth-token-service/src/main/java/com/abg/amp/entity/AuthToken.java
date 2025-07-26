package com.abg.amp.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "auth_token")
public class AuthToken {
    //private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "rec_no")
    int recordNumber;
    @Column(name = "update_time")
    private LocalDateTime updateTime;
    @Column(name = "token_type")
    private String tokenType;
    @Column(nullable = false, name = "expires_in")
    private int expiresIn;
    @Column(nullable = false, name = "access_token")
    private String AccessToken;
    @Column(name = "scope")
    private String scope;

    public int getRecordNumber() {
        return recordNumber;
    }

    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getAccessToken() {
        return AccessToken;
    }

    public void setAccessToken(String accessToken) {
        AccessToken = accessToken;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public AuthToken(){

    }
}
