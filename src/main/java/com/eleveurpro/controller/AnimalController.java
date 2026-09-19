package com.eleveurpro.controller;

import com.eleveurpro.dto.AnimalRequest;
import com.eleveurpro.dto.AnimalResponse;
import com.eleveurpro.dto.PageResponse;
import com.eleveurpro.entity.enums.StatutAnimal;
import com.eleveurpro.service.AnimalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/animals")
@Tag(name = "Animaux", description = "CRUD et recherche des animaux")
public class AnimalController {

    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping
    @Operation(summary = "Lister tous les animaux")
    public ResponseEntity<List<AnimalResponse>> getAll() {
        return ResponseEntity.ok(animalService.getAll());
    }

    @GetMapping("/page")
    @Operation(summary = "Lister les animaux avec pagination")
    public ResponseEntity<PageResponse<AnimalResponse>> getAllPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(animalService.getAllPaginated(page, size));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un animal par id")
    public ResponseEntity<AnimalResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(animalService.getById(id));
    }

    @GetMapping("/search")
    @Operation(summary = "Rechercher des animaux avec filtres et pagination")
    public ResponseEntity<PageResponse<AnimalResponse>> search(
            @RequestParam(required = false) String numero,
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) Long especeId,
            @RequestParam(required = false) Long raceId,
            @RequestParam(required = false) StatutAnimal statut,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(animalService.search(numero, nom, especeId, raceId, statut, page, size));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','ELEVEUR')")
    @Operation(summary = "Créer un animal")
    public ResponseEntity<AnimalResponse> create(@Valid @RequestBody AnimalRequest request) {
        return ResponseEntity.ok(animalService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ELEVEUR')")
    @Operation(summary = "Modifier un animal")
    public ResponseEntity<AnimalResponse> update(@PathVariable Long id, @Valid @RequestBody AnimalRequest request) {
        return ResponseEntity.ok(animalService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ELEVEUR')")
    @Operation(summary = "Supprimer un animal")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        animalService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
