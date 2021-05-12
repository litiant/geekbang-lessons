package org.geektimes.oauth.controller;

import org.geektimes.oauth.GiteeOauth;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class InfoGiteeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
        String code = request.getParameter("code");
        request.getServletContext().log(String.format("code %s", code));
        GiteeOauth giteeOauth = new GiteeOauth();
        String token = giteeOauth.getAccessToken(code);

        request.getServletContext().log(String.format("fowrd %s", token));

    }
}
