package edu.ifgoiano.example.events.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.ifgoiano.example.events.models.Image;


@Repository
public interface ImageRepository extends JpaRepository<Image, UUID> 
{
    Optional<Image> findByName(String name);
}
