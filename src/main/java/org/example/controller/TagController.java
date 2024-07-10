package org.example.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.example.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tag")
public class TagController {
    @Autowired
    TagService tagService;

    @SecurityRequirement(name = "JWT")
    @GetMapping
    public ResponseEntity<?> getAllTags() {
        return ResponseEntity.ok(tagService.getAllTags());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "JWT")
    @PostMapping
    public ResponseEntity<?> createTag(@RequestParam String name) {
        return ResponseEntity.ok(tagService.createTag(name));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.ok().build();
    }
}
