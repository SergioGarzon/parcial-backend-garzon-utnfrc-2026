package edu.backend.parcial.services.intefaces;

import edu.backend.parcial.models.Tarjeta;

import java.util.List;

public interface ITarjetaService<Tarjeta, Long> {

    List<Tarjeta> getAll();

    Tarjeta getById(Long id);

    Tarjeta getByNumero(String numero);

    boolean addTarjeta(Tarjeta tarjeta);

    boolean removeTarjetaId(Long id);

    boolean removeTarjetaNumero(String numero);
}
