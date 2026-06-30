package edu.backend.parcial.services;

import edu.backend.parcial.models.Liquidaciones;

import java.util.List;

public interface ITarjetaService<T, K> {
    public List<T> getAll();

    T getById(K id);

    void add(T entity);

    void update(T entity);

    T delete(K id);

}
