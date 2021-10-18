package com.kodilla.task.httpconverter;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/converter")
@Slf4j
public class TaskController {

    @PostMapping(consumes = MediaType.TEXT_PLAIN_VALUE)
    public void addCustomObject(@RequestBody ExampleClass input) {
        log.info("Received input: {}", input);
    }
}
