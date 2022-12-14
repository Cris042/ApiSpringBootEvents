package edu.ifgoiano.example.events.service;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import edu.ifgoiano.example.events.models.Participants;
import edu.ifgoiano.example.events.repository.ParticipantRepository;

@Service
public class ParticipantService 
{
    @Autowired
    ParticipantRepository participantsRepository;
    
    @Transactional
    public Participants save(Participants obj) 
    {
        return participantsRepository.save( obj );
    }

    public List<Participants> findAll()
    {
        return participantsRepository.findAll();
    }

    public List<Participants> findByEventID(UUID id) 
    {
        return participantsRepository.findByEventId( id );
    }

    public Boolean existsByUserID(UUID id)
    {
        return  participantsRepository.existsByUserId(id);
    }
}
