package com.seong.bks.controller;

import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class BksController {
    public static String apiUrl = "siteName";

    public String login(String Id, String pw) {
        System.out.println("login start!");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", "test");
        params.add("userId", Id);
        params.add("userPwd", pw);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl + "/login/action", params, String.class);
        String[] cookieData = String.valueOf(response.getHeaders().get("Set-Cookie")).split(";");
        String cookie = cookieData[0].replace("[", "").replace("]", "");
        System.out.println("[login] Status Code : " + response.getStatusCode());

        return cookie;
    }

    public void bookingEvent(String cookie, String resource, String id, String time) {
        System.out.println("booking Massage start!");
        HttpHeaders headers = new HttpHeaders();
        headers.set("cookie", cookie);

        LocalDateTime nextWeek = LocalDateTime.now();
        nextWeek = nextWeek.plusDays(7);
        String nextWeekDate = nextWeek.format(DateTimeFormatter.ISO_DATE);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        System.out.println("예약일 : " + nextWeekDate);
        params.add("resourceId", resource);
        params.add("userId", id);
        params.add("time", time);
        params.add("startDate", nextWeekDate);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl + "/booking/create", request, String.class);

        System.out.println("[booking massage] Status Code : " + response.getStatusCode());
        System.out.println("[booking massage] Status Code : " + response.getBody());
    }

}
