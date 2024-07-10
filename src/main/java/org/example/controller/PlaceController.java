package org.example.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import net.bytebuddy.implementation.bind.annotation.Default;
import org.example.dto.payment.request.PlaceCreateRequest;
import org.example.security.jwt.JwtUtils;
import org.example.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/place")
public class PlaceController {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    PlaceService placeService;

    @SecurityRequirement(name = "JWT")
    @GetMapping
    public ResponseEntity<?> getAllPlacePreviewsAround(@RequestParam Double XCoordinate, @RequestParam Double YCoordinate, @RequestParam() Double radius) {
        return ResponseEntity.ok(placeService.getPlacesAround(XCoordinate, YCoordinate, radius));
    }


    @SecurityRequirement(name = "JWT")
    @GetMapping("/{id}")
    public ResponseEntity<?> getAllPlaceById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(placeService.getPlaceDetailsById(id));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "JWT")
    @PostMapping
    public ResponseEntity<?> createPlace(@RequestBody PlaceCreateRequest body) {
        return ResponseEntity.ok(placeService.createPlace(body));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "JWT")
    @PatchMapping("/{id}")
    public ResponseEntity<?> updatePlace(@RequestBody PlaceCreateRequest body, @PathVariable Long id) {
        return ResponseEntity.ok(placeService.updatePlace(id, body));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlace(@RequestBody PlaceCreateRequest body, @PathVariable Long id) {
        placeService.deletePlace(id);
        return ResponseEntity.status(200).build();
    }
}
