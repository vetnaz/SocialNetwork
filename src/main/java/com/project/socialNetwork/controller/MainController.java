package com.project.socialNetwork.controller;

import com.project.socialNetwork.domain.User;
import com.project.socialNetwork.repo.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@RequestMapping("/")
public class MainController {
    final MessageRepo messageRepo;

    @Autowired
    public MainController(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    @GetMapping
    public String main(Model model, @AuthenticationPrincipal User user){
        HashMap<Object,Object> data = new HashMap<>();

        data.put("profile",user);
        data.put("messages",messageRepo.findAll());

        model.addAttribute("frontEndData",data);

        return "index";
    }



}
