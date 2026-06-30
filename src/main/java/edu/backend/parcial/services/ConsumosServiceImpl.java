package edu.backend.parcial.services;

import edu.backend.parcial.models.Consumos;
import edu.backend.parcial.models.Cotizaciones;
import edu.backend.parcial.repositories.ConsumosRepository;

import java.util.List;

public class ConsumosServiceImpl implements IConsumoService {
 
    
    private ConsumosRepository consumosRepository;

    public ConsumosServiceImpl() {
        consumosRepository = new ConsumosRepository();
    }

    @Override
    public List<Consumos> getAll() {
        return consumosRepository.getAll();
    }
    
    @Override
    public Consumos getById(Long id) {
        return consumosRepository.getById(id);
    }

    @Override
    public void add(Consumos entity) {
        consumosRepository.add(entity);
    }

    @Override
    public void update(Consumos entity) {
        consumosRepository.update(entity);
    }

    @Override
    public Consumos delete(Long id) {
        return consumosRepository.delete(id);
    }

}
