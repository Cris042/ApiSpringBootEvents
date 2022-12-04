package edu.ifgoiano.example.events.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.ifgoiano.example.events.models.Categorys;


public interface CategoryRepository extends JpaRepository<Categorys, UUID> 
{
    Boolean existsByName(String name);
    Optional<Categorys> findByName(String name);
    boolean existsById(UUID id);
    Optional<Categorys> findById(UUID id);
}
