package edu.ifgoiano.example.events.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.ifgoiano.example.events.models.Images;


@Repository
public interface ImageRepository extends JpaRepository<Images, UUID> 
{
}
