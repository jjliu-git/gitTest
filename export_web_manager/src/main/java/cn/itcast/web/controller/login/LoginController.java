package cn.itcast.web.controller.login;

import cn.itcast.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController extends BaseController {

    @RequestMapping("/login")
    public String login(HttpServletRequest request) {
        // 跳转显示侧面栏
        return "home/main";
    }

    @RequestMapping("/home")
    public String home(HttpServletRequest request) {
        // 跳转主页
        return "home/home";
    }
}
