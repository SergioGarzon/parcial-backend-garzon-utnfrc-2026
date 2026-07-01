package edu.backend.parcial.repositories;

import edu.backend.parcial.models.Cotizaciones;
import jakarta.persistence.EntityManager;

import java.util.List;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CotizacionesRepository extends Repository<Cotizaciones, String> {
    @Override
    public Cotizaciones getById(String moneda) {

        EntityManager manager = getManager();

        try {
            return manager.find(Cotizaciones.class, moneda);
        } finally {
            manager.close();
        }
    }

    @Override
    public List<Cotizaciones> getAll() {

        EntityManager manager = getManager();

        try {
            return manager.createQuery("FROM Cotizaciones",Cotizaciones.class).getResultList();
        } finally {
            manager.close();
        }
    }
}
