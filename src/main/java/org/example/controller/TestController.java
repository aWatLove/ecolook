package org.example.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/test/user")
    public ResponseEntity<?> test1() {
        return ResponseEntity.ok("hello user");
    }

    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/test/admin")
    public ResponseEntity<?> test2() {
        return ResponseEntity.ok("hello admin");
    }

    @SecurityRequirement(name = "JWT")
    @GetMapping("/test/any")
    public ResponseEntity<?> test3() {
        return ResponseEntity.ok("hello anyone");
    }
}
