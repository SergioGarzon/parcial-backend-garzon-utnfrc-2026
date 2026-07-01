package edu.backend.parcial.services;

import edu.backend.parcial.models.Consumo;
import edu.backend.parcial.repositories.ConsumosRepository;
import edu.backend.parcial.services.intefaces.IConsumoService;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ConsumosServiceImpl implements IConsumoService {

    private ConsumosRepository consumosRepository;

    public ConsumosServiceImpl(EntityManager em) {
        consumosRepository = new ConsumosRepository(em);
    }

    @Override
    public List<Consumo> getAll() {
        return consumosRepository.getAll();
    }

    @Override
    public Consumo getById(Long id) {
        return consumosRepository.getById(id);
    }

    @Override
    public boolean AddConsumo(Consumo consumo) {
        return consumosRepository.AddConsumo(consumo);
    }

    @Override
    public boolean deleteConsumo(Long id) {
        return consumosRepository.deleteConsumo(id);
    }

    @Override
    public List<Consumo> getConsumosByTarjetaAnioMes(Long idTarjeta, Integer anio, Integer mes) {
        return consumosRepository.getConsumosByTarjetaAnioMes(idTarjeta, anio, mes);
    }



}
