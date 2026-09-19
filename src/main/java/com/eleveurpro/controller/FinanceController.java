package com.eleveurpro.controller;

import com.eleveurpro.dto.DepenseRequest;
import com.eleveurpro.dto.DepenseResponse;
import com.eleveurpro.dto.RevenuRequest;
import com.eleveurpro.dto.RevenuResponse;
import com.eleveurpro.service.FinanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Finances", description = "CRUD des dépenses et revenus")
public class FinanceController {

    private final FinanceService financeService;

    public FinanceController(FinanceService financeService) {
        this.financeService = financeService;
    }

    // ==================== Dépenses ====================

    @GetMapping("/depenses")
    @Operation(summary = "Lister toutes les dépenses")
    public ResponseEntity<List<DepenseResponse>> getAllDepenses() {
        return ResponseEntity.ok(financeService.getAllDepenses());
    }

    @GetMapping("/depenses/{id}")
    @Operation(summary = "Récupérer une dépense par id")
    public ResponseEntity<DepenseResponse> getDepenseById(@PathVariable Long id) {
        return ResponseEntity.ok(financeService.getDepenseById(id));
    }

    @PostMapping("/depenses")
    @PreAuthorize("hasAnyRole('ADMIN','ELEVEUR')")
    @Operation(summary = "Créer une dépense")
    public ResponseEntity<DepenseResponse> createDepense(@Valid @RequestBody DepenseRequest request) {
        return ResponseEntity.ok(financeService.createDepense(request));
    }

    @PutMapping("/depenses/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ELEVEUR')")
    @Operation(summary = "Modifier une dépense")
    public ResponseEntity<DepenseResponse> updateDepense(@PathVariable Long id, @Valid @RequestBody DepenseRequest request) {
        return ResponseEntity.ok(financeService.updateDepense(id, request));
    }

    @DeleteMapping("/depenses/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ELEVEUR')")
    @Operation(summary = "Supprimer une dépense")
    public ResponseEntity<Void> deleteDepense(@PathVariable Long id) {
        financeService.deleteDepense(id);
        return ResponseEntity.noContent().build();
    }

    // ==================== Revenus ====================

    @GetMapping("/revenus")
    @Operation(summary = "Lister tous les revenus")
    public ResponseEntity<List<RevenuResponse>> getAllRevenus() {
        return ResponseEntity.ok(financeService.getAllRevenus());
    }

    @GetMapping("/revenus/{id}")
    @Operation(summary = "Récupérer un revenu par id")
    public ResponseEntity<RevenuResponse> getRevenuById(@PathVariable Long id) {
        return ResponseEntity.ok(financeService.getRevenuById(id));
    }

    @PostMapping("/revenus")
    @PreAuthorize("hasAnyRole('ADMIN','ELEVEUR')")
    @Operation(summary = "Créer un revenu")
    public ResponseEntity<RevenuResponse> createRevenu(@Valid @RequestBody RevenuRequest request) {
        return ResponseEntity.ok(financeService.createRevenu(request));
    }

    @PutMapping("/revenus/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ELEVEUR')")
    @Operation(summary = "Modifier un revenu")
    public ResponseEntity<RevenuResponse> updateRevenu(@PathVariable Long id, @Valid @RequestBody RevenuRequest request) {
        return ResponseEntity.ok(financeService.updateRevenu(id, request));
    }

    @DeleteMapping("/revenus/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ELEVEUR')")
    @Operation(summary = "Supprimer un revenu")
    public ResponseEntity<Void> deleteRevenu(@PathVariable Long id) {
        financeService.deleteRevenu(id);
        return ResponseEntity.noContent().build();
    }
}
