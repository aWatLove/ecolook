package org.example.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.example.dto.payment.request.UserInfoUpdateRequest;
import org.example.security.jwt.JwtUtils;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService userService;

    @SecurityRequirement(name = "JWT")
    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(HttpServletRequest request) {
        Long userId = jwtUtils.getUserIdFromJwtToken(jwtUtils.extractJwtToken(request));
        return ResponseEntity.ok(userService.getUserInfoById(userId));
    }

    @SecurityRequirement(name = "JWT")
    @PatchMapping
    public ResponseEntity<?> updateUserInfo(@RequestBody UserInfoUpdateRequest body, HttpServletRequest request) {
        Long userId = jwtUtils.getUserIdFromJwtToken(jwtUtils.extractJwtToken(request));
        return ResponseEntity.ok(userService.updateUserInfo(body, userId));
    }


}
