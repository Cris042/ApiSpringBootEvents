package edu.ifgoiano.example.events.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ifgoiano.example.events.models.Events;
import edu.ifgoiano.example.events.repository.EventRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Service
public class EventService 
{
    @Autowired
    EventRepository eventRepository;
    
    @Transactional
    public Events save(Events obj) 
    {
        return eventRepository.save( obj );
    }

    public Optional<Events> findByEventsname(String name) 
    {
        return eventRepository.findByName( name );
    }

    public Boolean existsByEventsname(String name)
    {
        return eventRepository.existsByName( name );
    }

    public Optional<Events> findByEventsID(UUID id) 
    {
        return eventRepository.findById( id );
    }

    public Boolean existsByEventsID(UUID  id )
    {
        return eventRepository.existsById( id );
    }   

    @Transactional
    public void delete(Events Events) 
    {
        eventRepository.delete(Events);
    }

    public List<Events> findAll()
    {
        return eventRepository.findAll();
    }

    public void deleteAll(List<Events> Events)
    {
        List<Events> allEvents = eventRepository.findAll();
        eventRepository.deleteAll( allEvents);
    }

}
