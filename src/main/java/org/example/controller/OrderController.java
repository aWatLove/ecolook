package org.example.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.example.dto.payment.request.OrderCreateRequest;
import org.example.model.EStatus;
import org.example.security.jwt.JwtUtils;
import org.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    OrderService orderService;

    @SecurityRequirement(name = "JWT")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @SecurityRequirement(name = "JWT")
    @GetMapping
    public ResponseEntity<?> getAllUsersOrders(HttpServletRequest request) {
        Long userId = jwtUtils.getUserIdFromJwtToken(jwtUtils.extractJwtToken(request));
        return ResponseEntity.ok(orderService.getAllOrderPreviews(userId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "JWT")
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getAllOrdersByUserId(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getAllOrderPreviews(id));
    }

    @SecurityRequirement(name = "JWT")
    @PostMapping
    public ResponseEntity<?> createOrder(OrderCreateRequest body, HttpServletRequest request) {
        Long userId = jwtUtils.getUserIdFromJwtToken(jwtUtils.extractJwtToken(request));
        return ResponseEntity.ok(orderService.createOrder(userId, body));
    }

    @SecurityRequirement(name = "JWT")
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable Long id) {
        orderService.changeStatus(id, EStatus.CANCELED.getTitle());
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "JWT")
    @PatchMapping("/{id}/process")
    public ResponseEntity<?> processOrder(@RequestParam String status, @PathVariable Long id) {
        orderService.changeStatus(id, status);
        return ResponseEntity.ok().build();
    }

}
