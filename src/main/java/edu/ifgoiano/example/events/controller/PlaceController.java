package edu.ifgoiano.example.events.controller;

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

import edu.ifgoiano.example.events.dtos.PlaceDTO;
import edu.ifgoiano.example.events.exceptions.others.NotFoundException;
import edu.ifgoiano.example.events.exceptions.others.UnsupportedException;
import edu.ifgoiano.example.events.models.Places;
import edu.ifgoiano.example.events.service.PlaceService;


@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RestController
@RequestMapping("/api/event/place")
public class PlaceController 
{
    @Autowired
    PlaceService placeService;


    @GetMapping("all")
    public ResponseEntity<List<PlaceDTO>> getAll(@PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable)
    {
        var placesAll = placeService.findAll();
        
        List<PlaceDTO> placeList = new ArrayList<>();
        
        for(Places place : placesAll) 
        {
            UUID id = place.getId();
            PlaceDTO placeObj = new PlaceDTO( place.getName(), place.getLatitude(), place.getLongitude(), place.getCapacity() );
            placeObj.add( WebMvcLinkBuilder.linkTo
            (
                WebMvcLinkBuilder.methodOn( PlaceController.class ).get( id ) )
                .withRel("Editar") 
            );
            
            placeList.add(placeObj);       
        }

        return ResponseEntity.status(HttpStatus.OK).body( placeList );
    }

    @PostMapping("add")
    public ResponseEntity<Object> save(@Valid @RequestBody PlaceDTO placeDTO)
    {
        if( placeService.existsByPlacesname( placeDTO.getName() ))
        {
            throw new UnsupportedException("Conflict: Name is already in use! !");
        }
       
        var placeEntities = new Places( );
        BeanUtils.copyProperties( placeDTO, placeEntities);  
        placeService.save( placeEntities );

        return ResponseEntity.status(HttpStatus.CREATED).body( placeDTO );
    }   

    @GetMapping("{id}")
    public ResponseEntity<PlaceDTO> get(@PathVariable(value = "id") UUID id)
    {
        Optional<Places> obj = placeService.findByPlacesID(id);

        if (!obj.isPresent()) 
        {
            throw new NotFoundException("Not found!.");
        }
       
        PlaceDTO placeObj = new PlaceDTO( obj.get().getName(), obj.get().getLatitude(), obj.get().getLongitude(), obj.get().getCapacity() );
        placeObj.add
        ( 
            WebMvcLinkBuilder.linkTo( WebMvcLinkBuilder.methodOn( PlaceController.class ).getAll(null) )
            .withRel("Listar Tudo") 
        );

        return ResponseEntity.status(HttpStatus.OK).body( placeObj );
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") UUID id)
    {
        Optional<Places> obj = placeService.findByPlacesID(id);

        if (!obj.isPresent()) 
        {
            throw new NotFoundException("Not found!.");
        }
        
        placeService.delete( obj.get() );
        return ResponseEntity.status(HttpStatus.OK).body( "Successfully Deleted!" );
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> update(@PathVariable(value = "id") UUID id, @RequestBody @Valid PlaceDTO placeDTO)
    {
        Optional<Places> obj = placeService.findByPlacesID(id);

        if (!obj.isPresent()) 
        {
            throw new NotFoundException("Not found!.");
        }
        if( !obj.get().getName().equals( placeDTO.getName() ) )
        {
            if( placeService.existsByPlacesname( placeDTO.getName() ))
            {
                throw new UnsupportedException("Conflict: Name is already in use! !");
            }
        }
       

        var placeEntities = new Places();
        BeanUtils.copyProperties( placeDTO, placeEntities );  
        placeEntities.setId( obj.get().getId() );
    
        return ResponseEntity.status(HttpStatus.OK).body( placeService.save( placeEntities ) );
    }
}
