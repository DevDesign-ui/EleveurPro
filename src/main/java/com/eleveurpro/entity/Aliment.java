package com.eleveurpro.entity;

import com.eleveurpro.entity.enums.TypeAliment;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "aliments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Aliment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nom;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TypeAliment type;

    @Column(nullable = false, length = 20)
    private String unite;

    @Column(name = "quantite_stock", nullable = false)
    private Double quantiteStock = 0.0;

    @Column(name = "prix_unitaire", precision = 12, scale = 2)
    private BigDecimal prixUnitaire;

    @Column(name = "seuil_alerte")
    private Double seuilAlerte;

    @Column(name = "date_expiration")
    private LocalDate dateExpiration;

    @Column(length = 200)
    private String fournisseur;

    @Column(nullable = false)
    private Boolean actif = true;
}
