package s55_22769.t_01.abdelrahman_mohamed.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @Value("${USER_NAME:Abdelrahman_Mohamed_Local}")
    private String userName;

    @Value("${ID:19002350}")
    private String id;

    @GetMapping("/welcome")
    public String welcome() {
        return "Hello " + userName + " " + id + ", from Notes API";
    }
}
