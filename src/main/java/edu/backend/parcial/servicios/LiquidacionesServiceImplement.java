package edu.backend.parcial.servicios;

import edu.backend.parcial.models.Consumo;
import edu.backend.parcial.models.Cotizacion;
import edu.backend.parcial.models.Liquidacion;
import edu.backend.parcial.models.Tarjeta;
import edu.backend.parcial.repositories.*;
import edu.backend.parcial.dto.LiquidacionDTO;
import edu.backend.parcial.excepciones.TarjetaInexistenteException;

import java.io.BufferedReader;
import java.io.FileReader;
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

        Liquidacion liquidaciones = liquidacionRepository.getLiquidacion(idTarjeta, anio, mes);

        List<Cotizacion> listaCotizaciones = cotizacionRepository.getAll();

        List<Consumo> consumosLista = consumoRepository.getConsumosByTarjetaAnioMes(liquidaciones.getTarjeta().getNumero(), anio, mes);

        double descuento = 0d;
        double descuentoAplicado = 0d;
        double impuesto = 0d;
        double impuestoExtraordinario = 0d;
        double consumos = 0d;
        double auxiliarConsumo = 0d;

        for (Consumo con: consumosLista) {
            if(con.getMoneda().equalsIgnoreCase("ARS")) {
                switch (con.getRubro()) {
                    case "COMBUSTIBLE":
                        //COMBUSTIBLE: 15% con tope de ARS 750 por transacción

                        descuento = (con.getMonto() * 0.15);

                        if (descuento <= 750)
                            descuentoAplicado += descuento;
                        else
                            descuentoAplicado += 750;
                        break;
                    case "SUPERMERCADO":
                        //SUPERMERCADOS: 20% con tope de ARS 3000 por transacción
                        descuento = (con.getMonto() * 0.2);

                        if (descuento <= 3000)
                            descuentoAplicado += descuento;
                        else
                            descuentoAplicado += 3000;
                        break;
                    case "RESTAURANTES":
                        //RESTAURANTES: 25% entre los días 10 y 15 de cada mes (ambos incluídos)
                        descuento = (con.getMonto() * 0.25);

                        if(con.getDia() >= 10 && con.getDia() <= 15) {
                            descuentoAplicado += descuento;
                        }
                        break;
                }

                if(con.getRubro().equalsIgnoreCase("OTROS")) {
                    impuesto += con.getMonto() * (0.12 + 0.21);
                } else {
                    impuesto += con.getMonto() * 0.21;
                }

                consumos += con.getMonto();
            }

            /*Cotizacion(moneda=ARS, tasaCambio=1.0)
                Cotizacion(moneda=USD, tasaCambio=1550.0)
                Cotizacion(moneda=EUR, tasaCambio=1680.0)
                Cotizacion(moneda=BRL, tasaCambio=280.0)
                Cotizacion(moneda=CLP, tasaCambio=1.45)*/

            switch (con.getMoneda()) {
                case "USD":
                    auxiliarConsumo = (con.getMonto() * 1550);
                    impuestoExtraordinario += auxiliarConsumo * 0.075;
                    consumos += auxiliarConsumo;
                    break;
                case "EUR":
                    auxiliarConsumo = (con.getMonto() * 1680);
                    impuestoExtraordinario += auxiliarConsumo * 0.075;
                    consumos += auxiliarConsumo;
                    break;
                case "BRL":
                    auxiliarConsumo = (con.getMonto() * 280);
                    impuestoExtraordinario += auxiliarConsumo * 0.075;
                    consumos += auxiliarConsumo;
                    break;
                case "CLP":
                    auxiliarConsumo = (con.getMonto() * 1.45);
                    impuestoExtraordinario += auxiliarConsumo * 0.075;
                    consumos += auxiliarConsumo;
                    break;
            }


        }

        LiquidacionDTO liquidacionDTO = new LiquidacionDTO();
        liquidacionDTO.setId(liquidaciones.getId());
        liquidacionDTO.setNumeroTarjeta(liquidaciones.getTarjeta().getNumero());
        liquidacionDTO.setTitular(liquidaciones.getTarjeta().getTitular());
        liquidacionDTO.setMes(liquidaciones.getMes());
        liquidacionDTO.setAnio(liquidaciones.getAnio());
        liquidacionDTO.setTotalAPagar(consumos - descuentoAplicado + impuesto + impuestoExtraordinario);
        liquidacionDTO.setTotalConsumos(consumos);
        liquidacionDTO.setTotalImpuestos(impuesto + impuestoExtraordinario);
        liquidacionDTO.setTotalDescuentos(descuentoAplicado);

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
        List<LiquidacionDTO> resultadoLote = new ArrayList<>();

        String rutaLimpia = rutaArchivo;
        if (rutaLimpia.startsWith("/") && System.getProperty("os.name").toLowerCase().contains("win")) {
            rutaLimpia = rutaLimpia.substring(1);
        }

        try (BufferedReader br = new BufferedReader(new FileReader(rutaLimpia))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] campos = linea.split(";");

                if (campos.length == 3) {

                    long idTarjeta = Long.parseLong(campos[0].trim());
                    int anio = Integer.parseInt(campos[1].trim());
                    int mes = Integer.parseInt(campos[2].trim());

                    try {
                        LiquidacionDTO dto = generarLiquidacion(idTarjeta, anio, mes);

                        if (dto != null) {
                            resultadoLote.add(dto);
                        }
                    } catch (edu.backend.parcial.excepciones.TarjetaInexistenteException e) {
                        System.err.println("Error en lote: " + e.getMessage());
                    }
                }
            }
        }

        return resultadoLote;
    }
}

