package edu.backend.parcial.repositories;

import edu.backend.parcial.models.Liquidaciones;
import jakarta.persistence.EntityManager;

import java.util.List;

public class LiquidacionesRepository extends Repository<Liquidaciones, Long> {
    
    @Override
    public Liquidaciones getById(Long id) {

        EntityManager manager = getManager();

        try {
            return manager.find(Liquidaciones.class, id);
        } finally {
            manager.close();
        }
    }

    @Override
    public List<Liquidaciones> getAll() {

        EntityManager manager = getManager();

        try {
            return manager.createQuery("FROM Liquidaciones",Liquidaciones.class).getResultList();
        } finally {
            manager.close();
        }
    }

    public Liquidaciones getLiquidacion(String numeroTarjeta, Integer anio, Integer mes) {

        /*Aqui esta el problema por el cual no me traia bien la informacion*/
        EntityManager manager = getManager();

        try {

            Liquidaciones liquidaciones = new Liquidaciones();

            liquidaciones = manager.createQuery("""
                    FROM Liquidaciones""", Liquidaciones.class)

                    //  l.tarjeta.numero = :numero AND
                    //.setParameter("numero", numeroTarjeta)
                    //.setParameter("anio", anio)
                    //.setParameter("mes", mes)
                    .getSingleResult();

            return liquidaciones;

        } finally {
            manager.close();
        }
    }
}
