package com.sudzey.sudzey.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/superadmin")
public class SuperAdmin {
    @GetMapping()
    public ResponseEntity<String> sayHi() {
        return ResponseEntity.ok("HI SUPER ADMIN");
    }
}