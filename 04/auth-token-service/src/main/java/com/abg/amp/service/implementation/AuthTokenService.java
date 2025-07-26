package com.abg.amp.service.implementation;

import com.abg.amp.entity.AuthToken;
import com.abg.amp.model.ResponseInfo;
import com.abg.amp.model.copybooks.AccessToken;
import com.abg.amp.repository.AuthTokenRepository;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.AccessibleObject;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

//@Service
public class AuthTokenService {
    private static final Logger log = LoggerFactory.getLogger(AuthTokenService.class);
    @Autowired
    RestTemplateCalls restTemplateCalls;
    @Autowired
    private AuthTokenRepository authTokenRepository;
    private AuthToken authToken = new AuthToken();
    private int numberOfTime = 0;
    private int expiresIn;

    @Retryable(value = Exception.class, maxAttempts = 2, backoff = @Backoff(delay = 2000))
    @Scheduled(fixedDelayString = "#{@authTokenService.getNextExecutionDelay()}")
    @Transactional
    public void fetchAuthToken() throws Exception {
        log.info("Number of Times: " + numberOfTime);
        AccessToken access = restTemplateCalls.callOAuth2();
        if(null != access) {
            String accessToken = access.getAccessToken();
            expiresIn = Integer.parseInt(access.getExpiresIn());
            Optional<AuthToken> optionalAuthToken = authTokenRepository.findTopByOrderByUpdateTimeDesc();
            //AuthToken authToken;
            if (optionalAuthToken.isPresent()) {
                authToken = optionalAuthToken.get();
                LocalDateTime oldTime = authToken.getUpdateTime();
                authToken.setUpdateTime(LocalDateTime.now());
                authToken.setAccessToken(access.getAccessToken());
                authToken.setExpiresIn(expiresIn);
                int update = authTokenRepository.updateExistingRow(oldTime, authToken.getUpdateTime(), accessToken, expiresIn);
                numberOfTime++;
            } else {
                authToken = new AuthToken();
                authToken.setUpdateTime(LocalDateTime.now());
                authToken.setTokenType(access.getTokenType());
                authToken.setAccessToken(accessToken);
                authToken.setExpiresIn(expiresIn);
                authToken.setScope(access.getScope());
                authTokenRepository.save(authToken);
                numberOfTime++;
            }
        }
    }

    public long getNextExecutionDelay(){
        log.info("****************INSIDE NEXT EXECUTION DELAY**************");
        /*AuthToken latestAuthToken = authTokenRepository.findAll().stream()
                .max(Comparator.comparing(AuthToken::getUpdateTime))
                .orElse(null);

        if (latestAuthToken != null) {
            // Returns delay in milliseconds
            return latestAuthToken.getExpiresIn();
        }*/
        //currentTime - (updateTime + expiresIn)
        Optional<AuthToken> latestTokenOpt = authTokenRepository.findTopByOrderByUpdateTimeDesc();
        if (latestTokenOpt.isPresent()) {
            LocalDateTime expiryTime = latestTokenOpt.get().getUpdateTime().plus(latestTokenOpt.get().getExpiresIn(), ChronoUnit.MILLIS);

            // Calculate difference between current time and expiryTime in seconds
            long newTime = Math.abs(ChronoUnit.MILLIS.between(LocalDateTime.now(), expiryTime));
            latestTokenOpt.get().setExpiresIn((int)newTime);
            AuthToken latestToken = latestTokenOpt.get();
            log.info("****** NEW EXPIRES IN ****** ---> " + latestToken.getExpiresIn());
            // Returns delay in milliseconds
            return latestToken.getExpiresIn();
        }
        return 15 * 1000L;
    }

}
