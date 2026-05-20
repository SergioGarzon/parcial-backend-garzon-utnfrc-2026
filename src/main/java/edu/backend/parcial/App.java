package edu.backend.parcial;

import java.util.List;

import java.sql.SQLException;

import edu.backend.parcial.models.Consumos;
import edu.backend.parcial.models.Cotizaciones;
import edu.backend.parcial.models.Liquidaciones;
import edu.backend.parcial.models.Tarjetas;
import edu.backend.parcial.services.ConsumosServiceImpl;
import edu.backend.parcial.services.CotizacionesServiceImpl;
import edu.backend.parcial.services.LiquidacionesServiceImpl;
import edu.backend.parcial.services.TarjetasServicesImpl;

import org.h2.tools.Server;


public class App {

    public static void main(String[] args) throws SQLException {       

        // Inicia en el navegador web el motor H2
        Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();

        TarjetasServicesImpl tarjetasServices = new TarjetasServicesImpl();

        List<Tarjetas> tarjetas = tarjetasServices.getAll();

        System.out.println("DATOS DE LA TARJETA");

        for (Tarjetas tarjeta : tarjetas) {
            System.out.println(tarjeta);
        }

        CotizacionesServiceImpl cotizacionesService = new CotizacionesServiceImpl();

        System.out.println("DATOS DE LA COTIZACIONES");

        List<Cotizaciones> cotizaciones = cotizacionesService.getAll();

        for (Cotizaciones cotizacion : cotizaciones) {
            System.out.println(cotizacion);
        }

        ConsumosServiceImpl consumosService = new ConsumosServiceImpl();

        System.out.println("DATOS DE LA CONSUMOS");

        List<Consumos> consumos = consumosService.getAll();

        for (Consumos consumo : consumos) {
            System.out.println(consumo);
        }

        LiquidacionesServiceImpl liquidacionesService = new LiquidacionesServiceImpl();

        System.out.println("DATOS DE LA LIQUIDACIONES");

        List<Liquidaciones> liquidaciones = liquidacionesService.getAll();

        for (Liquidaciones liquidacion : liquidaciones) {
            System.out.println(liquidacion);
        }
    }
}
