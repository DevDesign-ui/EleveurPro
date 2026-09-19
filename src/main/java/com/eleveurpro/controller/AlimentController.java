package com.eleveurpro.controller;

import com.eleveurpro.dto.AlimentRequest;
import com.eleveurpro.dto.AlimentResponse;
import com.eleveurpro.dto.ConsommationAlimentRequest;
import com.eleveurpro.dto.ConsommationAlimentResponse;
import com.eleveurpro.service.AlimentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Alimentation", description = "CRUD des aliments et consommations")
public class AlimentController {

    private final AlimentService alimentService;

    public AlimentController(AlimentService alimentService) {
        this.alimentService = alimentService;
    }

    // ==================== Aliments ====================

    @GetMapping("/aliments")
    @Operation(summary = "Lister tous les aliments")
    public ResponseEntity<List<AlimentResponse>> getAllAliments() {
        return ResponseEntity.ok(alimentService.getAll());
    }

    @GetMapping("/aliments/stock-bas")
    @Operation(summary = "Lister les aliments avec stock bas")
    public ResponseEntity<List<AlimentResponse>> getStockBas() {
        return ResponseEntity.ok(alimentService.getStockBas());
    }

    @GetMapping("/aliments/{id}")
    @Operation(summary = "Récupérer un aliment par id")
    public ResponseEntity<AlimentResponse> getAlimentById(@PathVariable Long id) {
        return ResponseEntity.ok(alimentService.getById(id));
    }

    @PostMapping("/aliments")
    @PreAuthorize("hasAnyRole('ADMIN','ELEVEUR')")
    @Operation(summary = "Créer un aliment")
    public ResponseEntity<AlimentResponse> createAliment(@Valid @RequestBody AlimentRequest request) {
        return ResponseEntity.ok(alimentService.create(request));
    }

    @PutMapping("/aliments/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ELEVEUR')")
    @Operation(summary = "Modifier un aliment")
    public ResponseEntity<AlimentResponse> updateAliment(@PathVariable Long id, @Valid @RequestBody AlimentRequest request) {
        return ResponseEntity.ok(alimentService.update(id, request));
    }

    @DeleteMapping("/aliments/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ELEVEUR')")
    @Operation(summary = "Supprimer un aliment")
    public ResponseEntity<Void> deleteAliment(@PathVariable Long id) {
        alimentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ==================== Consommations ====================

    @GetMapping("/consommations")
    @Operation(summary = "Lister toutes les consommations")
    public ResponseEntity<List<ConsommationAlimentResponse>> getAllConsommations() {
        return ResponseEntity.ok(alimentService.getAllConsommations());
    }

    @GetMapping("/consommations/animal/{animalId}")
    @Operation(summary = "Lister les consommations d'un animal")
    public ResponseEntity<List<ConsommationAlimentResponse>> getConsommationsByAnimal(@PathVariable Long animalId) {
        return ResponseEntity.ok(alimentService.getConsommationsByAnimal(animalId));
    }

    @PostMapping("/consommations")
    @PreAuthorize("hasAnyRole('ADMIN','ELEVEUR')")
    @Operation(summary = "Enregistrer une consommation (diminue automatiquement le stock)")
    public ResponseEntity<ConsommationAlimentResponse> createConsommation(@Valid @RequestBody ConsommationAlimentRequest request) {
        return ResponseEntity.ok(alimentService.createConsommation(request));
    }
}
