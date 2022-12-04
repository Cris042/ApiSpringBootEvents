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

import edu.ifgoiano.example.events.dtos.CategoryDTO;
import edu.ifgoiano.example.events.exceptions.others.NotFoundException;
import edu.ifgoiano.example.events.exceptions.others.UnsupportedException;
import edu.ifgoiano.example.events.models.Categorys;
import edu.ifgoiano.example.events.service.CategoryService;


@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RestController
@RequestMapping("/api/event/category")
public class CategoryController 
{
    @Autowired
    CategoryService categoryService;


    @GetMapping("all")
    public ResponseEntity<List<CategoryDTO>> getAll(@PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable)
    {
        var categorysAll = categoryService.findAll();
        
        List<CategoryDTO> categoryList = new ArrayList<>();
        
        for(Categorys category : categorysAll) 
        {
            UUID id = category.getId();
            CategoryDTO categoryObj = new CategoryDTO( category.getName() );
            categoryObj.add( WebMvcLinkBuilder.linkTo
            (
                WebMvcLinkBuilder.methodOn( CategoryController.class ).get( id ) )
                .withRel("Editar") 
            );
            
            categoryList.add(categoryObj);       
        }

        return ResponseEntity.status(HttpStatus.OK).body( categoryList );
    }

    @PostMapping("add")
    public ResponseEntity<Object> save(@Valid @RequestBody CategoryDTO categoryDTO)
    {
        if( categoryService.existsByCategorysname( categoryDTO.getName() ))
        {
            throw new UnsupportedException("Conflict: Name is already in use! !");
        }
       
        var categoryEntities = new Categorys( );
        BeanUtils.copyProperties( categoryDTO, categoryEntities);  
        categoryService.save( categoryEntities );

        return ResponseEntity.status(HttpStatus.CREATED).body( categoryDTO );
    }   

    @GetMapping("{id}")
    public ResponseEntity<CategoryDTO> get(@PathVariable(value = "id") UUID id)
    {
        Optional<Categorys> obj = categoryService.findByCategorysID(id);

        if (!obj.isPresent()) 
        {
            throw new NotFoundException("Not found!.");
        }
       
        CategoryDTO categoryObj = new CategoryDTO( obj.get().getName() );
        categoryObj.add
        ( 
            WebMvcLinkBuilder.linkTo( WebMvcLinkBuilder.methodOn( CategoryController.class ).getAll(null) )
            .withRel("Listar Tudo") 
        );

        return ResponseEntity.status(HttpStatus.OK).body( categoryObj );
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") UUID id)
    {
        Optional<Categorys> obj = categoryService.findByCategorysID(id);

        if (!obj.isPresent()) 
        {
            throw new NotFoundException("Not found!.");
        }
        
        categoryService.delete( obj.get() );
        return ResponseEntity.status(HttpStatus.OK).body( "Successfully Deleted!" );
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> update(@PathVariable(value = "id") UUID id, @RequestBody @Valid CategoryDTO categoryDTO)
    {
        Optional<Categorys> obj = categoryService.findByCategorysID(id);

        if (!obj.isPresent()) 
        {
            throw new NotFoundException("Not found!.");
        }
        if( !obj.get().getName().equals( categoryDTO.getName() ) )
        {
            if( categoryService.existsByCategorysname( categoryDTO.getName() ))
            {
                throw new UnsupportedException("Conflict: Name is already in use! !");
            }
        }
       

        var categoryEntities = new Categorys();
        BeanUtils.copyProperties( categoryDTO, categoryEntities);  
        categoryEntities.setId( obj.get().getId() );
    
        return ResponseEntity.status(HttpStatus.OK).body( categoryService.save( categoryEntities ) );
    }
}
