package br.univille.app_a.controller;

import org.springframework.web.bind.annotation.RestController;

import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;
import io.dapr.client.domain.HttpExtension;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api")
public class HomeController {

    //  ******** APPLICAÇÃO A  ************

    @GetMapping()
    public ResponseEntity index() {
        System.out.println("Hello from App A");

        try(DaprClient daprClient = new DaprClientBuilder().build()) {
            
            daprClient.invokeMethod("app-b", "/api", null, HttpExtension.GET).block();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error in Dapr Client");
        }

        return ResponseEntity.ok().body("Hello from App A");
    }
}