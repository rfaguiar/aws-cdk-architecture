package br.com.rfaguiar.awsproject02.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/test")
public class TestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/dog/{name}")
    public String dogTest(@PathVariable String name) {
        LOGGER.info("Test controller - name: {}", name);
        return "Name: ".concat(name);
    }

    @PostMapping("/echo")
    public String echoTest(@RequestBody String message) {
        LOGGER.info("Test controller - echo message: {}", message);
        return "message: ".concat(message);
    }
}
