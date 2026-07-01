package edu.backend.parcial.services;

import edu.backend.parcial.excepciones.TarjetaInexistenteException;
import edu.backend.parcial.models.Liquidacion;
import edu.backend.parcial.repositories.LiquidacionesRepository;
import edu.backend.parcial.services.intefaces.ILiquidacionesService;
import jakarta.persistence.EntityManager;

import java.util.List;

public class LiquidacionesServiceImpl implements ILiquidacionesService {
    
    private LiquidacionesRepository liquidacionesRepository;

    public LiquidacionesServiceImpl(EntityManager em) {
        liquidacionesRepository = new LiquidacionesRepository(em);
    }

    @Override
    public Liquidacion getById(Long id) {
        return liquidacionesRepository.getById(id);
    }

    @Override
    public List<Liquidacion> getAllLiquidaciones() {
        return liquidacionesRepository.getAll();
    }

    @Override
    public Liquidacion getLiquidacion(Long numeroTarjeta, Integer anio, Integer mes) throws TarjetaInexistenteException  {
        return liquidacionesRepository.getLiquidacion(numeroTarjeta, anio, mes);
    }

    @Override
    public Liquidacion getLiquidacionTarjeta(String numeroTarjeta, Integer anio, Integer mes) throws TarjetaInexistenteException  {
        return liquidacionesRepository.getLiquidacionTarjeta(numeroTarjeta, anio, mes);
    }

    @Override
    public boolean AddLiquidacion(Liquidacion liquidacion) {
        return liquidacionesRepository.AddLiquidacion(liquidacion);
    }

    @Override
    public boolean deleteLiquidacion(Long id) {
        return liquidacionesRepository.deleteLiquidacion(id);
    }

}
