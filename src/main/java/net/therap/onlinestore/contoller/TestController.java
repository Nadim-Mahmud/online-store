package net.therap.onlinestore.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author nadimmahmud
 * @since 3/4/23
 */
@Controller
public class TestController {

    @GetMapping("/")
    public String showHome(){
        return "index";
    }
}
