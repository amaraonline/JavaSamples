package com.examples;

//Here Maps dependency to add in build file to work this service
//implementation 'com.here.account:here-oauth-client:0.4.23'

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.http.HttpHost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.here.account.auth.provider.FromDefaultHereCredentialsPropertiesFile;
import com.here.account.http.HttpProvider;
import com.here.account.http.apache.ApacheHttpClientProvider;
import com.here.account.oauth2.AccessTokenResponse;
import com.here.account.oauth2.HereAccessTokenProvider;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HereApiOAuthTokenService {

    File hereProperties;

    CloseableHttpClient httpclient;

    @PostConstruct
    public void loadHereProperties() {

        HttpHost proxy = new HttpHost("PROXY_HOST", "PROXY_PORT");
        DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
        httpclient = HttpClients.custom()
                .setRoutePlanner(routePlanner)
                .build();
        try {
          // make sure credential.properties file in application classpath
          /*
            Example of credentials properties file:
  
            here.user.id = example-here-user-id
            here.client.id = example-client-id
            here.access.key.id = example-access-key-id
            here.access.key.secret = example-access-key-secret
            here.token.endpoint.url = https://account.api.here.com/oauth2/token
            here.token.scope = hrn:here:authorization:::project/example-project   // optional
          */
            hereProperties = new ClassPathResource("credentials.properties").getFile();
        } catch (IOException e) {
            log.debug("Issue in loading Here API Credentials Properties....");
            log.info("Issue in loading Here API Credentials Properties....",e);
        }
    }

    public Map<String, String> getAuthToken(){
        HttpProvider httpProvider = ApacheHttpClientProvider.builder().setHttpClient(httpclient).build();

        HereAccessTokenProvider accessTokens = HereAccessTokenProvider.builder()
                .setClientAuthorizationRequestProvider(new FromDefaultHereCredentialsPropertiesFile(hereProperties))
                .setHttpProvider(httpProvider)
                .setAlwaysRequestNewToken(true)
                .build();
      
        AccessTokenResponse accessTokenResponse = accessTokens.getAccessTokenResponse();
        Map<String, String> tokenMap = new HashMap<String, String>();
        tokenMap.put("access_token",accessTokenResponse.getAccessToken());
        tokenMap.put("token_type",accessTokenResponse.getTokenType());
        tokenMap.put("expires_in",String.valueOf(accessTokenResponse.getExpiresIn()));
        tokenMap.put("scope",accessTokenResponse.getScope());
        log.debug("accessToken :: "+accessTokenResponse.getAccessToken());
        return tokenMap;
    }

}
