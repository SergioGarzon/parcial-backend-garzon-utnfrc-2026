package edu.backend.parcial.services;

import edu.backend.parcial.dto.LiquidacionDTO;
import edu.backend.parcial.models.Liquidaciones;
import edu.backend.parcial.repositories.LiquidacionesRepository;

import java.util.List;

public class LiquidacionesServiceImpl implements ILiquidacionesService {
    
    private LiquidacionesRepository liquidacionesRepository;

    public LiquidacionesServiceImpl() {
        liquidacionesRepository = new LiquidacionesRepository();
    }

    @Override
    public List<Liquidaciones> getAll() {
        return liquidacionesRepository.getAll();
    }

    @Override
    public Liquidaciones getById(Long id) {
        return liquidacionesRepository.getById(id);
    }

    @Override
    public void add(Liquidaciones entity) {
        liquidacionesRepository.add(entity);
    }

    @Override
    public void update(Liquidaciones entity) {
        liquidacionesRepository.update(entity);
    }

    @Override
    public Liquidaciones delete(Long id) {
        return liquidacionesRepository.delete(id);
    }

    @Override
    public LiquidacionDTO obtenerYMostrar() {
        Liquidaciones liq = liquidacionesRepository.getLiquidacion("4500123412340001", 2026, 5);

        LiquidacionDTO liquidacionDTO = new LiquidacionDTO();

        /*
        * private Long id;
            private String numeroTarjeta;
            private String titular;
            private int mes;
            private int anio;
            private double totalAPagar;
            private double totalConsumos;
            private double totalImpuestos;
            private double totalDescuentos;*/

        liquidacionDTO.setId(liq.getId());
        liquidacionDTO.setNumeroTarjeta(liq.getTarjeta().getNumero());
        liquidacionDTO.setTitular(liq.getTarjeta().getTitular());
        liquidacionDTO.setMes(liq.getMes());
        liquidacionDTO.setAnio(liq.getAnio());
        liquidacionDTO.setTotalAPagar(liq.getTotalAPagar());
        liquidacionDTO.setTotalConsumos(liq.getTotalConsumos());
        liquidacionDTO.setTotalImpuestos(liq.getTotalImpuestos());
        liquidacionDTO.setTotalDescuentos(liq.getTotalDescuentos());

        return liquidacionDTO;
    }
}
