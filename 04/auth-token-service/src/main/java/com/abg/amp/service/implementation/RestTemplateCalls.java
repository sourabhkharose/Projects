package com.abg.amp.service.implementation;

import com.abg.amp.configuration.CacheConfiguration;
import com.abg.amp.constants.Constants;
import com.abg.amp.model.copybooks.AccessToken;
import com.abg.amp.utils.StringUtils;
/*import com.amazonaws.regions.Regions;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;*/
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Service
public class RestTemplateCalls {
    private static final Logger log = LoggerFactory.getLogger(RestTemplateCalls.class);
    @Autowired
    CacheConfiguration cacheToken;
    @Value("${spring.okta.token.amp.secretsmanager.secretName}")
    private String secretNameOktaToken;
    @Value("${oktaTokenUrl}")
    private String oktaTokenUrl;
    private String clientId = "";
    private String clientSecret = "";
    private int accessTokenCallNo = 0;

    public AccessToken callOAuth2() throws Exception {
        log.warn("callOAuth2 service for access token");
        log.warn("calling OAuth2.....");

        AccessToken access = new AccessToken();


        String urlParameters = "";
        /*AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard().withRegion(Regions.US_EAST_1).build();*/
        /*GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest().withSecretId(this.secretNameOktaToken);
        GetSecretValueResult getSecretValueResponse = client.getSecretValue(getSecretValueRequest);*/

        //if(null!=getSecretValueResponse) {
        boolean run = true;
        if(run){
            access.setScope("ABG");
            access.setAccessToken("abg-12h567-sjuy8");
            access.setExpiresIn("60000");
            access.setTokenType("new");
            return access;
        }
        log.info("Secret Value Response is Okay");
        String secret = "{\n" +
                "  \"client_id\": \"sourabh\",\n" +
                "  \"client_secret\": \"abcdef\",\n" +
                "  \"addressLine1\": \"\",\n" +
                "  \"addressLine2\": \"\",\n" +
                "  \"addressLine3\": \"\",\n" +
                "  \"emailAddress\": \"\"}"; // getSecretValueResponse.getSecretString();

        if(!StringUtils.isEmpty(secret)) {
            log.info("Secret string is OKAY");

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode secretsJson = objectMapper.readTree(secret);

            if(null!=secretsJson) {

                clientId = secretsJson.get("client_id").textValue();
                clientSecret = secretsJson.get("client_secret").textValue();
                log.info("Client ID: "+clientId);
                log.info("Client Secret: "+clientSecret);

                urlParameters = "client_id="+clientId+"&client_secret="+clientSecret+"&grant_type=client_credentials";

                URL resourceurl = new URL(oktaTokenUrl);

                byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
                int postDataLength = postData.length;
                HttpsURLConnection conn = (HttpsURLConnection) resourceurl.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("charset", "utf-8");
                conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
                conn.setDoOutput(true);
                conn.setConnectTimeout(60000);
                log.warn("before read timeout access ");
                conn.setReadTimeout(30000);
                log.warn("after read timeout... access");

                try(OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream())){
                    log.warn("before call json : " + urlParameters);
                    wr.write(urlParameters);
                    wr.flush();

                    String jsonResponse;
                    try(InputStream response = conn.getInputStream()){
                        jsonResponse = StringUtils.convertStreamToString(response);
                        access = StringUtils.createAccessToken(jsonResponse);
                        cacheToken.CacheAccessUtil("accessToken",access, Long.valueOf(access.getExpiresIn()));
                        Constants.time = Integer.valueOf(access.getExpiresIn());
                        cacheToken.cache.put("accessToken",access);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("Unsuccessful connection for access token");
                    accessTokenCallNo++;
                    try(InputStream errorResponse = conn.getErrorStream()){
                        String jsonResponse = StringUtils.convertStreamToString(errorResponse);
                        log.error(errorResponse.toString());
                    }
                }
            }
        }
        //}

        return access;
    }
}
