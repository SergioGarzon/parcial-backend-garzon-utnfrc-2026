package edu.backend.parcial.repositories;

import edu.backend.parcial.models.Consumo;
import jakarta.persistence.EntityManager;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class ConsumosRepository {

    private EntityManager manager;

    public ConsumosRepository(EntityManager em) {
        this.manager = em;
    }

    public List<Consumo> getAll() {
        return manager.createQuery("FROM Consumos",Consumo.class).getResultList();
    }




}
