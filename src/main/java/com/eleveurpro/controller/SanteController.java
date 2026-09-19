package com.eleveurpro.controller;

import com.eleveurpro.dto.*;
import com.eleveurpro.service.SanteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Santé", description = "CRUD maladies, consultations, vaccinations, traitements")
public class SanteController {

    private final SanteService santeService;

    public SanteController(SanteService santeService) {
        this.santeService = santeService;
    }

    // ==================== Maladies ====================

    @GetMapping("/maladies")
    @Operation(summary = "Lister toutes les maladies")
    public ResponseEntity<List<MaladieResponse>> getAllMaladies() {
        return ResponseEntity.ok(santeService.getAllMaladies());
    }

    @GetMapping("/maladies/{id}")
    @Operation(summary = "Récupérer une maladie par id")
    public ResponseEntity<MaladieResponse> getMaladieById(@PathVariable Long id) {
        return ResponseEntity.ok(santeService.getMaladieById(id));
    }

    @PostMapping("/maladies")
    @PreAuthorize("hasAnyRole('ADMIN','ELEVEUR','VETERINAIRE')")
    @Operation(summary = "Créer une maladie")
    public ResponseEntity<MaladieResponse> createMaladie(@Valid @RequestBody MaladieRequest request) {
        return ResponseEntity.ok(santeService.createMaladie(request));
    }

    @PutMapping("/maladies/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ELEVEUR','VETERINAIRE')")
    @Operation(summary = "Modifier une maladie")
    public ResponseEntity<MaladieResponse> updateMaladie(@PathVariable Long id, @Valid @RequestBody MaladieRequest request) {
        return ResponseEntity.ok(santeService.updateMaladie(id, request));
    }

    @DeleteMapping("/maladies/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','VETERINAIRE')")
    @Operation(summary = "Supprimer une maladie")
    public ResponseEntity<Void> deleteMaladie(@PathVariable Long id) {
        santeService.deleteMaladie(id);
        return ResponseEntity.noContent().build();
    }

    // ==================== Consultations ====================

    @GetMapping("/consultations")
    @Operation(summary = "Lister toutes les consultations")
    public ResponseEntity<List<ConsultationSanteResponse>> getAllConsultations() {
        return ResponseEntity.ok(santeService.getAllConsultations());
    }

    @GetMapping("/consultations/{id}")
    @Operation(summary = "Récupérer une consultation par id")
    public ResponseEntity<ConsultationSanteResponse> getConsultationById(@PathVariable Long id) {
        return ResponseEntity.ok(santeService.getConsultationById(id));
    }

    @PostMapping("/consultations")
    @PreAuthorize("hasAnyRole('ADMIN','ELEVEUR','VETERINAIRE')")
    @Operation(summary = "Créer une consultation")
    public ResponseEntity<ConsultationSanteResponse> createConsultation(@Valid @RequestBody ConsultationSanteRequest request) {
        return ResponseEntity.ok(santeService.createConsultation(request));
    }

    @PutMapping("/consultations/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ELEVEUR','VETERINAIRE')")
    @Operation(summary = "Modifier une consultation")
    public ResponseEntity<ConsultationSanteResponse> updateConsultation(@PathVariable Long id, @Valid @RequestBody ConsultationSanteRequest request) {
        return ResponseEntity.ok(santeService.updateConsultation(id, request));
    }

    @DeleteMapping("/consultations/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','VETERINAIRE')")
    @Operation(summary = "Supprimer une consultation")
    public ResponseEntity<Void> deleteConsultation(@PathVariable Long id) {
        santeService.deleteConsultation(id);
        return ResponseEntity.noContent().build();
    }

    // ==================== Vaccinations ====================

    @GetMapping("/vaccinations")
    @Operation(summary = "Lister toutes les vaccinations")
    public ResponseEntity<List<VaccinationResponse>> getAllVaccinations() {
        return ResponseEntity.ok(santeService.getAllVaccinations());
    }

    @GetMapping("/vaccinations/{id}")
    @Operation(summary = "Récupérer une vaccination par id")
    public ResponseEntity<VaccinationResponse> getVaccinationById(@PathVariable Long id) {
        return ResponseEntity.ok(santeService.getVaccinationById(id));
    }

    @PostMapping("/vaccinations")
    @PreAuthorize("hasAnyRole('ADMIN','ELEVEUR','VETERINAIRE')")
    @Operation(summary = "Créer une vaccination")
    public ResponseEntity<VaccinationResponse> createVaccination(@Valid @RequestBody VaccinationRequest request) {
        return ResponseEntity.ok(santeService.createVaccination(request));
    }

    @PutMapping("/vaccinations/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ELEVEUR','VETERINAIRE')")
    @Operation(summary = "Modifier une vaccination")
    public ResponseEntity<VaccinationResponse> updateVaccination(@PathVariable Long id, @Valid @RequestBody VaccinationRequest request) {
        return ResponseEntity.ok(santeService.updateVaccination(id, request));
    }

    @DeleteMapping("/vaccinations/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','VETERINAIRE')")
    @Operation(summary = "Supprimer une vaccination")
    public ResponseEntity<Void> deleteVaccination(@PathVariable Long id) {
        santeService.deleteVaccination(id);
        return ResponseEntity.noContent().build();
    }

    // ==================== Traitements ====================

    @GetMapping("/traitements")
    @Operation(summary = "Lister tous les traitements")
    public ResponseEntity<List<TraitementResponse>> getAllTraitements() {
        return ResponseEntity.ok(santeService.getAllTraitements());
    }

    @GetMapping("/traitements/{id}")
    @Operation(summary = "Récupérer un traitement par id")
    public ResponseEntity<TraitementResponse> getTraitementById(@PathVariable Long id) {
        return ResponseEntity.ok(santeService.getTraitementById(id));
    }

    @PostMapping("/traitements")
    @PreAuthorize("hasAnyRole('ADMIN','ELEVEUR','VETERINAIRE')")
    @Operation(summary = "Créer un traitement")
    public ResponseEntity<TraitementResponse> createTraitement(@Valid @RequestBody TraitementRequest request) {
        return ResponseEntity.ok(santeService.createTraitement(request));
    }

    @PutMapping("/traitements/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ELEVEUR','VETERINAIRE')")
    @Operation(summary = "Modifier un traitement")
    public ResponseEntity<TraitementResponse> updateTraitement(@PathVariable Long id, @Valid @RequestBody TraitementRequest request) {
        return ResponseEntity.ok(santeService.updateTraitement(id, request));
    }

    @DeleteMapping("/traitements/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','VETERINAIRE')")
    @Operation(summary = "Supprimer un traitement")
    public ResponseEntity<Void> deleteTraitement(@PathVariable Long id) {
        santeService.deleteTraitement(id);
        return ResponseEntity.noContent().build();
    }
}
