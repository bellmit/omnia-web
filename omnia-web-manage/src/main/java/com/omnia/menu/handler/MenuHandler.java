package com.omnia.menu.handler;


import com.omnia.common.handler.ViewHandler;
import com.omnia.menu.data.MenuData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Created by khaerothe on 2015/4/19.
 */
public class MenuHandler implements ViewHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) {
        //TODO generate module mark
        String module = "";
//        request.getRequestURI();
        String menuElement = new MenuData("menu").genHtml();

        request.setAttribute("menu", menuElement);
    }
}
