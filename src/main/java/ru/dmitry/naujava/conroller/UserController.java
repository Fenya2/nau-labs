package ru.dmitry.naujava.conroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.dmitry.naujava.Constants;
import ru.dmitry.naujava.entity.User;
import ru.dmitry.naujava.repository.criteriaapi.UserRepository;
import org.springframework.ui.Model;

/**
 * Контроллер для работы с пользователями
 */
@Controller
@RequestMapping(Constants.BASE_WEB_HTML_ENDPOINT + "/users")
public class UserController {
    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/list")
    public String userListView(Model model)
    {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "userList";
    }
}
