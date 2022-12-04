package edu.ifgoiano.example.events.service;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ifgoiano.example.events.models.Athletics;
import edu.ifgoiano.example.events.repository.AthleticsRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Service
public class AthleticService
{
    @Autowired
    AthleticsRepository athleticsRepository;
    
    @Transactional
    public Athletics save(Athletics obj) 
    {
        return athleticsRepository.save( obj );
    }

    public Optional<Athletics> findByAthleticsname(String name) 
    {
        return athleticsRepository.findByName( name );
    }

    public Boolean existsByAthleticsname(String name)
    {
        return athleticsRepository.existsByName( name );
    }

    public Optional<Athletics> findByAthleticsID(UUID id) 
    {
        return athleticsRepository.findById( id );
    }

    public Boolean existsByAthleticsID(UUID  id )
    {
        return athleticsRepository.existsById( id );
    }   

    @Transactional
    public void delete(Athletics Athletics) 
    {
        athleticsRepository.delete(Athletics);
    }

    public List<Athletics> findAll()
    {
        return athleticsRepository.findAll();
    }

    public void deleteAll(List<Athletics> Athletics)
    {
        List<Athletics> allAthletics = athleticsRepository.findAll();
        athleticsRepository.deleteAll( allAthletics);
    }

    
}
