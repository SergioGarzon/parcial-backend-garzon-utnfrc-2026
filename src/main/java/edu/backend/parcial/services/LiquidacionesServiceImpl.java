package edu.backend.parcial.services;

import edu.backend.parcial.models.Liquidaciones;
import edu.backend.parcial.models.Tarjetas;
import edu.backend.parcial.repositories.LiquidacionesRepository;

import java.util.List;

public class LiquidacionesServiceImpl implements ILiquidacionesService {
    
    private LiquidacionesRepository liquidacionesRepository;

    public LiquidacionesServiceImpl() {
        liquidacionesRepository = new LiquidacionesRepository();
    }

    @Override
    public List<Liquidaciones> getAll() {
        return liquidacionesRepository.getAll();
    }

    @Override
    public Liquidaciones getById(Long id) {
        return liquidacionesRepository.getById(id);
    }

    @Override
    public void add(Liquidaciones entity) {
        liquidacionesRepository.add(entity);
    }

    @Override
    public void update(Liquidaciones entity) {
        liquidacionesRepository.update(entity);
    }

    @Override
    public Liquidaciones delete(Long id) {
        return liquidacionesRepository.delete(id);
    }

    @Override
    public String obtenerYMostrar() {
        Liquidaciones liq = liquidacionesRepository.getLiquidacion("999999", 2026, 5);
        return liq.toString();
    }
}
