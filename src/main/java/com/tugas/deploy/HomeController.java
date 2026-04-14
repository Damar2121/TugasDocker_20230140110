package com.tugas.deploy;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
        if ("admin".equals(username) && "20230140110".equals(password)) {
            session.setAttribute("user", username);
            return "redirect:/home";
        }
        model.addAttribute("error", "Username atau Password salah!");
        return "login";
    }

    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        
        List<Mahasiswa> listData = (List<Mahasiswa>) session.getAttribute("listData");
        if (listData == null) {
            listData = new ArrayList<>();
        }
        
        model.addAttribute("listData", listData);
        return "home";
    }

    @GetMapping("/form")
    public String formPage(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        return "form";
    }

    @PostMapping("/submit-form")
    public String submitForm(@RequestParam String nama, @RequestParam String nim, @RequestParam String jenisKelamin, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }

        List<Mahasiswa> listData = (List<Mahasiswa>) session.getAttribute("listData");
        if (listData == null) {
            listData = new ArrayList<>();
        }

        listData.add(new Mahasiswa(nama, nim, jenisKelamin));
        session.setAttribute("listData", listData);

        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
