package org.geektimes.oauth;


import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Controller
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    @Resource
    private ConfigurationOAuthProperties configurationOAuthProperties;

    @GetMapping("/oauth")
    public String giteeOauth() {
        OAuthProperties oAuthProperties = configurationOAuthProperties.getOAuthProperties();
        String url = oAuthProperties.getUrl() + "authorize?client_id=" + oAuthProperties.getClientId() + "&redirect_uri=" + oAuthProperties.getRedirectUri() +
                "&response_type=code";
        return "redirect:" + url;
    }

    @GetMapping("/code")
    @ResponseBody
    public String callBack(String code) {
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        OAuthProperties oAuthProperties = configurationOAuthProperties.getOAuthProperties();
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;
        // 以表单的方式提交
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //将请求头部和参数合成一个请求
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(param, headers);
        String url = oAuthProperties.getUrl() + "token?grant_type=authorization_code&code=" + code + "&client_id=" + oAuthProperties.getClientId()
                + "&redirect_uri=" + oAuthProperties.getRedirectUri() + "&client_secret=" + oAuthProperties.getClientSecret();
        ResponseEntity<Token> responseEntity = client.exchange(url, method, requestEntity, Token.class);
        return JSONObject.toJSONString(responseEntity.getBody());
    }


}
