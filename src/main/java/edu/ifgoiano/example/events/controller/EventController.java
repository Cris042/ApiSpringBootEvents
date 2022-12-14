package edu.ifgoiano.example.events.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ifgoiano.example.events.dtos.EventDTO;
import edu.ifgoiano.example.events.dtos.request.IdUserDTO;
import edu.ifgoiano.example.events.exceptions.others.NotFoundException;
import edu.ifgoiano.example.events.exceptions.others.UnsupportedException;
import edu.ifgoiano.example.events.models.Categorys;
import edu.ifgoiano.example.events.models.Events;
import edu.ifgoiano.example.events.models.Participants;
import edu.ifgoiano.example.events.models.Places;
import edu.ifgoiano.example.events.service.CategoryService;
import edu.ifgoiano.example.events.service.EventService;
import edu.ifgoiano.example.events.service.ParticipantService;
import edu.ifgoiano.example.events.service.PlaceService;


@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RestController
@RequestMapping("/api/event/event")
public class EventController 
{
    @Autowired
    EventService eventService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    PlaceService placeService;

    @Autowired
    ParticipantService participantService;

    @GetMapping("all")
    public ResponseEntity<List<EventDTO>> getAll(@PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable)
    {
        var eventAll = eventService.findAll();
        
        List<EventDTO> eventList = new ArrayList<>();
        
        for(Events event : eventAll) 
        {
            UUID id = event.getId();
            EventDTO eventObj = new EventDTO
            ( 
                event.getName(), event.getDescription(), event.getPayment(), event.getDate(), 
                event.getAthletics(), event.getImagens(), event.getAttraction(), event.getCategory(), event.getPlace()
            );

            eventObj.add( WebMvcLinkBuilder.linkTo
            (
                WebMvcLinkBuilder.methodOn( EventController.class ).get( id ) )
                .withRel("Editar") 
            );

            eventObj.add( WebMvcLinkBuilder.linkTo
            (
                WebMvcLinkBuilder.methodOn( EventController.class ).getAthletics( id ) )
                .withRel("List Athletics") 
            );

            eventObj.add( WebMvcLinkBuilder.linkTo
            (
                WebMvcLinkBuilder.methodOn( EventController.class ).getAttraction( id ) )
                .withRel("List Attraction") 
            );

            eventObj.add( WebMvcLinkBuilder.linkTo
            (
                WebMvcLinkBuilder.methodOn( EventController.class ).getImagens( id ) )
                .withRel("List Imagens") 
            );
            
            eventList.add(eventObj);       
        }

        return ResponseEntity.status(HttpStatus.OK).body( eventList );
    }

    @GetMapping("participant/{id}")
    public ResponseEntity<List<IdUserDTO>> getParticipants(@PathVariable(value = "id") UUID id ,@PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable)
    {
        Optional<Events> obj = eventService.findByEventsID(id);

        if (!obj.isPresent()) 
        {
            throw new NotFoundException("Not found!.");
        }
        
        var participantsAll = participantService.findByEventID( id );

        List<IdUserDTO> participantsList = new ArrayList<>();
        
        for(Participants participant : participantsAll) 
        {
           
            IdUserDTO eventObj = new IdUserDTO
            ( 
                participant.getUserId()
            );
            
            participantsList .add(eventObj);       
        }

        return ResponseEntity.status(HttpStatus.OK).body( participantsList  );
    }

    @PostMapping("add")
    public ResponseEntity<Object> save(@Valid @RequestBody EventDTO eventDTO)
    {
        if( eventService.existsByEventsname( eventDTO.getName() ))
        {
            throw new UnsupportedException("Conflict: Name is already in use! !");
        }
        if( placeService.existsByPlacesID( eventDTO.getPlace().getId() ) == false)
        {
            throw new NotFoundException("Conflict: Place not found! !");
        }
        if( categoryService.existsByCategorysID( eventDTO.getCategory().getId() ) == false)
        {
            throw new NotFoundException("Conflict: Categorys not found! !");
        }
       
        var eventEntities = new Events();
        BeanUtils.copyProperties( eventDTO, eventEntities);  

        eventEntities.setCreationDate(LocalDateTime.now( ZoneId.of("America/Sao_Paulo")) );
        eventEntities.setCategory( eventDTO.getCategory() );
        eventEntities.setPlace( eventDTO.getPlace() );
     
        eventService.save( eventEntities );

        Optional<Categorys> category = categoryService.findByCategorysID( eventDTO.getCategory().getId() );
        Optional<Places> place = placeService.findByPlacesID( eventDTO.getPlace().getId() );

        Places placeEntities = new Places
        ( 
            place.get().getId(), place.get().getName(), place.get().getLatitude(), place.get().getLongitude(), place.get().getCapacity() 
        );
        Categorys categoryEntities = new Categorys( category.get().getId(), category.get().getName() );

        eventDTO.setPlace( placeEntities );
        eventDTO.setCategory( categoryEntities );

        return ResponseEntity.status(HttpStatus.CREATED).body( eventDTO );
    }   

    @PostMapping("participant/{id}")
    public ResponseEntity<Object> participant(@PathVariable(value = "id") UUID id, @Valid @RequestBody IdUserDTO idUser)
    {
        Optional<Events> obj = eventService.findByEventsID(id);

        if (!obj.isPresent()) 
        {
            throw new NotFoundException("Not found!.");
        }

        if( participantService.existsByUserID( idUser.getId() ) == true )
        {
            throw new NotFoundException("Conflict!");
        }

        var participantEntities = new Participants();

        participantEntities.setUserId( idUser.getId() );
        participantEntities.setEventId( id );
        participantService.save(participantEntities);

        return ResponseEntity.status(HttpStatus.OK).body( "Successfull!" );
    }

    @GetMapping("{id}")
    public ResponseEntity<EventDTO> get(@PathVariable(value = "id") UUID id)
    {
        Optional<Events> obj = eventService.findByEventsID(id);

        if (!obj.isPresent()) 
        {
            throw new NotFoundException("Not found!.");
        }
       
        EventDTO eventObj = new EventDTO
        ( 
            obj.get().getName(), obj.get().getDescription(), obj.get().getPayment(), obj.get().getDate(),
            obj.get().getAthletics(), obj.get().getImagens(), obj.get().getAttraction(), obj.get().getCategory(), obj.get().getPlace()
        );

        eventObj.add
        ( 
            WebMvcLinkBuilder.linkTo( WebMvcLinkBuilder.methodOn( EventController.class ).getAll(null) )
            .withRel("Listar Tudo") 
        );

        eventObj.add( WebMvcLinkBuilder.linkTo
        (
            WebMvcLinkBuilder.methodOn( EventController.class ).getAthletics( id ) )
            .withRel("List Athletics") 
        );

        eventObj.add( WebMvcLinkBuilder.linkTo
        (
            WebMvcLinkBuilder.methodOn( EventController.class ).getAttraction( id ) )
            .withRel("List Attraction") 
        );

        eventObj.add( WebMvcLinkBuilder.linkTo
        (
            WebMvcLinkBuilder.methodOn( EventController.class ).getImagens( id ) )
            .withRel("List Imagens") 
        );

        return ResponseEntity.status(HttpStatus.OK).body( eventObj );
    }

    @GetMapping("imagens/{id}")
    public ResponseEntity<?> getImagens(@PathVariable(value = "id") UUID id)
    {
        Optional<Events> obj = eventService.findByEventsID(id);

        if (!obj.isPresent()) 
        {
            throw new NotFoundException("Not found!.");
        }
       
        EventDTO eventObj = new EventDTO
        ( 
            obj.get().getName(), obj.get().getDescription(), obj.get().getPayment(), obj.get().getDate(),
            obj.get().getAthletics(), obj.get().getImagens(), obj.get().getAttraction(), obj.get().getCategory(), obj.get().getPlace()
        );

        eventObj.add
        ( 
            WebMvcLinkBuilder.linkTo( WebMvcLinkBuilder.methodOn( EventController.class ).getAll(null) )
            .withRel("Listar Tudo") 
        );
        
        return ResponseEntity.status(HttpStatus.OK).body( eventObj.getImagens() );
    }

    @GetMapping("attraction/{id}")
    public ResponseEntity<?> getAttraction(@PathVariable(value = "id") UUID id)
    {
        Optional<Events> obj = eventService.findByEventsID(id);

        if (!obj.isPresent()) 
        {
            throw new NotFoundException("Not found!.");
        }
       
        EventDTO eventObj = new EventDTO
        ( 
            obj.get().getName(), obj.get().getDescription(), obj.get().getPayment(), obj.get().getDate(),
            obj.get().getAthletics(), obj.get().getImagens(), obj.get().getAttraction(), obj.get().getCategory(), obj.get().getPlace()
        );

        eventObj.add
        ( 
            WebMvcLinkBuilder.linkTo( WebMvcLinkBuilder.methodOn( EventController.class ).getAll(null) )
            .withRel("Listar Tudo") 
        );

        return ResponseEntity.status(HttpStatus.OK).body( eventObj.getAttraction() );
    }

    @GetMapping("athletics/{id}")
    public ResponseEntity<?> getAthletics(@PathVariable(value = "id") UUID id)
    {
        Optional<Events> obj = eventService.findByEventsID(id);

        if (!obj.isPresent()) 
        {
            throw new NotFoundException("Not found!.");
        }
       
        EventDTO eventObj = new EventDTO
        ( 
            obj.get().getName(), obj.get().getDescription(), obj.get().getPayment(), obj.get().getDate(),
            obj.get().getAthletics(), obj.get().getImagens(), obj.get().getAttraction(), obj.get().getCategory(), obj.get().getPlace()
        );

        eventObj.add
        ( 
            WebMvcLinkBuilder.linkTo( WebMvcLinkBuilder.methodOn( EventController.class ).getAll(null) )
            .withRel("Listar Tudo") 
        );

        return ResponseEntity.status(HttpStatus.OK).body( eventObj.getAthletics() );
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") UUID id)
    {
        Optional<Events> obj = eventService.findByEventsID(id);

        if (!obj.isPresent()) 
        {
            throw new NotFoundException("Not found!.");
        }
        
        eventService.delete( obj.get() );
        return ResponseEntity.status(HttpStatus.OK).body( "Successfully Deleted!" );
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> update(@PathVariable(value = "id") UUID id, @RequestBody @Valid EventDTO eventDTO)
    {
        Optional<Events> obj = eventService.findByEventsID(id);

        if (!obj.isPresent()) 
        {
            throw new NotFoundException("Not found!.");
        }
        if( !obj.get().getName().equals( eventDTO.getName() ) )
        {
            if( eventService.existsByEventsname( eventDTO.getName() ))
            {
                throw new UnsupportedException("Conflict: Name is already in use! !");
            }
        }
       
        var eventEntities = new Events();
        BeanUtils.copyProperties( eventDTO, eventEntities );  

        eventEntities.setId( obj.get().getId() );
        eventEntities.setCategory( eventDTO.getCategory() );
        eventEntities.setPlace( eventDTO.getPlace() );
    
        return ResponseEntity.status(HttpStatus.OK).body( eventService.save( eventEntities ) );
    }

    
}
