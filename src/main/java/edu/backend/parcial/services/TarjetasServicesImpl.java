package edu.backend.parcial.services;

import java.util.List;

import edu.backend.parcial.models.Tarjetas;
import edu.backend.parcial.repositories.TarjetasRepository;

public class TarjetasServicesImpl implements ITarjetaService<Tarjetas, Long> {

    private TarjetasRepository tarjetasRepository;

    public TarjetasServicesImpl() {
        tarjetasRepository = new TarjetasRepository();
    }

    @Override
    public List<Tarjetas> getAll() {
        return tarjetasRepository.getAll();
    }

    @Override
    public Tarjetas getById(Long id) {
        return tarjetasRepository.getById(id);
    }

    @Override
    public void add(Tarjetas entity) {
        tarjetasRepository.add(entity);
    }

    @Override
    public void update(Tarjetas entity) {
        tarjetasRepository.update(entity);
    }

    @Override
    public Tarjetas delete(Long id) {
        return tarjetasRepository.delete(id);
    }
}
    

