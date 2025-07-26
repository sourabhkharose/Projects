package com.abg.amp.model;

import com.abg.amp.model.copybooks.AccessToken;

public class ResponseInfo {
    private AccessToken accessToken = null;
    public AccessToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }
}
