package com.mmall.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by chenyawei on 2019/4/21.
 */
@Slf4j
public class CookieUtil {


    //本地调试的时候改为：//localhost
    private final static String COOKIE_DOMAIN =  "localhost"; //"bytenote.cn"; // "localhost"; //".bytenote.com";
    private final static String COOKIE_NAME = "mmall_login_token";


    public static String readLoginToken(HttpServletRequest request) {
        Cookie[] cks = request.getCookies();
        String token = null;
        if (cks != null) {
            for (Cookie ck : cks) {
                log.info("read cookieName:{},cookieValue:{}", ck.getName(), ck.getValue());
                if (StringUtils.equals(ck.getName(), COOKIE_NAME)) {
                    log.info("return cookieName:{},cookieValue:{}", ck.getName(), ck.getValue());
                    token = ck.getValue();
                    return token;
                }
            }
        }
        if (token == null){
            token = request.getHeader(COOKIE_NAME);
        }
        return token;
    }

    //X:domain=".bytenote.com"
    //a:A.bytenote.com            cookie:domain=A.bytenote.com;path="/"
    //b:B.bytenote.com            cookie:domain=B.bytenote.com;path="/"
    //c:A.bytenote.com/test/cc    cookie:domain=A.bytenote.com;path="/test/cc"
    //d:A.bytenote.com/test/dd    cookie:domain=A.bytenote.com;path="/test/dd"
    //e:A.bytenote.com/test       cookie:domain=A.bytenote.com;path="/test"
//a拿不到b的，c拿不到d的，c和d能拿到e的,e拿不到c和d的；domain设置为一级域名就能共享所有的二级域名下的cookie
    public static void writeLoginToken(HttpServletResponse response, String token) {
        Cookie ck = new Cookie(COOKIE_NAME, token);
        ck.setDomain(COOKIE_DOMAIN);
        ck.setPath("/");//代表设置在根目录
        ck.setHttpOnly(true);//防止脚本攻击
        //单位是秒。
        //如果这个maxage不设置的话，cookie就不会写入硬盘，而是写在内存。只在当前页面有效。
        ck.setMaxAge(60 * 60 * 24 * 365);//如果是-1，代表永久
        log.info("write cookieName:{},cookieValue:{}", ck.getName(), ck.getValue());
        response.addCookie(ck);
        response.addHeader(COOKIE_NAME,token);
    }


    public static void delLoginToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cks = request.getCookies();
        if (cks != null) {
            for (Cookie ck : cks) {
                if (StringUtils.equals(ck.getName(), COOKIE_NAME)) {
                    ck.setDomain(COOKIE_DOMAIN);
                    ck.setPath("/");
                    ck.setMaxAge(0);//设置成0，代表删除此cookie。
                    log.info("del cookieName:{},cookieValue:{}", ck.getName(), ck.getValue());
                    response.addCookie(ck);
                    response.addHeader(COOKIE_NAME,null);
                    return;
                }
            }
        }
    }


}
