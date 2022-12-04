package edu.ifgoiano.example.events.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ifgoiano.example.events.models.Attractions;
import edu.ifgoiano.example.events.repository.AttractionRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Service
public class AttractionService 
{
    @Autowired
    AttractionRepository attractionRepository;
    
    @Transactional
    public Attractions save(Attractions obj) 
    {
        return attractionRepository.save( obj );
    }

    public Optional<Attractions> findByAttractionsname(String name) 
    {
        return attractionRepository.findByName( name );
    }

    public Boolean existsByAttractionsname(String name)
    {
        return attractionRepository.existsByName( name );
    }

    public Optional<Attractions> findByAttractionsID(UUID id) 
    {
        return attractionRepository.findById( id );
    }

    public Boolean existsByAttractionsID(UUID  id )
    {
        return attractionRepository.existsById( id );
    }   

    @Transactional
    public void delete(Attractions Attractions) 
    {
        attractionRepository.delete(Attractions);
    }

    public List<Attractions> findAll()
    {
        return attractionRepository.findAll();
    }

    public void deleteAll(List<Attractions> Attractions)
    {
        List<Attractions> allAttractions = attractionRepository.findAll();
        attractionRepository.deleteAll( allAttractions);
    }
}
