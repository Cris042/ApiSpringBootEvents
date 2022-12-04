package edu.ifgoiano.example.events.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ifgoiano.example.events.models.Categorys;
import edu.ifgoiano.example.events.repository.CategoryRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Service
public class CategoryService 
{
    @Autowired
    CategoryRepository categoryRepository;
    
    @Transactional
    public Categorys save(Categorys obj) 
    {
        return categoryRepository.save( obj );
    }

    public Optional<Categorys> findByCategorysname(String name) 
    {
        return categoryRepository.findByName( name );
    }

    public Boolean existsByCategorysname(String name)
    {
        return categoryRepository.existsByName( name );
    }

    public Optional<Categorys> findByCategorysID(UUID id) 
    {
        return categoryRepository.findById( id );
    }

    public Boolean existsByCategorysID(UUID  id )
    {
        return categoryRepository.existsById( id );
    }   

    @Transactional
    public void delete(Categorys Categorys) 
    {
        categoryRepository.delete(Categorys);
    }

    public List<Categorys> findAll()
    {
        return categoryRepository.findAll();
    }

    public void deleteAll(List<Categorys> Categorys)
    {
        List<Categorys> allCategorys = categoryRepository.findAll();
        categoryRepository.deleteAll( allCategorys);
    }
    
}
