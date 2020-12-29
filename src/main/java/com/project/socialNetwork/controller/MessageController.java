package com.project.socialNetwork.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.project.socialNetwork.domain.Messages;
import com.project.socialNetwork.domain.Views;
import com.project.socialNetwork.repo.MessageRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("message")
public class MessageController {

    private final MessageRepo messageRepo;

    public MessageController(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    @GetMapping
    @JsonView(Views.IdName.class)
    public List<Messages> list(){
        return  messageRepo.findAll();
    }

    @GetMapping("{id}")
    public Messages getOne(@PathVariable("id") Messages message){
        return message;

    }

    @PostMapping
    public Messages createBody(@RequestBody Messages message){
        message.setCreationDate(LocalDateTime.now());
        return messageRepo.save(message);
    }

    @PutMapping("{id}")
    public Messages update(@PathVariable("id") Messages messageFromDb, @RequestBody Messages message){
        BeanUtils.copyProperties(message,messageFromDb,"id");
        return messageRepo.save(messageFromDb);
    }

    @DeleteMapping("{id}")
    public  void delete(@PathVariable("id") Messages message){
        messageRepo.delete(message);
    }

}
