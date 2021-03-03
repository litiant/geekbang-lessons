package org.mywebmvc.utils;

/**
 * Created by lt 2021/3/2
 */
public class View {

    private String url;//跳转路径

    private String dispath = Constant.FORWARD;

    public View(String url) {
        this.url = url;
    }

    public View(String url,String name,Object value) {
        this.url = url;
        ViewData view = new ViewData();
        view.put(name, value);
    }


    public View(String url,String name,String dispath ,Object value) {
        this.dispath = dispath;
        this.url = url;
        ViewData view = new ViewData();
        view.put(name, value);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDispath() {
        return dispath;
    }

    public void setDispath(String dispathAction) {
        this.dispath = dispathAction;
    }
}
