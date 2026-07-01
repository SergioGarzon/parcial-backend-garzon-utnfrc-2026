package edu.backend.parcial.services;

import edu.backend.parcial.models.Liquidaciones;
import edu.backend.parcial.dto.LiquidacionDTO;

import java.util.List;

public interface ILiquidacionesService {

    List<Liquidaciones> getAll();

    Liquidaciones getById(Long id);

    void add(Liquidaciones entity);

    void update(Liquidaciones entity);

    Liquidaciones delete(Long id);

    LiquidacionDTO obtenerYMostrar();
}
