package edu.backend.parcial.services;

import edu.backend.parcial.models.Liquidaciones;

import java.util.List;

public interface ILiquidacionesService {

    List<Liquidaciones> getAll();

    Liquidaciones getById(Long id);

    void add(Liquidaciones entity);

    void update(Liquidaciones entity);

    Liquidaciones delete(Long id);

    String obtenerYMostrar();
}
