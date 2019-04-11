package ua.com.shop.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HtmlPageController {

    @RequestMapping("/home")
    public String mainPage(){
        return "index.html";
    }

    @RequestMapping("/admin")
    public String adminPage(){
        return "admin.html";
    }

    @RequestMapping("/sing")
    public String singPage(){
        return "sing.html";
    }

    @RequestMapping("/cart")
    public String cartPage(){
        return "cart.html";
    }

}
