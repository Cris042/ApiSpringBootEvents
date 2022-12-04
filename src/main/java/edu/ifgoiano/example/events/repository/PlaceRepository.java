package edu.ifgoiano.example.events.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.ifgoiano.example.events.models.Places;

public interface PlaceRepository extends JpaRepository<Places, UUID> 
{
    Boolean existsByName(String name);
    Optional<Places> findByName(String name);
    boolean existsById(UUID id);
    Optional<Places> findById(UUID id);
}
