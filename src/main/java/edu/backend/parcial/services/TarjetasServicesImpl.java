package edu.backend.parcial.services;

import edu.backend.parcial.models.Tarjeta;
import edu.backend.parcial.repositories.TarjetasRepository;
import edu.backend.parcial.services.intefaces.ITarjetaService;
import jakarta.persistence.EntityManager;

import java.util.List;

public class TarjetasServicesImpl implements ITarjetaService<Tarjeta, Long> {

    private TarjetasRepository tarjetasRepository;

    public TarjetasServicesImpl(EntityManager em) {
        tarjetasRepository = new TarjetasRepository(em);
    }

    @Override
    public List<Tarjeta> getAll() {
        return tarjetasRepository.getAll();
    }

    @Override
    public Tarjeta getById(Long id) {
        return tarjetasRepository.getById(id);
    }

    @Override
    public Tarjeta getByNumero(String numero) {
        return tarjetasRepository.getByNumero(numero);
    }

    @Override
    public boolean addTarjeta(Tarjeta tarjeta) {
        return tarjetasRepository.AddTarjeta(tarjeta);
    }

    @Override
    public boolean removeTarjetaId(Long id) {
        return tarjetasRepository.deleteTarjeta(id);
    }

    @Override
    public boolean removeTarjetaNumero(String numero) {
        return tarjetasRepository.deleteTarjetaPorNumero(numero);
    }

}
    

