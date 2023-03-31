package com.example.coffeemember.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoffeeMemberRestController {

    @GetMapping("/member")
    public String hello(){
        return "coffee-member service";
    }
}
