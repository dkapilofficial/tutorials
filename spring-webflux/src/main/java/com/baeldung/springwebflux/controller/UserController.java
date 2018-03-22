package com.baeldung.springwebflux.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class UserController {

    @GetMapping(path = "/users/{name}")
    public Mono<String> getName(@PathVariable String name) {
        return Mono.just(name);
    }
}
