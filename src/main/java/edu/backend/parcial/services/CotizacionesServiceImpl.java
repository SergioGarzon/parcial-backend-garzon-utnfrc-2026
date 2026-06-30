package edu.backend.parcial.services;

import edu.backend.parcial.models.Cotizaciones;
import edu.backend.parcial.repositories.CotizacionesRepository;

import java.util.List;

public class CotizacionesServiceImpl implements ICotizacionesService {
    
    private CotizacionesRepository cotizacionesRepository;

    public CotizacionesServiceImpl() {
        cotizacionesRepository = new CotizacionesRepository();
    }

    @Override
    public List<Cotizaciones> getAll() {
        return cotizacionesRepository.getAll();
    }
    
    @Override
    public Cotizaciones getById(String id) {
        return cotizacionesRepository.getById(id);
    }

    @Override
    public void add(Cotizaciones entity) {
        cotizacionesRepository.add(entity);
    }

    @Override
    public void update(Cotizaciones entity) {
        cotizacionesRepository.update(entity);
    }

    @Override
    public Cotizaciones delete(String id) {
        return cotizacionesRepository.delete(id);
    }

}
