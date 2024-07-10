package org.example.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.example.service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/option")
public class OptionsController {
    @Autowired
    OptionService optionService;

    @SecurityRequirement(name = "JWT")
    @GetMapping
    public ResponseEntity<?> getAllOptions() {
        return ResponseEntity.ok(optionService.getAllOptions());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "JWT")
    @PostMapping
    public ResponseEntity<?> createOption(@RequestParam String title, @RequestParam Double price) {
        return ResponseEntity.ok(optionService.createOption(title, price));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOption(@PathVariable Long id) {
        optionService.deleteOption(id);
        return ResponseEntity.ok().build();
    }
}
