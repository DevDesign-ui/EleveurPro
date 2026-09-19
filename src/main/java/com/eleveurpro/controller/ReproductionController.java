package com.eleveurpro.controller;

import com.eleveurpro.dto.NaissanceRequest;
import com.eleveurpro.dto.NaissanceResponse;
import com.eleveurpro.dto.ReproductionRequest;
import com.eleveurpro.dto.ReproductionResponse;
import com.eleveurpro.service.ReproductionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Reproduction & Naissances", description = "CRUD reproductions et naissances")
public class ReproductionController {

    private final ReproductionService reproductionService;

    public ReproductionController(ReproductionService reproductionService) {
        this.reproductionService = reproductionService;
    }

    // ==================== Reproductions ====================

    @GetMapping("/reproductions")
    @Operation(summary = "Lister toutes les reproductions")
    public ResponseEntity<List<ReproductionResponse>> getAllReproductions() {
        return ResponseEntity.ok(reproductionService.getAllReproductions());
    }

    @GetMapping("/reproductions/{id}")
    @Operation(summary = "Récupérer une reproduction par id")
    public ResponseEntity<ReproductionResponse> getReproductionById(@PathVariable Long id) {
        return ResponseEntity.ok(reproductionService.getReproductionById(id));
    }

    @PostMapping("/reproductions")
    @PreAuthorize("hasAnyRole('ADMIN','ELEVEUR')")
    @Operation(summary = "Créer une reproduction")
    public ResponseEntity<ReproductionResponse> createReproduction(@Valid @RequestBody ReproductionRequest request) {
        return ResponseEntity.ok(reproductionService.createReproduction(request));
    }

    @PutMapping("/reproductions/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ELEVEUR')")
    @Operation(summary = "Modifier une reproduction")
    public ResponseEntity<ReproductionResponse> updateReproduction(@PathVariable Long id, @Valid @RequestBody ReproductionRequest request) {
        return ResponseEntity.ok(reproductionService.updateReproduction(id, request));
    }

    @DeleteMapping("/reproductions/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ELEVEUR')")
    @Operation(summary = "Supprimer une reproduction")
    public ResponseEntity<Void> deleteReproduction(@PathVariable Long id) {
        reproductionService.deleteReproduction(id);
        return ResponseEntity.noContent().build();
    }

    // ==================== Naissances ====================

    @GetMapping("/naissances")
    @Operation(summary = "Lister toutes les naissances")
    public ResponseEntity<List<NaissanceResponse>> getAllNaissances() {
        return ResponseEntity.ok(reproductionService.getAllNaissances());
    }

    @GetMapping("/naissances/{id}")
    @Operation(summary = "Récupérer une naissance par id")
    public ResponseEntity<NaissanceResponse> getNaissanceById(@PathVariable Long id) {
        return ResponseEntity.ok(reproductionService.getNaissanceById(id));
    }

    @PostMapping("/naissances")
    @PreAuthorize("hasAnyRole('ADMIN','ELEVEUR')")
    @Operation(summary = "Créer une naissance (possibilité de créer automatiquement les nouveaux animaux)")
    public ResponseEntity<NaissanceResponse> createNaissance(@Valid @RequestBody NaissanceRequest request) {
        return ResponseEntity.ok(reproductionService.createNaissance(request));
    }

    @PutMapping("/naissances/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ELEVEUR')")
    @Operation(summary = "Modifier une naissance")
    public ResponseEntity<NaissanceResponse> updateNaissance(@PathVariable Long id, @Valid @RequestBody NaissanceRequest request) {
        return ResponseEntity.ok(reproductionService.updateNaissance(id, request));
    }

    @DeleteMapping("/naissances/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ELEVEUR')")
    @Operation(summary = "Supprimer une naissance")
    public ResponseEntity<Void> deleteNaissance(@PathVariable Long id) {
        reproductionService.deleteNaissance(id);
        return ResponseEntity.noContent().build();
    }
}
