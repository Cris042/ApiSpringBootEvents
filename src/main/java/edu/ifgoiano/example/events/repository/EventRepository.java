package edu.ifgoiano.example.events.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.ifgoiano.example.events.models.Events;

public interface EventRepository extends JpaRepository<Events, UUID> 
{
    Boolean existsByName(String name);
    Optional<Events> findByName(String name);
    boolean existsById(UUID id);
    Optional<Events> findById(UUID id);
}
