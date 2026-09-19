package com.eleveurpro.controller;

import com.eleveurpro.dto.RapportResponse;
import com.eleveurpro.service.RapportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/rapports")
@PreAuthorize("hasAnyRole('ADMIN','ELEVEUR')")
@Tag(name = "Rapports", description = "Rapports de l'exploitation avec filtrage par date")
public class RapportController {

    private final RapportService rapportService;

    public RapportController(RapportService rapportService) {
        this.rapportService = rapportService;
    }

    @GetMapping("/cheptel")
    @Operation(summary = "Rapport du cheptel")
    public ResponseEntity<RapportResponse> rapportCheptel(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(rapportService.rapportCheptel(debut, fin));
    }

    @GetMapping("/ventes")
    @Operation(summary = "Rapport des ventes")
    public ResponseEntity<RapportResponse> rapportVentes(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(rapportService.rapportVentes(debut, fin));
    }

    @GetMapping("/depenses")
    @Operation(summary = "Rapport des dépenses")
    public ResponseEntity<RapportResponse> rapportDepenses(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(rapportService.rapportDepenses(debut, fin));
    }

    @GetMapping("/revenus")
    @Operation(summary = "Rapport des revenus")
    public ResponseEntity<RapportResponse> rapportRevenus(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(rapportService.rapportRevenus(debut, fin));
    }

    @GetMapping("/sante")
    @Operation(summary = "Rapport de santé")
    public ResponseEntity<RapportResponse> rapportSante(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(rapportService.rapportSante(debut, fin));
    }

    @GetMapping("/naissances")
    @Operation(summary = "Rapport des naissances")
    public ResponseEntity<RapportResponse> rapportNaissances(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(rapportService.rapportNaissances(debut, fin));
    }

    @GetMapping("/mortalite")
    @Operation(summary = "Rapport de mortalité")
    public ResponseEntity<RapportResponse> rapportMortalite(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(rapportService.rapportMortalite(debut, fin));
    }

    @GetMapping("/alimentation")
    @Operation(summary = "Rapport d'alimentation")
    public ResponseEntity<RapportResponse> rapportAlimentation(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(rapportService.rapportAlimentation(debut, fin));
    }
}
