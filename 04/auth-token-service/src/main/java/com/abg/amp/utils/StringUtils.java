package com.abg.amp.utils;

import com.abg.amp.model.copybooks.AccessToken;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;

public class StringUtils {
    private static final Logger log = LogManager.getLogger(StringUtils.class);
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String createJson(Object obj) throws JsonGenerationException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.UpperCamelCaseStrategy.UPPER_CAMEL_CASE);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String json = mapper.writeValueAsString(obj);
        return json;
    }

    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            log.error("Error in convertStreamToString  : " + e.getMessage());
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                log.error("Error in convertStreamToString  : " + e.getMessage());
            }
        }
        return sb.toString();
    }

    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static AccessToken createAccessToken(String json) {
        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
        try {
            AccessToken rs = mapper.readValue(json, AccessToken.class);
            return rs;
        } catch (Exception e) {
            log.error("Error in createFDMSResponse  : " + e.getMessage());
        }
        return null;
    }
}
