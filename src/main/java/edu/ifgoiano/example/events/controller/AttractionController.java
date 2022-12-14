package edu.ifgoiano.example.events.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

import edu.ifgoiano.example.events.dtos.AttractionDTO;
import edu.ifgoiano.example.events.dtos.request.IdAttractionDTO;
import edu.ifgoiano.example.events.exceptions.others.NotFoundException;
import edu.ifgoiano.example.events.exceptions.others.UnsupportedException;
import edu.ifgoiano.example.events.models.Attractions;
import edu.ifgoiano.example.events.service.AttractionService;
import edu.ifgoiano.example.events.service.EventService;


@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RestController
@RequestMapping("/api/event/attraction")
public class AttractionController 
{
    @Autowired
    AttractionService attractionService;
    
    @Autowired
    EventService eventService;


    @GetMapping("all")
    public ResponseEntity<List<AttractionDTO>> getAll(@PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable)
    {
        var attractionsAll = attractionService.findAll();
        
        List<AttractionDTO> attractionsList = new ArrayList<>();
        
        for(Attractions attraction : attractionsAll) 
        {
            UUID id = attraction.getId();
            AttractionDTO attractionObj = new AttractionDTO( attraction.getName(), attraction.getDescription() );
            attractionObj.add( WebMvcLinkBuilder.linkTo
            (
                WebMvcLinkBuilder.methodOn( AttractionController.class ).get( id ) )
                .withRel("Editar") 
            );

            attractionsList.add(attractionObj);       
        }

        return ResponseEntity.status(HttpStatus.OK).body( attractionsList );
    }

    @PostMapping("add")
    public ResponseEntity<Object> save(@Valid @RequestBody AttractionDTO attractionDTO)
    {
        if( attractionService.existsByAttractionsname( attractionDTO.getName() ))
        {
            throw new UnsupportedException("Conflict: Name is already in use! !");
        }
       
        var attractionEntities = new Attractions();
        BeanUtils.copyProperties( attractionDTO, attractionEntities);  
        attractionService.save( attractionEntities );

        return ResponseEntity.status(HttpStatus.CREATED).body( attractionDTO );
    }   

    @PostMapping("event/{id}")
    public ResponseEntity<Object> saveAttractionEvent(@Valid  @RequestBody IdAttractionDTO attractionDTO ,@PathVariable(value = "id") UUID id )
    {
        var obj = eventService.findByEventsID(id);
        var attraction = attractionService.findByAttractionsID( attractionDTO.getId() );

        if (!obj.isPresent() && attraction.isPresent()) 
        {
            throw new NotFoundException("Not found!.");
        }

        Set<Attractions> attractionsOLD = obj.get().getAttraction();
        Set<Attractions>attractions = new HashSet<>();
      
        attractions.add( attraction.get() );

        attractionsOLD.forEach( attractionOLD -> 
        {
            attractions.add( attractionOLD );
        });

        var thingEntities = obj.get();
        BeanUtils.copyProperties( obj.get(), thingEntities);  
        thingEntities.setAttraction( attractions ); 

        eventService.save( thingEntities );
         
        return ResponseEntity.status(HttpStatus.CREATED).body( "Successfully|" );
    }

    @GetMapping("{id}")
    public ResponseEntity<AttractionDTO> get(@PathVariable(value = "id") UUID id)
    {
        Optional<Attractions> obj = attractionService.findByAttractionsID(id);

        if (!obj.isPresent()) 
        {
            throw new NotFoundException("Not found!.");
        }
       
        AttractionDTO attractionObj = new AttractionDTO( obj.get().getName(), obj.get().getDescription() );
        attractionObj.add
        ( 
            WebMvcLinkBuilder.linkTo( WebMvcLinkBuilder.methodOn( AttractionController.class ).getAll(null) )
            .withRel("Listar Tudo") 
        );

        return ResponseEntity.status(HttpStatus.OK).body( attractionObj );
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") UUID id)
    {
        Optional<Attractions> obj = attractionService.findByAttractionsID(id);

        if (!obj.isPresent()) 
        {
            throw new NotFoundException("Not found!.");
        }
        
        attractionService.delete( obj.get() );
        return ResponseEntity.status(HttpStatus.OK).body( "Successfully Deleted!" );
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> update(@PathVariable(value = "id") UUID id, @RequestBody @Valid AttractionDTO attractionDTO)
    {
        Optional<Attractions> obj = attractionService.findByAttractionsID(id);

        if (!obj.isPresent()) 
        {
            throw new NotFoundException("Not found!.");
        }
        if( !obj.get().getName().equals( attractionDTO.getName() ) )
        {
            if( attractionService.existsByAttractionsname( attractionDTO.getName() ))
            {
                throw new UnsupportedException("Conflict: Name is already in use! !");
            }
        }
       

        var attractionEntities = new Attractions();
        BeanUtils.copyProperties( attractionDTO, attractionEntities);  
        attractionEntities.setId( obj.get().getId() );
    
        return ResponseEntity.status(HttpStatus.OK).body( attractionService.save( attractionEntities ) );
    }
}
