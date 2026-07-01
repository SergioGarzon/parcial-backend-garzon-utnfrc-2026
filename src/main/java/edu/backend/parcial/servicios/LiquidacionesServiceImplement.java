package edu.backend.parcial.servicios;

import edu.backend.parcial.models.Liquidacion;
import edu.backend.parcial.models.Tarjeta;
import edu.backend.parcial.repositories.*;
import edu.backend.parcial.dto.LiquidacionDTO;
import edu.backend.parcial.excepciones.TarjetaInexistenteException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
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

    @Override
    public LiquidacionDTO generarLiquidacion(long idTarjeta, int anio, int mes) throws TarjetaInexistenteException {

        Liquidacion liquidaciones = this.liquidacionRepository.getLiquidacion(idTarjeta, anio, mes);

        LiquidacionDTO liquidacionDTO = new LiquidacionDTO();
        liquidacionDTO.setId(liquidaciones.getId());
        liquidacionDTO.setNumeroTarjeta(liquidaciones.getTarjeta().getNumero());
        liquidacionDTO.setTitular(liquidaciones.getTarjeta().getTitular());
        liquidacionDTO.setMes(liquidaciones.getMes());
        liquidacionDTO.setAnio(liquidaciones.getAnio());
        liquidacionDTO.setTotalAPagar(liquidaciones.getTotalConsumos() - liquidaciones.getTotalDescuentos() + liquidaciones.getTotalImpuestos());
        liquidacionDTO.setTotalConsumos(liquidaciones.getTotalConsumos());
        liquidacionDTO.setTotalImpuestos(liquidaciones.getTotalImpuestos());
        liquidacionDTO.setTotalDescuentos(liquidaciones.getTotalDescuentos());

        return liquidacionDTO;
    }

    @Override
    public List<String> getLiquidacionesPendientes(int anio, int mes) {
        List<Tarjeta> tarjetas = tarjetaRepository.getTarjetasSinLiquidacion(anio, mes);
        List<String> listaNumerosTarjetas = new LinkedList<>();

        for (Tarjeta t: tarjetas) {
            listaNumerosTarjetas.add(t.getNumero());
        }

        return listaNumerosTarjetas;
    }

    @Override
    public List<LiquidacionDTO> liquidarLote(String rutaArchivo) throws IOException {
        return null;
    }
}

