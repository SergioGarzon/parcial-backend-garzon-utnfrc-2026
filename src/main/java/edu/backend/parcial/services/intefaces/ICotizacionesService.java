package edu.backend.parcial.services.intefaces;

import edu.backend.parcial.models.Cotizacion;

import java.math.BigDecimal;
import java.util.List;

public interface ICotizacionesService {

    Cotizacion getByMoneda(String moneda);

    Cotizacion getByTasaCambio(BigDecimal tasaCambio);

    List<Cotizacion> getAllCotizaciones();

    boolean AddCotizacion(Cotizacion cotizacion);
}
