package com.example.demo.controller;

import com.example.demo.configuration.BotConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.bot.MonkeyServiceBot;

@SpringBootApplication

@RestController

public class AccessControler {

    @GetMapping("/get-access")
    public String getAccess(){


        MonkeyServiceBot.INSTANCE.accessCommand(1978715207L,"Пользователь запрашивает у вас доступ");

        return "okk";
    }
}


