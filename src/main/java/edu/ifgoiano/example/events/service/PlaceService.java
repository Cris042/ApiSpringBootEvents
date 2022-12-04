package edu.ifgoiano.example.events.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ifgoiano.example.events.models.Places;
import edu.ifgoiano.example.events.repository.PlaceRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Service
public class PlaceService 
{
    @Autowired
    PlaceRepository placeRepository;
    
    @Transactional
    public Places save(Places obj) 
    {
        return placeRepository.save( obj );
    }

    public Optional<Places> findByPlacesname(String name) 
    {
        return placeRepository.findByName( name );
    }

    public Boolean existsByPlacesname(String name)
    {
        return placeRepository.existsByName( name );
    }

    public Optional<Places> findByPlacesID(UUID id) 
    {
        return placeRepository.findById( id );
    }

    public Boolean existsByPlacesID(UUID  id )
    {
        return placeRepository.existsById( id );
    }   

    @Transactional
    public void delete(Places Places) 
    {
        placeRepository.delete(Places);
    }

    public List<Places> findAll()
    {
        return placeRepository.findAll();
    }

    public void deleteAll(List<Places> Places)
    {
        List<Places> allPlaces = placeRepository.findAll();
        placeRepository.deleteAll( allPlaces);
    }
}
