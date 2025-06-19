package br.univille.app_a.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api")
public class HomeController {

    //  ******** APPLICAÇÃO A  ************

    @GetMapping()
    public ResponseEntity index() {
        return ResponseEntity.ok().body("Hello from App A");
    }
}