package com.omnia.authentication.controller;

import com.omnia.authentication.command.LoginCommand;
import com.omnia.authentication.command.LogoutCommand;
import com.omnia.authentication.vo.LoginSession;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.axonframework.commandhandling.callbacks.FutureCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.concurrent.ExecutionException;

/**
 * Created by khaerothe on 2015/4/29.
 */
@Controller
public class LoginController {

    private static final String LOGIN_SESSION_NAME = "loginSession";

    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private CommandBus commandBus;

//    private LoginService loginService = new LoginService();

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(HttpServletRequest request, HttpServletResponse response){
        LoginSession loginSession = (LoginSession) request.getSession().getAttribute("loginSession");
        if(loginSession != null){
            //TODO when logging on, redirect a url;
            return "redirect:/index";
        }else{
            return "login/login";
        }
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String loginProcess(HttpSession session, String username, String password){
        LoginCommand loginCommand = new LoginCommand(username, password);
        FutureCallback<LoginSession> callback = new FutureCallback<>();
        commandBus.dispatch(new GenericCommandMessage<>(loginCommand), callback);

        LoginSession loginSession = null;
        try {
            loginSession = callback.get();
        } catch (InterruptedException | ExecutionException e) {
            LOG.error("error call of login command", e);
        }
        if(loginSession != null){
            session.setAttribute(LOGIN_SESSION_NAME, loginSession);
            return "{\"success\" : true}";
        }else{
            return "{\"success\" : true}";
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutProcess(HttpSession session){
        LogoutCommand logoutCommand = new LogoutCommand();

        commandBus.dispatch(new GenericCommandMessage<>(logoutCommand));
        session.removeAttribute(LOGIN_SESSION_NAME);
        return "redirect:/login";
    }
}
