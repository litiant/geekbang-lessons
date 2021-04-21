package org.geektimes.oauth;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class GiteeOauth {

    private StringBuilder url;

    private static final String GITEE_CLIENT_ID = "d6873526ee2959d553b131574a9612dffcbfe72123ef65db30e98ddd9e8c0d27";
    private static final String GITEE_CLIENT_SECRET = "3f1b6dbd9f5b242cc24bb39bdd21c4566e637a5d7b91731d7f018570a16444f6";

    public GiteeOauth(String authorizeURL, String clientId, String redirectURI, String link){
        url = new StringBuilder();
       try{
           url.append(authorizeURL)
                   .append("/oauth/authorize?client_id=").append(clientId)
                   .append("&redirect_uri=").append(URLEncoder.encode(redirectURI, "UTF-8"))
                   .append("&response_type=code");
       }catch (UnsupportedEncodingException e){
           e.printStackTrace();
       }
    }

    public GiteeOauth(String authorizeURL, String code , String clientId ,  String redirectURI , String clientSecret) {
        url = new StringBuilder();
        try {
            url.append(authorizeURL)
                    .append("/oauth/token?grant_type=authorization_code&code=").append(code)
                    .append("&client_id=").append(clientId)
                    .append("&redirect_uri=").append(URLEncoder.encode(redirectURI, "UTF-8"))
                    .append("&client_secret=").append(clientSecret);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public String getAuthorize() {
        return url.toString();
    }

    public String getToken(){
        return url.toString();
    }

}
