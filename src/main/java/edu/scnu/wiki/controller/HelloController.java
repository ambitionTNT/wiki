package edu.scnu.wiki.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author long
 * @version 1.0
 * @ClassName HellloController
 * @description: TODO
 * @date 2023/9/23 15:31
 */
@RestController
//@Controller
public class
HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "hello World";
    }
    @PostMapping("/hello/post")
    public String helloPost(@RequestParam("name") String name){
        return "hello World" +name;
    }
}
