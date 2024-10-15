package br.com.fiap.contatos.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/en")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("pt-br")
    public String ola() {
        return "<h1>Olá Mundo</h1>";
    }

}
