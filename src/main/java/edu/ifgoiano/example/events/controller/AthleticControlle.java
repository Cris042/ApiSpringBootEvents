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

import edu.ifgoiano.example.events.dtos.AthleticDTO;
import edu.ifgoiano.example.events.dtos.request.IdAthleticDTO;
import edu.ifgoiano.example.events.exceptions.others.NotFoundException;
import edu.ifgoiano.example.events.exceptions.others.UnsupportedException;
import edu.ifgoiano.example.events.models.Athletics;
import edu.ifgoiano.example.events.models.Events;
import edu.ifgoiano.example.events.service.AthleticService;
import edu.ifgoiano.example.events.service.EventService;


@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RestController
@RequestMapping("/api/event/athletic")
public class AthleticControlle 
{
    @Autowired
    AthleticService athleticService;

    @Autowired
    EventService eventService;


    @GetMapping("all")
    public ResponseEntity<List<AthleticDTO>> getAll(@PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable)
    {
        var athleticsAll = athleticService.findAll();
        
        List<AthleticDTO> athleticsList = new ArrayList<>();
        
        for(Athletics athletic : athleticsAll) 
        {
            UUID id = athletic.getId();
            AthleticDTO athleticsObj = new AthleticDTO( athletic.getName(), athletic.getDescription(), athletic.getCourse() );
            athleticsObj.add
            ( 
                WebMvcLinkBuilder.linkTo( WebMvcLinkBuilder.methodOn( AthleticControlle.class ).get( id ) )
                .withRel("Editar") 
            );

            athleticsList.add(athleticsObj);       
        }

        return ResponseEntity.status(HttpStatus.OK).body( athleticsList );
    }

    @PostMapping("add")
    public ResponseEntity<Object> save(@Valid @RequestBody AthleticDTO athleticDTO)
    {
        if( athleticService.existsByAthleticsname( athleticDTO.getName() ))
        {
            throw new UnsupportedException("Conflict: Name is already in use! !");
        }
       
        var athleticEntities = new Athletics( );
        BeanUtils.copyProperties( athleticDTO, athleticEntities);  
        athleticService.save( athleticEntities );

        return ResponseEntity.status(HttpStatus.CREATED).body( athleticDTO );
    }  
    
    @PostMapping("event/{id}")
    public ResponseEntity<Object> saveAthleticEvent(@Valid  @RequestBody IdAthleticDTO athleticDTO ,@PathVariable(value = "id") UUID id )
    {
        Optional<Events> obj = eventService.findByEventsID(id);
        Optional<Athletics> athletic = athleticService.findByAthleticsID( athleticDTO.getId() );

        if (!obj.isPresent() || athletic.isPresent()) 
        {
            throw new NotFoundException("Not found!.");
        }

        Set<Athletics> athletics = new HashSet<>();
        var athleticEntities = new Athletics( athletic.get().getName(), athletic.get().getDescription(), athletic.get().getCourse() );

        athletics.add( athleticEntities );


        var thingEntities = new Events();
        BeanUtils.copyProperties( obj.get(), thingEntities);  
        thingEntities.setAthletics( athletics ); 

        eventService.save( thingEntities );
         
        return ResponseEntity.status(HttpStatus.CREATED).body( "Successfully|" );
    }

    @GetMapping("{id}")
    public ResponseEntity<AthleticDTO> get(@PathVariable(value = "id") UUID id)
    {
        Optional<Athletics> obj = athleticService.findByAthleticsID(id);

        if (!obj.isPresent()) 
        {
            throw new NotFoundException("Not found!.");
        }
       
        AthleticDTO athleticobj = new AthleticDTO( obj.get().getName(), obj.get().getDescription(), obj.get().getCourse() );
        athleticobj.add
        ( 
            WebMvcLinkBuilder.linkTo( WebMvcLinkBuilder.methodOn( AthleticControlle.class ).getAll(null) )
            .withRel("Listar Tudo") 
        );

        return ResponseEntity.status(HttpStatus.OK).body( athleticobj );
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") UUID id)
    {
        Optional<Athletics> obj = athleticService.findByAthleticsID(id);

        if (!obj.isPresent()) 
        {
            throw new NotFoundException("Not found!.");
        }
        
        athleticService.delete( obj.get() );
        return ResponseEntity.status(HttpStatus.OK).body( "Successfully Deleted!" );
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> update(@PathVariable(value = "id") UUID id, @RequestBody @Valid AthleticDTO athleticDTO)
    {
        Optional<Athletics> obj = athleticService.findByAthleticsID(id);

        if (!obj.isPresent()) 
        {
            throw new NotFoundException("Not found!.");
        }
        if( !obj.get().getName().equals( athleticDTO.getName() ) )
        {
            if( athleticService.existsByAthleticsname( athleticDTO.getName() ))
            {
                throw new UnsupportedException("Conflict: Name is already in use! !");
            }
        }
       

        var athleticEntities = new Athletics();
        BeanUtils.copyProperties( athleticDTO, athleticEntities);  
        athleticEntities.setId( obj.get().getId() );
    
        return ResponseEntity.status(HttpStatus.OK).body( athleticService.save( athleticEntities ) );
    }
}
