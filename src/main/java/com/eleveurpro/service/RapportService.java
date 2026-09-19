package com.eleveurpro.service;

import com.eleveurpro.dto.RapportResponse;

import java.time.LocalDate;

public interface RapportService {
    RapportResponse rapportCheptel(LocalDate debut, LocalDate fin);
    RapportResponse rapportVentes(LocalDate debut, LocalDate fin);
    RapportResponse rapportDepenses(LocalDate debut, LocalDate fin);
    RapportResponse rapportRevenus(LocalDate debut, LocalDate fin);
    RapportResponse rapportSante(LocalDate debut, LocalDate fin);
    RapportResponse rapportNaissances(LocalDate debut, LocalDate fin);
    RapportResponse rapportMortalite(LocalDate debut, LocalDate fin);
    RapportResponse rapportAlimentation(LocalDate debut, LocalDate fin);
}
