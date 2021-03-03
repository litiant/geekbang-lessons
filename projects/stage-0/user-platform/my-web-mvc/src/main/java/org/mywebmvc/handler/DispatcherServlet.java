package org.mywebmvc.handler;

import org.mywebmvc.annotation.Controller;
import org.mywebmvc.annotation.RequestMapping;
import org.mywebmvc.utils.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by lt 2021/3/2
 */
//@WebServlet(name = "dispatcherServlet", urlPatterns = "/*", loadOnStartup = 1, initParams = {@WebInitParam(name="base-package", value = "org.geektimes.projects.user.web.controller")})
public class DispatcherServlet extends HttpServlet {

    private Map<String, Class<?>> requesetMap = new HashMap<String, Class<?>>();

    private ServletContext servletContext;


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        servletContext.log("doGet......");
        this.execute(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        servletContext.log("doPost......");
        this.execute(request, response);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        /*
         * 重写了Servlet的init方法后一定要记得调用父类的init方法，
         * 否则在service/doGet/doPost方法中使用getServletContext()方法获取ServletContext对象时
         * 就会出现java.lang.NullPointerException异常
         */
        super.init(config);
        servletContext = config.getServletContext();
        servletContext.log("初始化开始");
        //获取web.xml中配置的要扫描的包
        String basePackage = config.getInitParameter("basePackage");
        //如果配置了多个包，已","隔开
        if (basePackage.indexOf(",")>0) {
            String[] packageNameArr = basePackage.split(",");
            for (String packageName : packageNameArr) {
                initRequestMapingMap(packageName);
            }
        }else {
            initRequestMapingMap(basePackage);
        }
        servletContext.log("初始化结束");
    }

    private void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.保存HttpRequest、HttpResponse
        WebContext.requestHodler.set(request);
        WebContext.responseHodler.set(response);
        //2.解析请求的url
        String requestUrl = resolveRequestURI(request);
        //3.根据 请求的url获取要使用的类
        Class<?> clazz = requesetMap.get(requestUrl);
        //4.创建类的实例
        Object classInstance = BeanUtils.instanceClass(clazz);
        //5.获取类中定义的方法
        Method[] methods = BeanUtils.findDeclaredMethods(clazz);
        //遍历所有方法,找出url与RequestMapping注解的value值相匹配的方法
        Method method = null;
        for (Method m : methods) {
            if (m.isAnnotationPresent(RequestMapping.class)) {
                String value = m.getAnnotation(RequestMapping.class).value();
                if (value != null && !"".equals(value.trim()) && requestUrl.equals(value.trim())) {
                    //找到要执行的目标方法
                    method = m;
                    break;
                }
            }
        }
        //6.执行url对应的方法,处理用户请求
        if (method != null) {
            Object retObject = null;
            try {
                retObject = method.invoke(classInstance);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            //如果有返回值,就代表用户需要返回视图
            if (retObject != null) {
                View view = (View) retObject;
                //判断要使用的跳转方式
                if (view.getDispath().equals(Constant.FORWARD)) {
                    //使用服务器端跳转方式
                    request.getRequestDispatcher(view.getUrl()).forward(request, response);
                } else if (view.getDispath().equals(Constant.REDIRECT)) {
                    //使用客户端跳转方式
                    response.sendRedirect(request.getContextPath() + view.getUrl());
                } else {
                    request.getRequestDispatcher(view.getUrl()).forward(request, response);
                }
            }
        }
    }

    /**
     * 从HttpRequest中解析出 请求路径,即 RequestMapping() 的value值.
     *
     * @param request
     * @return
     */
    private String resolveRequestURI(HttpServletRequest request) {
        String path = request.getContextPath() + "/";
        String requestUri = request.getRequestURI();
        String midUrl = requestUri.replace(path, "");
        String lastUrl = midUrl.substring(0, midUrl.lastIndexOf("."));
        return lastUrl;
    }

    /**
     * @Method: initRequestMapingMap
     * @Description:添加使用了Controller注解的Class到RequestMapingMap中
     */
    private void initRequestMapingMap(String packageName){
        //得到扫描包下的class
        Set<Class<?>> setClasses =  ScanClassUtil.getClasses(packageName);
        for (Class<?> clazz :setClasses) {
            if (clazz.isAnnotationPresent(Controller.class)) {
                Method[] methods = BeanUtils.findDeclaredMethods(clazz);
                for(Method m:methods){//循环方法，找匹配的方法进行执行
                    if(m.isAnnotationPresent(RequestMapping.class)){
                        String anoPath = m.getAnnotation(RequestMapping.class).value();
                        if(anoPath!=null && !"".equals(anoPath.trim())){
                            if(requesetMap.containsKey(anoPath)){
                                throw new RuntimeException("RequestMapping映射的地址不允许重复！");
                            }
                            //把所有的映射地址存储起来  映射路径--类
                            requesetMap.put(anoPath, clazz);
                        }
                    }
                }
            }
        }
    }
}
