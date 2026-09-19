package com.eleveurpro.controller;

import com.eleveurpro.dto.EspeceRequest;
import com.eleveurpro.dto.EspeceResponse;
import com.eleveurpro.dto.PageResponse;
import com.eleveurpro.service.EspeceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/especes")
@Tag(name = "Espèces", description = "CRUD des espèces")
public class EspeceController {

    private final EspeceService especeService;

    public EspeceController(EspeceService especeService) {
        this.especeService = especeService;
    }

    @GetMapping
    @Operation(summary = "Lister toutes les espèces")
    public ResponseEntity<List<EspeceResponse>> getAll() {
        return ResponseEntity.ok(especeService.getAll());
    }

    @GetMapping("/page")
    @Operation(summary = "Lister les espèces avec pagination")
    public ResponseEntity<PageResponse<EspeceResponse>> getAllPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(especeService.getAllPaginated(page, size));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une espèce par id")
    public ResponseEntity<EspeceResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(especeService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','ELEVEUR')")
    @Operation(summary = "Créer une espèce")
    public ResponseEntity<EspeceResponse> create(@Valid @RequestBody EspeceRequest request) {
        return ResponseEntity.ok(especeService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ELEVEUR')")
    @Operation(summary = "Modifier une espèce")
    public ResponseEntity<EspeceResponse> update(@PathVariable Long id, @Valid @RequestBody EspeceRequest request) {
        return ResponseEntity.ok(especeService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Supprimer une espèce")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        especeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
