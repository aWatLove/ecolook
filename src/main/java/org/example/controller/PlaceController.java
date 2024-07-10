package org.example.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import net.bytebuddy.implementation.bind.annotation.Default;
import org.example.security.jwt.JwtUtils;
import org.example.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
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

}
