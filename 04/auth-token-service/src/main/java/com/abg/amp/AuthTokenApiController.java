package com.abg.amp;

import com.abg.amp.model.copybooks.AccessToken;
import com.abg.amp.service.implementation.tempTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/token/refresh")
public class AuthTokenApiController {
    private static final Logger log = LoggerFactory.getLogger(AuthTokenApiController.class);

    private tempTokenService tempTokenServiceObj;
    private AccessToken accessToken;

    public AuthTokenApiController(@Qualifier("RefreshToken") tempTokenService tempTokenServiceObj, AccessToken accessToken){
        this.tempTokenServiceObj = tempTokenServiceObj;
        this.accessToken = accessToken;
    }

    @GetMapping()
    public ResponseEntity<AccessToken> refreshToken(){
        tempTokenServiceObj.execute();
        return new ResponseEntity<>(accessToken, HttpStatus.OK);
    }
}
