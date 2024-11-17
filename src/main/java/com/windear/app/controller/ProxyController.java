package com.windear.app.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@RestController
public class ProxyController {
   @Value("${thirdparty.baseApiUrl}")
   private String baseApiUrl;
   private final RestTemplate restTemplate = new RestTemplate();
   
   @GetMapping("/api/search")
   public ResponseEntity<String> searchBooks(@RequestParam String q) {
      String url = baseApiUrl+ q;
      ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
      return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
   }
}