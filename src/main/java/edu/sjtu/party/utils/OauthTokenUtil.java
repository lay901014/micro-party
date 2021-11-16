/**   
 * @Description:
 * @Package: sjtu.api.common.util 
 * @author: lulei 
 * @date: 2018年11月13日 上午9:07:45 
 */
package edu.sjtu.party.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;


/**   
 * @ClassName:  OauthTokenUtil   
 * @Description:TODO
 * @author: lulei
 * @date:   2018年11月13日 上午9:07:45   
 *     
 */
public class OauthTokenUtil {
    
    public final static Gson gson = new Gson();
    
    public final static String TOKEN_ENDPOINT_URL = "https://jaccount.sjtu.edu.cn/oauth2/token";

    public final static String NOTIFY_SCOPE = "send_notification";


    public static String getTokenByClientGrant(String clientId, String secret,
            String scope) {
        
        OAuthClientRequest oAuthClientRequest;
        try {
            oAuthClientRequest = OAuthClientRequest
                    .tokenLocation(TOKEN_ENDPOINT_URL)
                    .setClientId(clientId)
                    .setClientSecret(secret)
                    .setGrantType(GrantType.CLIENT_CREDENTIALS)
                    .setScope(scope)
                    .buildBodyMessage();
            
            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
            OAuthJSONAccessTokenResponse oAuthResp = oAuthClient.accessToken(oAuthClientRequest);
            JsonObject jsonObject = gson.fromJson(oAuthResp.getBody(), JsonObject.class);
            if(jsonObject != null)
                return jsonObject.get("access_token").getAsString();
            return null;
            
        }catch (Exception e) {
            throw new RuntimeException("ClientGrant---获取token异常", e);
        }
    }
    
    /**
     * @Title: getTokenByCode   
     * @Description: AUTHORIZATION_CODE模式获取token
     * @param: @param clientId
     * @param: @param secret
     * @param: @param tokenUrl
     * @param: @param rediectUrl
     * @param: @param code
     * @param: @return      
     * @return: JsonObject      
     * @throws
     */
    public static JsonObject getTokenByCodeGrant(String clientId, String secret,
                                                 String rediectUrl, String code) {
        
        OAuthClientRequest oAuthClientRequest;
        try {
            oAuthClientRequest = OAuthClientRequest
                    .tokenLocation(TOKEN_ENDPOINT_URL)
                    .setGrantType(GrantType.AUTHORIZATION_CODE)
                    .setClientId(clientId)
                    .setClientSecret(secret)
                    .setRedirectURI(rediectUrl)
                    .setCode(code)
                    .buildBodyMessage();
        
            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
            OAuthJSONAccessTokenResponse oAuthResp = oAuthClient.accessToken(oAuthClientRequest);
            JsonObject jsonObject = gson.fromJson(oAuthResp.getBody(), JsonObject.class);
            return jsonObject;
        }catch (Exception e) {
            throw new RuntimeException("CodeGrant---获取token异常", e);
        }
        
    }

    public static String getTokenByPassword(String clientId, String secret, String scope, String userName, String password) {
        OAuthClientRequest oAuthClientRequest;
        try {
            oAuthClientRequest = OAuthClientRequest
                    .tokenLocation(TOKEN_ENDPOINT_URL)
                    .setClientId(clientId)
                    .setClientSecret(secret)
                    .setGrantType(GrantType.PASSWORD)
                    .setUsername(userName)
                    .setPassword(password)
                    .setScope(scope)
                    .buildBodyMessage();

            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
            OAuthJSONAccessTokenResponse oAuthResp = oAuthClient.accessToken(oAuthClientRequest);
            JsonObject jsonObject = gson.fromJson(oAuthResp.getBody(), JsonObject.class);
            if(jsonObject != null)
                return jsonObject.get("access_token").getAsString();
            return null;

        }catch (Exception e) {
            throw new RuntimeException("ClientGrant---获取token异常", e);
        }
    }
    
    public static JsonObject getTokenByRefreshToken(String clientId, String secret,
                                                    String refresh_token) {
        
        OAuthClientRequest oAuthClientRequest;
        try {
            oAuthClientRequest = OAuthClientRequest
                    .tokenLocation(TOKEN_ENDPOINT_URL)
                    .setGrantType(GrantType.REFRESH_TOKEN)
                    .setClientId(clientId)
                    .setClientSecret(secret)
                    .setRefreshToken(refresh_token)
                    .buildBodyMessage();
            
            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
            OAuthJSONAccessTokenResponse oAuthResp = oAuthClient.accessToken(oAuthClientRequest);
            JsonObject jsonObject = gson.fromJson(oAuthResp.getBody(), JsonObject.class);
            return jsonObject;
        }catch (Exception e) {
            throw new RuntimeException("RefreshToken---获取token异常", e);
        }
    }

}
