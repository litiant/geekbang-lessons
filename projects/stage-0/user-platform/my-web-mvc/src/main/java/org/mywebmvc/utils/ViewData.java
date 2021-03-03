package org.mywebmvc.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lt 2021/3/2
 */
public class ViewData {
    private HttpServletRequest request;

    public ViewData() {
        initRequest();
    }

    private void initRequest(){
        this.request = WebContext.requestHodler.get();
    }

    public void put(String name,Object value){
        this.request.setAttribute(name, value);
    }
}
