package edu.ifgoiano.example.events.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.ifgoiano.example.events.models.Participants;

public interface ParticipantRepository extends JpaRepository<Participants, UUID> 
{
    List<Participants> findByEventId(UUID id);
    boolean existsByUserId(UUID id);
}
