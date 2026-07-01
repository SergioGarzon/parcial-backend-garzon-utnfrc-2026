package edu.backend.parcial.services;

import edu.backend.parcial.models.Cotizacion;
import edu.backend.parcial.repositories.CotizacionesRepository;
import edu.backend.parcial.services.intefaces.ICotizacionesService;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.util.List;

public class CotizacionesServiceImpl implements ICotizacionesService {
    
    private CotizacionesRepository cotizacionesRepository;

    public CotizacionesServiceImpl(EntityManager em) {
        cotizacionesRepository = new CotizacionesRepository(em);
    }

    @Override
    public Cotizacion getByMoneda(String moneda) {
        return cotizacionesRepository.getByMoneda(moneda);
    }

    @Override
    public Cotizacion getByTasaCambio(BigDecimal tasaCambio) {
        return cotizacionesRepository.getByTasaCambio(tasaCambio);
    }

    @Override
    public List<Cotizacion> getAllCotizaciones() {
        return cotizacionesRepository.getAll();
    }

    @Override
    public boolean AddCotizacion(Cotizacion cotizacion) {
        return cotizacionesRepository.AddCotizacion(cotizacion);
    }
}
