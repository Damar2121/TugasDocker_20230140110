package com.tugas.deploy.controller;

import com.tugas.deploy.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    // List untuk menyimpan data sementara (temporary)
    private static List<User> userList = new ArrayList<>();

    @GetMapping("/")
    public String index() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String username, @RequestParam String password, HttpSession session) {
        // Login sesuai permintaan: admin & nim kamu
        if ("admin".equals(username) && "20230140110".equals(password)) {
            session.setAttribute("loggedInUser", username);
            return "redirect:/home";
        }
        return "redirect:/?error";
    }

    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/";
        }
        model.addAttribute("listData", userList);
        return "home";
    }

    @GetMapping("/form")
    public String form(HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/";
        }
        return "form";
    }

    @PostMapping("/submit-form")
    public String submit(@ModelAttribute User user, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/";
        }
        userList.add(user);
        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}