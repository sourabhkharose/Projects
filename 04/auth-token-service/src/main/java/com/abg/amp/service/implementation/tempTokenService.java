package com.abg.amp.service.implementation;

import com.abg.amp.entity.AuthToken;
import com.abg.amp.model.copybooks.AccessToken;
import com.abg.amp.repository.AuthTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

@Service("RefreshToken")
public class tempTokenService {
    private static final Logger log = LoggerFactory.getLogger(tempTokenService.class);
    @Autowired
    RestTemplateCalls restTemplateCalls;
    @Autowired
    private AuthTokenRepository authTokenRepository;
    private AuthToken authToken = new AuthToken();
    private int numberOfTime = 1;
    private int expiresIn;
    private AccessToken access;
    public Optional<AuthToken> latestTokenOpt;


    public class RefereshTokenTask extends TimerTask {
        @Override
        public void run() {
            try {
                log.info("********* RUNNING REFRESH TOKEN TASK CLASS *********");
                fetchToken();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public void fetchToken() throws Exception {
            log.info("Number of Times: " + numberOfTime);
            access = restTemplateCalls.callOAuth2();

            if (null != access) {
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

            latestTokenOpt = authTokenRepository.findTopByOrderByUpdateTimeDesc();
            if (latestTokenOpt.isPresent()) {
                LocalDateTime expiryTime = latestTokenOpt.get().getUpdateTime().plus(latestTokenOpt.get().getExpiresIn(), ChronoUnit.MILLIS);

                // Calculate difference between current time and expiryTime in seconds
                long newTime = Math.abs(ChronoUnit.MILLIS.between(LocalDateTime.now(), expiryTime));
                latestTokenOpt.get().setExpiresIn((int)newTime);
                AuthToken latestToken = latestTokenOpt.get();
                log.info("****** NEW EXPIRES IN ****** ---> " + latestToken.getExpiresIn());
                // Returns delay in milliseconds
                //return latestToken.getExpiresIn();
            }
        }

    }
    public void execute() {
        Timer time = new Timer();
        long delay = 0;
        long period = latestTokenOpt.get().getExpiresIn();

        time.scheduleAtFixedRate(new RefereshTokenTask(), delay, period);
    }

}
