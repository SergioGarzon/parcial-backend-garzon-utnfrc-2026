package edu.backend.parcial;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import edu.backend.parcial.dto.LiquidacionDTO;
import edu.backend.parcial.models.Consumo;
import edu.backend.parcial.models.Liquidacion;
import edu.backend.parcial.services.ConsumosServiceImpl;
import edu.backend.parcial.services.LiquidacionesServiceImpl;
import edu.backend.parcial.services.TarjetasServicesImpl;
import edu.backend.parcial.models.Tarjeta;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.h2.tools.Server;


public class App {

    public static void main(String[] args) throws SQLException {

        
        // Inicia en el navegador web el motor H2
        Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("parcial-garzon");
        EntityManager em = emf.createEntityManager();

        TarjetasServicesImpl tarjetasServices = new TarjetasServicesImpl(em);
        ConsumosServiceImpl consumosService = new ConsumosServiceImpl(em);
        LiquidacionesServiceImpl liquidacionesService = new LiquidacionesServiceImpl(em);

        /*
        for (Tarjeta t: tarjetasServices.getAll()) {
            System.out.println(t.toString());
        }

        System.out.println("\nBuscamos tarjeta por ID = 1");
        System.out.println(tarjetasServices.getById(1l));

        System.out.println("\n\nBuscamos tarjeta por Numero = 4500123412340001");
        System.out.println(tarjetasServices.getByNumero("4500123412340001"));

        System.out.println("\n\nAgregamos una tarjeta");
        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setNumero("45646688878788");
        tarjeta.setTitular("erere");
        tarjeta.setLimiteCredito(545.2d);

        System.out.println((tarjetasServices.addTarjeta(tarjeta)) ? "AGREGADA" : "NO AGREGADA");

        System.out.println("\n\nBuscamos Consumo de Tarjeta");
        List<Consumo> listaConsumos = consumosService.getConsumosByTarjetaAnioMes("5555", 2026, 5);

        listaConsumos.stream().forEach(System.out::println);

        */


        Liquidacion liquidacion = liquidacionesService.getLiquidacion(1L, 2026, 5);

        LiquidacionDTO liquidacionDTO = new LiquidacionDTO();
        liquidacionDTO.setId(liquidacion.getId());
        liquidacionDTO.setNumeroTarjeta(liquidacion.getTarjeta().getNumero());
        liquidacionDTO.setTitular(liquidacion.getTarjeta().getTitular());
        liquidacionDTO.setMes(liquidacion.getMes());
        liquidacionDTO.setAnio(liquidacion.getAnio());
        liquidacionDTO.setTotalAPagar(liquidacion.getTotalAPagar() - liquidacion.getTotalDescuentos() + liquidacion.getTotalImpuestos());
        liquidacionDTO.setTotalConsumos(liquidacion.getTotalConsumos());
        liquidacionDTO.setTotalImpuestos(liquidacion.getTotalImpuestos());
        liquidacionDTO.setTotalDescuentos(liquidacion.getTotalDescuentos());

        System.out.println(liquidacionDTO.toString());

        List<Tarjeta> tarjetas = tarjetasServices.getTarjetasSinLiquidacion(2026, 5);
        List<String> listaNumerosTarjetas = new LinkedList<>();

        for (Tarjeta t: tarjetas) {
            listaNumerosTarjetas.add(t.getNumero());
        }

        for (int i = 0; i < listaNumerosTarjetas.size() ; i++) {
            System.out.println(listaNumerosTarjetas.get(i));
        }



        if (em != null && em.isOpen()) {
            em.close();
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
        }

        return;

    }
}
