package edu.sjtu.party.web.controller;

import edu.sjtu.party.web.controller.annotation.CheckPriviledge;
import edu.sjtu.party.web.controller.interceptor.SecurityInterceptor;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class UIController {

    @Value("${client.id}")
    private String CLIENT_ID;

    @Value("${oauth.logout.url}")
    private String LOGOUT_LOCATION;


    @RequestMapping(value = { "/", "/ui/**" }, method = RequestMethod.GET)
    @CheckPriviledge
    public String login() {
        return "index";
    }

    @RequestMapping("/relogin")
    @CheckPriviledge
    public void relogin(HttpServletResponse resp) throws IOException {
        resp.sendRedirect("/");
    }

    @RequestMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse resp) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        resp.sendRedirect("/");
    }

    @RequestMapping(value = "alive")
    public String alive() {
        return "alive";
    }

}
