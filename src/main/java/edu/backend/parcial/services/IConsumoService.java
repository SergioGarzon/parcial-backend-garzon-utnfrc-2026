package edu.backend.parcial.services;

import edu.backend.parcial.models.Consumos;

import java.util.List;

public interface IConsumoService {

    List<Consumos> getAll();

    Consumos getById(Long id);

    void add(Consumos entity);

    void update(Consumos entity);

    Consumos delete(Long id);
}
