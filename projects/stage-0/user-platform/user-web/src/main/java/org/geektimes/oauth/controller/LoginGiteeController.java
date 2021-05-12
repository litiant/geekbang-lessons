package org.geektimes.oauth.controller;

import org.geektimes.oauth.GiteeOauth;
import org.mywebmvc.annotation.Controller;
import org.mywebmvc.annotation.RequestMapping;
import org.mywebmvc.utils.View;
import org.mywebmvc.utils.ViewData;
import org.mywebmvc.utils.WebContext;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.io.IOException;


//@Path("/oauth")
//@Controller
public class LoginGiteeController extends HttpServlet {

    @Resource(name="bean/GiteeOauth")
    private GiteeOauth giteeOauth;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = giteeOauth.authorize();
        request.getServletContext().log(String.format("fowrd %s", url));
//        request.getRequestDispatcher(url).forward(request, response);
        response.sendRedirect(url);
        return;
    }


    //    @GET
//    @Path("/login")
//    @RequestMapping("/oauth/login")
    public View login(){
        HttpServletRequest request = WebContext.requestHodler.get();
        ServletContext servletContext = request.getServletContext();
        String url = giteeOauth.authorize();
        request.getServletContext().log(String.format("fowrd %s", url));

        ViewData viewData = new ViewData();
//        viewData.put("msg",username+"注册成功");
        return new View("index");
    }
}
