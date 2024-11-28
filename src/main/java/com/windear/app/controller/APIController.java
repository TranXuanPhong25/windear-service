package com.windear.app.controller;

import com.windear.app.model.AnalyticStat;
import com.windear.app.model.Message;
import com.windear.app.repository.InternalBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "api", produces = MediaType.APPLICATION_JSON_VALUE)
// For simplicity of this sample, allow all origins. Real applications should configure CORS for their use case.
public class APIController {

   @GetMapping(value = "/public")
   public Message publicEndpoint() {
      return new Message("All good. You DO NOT need to be authenticated to call /api/public.");
   }
   
   @GetMapping(value = "/private")
   public Message privateEndpoint() {
      return new Message("All good. You can see this because you are Authenticated.");
   }
   
//   @GetMapping(value = "/private-scoped")
//   public Message privateScopedEndpoint() {
//      return new Message("All good. You can see this because you are Authenticated with a Token granted the 'read:messages' scope");
//   }
   
   @GetMapping(value = "/admin")
   public Message adminRoleBasedEndpoint() {
      return new Message("admin message");
   }
}