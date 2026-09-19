package com.eleveurpro.controller;

import com.eleveurpro.dto.AchatRequest;
import com.eleveurpro.dto.AchatResponse;
import com.eleveurpro.dto.VenteRequest;
import com.eleveurpro.dto.VenteResponse;
import com.eleveurpro.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Achats & Ventes", description = "CRUD des achats et ventes")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // ==================== Achats ====================

    @GetMapping("/achats")
    @Operation(summary = "Lister tous les achats")
    public ResponseEntity<List<AchatResponse>> getAllAchats() {
        return ResponseEntity.ok(transactionService.getAllAchats());
    }

    @GetMapping("/achats/{id}")
    @Operation(summary = "Récupérer un achat par id")
    public ResponseEntity<AchatResponse> getAchatById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getAchatById(id));
    }

    @PostMapping("/achats")
    @PreAuthorize("hasAnyRole('ADMIN','ELEVEUR')")
    @Operation(summary = "Créer un achat")
    public ResponseEntity<AchatResponse> createAchat(@Valid @RequestBody AchatRequest request) {
        return ResponseEntity.ok(transactionService.createAchat(request));
    }

    @PutMapping("/achats/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ELEVEUR')")
    @Operation(summary = "Modifier un achat")
    public ResponseEntity<AchatResponse> updateAchat(@PathVariable Long id, @Valid @RequestBody AchatRequest request) {
        return ResponseEntity.ok(transactionService.updateAchat(id, request));
    }

    @DeleteMapping("/achats/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ELEVEUR')")
    @Operation(summary = "Supprimer un achat")
    public ResponseEntity<Void> deleteAchat(@PathVariable Long id) {
        transactionService.deleteAchat(id);
        return ResponseEntity.noContent().build();
    }

    // ==================== Ventes ====================

    @GetMapping("/ventes")
    @Operation(summary = "Lister toutes les ventes")
    public ResponseEntity<List<VenteResponse>> getAllVentes() {
        return ResponseEntity.ok(transactionService.getAllVentes());
    }

    @GetMapping("/ventes/{id}")
    @Operation(summary = "Récupérer une vente par id")
    public ResponseEntity<VenteResponse> getVenteById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getVenteById(id));
    }

    @PostMapping("/ventes")
    @PreAuthorize("hasAnyRole('ADMIN','ELEVEUR')")
    @Operation(summary = "Créer une vente")
    public ResponseEntity<VenteResponse> createVente(@Valid @RequestBody VenteRequest request) {
        return ResponseEntity.ok(transactionService.createVente(request));
    }

    @PutMapping("/ventes/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ELEVEUR')")
    @Operation(summary = "Modifier une vente")
    public ResponseEntity<VenteResponse> updateVente(@PathVariable Long id, @Valid @RequestBody VenteRequest request) {
        return ResponseEntity.ok(transactionService.updateVente(id, request));
    }

    @DeleteMapping("/ventes/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ELEVEUR')")
    @Operation(summary = "Supprimer une vente")
    public ResponseEntity<Void> deleteVente(@PathVariable Long id) {
        transactionService.deleteVente(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/ventes/{id}/confirmer")
    @PreAuthorize("hasAnyRole('ADMIN','ELEVEUR')")
    @Operation(summary = "Confirmer une vente (change le statut de l'animal à VENDU et enregistre un revenu)")
    public ResponseEntity<Void> confirmerVente(@PathVariable Long id) {
        transactionService.confirmerVente(id);
        return ResponseEntity.ok().build();
    }
}
