package org.geektimes.oauth;

/**
 * Gitee 授权属性
 *
 * @author wenhai
 * @date   2021/4/22
 */
public class OAuthProperties {
    private String url;
    private String clientId;
    private String redirectUri;
    private String clientSecret;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }


    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }
}
