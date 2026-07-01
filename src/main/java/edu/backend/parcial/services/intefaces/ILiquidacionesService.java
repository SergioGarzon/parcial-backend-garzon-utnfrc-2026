package edu.backend.parcial.services.intefaces;

import edu.backend.parcial.models.Liquidacion;

import java.util.List;

public interface ILiquidacionesService {

    Liquidacion getById(Long id);

    List<Liquidacion> getAllLiquidaciones();

    Liquidacion getLiquidacion(String numeroTarjeta, Integer anio, Integer mes);

    boolean AddLiquidacion(Liquidacion liquidacion);

    boolean deleteLiquidacion(Long id);

}
