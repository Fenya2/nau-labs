package ru.dmitry.naujava.conroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.dmitry.naujava.dto.user.CreateUserDTO;
import ru.dmitry.naujava.service.UserService;

@Controller
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @GetMapping("/registration")
    public String getRegistration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String processRegistration(CreateUserDTO createUserDTO) {
        userService.createUser(createUserDTO);
        return "redirect:/login";
    }

    @GetMapping("/home")
    public String getHome()
    {
        return "home";
    }
}
