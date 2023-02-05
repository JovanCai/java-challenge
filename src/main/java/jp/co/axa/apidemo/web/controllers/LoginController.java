package jp.co.axa.apidemo.web.controllers;

import jp.co.axa.apidemo.application.auth.TokenUtils;
import jp.co.axa.apidemo.domain.entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class LoginController {


    @RequestMapping("/doLogin")
    public String doLogin(ModelMap map, User user, HttpServletRequest request) {
        // todo: change to get name and password from non-in-memory database
        if (user != null && user.getName().equals("admin")
                && user.getPassword().equals("admin")) {
            TokenUtils.initToken(user);
            return "redirect:/api/v1/index";
        }
        return "login";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }
}