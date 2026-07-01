package edu.backend.parcial;

import java.sql.SQLException;

import edu.backend.parcial.services.ConsumosServiceImpl;
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
        

        if (em != null && em.isOpen()) {
            em.close();
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
        }

    }
}
