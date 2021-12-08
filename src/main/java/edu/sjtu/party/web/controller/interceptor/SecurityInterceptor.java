package edu.sjtu.party.web.controller.interceptor;

import com.google.gson.Gson;
import edu.sjtu.party.model.UserProfile;
import edu.sjtu.party.model.metadata.Power;
import edu.sjtu.party.model.metadata.PowerDefinition;
import edu.sjtu.party.service.UserProfileService;
import edu.sjtu.party.utils.weixin.WeixinUtils;
import edu.sjtu.party.web.controller.annotation.CheckPriviledge;
import edu.sjtu.json.Response;
import edu.sjtu.web.annotation.API;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.http.HttpStatus;
import org.apache.oltu.oauth2.client.response.OAuthAuthzResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.*;

@Component
public class SecurityInterceptor implements HandlerInterceptor {

    @Value("${client.id}")
    private String CLIENT_ID;
    @Value("${client.secret}")
    private String CLIENT_SECRET;
    @Value("${oauth.authorize.url}")
    private String AUTHORIZATION_LOCATION;
    @Value("${oauth.token.url}")
    private String TOKEN_LOCATION;
    @Value("${oauth.right.url}")
    private String RIGHT_URL;
    @Value("${oauth.profile.url}")
    private String PROFILE_URL;

    @Autowired
    private UserProfileService userProfileService;

    private final static Gson gson = new Gson();
    private final static String ACCESS_TOKEN_KEY = "_X_ACCESS_TOKEN";
    private final static String CURRENT_USER_ID_KEY = "_X_CURRENT_USER_ID";
    private final static String CURRENT_USER_KEY = "_X_CURRENT_USER";
    private final static String PERSON_NAME_KEY = "_X_PERSON_NAME";
    private final static String STATE_MAP_KEY = "_X_STATE_MAP";
    private final static int MAX_ENTRIES = 10;
    private final static String AUTH_POWER = "_x_auth_power";



    public final static String getLoginUserID(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        return (String) session.getAttribute(CURRENT_USER_ID_KEY);
    }

    public final static UserProfile getLoginUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        return (UserProfile) session.getAttribute(CURRENT_USER_KEY);
    }

    public final static String getLoginUserName(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        return (String) session.getAttribute(PERSON_NAME_KEY);
    }



    public final static PowerDefinition getPowers(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        return (PowerDefinition) session.getAttribute(AUTH_POWER);
    }

    public final static String getCurrentUrl(HttpServletRequest request, String replacePath) {
        String qs = request.getQueryString();
        StringBuffer buffer = request.getRequestURL();
        int index = buffer.lastIndexOf(";jsessionid");
        if (index != -1) {
            buffer.setLength(index);
        }
        if (qs != null) {
            buffer.append('?').append(qs);
        }

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(buffer.toString());
        if (replacePath != null) {
            uriBuilder.replacePath(replacePath);
        }
        return uriBuilder.build().toString();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        if (!(handler instanceof HandlerMethod)) {
//            return true;
//        }
//
//        HandlerMethod method = (HandlerMethod) handler;
//        CheckPriviledge checkPriviledge = method.getMethodAnnotation(CheckPriviledge.class);
//        if (checkPriviledge == null) {
//            checkPriviledge = AnnotationUtils.findAnnotation(method.getBeanType(), CheckPriviledge.class);
//        }
//
//        if (checkPriviledge == null) {
//            //不需要登录即可访问的
//            return true;
//        }
//
//        API api = method.getMethodAnnotation(API.class);
//        if (api == null) {
//            api = AnnotationUtils.findAnnotation(method.getBeanType(), API.class);
//        }
//
//        boolean logined = null != getLoginUserID(request);
//
//        if (!logined) {
//            if (api != null) {
//                //API访问不重定向到登陆界面
//                response.setStatus(HttpStatus.SC_UNAUTHORIZED);
//            } else {
//                tryToLogin(request, response);
//            }
//            return false;
//        }
//
//        //check privileges
//        Power[] power = checkPriviledge.type();
//        if (ArrayUtils.contains(power, Power.NONE)) {
//            //不需要权限控制，登录即可访问
//            return true;
//        }
//
//        if (checkPriviledge(request, power)) {
//            return true;
//        }
//
//        return false;

        return true;
    }

    /**
     * 检查当前登录用户是否有对应的权限
     * @param request
     * @return
     */
    public boolean checkPriviledge(HttpServletRequest request, Power[] powers) {
        PowerDefinition powerDefinition = getPowers(request);
        if(powerDefinition == null){
            return false;
        }
        if(ArrayUtils.contains(powers, powerDefinition.power)) {
            return true;
        }
        return false;

    }

    private void tryToLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        //是否为登录后的回调
        if (StringUtils.isNotEmpty(request.getParameter(OAuth.OAUTH_CODE)) || StringUtils.isNotEmpty(request.getParameter(OAuthError.OAUTH_ERROR))) {
            OAuthAuthzResponse oar = OAuthAuthzResponse.oauthCodeAuthzResponse(request);
            String code = oar.getCode();
            String state = oar.getState();

            UserProfile userProfile = WeixinUtils.getSpecAccessToken(CLIENT_ID, CLIENT_SECRET, code);

            if(userProfile != null) {
                session.setAttribute(ACCESS_TOKEN_KEY, userProfile.getAccessToken());
                session.setAttribute(CURRENT_USER_KEY, userProfile);
                session.setAttribute(CURRENT_USER_ID_KEY, userProfile.getOpenId());
                session.setAttribute(PERSON_NAME_KEY, userProfile.getNickname());

                userProfileService.saveUserProfile(userProfile);
//            if(StringUtils.isNotEmpty(this.getRedirectUriFromSession(session, state))) {
//                String url = this.getRedirectUriFromSession(session, state);
//                response.sendRedirect(this.getRedirectUriFromSession(session, state));
//            }else {
                response.sendRedirect("/");
//            }
            }
            return;
        }
        redirectToAuthorize(request, response, session);
    }

    private void redirectToAuthorize(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        String redirectUrl = URLEncoder.encode(getCurrentUrl(request, null), "utf8");
        String state = UUID.randomUUID().toString().substring(0, 6);

        this.putRedirectUriToSession(session, state, redirectUrl);
        String url = AUTHORIZATION_LOCATION.replace("REDIRECT_URI", redirectUrl).replace("STATE", state);
        response.sendRedirect(url);
    }

    private String getRedirectUriFromSession(HttpSession session, String state) {
        return this.getStateMap(session).get(state);
    }

    private String putRedirectUriToSession(HttpSession session, String state, String redirectUrl) {
        return this.getStateMap(session).put(state, redirectUrl);
    }

    private Map<String, String> getStateMap(HttpSession session) {
        Map<String, String> result = (Map<String, String>) session.getAttribute(STATE_MAP_KEY);
        if (result == null) {
            result = new LinkedHashMap<String, String>() {
                @Override
                protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
                    return this.size() > MAX_ENTRIES;
                }
            };
            session.setAttribute(STATE_MAP_KEY, result);
        }
        return result;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }

}

