package planner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class StartController {
    @GetMapping("/")
    public String start(Principal principal, HttpSession session) {
        return "welcome-page";
    }
}
