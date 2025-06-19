package br.univille.app_b.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api")
public class HomeController {

  // ******** APPLICAÇÃO B ************

  @GetMapping()
  public ResponseEntity index() {
    System.out.println("Hello from App B");
    return ResponseEntity.ok().body("Hello from App B");
  }
}