package com.eleveurpro.controller;

import com.eleveurpro.dto.RaceRequest;
import com.eleveurpro.dto.RaceResponse;
import com.eleveurpro.service.RaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/races")
@Tag(name = "Races", description = "CRUD des races")
public class RaceController {

    private final RaceService raceService;

    public RaceController(RaceService raceService) {
        this.raceService = raceService;
    }

    @GetMapping
    @Operation(summary = "Lister toutes les races")
    public ResponseEntity<List<RaceResponse>> getAll() {
        return ResponseEntity.ok(raceService.getAll());
    }

    @GetMapping("/espece/{especeId}")
    @Operation(summary = "Lister les races par espèce")
    public ResponseEntity<List<RaceResponse>> getByEspece(@PathVariable Long especeId) {
        return ResponseEntity.ok(raceService.getByEspeceId(especeId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une race par id")
    public ResponseEntity<RaceResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(raceService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','ELEVEUR')")
    @Operation(summary = "Créer une race")
    public ResponseEntity<RaceResponse> create(@Valid @RequestBody RaceRequest request) {
        return ResponseEntity.ok(raceService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ELEVEUR')")
    @Operation(summary = "Modifier une race")
    public ResponseEntity<RaceResponse> update(@PathVariable Long id, @Valid @RequestBody RaceRequest request) {
        return ResponseEntity.ok(raceService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Supprimer une race")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        raceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
