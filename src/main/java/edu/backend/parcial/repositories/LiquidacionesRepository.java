package edu.backend.parcial.repositories;

import edu.backend.parcial.models.Liquidacion;
import edu.backend.parcial.excepciones.TarjetaInexistenteException;
import jakarta.persistence.EntityManager;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LiquidacionesRepository {

    private EntityManager manager;

    public LiquidacionesRepository(EntityManager em) {
        this.manager = em;
    }

    public Liquidacion getById(Long id) {
        return manager.find(Liquidacion.class, id);
    }

    public Liquidacion getLiquidacion(String numeroTarjeta, Integer anio, Integer mes) throws TarjetaInexistenteException {

        try {
            Long cantidadTarjetas = manager.createQuery(
                            "SELECT COUNT(t) FROM Tarjetas t WHERE t.numero = :numero", Long.class)
                    .setParameter("numero", numeroTarjeta)
                    .getSingleResult();

            if (cantidadTarjetas == 0) {
                throw new edu.backend.parcial.excepciones.TarjetaInexistenteException(numeroTarjeta);
            }

            return manager.createQuery("""
                FROM Liquidaciones l 
                WHERE l.tarjeta.numero = :numero 
                  AND l.anio = :anio 
                  AND l.mes = :mes
                """, Liquidacion.class)
                    .setParameter("numero", numeroTarjeta)
                    .setParameter("anio", anio)
                    .setParameter("mes", mes)
                    .getSingleResult();

        } catch (jakarta.persistence.NoResultException e) {
            return null;
        }
    }


}
