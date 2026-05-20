package edu.backend.parcial.services;

import java.util.List;


public interface  IService<T, K> {

    public List<T> getAll();

    T getById(K id);

    void add(T entity);

    void update(T entity);

    T delete(K id);
    
} 
