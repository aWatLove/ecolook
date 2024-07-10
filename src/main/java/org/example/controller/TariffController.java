package org.example.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.example.dto.payment.request.TariffSaveRequest;
import org.example.service.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tariff")
public class TariffController {
    @Autowired
    TariffService tariffService;

    @SecurityRequirement(name = "JWT")
    @GetMapping
    public ResponseEntity<?> getAllTariffs() {
        return ResponseEntity.ok(tariffService.getAllTariffs());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "JWT")
    @PostMapping
    public ResponseEntity<?> createTariff(@RequestBody TariffSaveRequest body) {
        return ResponseEntity.ok(tariffService.createTariff(body));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "JWT")
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateTariff(@PathVariable Long id, @RequestBody TariffSaveRequest body) {
        return ResponseEntity.ok(tariffService.updateTariff(id, body));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTariff(@PathVariable Long id) {
        tariffService.deleteTariff(id);
        return ResponseEntity.ok().build();
    }
}
