package edu.scnu.wiki.controller;

import edu.scnu.wiki.domain.Test;
import edu.scnu.wiki.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Autowired
    private TestService testService;
    @GetMapping("/hello")
    public String hello(){
        return "hello World";
    }
    @PostMapping("/hello/post")
    public String helloPost(@RequestParam("name") String name){
        return "hello World" +name;
    }

    @GetMapping("/hello/list")
    public List<Test> testList(){
        return testService.list();
    }
}
