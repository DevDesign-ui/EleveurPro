package com.eleveurpro.service;

import com.eleveurpro.dto.*;
import com.eleveurpro.entity.*;
import com.eleveurpro.entity.enums.Sexe;
import com.eleveurpro.entity.enums.StatutAnimal;
import com.eleveurpro.exception.ResourceAlreadyExistsException;
import com.eleveurpro.exception.ResourceNotFoundException;
import com.eleveurpro.repository.*;
import com.eleveurpro.service.impl.AnimalServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnimalServiceTest {

    @Mock private AnimalRepository animalRepository;
    @Mock private EspeceRepository especeRepository;
    @Mock private RaceRepository raceRepository;

    @InjectMocks private AnimalServiceImpl animalService;

    private Espece espece;
    private Race race;
    private Animal animal;

    @BeforeEach
    void setUp() {
        espece = Espece.builder().id(1L).nom("Bovin").actif(true).build();
        race = Race.builder().id(1L).nom("Zebu").espece(espece).build();
        animal = Animal.builder()
                .id(1L)
                .numeroIdentification("ANIM-001")
                .nom("TestAnimal")
                .espece(espece)
                .race(race)
                .sexe(Sexe.MALE)
                .statut(StatutAnimal.PRESENT)
                .build();
    }

    @Test
    void create_shouldReturnAnimalResponse() {
        AnimalRequest request = AnimalRequest.builder()
                .numeroIdentification("ANIM-001")
                .nom("TestAnimal")
                .especeId(1L)
                .raceId(1L)
                .sexe(Sexe.MALE)
                .statut(StatutAnimal.PRESENT)
                .build();

        when(animalRepository.existsByNumeroIdentification(anyString())).thenReturn(false);
        when(especeRepository.findById(1L)).thenReturn(Optional.of(espece));
        when(raceRepository.findById(1L)).thenReturn(Optional.of(race));
        when(animalRepository.save(any(Animal.class))).thenReturn(animal);

        AnimalResponse response = animalService.create(request);

        assertNotNull(response);
        assertEquals("ANIM-001", response.getNumeroIdentification());
        assertEquals("Bovin", response.getEspeceNom());
        assertEquals("Zebu", response.getRaceNom());
    }

    @Test
    void create_shouldThrowWhenNumeroExists() {
        AnimalRequest request = AnimalRequest.builder()
                .numeroIdentification("ANIM-001")
                .especeId(1L)
                .sexe(Sexe.MALE)
                .statut(StatutAnimal.PRESENT)
                .build();

        when(animalRepository.existsByNumeroIdentification(anyString())).thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> animalService.create(request));
    }

    @Test
    void getById_shouldReturnAnimal() {
        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));

        AnimalResponse response = animalService.getById(1L);

        assertNotNull(response);
        assertEquals("ANIM-001", response.getNumeroIdentification());
    }

    @Test
    void getById_shouldThrowWhenNotFound() {
        when(animalRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> animalService.getById(99L));
    }

    @Test
    void update_shouldUpdateAnimal() {
        AnimalRequest request = AnimalRequest.builder()
                .numeroIdentification("ANIM-002")
                .nom("UpdatedAnimal")
                .especeId(1L)
                .raceId(1L)
                .sexe(Sexe.FEMELLE)
                .statut(StatutAnimal.PRESENT)
                .build();

        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        when(animalRepository.existsByNumeroIdentification("ANIM-002")).thenReturn(false);
        when(especeRepository.findById(1L)).thenReturn(Optional.of(espece));
        when(raceRepository.findById(1L)).thenReturn(Optional.of(race));
        when(animalRepository.save(any(Animal.class))).thenAnswer(inv -> inv.getArgument(0));

        AnimalResponse response = animalService.update(1L, request);

        assertNotNull(response);
        assertEquals("ANIM-002", response.getNumeroIdentification());
        assertEquals("UpdatedAnimal", response.getNom());
    }

    @Test
    void delete_shouldDeleteAnimal() {
        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        doNothing().when(animalRepository).delete(any(Animal.class));

        animalService.delete(1L);

        verify(animalRepository, times(1)).delete(animal);
    }

    @Test
    void getAllPaginated_shouldReturnPage() {
        Page<Animal> page = new PageImpl<>(List.of(animal), PageRequest.of(0, 10), 1);
        when(animalRepository.findAll(any(PageRequest.class))).thenReturn(page);

        PageResponse<AnimalResponse> response = animalService.getAllPaginated(0, 10);

        assertNotNull(response);
        assertEquals(1, response.getContent().size());
        assertEquals(1, response.getTotalElements());
        assertEquals(1, response.getTotalPages());
        assertTrue(response.isLast());
    }
}
