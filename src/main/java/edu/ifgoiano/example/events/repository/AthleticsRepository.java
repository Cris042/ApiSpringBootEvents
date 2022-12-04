package edu.ifgoiano.example.events.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.ifgoiano.example.events.models.Athletics;

public interface AthleticsRepository extends JpaRepository<Athletics, UUID> 
{
    Boolean existsByName(String name);
    Optional<Athletics> findByName(String name);
    boolean existsById(UUID id);
    Optional<Athletics> findById(UUID id);
}