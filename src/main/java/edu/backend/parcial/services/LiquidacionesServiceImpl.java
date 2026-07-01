package edu.backend.parcial.services;

import edu.backend.parcial.repositories.LiquidacionesRepository;
import edu.backend.parcial.services.intefaces.ILiquidacionesService;
import jakarta.persistence.EntityManager;

public class LiquidacionesServiceImpl implements ILiquidacionesService {
    
    private LiquidacionesRepository liquidacionesRepository;

    public LiquidacionesServiceImpl(EntityManager em) {
        liquidacionesRepository = new LiquidacionesRepository(em);
    }


}
