package edu.backend.parcial.services;

import edu.backend.parcial.models.Cotizaciones;

import java.util.List;

public interface ICotizacionesService {

    List<Cotizaciones> getAll();

    Cotizaciones getById(String id);

    void add(Cotizaciones entity);

    void update(Cotizaciones entity);

    Cotizaciones delete(String id);

}
