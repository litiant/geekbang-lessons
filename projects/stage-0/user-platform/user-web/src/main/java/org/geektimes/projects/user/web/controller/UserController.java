package org.geektimes.projects.user.web.controller;

import org.geektimes.projects.user.domain.User;
import org.geektimes.projects.user.service.UserServiceImpl;
import org.mywebmvc.annotation.Controller;
import org.mywebmvc.annotation.RequestMapping;
import org.mywebmvc.utils.View;
import org.mywebmvc.utils.ViewData;
import org.mywebmvc.utils.WebContext;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lt 2021/3/2
 */
@Controller
public class UserController{

    @RequestMapping("register")
    public View register(){
        HttpServletRequest request = WebContext.requestHodler.get();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        request.getServletContext().log("username:"+username+"\t"+"password:"+password);

        User user = new User();
        user.setName(username);
        user.setPassword(password);
        user.setEmail("lt@163.com");
        user.setPhoneNumber("18600009999");
        new UserServiceImpl().register(user);

        ViewData viewData = new ViewData();
        viewData.put("msg",username+"注册成功");
        return new View("success.jsp");

    }
}
