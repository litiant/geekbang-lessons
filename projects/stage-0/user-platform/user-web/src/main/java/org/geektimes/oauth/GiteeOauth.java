package org.geektimes.oauth;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

public class GiteeOauth {

    private static final String REDIRECT_RUI = "http://localhost:8080/oauth/info";
    private static final String GITEE_CLIENT_ID = "d6873526ee2959d553b131574a9612dffcbfe72123ef65db30e98ddd9e8c0d27";
    private static final String GITEE_CLIENT_SECRET = "3f1b6dbd9f5b242cc24bb39bdd21c4566e637a5d7b91731d7f018570a16444f6";


    public String authorize(){
        return "https://gitee.com/oauth/authorize?client_id="+GITEE_CLIENT_ID+"&redirect_uri="+REDIRECT_RUI+"&response_type=code";
    }

    public String accessToken(String code){
        //过期后(24H)，重新获取 token
        //https://gitee.com/oauth/token?grant_type=refresh_token&refresh_token={refresh_token}
        return "https://gitee.com/oauth/token?grant_type=authorization_code&code="+code+"&client_id="+GITEE_CLIENT_ID+"&redirect_uri="+REDIRECT_RUI+"&client_secret="+GITEE_CLIENT_SECRET;

    }

    public String userInfo(String accessToken){
        return "https://gitee.com/api/v5/user?access_token="+accessToken;
    }


    public static void main(String[] args) throws Exception {
        String code = "581378519855708b6527a0f9cda771a0f425c546d0d7273f8ba49b898a249286";
        String url = new GiteeOauth().getAccessToken(code);

//        URI uri = new URI(url);
//        URL targetUrl = uri.toURL();
//        HttpURLConnection connection = (HttpURLConnection) targetUrl.openConnection();
//        connection.setRequestMethod("GET");
//        try (InputStream inputStream = connection.getInputStream()) {
//            System.out.println(IOUtils.toString(inputStream, "UTF-8"));
//        }
//        // 关闭连接
//        connection.disconnect();
    }

    public String getAccessToken(String code){
        String url = accessToken(code);

        Client client = ClientBuilder.newClient();
        Response response = client
                .target(url)
                .request() // Invocation.Builder
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36")
                .get();
        int statusCode = response.getStatusInfo().getStatusCode();
        if(Response.Status.OK.getStatusCode() == statusCode){
            return response.readEntity(String.class);
        }
        return String.valueOf(statusCode);
    }


//    public GiteeOauth(String authorizeURL, String clientId, String redirectURI, String link){
//        url = new StringBuilder();
//       try{
//           url.append(authorizeURL)
//                   .append("/oauth/authorize?client_id=").append(clientId)
//                   .append("&redirect_uri=").append(URLEncoder.encode(redirectURI, "UTF-8"))
//                   .append("&response_type=code");
//       }catch (UnsupportedEncodingException e){
//           e.printStackTrace();
//       }
//    }
}
