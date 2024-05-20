//package com.trip.chatbot;
//
//import org.json.JSONObject;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;
//
//@RestController
//@RequestMapping("/chat")
//public class ChatController {
//
//    private final RestTemplate restTemplate = new RestTemplate();
//
//    @PostMapping("/chat")
//    public String chat(@RequestBody String userMessage) {
//        String url = "http://localhost:5000/flask-api/chat";
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        JSONObject json = new JSONObject();
//        json.put("message", userMessage);
//        HttpEntity<String> request = new HttpEntity<>(json.toString(), headers);
//        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
//        return response.getBody();
//    }
//}


