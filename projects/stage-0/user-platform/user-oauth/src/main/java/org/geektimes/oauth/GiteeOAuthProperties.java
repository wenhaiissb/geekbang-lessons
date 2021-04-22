package org.geektimes.oauth;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Properties;
@Service
public class GiteeOAuthProperties implements ConfigurationOAuthProperties {
    private OAuthProperties oAuthProperties;

    @Override
    public OAuthProperties getOAuthProperties() {
        if (oAuthProperties == null) {
            oAuthProperties = new OAuthProperties();
            Properties properties = new Properties();
            InputStream in = GiteeOAuthProperties.class.getClassLoader().getResourceAsStream("META-INF/client.properties");
            try {
                properties.load(in);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
            oAuthProperties.setClientId(properties.getProperty("clientId"));
            oAuthProperties.setClientSecret(properties.getProperty("clientSecret"));
            oAuthProperties.setRedirectUri(properties.getProperty("redirectUri"));
            oAuthProperties.setUrl(properties.getProperty("url"));
        }


        return oAuthProperties;
    }
}
