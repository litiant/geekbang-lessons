package org.geektimes.oauth.controller;

import org.mywebmvc.annotation.Controller;
import org.mywebmvc.annotation.RequestMapping;
import org.mywebmvc.utils.WebContext;

import javax.servlet.http.HttpServletRequest;


@Controller
public class LoginGiteeController {



    @RequestMapping("/oauth/login")
    public String login(){
        HttpServletRequest request = WebContext.requestHodler.get();
        String type = request.getParameter("type").toUpperCase();
        if ("GITEE".equals(type)) {

        }
        return null;
    }
}
