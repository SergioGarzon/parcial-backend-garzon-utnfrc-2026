package edu.backend.parcial;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import edu.backend.parcial.repositories.LiquidacionesRepository;
import edu.backend.parcial.repositories.TarjetasRepository;
import edu.backend.parcial.repositories.ConsumosRepository;
import edu.backend.parcial.repositories.CotizacionesRepository;
import edu.backend.parcial.excepciones.TarjetaInexistenteException;
import edu.backend.parcial.dto.LiquidacionDTO;
import edu.backend.parcial.servicetest.LiquidacionService;
import edu.backend.parcial.servicetest.LiquidacionesServiceImplement;

public class LiquidacionServiceImplTest {


    private EntityManager em;
    private EntityManagerFactory emf;
    private LiquidacionService liquidacionService;

    @BeforeEach
    void setUp() {
        emf = Persistence.createEntityManagerFactory("parcial-garzon");
        em = emf.createEntityManager();

        CotizacionesRepository cotizacionRepository = new CotizacionesRepository(/*em*/);
        ConsumosRepository consumoRepository = new ConsumosRepository(/*em*/);
        TarjetasRepository tarjetaRepository = new TarjetasRepository(/*em*/);
        LiquidacionesRepository liquidacionRepository = new LiquidacionesRepository(/*em*/);

        liquidacionService = new LiquidacionesServiceImplement(
                cotizacionRepository,
                consumoRepository,
                tarjetaRepository,
                liquidacionRepository
        );

        /*

        ATENCIÓN!!!!

        Esto es un Ejemplo de inicialización del servicio, pero aquí va el código
        que hayan hecho para crear su servicio.
        */

        /*
        CotizacionRepository cotizacionRepository = new CotizacionRepository(em);
        ConsumoRepository consumoRepository = new ConsumoRepository(em);
        TarjetaRepository tarjetaRepository = new TarjetaRepository(em);
        LiquidacionRepository liquidacionRepository = new LiquidacionRepository(em);

        liquidacionService = new LiquidacionServiceImpl(
                cotizacionRepository,
                consumoRepository,
                tarjetaRepository,
                liquidacionRepository
        );
        */
    }

    @AfterEach
    void tearDown() {
        if (em != null && em.isOpen()) {
            em.close();
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    @Test
    void testTarjetaInexistente() {
        assertThrows(TarjetaInexistenteException.class, () -> liquidacionService.generarLiquidacion(/*999999*/ 4500123412340001l, 2026, 5));
    }

    @Test
    void testGenerarLiquidacion() {
        em.getTransaction().begin();
        LiquidacionDTO liquidacion = liquidacionService.generarLiquidacion(1L, 2026, 5);
        em.getTransaction().commit();

        assertNotNull(liquidacion);
        assertEquals("4500123412340001", liquidacion.getNumeroTarjeta());
        assertEquals(2026, liquidacion.getAnio());
        assertEquals(5, liquidacion.getMes());
        assertEquals("Juan Perez", liquidacion.getTitular());

        assertEquals(436400.0, liquidacion.getTotalConsumos(), 0.01);
        assertEquals(0.45, liquidacion.getTotalImpuestos(), 0.01);
        assertEquals(4950.0, liquidacion.getTotalDescuentos(), 0.01);
        assertEquals(431450.45, liquidacion.getTotalAPagar(), 0.01);
    }

    @Test
    void testGetLiquidacionesPendientes() {
        em.getTransaction().begin();
        List<String> pendientesAntes = liquidacionService.getLiquidacionesPendientes(2026, 5);
        assertEquals(10, pendientesAntes.size());

        liquidacionService.generarLiquidacion(1L, 2026, 5);
        em.getTransaction().commit();

        em.getTransaction().begin();
        List<String> pendientesDespues = liquidacionService.getLiquidacionesPendientes(2026, 5);
        assertEquals(9, pendientesDespues.size());
        assertFalse(pendientesDespues.contains("4500123412340001"));
        em.getTransaction().commit();
    }

    @Test
    void testLiquidarLote() throws IOException {
        URL url = getClass().getClassLoader().getResource("liquidaciones.csv");
        assertNotNull(url, "No se encontró el archivo de lotes liquidaciones.csv");

        em.getTransaction().begin();
        List<LiquidacionDTO> liquidaciones = liquidacionService.liquidarLote(url.getPath());
        em.getTransaction().commit();

        assertFalse(liquidaciones.isEmpty());
    }
}
