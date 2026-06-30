package edu.backend.parcial.services;

import edu.backend.parcial.models.Liquidaciones;
import edu.backend.parcial.repositories.*;
import edu.backend.parcial.dto.LiquidacionDTO;
import edu.backend.parcial.excepciones.TarjetaInexistenteException;

import java.io.IOException;
import java.util.List;

public class LiquidacionesServiceImplement implements LiquidacionService {

    private CotizacionesRepository cotizacionRepository;
    private ConsumosRepository consumoRepository;
    private TarjetasRepository tarjetaRepository;
    private LiquidacionesRepository liquidacionRepository;

    public LiquidacionesServiceImplement(CotizacionesRepository cotizacionRepository,
                                         ConsumosRepository consumoRepository,
                                         TarjetasRepository tarjetaRepository,
                                         LiquidacionesRepository liquidacionRepository) {
        this.cotizacionRepository = cotizacionRepository;
        this.consumoRepository = consumoRepository;
        this.tarjetaRepository = tarjetaRepository;
        this.liquidacionRepository = liquidacionRepository;
    }

    public LiquidacionDTO generarLiquidacion(long idTarjeta, int anio, int mes) throws TarjetaInexistenteException {

        /*
            private Long id;
            private Tarjetas tarjeta;
            private Integer mes;
            private Integer anio;
            private Double totalAPagar;
            private Double totalConsumos;
            private Double totalImpuestos;
            private Double totalDescuentos;
        */

        Liquidaciones liquidaciones = this.liquidacionRepository.getLiquidacion(String.valueOf(idTarjeta), anio, mes);

        /*
            Long id;
            String numeroTarjeta;
            String titular;
            int mes;
            int anio;
            double totalAPagar;
            double totalConsumos;
            double totalImpuestos;
            double totalDescuentos;
        */
        LiquidacionDTO liquidacionDTO = new LiquidacionDTO();
        liquidacionDTO.setId(liquidaciones.getId());
        liquidacionDTO.setNumeroTarjeta(liquidaciones.getTarjeta().getNumero());
        liquidacionDTO.setTitular(liquidaciones.getTarjeta().getTitular());
        liquidacionDTO.setMes(liquidaciones.getMes());
        liquidacionDTO.setAnio(liquidaciones.getAnio());
        liquidacionDTO.setTotalAPagar(liquidaciones.getTotalAPagar());
        liquidacionDTO.setTotalConsumos(liquidaciones.getTotalConsumos());
        liquidacionDTO.setTotalImpuestos(liquidaciones.getTotalImpuestos());
        liquidacionDTO.setTotalDescuentos(liquidaciones.getTotalDescuentos());

        return liquidacionDTO;
    }

    public List<String> getLiquidacionesPendientes(int anio, int mes) {
        return null;
    }

    public List<LiquidacionDTO> liquidarLote(String rutaArchivo) throws IOException {
        return null;
    }
}
