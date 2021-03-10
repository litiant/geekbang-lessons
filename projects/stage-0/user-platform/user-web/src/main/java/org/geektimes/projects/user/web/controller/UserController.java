package org.geektimes.projects.user.web.controller;

import org.apache.commons.lang.StringUtils;
import org.geektimes.projects.user.domain.User;
import org.geektimes.projects.user.service.UserService;
import org.geektimes.projects.user.service.UserServiceImpl;
import org.mywebmvc.annotation.Controller;
import org.mywebmvc.annotation.RequestMapping;
import org.mywebmvc.utils.View;
import org.mywebmvc.utils.ViewData;
import org.mywebmvc.utils.WebContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by lt 2021/3/2
 */
@Controller
public class UserController{

    @Resource(name = "bean/DefaultUserService")
    private UserService userService;

    @RequestMapping("register")
    public View register(){
        HttpServletRequest request = WebContext.requestHodler.get();
        String id = request.getParameter("id");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String phoneNumber = request.getParameter("phoneNumber");
        request.getServletContext().log("username:"+username+"\t"+"password:"+password);

        User user = new User();
        user.setId(StringUtils.isNotBlank(id) ? Long.getLong(id) : 0L);
        user.setName(username);
        user.setPassword(password);
        user.setEmail("lt@163.com");
        user.setPhoneNumber(phoneNumber);
        new UserServiceImpl().register(user);

        ViewData viewData = new ViewData();
        viewData.put("msg",username+"注册成功");
        return new View("success.jsp");

    }
}
