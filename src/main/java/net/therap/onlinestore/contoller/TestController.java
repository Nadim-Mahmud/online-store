package net.therap.onlinestore.contoller;

import net.therap.onlinestore.entity.User;
import net.therap.onlinestore.entity.UserType;
import net.therap.onlinestore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * @author nadimmahmud
 * @since 3/4/23
 */
@Controller
public class TestController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String showHome() throws Exception {

        //System.out.println(userService.findAll());

        return "home";
    }
}
