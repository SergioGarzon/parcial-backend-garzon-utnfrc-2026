package edu.backend.parcial.services.intefaces;

import edu.backend.parcial.models.Consumo;

import java.util.List;

public interface IConsumoService {

    List<Consumo> getAll();

    Consumo getById(Long id);

    boolean AddConsumo(Consumo consumo);

    boolean deleteConsumo(Long id);

    List<Consumo> getConsumosByTarjetaAnioMes(String numeroTarjeta, Integer anio, Integer mes);

    List<Consumo> getConsumoTarjeta(Integer anio, Integer mes);

}
